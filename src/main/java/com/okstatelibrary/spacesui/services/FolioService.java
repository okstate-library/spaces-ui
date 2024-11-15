package com.okstatelibrary.spacesui.services;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okstatelibrary.spacesui.folio.models.Root;
import com.okstatelibrary.spacesui.util.SystemProperties;

@Service
public class FolioService extends FolioServiceToken {

	private RestTemplate restTemplate = new RestTemplate();

	ObjectMapper mapper = new ObjectMapper();

	private HttpHeaders getHttpHeaders() {

		HttpHeaders headers = new HttpHeaders();

		headers.add("x-okapi-tenant", SystemProperties.FolioTenant);

		headers.add("x-okapi-token", this.getToken());

		return headers;

	}

	private HttpEntity<String> getHttpRequest() {

		return new HttpEntity<String>(getHttpHeaders());
	}

	public boolean isUserExists(String externalSystemId)
			throws JsonParseException, JsonMappingException, RestClientException, IOException {

		try {

			String url = SystemProperties.FolioURL + "users?query=(externalSystemId=A" + externalSystemId
					+ " and active=true)";

			System.out.println("url " + url);

			ResponseEntity<Root> response = restTemplate.exchange(url, HttpMethod.GET, getHttpRequest(), Root.class);

			if (response.getBody().totalRecords > 0) {

				return true;

			} else {

				return false;
			}

		} catch (Exception e) {

			// TODO: handle exception
			e.getMessage();
			e.printStackTrace();
			return false;
		}
	}

}
