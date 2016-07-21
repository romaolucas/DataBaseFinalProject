package tum.in.dbpra.bean;

import java.util.Date;

public class ActivityLogBean {
	private int rfid;
	private int stageid;
	private Date entertime;
	private Date exittime;
	/**
	 * @return the rfid
	 */
	public int getRfid() {
		return rfid;
	}
	/**
	 * @param rfid the rfid to set
	 */
	public void setRfid(int rfid) {
		this.rfid = rfid;
	}
	/**
	 * @return the stageid
	 */
	public int getStageid() {
		return stageid;
	}
	/**
	 * @param stageid the stageid to set
	 */
	public void setStageid(int stageid) {
		this.stageid = stageid;
	}
	/**
	 * @return the entertime
	 */
	public Date getEntertime() {
		return entertime;
	}
	/**
	 * @param entertime the entertime to set
	 */
	public void setEntertime(Date entertime) {
		this.entertime = entertime;
	}
	/**
	 * @return the exittime
	 */
	public Date getExittime() {
		return exittime;
	}
	/**
	 * @param exittime the exittime to set
	 */
	public void setExittime(Date exittime) {
		this.exittime = exittime;
	}
	
}