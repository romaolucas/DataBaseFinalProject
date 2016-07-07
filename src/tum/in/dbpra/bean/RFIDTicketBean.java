package tum.in.dbpra.bean;

import java.util.Date;

public class RFIDTicketBean {
	private boolean status;
	private double balance;
	private Date activationDate;
	private int rfid;

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the activationDate
	 */
	public Date getActivationDate() {
		return activationDate;
	}

	/**
	 * @param activationDate
	 *            the activationDate to set
	 */
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	/**
	 * @return the rfid
	 */
	public int getRfid() {
		return rfid;
	}

	/**
	 * @param rfid
	 *            the rfid to set
	 */
	public void setRfid(int rfid) {
		this.rfid = rfid;
	}
}