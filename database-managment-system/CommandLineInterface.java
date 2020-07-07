import java.util.Scanner;
import java.sql.*;

public class CommandLineInterface {
	String quit = "quit";

	CommandLineInterface(Connection cn) {

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println(
					"What would you like to do? Options are \"quit\" - to exit the database, report and insert ");
			String input = sc.nextLine().toLowerCase().trim();
			if (input.equals("quit")) {
				System.out.println("Exiting");
				sc.close();
				break;
			}

			else if (input.equals("report")) {
				System.out.println("Enter a report- menu or party ");
				input = sc.nextLine();

				if (input.equals("party")) {

					System.out.println("Enter a party id number ");
					while (true) {
						try {
							int num = sc.nextInt();
							if (num > 0) {
								Database.partyReport(cn, num);
								break;
							} else {
								System.out.println("Re enter your number");
							}
						} catch (Exception e) {
							System.out.println("INVALID REQUEST");
							break;
						}
					}
					
				}
				else if(input.equals("menu")){
					System.out.println("Enter a menu id number ");
					while (true) {
						try {
							int num = sc.nextInt();
							if (num > 0) {
								Database.menuReport(cn, num);
								break;
							} else {
								System.out.println("Re enter your number");
							}
						} catch (Exception e) {
							System.out.println("INVALID REQUEST");
							break;
						}
					}		
				}
				sc.nextLine();
			}
		}
	}
}
