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

import tum.in.dbpra.bean.SaleBean;
import tum.in.dbpra.dao.SaleDAO;

import com.sun.istack.internal.logging.Logger;

/**
 * Servlet implementation class SalesManagement
 */
@WebServlet("/salesManagement")
public class SalesManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SalesManagement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		int pid = Integer.parseInt(request.getParameter("pid"));
		SaleDAO dao = new SaleDAO();
		List<SaleBean> sales = dao.getSalesForProvider(pid);
		request.setAttribute("pid", pid);
		request.setAttribute("sales", sales);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("sales-view.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
