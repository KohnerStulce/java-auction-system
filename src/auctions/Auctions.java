package auctions;

import java.time.LocalDateTime;

public class Auctions {

	// Auction attributes
	private LocalDateTime timePosted;
	private String itemName;
	private String category;
	private float startingAmount;
	private float currentBid;
	private String highestBidder;
	private String auctionStatus;
	private String auctioneer;

	// Constructor for auction object
	public Auctions(String itemName, String category, LocalDateTime timePosted,
			float startingAmount, float currentBid, String highestBidder,
			String auctionStatus, String auctioneer) {
		
		this.itemName = itemName;
		this.category = category;

		// If no time given, set current time
		if (timePosted != null) {
			this.timePosted = timePosted;
		} else {
			setPostedTime();
		}

		this.startingAmount = startingAmount;
		this.currentBid = currentBid;

		// Default values if empty
		if (highestBidder != null && !highestBidder.isBlank()) {
			this.highestBidder = highestBidder;
		} else {
			this.highestBidder = "None";
		}

		if (auctionStatus != null && !auctionStatus.isBlank()) {
			this.auctionStatus = auctionStatus;
		} else {
			this.auctionStatus = "Upcoming";
		}

		this.auctioneer = auctioneer;
	}

	// Sets current time for new auction
	private void setPostedTime() {
		this.timePosted = LocalDateTime.now();
	}

	// Only updates bid if higher
	public void setCurrentBid(float currentBid) {
		if (currentBid > this.currentBid) {
			this.currentBid = currentBid;
		}
	}

	// Basic getters/setters
	public String getItemName() { return itemName; }
	public void setItemName(String itemName) { this.itemName = itemName; }

	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }

	public LocalDateTime getPostedTime() { return timePosted; }

	public float getStartingAmount() { return startingAmount; }

	public float getCurrentBid() { return currentBid; }

	public String getHighestBidder() { return highestBidder; }
	public void setHighestBidder(String highestBidder) { this.highestBidder = highestBidder; }

	public String getAuctionStatus() { return auctionStatus; }
	public void setAuctionStatus(String auctionStatus) { this.auctionStatus = auctionStatus; }

	public String getAuctioneer() { return auctioneer; }
}