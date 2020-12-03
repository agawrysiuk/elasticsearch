package agawrysiuk.edu.elasticsearchspring.exceptions.messages;

import lombok.Getter;

public enum ExceptionMessage {
    CANT_DOWNLOAD("Can't download from ElasticSearch."),
    CANT_SAVE("Can't save to ElasticSearch."),
    EMPTY_REQUEST("Provided json is empty!"),
    INVALID_BODY("Provided json is invalid!"),
    NO_DATA("No data found to upload to Elasticsearch."),
    UPLOADING_INTERRUPTED("Uploading data to Elasticsearch interrupted.");

    @Getter
    private final String text;

    ExceptionMessage(String text) {
        this.text = text;
    }
}
