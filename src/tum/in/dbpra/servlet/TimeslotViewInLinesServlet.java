package tum.in.dbpra.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import tum.in.dbpra.bean.BandBean;
import tum.in.dbpra.bean.FestivalBean;
import tum.in.dbpra.bean.StageBean;
import tum.in.dbpra.bean.TimeslotBean;
import tum.in.dbpra.dao.BandDAO;
import tum.in.dbpra.dao.FestivalDAO;
import tum.in.dbpra.dao.StageDAO;
import tum.in.dbpra.dao.TimeslotDAO;

/**
 * Servlet implementation class TimeslotServlet
 */
@WebServlet("/timeslotviewinlines")
public class TimeslotViewInLinesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimeslotViewInLinesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException {
		try {
        	//set values for Band drop down
			BandDAO bandDao = new BandDAO();
        	ArrayList<BandBean> bands = bandDao.getAllBands();
        	request.setAttribute("bands", bands);
        	
        	//set values for Stage drop down
        	StageDAO stageDao = new StageDAO();
        	ArrayList<StageBean> stages = stageDao.getAllStages();
        	request.setAttribute("stages", stages);
        	
        	//retrieve data about Festival(dates)
        	FestivalDAO festivalDAO = new FestivalDAO();
        	FestivalBean festivalBean = new FestivalBean();
        	festivalDAO.getAllData(festivalBean);
        	request.setAttribute("festival", festivalBean);
        	
        	//collect information about needed timeslots
        	String stageName = null;
        	String bandName = null;
        	Integer stageID = null;
        	Integer bandID = null;
        	Timestamp fromTime = null;
        	Timestamp tillTime = null;
        	Integer fromYear = null;
        	Integer tillYear = null;
        	Integer fromMonth = null;
        	Integer tillMonth = null;
        	Integer fromDay = null;
        	Integer tillDay = null;
        	
        	if(request.getParameter("stages")!=null && !request.getParameter("stages").equals("any")) stageName = request.getParameter("stages");
        	if(stageName!=null) stageID = stageDao.getStageIdByName(stageName);
        	
        	if(request.getParameter("bands")!=null && !request.getParameter("bands").equals("any")) bandName = request.getParameter("bands");
        	if(bandName!=null) bandID = bandDao.getBandIdByName(bandName);
        	
        	if(request.getParameter("fromYear")!=null || request.getParameter("fromMonth")!=null || request.getParameter("fromDay")!=null){
        		fromTime = new Timestamp(1, 1, 1, 0, 0, 0, 0);
        		if(request.getParameter("fromYear")!=null) fromTime.setYear(Integer.parseInt(request.getParameter("fromYear"))-1900);
        		fromYear = fromTime.getYear()+1900;
        		if(request.getParameter("fromMonth")!=null) fromTime.setMonth(Integer.parseInt(request.getParameter("fromMonth"))-1);
        		fromMonth = fromTime.getMonth()+1;
        		if(request.getParameter("fromDay")!=null) fromTime.setDate(Integer.parseInt(request.getParameter("fromDay")));
        		fromDay = fromTime.getDate();
        	}
        	
        	if(request.getParameter("tillYear")!=null || request.getParameter("tillMonth")!=null || request.getParameter("tillDay")!=null){
        		tillTime = new Timestamp(1, 1, 1, 23, 59, 59, 0);
        		if(request.getParameter("tillYear")!=null) tillTime.setYear(Integer.parseInt(request.getParameter("tillYear"))-1900);
        		tillYear = tillTime.getYear()+1900;
        		if(request.getParameter("tillMonth")!=null) tillTime.setMonth(Integer.parseInt(request.getParameter("tillMonth"))-1);
        		tillMonth = tillTime.getMonth()+1;
        		if(request.getParameter("tillDay")!=null) tillTime.setDate(Integer.parseInt(request.getParameter("tillDay")));
        		tillDay = tillTime.getDate();
        	}
        	
        	
        	
        	//find needed timeslots
        	TimeslotDAO timeslotDao = new TimeslotDAO();
        	TimeslotBean timeslotBean = new TimeslotBean();
        	timeslotBean.setSectionID(stageID);
        	timeslotBean.setpID(bandID);
        	timeslotBean.setTimeBuildUp(fromTime);
        	timeslotBean.setTimeGone(tillTime);
        	ArrayList<TimeslotBean> timeslots = timeslotDao.getTimeSlots(timeslotBean);
        	request.setAttribute("timeslots", timeslots);
        	
        	
        	
        	//save chosen attributes
        	request.setAttribute("prevStage", stageName);
        	request.setAttribute("prevBand", bandName);
        	request.setAttribute("prevFromYear", fromYear);
        	request.setAttribute("prevTillYear", tillYear);
        	request.setAttribute("prevFromMonth", fromMonth);
        	request.setAttribute("prevTillMonth", tillMonth);
        	request.setAttribute("prevFromDay", fromDay);
        	request.setAttribute("prevTillDay", tillDay);
        	
        	
    	} catch (Throwable e) {
    		e.printStackTrace();
    		request.setAttribute("error", e.toString() + e.getMessage());
    	}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/TimeslotViewInLines.jsp");
		dispatcher.forward(request, response);
		
		
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
				
			doGet(request, response);
			
	}	
}
