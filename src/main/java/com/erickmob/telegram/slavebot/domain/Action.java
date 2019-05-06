package com.erickmob.telegram.slavebot.domain;

public enum Action {
	help("/help", "help"), 
	bebi("/bebi", "Bebeu"), 
	caguei("/caguei", "Cagou");

	private String action;
	private String actionDone;
  
	Action(final String action) {
		this.action = action;
	}
  
	Action(final String action, final String actionDone) {
		this(action);
		this.actionDone = actionDone;
	}
  
    public static Action fromString(String text) {
        for (Action b : Action.values()) {
            if (b.action.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
