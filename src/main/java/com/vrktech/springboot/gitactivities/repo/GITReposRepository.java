package com.vrktech.springboot.gitactivities.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vrktech.springboot.gitactivities.entities.RepoDetails;

@Repository
public interface GITReposRepository extends JpaRepository<RepoDetails, Long> {

	List<RepoDetails> findByOwnerName(String ownerName);

	List<RepoDetails> findByOwnerNameAndName(String ownerName, String repoName);

}
