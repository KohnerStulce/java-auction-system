package utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import auctions.AllAuctions;
import auctions.Auctions;
import users.*;
import users.Auctioneer;
import users.Customer;
import com.odu.Accounts;

public class Utilites {
	
	// Stores account info from provided JAR file
	private Accounts customers = new Accounts("Customers");
	private Accounts auctioneers = new Accounts("Auctioneers");
	private Accounts admin = new Accounts("ADMIN");
	
	// Stores all auctions in system
	private AllAuctions all = new AllAuctions();

	// Keeps track of users currently using program
	private ArrayList<Customer> customerList = new ArrayList<>();
	private ArrayList<Auctioneer> auctioneerList = new ArrayList<>();
	
	/*
	 * Create all the menu methods, refer to the instructions to see specifics
	 */
	public void viewMainMenu() {
		Scanner in = new Scanner(System.in);

		boolean active = true;

		// Main loop keeps program running until user quits
		while (active) {
			System.out.println("\n===== MAIN MENU =====");
			System.out.println("1. Sign In");
			System.out.println("2. Create Customer Account");
			System.out.println("3. View Auctions");
			System.out.println("4. Quit");
			System.out.print("Choose an option: ");

			String choice = in.nextLine();

			switch (choice) {
			case "1":
				login(in); // handles login flow
				break;
			case "2":
				customerAccountCreation(in); // creates new customer
				break;
			case "3":
				all.printAuctions(all.getAllAuctions()); // prints auctions
				break;
			case "4":
				active = false; // exits loop
				break;
			default:
				System.out.println("Incorrect Input");
			}
		}

		// Save auctions before quitting
		writeToAuctions();
		System.out.println("Quiting");
		in.close();
	}
	
	// Finds customer object by username
	private Customer findCustomer(String username) {
		for (Customer x : customerList) {
			if (x.getUsername().equals(username)) {
				return x;
			}
		}
		return null;
	}

	// Finds auctioneer object by username
	private Auctioneer findAuctioneer(String username) {
		for (Auctioneer x : auctioneerList) {
			if (x.getUsername().equals(username)) {
				return x;
			}
		}
		return null;
	}
	
	private void viewCustomerMenu(String username, String password, Scanner in) {

		// Create or retrieve existing customer
		Customer currentCustomer = findCustomer(username);

		if (currentCustomer == null) {
			currentCustomer = new Customer(username, password, all);
			customerList.add(currentCustomer);
		}

		boolean active = true;

		while (active) {
			System.out.println("\n===== CUSTOMER MENU =====");
			System.out.println("1. View All Auctions");
			System.out.println("2. Add To Account Balance");
			System.out.println("3. Make Bid");
			System.out.println("4. View My Bids");
			System.out.println("5. Control Watch List");
			System.out.println("6. View Watch List");
			System.out.println("7. Logout");
			System.out.print("Choose an option: ");

			String choice = in.nextLine();

			switch (choice) {
			case "1":
				all.printAuctions(all.getAllAuctions());
				break;
			case "2":
				currentCustomer.addToAccountBalance(in); // add money
				break;
			case "3":
				currentCustomer.makeBid(in, all, customerList); // bid logic
				break;
			case "4":
				currentCustomer.printBids(all);
				break;
			case "5":
				currentCustomer.controlWatchList(all, in);
				break;
			case "6":
				currentCustomer.printWatchList(all);
				break;
			case "7":
				active = false;
				break;
			default:
				System.out.println("Incorrect Input");
			}
		}
	}
	
	private void viewAdminMenu(String username, Scanner in) {

		// Create admin object (simple access control)
		Admin currentAdmin = new Admin(username, "");

		boolean active = true;

		while (active) {
			System.out.println("\n===== ADMIN MENU =====");
			System.out.println("1. Create Auctioneer Account");
			System.out.println("2. Logout");
			System.out.print("Choose an option: ");

			String choice = in.nextLine();

			switch (choice) {
			case "1":
				currentAdmin.createAuctioneer(in, auctioneers);
				break;
			case "2":
				active = false;
				break;
			default:
				System.out.println("Incorrect Input");
			}
		}
	}
	
	private void viewAuctioneerMenu(String username, String password, Scanner in) {

		// Create or retrieve auctioneer
		Auctioneer currentAuctioneer = findAuctioneer(username);

		if (currentAuctioneer == null) {
			currentAuctioneer = new Auctioneer(username, password, all);
			auctioneerList.add(currentAuctioneer);
		}

		boolean active = true;

		while (active) {
			System.out.println("\n===== AUCTIONEER MENU =====");
			System.out.println("1. View My Auctions");
			System.out.println("2. Add Auction");
			System.out.println("3. Start Auction");
			System.out.println("4. End Auction");
			System.out.println("5. Logout");
			System.out.print("Choose an option: ");

			String choice = in.nextLine();

			switch (choice) {
			case "1":
				currentAuctioneer.setAuctions(all);
				currentAuctioneer.printAuctions(all);
				break;
			case "2":
				currentAuctioneer.addAuctions(in, all);
				break;
			case "3":
				currentAuctioneer.startAuction(in, all);
				break;
			case "4":
				currentAuctioneer.endAuction(in, all);
				break;
			case "5":
				active = false;
				break;
			default:
				System.out.println("Incorrect Input");
			}
		}
	}
	
	private void login(Scanner in) {

	    System.out.println("What type of account are you logging into: C(customer), A(auctioneer), ADMIN");
	    String userType = in.nextLine().trim();

	    // Routes user to correct login type
	    if (userType.equalsIgnoreCase("C")) {
	        signIn(in, "C");
	    } else if (userType.equalsIgnoreCase("A")) {
	        signIn(in, "A");
	    } else if (userType.equalsIgnoreCase("ADMIN")) {
	        signIn(in, "ADMIN");
	    } else {
	        System.out.println("Incorrect Input");
	    }
	}
	
	private void signIn(Scanner in, String userType) {

		System.out.print("Enter username: ");
		String username = in.nextLine();

		System.out.print("Enter password: ");
		String password = in.nextLine();

		// Validates login and opens correct menu
		if (userType.equals("C")) {
			if (customers.signIn(username, password)) {
				viewCustomerMenu(username, password, in);
			}
		} else if (userType.equals("A")) {
			if (auctioneers.signIn(username, password)) {
				viewAuctioneerMenu(username, password, in);
			}
		} else if (userType.equals("ADMIN")) {
			if (admin.signIn(username, password)) {
				viewAdminMenu(username, in);
			}
		}
	}

	private void customerAccountCreation(Scanner in) {

		System.out.print("Enter customer username: ");
		String username = in.nextLine();

		System.out.print("Enter customer password: ");
		String password = in.nextLine();

		// Adds account using provided JAR
		customers.addAccount(username, password);
	}

	private void writeToAuctions() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("Auctions.txt"));

			// Writes all auctions to file
			for (Auctions x : all.getAllAuctions()) {
				bw.write(x.getItemName() + "," + x.getCategory() + "," + x.getPostedTime() + ","
						+ x.getStartingAmount() + "," + x.getCurrentBid() + "," + x.getHighestBidder() + ","
						+ x.getAuctionStatus() + "," + x.getAuctioneer());
				bw.newLine();
			}

			bw.close();
		} catch (Exception e) {
			System.out.println("Could not save auctions.");
		}
	}
}