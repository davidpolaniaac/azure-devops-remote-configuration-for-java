package azure.devops.remote.configuration;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import azure.devops.remote.configuration.exception.RemoteConfigurationException;

@RunWith(MockitoJUnitRunner.class)
public class RemoteConfigurationImplTest {
	
	@Mock
	private AzureDevOpsClient azureDevOpsClient;

	@Mock
	private RemoteConfigurationProperties remoteConfigurationProperties;
	
	@InjectMocks
	private RemoteConfigurationImpl remoteConfigurationImpl;

	@Test
	public void testBuildConfigurationAuthorizationHeader() throws Exception {
		String organization = "test";
		String project= "test";
		String repository= "test";
		String pathFile= "test";
		String authorizationHeader= "test";
		//Assert
		//Act
		remoteConfigurationImpl.buildConfiguration(organization, project, repository, pathFile, authorizationHeader);
		//Assert
		verify(remoteConfigurationProperties).setOrganization(organization);
		verify(remoteConfigurationProperties).setProject(project);
		verify(remoteConfigurationProperties).setRepository(repository);
		verify(remoteConfigurationProperties).setAuthorizationHeader(authorizationHeader);
	}

	@Test
	public void testBuildConfigurationUserAndPassword() throws Exception {
		//Assert
		String organization = "test";
		String project= "test";
		String repository= "test";
		String pathFile= "test";
		String username= "test";
		String password= "test";
		//Act
		remoteConfigurationImpl.buildConfiguration(organization, project, repository, pathFile, username, password);
		//Assert
		verify(remoteConfigurationProperties).setOrganization(organization);
		verify(remoteConfigurationProperties).setProject(project);
		verify(remoteConfigurationProperties).setUsername(username);
		verify(remoteConfigurationProperties).setPassword(password);
	}

	@Test
	public void testGetValueConfiguration() throws Exception {
		String json = "{ \"name\": \"test\", \"java\": true }";
		JsonElement jsonElement = JsonParser.parseString(json);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		//Assert
		when(azureDevOpsClient.getContentfromItem()).thenReturn(jsonObject);
		//Act
		String actual = remoteConfigurationImpl.getValueConfiguration("name", String.class);
		//Assert
		assertEquals("test", actual);
	}
	
	@Test(expected= RemoteConfigurationException.class) 
	public void testGetValueConfigurationException() throws Exception {
		String json = "{ \"name\": \"test\", \"java\": true }";
		JsonElement jsonElement = JsonParser.parseString(json);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		//Assert
		when(azureDevOpsClient.getContentfromItem()).thenReturn(jsonObject);
		//Act
		remoteConfigurationImpl.getValueConfiguration("java", Integer.class);
	}
	
	@Test
	public void testGetValueFromJsonConfiguration() throws Exception {
		String json = "{ \"name\": \"test\", \"java\": true }";
		JsonElement jsonElement = JsonParser.parseString(json);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		//Assert
		when(azureDevOpsClient.getContentfromItem()).thenReturn(jsonObject);
		//Act
		String actual = remoteConfigurationImpl.getValueConfiguration(jsonObject, "name", String.class);
		//Assert
		assertEquals("test", actual);
	}
	
	@Test(expected= RemoteConfigurationException.class) 
	public void testGetValueFromJsonConfigurationException() throws Exception {
		String json = "{ \"name\": \"test\", \"java\": true }";
		JsonElement jsonElement = JsonParser.parseString(json);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		//Assert
		when(azureDevOpsClient.getContentfromItem()).thenReturn(jsonObject);
		//Act
		remoteConfigurationImpl.getValueConfiguration(jsonObject, "name", Integer.class);
		
	}

	@Test
	public void testGetConfiguration() throws Exception {
		//Assert
		String json = "{ \"name\": \"test\", \"java\": true }";
		JsonElement jsonElement = JsonParser.parseString(json);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		when(azureDevOpsClient.getContentfromItem()).thenReturn(jsonObject);
		//Act
		 JsonObject actual = remoteConfigurationImpl.getConfiguration();
		//Assert
		 assertEquals(jsonObject, actual);
	}

}
