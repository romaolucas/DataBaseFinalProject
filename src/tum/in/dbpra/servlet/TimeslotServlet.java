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
import tum.in.dbpra.dao.BandDAO.BandNotFoundException;
import tum.in.dbpra.dao.StageDAO.StageNotFoundException;




/**
 * Servlet implementation class TimeslotServlet
 */
@WebServlet("/timeslotAssign")
public class TimeslotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Integer twentyMinutes = 1200000;
	private static Date startDate = new Date(0);
	private static Date endDate = new Date(0);
	private static Time startTime = new Time(0);
	private static Time finishTime = new Time(0);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TimeslotServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			NumberFormatException {
		try {
			BandDAO bandDao = new BandDAO();
			ArrayList<BandBean> bands = bandDao.getAllBands();
			request.setAttribute("bands", bands);

			StageDAO stageDao = new StageDAO();
			ArrayList<StageBean> stages = stageDao.getAllStages();
			request.setAttribute("stages", stages);

			Integer stageID = stageDao
					.getStageIdByName(stages.get(0).getName());
			String stageName = stages.get(0).getName();

			TimeslotDAO timeslotDao = new TimeslotDAO();
			ArrayList<TimeslotBean> timeslots = timeslotDao
					.getTimeSlotsByStage(stageID);
			request.setAttribute("bookedTimeslots", timeslots);

			FestivalDAO festivalDAO = new FestivalDAO();
			FestivalBean festivalBean = new FestivalBean();
			festivalDAO.getAllData(festivalBean);

			startDate = festivalBean.getStartDate();
			endDate = festivalBean.getEndDate();
			startTime = festivalBean.getStartTime();
			finishTime = festivalBean.getEndTime();

			request.setAttribute("festival", festivalBean);
			request.setAttribute("prevStage", stageName);

		} catch (Throwable e) {
			e.printStackTrace();
			request.setAttribute("error", e.toString() + e.getMessage());
		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/TimeslotAssign.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String checkButton = request.getParameter("Check");
		String[] choosenTimeCells = request.getParameterValues("timecell");

		if (checkButton != null) {

			BandDAO bandDao = new BandDAO();
			String bandName = request.getParameter("bands");
			Integer bandID = null;

			StageDAO stageDao = new StageDAO();
			String stageName = request.getParameter("stages");
			Integer stageID = null;

			TimeslotDAO timeslotDao = new TimeslotDAO();
			TimeslotBean timeslotBean = new TimeslotBean();

			if (choosenTimeCells != null && choosenTimeCells.length != 0) {
				try {
					Integer insertions = 0;

					for (int i = 0; i < choosenTimeCells.length; i++) {
						bandID = bandDao.getBandIdByName(bandName);
						stageID = stageDao.getStageIdByName(stageName);
						timeslotBean.setpID(bandID);
						timeslotBean.setSectionID(stageID);

						Integer hour = 0;
						Integer day = 1;
						String[] data = choosenTimeCells[i].split("_");

						hour = Integer.parseInt(data[0]);
						day = Integer.parseInt(data[1]);

						Timestamp t1 = new Timestamp(2016, 8, day, hour, 0, 0,
								0);
						Timestamp t2 = new Timestamp(2016, 8, day, hour, 20, 0,
								0);
						Timestamp t3 = new Timestamp(2016, 8, day, hour, 40, 0,
								0);
						Timestamp t4 = new Timestamp(2016, 8, day, hour + 1,
								00, 0, 0);

						timeslotBean.setTimeBuildUp(t1);
						timeslotBean.setTimePlay(t2);
						timeslotBean.setTimeFinish(t3);
						timeslotBean.setTimeGone(t4);

						if (timeslotDao.insertTimeslot(timeslotBean))
							insertions++;

					}
					if (insertions > 0)
						JOptionPane.showMessageDialog(null, insertions
								+ " timeslot(s) successfully added!");
					else
						JOptionPane.showMessageDialog(null,
								"Timeslot(s) couldn't be added. Sorry!");
				} catch (ClassNotFoundException | SQLException
						| BandNotFoundException | StageNotFoundException e) {
					
					System.out.print(e.fillInStackTrace());
				}

				doGet(request, response);
			}
		} else {
			try {
				BandDAO bandDao = new BandDAO();
				ArrayList<BandBean> bands = bandDao.getAllBands();
				request.setAttribute("bands", bands);

				StageDAO stageDao = new StageDAO();
				ArrayList<StageBean> stages = stageDao.getAllStages();
				request.setAttribute("stages", stages);

				Integer stageID = stageDao.getStageIdByName(request
						.getParameter("stages"));
				String stageName = request.getParameter("stages");

				TimeslotDAO timeslotDao = new TimeslotDAO();
				ArrayList<TimeslotBean> timeslots = timeslotDao
						.getTimeSlotsByStage(stageID);
				request.setAttribute("bookedTimeslots", timeslots);

				FestivalDAO festivalDAO = new FestivalDAO();
				FestivalBean festivalBean = new FestivalBean();
				festivalDAO.getAllData(festivalBean);

				startDate = festivalBean.getStartDate();
				endDate = festivalBean.getEndDate();
				startTime = festivalBean.getStartTime();
				finishTime = festivalBean.getEndTime();

				request.setAttribute("festival", festivalBean);
				request.setAttribute("prevStage", stageName);

			} catch (Throwable e) {
				e.printStackTrace();
				request.setAttribute("error", e.toString() + e.getMessage());
			}

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/TimeslotAssign.jsp");
			dispatcher.forward(request, response);

		}

		/*
		 * else if(button_1_1!=null){
		 * System.out.println("first button is clicked!"); }
		 */

	}

}