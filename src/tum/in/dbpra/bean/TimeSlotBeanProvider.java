package tum.in.dbpra.bean;

import java.sql.Timestamp;

public class TimeSlotBeanProvider {
	protected int id;
	protected int sectionid;
	protected Timestamp timebuildup;
	protected Timestamp timeplay;
	protected Timestamp timefinish;
	protected Timestamp timegone;
	protected String stageName;
	protected String stageAddress;

	public TimeSlotBeanProvider() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSectionid() {
		return sectionid;
	}

	public void setSectionid(int sectionid) {
		this.sectionid = sectionid;
	}

	public Timestamp getTimebuildup() {
		return timebuildup;
	}

	public void setTimebuildup(Timestamp timebuildup) {
		this.timebuildup = timebuildup;
	}

	public Timestamp getTimeplay() {
		return timeplay;
	}

	public void setTimeplay(Timestamp timeplay) {
		this.timeplay = timeplay;
	}

	public Timestamp getTimefinish() {
		return timefinish;
	}

	public void setTimefinish(Timestamp timefinish) {
		this.timefinish = timefinish;
	}

	public Timestamp getTimegone() {
		return timegone;
	}

	public void setTimegone(Timestamp timegone) {
		this.timegone = timegone;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getStageAddress() {
		return stageAddress;
	}

	public void setStageAddress(String stageAddress) {
		this.stageAddress = stageAddress;
	}

}