package com.crawl.mapper.impl;

import com.crawl.builder.CrawlResponseBuilder;
import com.crawl.mapper.CrawlMapper;
import com.crawl.model.dto.CrawlResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CrawlMapperImpl implements CrawlMapper {

    @Override
    public CrawlResponse map(Set<String> links, Set<String> images, String url) {
        String domainName = url.split("://")[1].split("/")[0];
        List<String> internalLinks = new ArrayList<>();
        List<String> externalLinks = new ArrayList<>();
        List<String> imgLinks = new ArrayList<>(images);

        populateLinks(links, domainName, internalLinks, externalLinks);

        return CrawlResponseBuilder.aCrawlResponseBuilder()
                .withExternalLinks(externalLinks)
                .withInternalLinks(internalLinks)
                .withImages(imgLinks)
                .build();
    }

    private void populateLinks(Set<String> links, String domainName, List<String> internalLinks, List<String> externalLinks) {
        for(String link : links) {
            if(isInternalLink(link, domainName)) {
                internalLinks.add(link);
            }
            else {
                externalLinks.add(link);
            }
        }
    }

    private boolean isInternalLink(String url, String domainName) {
        if(url.startsWith("http")) {
            return url.split("://")[1].split("/")[0].equals(domainName);
        }
        else {
            return true;
        }
    }
}
