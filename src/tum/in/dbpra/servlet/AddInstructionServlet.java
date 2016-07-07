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
			HttpSession session = request.getSession(false);
			String email = (String) session.getAttribute("email");
			ProviderDAO providerDAO = new ProviderDAO();
			List<ProviderBean> providerList = providerDAO.getProviders(email);
			ProviderBean provider = providerList.get(0);
			int pid = provider.getId();
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String status = "Sent";
			String type = "From Band";
			InstructionDAO instructionDAO = new InstructionDAO();
			instructionDAO.insertInstruction(pid, title, description, status,
					type);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/add-instruction.jsp");
			dispatcher.forward(request, response);
		} catch (Throwable e) {
			request.setAttribute("error", e.getMessage());

		}
	}
}