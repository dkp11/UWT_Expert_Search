<!-- Expert Search User Interface 
	 TCSS 554 A
	 Created by group: Sagar Kamboj, Sanjeev Kamboj, Dineshkumar Prasath-->
	 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@page import="search.RunProgram"%>
<%@page import="search.Faculty"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>UW Faculty Search</title>
		
		<!-- Links css pages and imports required libraries-->
		<link href="search.css" type="text/css" rel="stylesheet">		
		<script src="https://ajax.googleapis.com/ajax/libs/prototype/1.7.0.0/prototype.js" type="text/javascript"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/scriptaculous/1.9.0/scriptaculous.js" type="text/javascript"></script>
		<script src="https://www.cs.washington.edu/education/courses/cse190m/12sp/homework/9/provided.js" type="text/javascript"></script>
		<meta name="ROBOTS" content="NOINDEX, NOFOLLOW" />
	</head>
	<body>
	<div class = "body">
		<div class="headfoot">
			<h1>
				<img src="http://www.oceannetworks.ca/sites/default/files/styles/thumbnail/public/uw_seal.gif?itok=PviZdzPz" alt="logo" >
				UW Expert Search
			</h1>
		</div>			
	<div id="tfheader">
		<form id="tfnewsearch" name = "se">
				<!-- Displays the textbox with categorical choices-->
				<select name="t" id ="t">
		  			<option value="name">Name</option>
		  			<option value="department">Department</option>
		 			<option value="expertise">Expertise</option>
		 			<option value="email">Email</option>
		 			<option value="netid">UW Net ID</option>
		 			<option value="telephone">Phone Number</option>
				</select>		
		        <input type="text" placeholder="Please enter the query" class="tftextinput" name="s" size="21" maxlength="120">
		        <input type="submit" name = "but" value="search" class="tfbutton">
		</form>
	
	<!-- Style for each panel -->
	<div style="style="color: #ffffff;
	background-color: #000000;
	opacity: .8;
	text-align: center;
	border-collapse: collapse;
	width: 100%;"">
	<%
	
				   if((request.getParameter("s") != null)) //search box contains something
				   {
					  
			          String t = request.getParameter("t"); //get dropdown box value
			     	  String tex = request.getParameter("s"); // get search textbox value
			     	  
			     	  RunProgram r = new RunProgram();
			     	  List<Faculty> recs = r.runProgram(t, tex); //pass two values into object to query in database.
  
			 		  if(recs.size() != 0) //if the list of data contained is 0
			 		  {
			 		   for(int i = 0; i< recs.size(); i++)
			 		   {
			 			   //get each faculty object returned
			 			   Faculty f = recs.get(i);
			 			   String LName = f.getFirst_name();
			 			   String FName = f.getLast_name();
			 			   String Link = f.getLink();
			 			   String dept = f.getDepartment();
			 			   String intro = "";

			 			   //check to see if intro exists
			 			   int intro_exists = f.getInfoExists();
			 			   //check to see if picture exists
			 			   int pic_exists = f.getPictureExists();
			 			   int number = -1;
			 			   
			 			   //see if an image exists
			 			   if(pic_exists == 1) {
				 			   number = f.getID();

			 			   }
			 			   else
			 			   {
				 			   number = -1;

			 			   }
			 			   
			 			   //see if description exists
			 			   if(intro_exists == 1) {
			 				   
			 				   intro = f.getInfoText();
			 				   if(intro.length() > 100)
			 				   {
			 					   intro = intro.substring(0, 100);
			 				   }
		
			 			   }
			 			   else
			 			   {
			 				   intro = "No description available";
			 			   }
     %>
			 			<!--Make the whole panel a link and display with according styles-->	
			 			<a href="<%=Link%>">			 			
				 			<p style="color: #ffffff;
				 			   float: right;
							   background-color: #000000;
							   opacity: 1;
							   border-collapse: collapse;
							   text-align:left;
							   width: 100%;">
				 			
				 				<!-- Display image with it's proper id -->
				 			    <img src="Images/<%=number%>.jpg"  style="float:left; margin:0 5px 0 150px; padding-right: 20px; width:155px; height:183px; opacity: 1;">
				 			
				 				<!-- Display expert information -->
				 				<span style ="font-size: 24px; color:#999933;"><%=FName%> <%=LName%></span>
				 				<br>
				 				<span style="color: #6f46be"><%=Link%></span>
				 				<br>
				 				<%=dept%>
				 				<br>
				 				<%=intro%>
				 				<br>
				 			</p>
			 			</a>
			 
			 		 <%}
			 		 }
			 		 else
			 		 { %>
			 		 	<!-- Did not find a result. Tell user to try again -->
			 			 <p style="color: #ffffff;
				 			   float: right;
							   background-color: #000000;
							   opacity: 1;
							   border-collapse: collapse;
							   text-align:left;
							   width: 100%;"> Did not find results. Please try again</p>
			 			 <%
			 		 }
				   }
		 		  
		 		%>
	</div>
	</div>
	</div>
  </body>
</html>		