package com.vrktech.springboot.gitactivities.transformer;

import static java.text.MessageFormat.format;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vrktech.springboot.gitactivities.dto.CommitStatistics;
import com.vrktech.springboot.gitactivities.dto.RepoStatistics;
import com.vrktech.springboot.gitactivities.dto.WeeklyDetails;
import com.vrktech.springboot.gitactivities.entities.WeeklyStatistics;
import com.vrktech.springboot.gitactivities.exception.ErrorCode;
import com.vrktech.springboot.gitactivities.exception.GitCustomException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GitActivityTransformer {

	private static final Logger logger = LoggerFactory.getLogger(GitActivityTransformer.class);


	public List<RepoStatistics> getRepositoryStatistics(List<WeeklyStatistics> weeklyStatisticList)
			throws GitCustomException {

		List<RepoStatistics> repositoryStatisticsList = new ArrayList<>();
		RepoStatistics repositoryStatistics = null;
		WeeklyDetails details = new WeeklyDetails();
		List<WeeklyDetails> list = null;
		Map<Object, List<WeeklyStatistics>> weeklyList = null;

		try {
			weeklyList = weeklyStatisticList.stream()
					.collect(Collectors.groupingBy(w -> w.getRepo().getRepoId()));

			for (Map.Entry<Object, List<WeeklyStatistics>> entry : weeklyList.entrySet()) {
				repositoryStatistics = new RepoStatistics();
				list = new ArrayList<>();
				repositoryStatistics.setRepoId((Integer) entry.getKey());
				String repoName = null;
				for (WeeklyStatistics weekInfo : entry.getValue()) {
					details = new WeeklyDetails();
					details.setWeekOf(weekInfo.getWeekOf().toString());
					details.setTotalCommits(weekInfo.getTotalCommits());
					repoName = weekInfo.getRepo().getName();
					list.add(details);
				}

				repositoryStatistics.setRepoName(repoName);
				repositoryStatistics.setWeeklyDetails(list);
				repositoryStatisticsList.add(repositoryStatistics);
			}
		} catch (Exception ex) {
			logger.error(format("Unable to transform weeklyStatisticList"), ex);
			GitCustomException gitCustomException = new GitCustomException(ErrorCode.GIT_400,
					new Object[] {});
			throw gitCustomException;
		}

		return repositoryStatisticsList;

	}

}
