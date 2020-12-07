package com.crawl.builder;

import com.crawl.model.dto.CrawlResponse;

import java.util.List;

public class CrawlResponseBuilder {
    private List<String> internalLinks;
    private List<String> externalLinks;
    private List<String> images;

    public static CrawlResponseBuilder aCrawlResponseBuilder() {
        return new CrawlResponseBuilder();
    }

    public CrawlResponseBuilder withInternalLinks(List<String> internalLinks) {
        this.internalLinks = internalLinks;
        return this;
    }

    public CrawlResponseBuilder withExternalLinks(List<String> externalLinks) {
        this.externalLinks = externalLinks;
        return this;
    }

    public CrawlResponseBuilder withImages(List<String> images) {
        this.images = images;
        return this;
    }

    public CrawlResponse build() {
        CrawlResponse response = new CrawlResponse();
        response.setInternalLinks(internalLinks);
        response.setExternalLinks(externalLinks);
        response.setImages(images);
        return response;
    }
}
