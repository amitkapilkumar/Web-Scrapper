package com.crawl.service;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface DocumentParsingService {
    Document parse(String url) throws IOException;
}
