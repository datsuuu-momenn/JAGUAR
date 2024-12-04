package com.example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class GameHistory {
    private int round;
    private String playerAChoice;
    private String playerBChoice;
    private int playerAPayoff;
    private int playerBPayoff;
} 