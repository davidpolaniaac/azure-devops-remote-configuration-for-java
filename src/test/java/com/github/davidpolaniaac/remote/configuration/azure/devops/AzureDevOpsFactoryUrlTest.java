package com.github.davidpolaniaac.remote.configuration.azure.devops;

import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.davidpolaniaac.remote.configuration.azure.devops.AzureDevOpsFactoryUrl;

@RunWith(MockitoJUnitRunner.class)
public class AzureDevOpsFactoryUrlTest {
	
	@InjectMocks
	private AzureDevOpsFactoryUrl azureDevOpsFactoryUrl;

	@Test
	public void testGetBaseApiGit() throws Exception {
		//Arrange
		String organization = "org";
		String project = "project";
		//Act
		UriComponentsBuilder actual = azureDevOpsFactoryUrl.getBaseApiGit(organization, project);
		//Assert
		assertTrue(actual.build().toString().contains(organization));
		assertTrue(actual.build().toString().contains(project));
	}

	@Test
	public void testBuildGitApiGetItem() throws Exception {
		//Arrange
		String organization = "org";
		String project = "project";
		String repository = "repo";
		String path = "path";
		//Act
		URI actual = azureDevOpsFactoryUrl.buildGitApiGetItem(organization, project, repository, path);
		//Assert
		assertTrue(actual.toString().contains(repository));
		assertTrue(actual.toString().contains(path));
	}

}
