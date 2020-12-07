package com.crawl.service;

import com.crawl.mapper.CrawlMapper;
import com.crawl.mapper.impl.CrawlMapperImpl;
import com.crawl.model.dto.CrawlResponse;
import com.crawl.service.DocumentParsingService;
import com.crawl.service.impl.CrawlServiceImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.crawl.util.AppConstants.CSS_QUERY_HREF;
import static com.crawl.util.AppConstants.CSS_QUERY_IMG;
import static com.crawl.util.AppConstants.KEY_HREF;
import static com.crawl.util.AppConstants.KEY_IMG_SRC;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrawlServiceImplTest {

    @InjectMocks
    private CrawlServiceImpl crawlServiceImpl;

    @Mock
    private DocumentParsingService documentParsingService;

    @Spy
    private CrawlMapper crawlMapper = new CrawlMapperImpl();

    @After
    public void verifyAfter() {
        verifyNoMoreInteractions(documentParsingService, crawlMapper);
    }

    @Test
    public void testCrawl() throws IOException {
        String url = "https://wiprodigital.com/";
        Document document = mock(Document.class);
        Elements links = getLinks("https://www.linkedin.com/", "https://www.facebook.com/");
        Elements images = getImages("https://s17776.pcdn.co/wp-content/uploads/2016/05/cisco.png");

        when(documentParsingService.parse(url)).thenReturn(document);
        when(document.select(CSS_QUERY_HREF)).thenReturn(links);
        when(document.select(CSS_QUERY_IMG)).thenReturn(images);

        CrawlResponse response =  crawlServiceImpl.crawl(url);

        assertNotNull(response);
        assertEquals(response.getExternalLinks().size(), 2);
        assertTrue(response.getExternalLinks().contains("https://www.linkedin.com/"));
        assertTrue(response.getExternalLinks().contains("https://www.facebook.com/"));
        assertEquals(response.getInternalLinks().size(), 0);
        assertEquals(response.getImages().size(), 1);
        assertTrue(response.getImages().contains("https://s17776.pcdn.co/wp-content/uploads/2016/05/cisco.png"));

        verify(documentParsingService).parse(url);
        verify(crawlMapper).map(anySet(), anySet(), eq(url));
    }

    @Test
    public void testCrawlWithInternalLinksCrawling() throws IOException {
        String url = "https://wiprodigital.com/";
        Document document = mock(Document.class);
        Elements links = getLinks("https://www.linkedin.com/", "https://www.facebook.com/", "https://wiprodigital.com/what-we-do/");
        Elements images = getImages("https://s17776.pcdn.co/wp-content/uploads/2016/05/cisco.png");

        when(documentParsingService.parse(url)).thenReturn(document);
        when(documentParsingService.parse("https://wiprodigital.com/what-we-do/")).thenReturn(document);
        when(document.select(CSS_QUERY_HREF)).thenReturn(links);
        when(document.select(CSS_QUERY_IMG)).thenReturn(images);

        CrawlResponse response =  crawlServiceImpl.crawl(url);

        assertNotNull(response);
        assertEquals(response.getExternalLinks().size(), 2);
        assertTrue(response.getExternalLinks().contains("https://www.linkedin.com/"));
        assertTrue(response.getExternalLinks().contains("https://www.facebook.com/"));
        assertEquals(response.getInternalLinks().size(), 1);
        assertTrue(response.getInternalLinks().contains("https://wiprodigital.com/what-we-do/"));
        assertEquals(response.getImages().size(), 1);
        assertTrue(response.getImages().contains("https://s17776.pcdn.co/wp-content/uploads/2016/05/cisco.png"));

        verify(documentParsingService).parse(url);
        verify(documentParsingService).parse("https://wiprodigital.com/what-we-do/");
        verify(crawlMapper).map(anySet(), anySet(), eq(url));
        verify(crawlMapper).map(anySet(), anySet(), eq("https://wiprodigital.com/what-we-do/"));
    }

    private Elements getImages(String... images) {
        List<Element> elements = new ArrayList<>();
        for(String image : images) {
            Element element = mock(Element.class);
            when(element.attr(KEY_IMG_SRC)).thenReturn(image);
            elements.add(element);
        }
        return new Elements(elements);
    }

    private Elements getLinks(String... links) {
        List<Element> elements = new ArrayList<>();
        for(String link : links) {
            Element element = mock(Element.class);
            when(element.attr(KEY_HREF)).thenReturn(link);
            elements.add(element);
        }
        return new Elements(elements);
    }
}
