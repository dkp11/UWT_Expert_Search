//Information Retrieval
package search;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class creates  an object to interact with the database.
 * Group: Sagar Kamboj, Dinesh Prasad, Sanjeev Kamboj
 * 
 * @author Sanjeev Kamboj
 */
public class SqlDao {
	
	/** The connection of the database.*/
	private Connection my_con;
	
	/** Constructor */
	public SqlDao(final Connection the_con) {
		my_con = the_con;
	}
	
	/**
	 * Retrieve a list of faculty by experience
	 * 
	 * @param the_query - the query that user passes in 
	 * @return A map with integer, Faculty key-value pair
	 */
	protected Map<Integer, Faculty> getFacultyByExp(final String the_query) {
		ResultSet result = null;
		
		String str = "Select * From Faculty_UWT" +
				      " where (HomePageExists = 1) And (Expertise Like ?" +
				       "OR FocusArea Like ?) ";
				 
		try {
			PreparedStatement statement = my_con.prepareStatement(str);
			
			statement.setString(1,"%" + the_query + "%");
			statement.setString(2, "%" + the_query + "%");
			result = statement.executeQuery();
			
					
		} catch (SQLException e) {
			System.err.println("Error in getFacultyByExp");
			e.printStackTrace();
		}
		 
		return findRecords(result, the_query);
		
		
	}
	
