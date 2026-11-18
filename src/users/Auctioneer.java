package users;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import auctions.AllAuctions;
import auctions.Auctions;

public class Auctioneer extends Users{

	private ArrayList<Auctions> auctions = new ArrayList<>();
	
	public Auctioneer(String username, String password, AllAuctions obj) {
		super(username, password);
		setAuctions(obj);
	}
	
	// Loads auctions owned by this auctioneer
	public void setAuctions(AllAuctions obj) {
		auctions.clear();
		
		for (Auctions x : obj.getAllAuctions()) {
			if (x.getAuctioneer().equals(getUsername())) {
				auctions.add(x);
			}
		}
	}
	
	public void addAuctions(Scanner in, AllAuctions obj) {
		System.out.println("Do you want to manually upload an auction? (Y or N)");
		String choice = in.nextLine();
		
		if(choice.equalsIgnoreCase("Y")) {
			manualAddition(in, obj);
		}
	}
	
	// Writes auction to file
	private void writeToAuctions(Auctions obj) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("Auctions.txt", true));

			bw.write(obj.getItemName() + "," + obj.getCategory() + "," + obj.getPostedTime() + ","
					+ obj.getStartingAmount() + "," + obj.getCurrentBid() + ","
					+ obj.getHighestBidder() + "," + obj.getAuctionStatus() + ","
					+ obj.getAuctioneer());

			bw.newLine();
			bw.close();
		} catch (Exception e) {
			System.out.println("Could not write auction.");
		}
	}

	// Creates new auction
	private void manualAddition(Scanner in, AllAuctions allAuctionsObj) {

		System.out.print("Enter item name: ");
		String itemName = in.nextLine();

		System.out.print("Enter category: ");
		String category = in.nextLine();

		System.out.print("Enter starting amount: ");
		float startingAmount = Float.parseFloat(in.nextLine());

		Auctions auction = new Auctions(
				itemName, category, null,
				startingAmount, startingAmount,
				"None", "Upcoming", getUsername());

		allAuctionsObj.addAuction(auction);
		setAuctions(allAuctionsObj);
		writeToAuctions(auction);

		System.out.println("Auction added.");
	}
	
	// Starts an auction
	public void startAuction(Scanner in, AllAuctions obj) {
		setAuctions(obj);

		if (auctions.size() == 0) {
			System.out.println("No auctions to start.");
			return;
		}

		printAuctions(obj);
		System.out.print("Which auction do you want to start? ");
		int choice = Integer.parseInt(in.nextLine());

		if (choice >= 1 && choice <= auctions.size()) {
			auctions.get(choice - 1).setAuctionStatus("In Progress");
			System.out.println("Auction started.");
		} else {
			System.out.println("Invalid choice.");
		}
	}

	// Ends an auction
	public void endAuction(Scanner in, AllAuctions obj) {
		setAuctions(obj);

		if (auctions.size() == 0) {
			System.out.println("No auctions to end.");
			return;
		}

		printAuctions(obj);
		System.out.print("Which auction do you want to end? ");
		int choice = Integer.parseInt(in.nextLine());

		if (choice >= 1 && choice <= auctions.size()) {
			auctions.get(choice - 1).setAuctionStatus("Completed");
			System.out.println("Auction ended.");
		} else {
			System.out.println("Invalid choice.");
		}
	}

	// Prints this auctioneer's auctions
	public void printAuctions(AllAuctions obj) {
		obj.printAuctions(auctions);
	}
}