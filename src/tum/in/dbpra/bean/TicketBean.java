package tum.in.dbpra.bean;

import java.util.Date;

public class TicketBean {

	private int tickeID;

	/**
	 * @return the tickeID
	 */
	public int getTickeID() {
		return tickeID;
	}

	/**
	 * @param tickeID
	 *            the tickeID to set
	 */
	public void setTickeID(int tickeID) {
		this.tickeID = tickeID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the purchaseDate
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @param purchaseDate
	 *            the purchaseDate to set
	 */
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	private String name;
	private Date purchaseDate;
	private Double price;

}