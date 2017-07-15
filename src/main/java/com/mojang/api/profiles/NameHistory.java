package com.mojang.api.profiles;

public class NameHistory {
	private String name;
	private long changedToAt;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getChangedToAt() {
		return changedToAt;
	}
	
	public void setChangedToAt(long changedToAt) {
		this.changedToAt = changedToAt;
	}
}
