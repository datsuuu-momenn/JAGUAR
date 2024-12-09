package com.example.bean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    private String playerAChoice = "-"; // プレイヤーAの選択
    private String playerBChoice = "-"; // プレイヤーBの選択 (デフォルトは努力)
    private int round = 1;             // 現在のラウンド
    private double playerAPayoff;      // プレイヤーAの利得
    private double playerBPayoff;      // プレイヤーBの利得
    private double totalAPayoff = 0;   // プレイヤーAの累計利得
    private double totalBPayoff = 0;   // プレイヤーBの累計利得

    private double[] payoffCC = {7, 7}; // 利得表：努力 - 努力
    private double[] payoffCD = {5, 10}; // 利得表：努力 - 怠ける
    private double[] payoffDC = {10, 5}; // 利得表：怠ける - 努力
    private double[] payoffDD = {5, 5}; // 利得表：怠ける - 怠ける

    private List<String> roundHistory = new ArrayList<>(); // 履歴の保存

    private static final double DELTA_REST = 0;   // 休息の割引率
    private static final double BASE_EFFORT = 5;    // 基礎努力収益
    private static final double BASE_LAZY = 10;      // 基礎怠け収益
    private static final double EFFORT_INCREMENT = 1.5; // 努力スキル増加
    private static final double REST_BONUS = 3;      // 短期ボーナス
    private static final double TEAM_BONUS = 2;      // チームボーナス
    private static final int TOTAL_ROUNDS = 100;      // 全ラウンド数
    private int totalEffortA = 0; // 玩家A的总努力次数
    private int totalEffortB = 0; // 玩家B的总努力次数

    @Getter
    @Setter
    private String gameTheoryExplanation; // 添加这个字段来存储博弈论解释

    @Getter
    @Setter
    private double[] previousPayoffCC = {0, 0}; // 前回の利得表：努力 - 努力
    private double[] previousPayoffCD = {0, 0}; // 前回の利得表：努力 - 怠ける
    private double[] previousPayoffDC = {0, 0}; // 前回の利得表：怠ける - 努力
    private double[] previousPayoffDD = {0, 0}; // 前回の利得表：怠ける - 怠ける

    /**
     * プレイヤーAの選択に基づき、プレイヤーBの戦略を調整。
     */
    public void play() {
        // 履歴に基づくプレイヤーBの選択調整
        int cooperateCount = (int) roundHistory.stream().filter(choice -> choice.startsWith("C")).count();
        if (cooperateCount > round / 2) {
            playerBChoice = "C"; // プレイヤーAが努力を多く選んだ場合、プレイヤーBも努力を選ぶ
        } else {
            playerBChoice = "D"; // プレイヤーAが怠けるを多く選んだ場合、プレイヤーBは怠けるを選ぶ
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


        // 利得表を更新する前に、現在のラウンドのナッシュ均衡を計算
        PayoffMatrix currentPayoffMatrix = new PayoffMatrix(2, 2);
        // 使用当前的利得表填充矩阵
        currentPayoffMatrix.setPayoff(0, 0, payoffCC[0], payoffCC[1]); // (C,C)
        currentPayoffMatrix.setPayoff(0, 1, payoffCD[0], payoffCD[1]); // (C,D)
        currentPayoffMatrix.setPayoff(1, 0, payoffDC[0], payoffDC[1]); // (D,C)
        currentPayoffMatrix.setPayoff(1, 1, payoffDD[0], payoffDD[1]); // (D,D)
        
        // ナッシュ均衡の説明を計算して更新
        provideGameTheoryExplanation(currentPayoffMatrix);

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
          // 現在の利得表を前回の利得表として保存
        previousPayoffCC = Arrays.copyOf(payoffCC, 2);
        previousPayoffCD = Arrays.copyOf(payoffCD, 2);
        previousPayoffDC = Arrays.copyOf(payoffDC, 2);
        previousPayoffDD = Arrays.copyOf(payoffDD, 2);

        // int playerAcountC = (int) roundHistory.stream().filter(choice -> choice.startsWith("C")).count();
        // int playerAcountD = (int) roundHistory.stream().filter(choice -> choice.startsWith("D")).count();

        // int playerBcountC = (int) roundHistory.stream().filter(choice -> choice.endsWith("C")).count();
        // int playerBcountD = (int) roundHistory.stream().filter(choice -> choice.endsWith("D")).count();


        DecimalFormat df = new DecimalFormat("#.##");
        
        // 基本報酬の設定
        double effortRewardA = BASE_EFFORT;
        double effortRewardB = BASE_EFFORT;

        // プレイヤーAが努力を選択した場合のスキル向上計算
        if (playerAChoice.equals("C")) {
            totalEffortA++;
            effortRewardA = BASE_EFFORT + EFFORT_INCREMENT * Math.log(1 + totalEffortA);
        }
        effortRewardA = Double.parseDouble(df.format(effortRewardA));

        // プレイヤーBが努力を選択した場合のスキル向上計算
        if (playerBChoice.equals("C")) {
            totalEffortB++;
            effortRewardB = BASE_EFFORT + EFFORT_INCREMENT * Math.log(1 + totalEffortB);
        }
        effortRewardB = Double.parseDouble(df.format(effortRewardB));
        
        // 怠ける場合の報酬計算
        double lazyReward = BASE_LAZY + DELTA_REST * REST_BONUS;
        lazyReward = Double.parseDouble(df.format(lazyReward));

        // 利得表の設定
        payoffCC[0] = effortRewardA + TEAM_BONUS;
        payoffCC[1] = effortRewardB + TEAM_BONUS;

        payoffCD[0] = effortRewardA;    // (C, D)
        payoffCD[1] = lazyReward;

        payoffDC[0] = lazyReward;      // (D, C)
        payoffDC[1] = effortRewardB;

        payoffDD[0] = lazyReward;      // (D, D)
        payoffDD[1] = lazyReward;
    }


    /**
     * 利得行列を表現するための内部クラス
     */
    static class PayoffMatrix {
        double[][][] matrix;

        public PayoffMatrix(int rows, int cols) {
            matrix = new double[rows][cols][2];
        }

        public void setPayoff(int row, int col, double payoffA, double payoffB) {
            matrix[row][col][0] = payoffA;
            matrix[row][col][1] = payoffB;
        }

        public double[] getPayoff(int row, int col) {
            return matrix[row][col];
        }
    }

    /**
     * 利得行列に基づいてナッシュ均衡を動的に判定し、ゲーム理論の説明を提供する。
     */
    private void provideGameTheoryExplanation(PayoffMatrix payoffMatrix) {
        // 変数の初期化
        int rows = payoffMatrix.matrix.length;
        int cols = payoffMatrix.matrix[0].length;
        boolean[][] bestResponsesA = new boolean[rows][cols];
        boolean[][] bestResponsesB = new boolean[rows][cols];
        boolean isNashEquilibrium = true;

        // プレイヤーAの最適反応を計算
        for (int j = 0; j < cols; j++) {
            double maxPayoffA = Double.NEGATIVE_INFINITY;
            
            // 各列での最大利得を探索
            for (int i = 0; i < rows; i++) {
                double payoffA = payoffMatrix.getPayoff(i, j)[0];
                if (payoffA > maxPayoffA) {
                    maxPayoffA = payoffA;
                }
            }
            
            // 最適反応を記録
            for (int i = 0; i < rows; i++) {
                if (payoffMatrix.getPayoff(i, j)[0] == maxPayoffA) {
                    bestResponsesA[i][j] = true;
                }
            }
        }

        // 判断玩家 B 的最佳反应
        for (int i = 0; i < rows; i++) {
            double maxPayoffB = Double.NEGATIVE_INFINITY;

            // 找到当前行中玩家 B 的最大收益
            for (int j = 0; j < cols; j++) {
                double payoffB = payoffMatrix.getPayoff(i, j)[1];
                if (payoffB > maxPayoffB) {
                    maxPayoffB = payoffB;
                }
            }

            // 标记最佳反应
            for (int j = 0; j < cols; j++) {
                if (payoffMatrix.getPayoff(i, j)[1] == maxPayoffB) {
                    bestResponsesB[i][j] = true;
                }
            }
        }

        // 現在の戦略の組み合わせがナッシュ均衡かどうかを判定
        int playerARow = playerAChoice.equals("C") ? 0 : 1;
        int playerBCol = playerBChoice.equals("C") ? 0 : 1;
        isNashEquilibrium = bestResponsesA[playerARow][playerBCol] && bestResponsesB[playerARow][playerBCol];

        // ナッシュ均衡判定後、詳細な説明を生成
        StringBuilder explanation = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.##");
        
        // 現在の戦略の組み合わせによる利得の説明を追加
        explanation.append("現在のラウンド：\n");
        explanation.append("プレイヤーAは").append(playerAChoice.equals("C") ? "努力" : "怠ける")
                  .append("、プレイヤーBは").append(playerBChoice.equals("C") ? "努力" : "怠ける")
                  .append("\n利得：A = ").append(df.format(playerAPayoff))
                  .append("、B = ").append(df.format(playerBPayoff)).append("\n\n");

        // ナッシュ均衡分析
        if (isNashEquilibrium) {
            explanation.append("【ナッシュ均衡分析】\n");
            explanation.append("★これはナッシュ均衡戦略の組み合わせです。\n");
            explanation.append("• 現在の状況では、両者とも一方的に戦略を変更する動機がありません\n");
            explanation.append("• この戦略の組み合わせは安定的です。どちらかが戦略を変更すると自身の利得が減少するためです\n\n");
        } else {
            explanation.append("【ナッシュ均衡分析】\n");
            explanation.append("★これはナッシュ均衡戦略の組み合わせではありません。\n");
            // より良い選択肢の分析
            double currentPayoffA = payoffMatrix.getPayoff(playerARow, playerBCol)[0];
            double currentPayoffB = payoffMatrix.getPayoff(playerARow, playerBCol)[1];
            
            // プレイヤーAのより良い選択肢を確認
            int otherChoice = playerARow == 0 ? 1 : 0;
            double alternativePayoffA = payoffMatrix.getPayoff(otherChoice, playerBCol)[0];
            if (alternativePayoffA > currentPayoffA) {
                explanation.append("• 相手が戦略を変更しない場合、プレイヤーAは")
                          .append(otherChoice == 0 ? "努力" : "怠ける")
                          .append("戦略に切り替えることで利得を向上できます\n");
            }
            
            // プレイヤーBのより良い選択肢を確認
            otherChoice = playerBCol == 0 ? 1 : 0;
            double alternativePayoffB = payoffMatrix.getPayoff(playerARow, otherChoice)[1];
            if (alternativePayoffB > currentPayoffB) {
                explanation.append("• 相手が戦略を変更しない場合、プレイヤーBは")
                          .append(otherChoice == 0 ? "努力" : "怠ける")
                          .append("戦略に切り替えることで利得を向上できます\n");
            }
            explanation.append("\n");
        }

        // パレート最適分析を追加
        boolean isParetoOptimal = isParetoOptimal(payoffMatrix, playerARow, playerBCol);

        explanation.append("【パレート効率性分析】\n");
        if (isParetoOptimal) {
            explanation.append("★現在の戦略の組み合わせはパレート最適です。\n");
            explanation.append("• 他のプレイヤーの利得を減少させることなく、一方の利得を増加させる戦略の組み合わせは存在しません\n");
            explanation.append("• これは現在の結果が効率性の観点から最適であり、戦略を変更しても双方の利得を改善できないことを意味します\n");
        } else {
            explanation.append("★現在の戦略の組み合わせはパレート最適ではありません。\n");
            explanation.append("• 他の戦略の組み合わせにより、少なくとも一方の利得を増加させつつ、他方の利得を減少させることなく改善できます\n");
            explanation.append("• これは効率性の観点からまだ改善の余地があることを示しています\n");
            
            // パレート改善を探す
            for (int i = 0; i < payoffMatrix.matrix.length; i++) {
                for (int j = 0; j < payoffMatrix.matrix[0].length; j++) {
                    double alternativeAPayoff = payoffMatrix.getPayoff(i, j)[0];
                    double alternativeBPayoff = payoffMatrix.getPayoff(i, j)[1];
                    
                    if ((alternativeAPayoff > playerAPayoff && alternativeBPayoff >= playerBPayoff) ||
                        (alternativeAPayoff >= playerAPayoff && alternativeBPayoff > playerBPayoff)) {
                        explanation.append("• 例えば、")
                                  .append(i == 0 ? "努力" : "怠ける")
                                  .append("-")
                                  .append(j == 0 ? "努力" : "怠ける")
                                  .append("の戦略の組み合わせを選択すると、両者の利得は(A, B) = (")
                                  .append(df.format(alternativeAPayoff))
                                  .append(", ")
                                  .append(df.format(alternativeBPayoff))
                                  .append(")に改善できます\n");
                        break;
                    }
                }
            }
        }

        // 囚人のジレンマ分析
        boolean isCC = playerAChoice.equals("C") && playerBChoice.equals("C");
        boolean isPareto = isParetoOptimal(payoffMatrix, playerARow, playerBCol);
        boolean isNash = isNashEquilibrium;

        if (isCC && isPareto && !isNash) {
            explanation.append("\n【囚人のジレンマ分析】\n");
            explanation.append("★現在の戦略の組み合わせは典型的な囚人のジレンマの特徴を示しています：\n");
            explanation.append("• 両者が努力を選択する(努力,努力)はパレート最適であり、最大の総合利得をもたらします\n");
            explanation.append("• しかし、これはナッシュ均衡ではありません。各プレイヤーには怠ける戦略に一方的に変更する動機があるためです\n");
            explanation.append("• これは個人の合理性と集団の合理性の対立を反映しています：\n");
            explanation.append("  - 個人の観点からは、相手がどちらを選んでも怠けるほうが高い利得を得られます\n");
            explanation.append("  - しかし、両者がそう考えて怠けると、最終的に双方が損失を被る状況に陥ります\n");
            explanation.append("• このジレンマは現実でもよく見られます。例えば：\n");
            explanation.append("  - 軍拡競争：各国は軍事的優位を保ちたいが、過度な軍拡は共通の損失をもたらす\n");
            explanation.append("  - 環境保護：企業は環境保護コストを抑えたいが、共に汚染すると環境悪化を招く\n");
            explanation.append("  - チーム協力：メンバーは手を抜きたいが、全員がそうすればプロジェクトは失敗する\n");
        } else if (!isCC && !isPareto && isNash) {
            explanation.append("\n【囚人のジレンマ分析】\n");
            explanation.append("★現在の怠ける戦略の組み合わせは囚人のジレンマの悲劇的な結末を反映しています：\n");
            explanation.append("• 両者が怠けるのはナッシュ均衡ですが、次善の結果をもたらします\n");
            explanation.append("• 効果的な努力メカニズムを確立により\n");
            explanation.append("  このジレンマを打破し、パレート改善を実現できる可能性があります。例：\n");
            explanation.append("  - 繰り返しゲームによる信頼構築\n");
            explanation.append("  - 罰則メカニズムの設定\n");
            explanation.append("  - 拘束力のある約束の確立\n");

        } else if (playerAChoice.equals("C") != playerBChoice.equals("C")) {
            explanation.append("\n【囚人のジレンマ分析】\n");
            explanation.append("★現在は非対称な戦略の組み合わせです：\n");
            explanation.append("• 一方が努力を選択し、もう一方が怠けるを選択しています\n");
            explanation.append("• この不均衡な状態は通常一時的なものです\n");
            explanation.append("• 努力する側は搾取されるリスクに直面し、次のラウンドで怠けるに転じる可能性があります\n");
            explanation.append("• この動態は信頼構築の脆弱性と努力維持の困難さを反映しています\n");
        }

        gameTheoryExplanation = explanation.toString();
    }

    /**
     * パレート最適性の判定
     * パレート最適とは、他のプレイヤーの利得を減少させることなく、
     * 少なくとも1人のプレイヤーの利得を増加させる戦略の組み合わせが存在しない状態。
     */
    private boolean isParetoOptimal(PayoffMatrix payoffMatrix, int currentRow, int currentCol) {
        double currentAPayoff = payoffMatrix.getPayoff(currentRow, currentCol)[0];
        double currentBPayoff = payoffMatrix.getPayoff(currentRow, currentCol)[1];

        int rows = payoffMatrix.matrix.length;
        int cols = payoffMatrix.matrix[0].length;

        // すべての戦略の組み合わせを検証し、パレート改善が可能か確認
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double alternativeAPayoff = payoffMatrix.getPayoff(i, j)[0];
                double alternativeBPayoff = payoffMatrix.getPayoff(i, j)[1];

                // パレート改善の条件を確認
                if ((alternativeAPayoff > currentAPayoff && alternativeBPayoff >= currentBPayoff) ||
                    (alternativeAPayoff >= currentAPayoff && alternativeBPayoff > currentBPayoff)) {
                    return false; // パレート改善が可能な場合、パレート最適ではない
                }
            }
        }
        return true; // パレート改善が不可能な場合、パレート最適である
    }

}
