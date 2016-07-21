package tum.in.dbpra.bean;

public class SaleBean {
	private int soldquantity;
	private double income;
	private String productName, section;
	public int getSoldquantity() {
		return soldquantity;
	}
	public void setSoldquantity(int soldquantity) {
		this.soldquantity = soldquantity;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
}
