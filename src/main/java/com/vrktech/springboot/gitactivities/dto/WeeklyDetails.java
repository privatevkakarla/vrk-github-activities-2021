package com.vrktech.springboot.gitactivities.dto;

public class WeeklyDetails {

	private String weekOf;
	private int totalCommits;

	public String getWeekOf() {
		return weekOf;
	}

	public void setWeekOf(String weekOf) {
		this.weekOf = weekOf;
	}

	public int getTotalCommits() {
		return totalCommits;
	}

	public void setTotalCommits(int totalCommits) {
		this.totalCommits = totalCommits;
	}

}
