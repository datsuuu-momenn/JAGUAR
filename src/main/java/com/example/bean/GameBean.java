package com.example.bean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.internal.log.SubSystemLogging;

/**
 * GameBean - 囚人のジレンマゲームのロジックを管理するManaged Bean
 * セッションスコープでプレイヤーAの選択、ラウンド情報、利得表を管理。
 */
@Named
@Getter
@Setter
@SessionScoped
public class GameBean implements Serializable {

    private String playerAChoice = "C"; // プレイヤーAの選択
    private String playerBChoice = "C"; // プレイヤーBの選択 (デフォルトは合作)
    private int round = 1;             // 現在のラウンド
    private double playerAPayoff;      // プレイヤーAの利得
    private double playerBPayoff;      // プレイヤーBの利得
    private double totalAPayoff = 0;   // プレイヤーAの累計利得
    private double totalBPayoff = 0;   // プレイヤーBの累計利得

    private double[] payoffCC = new double[2]; // 利得表：合作 - 合作
    private double[] payoffCD = new double[2]; // 利得表：合作 - 背叛
    private double[] payoffDC = new double[2]; // 利得表：背叛 - 合作
    private double[] payoffDD = new double[2]; // 利得表：背叛 - 背叛

    // 各ラウンドの履歴
    private List<String> roundHistory = new ArrayList<>();
    private static final int TOTAL_ROUNDS = 10; // 総ラウンド数

    /**
     * プレイヤーAの選択に基づき、プレイヤーBの戦略を調整。
     */
    public void play() {
        // 履歴に基づくプレイヤーBの選択調整
        int cooperateCount = (int) roundHistory.stream().filter(choice -> choice.startsWith("C")).count();
        if (cooperateCount > round / 2) {
            playerBChoice = "C"; // プレイヤーAが合作を多く選んだ場合、プレイヤーBも合作を選ぶ
        } else {
            playerBChoice = "D"; // プレイヤーAが背叛を多く選んだ場合、プレイヤーBは背叛を選ぶ
        }

        // 各戦略に応じた利得を計算
        if ("C".equals(playerAChoice) && "C".equals(playerBChoice)) {
            playerAPayoff = payoffCC[0];
            playerBPayoff = payoffCC[1];
        } else if ("C".equals(playerAChoice) && "D".equals(playerBChoice)) {
            playerAPayoff = payoffCD[0];
            playerBPayoff = payoffCD[1];
        } else if ("D".equals(playerAChoice) && "C".equals(playerBChoice)) {
            playerAPayoff = payoffDC[0];
            playerBPayoff = payoffDC[1];
        } else {
            playerAPayoff = payoffDD[0];
            playerBPayoff = payoffDD[1];
        }

        // 履歴と累計利得の更新
        roundHistory.add(playerAChoice + ", " + playerBChoice);
        totalAPayoff += playerAPayoff;
        totalBPayoff += playerBPayoff;

        // 次のラウンドに進む
        if (round < TOTAL_ROUNDS) {
            round++;
        }
        updatePayoffTable();
    }

    /**
     * 利得表の更新。
     */
    public void updatePayoffTable() {
        int remainingRounds = TOTAL_ROUNDS - round;

        // 努力の収益
        double effortReward = 6 + 0.8 * (1 - Math.pow(0.8, remainingRounds)) / (1 - 0.8);

        // 怠ける収益
        double lazyReward = 8 + 0.5 * 2;

        // 利得表の設定
        payoffCC[0] = effortReward + 2; // (C, C)
        payoffCC[1] = effortReward + 2;

        payoffCD[0] = effortReward;    // (C, D)
        payoffCD[1] = lazyReward;

        payoffDC[0] = lazyReward;      // (D, C)
        payoffDC[1] = effortReward;

        payoffDD[0] = lazyReward;      // (D, D)
        payoffDD[1] = lazyReward;
    }

}
