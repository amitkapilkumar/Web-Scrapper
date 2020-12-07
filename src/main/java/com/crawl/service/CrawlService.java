package com.crawl.service;

import com.crawl.model.dto.CrawlResponse;

import java.io.IOException;

public interface CrawlService {
    CrawlResponse crawl(String url) throws IOException;
}
