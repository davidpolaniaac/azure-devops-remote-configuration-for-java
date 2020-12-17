package azure.devops.remote.configuration;

import java.net.URI;

import org.springframework.http.ResponseEntity;

public interface HttpClient {
	
	public <T> ResponseEntity<T> restCall(URI url, Class<T> responseType, String authHeader);
	public <T> ResponseEntity<T> restCall(URI url, Class<T> responseType, String username, String password);

}
