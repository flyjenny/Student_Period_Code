package Server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.htmlcleaner.XPatherException;

import Flight.Crawler;

public class FlashFligh implements Runnable {
	/*
	 * public FlashFligh(String firstday, String endday, String begin, String
	 * end, String highestprice) { Global.$firstday = firstday; Global.$endday =
	 * endday; Global.$begin = begin; Global.$end = end; Global.$highestprice =
	 * highestprice; }
	 */

	public void run() {
		int flighrecord = 1;
		while (true) {
			System.out.println("Fligh reflash times: " + (flighrecord++));
			if (Global.$begin != null && Global.$end != null
					&& Global.$endday != null && Global.$firstday != null
					&& Global.$highestprice != null) {
				Date dateTemp = null;
				Date dateTemp2 = null;
				try {
					dateTemp = new SimpleDateFormat("yyyy-MM-dd")
							.parse(Global.$firstday);
					dateTemp2 = new SimpleDateFormat("yyyy-MM-dd")
							.parse(Global.$endday);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Calendar calendarTemp = Calendar.getInstance();
				calendarTemp.setTime(dateTemp);
				String handleDate = null;

				while (calendarTemp.getTime().getTime() != dateTemp2.getTime()) {
					handleDate = new SimpleDateFormat("yyyy-MM-dd")
							.format(calendarTemp.getTime());
					handleDate = handleDate.replaceAll("-", "");
					System.out.println(handleDate);
					/*
					 * 从网上把抓取数据存入数据库flight.certainid
					 */
					try {
						Crawler cl = new Crawler(handleDate, Global.$begin,
								Global.$end);
					} catch (XPatherException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					calendarTemp.add(Calendar.DAY_OF_YEAR, 1);

				}
			}
			try {
				Thread.sleep(Global.$flighflash);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
