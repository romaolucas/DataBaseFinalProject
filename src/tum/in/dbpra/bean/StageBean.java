package tum.in.dbpra.bean;

public class StageBean {

	private Integer sectionID;
	private Integer capacity;
	private Integer areaID;
	private String name;
	private Integer metersquarres;

	public StageBean() {
	}

	public Integer getSectionID() {
		return sectionID;
	}

	public void setSectionID(Integer sectionID) {
		this.sectionID = sectionID;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
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

	public Integer getAreaID() {
		return areaID;
	}

	public void setAreaID(Integer areaID) {
		this.areaID = areaID;
	}

}