package com.crawl.service.impl;

import com.crawl.builder.CrawlResponseBuilder;
import com.crawl.model.dto.CrawlResponse;
import com.crawl.service.CrawlService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CrawlServiceImpl implements CrawlService {

    @Override
    public CrawlResponse crawl(String url) {
        return CrawlResponseBuilder.aCrawlResponseBuilder()
                .withImages(new ArrayList<>())
                .withInternalLinks(new ArrayList<>())
                .withExternalLinks(new ArrayList<>()).build();
    }
}
