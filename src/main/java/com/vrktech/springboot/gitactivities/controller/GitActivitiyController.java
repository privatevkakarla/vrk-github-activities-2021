package com.vrktech.springboot.gitactivities.controller;

import java.util.List;

import com.vrktech.springboot.gitactivities.constants.GitConstants;
import com.vrktech.springboot.gitactivities.dto.CommitStatistics;
import com.vrktech.springboot.gitactivities.dto.GitApiRequest;
import com.vrktech.springboot.gitactivities.dto.RepoStatistics;
import com.vrktech.springboot.gitactivities.exception.ErrorCode;
import com.vrktech.springboot.gitactivities.exception.ErrorMessage;
import com.vrktech.springboot.gitactivities.exception.ErrorMessageGenerator;
import com.vrktech.springboot.gitactivities.exception.GitCustomException;
import com.vrktech.springboot.gitactivities.service.IGitActivityService;
import com.vrktech.springboot.gitactivities.utils.GitRequestVerificationUtil;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1/github/", produces = MediaType.APPLICATION_JSON_VALUE)
public class GitActivitiyController {

	@Autowired
	IGitActivityService iGitService;

	@Autowired
	ErrorMessageGenerator errorMessageGenerator;

	@Autowired
	GitRequestVerificationUtil validationUtils;

	@ApiOperation(value = "Creates repos with the statistics of the "
			+ "given user related repositories", notes = "If provided only the username retrieves all the repositories of the given user and creates repos "
					+ "otherwise if we provide both username and repository name it will get only specific repository statistics of the given user and creates repos", response = String.class)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successfully retrieved  repos", response = Object.class) })
	@RequestMapping(value = "/users/repos/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> populateDataStore(@RequestBody GitApiRequest apiRequest) 
			throws Exception {

		ResponseEntity<Object> responseEntity = null;
		String userName = null;

		try {

			userName = apiRequest.getUserName();

			if (Strings.isBlank(userName)) {
				throw new GitCustomException(ErrorCode.GIT_001, new Object[] {});
			}

			iGitService.poulateRepositoryStatistics(userName);
			responseEntity = new ResponseEntity<>(GitConstants.GIT_REPOS_SUCCESS, HttpStatus.OK);

		} catch (GitCustomException gitCustomException) {

			ErrorMessage errorMessage = errorMessageGenerator.generateError(gitCustomException);
			responseEntity = new ResponseEntity<>(errorMessage,
					ErrorCode.httpStatusCode((ErrorCode) gitCustomException.getErrorCode()));

		}

		return responseEntity;

	}

}
