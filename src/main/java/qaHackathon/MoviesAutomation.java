package qaHackathon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
public class MoviesAutomation {



	    private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search?q=";
	    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3";

	    public static void main(String[] args) throws Exception {
	        List<String> movieList = readMovieList("movies.txt");
	        Map<String, String> movieWikiMap = new ConcurrentHashMap<>();

	        ExecutorService executor = Executors.newFixedThreadPool(20);

	        // Step 1: Google Search and Extract Wikipedia URLs
	        for (String movie : movieList) {
	            executor.submit(() -> {
	                try {
	                    String searchUrl = GOOGLE_SEARCH_URL + movie + "+wikipedia";
	                    Document doc = Jsoup.connect(searchUrl).userAgent(USER_AGENT).get();
	                    Element wikiLink = doc.select("a[href*=/wiki/]").first();
	                    if (wikiLink != null) {
	                        String wikiUrl = wikiLink.attr("href");
	                        if (wikiUrl.startsWith("/wiki/")) {
	                            wikiUrl = "https://en.wikipedia.org" + wikiUrl;
	                            movieWikiMap.put(movie, wikiUrl);
	                        }
	                    }
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            });
	        }
	        executor.shutdown();
	        executor.awaitTermination(10, TimeUnit.MINUTES);

	        // Step 2: Scrape Wikipedia Pages and Compare with IMDb
	        for (Map.Entry<String, String> entry : movieWikiMap.entrySet()) {
	            String movie = entry.getKey();
	            String wikiUrl = entry.getValue();
	            executor.submit(() -> {
	                try {
	                    Document wikiDoc = Jsoup.connect(wikiUrl).userAgent(USER_AGENT).get();
	                    String directorWiki = extractDirectorFromWikipedia(wikiDoc);
	                    String imdbUrl = extractImdbUrlFromWikipedia(wikiDoc);
	                    
	                    if (imdbUrl != null) {
	                        Document imdbDoc = Jsoup.connect(imdbUrl).userAgent(USER_AGENT).get();
	                        String directorImdb = extractDirectorFromImdb(imdbDoc);
	                        boolean isMatch = directorWiki.equals(directorImdb);
	                        // Report Results
	                        System.out.println("Movie: " + movie);
	                        System.out.println("Wikipedia Director: " + directorWiki);
	                        System.out.println("IMDB Director: " + directorImdb);
	                        System.out.println("Match: " + isMatch);
	                    }
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            });
	        }
	        executor.shutdown();
	        executor.awaitTermination(10, TimeUnit.MINUTES);

	        // Generate HTML Report
	        generateHtmlReport(movieWikiMap);
	    }

	    private static List<String> readMovieList(String filename) throws IOException {
	        return Files.readAllLines(Paths.get(filename));
	    }

	    private static String extractDirectorFromWikipedia(Document doc) {
	        // Implement logic to extract director from Wikipedia page
	        return "";
	    }

	    private static String extractImdbUrlFromWikipedia(Document doc) {
	        // Implement logic to extract IMDb URL from Wikipedia page
	        return "";
	    }

	    private static String extractDirectorFromImdb(Document doc) {
	        // Implement logic to extract director from IMDb page
	        return "";
	    }

	    private static void generateHtmlReport(Map<String, String> movieWikiMap) {
	        // Implement logic to generate HTML report
	    }
	

	 
}
