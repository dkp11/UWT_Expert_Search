//Information Retrieval 
package search;
//Information Retrieval

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;


/**
 * An parser that traverses all the the html pages and
 * puts it in a database.
 * Group: Sagar Kamboj, Dinesh Prasad, Sanjeev Kamboj
 * 
 * @author Sanjeev Kamboj
 */
public class HtmlParser {
	public static void main(String[] args) {
		
		//Make the database connection
		Connection myCon = makeConnection();
		
		//An object to interact with the database
		final SqlDao my_dao = new SqlDao(myCon);
		
		//Gets the homepage of every faculty
		ResultSet pages = my_dao.getAllHomePages();
		
		//Crawler object to go through all the pages.
		final Crawler my_crawler = new Crawler();
		
		try {
			while(pages.next()) {
				String text = "";
				final int id = Integer.parseInt(pages.getString(1));
				final String link = pages.getString(2);
				
				text = my_crawler.crawlLink(link);
				text += getOtherText(my_crawler);
				
				my_dao.insertHtmlText(id, text);

			}
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Get the text of the links that the faculty hompage link to
	 * 
	 * @param my_crawler - The crawler that goes through all the pages
	 * @return The text of all the hompages
	 */
	private static String getOtherText(final Crawler my_crawler) {
		String text = "";
		for(String link : my_crawler.need_to_see) {
			text += my_crawler.crawlLink(link);
		}
		my_crawler.need_to_see = new HashSet<String> ();
		my_crawler.link_once = false;
		
		return text;
		
	}

	/**
	 * A private method that makes the connection with the database
	 * @return A connection with the database
	 */
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
			System.err.println("Error in make connection");
			e1.printStackTrace();
		}
		return myCon;
		
	}
}
