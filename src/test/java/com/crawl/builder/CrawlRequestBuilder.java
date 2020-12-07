package com.crawl.builder;

import com.crawl.model.dto.CrawlRequest;

public class CrawlRequestBuilder {
    private String url;

    public static CrawlRequestBuilder aCrawlRequestBuilder() {
        return new CrawlRequestBuilder();
    }

    public CrawlRequestBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public CrawlRequest build() {
        CrawlRequest request = new CrawlRequest();
        request.setUrl(url);
        return request;
    }
}
