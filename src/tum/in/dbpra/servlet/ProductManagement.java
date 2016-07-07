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

import com.sun.istack.internal.logging.Logger;

import tum.in.dbpra.bean.ProductBean;
import tum.in.dbpra.dao.ProductDAO;

/**
 * Servlet implementation class ProductManagement
 */
@WebServlet("/productManagement")
public class ProductManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductManagement() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Logger log = Logger.getLogger(ProductManagement.class);
		log.info("here in prod management");
		int pid = Integer.parseInt(request.getParameter("pid"));
		ProductDAO dao = new ProductDAO();
		List<ProductBean> products = dao.getProducts(pid);
		request.setAttribute("products", products);
		request.setAttribute("pid", pid);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/product-management.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Logger log = Logger.getLogger(ProductManagement.class);
		HttpSession session = request.getSession(false);
		String email = (String) session.getAttribute("email");
		String name = request.getParameter("name");
		int pid = Integer.parseInt(request.getParameter("pid"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		ProductDAO dao = new ProductDAO();
		boolean wasUpdated = dao.updateProduct(email, name, quantity);
		List<ProductBean> products = dao.getProducts(pid);
		request.setAttribute("products", products);
		request.setAttribute("pid", pid);
		request.setAttribute("wasUpdated", wasUpdated);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("product-management.jsp");
		dispatcher.forward(request, response);
	}

}