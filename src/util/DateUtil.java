package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Helper functions for handling dates.
 * 
 * @author Yves Gonzaga
 */
public class DateUtil {

    /** The date pattern that is used for conversion. Change as you wish. */
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final String TIME_PATTERN = "HH:mm";
    
    /** The date formatter. */
    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern(DATE_PATTERN);
    
    private static final DateTimeFormatter TIME_FORMATTER = 
            DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * Returns the given date as a well formatted String. The above defined 
     * {@link DateUtil#DATE_PATTERN} is used.
     * 
     * @param date the date to be returned as a string
     * @return formatted string
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }
    /**
     * Converts a String in the format of the defined {@link DateUtil#DATE_PATTERN} 
     * to a {@link LocalDate} object.
     * 
     * Returns null if the String could not be converted.
     * 
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static LocalDate parse(String dateString) {
        try {
        	return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    
    
    
    /*
     * Format time and date to String
     */
    public static String formatTime(String time) throws ParseException {
    	Date date = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).parse(time);
		return new SimpleDateFormat("hh:mm, a").format(date);
    }
    
    public static String formatDate(String stringDate) throws ParseException {
    	Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(stringDate);
		return new SimpleDateFormat("MMMM d, yyyy").format(date);
    }
    
    /*
     * Format datetime to String
     */
    public static String formatStringDT(String stringDateTime) throws ParseException {
    	Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(stringDateTime);
    	return new SimpleDateFormat("MMMM d, yyyy hh:mm, a").format(date);
    }
    
    /*
     * Format datetime to String
     */
    public static String reformatStringDT(String stringDateTime) throws ParseException {
    	Date date = new SimpleDateFormat("MMMM d, yyyy hh:mm, a", Locale.ENGLISH).parse(stringDateTime);
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    
    /*
     * Format datetime 
     */
    public static Date formatDateTime(String stringDateTime) throws ParseException {
    	Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(stringDateTime);
		return date;
    }
    
    
    /*
     * Format datetime 
     */
    public static Date reformatDateTime(String stringDateTime) throws ParseException {
    	Date date = new SimpleDateFormat("MMMM d, yyyy hh:mm, a", Locale.ENGLISH).parse(stringDateTime);
		return date;
    }
    
    
    /*
     * Reformat the date to its database format.
     */
    public static String reformatTime(String time) throws ParseException {
    	Date date = new SimpleDateFormat("hh:mm, a", Locale.ENGLISH).parse(time);
		return new SimpleDateFormat("HH:mm:ss").format(date);
    }
    
    public static String reformatDate(String stringDate) throws ParseException {
    	Date date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(stringDate);
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    
    
    
    
    /*
     * Reformat the date and returns as Date.
     */
    public static Date reformatTimeRDate(String time) throws ParseException {
    	Date date = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).parse(time);
		return date;
    }
    
    public static Date reformatDateRDate(String stringDate) throws ParseException {
    	Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(stringDate);
		return date;
    }
    
    
    
    
    
    
    /*
     * Create new date and time
     */
    public static String newDate() {
    	
    	DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
	    Date dateobj = new Date();
    	return df.format(dateobj);
    }
    
    public static String newDateNormal() {
    	
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    Date dateobj = new Date();
    	return df.format(dateobj);
    }
  
    public static String newTime() {
    	
    	DateFormat df = new SimpleDateFormat("hh:mm, a");
	    Date dateobj = new Date();
    	return df.format(dateobj);
    }
     
    public static String newDateTime() {
    	
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date dateobj = new Date();
    	return df.format(dateobj);
    }

    
    
	// Get yesterday's date
    public static String yesterday() {
	
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		
		return sdf.format(cal.getTime());
	}
	
    
    
    /**
     * Checks the String whether it is a valid date.
     * 
     * @param dateString
     * @return true if the String is a valid date
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }
}