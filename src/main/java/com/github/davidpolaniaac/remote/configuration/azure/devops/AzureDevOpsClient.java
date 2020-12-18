package com.github.davidpolaniaac.remote.configuration.azure.devops;

import com.github.davidpolaniaac.remote.configuration.azure.devops.exception.RemoteConfigurationException;
import com.google.gson.JsonObject;

public interface AzureDevOpsClient {
	
	JsonObject getContentfromItem() throws RemoteConfigurationException;
	
}
