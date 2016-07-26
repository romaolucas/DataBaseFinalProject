package tum.in.dbpra.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tum.in.dbpra.dbutils.PGUtils;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/confirmRegistration")
//For new user registration
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean registrationSuccessful=false;
	Connection connection;
	PreparedStatement preparedStatement;
		

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try {
			//query to insert visitor data into the database
			String insertVisitor = "INSERT INTO visitor(visitorid, firstname, lastname, username, password, address,phone, email)"
					+ "VALUES (9, ?, ?, ?, ?, ?, ? ,?);";
			String firstname=request.getParameter("firstnamesignup");
			System.out.println("Fistname:"+firstname);
			String lastname=request.getParameter("lastnamesignup");
			String username=request.getParameter("usernamesignup");
			String password=request.getParameter("passwordsignup");
			String email=request.getParameter("emailsignup");
			System.out.println("Email:"+email);
			String address=request.getParameter("addresssignup");
			String phone=request.getParameter("phonesignup");
			
			connection=PGUtils.createConnection();
			
			preparedStatement=connection.prepareStatement(insertVisitor);
			preparedStatement.setString(1, firstname);
			preparedStatement.setString(2, lastname);
			preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);	
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, phone);
            preparedStatement.setString(7, email);
			
            int rows=preparedStatement.executeUpdate();
			if(rows>0){
				   registrationSuccessful=true;
				   	}	
			System.out.println("Registration status: "+rows);
			
		} catch (Exception e) {
			String message=e.getMessage();
			e.printStackTrace();
			System.out.println("Error message:"+message);
			request.setAttribute("error", message);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			request.setAttribute("errormessage", message);
			dispatcher.forward(request, response);
		}
		request.setAttribute("loginStatus", registrationSuccessful);
		if (registrationSuccessful) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/welcomeVisitor.jsp");
			dispatcher.forward(request, response);
		}
	}
}