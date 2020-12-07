package com.crawl.service.impl;

import com.crawl.service.DocumentParsingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DocumentParsingServiceImpl implements DocumentParsingService {
    @Override
    public Document parse(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
