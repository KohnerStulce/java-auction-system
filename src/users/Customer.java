package users;

import java.util.ArrayList;
import java.util.Scanner;

import auctions.AllAuctions;
import auctions.Auctions;

public class Customer extends Users{

	private float accountBalance; // The account balance
	private ArrayList<Auctions> bids = new ArrayList<>(); // Auctions being bid on
	private ArrayList<Auctions> watchList = new ArrayList<>(); // Auctions in watch list
	
	public Customer(String username, String password, AllAuctions obj) {
		super(username, password);
		accountBalance = 0;
		generateBids(obj);
	}
	
	//CREATE SETTERS AND GETTERS
	public float getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}

	public ArrayList<Auctions> getBids() {
		return bids;
	}

	public void setBids(ArrayList<Auctions> bids) {
		this.bids = bids;
	}

	public ArrayList<Auctions> getWatchList() {
		return watchList;
	}

	public void setWatchList(ArrayList<Auctions> watchList) {
		this.watchList = watchList;
	}
	/*
	 * If AllAuctions arraylist is not null, store all auctions that belong to the user into bids arrayList
	 */
	private void generateBids(AllAuctions obj) {
		//TODO
		bids.clear();

		if (obj.getAllAuctions() != null) {
			for (Auctions x : obj.getAllAuctions()) {
				if (x.getHighestBidder().equals(getUsername())) {
					bids.add(x);
				}
			}
		}
	}
	/*
	 * Ask how much they want to add to the account. Add that amount to account.
	 */
	public void addToAccountBalance(Scanner in) {
		//TODO
		System.out.print("How much do you want to add? ");
		float amount = Float.parseFloat(in.nextLine());

		if (amount > 0) {
			accountBalance += amount;
			System.out.println("New balance: $" + accountBalance);
		} else {
			System.out.println("Invalid amount.");
		}
	}
	
	// Subtracts money from the account
	private void subtractFromAccountBalance(float subtraction) {
		accountBalance = accountBalance-subtraction;
	}
	
	/*
	 * This method is called when a bid is made. It credits the previous top bidder with the amount that they bidded.
	 */
	private void addAfterFailedBid(float addition, String previousTopBidder, ArrayList<Customer> customers) {
		//TODO
		for (Customer x : customers) {
			if (x.getUsername().equals(previousTopBidder)) {
				x.setAccountBalance(x.getAccountBalance() + addition);
				break;
			}
		}
	}

	// Adds an auction to the watch list
	private void addToWatchList(Auctions obj) {
		//TODO
		if (!watchList.contains(obj)) {
			watchList.add(obj);
			System.out.println("Added to watch list.");
		}
	}
	
	// Removes an auction from the watch list
	private void removeFromWatchList(Auctions obj) {
		//TODO
		watchList.remove(obj);
		System.out.println("Removed from watch list.");
	}
	
	// Prints all bids for this customer
	public void printBids(AllAuctions obj) {
		generateBids(obj);
		obj.printAuctions(bids);
	}
	
	// Prints the watch list
	public void printWatchList(AllAuctions obj) {
		obj.printAuctions(watchList);
	}
	
	/*
	 * Used as the driver to make bids. Prints all the auctions, asks which auction they want to bid on and how much.
	 * Create a auction object of the selected auction 
	 * Check if the bid is valid. 
	 * If it is call the following methods [addAfterFailedBid() from this class,setCurrentBid() for the Auction Class, and subtractFromAccountBalance() from this class]
	 * Add it to the customers bids list
	 */
	public void makeBid(Scanner in,AllAuctions obj, ArrayList<Customer> customers) {
		//TODO
		if (obj.getAllAuctions().size() == 0) {
			System.out.println("No auctions available.");
			return;
		}

		obj.printAuctions(obj.getAllAuctions());
		System.out.print("Which auction do you want to bid on? ");
		int choice = Integer.parseInt(in.nextLine());

		if (choice < 1 || choice > obj.getAllAuctions().size()) {
			System.out.println("Invalid auction.");
			return;
		}

		Auctions selected = obj.getAllAuctions().get(choice - 1);

		if (selected.getAuctionStatus().equalsIgnoreCase("Upcoming")
				|| selected.getAuctionStatus().equalsIgnoreCase("Completed")) {
			System.out.println("You cannot bid on this auction right now.");
			return;
		}

		System.out.print("Enter bid amount: ");
		float amount = Float.parseFloat(in.nextLine());

		if (amount <= selected.getCurrentBid()) {
			System.out.println("Bid must be higher than current bid.");
			return;
		}

		if (amount > accountBalance) {
			System.out.println("Not enough account balance.");
			return;
		}

		String previousTopBidder = selected.getHighestBidder();
		float previousTopBid = selected.getCurrentBid();

		if (!previousTopBidder.equals("None")) {
			addAfterFailedBid(previousTopBid, previousTopBidder, customers);
		}

		selected.setCurrentBid(amount);
		selected.setHighestBidder(getUsername());
		subtractFromAccountBalance(amount);

		if (!bids.contains(selected)) {
			bids.add(selected);
		}

		System.out.println("Bid placed successfully.");
	}
	
	/*
	 * Used as the driver to add and delete auctions from watch list. 
	 * Ask if the customer wants to add or remove from watch list.
	 * IF ADDING
	 * 	Print All the Auctions and ask which one the want to add.
	 * 	add that auction to watchlist with addToWatchList()
	 * IF removing
	 * 	Print Watch List and ask which one they want to remove
	 *  Remove it from the watchlist with removeFromWatchList()
	 */
	public void controlWatchList(AllAuctions obj, Scanner in) {
		//TODO
		System.out.println("Type A to add to watch list or R to remove from watch list.");
		String choice = in.nextLine();

		if (choice.equalsIgnoreCase("A")) {
			obj.printAuctions(obj.getAllAuctions());
			System.out.print("Which auction do you want to add? ");
			int auctionChoice = Integer.parseInt(in.nextLine());

			if (auctionChoice >= 1 && auctionChoice <= obj.getAllAuctions().size()) {
				addToWatchList(obj.getAllAuctions().get(auctionChoice - 1));
			} else {
				System.out.println("Invalid choice.");
			}
		} else if (choice.equalsIgnoreCase("R")) {
			printWatchList(obj);

			if (watchList.size() == 0) {
				return;
			}

			System.out.print("Which auction do you want to remove? ");
			int watchChoice = Integer.parseInt(in.nextLine());

			if (watchChoice >= 1 && watchChoice <= watchList.size()) {
				removeFromWatchList(watchList.get(watchChoice - 1));
			} else {
				System.out.println("Invalid choice.");
			}
		} else {
			System.out.println("Invalid choice.");
		}
	}
}