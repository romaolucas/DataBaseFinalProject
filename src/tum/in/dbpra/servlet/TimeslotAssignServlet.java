package tum.in.dbpra.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import tum.in.dbpra.bean.FestivalBean;
import tum.in.dbpra.bean.BandBean;
import tum.in.dbpra.bean.StageBean;
import tum.in.dbpra.bean.TimeslotBean;

import tum.in.dbpra.dao.FestivalDAO;
import tum.in.dbpra.dao.BandDAO;
import tum.in.dbpra.dao.StageDAO;
import tum.in.dbpra.dao.TimeslotDAO;

import tum.in.dbpra.dao.FestivalDAO.FestivalNotFoundException;
import tum.in.dbpra.dao.BandDAO.BandNotFoundException;
import tum.in.dbpra.dao.StageDAO.StageNotFoundException;
import tum.in.dbpra.dao.TimeslotDAO.TimeslotNotFoundException;

/**
 * Servlet implementation class TimeslotServlet
 */
@WebServlet("/timeslotAssign")
public class TimeslotAssignServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimeslotAssignServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException {
		
		doPost(request, response);
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Integer stageID = null;
    	Integer bandID = null;

    	TimeslotDAO timeslotDao = new TimeslotDAO();
    	TimeslotBean timeslotBean = new TimeslotBean();
		
    	
		try {
			//retrieve data about Festival(dates)
	       	FestivalDAO festivalDAO = new FestivalDAO();
	       	FestivalBean festivalBean = new FestivalBean();
	       	festivalDAO.getAllData(festivalBean);
	       	request.setAttribute("festival", festivalBean);
	       	
	       	//set values for Band drop down
			BandDAO bandDao = new BandDAO();
        	ArrayList<BandBean> bands = bandDao.getAllBands();
        	request.setAttribute("bands", bands);
        	
        	//set values for Stage drop down
        	StageDAO stageDao = new StageDAO();
        	ArrayList<StageBean> stages = stageDao.getAllStages();
        	request.setAttribute("stages", stages);
        	
        	//retrieve the value of band
        	if(request.getParameter("bands")!=null)
        		bandID = Integer.parseInt(request.getParameter("bands"));
        	else bandID = bands.get(0).getpID();
    	
        	//retrieve the value of stage
        	if(request.getParameter("stages")!=null)
        		stageID = Integer.parseInt(request.getParameter("stages"));
        	else stageID = stages.get(0).getSectionID();
        	
        	//find timeslots for current stage
        	ArrayList<TimeslotBean> timeslots = timeslotDao.getTimeSlotsByStage(stageID);
        	request.setAttribute("bookedTimeslots", timeslots);

        	//retrieve values of "Assign" button and checked checkboxes in the table
        	String checkButton = request.getParameter("Assign");
        	String[] choosenTimeCells = request.getParameterValues("timecell");
        	
        	//assign timeslots
        	if(checkButton!=null && choosenTimeCells!= null && choosenTimeCells.length!=0){
		    			
        		//prepare variables for timeslots
        		ArrayList<TimeslotBean> timeslotsToAdd = new ArrayList<TimeslotBean>();
		   		Integer count = 0;
		    			
		    	//for each chosen checkbox
	 			for(int i=0; i<choosenTimeCells.length; i++){
		    		
	 				//create an instance of timeslot with chosen band and stage
	 				timeslotBean = new TimeslotBean();
		    		timeslotBean.setpID(bandID);
				   	timeslotBean.setSectionID(stageID);
					
				   	//prepare variables for time values
				   	Integer hour = 0;
				   	Integer day = 1;
				   	Integer month = 1;
				   	Integer year = 1;
						
					//retrieve time values
			    	String[] data = choosenTimeCells[i].split("_");	    	
			    	hour = Integer.parseInt(data[0]);
			    	day= Integer.parseInt(data[1]);
			    	month = festivalBean.getStartDate().getMonth()+1;
			    	year = festivalBean.getStartDate().getYear()+1900;
					
			    	//convert time values to Timestamps
					Timestamp t1 = new Timestamp(year-1900, month-1, day, hour, 00, 00, 0);
					Timestamp t2 = new Timestamp(year-1900, month-1, day, hour, 20, 00, 0);
					Timestamp t3 = new Timestamp(year-1900, month-1, day, hour, 40, 00, 0);
					Timestamp t4 = new Timestamp(year-1900, month-1, day, hour+1, 00, 00, 0);
						
					//set those Timestamps as parameters to the timeslot
					timeslotBean.setTimeBuildUp(t1);
					timeslotBean.setTimePlay(t2);
					timeslotBean.setTimeFinish(t3);
					timeslotBean.setTimeGone(t4);
		    			
					//add this timeslot to list of timeslots
					timeslotsToAdd.add(timeslotBean);
					count++;
		    							   		
		    	}
		    			
	 			//try to add all timeslots to the database
	 			if(timeslotDao.insertTimeslots(timeslotsToAdd)){
	 				JOptionPane.showMessageDialog(null,"The assignment was done successfully!");
	 				timeslots = timeslotDao.getTimeSlotsByStage(stageID);
	 				request.setAttribute("bookedTimeslots", timeslots);
	 			}
						
		    }
        	
        	//save current values for the next screen
        	request.setAttribute("prevStage", stageID);
        	request.setAttribute("prevBand", bandID);
        	
		    		
		}
		catch (ClassNotFoundException | SQLException | FestivalNotFoundException | BandNotFoundException | StageNotFoundException | TimeslotNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("error", e.toString() + e.getMessage());
    	} 
			
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/TimeslotAssign.jsp");
		dispatcher.forward(request, response);
		
		
		
	}
	
	
}
