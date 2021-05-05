package com.vrktech.springboot.gitactivities.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vrktech.springboot.gitactivities.entities.WeeklyStatistics;

@Repository
public interface WeeklyCommitsRepository extends JpaRepository<WeeklyStatistics, Long> {

	@Query("SELECT ws FROM WeeklyStatistics  ws WHERE ws.repo.ownerName = ?1")
	List<WeeklyStatistics> findByUser(String repoName);

	@Query("SELECT ws FROM WeeklyStatistics  ws WHERE ws.repo.ownerName = ?1 and ws.repo.name = ?2")
	List<WeeklyStatistics> findByOwnerNameAndRepoName(String userName, String repoName);

}
