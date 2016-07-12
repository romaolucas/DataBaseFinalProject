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
import tum.in.dbpra.dao.BandDAO.BandNotFoundException;
import tum.in.dbpra.dao.FestivalDAO;
import tum.in.dbpra.dao.FestivalDAO.FestivalNotFoundException;
import tum.in.dbpra.dao.StageDAO;
import tum.in.dbpra.dao.StageDAO.StageNotFoundException;
import tum.in.dbpra.dao.TimeslotDAO;

/**
 * Servlet implementation class TimeslotServlet
 */
@WebServlet("/timeslotAssign")
public class TimeslotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Integer twentyMinutes = 1200000;
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimeslotServlet() {
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
        	Integer stageID = stages.get(0).getSectionID();
        	Integer bandID = bands.get(0).getpID();
        	
        	
        	//find needed timeslots
        	TimeslotDAO timeslotDao = new TimeslotDAO();
        	TimeslotBean timeslotBean = new TimeslotBean();
        	timeslotBean.setSectionID(stageID);
        	ArrayList<TimeslotBean> timeslots = timeslotDao.getTimeSlotsByStage(stageID);
        	request.setAttribute("bookedTimeslots", timeslots);
        	
        	
    	} catch (Throwable e) {
    		e.printStackTrace();
    		request.setAttribute("error", e.toString() + e.getMessage());
    	}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/TimeslotAssign.jsp");
		dispatcher.forward(request, response);
		
		
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String checkButton = request.getParameter("Assign");
		String[] choosenTimeCells = request.getParameterValues("timecell");
		
		if(checkButton!=null){
			
			BandDAO bandDao = new BandDAO();
			String bandName = request.getParameter("bands");
	    	Integer bandID = null;
	    	
	    	StageDAO stageDao = new StageDAO();
			String stageName = request.getParameter("stages");
	    	Integer stageID = null;
	    	
	    	TimeslotDAO timeslotDao = new TimeslotDAO();
	    	TimeslotBean timeslotBean = new TimeslotBean();
	    	
	    	if(choosenTimeCells!= null && choosenTimeCells.length!=0)	{	
	    		try {
	    			Integer insertions = 0;
	    			
	    			for(int i=0; i<choosenTimeCells.length; i++){
							bandID = bandDao.getBandIdByName(bandName);
							stageID = stageDao.getStageIdByName(stageName);
							timeslotBean.setpID(bandID);
					    	timeslotBean.setSectionID(stageID);
					    	
					    	Integer hour = 0;
					    	Integer day = 1;
					    	Integer month = 1;
					    	Integer year = 1;
					    	
					    	String[] data = choosenTimeCells[i].split("_");
					    	
					    	//retrieve data about Festival(dates)
				        	FestivalDAO festivalDAO = new FestivalDAO();
				        	FestivalBean festivalBean = new FestivalBean();
				        	festivalDAO.getAllData(festivalBean);
				        	
				        	hour = Integer.parseInt(data[0]);
					    	day= Integer.parseInt(data[1]);
					    	month = festivalBean.getStartDate().getMonth()+1;
					    	year = festivalBean.getStartDate().getYear()+1900;
					    	
					    	Timestamp t1 = new Timestamp(year-1900, month-1, day, hour, 0, 0, 0);
					    	Timestamp t2 = new Timestamp(year-1900, month-1, day, hour, 20, 0, 0);
					    	Timestamp t3 = new Timestamp(year-1900, month-1, day, hour, 40, 0, 0);
					    	Timestamp t4 = new Timestamp(year-1900, month-1, day, hour+1, 00, 0, 0);
					    	
					    	timeslotBean.setTimeBuildUp(t1);
					    	timeslotBean.setTimePlay(t2);
					    	timeslotBean.setTimeFinish(t3);
					    	timeslotBean.setTimeGone(t4);
	    			
	    			

				   	if(timeslotDao.insertTimeslot(timeslotBean))
				   		insertions++;
				   		
	    		}
		    		if(insertions>0)
					   	JOptionPane.showMessageDialog(null, insertions+ " timeslot(s) successfully added!");	
					else
					   	JOptionPane.showMessageDialog(null, "Timeslot(s) couldn't be added. Sorry!");	
	    	}
	    		catch (ClassNotFoundException | SQLException | BandNotFoundException | StageNotFoundException  e){} catch (FestivalNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			
	    	doGet(request, response);
	    }
	}
		else {
			try {
	        	BandDAO bandDao = new BandDAO();
	        	ArrayList<BandBean> bands = bandDao.getAllBands();
	        	request.setAttribute("bands", bands);
	        	
	        	StageDAO stageDao = new StageDAO();
	        	ArrayList<StageBean> stages = stageDao.getAllStages();
	        	request.setAttribute("stages", stages);
	        	
	        	Integer stageID = stageDao.getStageIdByName(request.getParameter("stages"));
	        	String stageName = request.getParameter("stages");
	        	String bandName = request.getParameter("bands");
	        	
	        	TimeslotDAO timeslotDao = new TimeslotDAO();
	        	ArrayList<TimeslotBean> timeslots = timeslotDao.getTimeSlotsByStage(stageID);
	        	request.setAttribute("bookedTimeslots", timeslots);
	        	
	        	FestivalDAO festivalDAO = new FestivalDAO();
	        	FestivalBean festivalBean = new FestivalBean();
	        	festivalDAO.getAllData(festivalBean);
	        	
	        	
	        			
	        	request.setAttribute("festival", festivalBean);
	        	request.setAttribute("prevStage", stageName);
	        	request.setAttribute("prevBand", bandName);
	        	
	        	
	        	
	    	} catch (Throwable e) {
	    		e.printStackTrace();
	    		request.setAttribute("error", e.toString() + e.getMessage());
	    	}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/TimeslotAssign.jsp");
			dispatcher.forward(request, response);
			
		}
		
		
		/*else if(button_1_1!=null){
			System.out.println("first button is clicked!");
		}*/
		
		
	}
	
	
}
