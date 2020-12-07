package com.crawl.controller;

import com.crawl.model.dto.CrawlRequest;
import com.crawl.model.dto.CrawlResponse;
import com.crawl.service.CrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CrawlerController {

    @Autowired
    private CrawlService crawlService;

    @PostMapping(path = "/crawl", produces= MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CrawlResponse crawl(@RequestBody CrawlRequest crawlRequest) throws IOException {
        return crawlService.crawl(crawlRequest.getUrl());
    }
}
