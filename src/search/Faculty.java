//Information Retrieval

package search;

/**
 * Faculty object that stores information for a faculty member
 * Group: Sagar Kamboj, Dinesh Prasad, Sanjeev Kamboj
 * 
 * @author Sanjeev Kamboj
 */
public class Faculty {
	/** Faculty id*/
	private int id;
	
	/** Faculty netid*/
	private String net_id;
	
	/** First name of faculty*/
	private String first_name;
	
	/** last name of faculty*/
	private String last_name;
	
	/** Faculty position */
	private String position;
	
	/** Faculty department*/
	private String department;
	
	/** Faculty phone number*/
	private String phone;
	
	/** Faculty email*/
	private String email;
	
	/** link of the faculty homepage*/
	private String link;	
	
	/** Faculty intro*/
	private String my_info_text;
	
	/** If the intro exists*/
	private int my_info_exists;
	
	/** If the picture exists*/
	private int my_picture_exists;

	/** Constructor */
	public Faculty(final int the_id, final String the_netId, final String the_fn,
		      final String the_ln, final String the_pos, final String the_dep,
		      final String the_phone, final String the_email, final String the_link, 
		      final int the_info_exists, final String the_info_text, 
		      int the_picture_exists) {	
		id = the_id;
		net_id = the_netId;
		first_name = the_fn;
		last_name = the_ln;
		position = the_pos;
		department = the_dep;
		phone = the_phone;
		email = the_email;
		link = the_link;
		my_info_text = the_info_text;
		my_picture_exists = the_picture_exists;
		my_info_exists = the_info_exists;
	}

	//Getter Methods
	public int getID() { return id; }
	
	public void setID(int idt) { id = idt; }
	
	public String getLink() { return link; }
	
	public String getNet_id() { return net_id; }
	
	public String getFirst_name() { return first_name; }
	
	public String getLast_name() { return last_name; }
	
	public String getPosition() { return position; }
	
	public String getDepartment() { return department; }
	
	public String getPhone() { return phone; }

	public String getEmail() { return email; }
	
	public String getInfoText() { return my_info_text; }
	
	public int getInfoExists() { return my_info_exists; }
	
	public int getPictureExists() { return my_picture_exists; }
	
	/**
	 * 
	 * @param idt - The other id to compare with
	 * @return A boolean for if the faculty are equal
	 */
	public Boolean compareTo(int idt) {
		if ( id == idt) return true;
		return false;
	}

}
