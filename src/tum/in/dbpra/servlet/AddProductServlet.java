package tum.in.dbpra.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tum.in.dbpra.bean.ProductBean;
import tum.in.dbpra.dao.ProductDAO;

import com.sun.istack.internal.logging.Logger;

/**
 * Servlet implementation class AddProductServlet
 */
@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddProductServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Logger log = Logger.getLogger(ProductManagement.class);
		int pid = Integer.parseInt(request.getParameter("pid"));
		String name = request.getParameter("name");
		double price = Double.parseDouble(request.getParameter("price"));
		String category = request.getParameter("category");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		ProductBean product = new ProductBean(name, price, category, quantity);
		ProductDAO dao = new ProductDAO();
		dao.addProduct(pid, product);
		//updates the list of products
        List<ProductBean> products = dao.getProducts(pid);
		request.setAttribute("products", products);
		request.setAttribute("pid", pid);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("product-management.jsp");
		dispatcher.forward(request, response);
	}

}
