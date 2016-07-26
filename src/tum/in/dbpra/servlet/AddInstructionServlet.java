package tum.in.dbpra.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tum.in.dbpra.bean.ProviderBean;
import tum.in.dbpra.dao.ProviderDAO;
import tum.in.dbpra.dao.InstructionDAO;
import tum.in.dbpra.bean.InstructionBean;

/**
 * Servlet implementation class AddInstructionServlet
 */
@WebServlet("/addInstruction")
public class AddInstructionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean loginSuccessful = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddInstructionServlet() {
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			//Try to retrieve the session
			HttpSession session = request.getSession(false);
			//Retrieve the email from the session
			//Email identifies the user
			String email = (String) session.getAttribute("email");
			//Create ProviderDAO
			ProviderDAO providerDAO = new ProviderDAO();
			//Use ProviderDAO to get the logged user information using the email only
			List<ProviderBean> providerList = providerDAO.getProviders(email);
			//Select the only user in the list
			ProviderBean provider = providerList.get(0);
			//Get the provider ID from the retrieved information
			int pid = provider.getId();
			//Retrieve the title and description of the instruction from the request
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			//Set the status of the instruction to sent
			String status = "Sent";
			//Set the type of the instruction to "from band"
			//This could be different for when the organizer sends a note to an employee - not implemented - but very possible
			String type = "From Band";
			//Create instruction DAO
			InstructionDAO instructionDAO = new InstructionDAO();
			//Use the instructionDAO to Call the insertInstruction method with the following argument
			instructionDAO.insertInstruction(pid, title, description, status,
					type);
			//After successful insertion of the instruction in the database, we redirect the user to the same page
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/add-instruction.jsp");
			dispatcher.forward(request, response);
		} catch (Throwable e) {
			//Else we throw an exception
			request.setAttribute("error", e.getMessage());

		}
	}
}