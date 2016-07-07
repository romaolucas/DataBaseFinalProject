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

import tum.in.dbpra.bean.InstructionBean;
import tum.in.dbpra.bean.ProviderBean;
import tum.in.dbpra.bean.TimeSlotBean;
import tum.in.dbpra.dao.ProviderDAO;

/**
 * Servlet implementation class ManageInstructionsServlet
 */
@WebServlet("/ManageInstructionsServlet")
public class ManageInstructionsServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private boolean loginSuccessful = false;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManageInstructionsServlet() {
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
	    	List<InstructionBean> intsructions = providerDAO.getInstructions(providerList.get(0).getId());
			if(intsructions == null){
				//No instructions made
				request.setAttribute("noinstructions", "yes");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/view-instructions.jsp");
				dispatcher.forward(request, response);
			}
			else if(intsructions.size()>0){
				request.setAttribute("noinstructions", "no");
				request.setAttribute("instructions", intsructions);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/view-instructions.jsp");
				dispatcher.forward(request, response);
			}else{
				
			}
		} catch (Throwable e) {
			request.setAttribute("error", e.getMessage());
		
		}
		}
}