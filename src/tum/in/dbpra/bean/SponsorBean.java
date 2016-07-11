package tum.in.dbpra.bean;

public class SponsorBean extends ProviderBean {

	private double amount;
	private String type;
	
	/*private Integer pID;
	private String name;
	private String phoneNumber;
	private String website;
	private String address;
	private String email;
	private String password;*/

	public SponsorBean() {
		super();
	}

	public SponsorBean(double amount, String type) {
		super();
		this.amount = amount;
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}