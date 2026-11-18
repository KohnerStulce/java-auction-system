package auctions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class AllAuctions {
	
	private  ArrayList<Auctions> allAuctions = new ArrayList<>();
	
	public AllAuctions() {
		setAllAuctions();
	}
	
	/*
	 * Create Auctions.txt if it does not already exist. Can use new File("Auctions.txt").createNewFile();
	 * Read auctions line by line from the file and store them into allAuctions ArrayList.
	 * 
	 */
	private void setAllAuctions() {
		//TODO
		allAuctions.clear();
		
		try {
			File file = new File("Auctions.txt");
			file.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {
				if (line.isBlank()) {
					continue;
				}

				String[] arr = line.split(",");

				if (arr.length >= 8) {
					Auctions auction = new Auctions(
							arr[0],
							arr[1],
							LocalDateTime.parse(arr[2]),
							Float.parseFloat(arr[3]),
							Float.parseFloat(arr[4]),
							arr[5],
							arr[6],
							arr[7]);

					allAuctions.add(auction);
				}
			}

			br.close();
		} catch (Exception e) {
			System.out.println("Could not load auctions.");
		}
	}

	//Create getter for allAuctions
	//TODO
	
	// Gets the all auctions list
	public ArrayList<Auctions> getAllAuctions() {
		return allAuctions;
	}

	// Sets the all auctions list
	public void setAllAuctions(ArrayList<Auctions> allAuctions) {
		this.allAuctions = allAuctions;
	}

	// Adds one auction to the list
	public void addAuction(Auctions obj) {
		allAuctions.add(obj);
	}
	
	/*
	 * Takes in an ArrayList of Auctions, prints the auctions in format shown in instructions
	 */
	public void printAuctions(ArrayList<Auctions> obj) {
		
		if (obj == null || obj.size() == 0) {
			System.out.println("No auctions!");
			return;
		}
		//TODO

		System.out.println("---------------------------------------------------------------");
		System.out.printf("%-4s %-15s %-14s %-10s %-10s %-12s %-12s%n",
				"#", "Item", "Category", "Start", "Current", "Status", "Bidder");
		System.out.println("---------------------------------------------------------------");

		for (int i = 0; i < obj.size(); i++) {
			Auctions x = obj.get(i);

			System.out.printf("%-4d %-15s %-14s $%-9.2f $%-9.2f %-12s %-12s%n",
					i + 1,
					x.getItemName(),
					x.getCategory(),
					x.getStartingAmount(),
					x.getCurrentBid(),
					x.getAuctionStatus(),
					x.getHighestBidder());
		}

		System.out.println("---------------------------------------------------------------");
	}
	
	// Prints auctions from a chosen category
	public void printFilteredAuctions(Scanner in, ArrayList<Auctions> obj) {
		ArrayList<Auctions> filteredAuctions = new ArrayList<>();
		if (obj == null || obj.size() == 0) {
			System.out.println("No auctions!");
			return;
		}
		System.out.println("What Category Do you want to filter");
		System.out.print("\nAppliances\tClothing\tElectronics"
				+"\nFurniture\tToys\n");
		
		String choice = in.nextLine();

		for(Auctions x : obj) {
			if (x.getCategory().equalsIgnoreCase(choice)) {
				filteredAuctions.add(x);
			}
		}
		
		printAuctions(filteredAuctions);
	}
	
}