import java.sql.*;
/**
 * this abstract class contains methods to perform 
 * queries on MySQL server.
 * @author Said
 *
 */
public class Queries {
	String url = "jdbc:mysql://localhost:3306/schedulebuilder";
	String username = "root";
	String password = "said123123";
	
	/**
	 * Prints entered String in red.
	 * @param str
	 * @return
	 */
	public final String printRed(String str) {
		final String ANSI_RESET = "\u001B[0m";
		final String ANSI_RED = "\u001B[31;1m";
		 str = (ANSI_RED + str + ANSI_RESET );
		return str;
	}
	public final String printGreen(String str) {
		final String ANSI_RESET = "\u001B[0m";
		final String ANSI_GREEN = "\u001b[32m";
		str = (ANSI_GREEN + str + ANSI_RESET);
		return str;
	}
	public final String printBlue(String str) {
		final String ANSI_BLUE = "\u001b[36m";
		final String ANSI_RESET = "\u001B[0m";
		str = (ANSI_BLUE + str + ANSI_RESET);
		return str;
	}
	public final String printPurple(String str) {
		final String ANSI_PURPLE = "\u001b[35m";
		final String ANSI_RESET = "\u001B[0m";
		str = (ANSI_PURPLE + str + ANSI_RESET);
		return str;
	}
		/**
		 * String padding method, takes string and length as parameters.
		 * @param str
		 * @param leng
		 * @return return padded String.
		 */
	
	public static String padString(String str, int leng) {
		if (str != null) {
        for (int i = str.length(); i < leng ; i++)
            str += " ";
		}else {
			str = "";
			for (int i = 0; i < leng ; i++) {
				str += " ";
			}
		}
        return str;
    }
	/**
	 * this method takes a table as a parameter
	 * and prints a list of existing items in the table
	 * @param tableName
	 */
	public void printList(String table) {
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select name, id from "+table+";";
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println("ID  -  "+table);
		    System.out.println("______________________");
		    while (rs.next()) {
		    	
		    	System.out.println(padString(rs.getString(2), 10)  + (rs.getString(1)));
		    }
		    System.out.println();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * this method is used to create assignment field in the database
	 * @param table
	 * @param name
	 * @param deadline
	 * @param courseId
	 * @param grade
	 */
	public void insertIntoDatabase(String table, String name, String deadline, String courseId, double grade) {
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "insert into "+table+" (name, grade, deadline, courseId)"
		    		+ " values ('"+name +"' , " + grade + ", '" + deadline + "' ," + courseId +");";
		    Statement st = connection.createStatement();
		    st.executeUpdate(query);
		    }
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("added into "+table +"!");
	} //pushAssignment end.
	
	/**
	 * deletes a record from a table in the database.
	 * @param table
	 * @param id
	 */
	public void deleteRecord(String table, int id) {
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "delete from "+table+" where id = "+ id+";" ;
		    Statement st = connection.createStatement();
		    st.executeUpdate(query);
		    System.out.println("Record deleted!");
		    }
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * this method moves an assignment to completed assignments in the database.
	 * @param id
	 */
	public void submitAssignment(int id) {
		
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = 
		    		 "INSERT INTO completedassignments (id, name, grade, deadline, courseId)"
		    		+ " SELECT id, name, grade, deadline, courseId"
		    		+ " FROM assignments"
		    		+ " WHERE id = '"+id+"' ;";
		    String query1 = "delete from assignments where id = " + id;
		    Statement st = connection.createStatement();
		    st.executeUpdate(query);
		    st.executeUpdate(query1);
		    }
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	/**
	 * this method changes the deadline of an assignment or a task in the database.
	 * @param table
	 * @param id
	 * @param deadline
	 */
	public void changeDeadline(String table, int id, String deadline) {
			
			try (Connection connection =DriverManager.getConnection(url, username, password)) {
			    String query = "update "+table + " set deadline = '" +deadline + "' WHERE id = '" + id +"';";
			    Statement st = connection.createStatement();
			    System.out.println(query);
			    st.executeUpdate(query);
			    }
			catch(SQLException e) {
				if (e != null)
					System.out.println(e.getMessage());
				else
					System.out.println("Deadline changed!");
			}
			
			
	}
	
