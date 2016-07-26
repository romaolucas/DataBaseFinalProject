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
	public List<ProviderBean> getProviders(String email, String password) {
		Connection connection = null;
		ProviderBean providerBean;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		List<ProviderBean> providerList = new ArrayList<ProviderBean>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);

			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection
					.prepareStatement(PGUtils.fetchProviderCredentials);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, getPasswordMD5(password).toString());
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
                con.commit();
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
                con.commit();
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

	public List<ProviderBean> getProviders(String email) {
		Connection connection = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		ProviderBean providerBean;
		List<ProviderBean> providerList = new ArrayList<ProviderBean>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);

			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection
					.prepareStatement(PGUtils.fetchProviderCredentialsByEmail);
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


	public List<TimeSlotBeanProvider> getTimeSlots(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		TimeSlotBeanProvider timeslotBean;
		List<TimeSlotBeanProvider> timeslotList = new ArrayList<TimeSlotBeanProvider>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);

			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection
					.prepareStatement(PGUtils.fetchTimeSlots);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
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
					String getStage = "select * from section where sectionid = "
							+ timeslotBean.getSectionid();
					ResultSet rsGetStage;
					Statement stmtGetStage;
					stmtGetStage = connection.createStatement();
					rsGetStage = stmtGetStage.executeQuery(getStage);
					if (rsGetStage.next()) {
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
				resultSet.close();
				preparedStatement.close();
			} else {
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

	public List<InstructionBean> getInstructions(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		InstructionBean instructionBean;
		List<InstructionBean> instructionsList = new ArrayList<InstructionBean>();
		try {
			connection = PGUtils.createConnection();
			connection.setAutoCommit(false);

			// Fetch supplier key from the supplier table using the supplier
			// name that user provides as input
			preparedStatement = connection
					.prepareStatement(PGUtils.fetchInstructions);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				do {
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
					String getAssignment = "select * from assignment where instructionid = "
							+ instructionBean.getId();
					ResultSet rsGetAssignment;
					Statement stmtGetAssignment;
					stmtGetAssignment = connection.createStatement();
					rsGetAssignment = stmtGetAssignment
							.executeQuery(getAssignment);
					if (rsGetAssignment.next()) {
						instructionBean.setEmpid(rsGetAssignment.getInt("eid"));
						instructionBean.setAssigneddate(rsGetAssignment
								.getTimestamp("date"));
						String getName = "select * from employee where eid = "
								+ instructionBean.getEmpid();
						ResultSet rsGetName;
						Statement stmtGetName;
						stmtGetName = connection.createStatement();
						rsGetName = stmtGetName.executeQuery(getName);
						if (rsGetName.next()) {
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