	/**
	 * Get the list of faculty by name
	 * 
	 * @param the_name - The name of the faculty
	 * @return  A map with integer, Faculty key-value pair
	 */
	protected Map<Integer, Faculty> getFacultyByName(String the_name) {
		ResultSet result = null;
		the_name = the_name.toLowerCase();
		String[] name_edit = the_name.split(" ");
		String str = "";
		PreparedStatement statement = null;
				
		if(name_edit != null && name_edit.length == 2) {
			String name = name_edit[1] + ", " + name_edit[0];
			
			str = "Select * From Faculty_UWT" +
		          " where Faculty_UWT.Name Like ?" +
		         " OR Faculty_UWT.Name Like ?";
			
		    try {
				statement = my_con.prepareStatement(str);
				statement.setString(1, "%" + the_name + "%");
				statement.setString(2, "%" + name + "%");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			
		} else {
			str = "Select * From Faculty_UWT" +
				  " where Faculty_UWT.Name Like ?";
			try {
				statement = my_con.prepareStatement(str);
				statement.setString(1, "%" + the_name + "%");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
		try {
			
			result = statement.executeQuery();
					
		} catch (SQLException e) {
			System.err.println("Error in getFacultyByName");
			e.printStackTrace();
		}
		 
		return findRecords(result, the_name);
		
		
	}
	
	/**
	 * Get the faculty with the department passed in 
	 * @param the_dept - the department
	 * @return  A map with integer, Faculty key-value pair
	 */
	protected Map<Integer, Faculty> getFacultyByDept(final String the_dept) {
		ResultSet result = null;
		//List<Record> result_list = null;
		
		String str = "Select * From Faculty_UWT" +
				      " where Faculty_UWT.Department Like ?";
				 
		try {
			PreparedStatement statement = my_con.prepareStatement(str);
			
			statement.setString(1, "%" + the_dept + "%");
			
			result = statement.executeQuery();
					
		} catch (SQLException e) {
			System.err.println("Error in getFacultyByDept");
			e.printStackTrace();
		}
		 
		return findRecords(result, the_dept);
		
	}
	
	/**
	 * Get the list of all homepages
	 * 
	 * @return A resultSet with all the hompages
	 */
	protected ResultSet getAllHomePages() {
		ResultSet result = null;
		//List<Record> result_list = null;
		
		String str = "Select ID, Homepage From Faculty_UWT " +
				      "Where Faculty_UWT.HomePageExists =1";
		 
		try {
			PreparedStatement statement = my_con.prepareStatement(str);
			
			//statement.setDouble(1, 1);
			result = statement.executeQuery();
					
		} catch (SQLException e) {
			System.err.println("Error in getEmployer.");
			e.printStackTrace();
		}
		 
		return result;
		
		
	}
	
	/**
	 * Get the faculty by the telephone number
	 * 
	 * @param the_tele - The telephone passed in 
	 * @return The faculty with the phone number
	 */
	protected Map<Integer, Faculty> getByTele(final String the_tele) {
		ResultSet result = null;
		
		String str = "Select * From Faculty_UWT " +
				      "where Faculty_UWT.Phone = ?";
		 
		try {
			PreparedStatement statement = my_con.prepareStatement(str);
		
			statement.setString(1, the_tele);
			
			result = statement.executeQuery();
					
		} catch (SQLException e) {
			System.err.println("Error in getEmployer.");
			e.printStackTrace();
		}
		 
		return findRecords(result, the_tele);
		
		
	}
	
	/**
	 * Get the faculty with the netid
	 * 
	 * @param the_id - the id of the faculty
	 * @return The faculty with the id.
	 */
	protected Map<Integer, Faculty> getByNetID(final String the_id) {
		ResultSet result = null;
		
		String str = "Select * From Faculty_UWT " +
				      "where Faculty_UWT.NetID = ?";
		 
		try {
			PreparedStatement statement = my_con.prepareStatement(str);
		
			statement.setString(1, "" + the_id);
			
			result = statement.executeQuery();
					
		} catch (SQLException e) {
			System.err.println("Error in getEmployer.");
			e.printStackTrace();
		}
		 
		return findRecords(result, the_id);
	
	}
	
	/**
	 * Get the Faculty with the email passed in 
	 * @param the_email - The email of the faculty
	 * @return The faculty with the given email
	 */
	protected Map<Integer, Faculty> getByEmail(final String the_email) {
		ResultSet result = null;
		
		String str = "Select * From Faculty_UWT " +
				      "where Faculty_UWT.Email = ?";
		 
		try {
			PreparedStatement statement = my_con.prepareStatement(str);
		
			statement.setString(1, the_email);
			
			result = statement.executeQuery();
					
		} catch (SQLException e) {
			System.err.println("Error in getEmployer.");
			e.printStackTrace();
		}
		 
		return findRecords(result, the_email);
		
		
	}
	
	
	/**
	 * Helper method that returns a list of the strings matching query terms
	 * 
	 * @param the_result - The set of the faculty retrieved
	 * @param the_query - The query passed in by user
	 * @return A map that links the faculty id with the Faculty object
	 */
	private Map<Integer, Faculty> findRecords(final ResultSet the_result, String the_query) {
		Map<Integer, Faculty> found_list = new HashMap<Integer, Faculty>();
		
		if(the_result != null) {
			
		
			try {
				while(the_result.next()) {
					final int id= Integer.parseInt(the_result.getString(1));  
							
				    Faculty r = new Faculty(id, 
							              the_result.getString(2),  //netid
			                              the_result.getString(5),  //first name
			                              the_result.getString(4),  //last name
			                              the_result.getString(6),  //position
			                              the_result.getString(7),  //department
			                              the_result.getString(8),  //phone 
			                              the_result.getString(9),  //email
			                              the_result.getString(10),  //homepage
				    					  Integer.parseInt(the_result.getString(12)),  //info exists
				    					  the_result.getString(15),  //intro
				    					  Integer.parseInt(the_result.getString(16))); //pic exists
				    
				    
					found_list.put(id, r);
					
				}
				
			} catch (SQLException e) {
				System.err.println("Error in findRecords");
				e.printStackTrace();
			}
		}
		
		return found_list;
		
		
	}
	
	/**
	 * Insert the text of the crawled pages into the mtlText database
	 * 
	 * @param id - The id of the Faculty
	 * @param text - The test of the faculty's homepage
	 */
	protected void insertHtmlText(final int id, final String text) {
		
		try {
			PreparedStatement statement = my_con.prepareStatement("INSERT INTO HtmlText VALUES (?,?)");
			statement.setDouble(1, id);
			statement.setString(2, text);
			
			statement.executeUpdate();
		
		} catch (SQLException e) {
			System.err.println("Error in insertHTMLText");
			e.printStackTrace();
		}

	}
	
	/**
	 * Get the text of the faculty's homepage from faculty id
	 * 
	 * @param the_id - The id of the faculty member
	 * @return The text of the faculty's homepage
	 */
	protected String getTextFromId(final int the_id) {
		ResultSet result = null;
		String text_body = "";
		
		String str = "Select textBody From HtmlText " +
				      " where HtmlText.textID = ?";
				 
		try {
			PreparedStatement statement = my_con.prepareStatement(str);
			
			statement.setDouble(1, the_id);
			result = statement.executeQuery();
			
			if(result != null) {
				while(result.next()) {
					 text_body = result.getString(1);
				 }
			}
					
		} catch (SQLException e) {
			System.err.println("Error in getFacultyByExp");
			e.printStackTrace();
			
		}
		return  text_body;
		
	}
	
	/**
	 * Get a list of all the departments
	 * @return All the department names as an array of strings
	 */
	public List<String> getAllDept() {
		ResultSet result = null;
		final String str =  "Select distinct faculty_uwt.Department from Faculty_UWT";
		final List<String> list = new ArrayList<String>();
				 
		try {
			PreparedStatement statement = my_con.prepareStatement(str);
			
			result = statement.executeQuery();
					
			while(result.next()) {
				list.add(result.getString(1));
				
			}
			
		} catch (SQLException e) {
			System.err.println("Error in getAllDept");
			e.printStackTrace();
		}
	
		
		return list;
		
	}


}

