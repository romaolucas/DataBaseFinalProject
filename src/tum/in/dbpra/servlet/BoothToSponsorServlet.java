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
import tum.in.dbpra.bean.SponsorBean;
import tum.in.dbpra.dao.BoothDAO;
import tum.in.dbpra.dao.SponsorDAO;


/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/boothToSponsor")
public class BoothToSponsorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoothToSponsorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//set values for Booth drop down
		    BoothDAO boothDAO = new BoothDAO();
        	ArrayList<BoothBean> boothes = boothDAO.getFreeBoothes();
        	request.setAttribute("boothes", boothes);
        	
        	//set values for Sponsor drop down
        	SponsorDAO sponsorDAO = new SponsorDAO();
        	ArrayList<SponsorBean> sponsors = sponsorDAO.getAllSponsor();
        	request.setAttribute("sponsors", sponsors);
    	
    	} catch (Throwable e) {
    		e.printStackTrace();
    		request.setAttribute("error", e.toString() + e.getMessage());
    	}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/BoothToSponsorAssign.jsp");
		dispatcher.forward(request, response);
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//set values for Booth drop down
			BoothDAO boothDAO = new BoothDAO();
        	BoothBean booth = new BoothBean();
        	booth.setSectionID(Integer.parseInt(request.getParameter("boothes")));
        	
        	//set values for Sponsor drop down
        	SponsorDAO sponsorDAO = new SponsorDAO();
        	SponsorBean sponsor = new SponsorBean();
        	sponsor.setId(Integer.parseInt(request.getParameter("sponsors")));
        
        	//assign Booth to sponsor
        	if(boothDAO.assignBooth(sponsor, booth))
        		JOptionPane.showMessageDialog(null, "Booth was successfully assigned to chosen Sponsor!");
        	else JOptionPane.showMessageDialog(null, "Unfortunately booth can't be assigned to chosen Sponsor!");
        	
        	
    	} catch (Throwable e) {
    		e.printStackTrace();
    		request.setAttribute("error", e.toString() + e.getMessage());
    	}
		
		doGet(request, response);
		
	}

}
