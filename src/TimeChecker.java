import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import util.DateUtil;


public class TimeChecker {

	private static final long dispatchDuration = 36; // 16hrs taxi dispatch duration
	private static final String TIME = "12:00:00"; // hours when late occur
	
	
	public static String checkLate(String startTime, String time) throws ParseException
	{
		
		String alert = "";
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(startTime);
		String startTime1 = new SimpleDateFormat("yyyy-MM-dd").format(date);;
		
		Date currentDate = DateUtil.formatDateTime(time);
		//Date startDate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(startTime);
		Date startDate = DateUtil.reformatDateRDate(startTime1);
						// Driver Start time							  +  dispatch duration
		long endTime = TimeUnit.MILLISECONDS.toHours(startDate.getTime()) + dispatchDuration;
		
		// Set warning if 1 hour before end of dispatch duration else if 0 taxi is late
		if((endTime - TimeUnit.MILLISECONDS.toHours(currentDate.getTime())) == 1)	// 1 hour to go before late
		{
			alert = "warning";
		} else if((endTime - TimeUnit.MILLISECONDS.toHours(currentDate.getTime())) < 1) {
			alert = "late";
		}
		
		//System.out.println("Start: " + DateUtil.formatStringDT(startTime));
		//System.out.println("End: " + DateUtil.formatStringDT(time));
		//System.out.println(endTime - TimeUnit.MILLISECONDS.toHours(currentDate.getTime()));
		return alert;
	}
	
	public static String checkExpire(String expired, String currentTime) throws ParseException
	{
		String status = "";
		
		Date currentDate = DateUtil.reformatDateRDate(currentTime);
		Date expiredDate = DateUtil.reformatDateRDate(expired);
		
		long timeRemain =  expiredDate.getTime() - currentDate.getTime();
		
		if(TimeUnit.MILLISECONDS.toDays(timeRemain) < 31 && TimeUnit.MILLISECONDS.toDays(timeRemain) >= 1)
		{
			status = "warning";
		} else if(TimeUnit.MILLISECONDS.toDays(timeRemain) < 1)
		{
			status = "expired";
		}
		return status;
	}
	
	public static int checkAge(String birthDate) throws ParseException
	{
		int age = 0;
		Date currentDate = DateUtil.reformatDateRDate(DateUtil.newDateNormal());
		Date bDate = DateUtil.reformatDateRDate(birthDate);
		
		long timeRemain =  currentDate.getTime() - bDate.getTime();
		age =  (int) (TimeUnit.MILLISECONDS.toDays(timeRemain) / 365);
		
		return age;
	}

	
}
