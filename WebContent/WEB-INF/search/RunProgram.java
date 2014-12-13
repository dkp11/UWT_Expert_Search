//Information Retrieval

package search;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import org.apache.lucene.document.Document;


/**
 * The search engine of the program
 * Group: Sagar Kamboj, Dinesh Prasad, Sanjeev Kamboj
 * 
 * @author Sanjeev Kamboj
 */
public class RunProgram {
	
	/**
	 * An object to connect with the database
	 */
	private SqlDao my_dao;
    
	/**Constructor*/
	public RunProgram() {
		Connection myCon = makeConnection();
		
		my_dao = new SqlDao(myCon);
		
	}
	
	/**
	 * The program that UI calls to search through database
	 * and rank.
	 * 
	 * @param the_cat - The category of that user chooses 
	 * @param the_query - The query that the user enters.
	 * @return A lIst of faculty that meet the user's criteria
	 */
	public List<Faculty> runProgram(String the_cat, final String the_query) {

	    final Indexer my_indexer = new Indexer();
	    
	    final List<Faculty> list = new ArrayList<Faculty>();
	    
	    the_cat = the_cat.toLowerCase();
	    
	    Map<Integer,Faculty> id_to_rec = new HashMap<Integer, Faculty>();
	    
	    if(the_cat.equals("name")) {   //If passed in Name
	    	id_to_rec = my_dao.getFacultyByName(the_query);
	    	
	    } else if (the_cat.equals("department")) {  //passed in Dept
	    	id_to_rec = my_dao.getFacultyByDept(the_query);
	    	
	    } else if(the_cat.equals("expertise")) { //passed in Expertise
	    	id_to_rec = my_dao.getFacultyByExp(the_query);

	    } else if(the_cat.equals("telephone")) {
			id_to_rec = my_dao.getByTele(the_query);
			
		} else if(the_cat.equals("netid")) {
			id_to_rec = my_dao.getByNetID(the_query);
			
		} else if(the_cat.equals("email")) {
			id_to_rec = my_dao.getByEmail(the_query);
			
		}
		
	    
	    if(the_cat.equals("expertise")) {
	    	
			for(final Faculty r: id_to_rec.values()) {
				String text = my_dao.getTextFromId(r.getID());
				my_indexer.addIndex(Integer.toString(r.getID()), text);	 
																	
			}
			
			List<Document> my_docs = my_indexer.searchIndex(the_query);
			
			if(my_docs.isEmpty()) {
				System.out.println("No Results");
			} else {
			
				for(final Document d: my_docs) {
					final int id = Integer.parseInt(d.get("id"));
					final Faculty f= id_to_rec.get(id);
					
					list.add(f);
					
				}
			}
	    } else {
	    	for(final Faculty f: id_to_rec.values()) {
				/*System.out.println(r.getID() + "\t" + r.getFirst_name()
						   + " " + r.getLast_name() +  "\t" + r.getLink());
				*/
	    		list.add(f);
			}
	    }
	    
	    return list;
	}
	
	/** Gets all departments */
	public List<String> getAllDept() {
		return my_dao.getAllDept();
	}
	
	
	/** Make the connection to the database */
	private static Connection makeConnection() {
		//Accessing Driver from JAR file
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection myCon = null;
		//Creates a connection with a variable called myCon
		try {
			myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/FacultySearch",
															"root", "");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return myCon;
		
	}
	

}
