package com.github.davidpolaniaac.remote.configuration.azure.devops.model;

@lombok.Data
public class AzureDevOpsItemFile {
    private String objectID;
    private String gitObjectType;
    private String commitID;
    private String path;
    private String content;
    private ContentMetadata contentMetadata;
    private String url;
    private Links links;
}
