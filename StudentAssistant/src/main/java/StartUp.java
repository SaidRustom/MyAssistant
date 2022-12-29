import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
/**
 * This class is to be triggered at windows startup.
 * It print's today's schedule, and assignments due this week
 * @author Said
 */
public class StartUp {
	
	public static void main (String[] args) {
		Queries queries = new Queries();
		queries.createTasksFromAssignments(); // a STOREDPROCEDURE in DB to run everytime the program starts.
		Schedule s = new Schedule();
		s.printTodaySchedule();
		queries.printWeekAssignments();
		Test.menuChoiceAssignment();
	}
		
}
