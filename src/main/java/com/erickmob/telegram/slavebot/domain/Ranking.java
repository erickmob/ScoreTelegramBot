package com.erickmob.telegram.slavebot.domain;

public class Ranking {

    private Command command;
    private Integer userId;
    private Long totalWins;

    public Ranking(Command command, Integer userId, Long totalWins) {
        this.command = command;
        this.userId = userId;
        this.totalWins = totalWins;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(Long totalWins) {
        this.totalWins = totalWins;
    }
}
