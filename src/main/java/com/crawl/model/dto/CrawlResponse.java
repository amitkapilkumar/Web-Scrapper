package com.crawl.model.dto;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class CrawlResponse {
    private List<String> internalLinks;
    private List<String> externalLinks;
    private List<String> images;

    @JsonGetter(value = "internal-links")
    public List<String> getInternalLinks() {
        return internalLinks;
    }

    @JsonSetter("internal-links")
    public void setInternalLinks(List<String> internalLinks) {
        this.internalLinks = internalLinks;
    }

    @JsonGetter(value = "external-links")
    public List<String> getExternalLinks() {
        return externalLinks;
    }

    @JsonSetter("external-links")
    public void setExternalLinks(List<String> externalLinks) {
        this.externalLinks = externalLinks;
    }

    @JsonGetter("images")
    public List<String> getImages() {
        return images;
    }

    @JsonSetter("images")
    public void setImages(List<String> images) {
        this.images = images;
    }
}
