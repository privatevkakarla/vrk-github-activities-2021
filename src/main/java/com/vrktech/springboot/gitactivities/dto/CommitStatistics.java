package com.vrktech.springboot.gitactivities.dto;

public class CommitStatistics {

	private Long id;
	private String day;
	private Integer totalCommits;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Integer getTotalCommits() {
		return totalCommits;
	}

	public void setTotalCommits(Integer totalCommits) {
		this.totalCommits = totalCommits;
	}

}
