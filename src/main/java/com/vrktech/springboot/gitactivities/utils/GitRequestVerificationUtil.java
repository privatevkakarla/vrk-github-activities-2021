package com.vrktech.springboot.gitactivities.utils;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.vrktech.springboot.gitactivities.exception.ErrorCode;
import com.vrktech.springboot.gitactivities.exception.GitCustomException;

@Component
public class GitRequestVerificationUtil {

	public void verifyRepositoryRequest(String userName, String repoName) throws GitCustomException {

	}

	public void verifyStatisticsRequest(String ownerName, String serviceName)
			throws GitCustomException {

		if (serviceName.equalsIgnoreCase("undefined")) {
			serviceName = null;
		}

		if (ownerName.equalsIgnoreCase("undefined") && serviceName.equalsIgnoreCase("undefined")
				|| Strings.isBlank(ownerName) || Strings.isBlank(serviceName)) {
			throw new GitCustomException(ErrorCode.GIT_002, new Object[] {});
		}

	}

}
