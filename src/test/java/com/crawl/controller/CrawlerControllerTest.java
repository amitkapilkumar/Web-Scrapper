package com.crawl.controller;

import com.crawl.builder.CrawlRequestBuilder;
import com.crawl.model.dto.CrawlRequest;
import com.crawl.service.CrawlService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CrawlerControllerTest {
    @InjectMocks
    private CrawlerController crawlerController;

    @Mock
    private CrawlService crawlService;

    @After
    public void verifyAfter() {
        verifyNoMoreInteractions(crawlService);
    }

    @Test
    public void testCrawl() throws IOException {
        CrawlRequest request = CrawlRequestBuilder.aCrawlRequestBuilder().withUrl("https://wiprodigital.com/").build();

        crawlerController.crawl(request);

        verify(crawlService).crawl("https://wiprodigital.com/");

    }
}
