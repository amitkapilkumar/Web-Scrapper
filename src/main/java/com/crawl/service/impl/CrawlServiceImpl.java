package com.crawl.service.impl;

import com.crawl.builder.CrawlResponseBuilder;
import com.crawl.mapper.CrawlMapper;
import com.crawl.model.dto.CrawlResponse;
import com.crawl.service.DocumentParsingService;
import com.crawl.service.CrawlService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.crawl.util.AppConstants.CSS_QUERY_HREF;
import static com.crawl.util.AppConstants.CSS_QUERY_IMG;
import static com.crawl.util.AppConstants.KEY_HREF;
import static com.crawl.util.AppConstants.KEY_IMG_SRC;

@Service
public class CrawlServiceImpl implements CrawlService {

    @Autowired
    private DocumentParsingService documentParsingService;

    @Autowired
    private CrawlMapper crawlMapper;

    @Override
    public CrawlResponse crawl(String url) throws IOException {
        CrawlResponse response = crawlUrl(url);

        Set<String> externalHref = new HashSet<>();
        Set<String> internalHref = new HashSet<>();
        Set<String> images = new HashSet<>();
        for(String link : response.getInternalLinks()) {
            CrawlResponse res = crawlUrl(link);
            externalHref.addAll(res.getExternalLinks());
            internalHref.addAll(res.getInternalLinks());
            images.addAll(res.getImages());
        }

        externalHref.addAll(response.getExternalLinks());
        internalHref.addAll(response.getInternalLinks());
        images.addAll(response.getImages());

        return CrawlResponseBuilder.aCrawlResponseBuilder()
                .withExternalLinks(new ArrayList<>(externalHref))
                .withInternalLinks(new ArrayList<>(internalHref))
                .withImages(new ArrayList<>(images)).build();
    }

    private CrawlResponse crawlUrl(String url) throws  IOException {
        Document document = documentParsingService.parse(url);

        Elements linksElement = document.select(CSS_QUERY_HREF);
        Elements imagesElements = document.select(CSS_QUERY_IMG);

        Set<String> links = new HashSet<>();
        Set<String> images = new HashSet<>();

        populateLinksAndImages(linksElement, imagesElements, links, images);

        return crawlMapper.map(links, images, url);
    }

    private void populateLinksAndImages(Elements linksElement, Elements imagesElements, Set<String> links, Set<String> images) {
        for(Element link : linksElement) {
            links.add(link.attr(KEY_HREF));
        }
        for(Element image : imagesElements) {
            images.add(image.attr(KEY_IMG_SRC));
        }
    }
}
