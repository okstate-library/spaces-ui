package com.okstatelibrary.spacesui.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.okstatelibrary.spacesui.folio.models.User;
import com.okstatelibrary.spacesui.util.DateTimeUtil;
import com.okstatelibrary.spacesui.util.SystemProperties;

public class FolioServiceToken {

	private RestTemplate restTemplate = new RestTemplate();

	private static long currentTimeStap;

	private static long expireTimeStap;

	public static String authToken;

	public FolioServiceToken() {
		getToken();
	}

	private HttpHeaders getHttpHeaders() {

		HttpHeaders headers = new HttpHeaders();

		headers.add("x-okapi-tenant", SystemProperties.FolioTenant);

		return headers;

	}

	public String getToken() {

		int loopCount = 1;

		boolean success = false;

		while (!success) {

			User user = new User();

			user.username = SystemProperties.FolioUsername;

			user.password = SystemProperties.FolioPassword;

			HttpEntity<?> request = new HttpEntity<Object>(user, getHttpHeaders());

			try {

				loopCount++;

				System.out.println("loopCount" + loopCount);

				if (authToken == null || DateTimeUtil.getCurretTimeStamp() > expireTimeStap) {

					String url = SystemProperties.FolioURL + "authn/login-with-expiry";

					ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request,
							String.class);

					String[] vavlues = responseEntity.getHeaders().get("Set-Cookie").toString().split(";");

					for (String value : vavlues) {

						if (value.contains("[folioAccessToken=")) {

							String token = value.split("=")[1];

							authToken = token;

							currentTimeStap = DateTimeUtil.getCurretTimeStamp();

							expireTimeStap = currentTimeStap + 600;

							success = true; // If no exception, mark success

							return authToken;
						}
					}

				}

				return authToken;

			} catch (Exception e) {

				System.err.println("Error occurred: " + e.getMessage());

				e.getMessage();
				e.printStackTrace();

				return null;
			}

		}

		return null;

	}

}
