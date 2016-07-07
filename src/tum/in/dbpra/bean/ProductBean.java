package tum.in.dbpra.bean;

public class ProductBean {
	private String name, category;
	private int id, quantity;
	private double price;

	public ProductBean() {

	}

	public ProductBean(String name, double price, String category, int quantity) {
		this.name = name;
		this.price = price;
		this.category = category;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}