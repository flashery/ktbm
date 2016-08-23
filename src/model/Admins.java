package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/*
 * Parent class for different type of admin account
 * 
 */

public class Admins {

	/*
	 * Protected class member only accessible by subclasses
	 */
	
	public static final String [] USERS = {"admin", "cashier", "dispatcher"};
	public static final String [] EMP_STATUS = {"inactive", "active"};
	public static final String [] MARITAL_STATUS = {"single", "married", "widowed"};
	
	private SimpleIntegerProperty id;
	private SimpleStringProperty username;
	private SimpleStringProperty password;
	private SimpleStringProperty profilePic;
	private SimpleStringProperty firstName;
	private SimpleStringProperty midName;
	private SimpleStringProperty lastName;
	private SimpleStringProperty email;
	private SimpleStringProperty birthDate;
	private SimpleStringProperty birthPlace;
	private SimpleIntegerProperty age;
	private SimpleStringProperty prevCompany;
	private SimpleStringProperty contactNumber;
	private SimpleStringProperty maritalStatus;
	private SimpleStringProperty spouse;
	private SimpleStringProperty referral;
	private SimpleStringProperty userType;
	private SimpleStringProperty dateJoined;
	private SimpleStringProperty employmentStatus;
	

    /**
     * Default constructor.
     */
    public Admins() {
        this(0, null, null, null, null, null, null, null, null, null, 0, null, null, null, null, null, null, null, null);
    }

	
	public Admins(int id, String uName, String password, String profPic, String fName, String mInit, String lName, String myEmail, 
				  String birthDate, String birthPlace, int age, String prevCompany, String contactNumber, String maritalStatus,
				  String spouse, String referral, String uType, String dJoined, String employmentStatus) {
		
		this.id = new SimpleIntegerProperty(id);
		this.username = new SimpleStringProperty(uName);
		this.password = new SimpleStringProperty(password);
		this.profilePic = new SimpleStringProperty(profPic);
		this.firstName = new SimpleStringProperty(fName);
		this.midName = new SimpleStringProperty(mInit);
		this.lastName = new SimpleStringProperty(lName);
		this.email = new SimpleStringProperty(myEmail);
		this.birthDate = new SimpleStringProperty(birthDate);
		this.birthPlace = new SimpleStringProperty(birthPlace);
		this.age = new SimpleIntegerProperty(age);
		this.prevCompany = new SimpleStringProperty(prevCompany);
		this.contactNumber = new SimpleStringProperty(contactNumber);
		this.maritalStatus = new SimpleStringProperty(maritalStatus);
		this.spouse = new SimpleStringProperty(spouse);
		this.referral = new SimpleStringProperty(referral);
		this.userType = new SimpleStringProperty(uType);
		this.dateJoined = new SimpleStringProperty(dJoined);
		this.employmentStatus = new SimpleStringProperty(employmentStatus);
	}
	
	
	public void setId(int id)
	{
		this.id.set(id);
	}
	
	public int getId()
	{
		return this.id.get();
	}
	
	public void setUsername(String username) {
		this.username.set(username);
	}

	public String getUsername() {
		return username.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public String getPassword() {
		return this.password.get();
	}
	
	public void setProfilePic(String profilePic) {
		this.profilePic.set(profilePic);
		
	}

	public String getProfilePic() {
		return this.profilePic.get();
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

	public void setEmail(String email) 
	{
		this.email.set(email);
	}

	public String getEmail()
	{
		return this.email.get();
	}
	
	public void setBirthDate(String bday)
	{
		this.birthDate.set(bday);
	}
	
	public String getBirthDate()
	{
		return this.birthDate.get();
	}
	
	public void setBirthPlace(String bplace)
	{
		this.birthPlace.set(bplace);
	}
	
	public String getBirthPlace()
	{
		return this.birthPlace.get();
	}

	public void setAge(int age)
	{
		this.age.set(age);
	}
	
	public int getAge()
	{
		return this.age.get();
	}

	public void setPrevCompany(String prevCompany)
	{
		this.prevCompany.set(prevCompany);
	}
	
	public String getPrevCompany()
	{
		return this.prevCompany.get();
	}

	public void setContactNumber(String contactNumber)
	{
		this.contactNumber.set(contactNumber);
	}
	
	public String getContactNumber()
	{
		return this.contactNumber.get();
	}

	public void setMaritaLStatus(String maritalStatus)
	{
		this.maritalStatus.set(maritalStatus);
	}
	
	public String getMaritalStatus()
	{
		return this.maritalStatus.get();
	}
	
	public void setSpouse(String spouse)
	{
		this.spouse.set(spouse);
	}
	
	public String getSpouse()
	{
		return this.spouse.get();
	}

	public void setReferral(String referral)
	{
		this.referral.set(referral);
	}
	
	public String getReferral()
	{
		return this.referral.get();
	}
	
	public void setUserType(String userType)
	{
		this.userType.set(userType);
	}
	
	public String getUserType()
	{
		return this.userType.get();
	}
	
	public void setDateJoined(String dJoined)
	{
		this.dateJoined.set(dJoined);
	}
	
	public String getDateJoined()
	{
		return dateJoined.get();
	}
	

	public void setEmploymentStatus(String empStatus)
	{
		this.employmentStatus.set(empStatus);
	}
	
	public String getEmploymentStatus()
	{
		return this.employmentStatus.get();
	}


}
