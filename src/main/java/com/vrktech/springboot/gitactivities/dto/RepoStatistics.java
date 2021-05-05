package com.vrktech.springboot.gitactivities.dto;

import java.util.List;

public class RepoStatistics {

	private Integer repoId;

	private String repoName;

	private List<WeeklyDetails> weeklyDetails;

	public Integer getRepoId() {
		return repoId;
	}

	public void setRepoId(Integer repoId) {
		this.repoId = repoId;
	}

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	public List<WeeklyDetails> getWeeklyDetails() {
		return weeklyDetails;
	}

	public void setWeeklyDetails(List<WeeklyDetails> weeklyDetails) {
		this.weeklyDetails = weeklyDetails;
	}

}
