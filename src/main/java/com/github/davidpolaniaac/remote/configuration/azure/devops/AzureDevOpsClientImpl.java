package com.github.davidpolaniaac.remote.configuration.azure.devops;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

import com.github.davidpolaniaac.remote.configuration.azure.devops.exception.RemoteConfigurationException;
import com.github.davidpolaniaac.remote.configuration.azure.devops.model.AzureDevOpsItemFile;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

@Component
public class AzureDevOpsClientImpl implements AzureDevOpsClient {

	private final HttpClient httpClient;
	private final RemoteConfigurationProperties setting;
	private final AzureDevOpsFactoryUrl azureDevOpsFactoryUrl;

	@Autowired
	public AzureDevOpsClientImpl(HttpClient httpClient, RemoteConfigurationProperties setting,
			AzureDevOpsFactoryUrl azureDevOpsFactoryUrl) {
		this.httpClient = httpClient;
		this.setting = setting;
		this.azureDevOpsFactoryUrl = azureDevOpsFactoryUrl;
	}

	@Override
	public JsonObject getContentfromItem() throws RemoteConfigurationException {
		try {
			ResponseEntity<AzureDevOpsItemFile> response;
			URI url = azureDevOpsFactoryUrl.buildGitApiGetItem(setting.getOrganization(), setting.getProject(),
					setting.getRepository(), setting.getPathFile());
			if (!StringUtils.isEmpty(setting.getUsername()) && !StringUtils.isEmpty(setting.getPassword())) {
				response = httpClient.restCall(url, AzureDevOpsItemFile.class, setting.getUsername(),
						setting.getPassword());
			} else {
				response = httpClient.restCall(url, AzureDevOpsItemFile.class, setting.getAuthorizationHeader());
			}

			AzureDevOpsItemFile data = response.getBody();
			String content = data.getContent();
			@SuppressWarnings("deprecation")
			JsonElement jsonObject = new JsonParser().parse(content);
			JsonObject json = jsonObject.getAsJsonObject();
			return json;
		} catch (JsonParseException jsonException) {
			throw new RemoteConfigurationException(jsonException.getMessage());
		} catch (RestClientException restException) {
			throw new RemoteConfigurationException(restException.getMessage());
		} catch (NullPointerException exception) {
			throw new RemoteConfigurationException(exception.getMessage());
		} catch (Exception anyException) {
			throw new RemoteConfigurationException(anyException.getMessage());
		}
	}

}
