package tum.in.dbpra.bean;

import java.util.Date;

public class TicketCategoryBean {
private int id;
private String name;
private Double price;
private int volume;
private Date startTime;
private Date endTime;
private String description;
/**
 * @return the duration
 */
public int getDuration() {
	return duration;
}
/**
 * @param duration the duration to set
 */
public void setDuration(int duration) {
	this.duration = duration;
}
private int duration;

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
/**
 * @return the price
 */
public Double getPrice() {
	return price;
}
/**
 * @param price the price to set
 */
public void setPrice(Double price) {
	this.price = price;
}
/**
 * @return the volume
 */
public int getVolume() {
	return volume;
}
/**
 * @param volume the volume to set
 */
public void setVolume(int volume) {
	this.volume = volume;
}
/**
 * @return the startTime
 */
public Date getStartTime() {
	return startTime;
}
/**
 * @param startTime the startTime to set
 */
public void setStartTime(Date startTime) {
	this.startTime = startTime;
}
/**
 * @return the endTime
 */
public Date getEndTime() {
	return endTime;
}
/**
 * @param endTime the endTime to set
 */
public void setEndTime(Date endTime) {
	this.endTime = endTime;
}
/**
 * @return the description
 */
public String getDescription() {
	return description;
}
/**
 * @param description the description to set
 */
public void setDescription(String description) {
	this.description = description;
}

}
