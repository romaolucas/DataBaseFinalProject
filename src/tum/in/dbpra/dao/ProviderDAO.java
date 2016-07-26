package tum.in.dbpra.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tum.in.dbpra.bean.InstructionBean;
import tum.in.dbpra.bean.ProviderBean;
import tum.in.dbpra.bean.ProviderBean.AppStatus;
import tum.in.dbpra.bean.TimeSlotBeanProvider;
import tum.in.dbpra.dbutils.PGUtils;

public class ProviderDAO {

	/* StringBuffer getPasswordMD5: 
     *returns the MD5 code for a given string, it is used to
     store the password in the database
     * */

    private StringBuffer getPasswordMD5(String password)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte pass[] = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pass.length; i++)
			sb.append(Integer.toString((pass[i] & 0xff) + 0x100, 16).substring(
					1));
		return sb;
	}

	public void registerProvider(ProviderBean provider) {
		String getId = "select max(pid) from provider";
		String registerClient = "insert into provider values (?, ?, ?, ?, ?, ?, ?)";
		Connection con;
		ResultSet rsSelect;
		Statement stmt;
		PreparedStatement pstm;
		try {
            /*
             * connects to the database
             * */
			con = PGUtils.createConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(registerClient);
			stmt = con.createStatement();
			rsSelect = stmt.executeQuery(getId);
			int id = 1;
			if (rsSelect.next())
				id = Integer.parseInt(rsSelect.getString("max")) + 1;
			//add values of the provider created
            pstm.setInt(1, id);
			pstm.setString(2, provider.getName());
			pstm.setString(3, provider.getPhone());
			pstm.setString(4, provider.getWebsite());
			pstm.setString(5, provider.getAddress());
			pstm.setString(6, provider.getEmail());
			pstm.setString(7, getPasswordMD5(provider.getPassword()).toString());
			pstm.executeUpdate();
			con.commit();
			pstm.close();
			rsSelect.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /* List<ProviderBean> getProviders:
     *  get the provider with the respective criterion
     * */
	//This gets providers from the database that have the desired email AND password
	public List<ProviderBean> getProviders(String email, String password) {
		
		Connection connection = null;
		ProviderBean providerBean;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		List<ProviderBean> providerList = new ArrayList<ProviderBean>();
		 String fetchProviderCredentials = "select * from provider where email = ? and password = ?";
			
		try {
			//Create connection
			connection = PGUtils.createConnection();
			//Begin transaction
			connection.setAutoCommit(false);

			// Fetch the provider who has the desired email AND password
			preparedStatement = connection
					.prepareStatement(fetchProviderCredentials);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, getPasswordMD5(password).toString());
			resultSet = preparedStatement.executeQuery();
			//If our resultset has content
			if (resultSet.next()) {
				//That means we have one provider in the list in our case
				do {
					//Prepare the bean by setting the attributes to the retrieved info in DB
					providerBean = new ProviderBean();
					providerBean.setName(resultSet.getString("name"));
					providerBean.setPhone(resultSet.getString("phonenumber"));
					providerBean.setWebsite(resultSet.getString("website"));
					providerBean.setAddress(resultSet.getString("address"));
					providerBean.setEmail(resultSet.getString("email"));
					providerBean.setPassword(resultSet.getString("password"));
					providerBean.setId(resultSet.getInt("pid"));
					providerList.add(providerBean);
				} while (resultSet.next());
				//Close everything
				resultSet.close();
                connection.commit();
				preparedStatement.close();
			} else {
				//If result set is empty then we have no matching 
				providerList = null;
			}
			return providerList;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			PGUtils.closeConnection(connection);
		}
		return providerList;
	}

	//Works the same as the method above, but uses provider id instead of email and password to retrieve the provider
	public List<ProviderBean> getProviders(int pid) {
		Connection connection = null;
		ProviderBean providerBean;
		String query = "select * from provider where pid = ?";
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		List<ProviderBean> providerList = new ArrayList<ProviderBean>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, pid);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					providerBean = new ProviderBean();
					providerBean.setName(resultSet.getString("name"));
					providerBean.setPhone(resultSet.getString("phonenumber"));
					providerBean.setWebsite(resultSet.getString("website"));
					providerBean.setAddress(resultSet.getString("address"));
					providerBean.setEmail(resultSet.getString("email"));
					providerBean.setPassword(resultSet.getString("password"));
					providerBean.setId(resultSet.getInt("pid"));
					providerList.add(providerBean);
				} while (resultSet.next());
				resultSet.close();
				preparedStatement.close();
                connection.commit();
			} else {
				providerList = null;
			}
			return providerList;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			PGUtils.closeConnection(connection);
		}
		return providerList;
	}

	//Works the same as the method above, but uses provider email only to retrieve the provider
	public List<ProviderBean> getProviders(String email) {
		Connection connection = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		ProviderBean providerBean;
		List<ProviderBean> providerList = new ArrayList<ProviderBean>();
		String fetchProviderCredentialsByEmail = "select * from provider where email = ?";

		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);

			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection
					.prepareStatement(fetchProviderCredentialsByEmail);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
					providerBean = new ProviderBean();
					providerBean.setId(resultSet.getInt("pid"));
					providerBean.setName(resultSet.getString("name"));
					providerBean.setPhone(resultSet.getString("phonenumber"));
					providerBean.setWebsite(resultSet.getString("website"));
					providerBean.setAddress(resultSet.getString("address"));
					providerBean.setEmail(resultSet.getString("email"));
					providerBean.setPassword(resultSet.getString("password"));
					//decides whether it is a band or a sponsor
                    String getBand = "select * from band where pid = "
							+ Integer.toString(providerBean.getId());
					ResultSet rsGetBand;
					Statement stmtGetBand;
					stmtGetBand = connection.createStatement();
					rsGetBand = stmtGetBand.executeQuery(getBand);
					if (rsGetBand.next()) {
						providerBean.setCategory("Band");
					} else {
						String getSponsor = "select * from sponsor where pid = "
								+ Integer.toString(providerBean.getId());
						ResultSet rsGetSponsor;
						Statement stmtGetSponsor;
						stmtGetSponsor = connection.createStatement();
						rsGetSponsor = stmtGetSponsor.executeQuery(getSponsor);
						if (rsGetSponsor.next()) {
							providerBean.setCategory("Sponsor");
						}
					}
					providerList.add(providerBean);
				} while (resultSet.next());
				resultSet.close();
				preparedStatement.close();
			} else {
				providerList = null;
			}
			return providerList;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			PGUtils.closeConnection(connection);
		}
		return providerList;
	}


    /*
     * boolean hasApplication:
     * receives a provider and returns
     * whether it has or not an application and the status
     * of it
     * */
	public boolean hasApplication(ProviderBean provider) {
		Connection con;
		String query = "select pid, status, category from application where pid = "
				+ Integer.toString(provider.getId());
		ResultSet rs;
		ResultSet rsStatus;
		Statement stmt;
		Statement stmtStatus;
		boolean hasApplication = false;
		try {
			con = PGUtils.createConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				hasApplication = true;
				String appStatus = rs.getString("status");
				provider.setStatus(appStatus);
				provider.setCategory(rs.getString("category"));
				if (appStatus.equals("Approved"))
					provider.setApplicationStatus(AppStatus.APPROVED);
				else if (appStatus.equals("Declined"))
					provider.setApplicationStatus(AppStatus.DECLINED);
				else if (appStatus.equals("In Process"))
					provider.setApplicationStatus(AppStatus.IN_PROCESS);
				else if (appStatus.equals("Not Submitted"))
					provider.setApplicationStatus(AppStatus.NOT_SUBMITTED);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasApplication;
	}


	//This method retrieves timeslots for a certain provider (band)
	public List<TimeSlotBeanProvider> getTimeSlots(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		TimeSlotBeanProvider timeslotBean;
		List<TimeSlotBeanProvider> timeslotList = new ArrayList<TimeSlotBeanProvider>();
		try {
			connection = PGUtils.createConnection();
			//Begin transaction
			connection.setAutoCommit(false);

			// Fetch the timeslots using the provider id
			String fetchTimeSlots = "select * from timeslot where pid = ?";
			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection.prepareStatement(fetchTimeSlots);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				//If the result set has content
				//Then we have at least one assigned timeslot for the band
				do {
					//Prepare the bean by filling the attributes using the info retrieved from DB
					timeslotBean = new TimeSlotBeanProvider();
					timeslotBean.setId(resultSet.getInt("timeslotid"));
					timeslotBean.setSectionid(resultSet.getInt("sectionid"));
					timeslotBean.setTimebuildup(resultSet
							.getTimestamp("timebuildup"));
					timeslotBean
							.setTimeplay(resultSet.getTimestamp("timeplay"));
					timeslotBean.setTimefinish(resultSet
							.getTimestamp("timefinish"));
					timeslotBean
							.setTimegone(resultSet.getTimestamp("timegone"));
					//Timeslotbean also requires having information about the stage, this is retrieved from DB too
					String getStage = "select * from section where sectionid = "
							+ timeslotBean.getSectionid();
					ResultSet rsGetStage;
					Statement stmtGetStage;
					stmtGetStage = connection.createStatement();
					rsGetStage = stmtGetStage.executeQuery(getStage);
					if (rsGetStage.next()) {
						//If the timeslot is assigned a stage, then we need to find its name using its id
						timeslotBean.setStageName(rsGetStage.getString("name"));
						int areaid = rsGetStage.getInt("areaid");
						String getAddress = "select * from area where areaid = "
								+ areaid;
						ResultSet rsGetAddress;
						Statement stmtGetAddress;
						stmtGetAddress = connection.createStatement();
						rsGetAddress = stmtGetAddress.executeQuery(getAddress);
						if (rsGetAddress.next()) {
							String stageaddress = rsGetAddress
									.getString("address");
							timeslotBean.setStageAddress(stageaddress);
						}
					} else {

					}
					timeslotList.add(timeslotBean);
				} while (resultSet.next());
				//close
				resultSet.close();
				preparedStatement.close();
			} else {
				//This means the band has no timeslots
				timeslotList = null;
			}
			return timeslotList;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			PGUtils.closeConnection(connection);
		}
		return timeslotList;
	}

	//This method gets all the instructions submitted by a certain provider (band)
	public List<InstructionBean> getInstructions(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		InstructionBean instructionBean;
		List<InstructionBean> instructionsList = new ArrayList<InstructionBean>();
		String fetchInstructions = "select * from instruction where pid = ?";

		try {
			connection = PGUtils.createConnection();
			//Begin transaction
			connection.setAutoCommit(false);

			// Fetch the instructions submitted by the provider
			preparedStatement = connection
					.prepareStatement(fetchInstructions);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				//If the provider has submitted instructions, the result set has content
				do {
					//Prepare the instruction bean by filling the attributes using the info retrieved from DB
					instructionBean = new InstructionBean();
					instructionBean.setId(resultSet.getInt("instructionid"));
					instructionBean.setPid(resultSet.getInt("pid"));
					instructionBean.setTitle(resultSet.getString("title"));
					instructionBean.setDescription(resultSet
							.getString("description"));
					instructionBean.setStatus(resultSet.getString("status"));
					instructionBean.setType(resultSet.getString("type"));
					instructionBean.setCreationdate(resultSet
							.getTimestamp("date"));
					//We also need the instruction assignment date, which is stored in the assignment table
					String getAssignment = "select * from assignment where instructionid = "
							+ instructionBean.getId();
					ResultSet rsGetAssignment;
					Statement stmtGetAssignment;
					stmtGetAssignment = connection.createStatement();
					rsGetAssignment = stmtGetAssignment
							.executeQuery(getAssignment);
					if (rsGetAssignment.next()) {
						//If the instruction is already assigned, then the resultset should have some content
						instructionBean.setEmpid(rsGetAssignment.getInt("eid"));
						instructionBean.setAssigneddate(rsGetAssignment
								.getTimestamp("date"));
						//We need the first and last name of the employee to which the instruction was assigned, we get this info uding the emplpyee retrieved earlier
						String getName = "select * from employee where eid = "
								+ instructionBean.getEmpid();
						ResultSet rsGetName;
						Statement stmtGetName;
						stmtGetName = connection.createStatement();
						rsGetName = stmtGetName.executeQuery(getName);
						if (rsGetName.next()) {
							//If we find the name of the employee, then set the relevant attributes in the instructionbean
							String firstname = rsGetName.getString("firstname");
							String lastname = rsGetName.getString("lastname");
							instructionBean.setEmpfirstname(firstname);
							instructionBean.setEmplastname(lastname);
						}
					} else {
						// not assigned yet
					}
					instructionsList.add(instructionBean);
				} while (resultSet.next());
				//close
				resultSet.close();
				preparedStatement.close();
			} else {
				instructionsList = null;
			}
			return instructionsList;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			PGUtils.closeConnection(connection);
		}
		return instructionsList;
	}

}
