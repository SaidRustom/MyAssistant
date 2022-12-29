import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * this class contains methods to perform 
 * queries on MySQL server. Prints queries results to console. 
 * @author Said
 *
 */
public class Queries implements Print {
	String url = "jdbc:mysql://localhost:3306/schedulebuilder";
	String username = "root";
	String password = "said123123";
	

	/**
	 * this method takes a table as a parameter
	 * and prints a list of existing items in the table ((id, name) columns)
	 * @param tableName
	 */
	public void printList(String table) {
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select name, id from "+table+";";
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(" ID  -   "+table);
		    System.out.println("_______________________");
		    while (rs.next()) {
		    	
		    	System.out.println(" "+Print.padString(rs.getString(2), 10)  + (rs.getString(1)));
		    }
		    System.out.println();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
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
		    		+ " WHERE id = "+id+" ;";
		    String query1 = "delete from assignments where id = " + id ;
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
				if (e.equals(null))
					System.out.println(Print.printGreen("Deadline changed!"));
			}	
	}
	
	/**
	 * this method retrieves assignment grades from the database.
	 * @param table
	 */
	public void printGrades(String table) {	
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select "+table+".id, "+ table+".name, courses.name, "+table+".grade, "+table+".receivedgrade " 
		    		 +"from "+table+" left join courses on "+table+".courseid = courses.id"
		    				+ " order by grade desc;" ;
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(Print.padString("  ID",10) + Print.padString("Assignment",20) + Print.padString("Course",15) 
		    	+Print.padString("Weight(%)", 15) +  Print.padString("Received Grade", 10)   );
		    System.out.println("______________________________________________________________________________");
		    while (rs.next()) {	
		    	System.out.println(Print.padString("  " +rs.getString(1), 10)  + Print.padString(rs.getString(2), 20 ) 
		    		+Print.padString(rs.getString(3),15) + Print.printPurple(Print.padString("%"+ rs.getString(4), 15) )
		    		+Print.printBlue("%"+rs.getString(5)));
		    	System.out.println();
		    }
		    System.out.println();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Prints not submitted assignments to console with the number of days left to deadline
	 * Print assignments with less than 4 days to deadline in red.
	 * @param table the name of the table in DB (completedAssignments)
	 */
	public void printNotSubmittedAssignments(String table) {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select "+table+".name, "+table+".grade, "
		    		+ "courses.name, datediff(deadline, curdate()) as date from "+table+" left join courses on "
		    		+table+".courseid = courses.id" + " order by date asc;" ;
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(Print.padString(" Assignment",18) +Print.padString("Course",20) + Print.padString("Weight(%)",15) 
		    	+Print.padString("DaysLeft", 20));
		    System.out.println("_____________________________________________________________________");
		    while (rs.next()) {
		    	if ((rs.getInt(4)) >= 3) {
		    		System.out.println(Print.padString(rs.getString(1), 19) + Print.padString(rs.getString(3),20)
		    			+Print.padString("%"+rs.getString(2),15)+rs.getString(4) );
		    		System.out.println();
		    	}else {
		    		System.out.println(Print.padString(rs.getString(1), 19) + Print.padString(rs.getString(3),20)
		    			+Print.padString("%"+rs.getString(2),15)+Print.printRed(rs.getString(4)));
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
		System.out.println(Print.printGreen("added into "+table +"!"));
	
	}
	/**
	 * this method is used to create an assignment in the database
	 * @param table
	 * @param name
	 * @param deadline
	 * @param courseId
	 * @param grade
	 */
	public void insertIntoDatabase(String table, String name, String deadline, int courseId, double grade) {
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "insert into "+table+" (name, grade, deadline, courseId)"
		    		+ " values ('"+name +"' , " + grade + ", '" + deadline + "' ," + courseId +");";
		    Statement st = connection.createStatement();
		    st.executeUpdate(query);
		    }
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(Print.printGreen("added into "+table +"!"));
	} 
	
	/**
	 * adds a new class to the classes table.
	 * @param table
	 * @param courseid
	 * @param day
	 * @param time
	 */
	public void insertClassToDatabase(String table, int courseid, String day, String room, int time, int length) {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "insert into "+table+" (courseid, day, time, length, room)"
		    		+ " values ('"+courseid +"' , '" + day + "', '" + time + "', '"+length+"', '"+room+"' );";
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
	public void printCourseAssignments(int courseID) {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select name, id, grade, monthname(deadline), day(deadline) from assignments "
		    		+ "where courseid = " + courseID +";";
		    String query1 = "select name, id, grade, receivedGrade, monthname(deadline), day(deadline) from completedassignments "
		    		+ "where courseid = " + courseID +";";
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(Print.padString("", 3) + Print.padString("ID", 17) + Print.padString("Assignment", 20)+Print.padString("Grade", 20)
	    			+ Print.padString ("Received", 20)+ Print.padString("Deadline",20)+  Print.padString("Submitted",10));
		    System.out.println("__________________________________________________________________________________________________________");
		    while (rs.next()) {
		    	
		    	System.out.println(Print.padString("", 3) + Print.padString(rs.getString(2), 17) + Print.padString(rs.getString(1), 20)
		    		+Print.padString(rs.getString(3)+"%", 20) + Print.padString("", 20) 
		    		+Print.padString(rs.getString(4)+"-" +rs.getString(5),20)+"   NO" );
		    	System.out.println();
		    } rs = st.executeQuery(query1);
		    	while (rs.next()) {
		    	
		    	System.out.println(Print.padString("", 3) +Print.padString(rs.getString(2), 17) +Print.padString(rs.getString(1), 20)
		    		+Print.padString(rs.getString(3)+"%", 20)+Print.padString(rs.getString(4)+"%", 20)+ Print.padString(rs.getString(5)+ "-"
		    		+ rs.getString(6),20)+"   YES");
		    	System.out.println();
		    }
		    
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * prints a list of not submitted assignments for a course to console.
	 * @param courseID 
	 */
	public void printCourseNotSubmittedAssignments(int courseID) {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select name, id, grade, monthname(deadline), day(deadline) from assignments "
		    		+ "where courseid = " + courseID +";";
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(Print.padString("", 3) + Print.padString("ID", 17) +Print.padString("Assignment", 20)+Print.padString("Grade", 20)
	    			+Print.padString ("Received", 20)+Print.padString("Deadline",20)+Print.padString("Submitted",10));
		    System.out.println("__________________________________________________________________________________________________________");
		    while (rs.next()) {
		    	
		    	System.out.println(Print.padString("", 3) +Print.padString(rs.getString(2), 17) +Print.padString(rs.getString(1), 20)
		    		+Print.padString(rs.getString(3)+"%", 20) +Print.padString("", 20) 
		    		+Print.padString(rs.getString(4)+"-" +rs.getString(5),20)+"   NO" );
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
		    ResultSet rs = st.executeQuery(query);System.out.println();
		    System.out.println(Print.printBlue(Print.padString("  ID",10) +Print.padString("Hours",10)+ "Course Name"));
		    System.out.println(Print.printBlue("________________________________________"));
		    while (rs.next()) {
		    	System.out.println(Print.padString("",2) +Print.padString(rs.getString(1),9) +Print.padString(rs.getString(2),12) + rs.getString(3));
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
	/**
	 * @param table
	 * @param column
	 * @return The sum of a given column in a table
	 */
	public int getColumnSum(String table, String column) {
		int sum = 0;
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select sum("+column+") from " + table ;
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    while (rs.next()) {
		    	sum = (rs.getInt(1));      
		    	 
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
	/**
	 * prints a table to the console with the following columns:
	 * Possible, achieved, course, projectedGrade =>
	 * possible = possible (%) to achieve so far (depending on submitted assignments)
	 * achieved = achieved (%) in submitted assignments
	 * projectedGrade = depending on current performance. 
	 * @param id SemesterID
	 */
	public void printSemesterProgress(int id) {
		
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select * from `below85courses`;";
		    String query1 = "select * from `above85courses`;";
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(Print.padString("Possible",12) +Print.padString("Acheived",12) +Print.padString("Projected", 12) + "Course" );
		    System.out.println("_______________________________________________________");
		    	Course c = new Course();
		    	while (rs.next()) {
		    		System.out.println(Print.printGreen(Print.padString("",2) +Print.padString(rs.getString(1), 12) +Print.padString(rs.getString(2), 12) 
		    				+Print.padString(c.calculateLetterGrade(Double.parseDouble(rs.getString(4))),12)+ rs.getString(3)));
		    		System.out.println();
		    	}
		    	rs = st.executeQuery(query1);
		    	while(rs.next()) {
		    	
		    		System.out.println(Print.printRed(Print.padString("",2) +Print.padString(rs.getString(1), 12) +Print.padString(rs.getString(2), 12) 
		    				+Print.padString(c.calculateLetterGrade(Double.parseDouble(rs.getString(4))),12)+ rs.getString(3)));
		    		System.out.println();
		    	}
			}catch(SQLException e) {
			System.out.println(e.getMessage());
			}
	}
	/**
	 * prints all assignments due this week, as well as assignments worth more 10% due 2 weeks.
	 */
	public void printWeekAssignments() {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select * from weekassignments;";
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println("  "+Print.padString("Assignment", 20) +Print.padString("Weight",10) +Print.padString("daysLeft",17) + "Course");
		    System.out.println("___________________________________________________________");
		    while (rs.next()) {
		    	if ((rs.getInt(3)) < 2) {
		    		System.out.println("  "+Print.printRed(Print.padString(rs.getString(1), 22) +Print.padString("%"+rs.getString(2), 11) 
		    			+Print.padString(rs.getString(3), 15) + rs.getString(4)));
		    	}else
		    	System.out.println("  "+Print.padString(rs.getString(1), 22) +Print.padString("%"+rs.getString(2), 11) 
		    		+Print.padString(rs.getString(3), 15) + rs.getString(4));
		    	System.out.println();
		    }
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			}
	}
	/**
	 * creates an ArrayList of week's classes.
	 * @return ArrayList <Class>
	 */
	public ArrayList<Task> getWeekClasses() {
		ArrayList <Task> tasks = new ArrayList<>();
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			String query = "select classes.time, current_semester_courses.name, classes.day, classes.length, classes.room from current_semester_courses"
					+ " inner join classes on classes.courseid = current_semester_courses.id;";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				int time = rs.getInt(1);
				String name = (rs.getString(2)+" ("+rs.getString(5)+")");
				String day = rs.getString(3).toUpperCase();
				int length = (rs.getInt(4));
				String room = rs.getString(5);
				Task task = new Task(day,name,time,length,room);
				tasks.add(task);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace(); 
		}return tasks;
	}
	/**
	 * A method used to find if a class is scheduled at a specific time. 
	 * @param day
	 * @param time
	 * @return true if no class is scheduled at the specified time. 
	 */
	public boolean checkNoClassAtTimeSlot(String day, int time) {
		boolean available = true;
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			String query = "select CLASSES.id from classes inner join current_semester_courses on "
					+ "current_semester_courses.id = classes.courseid "
					+ "where CLASSES.day = '"+day+"' and CLASSES.time = "+time +";";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				if (!rs.getString(1).equalsIgnoreCase(null)) {
				available = false;
				}
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}return available;
	}
	/**
	 * A method to find if a task is scheduled at a specific time
	 * @param date
	 * @param time
	 * @param length
	 * @return true if no task is scheduled at the specified time.
	 */
	public boolean checkNoTasksAtTimeSlot(String date, int time, int length) {
		boolean available = true;
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			for(int i= 0; i<length; i++) {
				String query = "select id from tasks where date = '"+date+"' and time = "+(time+i)+";";
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(query);
				while(rs.next()) {
					if (!rs.getString(1).equalsIgnoreCase(null)) {
					available = false;
					}
				}
			}		
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}return available;
	}
	/**
	 * Creates an ArrayList<Task> of the week
	 * @return
	 */
	public ArrayList<Task> getWeekTasks(){
		ArrayList <Task> tasks = new ArrayList<>();
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			String query = "select tasks.name, tasks.time, tasks.day, tasks.length from tasks"
					+ " where day is not null and datediff(date, curdate()) < 8 and datediff(date,curdate()) > -1 "
					+ "or day is not null;";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				int time = Integer.parseInt(rs.getString(2));
				String name = rs.getString(1);
				String day = rs.getString(3).toUpperCase();
				int length = Integer.parseInt(rs.getString(4));
				for (int i = 0; i<length; i++) {
				Task task = new Task(day,name,(time+i),length);
				tasks.add(task);
				}
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace(); 
		}return tasks;
	}
	/**
	 * Calls "assignments_to_tasks() in the database.
	 * basically creating tasks from assignments table 
	 * where deadline is 14 or less days
	 * @param days number of days left to deadline
	 */
	public void createTasksFromAssignments(){
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			String query = "Call assignments_to_tasks();";
			Statement st = connection.createStatement();
			st.executeUpdate(query);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace(); 
		}
	}
	/**
	 * A method to print the tasks in the tasks table 
	 */
	public void printTasks() {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select tasks.id, tasks.name, tasks.length, tasks.day, tasks.time, tasks.date, courses.name from tasks"
		    		+ " left join assignments on tasks.assignmentid = assignments.id "
		    		+ "left join courses on assignments.courseid = courses.id where tasks.assignmentid != 'UNSET';";
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println("TASKS:" + Print.printGreen("(Green colored tasks are not school related tasks)\n"));
		    System.out.println(Print.padString("TaskID", 10) + Print.padString("Name",30) + Print.padString(" Length",10) 
		    	+ Print.padString(" Day       Time", 18) + Print.padString("date", 8)) ;
		    System.out.println("__________________________________________________________________________");
		    while (rs.next()) {
		    	System.out.println(Print.padString(rs.getString(1),10) +Print.padString(rs.getString(2).toUpperCase()
		    		+" ("+rs.getString(7)+")".toUpperCase(),30) 
		    		+Print.padString(rs.getString(3)+ " hours", 10) 
		    		+Print.padString(rs.getString(4).toUpperCase(),12) +Print.padString(rs.getString(5),5)+Print.padString(rs.getString(6),8));
		    } query = "select id, name, length, day, time, date from tasks where assignmentid is null;";
		    rs = st.executeQuery(query);
		    while (rs.next()) {
		    	if (rs.getString(1) != null) {
		    	System.out.println(Print.printGreen(Print.padString(rs.getString(1),10) +Print.padString(rs.getString(2).toUpperCase(),30) 
		    		+Print.padString(rs.getString(3)+ " hours", 10) 
		    		+Print.padString(rs.getString(4).toUpperCase(),12) +Print.padString(rs.getString(5),5) 
		    		+Print.padString(rs.getString(6),8)));
		    }}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
	    }catch(Exception e) {
	    	System.out.println(e.getMessage());
	    }
	}
	/**
	 * Prints to console a table of tasks completed within the last 7 days.
	 */
	public void PrintLastWeekTasks() {
		try (Connection connection =DriverManager.getConnection(url, username, password)) {
		    String query = "select * from completedtasks where datediff(curdate(), time) > -8;";
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    System.out.println();
		    System.out.println(Print.padString(" TaskID",10) +Print.padString("Name",25) +Print.padString("AssignmentID", 15) + 
		    		Print.padString("Length", 15)+ "Date Submitted" );
		    System.out.println("_____________________________________________________________________________");
		    	Course c = new Course();
		    	while (rs.next()) {
		    		System.out.println(Print.printGreen(Print.padString("",2) +Print.padString(rs.getString(1), 10) +Print.padString(rs.getString(2), 25) 
		    				+Print.padString(rs.getString(3), 15) +Print.padString(rs.getString(4), 15) + rs.getString(5)));
		    		System.out.println();
		    	}
			}catch(SQLException e) {
			System.out.println(e.getMessage());
			}
	}
	/**
	 * A method to change task given length (hours)
	 * @param id taskID
	 * @param length new task given length (in hours)
	 */
	public void changeTaskLength(int id, int length) {
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			String query = "update tasks set length = "+length+", edited = 'YES' where id = "+id+";" ;
			Statement st = connection.createStatement();
			st.executeUpdate(query);
			System.out.println(Print.printGreen("Task length updated!\nTaskID: " + id + "\nNew assigned time: " 
					+length+" hours\n"));
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace(); 
		}
	}
	/**
	 * A method to change a task time (for schedule)
	 * @param id TaskID
	 * @param time task assigned hour
	 * @param day task assigned day
	 */
	public void changeTaskDateTime(int id, String date , int time) {
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			String query = "update tasks set date = '"+date+"' , time = " + time + ", edited = 'YES' where id = " + id +";";
			Statement st = connection.createStatement();
			st.executeUpdate(query);
			System.out.println(Print.printGreen("\n Task day and time updated!") +"\n TaskID: " + id + "\n New date: "+ date+"\n New time: " + time +"\n");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace(); 
		}
	}
	/**
	 * a method to delete a task from DB
	 * @param id
	 */
	public void deleteTask(int id) {
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			String query = "delete from tasks where id = " + id +";";
			Statement st = connection.createStatement();
			st.executeUpdate(query);
			System.out.println(Print.printGreen("Task deleted!"));
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace(); 
		}
	}
	/**
	 * A method to move a task from tasks to completedTasks in DB 
	 * (for school assignments)
	 * @param id
	 * @param assignmentid
	 * @param length
	 * @param name
	 */
	public void completeTask(int id, int assignmentid, int length, String name) {
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			String query = "insert into completedtasks (id, name, assignmentid, length) values (" +id + ", '"+name
							+ "' ,"+assignmentid+" ,"+length+");"; 
			Statement st = connection.createStatement();
			st.executeUpdate(query);
			System.out.println("Task '"+ name +"' completed!");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace(); 
		}
	}
	/**
	 * A method to move a task from asks to completedTasks in DB
	 * (for non-school related tasks)
	 * @param id
	 * @param length
	 * @param name
	 */
	public void completeTask(int id, String name) {
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			String query = "DELETE FROM TASKS WHERE ID = "+id+";";
			Statement st = connection.createStatement();
			st.executeUpdate(query);
			System.out.println(Print.printGreen("Task '"+ name +"' marked as completed!"));
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace(); 
		}
	}
	/**
	 * Adds a new task to tasks table with a chosen time
	 * @param name
	 * @param day
	 * @param time
	 * @param length
	 */
	public void addTask(String name, String day, int time, String date, int length) {
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			String query = "insert into tasks (name, day, time, length, date, edited) values ('" +name 
							+ "', '"+day+ "', "+time+", "+length+", '"+date+"', 'YES' );"; 
			Statement st = connection.createStatement();
			st.executeUpdate(query);
			System.out.println(Print.printGreen("Task added!"));
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace(); 
		}
	}
	/**
	 * adds a new task to tasks table with a deadline,
	 * to be used by user when adding a task with unknown exact time.
	 * @param name
	 * @param length
	 * @param deadlineMonth
	 * @param deadlineDay
	 */
	public void addTask(String name, String deadline, int length) {
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			String query = "insert into tasks (name, deadline, length, edited ) values ('" +name 
							+ "', '"+deadline +"', "+length+", 'YES');"; 
			Statement st = connection.createStatement();
			st.executeUpdate(query);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace(); 
		}
	}
	/**
	 * Creates an ArrayList<Course> for current semester
	 * @return ArrayList<Course> for current semester.
	 */
	public ArrayList<Course> getSemesterCourses() {
		ArrayList<Course> courses = new ArrayList<>();
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			String query = "select id, name, hours from current_semester_courses; ";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				int id = (rs.getInt(1));
				String name = (rs.getString(2));
				int hours = rs.getInt(3);
				Course course = new Course(id,name,hours);
				courses.add(course);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace(); 
		}return courses;
	}
}

	

	

	
	


