package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/*
 * Parent class for different type of admin account
 * 
 */

public class Drivers {
	/*
	 * Protected class member only accessible by subclasses
	 */
	public static final String [] DRIVER_TYPE = {"regular", "extrador"};
	public static final String [] DISPATCH_STATUS = {"off duty", "on duty"};
	public static final String [] EMP_STATUS = {"inactive", "active", "blacklisted"};
	public static final String [] MARITAL_STATUS = {"single", "married", "widowed"};
	
	private SimpleIntegerProperty id;
	private SimpleStringProperty firstName;
	private SimpleStringProperty midName;
	private SimpleStringProperty lastName;
	private SimpleStringProperty profilePicture;
	private SimpleStringProperty signPicture;
	private SimpleDoubleProperty lessRate;
	private SimpleStringProperty birthDate;
	private SimpleStringProperty birthPlace;
	private SimpleIntegerProperty age;
	private SimpleStringProperty prevCompany;
	private SimpleStringProperty contactNumber;
	private SimpleStringProperty maritalStatus;
	private SimpleStringProperty spouse;
	private SimpleStringProperty referral;
	private SimpleStringProperty address;
	private SimpleStringProperty licenseNum;
	private SimpleStringProperty licenseExp;
	private SimpleStringProperty driverType;
	private SimpleStringProperty backjob;
	private SimpleStringProperty employmentStatus;
	private SimpleStringProperty dispatchStatus;

    /**
     * Default constructor.
     */
    public Drivers() {
        this(0, null, null, null, null, null, 0.0 , null, null, 0, null, null, null, null, null, null , null , null , null , null , null , null);
    }

	
	public Drivers(int id, String fName, String mName, String lName, String profPic, String signPic, double lessRate, String bDay, String bPlace, 
					int age, String prevCompany, String contactNum, String maritalStat, String spouse, String referral, 
					String  address, String licenseNum, String licenseExp, String driverType, String backjob, String empStatus, 
					String dispatchStatus) {
		
		this.id = new SimpleIntegerProperty(id);
		this.firstName = new SimpleStringProperty(fName);
		this.midName = new SimpleStringProperty(mName);
		this.lastName = new SimpleStringProperty(lName);
		this.profilePicture = new SimpleStringProperty(profPic);
		this.signPicture = new SimpleStringProperty(signPic);
		this.lessRate = new SimpleDoubleProperty(lessRate);
		this.birthDate = new SimpleStringProperty(bDay);
		this.birthPlace = new SimpleStringProperty(bPlace);
		this.age = new SimpleIntegerProperty(age);
		this.prevCompany = new SimpleStringProperty(prevCompany);
		this.contactNumber = new SimpleStringProperty(contactNum);
		this.maritalStatus = new SimpleStringProperty(maritalStat);
		this.spouse = new SimpleStringProperty(spouse);
		this.referral = new SimpleStringProperty(referral);
		this.address = new SimpleStringProperty(address);
		this.licenseNum = new SimpleStringProperty(licenseNum);
		this.licenseExp = new SimpleStringProperty(licenseExp);
		this.driverType = new SimpleStringProperty(driverType);
		this.backjob =  new SimpleStringProperty(backjob);
		this.employmentStatus = new SimpleStringProperty(empStatus);
		this.dispatchStatus = new SimpleStringProperty(dispatchStatus);

	}


	public void setId(int id) {
		this.id.set(id);
	}
	
	public int getId()
	{
		return this.id.get();
	}
	
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
		
	}

	public String getFirstName() {
		return this.firstName.get();
	}

	public void setMidName(String midName) {
		this.midName.set(midName);
	}

	public String getMidName() {
		return this.midName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
		
	}

	public String getLastName() {
		return this.lastName.get();
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture.set(profilePicture);
		
	}

	public String getProfilePicture() {
		return this.profilePicture.get();
	}
	
	public void setSignPicture(String signPicture) {
		this.signPicture.set(signPicture);
		
	}

	public String getSignPicture() {
		return this.signPicture.get();
	}
	
	public void setLessRate(double lessRate) {
		this.lessRate.set(lessRate);
	}
	
	public double getLessRate() {
		return this.lessRate.get();
	}
	
	public void setBirtDate(String birthDate) {
		this.birthDate.set(birthDate);
		
	}

	public String getBirthDate() {
		return this.birthDate.get();
	}
	
	public void setBirthPlace(String birthPlace) {
		this.birthPlace.set(birthPlace);
		
	}

	public String getBirthPlace() {
		return this.birthPlace.get();
	}
	
	public void setAge(int age) {
		this.age.set(age);
		
	}

	public int getAge() {
		return this.age.get();
	}
	
	public void setPrevCompany(String prevCompany) {
		this.prevCompany.set(prevCompany);
		
	}

	public String getPrevCompany() {
		return this.prevCompany.get();
	}
	
	public void setContactNumber(String contactNumber) {
		this.contactNumber.set(contactNumber);
		
	}

	public String getContactNumber() {
		return this.contactNumber.get();
	}
	
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus.set(maritalStatus);
		
	}

	public String getMaritalStatus() {
		return this.maritalStatus.get();
	}
	
	public void setSpouse(String spouse) {
		this.spouse.set(spouse);
		
	}

	public String getSpouse() {
		return this.spouse.get();
	}
	
	public void setReferral(String referral) {
		this.referral.set(referral);
		
	}

	public String getReferral() {
		return this.referral.get();
	}
	
	public void setAddress(String adress) 
	{
		this.address.set(adress);
	}

	public String getAddress()
	{
		return this.address.get();
	}
	
	public void setLicenseNum(String licenseNum)
	{
		this.licenseNum.set(licenseNum);
	}
	
	public String getLicenseNum()
	{
		return this.licenseNum.get();
	}
	
    public void setLicenseExp(String licenseExp) {
        this.licenseExp.set(licenseExp);
    }
    
    public String getLicenseExp() {
        return licenseExp.get();
    }

    public void setDriverType(String driverType) {
        this.driverType.set(driverType);
    }
    
    public String getDriverType() {
        return driverType.get();
    }
 
    
    public void setBackjob(String backjob) {
        this.backjob.set(backjob);
    }
    
    public String getBackjob() {
        return backjob.get();
    }
    
	public void setEmploymentStatus(String status) {
		this.employmentStatus.set(status);
	}

	public String getEmploymentStatus() {
		return this.employmentStatus.get();
	}
	
	public void setDispatchStatus(String dispatchStatus) {
		this.dispatchStatus.set(dispatchStatus);
	}

	public String getDispatchStatus() {
		return this.dispatchStatus.get();
	}

}
