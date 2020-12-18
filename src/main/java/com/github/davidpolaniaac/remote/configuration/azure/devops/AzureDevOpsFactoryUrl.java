package com.github.davidpolaniaac.remote.configuration.azure.devops;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AzureDevOpsFactoryUrl {
	
	private static final String SEGMENT_API = "_apis";
	private static final String SEGMENT_GIT = "git";
	private static final String SEGMENT_REPOSITORIES = "repositories";
	private static final String SEGMENT_ITEMS = "items";
	private static final String QUERY_PATH = "path";
	private static final String QUERY_FORMAT = "$format";
	private static final String QUERY_INCLUDE_CONTENT = "includeContent";
	private static final String QUERY_API_VERSION = "api-version";
	private static final String QUERY_INCLUDE_CONTENT_METADATA = "includeContentMetadata";
	private static final String QUERY_RESOLVELFS = "resolveLfs";
	private static final String SCHEME = "https";
	private static final String HOST = "dev.azure.com";
	private static final String API_VERSION = "6.0";
	private static final String FORMAT = "json";

	public UriComponentsBuilder getBaseApiGit(String organization, String project) {

		return UriComponentsBuilder.newInstance()
				.scheme(SCHEME).host(HOST)
				.pathSegment(organization)
				.pathSegment(project)
				.pathSegment(SEGMENT_API)
				.pathSegment(SEGMENT_GIT)
				.pathSegment(SEGMENT_REPOSITORIES);
	}
	
	public URI buildGitApiGetItem(String organization, String project, String repository, String path) {

		UriComponentsBuilder builderBase = getBaseApiGit(organization, project);
		return builderBase.pathSegment(repository)
				.pathSegment(SEGMENT_ITEMS)
				.queryParam(QUERY_PATH, path)
				.queryParam(QUERY_FORMAT, FORMAT)
				.queryParam(QUERY_INCLUDE_CONTENT, true)
				.queryParam(QUERY_INCLUDE_CONTENT_METADATA, true)
				.queryParam(QUERY_RESOLVELFS, true)
				.queryParam(QUERY_API_VERSION, API_VERSION)
				.build(true)
				.toUri();
	}

}
