//Information Retrieval
package search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * An indexer object that adds id, text pairs into Lucene.
 * Group: Sagar Kamboj, Dinesh Prasad, Sanjeev Kamboj
 * 
 * @author Sanjeev Kamboj
 */
public class Indexer {
	/** A constant for the number of hits to display */
	private static final int HITS_PER_PAGE = 10;
	
	/** An analyzer for indexing*/
    final private StandardAnalyzer analyzer;
	
    /**A directory that is the central structure for the indices*/
	final private Directory index;
	
	/** An indexwriter for indexing*/
	private IndexWriter w;
	
	/** Constructor*/
	public Indexer() {
		analyzer  = new StandardAnalyzer();
		index = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
		
		w = null;
		
		try {
			w = new IndexWriter(index, config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("IndexWriter Error");
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the index, tokenizing the text 
	 * @param id - the id of the record
	 * @param text - the text to tokenize
	 */
	public void addIndex(final String the_id, final String the_text) {
		
		  try {

			  Document doc = new Document();
		  
			  // We use a string field for id because we don't want it tokenized
			  doc.add(new StringField("id", the_id, Field.Store.YES));
			  
			// We use a string field for id because we don't want it tokenized
			  //doc.add(new StringField("img", data.getPicture(), Field.Store.YES));
		  
			  // A text field will be tokenized
			  doc.add(new TextField("text", the_text, Field.Store.YES));
			  
			  w.addDocument(doc);
			  w.commit();
			  
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  
			  System.err.println("Error in addIndex");
			  e.printStackTrace();
		  }
		  
	}
	
	/**
	 * Search the RamDirectory  
	 * @param the_query - The query that the user enters.
	 * @return A list of documents that are the top ranked documents
	 */
	public List<Document> searchIndex(final String the_query) {
		List<Document> ranking_list = new ArrayList<Document>();
		
		if(!the_query.equals("")) {
			
			Query q = null;
			ScoreDoc[] hits = null;
			
			try {
				q = new QueryParser("text", analyzer).parse(the_query);
				
				// Searching code
				
				if(index.listAll().length != 0) {
					
				    IndexReader reader = DirectoryReader.open(index);
				    IndexSearcher searcher = new IndexSearcher(reader);
				    TopScoreDocCollector collector = TopScoreDocCollector.create(HITS_PER_PAGE, true);
				    
				    searcher.search(q, collector);
				    hits = collector.topDocs().scoreDocs;
				    
				    ranking_list = getRankingList(hits, searcher);
				    
				    // reader can only be closed when there is no need to access the documents any more
				    reader.close();
				}
			    
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println("Error in searchIndex");
				e.printStackTrace();
			}
		} 
		return ranking_list;
		
	}
	
	/**
	 * Get the list of Documents that the query matches
	 * 
	 * @param hits A scoredoc object that Lucene returns 
	 * @param searcher - Allows to search through RamDirectory
	 * @return A list of Documents that match
	 */
	private List<Document> getRankingList(final ScoreDoc[] hits, 
                                          final IndexSearcher searcher) {

		List<Document> ranking_list = new ArrayList<Document>();

		//	Code to display the results of search
		System.out.println("Found " + hits.length + " hits.");

		for(int i=0; i<hits.length; i++) {
			int docId = hits[i].doc;
			Document d = null;
			
			try {
				d = searcher.doc(docId);
				ranking_list.add(d);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				System.err.println("Error in getRankingList.");
				e.printStackTrace();
			}
		}

		return ranking_list;

	}

}
