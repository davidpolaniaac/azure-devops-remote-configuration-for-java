package com.github.davidpolaniaac.remote.configuration.azure.devops;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.github.davidpolaniaac.remote.configuration.azure.devops.HttpClientImpl;

@RunWith(MockitoJUnitRunner.class)
public class HttpClientImplTest {
	@Mock
	private HttpHeaders httpHeaders;

	@Mock
	private RestTemplate restTemplate;
	@InjectMocks
	private HttpClientImpl httpClientImpl;

	@Test
	public void testHttpClientAuht() throws Exception {
		//Arrange
		URI url = new URI("http://example.com");
		String authHeader = null;
		ResponseEntity<String> value=new ResponseEntity<>(HttpStatus.OK);
		when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(String.class))).thenReturn(value);
		//Act
		ResponseEntity<String> reponse = httpClientImpl.restCall(url, String.class, authHeader);
		//Assert
		assertEquals(HttpStatus.OK, reponse.getStatusCode());
	}
	
	@Test(expected= RestClientException.class) 
	public void testHttpClientAuhtException() throws Exception {
		//Arrange
		URI url = new URI("http://example.com");
		String authHeader = null;
		doThrow(new RestClientException("test")).when(restTemplate).exchange(eq(url), eq(HttpMethod.GET), any(), eq(String.class));
		//Act
		httpClientImpl.restCall(url, String.class, authHeader);
	}
	
	@Test
	public void testHttpClientUserPassword() throws Exception {
		//Arrange
		URI url = new URI("http://example.com");
		ResponseEntity<String> value=new ResponseEntity<>(HttpStatus.OK);
		when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(), eq(String.class))).thenReturn(value);
		//Act
		ResponseEntity<String> reponse = httpClientImpl.restCall(url, String.class, "", "");
		//Assert
		assertEquals(HttpStatus.OK, reponse.getStatusCode());
	}
	
	@Test(expected= RestClientException.class) 
	public void testHttpClientUserPasswordException() throws Exception {
		//Arrange
		URI url = new URI("http://example.com");
		doThrow(new RestClientException("test")).when(restTemplate).exchange(eq(url), eq(HttpMethod.GET), any(), eq(String.class));
		//Act
		httpClientImpl.restCall(url, String.class, "", "");
	}

}
