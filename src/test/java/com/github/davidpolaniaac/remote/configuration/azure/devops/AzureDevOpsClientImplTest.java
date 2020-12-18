package com.github.davidpolaniaac.remote.configuration.azure.devops;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.github.davidpolaniaac.remote.configuration.azure.devops.AzureDevOpsClientImpl;
import com.github.davidpolaniaac.remote.configuration.azure.devops.AzureDevOpsFactoryUrl;
import com.github.davidpolaniaac.remote.configuration.azure.devops.HttpClient;
import com.github.davidpolaniaac.remote.configuration.azure.devops.RemoteConfigurationProperties;
import com.github.davidpolaniaac.remote.configuration.azure.devops.exception.RemoteConfigurationException;
import com.github.davidpolaniaac.remote.configuration.azure.devops.model.AzureDevOpsItemFile;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RunWith(MockitoJUnitRunner.class)
public class AzureDevOpsClientImplTest {
	@Mock
	private AzureDevOpsFactoryUrl azureDevOpsFactoryUrl;

	@Mock
	private HttpClient httpClient;

	@Mock
	private RemoteConfigurationProperties setting;
	@InjectMocks
	private AzureDevOpsClientImpl azureDevOpsClientImpl;


	@Test
	public void testGetContentfromItemAuth() throws Exception {
		//Arrange
		String json = "{ \"name\": \"test\", \"java\": true }";
		JsonElement jsonElement = JsonParser.parseString(json);
		JsonObject expected = jsonElement.getAsJsonObject();
		URI url = new URI("http://example.com");
		when(azureDevOpsFactoryUrl.buildGitApiGetItem(null, null, null, null)).thenReturn(url);
		String authHeader = "test";
		when(setting.getAuthorizationHeader()).thenReturn(authHeader);
		AzureDevOpsItemFile item = new AzureDevOpsItemFile();
		item.setContent(json);
		ResponseEntity<AzureDevOpsItemFile> reponse = new ResponseEntity<>(item, HttpStatus.OK);
		
		when(httpClient.restCall(url, AzureDevOpsItemFile.class, authHeader)).thenReturn(reponse);
		//Act
		JsonObject actual = azureDevOpsClientImpl.getContentfromItem();
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetContentfromItemAuthWithUser() throws Exception {
		//Arrange
		String json = "{ \"name\": \"test\", \"java\": true }";
		JsonElement jsonElement = JsonParser.parseString(json);
		JsonObject expected = jsonElement.getAsJsonObject();
		URI url = new URI("http://example.com");
		when(azureDevOpsFactoryUrl.buildGitApiGetItem(null, null, null, null)).thenReturn(url);
		String authHeader = "test";
		when(setting.getUsername()).thenReturn("user");
		when(setting.getAuthorizationHeader()).thenReturn(authHeader);
		AzureDevOpsItemFile item = new AzureDevOpsItemFile();
		item.setContent(json);
		ResponseEntity<AzureDevOpsItemFile> reponse = new ResponseEntity<>(item, HttpStatus.OK);
		
		when(httpClient.restCall(url, AzureDevOpsItemFile.class, authHeader)).thenReturn(reponse);
		//Act
		JsonObject actual = azureDevOpsClientImpl.getContentfromItem();
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetContentfromItemUserAndPassowrd() throws Exception {
		//Arrange
		String json = "{ \"name\": \"test\", \"java\": true }";
		JsonElement jsonElement = JsonParser.parseString(json);
		JsonObject expected = jsonElement.getAsJsonObject();
		URI url = new URI("http://example.com");
		when(azureDevOpsFactoryUrl.buildGitApiGetItem(null, null, null, null)).thenReturn(url);
		String user = "test";
		String pass = "pass";
		when(setting.getUsername()).thenReturn(user);
		when(setting.getPassword()).thenReturn(pass);
		AzureDevOpsItemFile item = new AzureDevOpsItemFile();
		item.setContent(json);
		ResponseEntity<AzureDevOpsItemFile> reponse = new ResponseEntity<>(item, HttpStatus.OK);
		
		when(httpClient.restCall(url, AzureDevOpsItemFile.class, user, pass)).thenReturn(reponse);
		//Act
		JsonObject actual = azureDevOpsClientImpl.getContentfromItem();
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test(expected= RemoteConfigurationException.class) 
	public void testGetContentfromItemNullException() throws Exception {
		//Arrange
		String authHeader = "test";
		when(setting.getAuthorizationHeader()).thenReturn(authHeader);
		URI url = new URI("http://example.com");
		when(azureDevOpsFactoryUrl.buildGitApiGetItem(null, null, null, null)).thenReturn(url);
		ResponseEntity<AzureDevOpsItemFile> reponse = new ResponseEntity<>(HttpStatus.OK);
		
		when(httpClient.restCall(url, AzureDevOpsItemFile.class, authHeader)).thenReturn(reponse);
		//Act
		azureDevOpsClientImpl.getContentfromItem();
	}
	
	@Test(expected= RemoteConfigurationException.class) 
	public void testGetContentfromItemException() throws Exception {
		//Arrange
		String authHeader = "test";
		when(setting.getAuthorizationHeader()).thenReturn(authHeader);
		URI url = new URI("http://example.com");
		AzureDevOpsItemFile item = new AzureDevOpsItemFile();
		item.setContent("#######");
		when(azureDevOpsFactoryUrl.buildGitApiGetItem(null, null, null, null)).thenReturn(url);
		ResponseEntity<AzureDevOpsItemFile> reponse = new ResponseEntity<>(item, HttpStatus.OK);
		when(azureDevOpsFactoryUrl.buildGitApiGetItem(null, null, null, null)).thenReturn(url);
		when(httpClient.restCall(url, AzureDevOpsItemFile.class, authHeader)).thenReturn(reponse);
		//Act
		azureDevOpsClientImpl.getContentfromItem();
	}
	
	@Test(expected= RemoteConfigurationException.class) 
	public void testGetContentfromItemJsonException() throws Exception {
		//Arrange
		String authHeader = "test";
		when(setting.getAuthorizationHeader()).thenReturn(authHeader);
		URI url = new URI("http://example.com");
		AzureDevOpsItemFile item = new AzureDevOpsItemFile();
		item.setContent("key : test");
		when(azureDevOpsFactoryUrl.buildGitApiGetItem(null, null, null, null)).thenReturn(url);
		ResponseEntity<AzureDevOpsItemFile> reponse = new ResponseEntity<>(item, HttpStatus.OK);
		when(azureDevOpsFactoryUrl.buildGitApiGetItem(null, null, null, null)).thenReturn(url);
		when(httpClient.restCall(url, AzureDevOpsItemFile.class, authHeader)).thenReturn(reponse);
		//Act
		azureDevOpsClientImpl.getContentfromItem();
	}
	
	@Test(expected= RemoteConfigurationException.class) 
	public void testGetContentfromItemRestException() throws Exception {
		//Arrange
		String authHeader = "test";
		when(setting.getAuthorizationHeader()).thenReturn(authHeader);
		URI url = new URI("http://example.com");
		when(azureDevOpsFactoryUrl.buildGitApiGetItem(null, null, null, null)).thenReturn(url);
		doThrow(new RestClientException("test")).when(httpClient).restCall(url, AzureDevOpsItemFile.class, authHeader);
		//Act
		azureDevOpsClientImpl.getContentfromItem();
	}

}
