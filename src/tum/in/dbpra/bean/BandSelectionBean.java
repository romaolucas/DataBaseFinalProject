package tum.in.dbpra.bean;

import java.util.Date;

public class BandSelectionBean {
	private int visitorID;
	private int bandID;
	private String name;
	private String style;
	private String country;
	private Date timeplay;
	private Date timefinish;
	/**
	 * @return the visitorID
	 */
	public int getVisitorID() {
		return visitorID;
	}
	/**
	 * @param visitorID the visitorID to set
	 */
	public void setVisitorID(int visitorID) {
		this.visitorID = visitorID;
	}
	/**
	 * @return the bandID
	 */
	public int getBandID() {
		return bandID;
	}
	/**
	 * @param bandID the bandID to set
	 */
	public void setBandID(int bandID) {
		this.bandID = bandID;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the timeplay
	 */
	public Date getTimeplay() {
		return timeplay;
	}
	/**
	 * @param timeplay the timeplay to set
	 */
	public void setTimeplay(Date timeplay) {
		this.timeplay = timeplay;
	}
	/**
	 * @return the timefinish
	 */
	public Date getTimefinish() {
		return timefinish;
	}
	/**
	 * @param timefinish the timefinish to set
	 */
	public void setTimefinish(Date timefinish) {
		this.timefinish = timefinish;
	}
	
}