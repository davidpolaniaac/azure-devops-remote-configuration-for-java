package azure.devops.remote.configuration.model;

@lombok.Data
public class ContentMetadata {
    private Long encoding;
    private String contentType;
    private String fileName;
    private String extension;
    private String vsLink;
}
