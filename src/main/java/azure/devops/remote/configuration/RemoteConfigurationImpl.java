package azure.devops.remote.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import azure.devops.remote.configuration.exception.RemoteConfigurationException;

@Component
public class RemoteConfigurationImpl implements RemoteConfiguration {

	private final RemoteConfigurationProperties remoteConfigurationProperties;
	private final AzureDevOpsClient azureDevOpsClient;

	@Autowired
	public RemoteConfigurationImpl(RemoteConfigurationProperties remoteConfigurationProperties,
			AzureDevOpsClient azureDevOpsClient) {
		this.remoteConfigurationProperties = remoteConfigurationProperties;
		this.azureDevOpsClient = azureDevOpsClient;
	}

	private void build(String organization, String project, String repository, String pathFile) {
		this.remoteConfigurationProperties.setOrganization(organization);
		this.remoteConfigurationProperties.setProject(project);
		this.remoteConfigurationProperties.setRepository(repository);
		this.remoteConfigurationProperties.setPathFile(pathFile);
	}

	public void buildConfiguration(String organization, String project, String repository, String pathFile,
			String authorizationHeader) {
		build(organization, project, repository, pathFile);
		this.remoteConfigurationProperties.setAuthorizationHeader(authorizationHeader);
	}

	public void buildConfiguration(String organization, String project, String repository, String pathFile,
			String username, String password) {
		build(organization, project, repository, pathFile);
		this.remoteConfigurationProperties.setUsername(username);
		this.remoteConfigurationProperties.setPassword(password);
	}

	public <T> T getValueConfiguration(String key, Class<T> type) throws RemoteConfigurationException {
		JsonObject configuration = azureDevOpsClient.getContentfromItem();
		try {
			JsonElement json = configuration.get(key);
			Gson gson = new GsonBuilder().create();
			T value = gson.fromJson(json, type);
			return value;
		}
		catch (JsonParseException e) {
		  throw new RemoteConfigurationException(e.getMessage());
		}
	}
	
	public <T> T getValueConfiguration(JsonObject jsonObject, String key, Class<T> type) throws RemoteConfigurationException {
		try {
			JsonElement json = jsonObject.get(key);
			Gson gson = new GsonBuilder().create();
			T value = gson.fromJson(json, type);
			return value;
		}
		catch (JsonParseException e) {
		  throw new RemoteConfigurationException(e.getMessage());
		}
	}

	public JsonObject getConfiguration() throws RemoteConfigurationException {
		JsonObject configuration = azureDevOpsClient.getContentfromItem();
		return configuration;
	}

}
