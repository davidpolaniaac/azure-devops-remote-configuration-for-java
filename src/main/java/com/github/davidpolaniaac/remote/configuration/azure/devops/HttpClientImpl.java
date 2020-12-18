package com.github.davidpolaniaac.remote.configuration.azure.devops;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClientImpl implements HttpClient {
	
	private final RestTemplate restTemplate;
	private final HttpHeaders httpHeaders;
	
	@Autowired
	public HttpClientImpl(RestTemplate restTemplate, HttpHeaders httpHeaders) {
		this.restTemplate = restTemplate;
		this.httpHeaders = httpHeaders;
	}

	@Retryable(maxAttempts = 10, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 900000))
	public <T> ResponseEntity<T> restCall(URI url, Class<T> responseType, String authHeader) {
		try {
			httpHeaders.set(HttpHeaders.AUTHORIZATION, authHeader);
			httpHeaders.set(HttpHeaders.ACCEPT,  MediaType.APPLICATION_JSON.toString());
			return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), responseType);
			
		} catch (RestClientException exception) {
			throw exception;
		}
	}
	
	@Retryable(maxAttempts = 10, value = RuntimeException.class, backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 900000))
	public <T> ResponseEntity<T> restCall(URI url, Class<T> responseType, String username, String password) {
		try {
			httpHeaders.setBasicAuth(username, password);
			httpHeaders.set(HttpHeaders.ACCEPT,  MediaType.APPLICATION_JSON.toString());
			return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), responseType);
			
		} catch (RestClientException exception) {
			throw exception;
		}
	}
	
}
