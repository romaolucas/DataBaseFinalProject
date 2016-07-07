package tum.in.dbpra.bean;

public class BandBean extends ProviderBean {
	private double charge;
	private String style, country;
	
	public BandBean() {
		super();
	}

	public BandBean(double charge, String style, String country) {
		this.charge = charge;
		this.style = style;
		this.country = country;
	}
	
	public double getCharge() {
		return charge;
	}

	public void setCharge(double charge) {
		this.charge = charge;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
