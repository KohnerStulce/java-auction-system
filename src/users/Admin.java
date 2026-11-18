package users;

import java.util.Scanner;
import com.odu.Accounts;

public class Admin extends Users{
	
	public Admin(String username, String password) {
		// Calls parent constructor
		super(username, password);
	}
	
	// Creates a new auctioneer account
	public void createAuctioneer(Scanner in, Accounts obj) {

		System.out.print("Enter new auctioneer username: ");
		String username = in.nextLine();

		System.out.print("Enter new auctioneer password: ");
		String password = in.nextLine();

		// Adds account using JAR
		obj.addAccount(username, password);
		System.out.println("Auctioneer account created.");
	}
}