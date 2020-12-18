package com.github.davidpolaniaac.remote.configuration.azure.devops;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RemoteConfigurationAutoConfiguration {
	
	@Bean
	public RemoteConfiguration remoteConfiguration(){
		RemoteConfigurationProperties remoteConfigurationProperties = new RemoteConfigurationProperties();
		AzureDevOpsFactoryUrl azureDevOpsFactoryUrl = new AzureDevOpsFactoryUrl();
		HttpClient httpClient = new HttpClientImpl(restTemplate(), httpHeaders());
		AzureDevOpsClient azureDevOpsClient = new AzureDevOpsClientImpl(httpClient , remoteConfigurationProperties, azureDevOpsFactoryUrl);
		return new RemoteConfigurationImpl(remoteConfigurationProperties, azureDevOpsClient);
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	@Bean
	public HttpHeaders httpHeaders(){
		return new HttpHeaders();
	}

}
