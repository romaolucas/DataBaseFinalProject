package com.tum.dbpra;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.text.SimpleDateFormat;

public class Util {

	Date todaysDate;
	SimpleDateFormat myFormatter;
	String formattedDate;
	Calendar myCal;

	public Util() {

	}

	public List<String> getMonthFromNow() {
		List<String> monthfromNow = new ArrayList<String>();
		myCal = Calendar.getInstance();
		todaysDate = new Date();
		myCal.add(Calendar.DATE, -1);
		for (int i = 0; i < 32; i++) {
			myCal.add(Calendar.DATE, +1);
			todaysDate = myCal.getTime();
			myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate = myFormatter.format(todaysDate);

			monthfromNow.add(formattedDate);

		}

		return monthfromNow;// end for

	}

	public static void main(String[] args) {
		new Util().getMonthFromNow();
	} // end main

} // end class DateCalculator