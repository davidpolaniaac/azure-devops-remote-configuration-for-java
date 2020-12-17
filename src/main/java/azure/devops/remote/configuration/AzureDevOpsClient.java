package azure.devops.remote.configuration;

import com.google.gson.JsonObject;

import azure.devops.remote.configuration.exception.RemoteConfigurationException;

public interface AzureDevOpsClient {
	
	JsonObject getContentfromItem() throws RemoteConfigurationException;
	
}
