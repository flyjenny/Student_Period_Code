package Flight;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		String firstDate = "2011-11-29";
		String EndDate = "2011-12-05";
		String begin = "上海";
		String end = "北京";
		String highestPrice = "700";
		
		Date dateTemp = new SimpleDateFormat("yyyy-MM-dd").parse(firstDate);
		Date dateTemp2 = new SimpleDateFormat("yyyy-MM-dd").parse(EndDate);
		Calendar calendarTemp = Calendar.getInstance();
		calendarTemp.setTime(dateTemp);
		String handleDate = null;

		while (calendarTemp.getTime().getTime() != dateTemp2.getTime()) {
			handleDate = new SimpleDateFormat("yyyy-MM-dd").format(calendarTemp
					.getTime());
			handleDate = handleDate.replaceAll("-", "");
			System.out.println(handleDate);
			/*
			 *	从网上把抓取数据存入数据库flight.certainid
			 */
			Crawler cl = new Crawler(handleDate, begin, end);
			calendarTemp.add(Calendar.DAY_OF_YEAR, 1);
			
		}
		/*
		 * 筛选符合要求数据存入数据库flight.certainid_tobesent
		 */
		FlighFilter ff = new FlighFilter(firstDate.replaceAll("-", ""),
				EndDate.replaceAll("-", ""), begin, end, highestPrice);
		List<String> output = ff.Msg();
		for (String list : output) {
			System.out.println(list);
		}

	}
}
