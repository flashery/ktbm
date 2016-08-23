package model;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/*
 * Parent class for different type of admin account
 * 
 */

public class Dispatch {

	/*
	 * Protected class member only accessible by subclasses
	 */


	private boolean showAllData;
	private String dateNow;
	
	private SimpleIntegerProperty id;
	private SimpleStringProperty driver;
	private SimpleIntegerProperty unitNumber;
	private SimpleStringProperty vehicle;
	private SimpleStringProperty admin;
	private SimpleStringProperty timeOut;
	private SimpleStringProperty brand;
	private SimpleDoubleProperty rate;
	private SimpleStringProperty contactNumber;
	private SimpleStringProperty status;
	private SimpleStringProperty date;

	public static final String [] DISPATCH_STATUS = {"on dispatch", "off dispatch"}; 
    /**
     * Default constructor.
     */
    public Dispatch() {
        this(0, null, 0, null, null, null, null, 0.0, null, null, null);
    }

	
	public Dispatch(int id, String driver, int unitNumber, String vehicle, String admin, String tOut, 
					String brand, double rate, String contactNumber, String status, String date) {
		
		this.id = new SimpleIntegerProperty(id);
		this.driver = new SimpleStringProperty(driver);
		this.unitNumber = new SimpleIntegerProperty(unitNumber);
		this.vehicle = new SimpleStringProperty(vehicle);
		this.admin = new SimpleStringProperty(admin);
		this.timeOut = new SimpleStringProperty(tOut);
		this.brand = new SimpleStringProperty(brand);
		this.rate = new SimpleDoubleProperty(rate);
		this.contactNumber = new SimpleStringProperty(contactNumber);
		this.status = new SimpleStringProperty(status);
		this.date = new SimpleStringProperty(date);
		
	}
	
	public void setId(int id) {
		this.id.set(id);
	}
	
	public int getId()
	{
		return this.id.get();
	}
	
	public void setDriver(String driver) {
		this.driver.set(driver);
	}
	
	public String getDriver() {
		return this.driver.get();
	}
	
	public void setUnitNumber(int unitNum) {
		this.unitNumber.set(unitNum);
	}
	
	public int getUnitNumber()
	{
		return this.unitNumber.get();
	}
	
	public void setVehicle(String vehicle) {
		this.vehicle.set(vehicle);
	}
	
	public String getVehicle() {
		return this.vehicle.get();
	}
	
	public void setAdmin(String admin) {
		this.admin.set(admin);
	}
	
	public String getAdmin() {
		return this.admin.get();
	}
	/*
	public void setTimeIn( String tIn) {
		this.timeIn.set(tIn);
	}

	public  String getTimeIn() {
		return this.timeIn.get();
	}*/
	
	public void setTimeOut( String tOut) {
		this.timeOut.set(tOut);
	}

	public  String getTimeOut() {
		return this.timeOut.get();
	}
	
	public void setBrand(String brand) {
		this.brand.set(brand);
	}

	public String getBrand() {
		return this.brand.get();
	}
	
	public void setRate(double rate) {
		this.rate.set(rate);
	}

	public double getRate() {
		return this.rate.get();
	}
	
	public void setContactNumber(String contactNum) {
		this.contactNumber.set(contactNum);
	}

	public String getContactNumber() {
		return this.contactNumber.get();
	}
	
	public void setStatus(String status) {
		this.status.set(status);
	}
	
	public String getStatus() {
		return this.status.get();
	}
	
	public void setDate(String date) {
		this.date.set(date);
	}
	
	public String getDate() {
		return this.date.get();
	}
	
	// Settings
	public void setShowAllData(boolean showAllData)
	{
		this.showAllData = showAllData;
	}
	
	public boolean getShowAllData()
	{
		return this.showAllData;
	}
	
	public void setDateNow(String dateNow)
	{
		this.dateNow = dateNow;
	}
	
	public String getDateNow()
	{
		return this.dateNow;
	}
}
