
import java.util.Scanner;
/**
 * Class Course contains methods to create/view/modify courses.
 * @author Said
 *
 */
public class Course implements Print  {
	 
	
	Scanner input = new Scanner(System.in);
	private int id;
	private String name;
	private int hours;
	private int grade; //(out of 100%)
	private double numericGrade; // (out of 4.0 to calculate GPA)
	private String letterGrade;
	private int semesterId;
	private String classDay;
	private int classTime;
	Queries queries = new Queries();
	
	public Course() {
	}
	public Course(int id, String name, int hours) {
		this.name = name;
		this.id = id;
		this.hours=hours;
	}
	/**
	 * Takes user input to add a course into DB
	 * Only asks for semesterID if static semesterID is not set.
	 * Calls Queries.printList("semesters"), Queries.insertIntoDatabase(), printCourseDetails()
	 */
	public void addCourse() {
		System.out.print("Enter course name: ");
		name = input.nextLine();
	
		System.out.print("Enter course total hours: ");
		hours = input.nextInt();
		if ( Test.semesterId == 0) {
			queries.printList("semesters");
			System.out.println("Enter semester ID: ");
			Test.semesterId = input.nextInt();
		}	
		printCourseDetails();
		System.out.print(Print.printBlue("\nAdd course?")+"\n (1)- Add course\n (2)- Enter course info again\n\n (0)- Courses menu\n ");
		System.out.print("   choice: ");
		int sure = input.nextInt();input.nextLine();
		if (sure == 1) {
			queries.insertIntoDatabase("courses", name, hours, Test.semesterId);
			System.out.print("\n Would you like to add class time?\n (1)-Add class time\n (2)-back to menu\n    choice: ");
			int choice = input.nextInt();input.nextLine();
			if (choice == 1) {
				addClass();
			}
		}else if (sure == 2) {
			addCourse();
		}else if (sure == 0) {
			Test.printCourseMenu();
		}else {
			System.out.println("invalid input, going back to courses menu...");
			System.out.println("");
		}
	}
	
	/**
	 * Prints the assignments of a specified course to console.
	 * Calls Queries.printList("courses"), Queries.printCourseAssignments()
	 */
	public void printCourseAssignments() {
		queries.printList("courses");
		System.out.println();
		System.out.print("Enter course ID: ");
		int id = input.nextInt();
		queries.printCourseAssignments(id);
		
	}
	/**
	 * Prints course details to console. 
	 */
	public void printCourseDetails() {
		System.out.println(Print.printBlue("\nCourse details: \n"));
		System.out.println("Course name: " + Print.printRed(name));
		System.out.println("Course hours: " + Print.printRed(String.valueOf(hours)));
		System.out.println("Semester ID = " + Print.printRed(String.valueOf(Test.semesterId)));
	}
	/**
	 * Takes user input to delete a course from DB
	 * Calls Queries.deleteRecord(), Queries.printList("courses")
	 */
	public void deleteCourse() {
		queries.printList("courses");
		System.out.println("Enter course ID to remove");
		int id = input.nextInt();
		System.out.println("Are you sure you want to delete course # " + id + "? ");
		System.out.println("Enter (1) to delete (2) to enter course id again (3) to go back to courses menu");
		int sure = input.nextInt();
		if (sure == 1) {
			queries.deleteRecord("courses", id);
		}else if (sure == 2) {
			deleteCourse();
		}else if( sure == 3) {
			Test.printCourseMenu();
		}else {
			System.out.println("invalid input...");
			deleteCourse();
		}
	}
	/**
	 * Takes user input to add a class into the DB
	 * Calls Queries.insertClasstoDatabase()
	 */
	public void addClass() {
		queries.printList("courses");
		System.out.print(" Which course do you want to add a class to? ");
		int id = input.nextInt();
		System.out.println("\n Day of class?");
		System.out.print(" (1)- Monday\n (2)- Tuesday\n (3)- Wednesday\n (4)- Thursday"
				+ "\n (5)- Friday\n (6)- Saturday\n (7)- Sunday\n       choice:  ");
		String day = input.next();
		switch(day){
		case "1": classDay = "Monday";break;	
		case "2": classDay = "Tuesday";break;	
		case "3": classDay = "Wednesday";break;	
		case "4": classDay = "Thursday";break;	
		case "5": classDay = "Friday";break;	
		case "6": classDay = "Saturday";break;	
		case "7": classDay = "Sunday";break;
		case "0": Test.printCourseMenu();break;
		default : System.out.println("Invalid input"); addClass();
		}
		System.out.print("\n Class room? ");
		String room = input.next(); input.nextLine();
		System.out.print(" Time of class?  ");
		int time = input.nextInt();
		System.out.print(" How many hours? ");
		int length = input.nextInt();
		System.out.println("\n Courseid: "+Print.printRed(""+id)+
				"\n class day: " +Print.printRed(classDay)+"\n class time: "+Print.printRed(""+time));
		System.out.print("\n (1)- Add class\n (0)- Back to menu: ");
		int choice = input.nextInt();
		if (choice == 1) {
			System.out.println();
			//adding classes record * length to the database.
			for(int i=0;i<length;i++) {
				queries.insertClassToDatabase("classes",id, classDay, room, time+i, length);
			}try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Test.printCourseMenu();
		}else {
			Test.printCourseMenu();
		}
	}

