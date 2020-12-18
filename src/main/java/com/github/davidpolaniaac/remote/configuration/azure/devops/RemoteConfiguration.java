package com.github.davidpolaniaac.remote.configuration.azure.devops;

import com.github.davidpolaniaac.remote.configuration.azure.devops.exception.RemoteConfigurationException;
import com.google.gson.JsonObject;

public interface RemoteConfiguration {

	public void buildConfiguration(String organization, String project, String repository, String pathFile,
			String username, String password);

	public void buildConfiguration(String organization, String project, String repository, String pathFile,
			String authorizationHeader);

	public <T> T getValueConfiguration(String key, Class<T> type) throws RemoteConfigurationException;
	
	public <T> T getValueConfiguration(JsonObject jsonObject, String key, Class<T> type) throws RemoteConfigurationException;

	public JsonObject getConfiguration() throws RemoteConfigurationException;

}
