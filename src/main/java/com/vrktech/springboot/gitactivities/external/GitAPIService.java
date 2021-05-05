package com.vrktech.springboot.gitactivities.external;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrktech.springboot.gitactivities.entities.RepoDetails;
import com.vrktech.springboot.gitactivities.entities.WeeklyStatistics;
import com.vrktech.springboot.gitactivities.exception.ErrorCode;
import com.vrktech.springboot.gitactivities.exception.GitCustomException;
import com.vrktech.springboot.gitactivities.repo.GITReposRepository;
import com.vrktech.springboot.gitactivities.utils.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GitAPIService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	DateUtils dateConvertionUtility;

	@Autowired
	GITReposRepository reposRepository;

	@Autowired
	GitAPIRestUtils gitApiUtils;

	private static final Logger logger = LoggerFactory.getLogger(GitAPIService.class);

	public void processRespositoryInfo(String userName)
			throws GitCustomException {

		List<LinkedHashMap> responseDetails = null;
		Set<WeeklyStatistics> weeklyDetails = null;
		String gitUrl = null;
		RepoDetails repoDetails = null;
		try {

			gitUrl = gitApiUtils.getGitApiURL(userName, null);
			responseDetails = gitApiUtils.invokeGitApi(gitUrl);

			if (null != responseDetails) {

				for (LinkedHashMap repo : responseDetails) {
					repoDetails = populateRepoDetails(repo);
					repoDetails.setOwnerName(userName);
					weeklyDetails = populateCommitsByRepository((String) repo.get("name"), userName);
					
					if(weeklyDetails.size()>0) {
						repoDetails.setWeeklyStatistics(weeklyDetails);
					}
					
					reposRepository.save(repoDetails);
				}
			}

		}

		catch (Exception e) {
			logger.error(
					"Problem occured while collecting the respositiry information and the error is {}", e);
			throw new GitCustomException(ErrorCode.GIT_451, new Object[] { e });
		}

	}

	@SuppressWarnings("unchecked")
	public Set<WeeklyStatistics> populateCommitsByRepository(String repoName, String ownerName)
			throws GitCustomException {

		ResponseEntity<Object> rateResponse = null;
		List<LinkedHashMap> repoResponseList = null;
		WeeklyStatistics weeklyStatistics = null;
		Set<WeeklyStatistics> weeklyStatisticsList = new HashSet<>();

		try {
			rateResponse = restTemplate.getForEntity(gitApiUtils.getGitApiURL(ownerName, repoName),
					Object.class);
			
			
			if (rateResponse.getStatusCode() == HttpStatus.OK) {

				repoResponseList = new ArrayList<>();

				if (rateResponse.getBody() instanceof List<?>) {
					repoResponseList = (List<LinkedHashMap>) rateResponse.getBody();
				} else {
					repoResponseList.add((LinkedHashMap) rateResponse.getBody());
				}
			
	             if(repoResponseList != null) {
	            	 for (LinkedHashMap obj : repoResponseList) {
	     				weeklyStatistics = new WeeklyStatistics();
	     				LinkedHashMap repoInfo = (LinkedHashMap) obj;
	     				if (repoInfo != null) {

	     					Integer total = (Integer) repoInfo.get("total");
	     					Integer week = (Integer) repoInfo.get("week");
	     					List<Integer> days = (ArrayList<Integer>) repoInfo.get("days");

	     					Date weekOf = dateConvertionUtility.getWeekDateFromMilliSec(week);

	     					weeklyStatistics.setTotalCommits(total);
	     					weeklyStatistics.setWeekOf(weekOf);
	     					weeklyStatisticsList.add(weeklyStatistics);

	     				}

	     			}
	             }
			}
			
		} catch (Exception e) {
			logger.error(
					"Problem occured while collecting the respositiry information and the error is {}", e);
			throw new GitCustomException(ErrorCode.GIT_451, new Object[] { e });
		}

		return weeklyStatisticsList;
	}

	@SuppressWarnings("rawtypes")
	private RepoDetails populateRepoDetails(LinkedHashMap repoInfo) {

		RepoDetails repoDetails = new RepoDetails();

		repoDetails.setRepoId((Integer) repoInfo.get("id"));
		repoDetails.setName((String) repoInfo.get("name"));
		repoDetails.setCreatedAt((String) repoInfo.get("created_at"));
		repoDetails.setUpdatedAt((String) repoInfo.get("updated_at"));
		return repoDetails;

	}

}
