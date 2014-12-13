//Information Retrieval Project

package search;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Crawler class to crawl through all the Html Pages
 * Group: Sagar Kamboj, Dinesh Prasad, Sanjeev Kamboj
 * 
 * @author Sanjeev Kamboj
 */
public class Crawler {
	
	/** A set of webpages that are already seen */
	protected Set<String> need_to_see;
	
	/** Is flag for if one iteration of the crawling has occured*/
	protected boolean link_once = false;
	
	/** Constructor*/
	public Crawler() {
		//seen_links = new HashSet<String>();
		need_to_see = new HashSet<String>();
		
	}
	
	/**
	 * A method that takes in a html link and retrieves the text
	 * 
	 * @param the_link - A link string
	 * @return A String which is the body of the html
	 */
	
	protected String crawlLink(String the_link) {
		Document doc = null;
		
		String text = "";
		
		try {
			System.out.println("Page Retriever: Connecting to " + the_link);
            doc = Jsoup.connect(the_link).get();
            
        } catch (IOException | IllegalArgumentException ie) {
                System.err.println("Cannot connect to " + the_link);
        }
		
		if(doc != null) {
			
		    Elements links = doc.select("a");
		 
            //for each element with a link
            //attribute add absolute url to list
            for (Element link : links) {
            	String str_link = link.attr("abs:href");
            	
            	if(!link_once) {
            		need_to_see.add(str_link);
            	}
            	
            }
            link_once = true;
            
            if(doc != null && doc.body()!=null) {
            	text = doc.body().text();
            }
		}
		
		
		return text;
		
	}
	

}
