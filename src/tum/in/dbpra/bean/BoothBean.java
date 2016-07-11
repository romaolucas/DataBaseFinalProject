package tum.in.dbpra.bean;

public class BoothBean {
	private Integer sectionID;
	private	String equipment;
	private	String service;
	private	String type;
	private Integer areaID;
	private String name;
	private Integer metersquarres;
	
	private Integer pID;
	
	public BoothBean(){}

	public Integer getSectionID() {
		return sectionID;
	}

	public void setSectionID(Integer sectionID) {
		this.sectionID = sectionID;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getAreaID() {
		return areaID;
	}

	public void setAreaID(Integer areaID) {
		this.areaID = areaID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMetersquarres() {
		return metersquarres;
	}

	public void setMetersquarres(Integer metersquarres) {
		this.metersquarres = metersquarres;
	}

	public Integer getpID() {
		return pID;
	}

	public void setpID(Integer pID) {
		this.pID = pID;
	}
	
}
