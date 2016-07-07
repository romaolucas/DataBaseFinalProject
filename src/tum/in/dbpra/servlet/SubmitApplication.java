package tum.in.dbpra.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.istack.internal.logging.Logger;

import tum.in.dbpra.bean.BandBeanProvider;
import tum.in.dbpra.bean.ProviderBean;
import tum.in.dbpra.bean.ProviderBean.AppStatus;
import tum.in.dbpra.bean.SponsorBean;
import tum.in.dbpra.dao.BandDAOProvider;
import tum.in.dbpra.dao.ProviderDAO;
import tum.in.dbpra.dao.SponsorDAO;

/**
 * Servlet implementation class SubmitApplication
 */
@WebServlet("/submitApplication")
public class SubmitApplication extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubmitApplication() {
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
		int pid = Integer.parseInt(request.getParameter("pid"));
		boolean isBand = true;
		Logger log = Logger.getLogger(SubmitApplication.class);
		String comment = request.getParameter("comment");
		if ("sponsor".equals(request.getParameter("category")))
			isBand = false;
		if (isBand) {
			String style = request.getParameter("style");
			double charge = Double.parseDouble(request.getParameter("charge"));
			String country = request.getParameter("country");
			BandBeanProvider band = new BandBeanProvider(charge, style, country);
			band.setId(pid);
			BandDAOProvider dao = new BandDAOProvider();
			dao.submitBandApp(band, comment);
			band.setApplicationStatus(AppStatus.IN_PROCESS);
		} else {
			String type = request.getParameter("type");
			double amount = Double.parseDouble(request.getParameter("amount"));
			SponsorBean sponsor = new SponsorBean(amount, type);
			sponsor.setId(pid);
			SponsorDAO dao = new SponsorDAO();
			dao.submitSponsorApp(sponsor, comment);
			sponsor.setApplicationStatus(AppStatus.IN_PROCESS);
			sponsor.setStatus("In Process");
			boolean hasApplication = true;
			ProviderDAO pdao = new ProviderDAO();
			List<ProviderBean> providers = pdao.getProviders(pid);
			request.setAttribute("providers", providers);
			log.info("name: " + providers.get(0).getName());
			request.setAttribute("application", hasApplication);
		}
		ProviderDAO pdao = new ProviderDAO();
		List<ProviderBean> providers = pdao.getProviders(pid);
		boolean hasApplication = true;
		providers.get(0).setApplicationStatus(AppStatus.IN_PROCESS);
		providers.get(0).setStatus("In Process");
		request.setAttribute("application", hasApplication);
		request.setAttribute("providers", providers);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/welcome-provider.jsp");
		dispatcher.forward(request, response);
	}
}