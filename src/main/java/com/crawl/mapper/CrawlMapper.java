package com.crawl.mapper;

import com.crawl.model.dto.CrawlResponse;

import java.util.Set;

public interface CrawlMapper {
    CrawlResponse map(Set<String> links, Set<String> images, String url);
}
