package tum.in.dbpra.bean;

import java.sql.Timestamp;

public class InstructionBean {
	protected int id;
	protected int pid;
	protected String title;
	protected String description;
	protected String status;
	protected String type;
	protected Timestamp creationdate;
	protected int empid;
	protected String empfirstname;
	protected String emplastname;
	protected Timestamp assigneddate;

	public InstructionBean() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Timestamp creationdate) {
		this.creationdate = creationdate;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getEmpfirstname() {
		return empfirstname;
	}

	public void setEmpfirstname(String empfirstname) {
		this.empfirstname = empfirstname;
	}

	public String getEmplastname() {
		return emplastname;
	}

	public void setEmplastname(String emplastname) {
		this.emplastname = emplastname;
	}

	public Timestamp getAssigneddate() {
		return assigneddate;
	}

	public void setAssigneddate(Timestamp assigneddate) {
		this.assigneddate = assigneddate;
	}

}