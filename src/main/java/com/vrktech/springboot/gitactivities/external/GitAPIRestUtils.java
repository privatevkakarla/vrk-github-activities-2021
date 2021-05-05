package com.vrktech.springboot.gitactivities.external;

import static java.text.MessageFormat.format;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.vrktech.springboot.gitactivities.config.GitApiServiceConfig;
import com.vrktech.springboot.gitactivities.exception.ErrorCode;
import com.vrktech.springboot.gitactivities.exception.GitCustomException;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GitAPIRestUtils {

	private static final Logger logger = LoggerFactory.getLogger(GitAPIRestUtils.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	GitApiServiceConfig gitApiServiceConfig;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<LinkedHashMap> invokeGitApi(String url) throws GitCustomException {

		ResponseEntity<Object> gitResponse = null;
		List<LinkedHashMap> responseList = null;
		HttpEntity<Object> httpEntity = null;

		try {

			httpEntity = new HttpEntity<Object>(getHeaders());
			gitResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Object.class);

			if (gitResponse.getStatusCode() == HttpStatus.OK) {

				responseList = new ArrayList<>();

				if (gitResponse.getBody() instanceof List<?>) {
					responseList = (List<LinkedHashMap>) gitResponse.getBody();
				} else {
					responseList.add((LinkedHashMap) gitResponse.getBody());
				}

				logger.info("Successfully collected the details from the git api");

			}

		} catch (HttpClientErrorException hCEEx) {

			if (hCEEx.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
				logger.error(format("The server has not found anything matching the Request-URI {0}", url));
				throw new GitCustomException(ErrorCode.GIT_404, new Object[] { url });

			}

			else if (hCEEx.getRawStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
				logger.error(format("UnAuthorozed access to the  Request-URI {0}", url));
				throw new GitCustomException(ErrorCode.GIT_404, new Object[] { url });

			}
			logger.error(format(
					"Problem occured while collecting the respositiry information and the error is {0}",
					hCEEx.getMessage()));
			throw new GitCustomException(ErrorCode.GIT_405, new Object[] { hCEEx.getMessage() });
		}

		catch (Exception ex) {
			logger.error(
					"Problem occured while collecting the respositiry information and the error is {}", ex);
			throw new GitCustomException(ErrorCode.GIT_451, new Object[] { ex });
		}

		return responseList;

	}

	private HttpHeaders getHeaders() {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;

	}

	public String getGitApiURL(String ownerName,String repoName)
			throws GitCustomException {

		UriComponentsBuilder uriBuilder = null;
		try {

			if (!Strings.isBlank(repoName) && !Strings.isBlank(ownerName)) {
				uriBuilder = UriComponentsBuilder
						.fromUriString(format(gitApiServiceConfig.getGitRepoStats(), ownerName, repoName));
			
				return uriBuilder.build().encode().toUriString();
			} 
			                          
			if (!Strings.isBlank(ownerName)) {
				uriBuilder = UriComponentsBuilder
						.fromUriString(format(gitApiServiceConfig.getGitRepoURL(), ownerName));
				return uriBuilder.build().encode().toUriString();
			} 
			

		} catch (Exception ex) {
			logger.error("Problem occured while building the git repo uri");
			throw new GitCustomException(ErrorCode.GIT_004, new Object[] {});
		}

		return null;

	}

}