	/**
	 * this method retrieves assignment grades from the database.
	 * @param table
	 */
	public void printGrades(String table) {
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select "+table+".id, "+ table+".name, courses.name, "+table+".grade, "+table+".receivedgrade " +
		    		 "from "+table+" left join courses on "+table+".courseid = courses.id"
		    				+ " order by grade desc;" ;
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(padString("  ID",10) + padString("Assignment",20) + padString("Course",15) +padString("Weight(%)", 15) +  padString("Received Grade", 10)   );
		    System.out.println("______________________________________________________________________________");
		    while (rs.next()) {
		    	
		    	System.out.println(padString("  " +rs.getString(1), 10)  + padString(rs.getString(2), 20 ) + padString(rs.getString(3),15) + printPurple(padString("%"+ rs.getString(4), 15) )
		    	+ printBlue("%"+rs.getString(5)));
		    	System.out.println();
		    }
		    System.out.println();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void printGradesDeadline(String table) {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select "+table+".name, "+table+".grade, "
		    		+ "courses.name, datediff(deadline, curdate()) as date from "+table+" left join courses on "+table+".courseid = courses.id"
		    				+ " order by date asc;" ;
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(padString("Assignment",18) +padString("Course",15) + padString("Weight(%)",15) +padString("DaysLeft", 20));
		    System.out.println("_____________________________________________________________________");
		    while (rs.next()) {
		    	if (Integer.parseInt(rs.getString(4)) >= 3) {
		    		System.out.println(padString(rs.getString(1), 19) + padString(rs.getString(3),20)+ padString("%"+rs.getString(2),15)+rs.getString(4) );
		    		System.out.println();
		    	}else {
		    		System.out.println(padString(rs.getString(1), 19) + padString(rs.getString(3),20)+ padString("%"+rs.getString(2),15)+printRed(rs.getString(4) ));
		    		System.out.println();
		    	}
		    }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * changes received grade for an assignment in the database.
	 * @param id
	 * @param grade
	 */
	public void updateReceivedGrade(int id, double grade){
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "update completedassignments set receivedgrade = '" +grade + "' WHERE id = '" + id +"';";
		    Statement st = connection.createStatement();
		    st.executeUpdate(query);
		    }
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("acheived grade updated!");
	}
	/**
	 * adds a course to the database
	 * @param table
	 * @param name
	 * @param hours
	 * @param semesterId
	 */
	public void insertIntoDatabase(String table, String name, int hours, int semesterId) {
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "insert into "+table+" (name, hours, semesterId)"
		    		+ " values ('"+name +"' , " + hours + ", '" + semesterId + "');";
		    Statement st = connection.createStatement();
		    st.executeUpdate(query);
		    }
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("added into "+table +"!");
	
	}
	/**
	 * adds a semester to the database
	 * @param table
	 * @param name
	 */
	public void insertIntoDatabase(String table, String name, String startDate, String endDate) {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "insert into "+table+" (name, startdate, enddate)"
		    		+ " values ('"+name +"' , '" +startDate+"', '"+endDate+"' );";
		    Statement st = connection.createStatement();
		    st.executeUpdate(query);
		    }
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("added into "+table +"!");
	}
	/**
	 * prints the assignments of a course.
	 * @param id
	 */
	public void printCourseAssignments(int id) {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select name, id, grade, monthname(deadline), day(deadline) from assignments "
		    		+ "where courseid = " + id +";";
		    String query1 = "select name, id, grade, monthname(deadline), day(deadline) from completedassignments "
		    		+ "where courseid = " + id +";";
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(padString("", 3) + padString("ID", 17) + padString("Assignment", 20)+padString("Grade", 20)
	    			+ padString("Deadline",20)+  padString("Submitted",10));
		    System.out.println("_____________________________________________________________________________________________________");
		    while (rs.next()) {
		    	
		    	System.out.println(padString("", 3) + padString(rs.getString(2), 17) + padString(rs.getString(1), 20)+padString(rs.getString(3)+"%", 20)+
		    			 padString(rs.getString(4) +"-" +rs.getString(5),20)+"   NO" );
		    	System.out.println();
		    } rs = st.executeQuery(query1);
		    	while (rs.next()) {
		    	
		    	System.out.println(padString("", 3) + padString(rs.getString(2), 17) + padString(rs.getString(1), 20)+padString(rs.getString(3)+"%", 20)
		    			+ padString(rs.getString(4)+ "-"+ rs.getString(5),20)+"   YES");
		    	System.out.println();
		    }
		    
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * prints the courses and the number of courses of a semester.
	 * @param semesterId
	 */
	public void getCourses(int semesterId) {
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
			String query = "Select id, hours, name from courses where semesterid = " + semesterId;
		    String query1 = "select count(*) from courses where semesterid = " + semesterId;
		    String query2 = "select sum(hours) from courses where semesterid = " + semesterId;
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println(padString("  ID",10) + padString("Hours",10)+ "Course Name");
		    System.out.println("________________________________________");
		    while (rs.next()) {
		    	System.out.println(padString("",2) + padString(rs.getString(1),9) + padString(rs.getString(2),12) + rs.getString(3));
		    	System.out.println();
		    }
		    System.out.println("________________________________________");
		    rs = st.executeQuery(query1);
		    System.out.print("Number of courses this semester: ");
		    while (rs.next()) {
		    	
		    	System.out.println(rs.getString(1));
		    }
		    rs = st.executeQuery(query2);
		    System.out.print("Total course hours this semester: ");
		    while (rs.next()) {
		    	System.out.println(rs.getString(1));
		    }
		    System.out.println("________________________________________");
		    
		    
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public int getColumnSum(String table, String column) {
		int sum = 0;
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select sum("+column+") from " + table ;
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    while (rs.next()) {
		    	sum = Integer.parseInt(rs.getString(1));      
		    	 
		    }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return sum;
	
	} 
	
	/**
	 * 
	 * @param table
	 * @param id
	 * @return Number of days left for a record to expire (assuming it has an end date).
	 */
	public int getDayDiff(String table,int id) {
		int dayDiff = 0;
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select datediff(enddate, curdate()) from " + table +" where id = " +id ;
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    while (rs.next()) {
		    	dayDiff = Integer.parseInt(rs.getString(1)); 
		    	
		    }
		}catch(SQLException e) {
				System.out.println(e.getMessage());
		}
		return dayDiff;
	}
	
	public void printSemesterProgress(int id) {
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select * from `85<courses`;";
		    String query1 = "select * from `85>courses`;";
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(padString("Possible",12) + padString("Acheived",12) +  padString("Projected", 12) + "Course" );
		    System.out.println("_______________________________________________________");
		    	Course c = new Course();
		    	while (rs.next()) {
		    		System.out.println(printGreen(padString("",2) + padString(rs.getString(1), 12) + padString(rs.getString(2), 12) +
		    				padString(c.calculateLetterGrade(Double.parseDouble(rs.getString(4))),12)+ rs.getString(3)));
		    		System.out.println();
		    	}
		    	rs = st.executeQuery(query1);
		    	while(rs.next()) {
		    	
		    		System.out.println(printRed(padString("",2) + padString(rs.getString(1), 12) + padString(rs.getString(2), 12) +
		    				padString(c.calculateLetterGrade(Double.parseDouble(rs.getString(4))),12)+ rs.getString(3)));
		    		System.out.println();
		    	}
			}catch(SQLException e) {
			System.out.println(e.getMessage());
			}
	}
	public void printWeekAssignments() {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select * from weekassignments;";
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(padString("Assignment", 20) + padString("Weight",10) + padString("daysLeft",17) + "Course");
		    System.out.println("___________________________________________________________");
		    while (rs.next()) {
		    	if (Integer.parseInt(rs.getString(3)) < 2) {
		    		System.out.println(printRed(padString(rs.getString(1), 22) + padString("%"+rs.getString(2), 11) + padString(rs.getString(3), 15) + rs.getString(4)));
		    	}else
		    	System.out.println(padString(rs.getString(1), 22) + padString("%"+rs.getString(2), 11) + padString(rs.getString(3), 15) + rs.getString(4));
		    	System.out.println();
		    }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			}
	}
	
}
	

	
	


