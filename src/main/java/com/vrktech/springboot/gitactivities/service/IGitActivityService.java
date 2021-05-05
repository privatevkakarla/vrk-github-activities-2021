package com.vrktech.springboot.gitactivities.service;

import com.vrktech.springboot.gitactivities.exception.GitCustomException;

public interface IGitActivityService {

	public void poulateRepositoryStatistics(String userName)
			throws GitCustomException;


}
