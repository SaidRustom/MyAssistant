import java.sql.*;
/**
 * this abstract class contains methods to perform 
 * queries on MySQL server.
 * @author Said
 *
 */
public abstract class Queries {
	String url = "jdbc:mysql://localhost:3306/schedulebuilder";
	String username = "root";
	String password = "said123123";
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
		    	
		    	System.out.println(rs.getString(2) + "   -    " + (rs.getString(1)));
		    }
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
			    st.executeUpdate(query);
			    }
			catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Deadline changed!");
	}
	
	/**
	 * this method retrieves assignment grades from the database.
	 * @param table
	 */
	public void printGrades(String table) {
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select "+table+".name, "+table+".grade, "+table+".receivedgrade,"
		    		+ "courses.name from "+table+" left join courses on "+table+".courseid = courses.id"
		    				+ " order by grade desc;" ;
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(" Weight(%) -  Received Grade  -   "+table  );
		    System.out.println("_____________________________________________________________");
		    while (rs.next()) {
		    	
		    	System.out.println("   "+(rs.getString(2)) + "           "+rs.getString(3)+"%              " + rs.getString(1)+" (" + rs.getString(4) +")");
		    }
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void printGradesDeadline(String table) {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select "+table+".name, "+table+".grade, "
		    		+ "courses.name, datediff(deadline, curdate()) from "+table+" left join courses on "+table+".courseid = courses.id"
		    				+ " order by grade desc;" ;
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(" Weight(%) -     DaysLeft    -   "+table  );
		    System.out.println("_____________________________________________________________");
		    while (rs.next()) {
		    	
		    	System.out.println("   "+(rs.getString(2)) +"              "+rs.getString(4)+"           " + rs.getString(1)+" (" + rs.getString(3) +")");
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
		    System.out.println("ID  -   Assingnment   -   Grade    -   Deadline    -    Submitted");
		    System.out.println("_________________________________________________________________");
		    while (rs.next()) {
		    	
		    	System.out.println((rs.getString(2)) + "      "+rs.getString(1)+"             " +rs.getString(3)+"%"
		    			+ "              " + rs.getString(4)+  rs.getString(5)+"         NO");
		    } rs = st.executeQuery(query1);
		    	while (rs.next()) {
		    	
		    	System.out.println((rs.getString(2)) + "      "+rs.getString(1)+"             " +rs.getString(3)+"%"
		    			+ "              " + rs.getString(4)+  rs.getString(5)+"          YES");
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
		    System.out.println("Course ID - Course Hours  -   Course Name");
		    System.out.println("________________________________________");
		    while (rs.next()) {
		    	System.out.println("# " + rs.getString(1) + "       -    " + rs.getString(2) + "         - " + rs.getString(3));
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
		    String query = "select sum(completedassignments.grade), sum(completedassignments.receivedgrade), courses.name"
		    		+ " from completedassignments left join courses on courses.id = completedassignments.courseId "
		    		+ "where completedassignments.receivedgrade is not null and courses.semesterid = "+id+" group by courses.name";
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println("Total Grade -  Acheived Grade -  Course");
		    System.out.println("______________________________________________");
		    while (rs.next()) {
		    	
		    	System.out.println(rs.getString(1) + "          -       " + (rs.getString(2)) + "          -   "+(rs.getString(3)));
		    }
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/*public void setCourseGrade(int id, String letterGrade) {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "update courses into "+table+" (name, startdate, enddate)"
		    		+ " values ('"+name +"' , '" +startDate+"', '"+endDate+"' );";
		    Statement st = connection.createStatement();
		    st.executeUpdate(query);
		    }
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}*/
}


	
	
	


