/**
 * Interface Print contain methods that change the color of the text printed to console,
 * pad Strings (for tables), and print tables to console easily.
 * @author Said
 *
 */
public interface Print {
	/**
	 * @param str
	 * @return str in red
	 */
	public static String printRed(String str) {
		final String ANSI_RESET = "\u001B[0m";
		final String ANSI_RED = "\u001B[31;1m";
		 str = (ANSI_RED + str + ANSI_RESET );
		return str;
	}
	/**
	 * @param str
	 * @return str in green
	 */
	public static String printGreen(String str) {
		final String ANSI_RESET = "\u001B[0m";
		final String ANSI_GREEN = "\u001b[32m";
		str = (ANSI_GREEN + str + ANSI_RESET);
		return str;
	}
	/**
	 * @param str
	 * @return str in blue.
	 */
	public static String printBlue(String str) {
		final String ANSI_BLUE = "\u001b[36m";
		final String ANSI_RESET = "\u001B[0m";
		str = (ANSI_BLUE + str + ANSI_RESET);
		return str;
	}
	/**
	 * @param str
	 * @return str in purple.
	 */
	public static String printPurple(String str) {
		final String ANSI_PURPLE = "\u001b[35m";
		final String ANSI_RESET = "\u001B[0m";
		str = (ANSI_PURPLE + str + ANSI_RESET);
		return str;
	}
	/**
	 * String padding method, takes string and length as parameters.
	 * @param str
	 * @param length
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
	 * Prints the first row of a tables (Column names).  --NOT CALLED ANYWHERE YET.
	 * Used to create a table with 2 columns
	 */
	public static void printTableLabels() {
		System.out.println(printBlue(padString("ID", 10) + padString("Name",25)));
		System.out.println(printBlue("_________________________"));
	}
	/**
	 * Prints the first row of a tables (Column names). --NOT CALLED ANYWHERE YET.
	 * Used to create a table with 3 columns
	 * @param field2 The name to be given to the 3rd column
	 */
	public static void printTableLabels(String field2) {
		System.out.println(printBlue(padString("ID", 10) + padString("Name", 25) + padString(field2,20)));
		System.out.println(printBlue("____________________________________________"));
	}
	/**
	 * Prints table fields, used for tables with 2 columns --NOT CALLED ANYWHERE YET. 
	 * @param id --first field (that is usually ID)
	 * @param field --second field
	 */
	public static void printTable(int id, String field) {
		System.out.println(padString(""+id, 10)+padString(field, 25));
	}
	/**
	 * Prints table fields, used for tables with 3 columns --NOT CALLED ANYWHERE YET. 
	 * @param id --first field (that is usually ID)
	 * @param field --second field
	 * @param field2 --third field
	 */
	public static void printTable(int id, String field, String field2) {
		System.out.println(padString(""+id, 10)+padString(field,25) + padString(field2,20));
	}
}
