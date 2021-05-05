package com.vrktech.springboot.gitactivities.dto;

public class GitApiRequest {

	private String userName;

	private String repoName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

}
