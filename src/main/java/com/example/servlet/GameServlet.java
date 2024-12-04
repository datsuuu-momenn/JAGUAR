package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/game/play")
public class GameServlet extends HttpServlet {

    private static final double deltaSkill = 0.8;   // 技能提升的折现因子
    private static final double deltaRest = 0.5;   // 休息的折现因子
    private static final double baseEffortReward = 6; // 基础努力收益
    private static final double baseLazyReward = 8;   // 基础偷懒收益
    private static final double deltaV = 1;          // 努力技能提升的增量
    private static final double restBonus = 2;       // 偷懒的好心情增量
    private static final double teamBonus = 2;       // 团队合作附加收益
    private static final int totalRounds = 10;       // 总回合数

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String playerAChoice = request.getParameter("playerAChoice");
        int round = Integer.parseInt(request.getParameter("round"));

        // 剩余回合数
        int remainingRounds = totalRounds - round;

        // 计算努力和偷懒的收益
        double effortReward = baseEffortReward +
                deltaSkill * (deltaV * (1 - Math.pow(deltaSkill, remainingRounds)) / (1 - deltaSkill));
        double lazyReward = baseLazyReward + deltaRest * restBonus;

        // 玩家 B 的策略选择
        String playerBChoice = "C".equals(playerAChoice) ? "C" : "D";

        // 计算利得
        double playerAPayoff, playerBPayoff;
        if ("C".equals(playerAChoice) && "C".equals(playerBChoice)) {
            playerAPayoff = effortReward + teamBonus;
            playerBPayoff = effortReward + teamBonus;
        } else if ("C".equals(playerAChoice) && "D".equals(playerBChoice)) {
            playerAPayoff = effortReward;
            playerBPayoff = lazyReward;
        } else if ("D".equals(playerAChoice) && "C".equals(playerBChoice)) {
            playerAPayoff = lazyReward;
            playerBPayoff = effortReward;
        } else {
            playerAPayoff = lazyReward;
            playerBPayoff = lazyReward;
        }

        // 返回 JSON 格式的结果
        out.print("{");
        out.printf("\"playerAChoice\":\"%s\",", playerAChoice);
        out.printf("\"playerBChoice\":\"%s\",", playerBChoice);
        out.printf("\"playerAPayoff\":\"%.2f\",", playerAPayoff);
        out.printf("\"playerBPayoff\":\"%.2f\"", playerBPayoff);
        out.print("}");
        out.flush();
    }
}

