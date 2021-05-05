package com.vrktech.springboot.gitactivities.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class RepoDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "repo_id")
	private Integer repoId;

	@Column(name = "repo_name")
	private String name;

	@Column(name = "created_at")
	private String createdAt;

	@Column(name = "updated_at")
	private String updatedAt;

	@Column(name = "repo_owner")
	private String ownerName;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "repoId", referencedColumnName = "repo_id", nullable = false)
	public Set<WeeklyStatistics> weeklyStatistics;

	public RepoDetails() {
		super();

	}

	public RepoDetails(Integer repoId, String name, String createdAt, String updatedAt,
			String ownerName) {
		super();
		this.repoId = repoId;
		this.name = name;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.ownerName = ownerName;
	}

	public Integer getRepoId() {
		return repoId;
	}

	public void setRepoId(Integer repoId) {
		this.repoId = repoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Set<WeeklyStatistics> getWeeklyStatistics() {
		return weeklyStatistics;
	}

	public void setWeeklyStatistics(Set<WeeklyStatistics> weeklyStatistics) {
		this.weeklyStatistics = weeklyStatistics;
	}

}
