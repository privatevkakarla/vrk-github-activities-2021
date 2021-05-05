package com.vrktech.springboot.gitactivities.serviceImpl;

import static java.text.MessageFormat.format;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrktech.springboot.gitactivities.dto.CommitStatistics;
import com.vrktech.springboot.gitactivities.dto.RepoStatistics;
import com.vrktech.springboot.gitactivities.entities.WeeklyStatistics;
import com.vrktech.springboot.gitactivities.exception.ErrorCode;
import com.vrktech.springboot.gitactivities.exception.GitCustomException;
import com.vrktech.springboot.gitactivities.external.GitAPIService;
import com.vrktech.springboot.gitactivities.repo.WeeklyCommitsRepository;
import com.vrktech.springboot.gitactivities.service.IGitActivityService;
import com.vrktech.springboot.gitactivities.transformer.GitActivityTransformer;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class GitActivityServiceImpl implements IGitActivityService {

	private static final Logger logger = LoggerFactory.getLogger(GitActivityServiceImpl.class);

	@Autowired
	GitAPIService gitAPIService;

	@Autowired
	WeeklyCommitsRepository weeklyRepository;

	@Autowired
	GitActivityTransformer gitActivityTransformer;

	@Override
	public void poulateRepositoryStatistics(String userName)
			throws GitCustomException {

		gitAPIService.processRespositoryInfo(userName);

	}

}
