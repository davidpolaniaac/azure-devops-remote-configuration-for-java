package azure.devops.remote.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
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
