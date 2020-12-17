package azure.devops.remote.configuration;

import com.google.gson.JsonObject;

import azure.devops.remote.configuration.exception.RemoteConfigurationException;

public interface RemoteConfiguration {

	public void buildConfiguration(String organization, String project, String repository, String pathFile,
			String username, String password);

	public void buildConfiguration(String organization, String project, String repository, String pathFile,
			String authorizationHeader);

	public <T> T getValueConfiguration(String key, Class<T> type) throws RemoteConfigurationException;

	public JsonObject getConfiguration() throws RemoteConfigurationException;

}
