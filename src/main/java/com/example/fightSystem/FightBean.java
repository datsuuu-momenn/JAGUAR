package com.example.fightSystem;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.example.fightSystem.entity.Skill;
import com.example.fightSystem.repository.SkillRepository;
import com.example.fightSystem.controller.FightController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
public class FightBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static final int[] HP_VALUES = {120, 160, 180, 200, 210, 230, 250, 270, 300};
    
    @Getter
    @Setter
    private int maxHp = 120; // デフォルト値
    
    @Getter
    @Setter
    private int currentHp = maxHp;
    
    // HPの比率を計算（進捗バー用）
    public double getHpRatio() {
        return (double) currentHp / maxHp;
    }
    
    // 新規ユーザー追加時にHPをランダムに設定
    public void randomizeMaxHp() {
        Random random = new Random();
        maxHp = HP_VALUES[random.nextInt(HP_VALUES.length)];
        currentHp = maxHp; // 新しい最大値で現在のHPを初期化
        System.out.println("New max HP set to: " + maxHp);
    }
    
    // HP回復メソッド（既存のresetHp()を修正）
    public void resetHp() {
        currentHp = maxHp;
        battleResult = "HPが全回復しました！";
    }
    
    /*** 本来DB等から拾って来るデータ ***************************************************/
	//従業員マスタ
	static List<SelectItem> OPPONENTS_LIST;
	static {
		OPPONENTS_LIST = new ArrayList<SelectItem>();
		OPPONENTS_LIST.add(new SelectItem("E0000","モクロー"));
		OPPONENTS_LIST.add(new SelectItem("E0001","ヒノアラシ"));
		OPPONENTS_LIST.add(new SelectItem("E0002","ワニノコ"));
		OPPONENTS_LIST.add(new SelectItem("E0003","ゼニガメ"));
	}

	//スキルマスタ
	private static Map<Object,List<SelectItem>> SKILL_MAP;
	static {
		List<SelectItem> opponent0Skills = new ArrayList<SelectItem>();
		opponent0Skills.add(new SelectItem("skill_01","リーフブレード"));
		opponent0Skills.add(new SelectItem("skill_02","ブレイブバード"));
		opponent0Skills.add(new SelectItem("skill_03","はっぱカッター"));

		List<SelectItem> opponent1Skills = new ArrayList<SelectItem>();
		opponent1Skills.add(new SelectItem("skill_04","マヒルノツメ"));
		opponent1Skills.add(new SelectItem("skill_04","マヒルノツメ"));
		opponent1Skills.add(new SelectItem("skill_03","はっぱカッター"));

		List<SelectItem> opponent2Skills = new ArrayList<SelectItem>();
		opponent2Skills.add(new SelectItem("skill_02","ブレイブバード"));
		opponent2Skills.add(new SelectItem("skill_02","ブレイブバード"));
		opponent2Skills.add(new SelectItem("skill_02","ブレイブバード"));

		List<SelectItem> opponent3Skills = new ArrayList<SelectItem>();
		opponent3Skills.add(new SelectItem("skill_04","マヒルノツメ"));
		opponent3Skills.add(new SelectItem("skill_04","マヒルノツメ"));
		opponent3Skills.add(new SelectItem("skill_03","はっぱカッター"));

		SKILL_MAP = new HashMap<Object,List<SelectItem>>();
		SKILL_MAP.put("E0000", opponent0Skills);
		SKILL_MAP.put("E0001", opponent1Skills);
		SKILL_MAP.put("E0002", opponent2Skills);
		SKILL_MAP.put("E0003", opponent3Skills);
	}
	/********************************************************************************/

	//選択された従業員
	@Getter
	@Setter
	private String selectedOpponent;
	
	//選択されたスキル
    @Getter
    @Setter
    private String selectedSkill;

	//選択されたスキルID
	@Getter
	@Setter
	private int selectedSkillId = 0;

	//従業員リスト
	@Getter
	@Setter
	private List<SelectItem> opponentsList = OPPONENTS_LIST;
	
	//スキルリスト
	@Getter
	@Setter
	private List<SelectItem> skillList;

	@PostConstruct
	public void init() {
		// デフォルトで最初の敵（モクロー）のスキルリストを設定
		selectedOpponent = "E0000";
		skillList = SKILL_MAP.get(selectedOpponent);
	}

	// スキルIDを更新
	public void updateSelectedSkillId() {
		if(selectedSkill != null) {
			selectedSkillId = Integer.parseInt(selectedSkill.replace("skill_", ""));
		}
	}

	/*** method ****************************************************************/
	public void changeSkillList() {
		this.skillList = SKILL_MAP.get(this.selectedOpponent);
	}

    //--------------------------------------------

    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Inject
    private SkillRepository skillRepository;
    
    @Inject
    private FightController fightController;
    
    @Getter
    @Setter
    private String battleResult;
    
    // 戦闘実行メソッド
    @Transactional
    public void fight() {
        if (selectedSkill == null || selectedOpponent == null) {
            battleResult = "敵とスキルを選択してください！";
            return;
        }

        // スキルIDを更新
        updateSelectedSkillId();
        
        // 選択された敵の名前を取得
        String opponentName = opponentsList.stream()
            .filter(item -> item.getValue().equals(selectedOpponent))
            .map(item -> item.getLabel())
            .findFirst()
            .orElse("不明な敵");
            
        // 選択されたスキルの名前を取得
        String skillName = skillList.stream()
            .filter(item -> item.getValue().equals(selectedSkill))
            .map(item -> item.getLabel())
            .findFirst()
            .orElse("不明なスキル");
            
        battleResult = opponentName + "の" + skillName + "！　";
        
        // ダメージ計算と適用を確実に行う
        try {
            // ダメージを計算
            int damage = calculateDamage(selectedSkillId);
            
            // 実際にHPを減少
            if (damage > 0) {
                takeDamage(damage);
                System.out.println("Damage dealt: " + damage + ", Current HP: " + currentHp);
            }
            
        } catch (Exception e) {
            battleResult = "技の処理中にエラーが発生しました：" + e.getMessage();
            e.printStackTrace();
        }
    }
    
    // 戦闘計算メソッド
    @Transactional
    public int calculateDamage(int skillId) {
        System.out.println("Calculating damage for skill ID: " + skillId); // デバッグ出力
        
        Skill skill = fightController.getSkill(skillId);
        if (skill == null) {
            System.out.println("Skill not found in database for ID: " + skillId); // デバッグ出力
            battleResult = "技の情報が見つかりません（ID: " + skillId + "）";
            return 0;
        }
        
        System.out.println("Found skill: " + skill.getSkillName()); // デバッグ出力
        
        int baseDamage = skill.getDamage();
        int accuracy = skill.getAccuracy();
        int criticalRate = skill.getCriticalRate();
        
        System.out.println("Base damage: " + baseDamage + ", Accuracy: " + accuracy + ", Critical rate: " + criticalRate); // デバッグ出力
        
        // 命中判定
        if (Math.random() * 100 > accuracy) {
            battleResult = "ミス！ダメージなし";
            return 0;
        }
        
        // クリティカル判定
        boolean isCritical = Math.random() * 100 < criticalRate;
        double finalDamage = baseDamage;
        if (isCritical) {
            finalDamage = baseDamage * 1.5;
            battleResult = "クリティカルヒット！" + (int)finalDamage + "のダメージ";
        } else {
            battleResult = (int)finalDamage + "のダメージ";
        }
        
        // スキルの詳細情報を取得して戦闘結果に追加
        Map<String, Object> skillDetails = fightController.getSkillDetails(skillId);
        if (skillDetails != null) {
            battleResult += String.format("（%s属性）", skillDetails.get("type"));
        }
        
        return (int)finalDamage;
    }
    
    // ダメージを受ける処理
    public void takeDamage(int damage) {
        int oldHp = currentHp;
        currentHp = Math.max(0, currentHp - damage);
        System.out.println("HP reduced from " + oldHp + " to " + currentHp); // デバッグ用
        
        if (currentHp == 0) {
            battleResult += "　戦闘不能！";
        }
    }
}
