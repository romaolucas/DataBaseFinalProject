package tum.in.dbpra.bean;

import java.util.Date;

public class AccommodationBean {
	private int	roomno;
	private Date checkinDate;
	/**
	 * @return the checkinDate
	 */
	public Date getCheckinDate() {
		return checkinDate;
	}
	/**
	 * @param checkinDate the checkinDate to set
	 */
	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}
	/**
	 * @return the checkoutDate
	 */
	public Date getCheckoutDate() {
		return checkoutDate;
	}
	/**
	 * @param checkoutDate the checkoutDate to set
	 */
	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	private Date checkoutDate;
	/**
	 * @return the roomno
	 */
	public int getRoomno() {
		return roomno;
	}
	/**
	 * @param roomno the roomno to set
	 */
	public void setRoomno(int roomno) {
		this.roomno = roomno;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the rent
	 */
	public Double getRent() {
		return rent;
	}
	/**
	 * @param rent the rent to set
	 */
	public void setRent(Double rent) {
		this.rent = rent;
	}
	/**
	 * @return the capasity
	 */
	public int getCapasity() {
		return capasity;
	}
	/**
	 * @param capasity the capasity to set
	 */
	public void setCapasity(int capasity) {
		this.capasity = capasity;
	}
	/**
	 * @return the smoking
	 */
	public boolean isSmoking() {
		return smoking;
	}
	/**
	 * @param smoking the smoking to set
	 */
	public void setSmoking(boolean smoking) {
		this.smoking = smoking;
	}
	/**
	 * @return the pets
	 */
	public boolean isPets() {
		return pets;
	}
	/**
	 * @param pets the pets to set
	 */
	public void setPets(boolean pets) {
		this.pets = pets;
	}
	/**
	 * @return the booked
	 */
	public boolean isBooked() {
		return booked;
	}
	/**
	 * @param booked the booked to set
	 */
	public void setBooked(boolean booked) {
		this.booked = booked;
	}
	/**
	 * @return the startdate
	 */
	public Date getStartdate() {
		return startdate;
	}
	/**
	 * @param startdate the startdate to set
	 */
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	private String address;
	private Double rent;
	private int capasity;
	private boolean smoking;
	private boolean pets;
	private boolean booked;
	private  Date startdate;
	private  Date endDate;
	

}
