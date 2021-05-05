package com.vrktech.springboot.gitactivities.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WeeklyStatistics implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "week_id")
	private Long weekId;

	@Column(name = "week_of")
	private Date weekOf;

	@Column(name = "total_commits")
	private int totalCommits;

	@ManyToOne(optional = false)
	@JoinColumn(name = "repoId", referencedColumnName = "repo_id", insertable = false, updatable = false)
	private RepoDetails repo;


	public Long getWeekId() {
		return weekId;
	}

	public void setWeekId(Long weekId) {
		this.weekId = weekId;
	}

	public Date getWeekOf() {
		return weekOf;
	}

	public void setWeekOf(Date weekOf) {
		this.weekOf = weekOf;
	}

	public int getTotalCommits() {
		return totalCommits;
	}

	public void setTotalCommits(int totalCommits) {
		this.totalCommits = totalCommits;
	}

	public RepoDetails getRepo() {
		return repo;
	}

	public void setRepo(RepoDetails repo) {
		this.repo = repo;
	}


}
