package com.jnrcorp.gtfs.load;

public class ImportHeaderMatchInfo {

	private boolean foundMatch;
	private String cause;

	public ImportHeaderMatchInfo(boolean startingMatchExists) {
		super();
		this.foundMatch = startingMatchExists;
	}

	public void setError(String cause, boolean matchExists) {
		this.foundMatch = matchExists;
		this.cause = cause;
	}

	public String getCause() {
		return cause;
	}

	public boolean isFoundMatch() {
		return foundMatch;
	}

}
