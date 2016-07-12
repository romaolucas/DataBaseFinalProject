package tum.in.dbpra.bean;

import java.util.Date;

public class ProductPurchaseBean {
private String productName;
private String productCategory;
private Date paymentDate;
private double totalPrice;
private int quantity;

/**
 * @return the productName
 */
public String getProductName() {
	return productName;
}
/**
 * @param productName the productName to set
 */
public void setProductName(String productName) {
	this.productName = productName;
}
/**
 * @return the productCategory
 */
public String getProductCategory() {
	return productCategory;
}
/**
 * @param productCategory the productCategory to set
 */
public void setProductCategory(String productCategory) {
	this.productCategory = productCategory;
}
/**
 * @return the paymentDate
 */
public Date getPaymentDate() {
	return paymentDate;
}
/**
 * @param paymentDate the paymentDate to set
 */
public void setPaymentDate(Date paymentDate) {
	this.paymentDate = paymentDate;
}
/**
 * @return the totalPrice
 */
public double getTotalPrice() {
	return totalPrice;
}
/**
 * @param totalPrice the totalPrice to set
 */
public void setTotalPrice(double totalPrice) {
	this.totalPrice = totalPrice;
}
/**
 * @return the quantity
 */
public int getQuantity() {
	return quantity;
}
/**
 * @param quantity the quantity to set
 */
public void setQuantity(int quantity) {
	this.quantity = quantity;
}

	
}
