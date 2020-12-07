# Web-Scrapper
Simple Web Crawler Application


## Solution Overview
Web Scrapper uses jsoup library to crawl the url, it fetch internal links, external links and links to images returning as a json response.
It will the internal links again, in this version only 2nd level of depth is covered i.e. internal links found after crawling main page will 
be crawled again for external, internal and images link. aftet that it will produce the final json response.


## Limitations / Future enahancement
- Additional logic needs to be added to support multiple depth crawling.
- text content can also be parsed and stored in db / files, for having simple search capability.
- Addtional integration test suite or  Refactroing can be done provided with additional time.


## API / endpoint
- Crawl URL : POST http://localhost:8080/crawl

By any Web client or postman, above endpoint can be called with the body :
{
	"url": "https://wiprodigital.com/"
}


## Deployment / Execution
Repo has executable jar file **Web-Scrapper-0.0.1-SNAPSHOT.jar**.
Which can be executed as **java -jar Web-Scrapper-0.0.1-SNAPSHOT.jar**
