package com.erickmob.telegram.slavebot.domain;

public enum Command {
	clear("/clear", "clear"),
	help("/help", "help"), 
	uno("/uno", "Uno"),
	detetive("/detetive", "Detetive"),
	ludo("/ludo", "Ludo"),
	vida("/vida", "Jogo da Vida"),
	placar("/placar", "Placar");

	private String action;
	private String actionDone;
  
	Command(final String action) {
		this.action = action;
	}
  
	Command(final String action, final String actionDone) {
		this(action);
		this.actionDone = actionDone;
	}
  
    public static Command fromString(String text) {
        for (Command b : Command.values()) {
            if (b.action.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public String getActionDone(){
		return this.actionDone;
	}

}
