package util;

public class Name {
	 public static String getFirstName(String fullName) {
	    	String [] fullNameArr = fullName.split(" ");
	    	String fName = "";
	    	String mName = "";
	    	String lName = "";
	    	int cout = 0;
	    	
	    	while(fullNameArr[cout].length() != 2){
	    		fName += fullNameArr[cout] + " ";
	    		cout++;
	    	}
	    	
	    	return fName;
	    }
	    
	 public static String getLastName(String fullName) {
	    	
	    	String [] fullNameArr = fullName.split(" ");
	    	String fName = "";
	    	String mName = "";
	    	String lName = "";
	    	int cout = 0;
	    	
	    	while(fullNameArr[cout].length() != 2){
	    		fName += fullNameArr[cout] + " ";
	    		cout++;
	    	}
	    	cout++;
	    	mName = fullNameArr[cout];
	    	while(cout < fullNameArr.length){
	    		lName += fullNameArr[cout] + " ";
	    		cout++;
	    	}
	    	
	    	return lName;
	    }
	   
	 public static String getMidName(String fullName) {
	   	String [] fullNameArr = fullName.split(" ");
	   	String fName = "";
	   	String mName = "";
	   	String lName = "";
	   	int cout = 0;
	   	
	   	// loop through the array until its not
	   	// middle initial. 2 chars, the letter and 
	   	// the dot, to get the first name
	   	while(fullNameArr[cout].length() != 2){
	   		fName += fullNameArr[cout] + " ";
	   		cout++;
	   	}
	   	
	   	// increment cout so we get middle name
	   	cout++;
	   	mName = fullNameArr[cout];
	   	
	   	// loop all through the remaining array to get last name
	   	while(cout < fullNameArr.length){
	   		lName += fullNameArr[cout] + " ";
	   		cout++;
	   	}
	   	
	   	return mName;
	   }
	 
	 public static String createFullName(String firName, String midName, String lastName){
		 
		String driverName;
		 
		// Create middle initial
		String driverMidName =  !midName.trim().equals("") ? midName.substring(0, 1) + "." : "";
		// Concatenate the driver's name
		driverName = firName + " " + driverMidName + " " + lastName;
			
		return driverName;
	 }
}
