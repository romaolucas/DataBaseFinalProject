package tum.in.dbpra.bean;

import java.sql.Timestamp;

//import java.util.Date;

public class TimeslotBean {

	private Integer timeslotID;
	private Integer pID;
	private Integer sectionID;

	private Timestamp timeBuildUp;
	private Timestamp timePlay;
	private Timestamp timeFinish;
	private Timestamp timeGone;

	public TimeslotBean() {
	}

	public Integer getTimeslotID() {
		return timeslotID;
	}

	public void setTimeslotID(Integer timeslotID) {
		this.timeslotID = timeslotID;
	}

	public Integer getpID() {
		return pID;
	}

	public void setpID(Integer pID) {
		this.pID = pID;
	}

	public Integer getSectionID() {
		return sectionID;
	}

	public void setSectionID(Integer sectionID) {
		this.sectionID = sectionID;
	}

	public Timestamp getTimeBuildUp() {
		return timeBuildUp;
	}

	public void setTimeBuildUp(Timestamp timeBuildUp) {
		this.timeBuildUp = timeBuildUp;
	}

	public Timestamp getTimePlay() {
		return timePlay;
	}

	public void setTimePlay(Timestamp timePlay) {
		this.timePlay = timePlay;
	}

	public Timestamp getTimeFinish() {
		return timeFinish;
	}

	public void setTimeFinish(Timestamp timeFinish) {
		this.timeFinish = timeFinish;
	}

	public Timestamp getTimeGone() {
		return timeGone;
	}

	public void setTimeGone(Timestamp timeGone) {
		this.timeGone = timeGone;
	}

}