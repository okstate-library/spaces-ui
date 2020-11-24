package com.okstatelibrary.spacesui.services;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okstatelibrary.spacesui.models.AccessToken;

/**
 * @author Damith
 *
 */
@Service
public class AccessTokenService {

	/// Rest Template
	private RestTemplate restTemplate = new RestTemplate();

	/// Object Mapper class.
	ObjectMapper mapper = new ObjectMapper();

	///
	/// Returns the access token for validation purposes.
	///
	public AccessToken getAccessToken(String url, String clientID, String clientSecret)
			throws RestClientException, JSONException, JsonParseException, JsonMappingException, IOException {

		try {

			MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
			parametersMap.set("client_id", clientID);
			parametersMap.set("client_secret", clientSecret);
			parametersMap.set("grant_type", "client_credentials");
			AccessToken accesstoken = mapper.readValue(restTemplate.postForObject(url, parametersMap, String.class),
					new TypeReference<AccessToken>() {
					});
			return accesstoken;
		} catch (Exception e) {
			return null;
		}

	}

}
