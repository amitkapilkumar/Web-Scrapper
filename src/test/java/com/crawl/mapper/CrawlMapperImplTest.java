package com.crawl.mapper;

import com.crawl.mapper.impl.CrawlMapperImpl;
import com.crawl.model.dto.CrawlResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CrawlMapperImplTest {
    @InjectMocks
    private CrawlMapperImpl crawlMapperImpl;

    @Test
    public void testMap() {
        Set<String> links = new HashSet<>();
        Set<String> images = new HashSet<>();

        links.add("https://wiprodigital.com/who-we-are/");
        links.add("https://wiprodigital.com/what-we-do/");
        links.add("https://wiprodigital.com/what-we-think/");
        links.add("https://linkedin.com/wipro");
        links.add("https://twitter.com//post/wipro");
        images.add("https://s17776.pcdn.co/wp-content/themes/wiprodigital/images/logo.png");
        images.add("https://s17776.pcdn.co/wp-content/themes/wiprodigital/images/logo-dk-2X.png");

        CrawlResponse response =  crawlMapperImpl.map(links, images, "https://wiprodigital.com");

        assertNotNull(response);

        assertEquals(response.getExternalLinks().size(), 2);
        assertTrue(response.getExternalLinks().contains("https://linkedin.com/wipro"));
        assertTrue(response.getExternalLinks().contains("https://twitter.com//post/wipro"));

        assertEquals(response.getInternalLinks().size(), 3);
        assertTrue(response.getInternalLinks().contains("https://wiprodigital.com/who-we-are/"));
        assertTrue(response.getInternalLinks().contains("https://wiprodigital.com/what-we-do/"));
        assertTrue(response.getInternalLinks().contains("https://wiprodigital.com/what-we-think/"));

        assertEquals(response.getImages().size(), 2);
        assertTrue(response.getImages().contains("https://s17776.pcdn.co/wp-content/themes/wiprodigital/images/logo.png"));
        assertTrue(response.getImages().contains("https://s17776.pcdn.co/wp-content/themes/wiprodigital/images/logo-dk-2X.png"));
    }

    @Test
    public void testMapWithEmptyLinksandImages() {
        CrawlResponse response = crawlMapperImpl.map(new HashSet<>(), new HashSet<>(), "https://wiprodigital.com");

        assertNotNull(response);

        assertEquals(response.getInternalLinks().size(), 0);
        assertEquals(response.getExternalLinks().size(), 0);
        assertEquals(response.getImages().size(), 0);
    }
}
