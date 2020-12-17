package azure.devops.remote.configuration.model;

@lombok.Data
public class Links {
    private Blob self;
    private Blob repository;
    private Blob blob;
}
