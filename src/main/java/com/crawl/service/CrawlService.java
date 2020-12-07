package com.crawl.service;

import com.crawl.model.dto.CrawlResponse;

public interface CrawlService {
    CrawlResponse crawl(String url);
}
