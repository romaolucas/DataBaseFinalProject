package tum.in.dbpra.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import tum.in.dbpra.bean.BoothBean;
import tum.in.dbpra.dao.BoothDAO;



/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/boothView")
public class BoothViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoothViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
        	BoothDAO boothDAO = new BoothDAO();
        	ArrayList<BoothBean> boothes = boothDAO.getAllBoothes();
        	request.setAttribute("boothes", boothes);
        	
    	} catch (Throwable e) {
    		e.printStackTrace();
    		request.setAttribute("error", e.toString() + e.getMessage());
    	}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/BoothView.jsp");
		dispatcher.forward(request, response);
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		
	}

}
