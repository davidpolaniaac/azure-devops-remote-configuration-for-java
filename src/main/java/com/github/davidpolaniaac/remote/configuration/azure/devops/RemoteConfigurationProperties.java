package com.github.davidpolaniaac.remote.configuration.azure.devops;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "remote-configuration")
public class RemoteConfigurationProperties {
	
	 private String organization;
	 private String project;
	 private String repository;
	 private String pathFile;
	 private String personalAccessToken;
	 private String authorizationHeader;
	 private String username;
	 private String password;

}
