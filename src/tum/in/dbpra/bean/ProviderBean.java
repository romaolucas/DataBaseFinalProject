package tum.in.dbpra.bean;

public class ProviderBean {
	public enum AppStatus { 
		NOT_SUBMITTED, IN_PROCESS, APPROVED, DECLINED
		}
	
    protected String name, password, email, phone, address, website;
    protected int id;
	protected AppStatus applicationStatus = AppStatus.NOT_SUBMITTED;
	protected String category;
	protected String status;
    
    public ProviderBean(){

    }
    public ProviderBean(String name, String password, String email, String phone, String address, String website) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.website = website;
    }
   
   
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public AppStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(AppStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
}