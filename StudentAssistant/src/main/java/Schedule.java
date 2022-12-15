import java.util.ArrayList;
import java.util.LinkedList;

public class Schedule extends Task {
	/**
	 * 7 for 7 days per week
	 * 21 for the number of hours. for now,
	 * the program will function between 9am-9pm
	 * (22 = 12 + 10) because the index(starts at 0) refers to the hour.
	 */
	public String[][] schedule = new String[7][22];
	 
	
	public void getClasses() {
		ArrayList<Task> classes = getWeekClasses();
		Task t = new Task();
		for (int i = 0; i<classes.size(); i++) {
			t = classes.get(i);
			String day = t.getDay().toUpperCase();
			switch (day) {
			case "MONDAY" : schedule[0][t.getTime()] = t.getName();
				break;
			case "TUESDAY" : schedule[1][t.getTime()] = t.getName();				 
				break;
			case "WEDNESDAY" : schedule[2][t.getTime()] = t.getName();				   
				break;
			case "THURSDAY" : schedule[3][t.getTime()] = t.getName();				  
				break;
			case "FRIDAY" : schedule[4][t.getTime()] = t.getName();
				break;
			case "SATURDAY" : schedule[5][t.getTime()] = t.getName();
				break;
			case "SUNDAY" : schedule[6][t.getTime()] = t.getName();
				break;
			}
		
		}
		// since the schedule starts at 9
		//index 8 will be used to store day of the week.
		schedule[0][8] = "MONDAY";
		schedule[1][8] = "TUESDAY";
		schedule[2][8] = "WEDNESDAY";
		schedule[3][8] = "THURSDAY";
		schedule[4][8] = "FRIDAY";
		schedule[5][8] = "SATURDAY";
		schedule[6][8] = "SUNDAY";
		
		System.out.print("     ");
		for(int j=8;j<22;j++) {
			if(j>9) {
				System.out.print(printBlue(padString(""+j+"|", 5)));
				//fixing the spaces on schedule (for visual purposes)
			}if(j==9) {
				System.out.print(printBlue(padString(""+j+" |", 5)));
			}
		for (int i=0; i<7;i++) {
			if(j==8) {
				System.out.print("\u001b[36m");
			}
			System.out.print(padString(schedule[i][j], 10)+padString("|",3));
			}
		System.out.println();
		System.out.print("\u001b[36m");System.out.println(padString("__|",3)+padString("\u001B[0m____________|",15) +padString("____________|",13)
			+padString("____________|",13)+padString("____________|",13)+padString("____________|",13)
			+padString("____________|",13)+padString("____________|",13));
		}
	}
}

	
	
	
	
	
	
	