	public void setNumericGrade(double numericGrade) {
		this.numericGrade = numericGrade;
	}
	public int getHours() {
		return hours;
	}
	public int getGrade() {
		return grade;
	}
	public double getNumericGrade() {
		return numericGrade;
	}
	public void setGrade(int grade) {
		this.grade =+ grade;
	}
	public String getName() {
		return name;
	}
	/**
	 * Calculates the letter grade of a course
	 * @param grade Percentage (%)
	 * @return LetterGrade (Ex:B+)
	 */
	public String calculateLetterGrade(double grade) {
		if (grade > 89 && grade < 101) {
			letterGrade = "A+";
		}else if (grade >= 85 && grade < 90) {
			letterGrade = "A";
		}else if (grade >= 80 && grade < 85) {
			letterGrade = "A-";
		}else if (grade >= 77 && grade < 80) {
			letterGrade = "B+";
		}else if (grade >= 73 && grade < 77) {
			letterGrade = "B";
		}else if (grade >= 70 && grade < 73) {
			letterGrade = "B-";
		}else if (grade >= 67 && grade < 70) {
			letterGrade = "C+";
		}else if (grade >= 63 && grade < 67) {
			letterGrade = "C";
		}else if (grade >= 60 && grade < 63) {
			letterGrade = "C-";
		}else if (grade >= 57 && grade < 60) {
			letterGrade = "D+";
		}else if (grade >= 53 && grade < 57) {
			letterGrade = "D";
		}else if (grade >= 50 && grade < 53) {
			letterGrade = "D-";
		}else if (grade >= 00 && grade < 50) {
			letterGrade = "F";
		}else {
			letterGrade = "undefined";
		}
		return letterGrade;
	}
	/**
	 * method used for the GPA Calculator feature.
	 * @return Course's numericalGrade (out or 4.0).
	 */
	public double getGrades() {
		int percentGrade;
		double numericGrade = 0;
		if (input.hasNextInt()) {
			percentGrade = input.nextInt();
			if (percentGrade < 50) {
				letterGrade = "F";
			}else if ( 50 < percentGrade && percentGrade < 52){
				letterGrade = "D-";
			}else if (52 < percentGrade && percentGrade < 56 ) {
				letterGrade = "D";
			}else if (56 < percentGrade && percentGrade <59) {
				letterGrade = "D+";
			}else if (59 < percentGrade && percentGrade <62) {
				letterGrade = "C-";
			}else if (62 < percentGrade && percentGrade <66) {
				letterGrade = "C";
			}else if (66 < percentGrade && percentGrade <69) {
				letterGrade = "C+";
			}else if (69 < percentGrade && percentGrade <72) {
				letterGrade = "B-";
			}else if (72 < percentGrade && percentGrade <76) {
				letterGrade = "B";
			}else if (76 < percentGrade && percentGrade <79) {
				letterGrade = "B+";
			}else if (79 < percentGrade && percentGrade <84) {
				letterGrade = "A-";
			}else if (84 < percentGrade && percentGrade <89) {
				letterGrade = "A";
			}else if (89 < percentGrade && percentGrade <101) {
				letterGrade = "A+";
			}	
		}else {
			letterGrade = input.next();
		}
		try {
		if (letterGrade.equalsIgnoreCase("F")) {
			numericGrade = 0.0;
		}else if (letterGrade.equalsIgnoreCase("D-")) {
			numericGrade = 1;
		}else if (letterGrade.equalsIgnoreCase("D")) {
			numericGrade = 1.2;
		}else if (letterGrade.equalsIgnoreCase("D+")) {
			numericGrade = 1.4;
		}else if (letterGrade.equalsIgnoreCase("C-")) {
			numericGrade = 1.7;
		}else if (letterGrade.equalsIgnoreCase("C")) {
			numericGrade = 2;
		}else if (letterGrade.equalsIgnoreCase("C+")) {
			numericGrade = 2.3;
		}else if (letterGrade.equalsIgnoreCase("B-")) {
			numericGrade = 2.7;
		}else if (letterGrade.equalsIgnoreCase("B")) {
			numericGrade = 3;
		}else if (letterGrade.equalsIgnoreCase("B+")) {
			numericGrade = 3.3;
		}else if (letterGrade.equalsIgnoreCase("A-")) {
			numericGrade = 3.6;
		}else if (letterGrade.equalsIgnoreCase("A")) {
			numericGrade = 3.8;
		}else if (letterGrade.equalsIgnoreCase("A+")) {
			numericGrade = 4;
		}else {
			System.out.println(Print.printRed(" invalid input"
					+ "\n Enter LetterGrade (EX:A-) or TotalGrade (EX:82)"));
			getGrades();
		}
	} catch(Exception e) {
		e.getStackTrace();
	}
		return numericGrade;
	}
}
	
	

