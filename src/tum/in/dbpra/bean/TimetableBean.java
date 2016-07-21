package tum.in.dbpra.bean;

import java.util.Date;

public class TimetableBean {
	private int id;
	private String name;
	private String style;
	private String country;
	private Double charge;
	private int sectionid;
	private Date timebuildup;
	private Date timeplay;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	private Date timefinish;
	private Date timegone;
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
	 * @return the charge
	 */
	public Double getCharge() {
		return charge;
	}
	/**
	 * @param charge the charge to set
	 */
	public void setCharge(Double charge) {
		this.charge = charge;
	}
	/**
	 * @return the sectionid
	 */
	public int getSectionid() {
		return sectionid;
	}
	/**
	 * @param sectionid the sectionid to set
	 */
	public void setSectionid(int sectionid) {
		this.sectionid = sectionid;
	}
	/**
	 * @return the timebuildup
	 */
	public Date getTimebuildup() {
		return timebuildup;
	}
	/**
	 * @param timebuildup the timebuildup to set
	 */
	public void setTimebuildup(Date timebuildup) {
		this.timebuildup = timebuildup;
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
	/**
	 * @return the timegone
	 */
	public Date getTimegone() {
		return timegone;
	}
	/**
	 * @param timegone the timegone to set
	 */
	public void setTimegone(Date timegone) {
		this.timegone = timegone;
	}
	
}