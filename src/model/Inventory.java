package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Inventory {
	private SimpleIntegerProperty id;
	private SimpleStringProperty driver;
	private SimpleIntegerProperty unitNumber;
	private SimpleDoubleProperty trip;
	private SimpleDoubleProperty presentOdo;
	private SimpleDoubleProperty prevOdo;
	private SimpleIntegerProperty pms;
	private SimpleDoubleProperty gasLtrs;
	private SimpleIntegerProperty checkTires;
	private SimpleIntegerProperty checkTools;
	private SimpleIntegerProperty doc;
	private SimpleStringProperty remarks;
	private SimpleStringProperty date;
	
	public Inventory() {
		this(0,null,0, 0.0, 0.0, 0, 0.0, 0, 0, 0, null, null);
	}
	
	public Inventory(int id, String driver, int unitNumber,  double trip, double odometer, 
					int pms, double gasLtrs, int checkTires, int checkTools, int doc, String remarks, String date){
		this.id = new SimpleIntegerProperty(id);
		this.driver = new SimpleStringProperty(driver);
		this.unitNumber = new SimpleIntegerProperty(unitNumber);
		this.trip = new SimpleDoubleProperty(trip);
		//this.odometer = new SimpleDoubleProperty(odometer);
		this.pms = new SimpleIntegerProperty(pms);
		this.gasLtrs = new SimpleDoubleProperty(gasLtrs);
		this.checkTires = new SimpleIntegerProperty(checkTires);
		this.checkTools = new SimpleIntegerProperty(checkTools);
		this.doc = new SimpleIntegerProperty(doc);
		this.remarks = new SimpleStringProperty(remarks);
		this.date = new SimpleStringProperty(date);
	}
	
	public void setId(int newValue) {
		this.id.set(newValue);
	}

	public int getId(){
		return this.id.get();
	}
	
	public void setDriver(String newValue){
		this.driver.set(newValue);
	}
	
	public String getDriver(){
		return this.driver.get();
	}
	
	public void setUnitNumber(int newValue){
		this.unitNumber.set(newValue);
	}
	
	public int getUnitNumber(){
		return this.unitNumber.get();
	}
	public void setTrip(double newValue){
		this.trip.set(newValue);
	}
	
	public double getTrip(){
		return this.trip.get();
	}

	/*
	public void setOdometer(double newValue){
		this.odometer.set(newValue);
	}
	
	public double getOdometer(){
		return this.odometer.get();
	}*/
	
	public void setPms(int newValue){
		this.pms.set(newValue);
	}
	
	public int getPms(){
		return this.pms.get();
	}

	public void setGasLtrs(double newValue){
		this.gasLtrs.set(newValue);
	}
	
	public double getGasLtrs(){
		return this.gasLtrs.get();
	}
	
	public void setCheckTires(int newValue){
		this.checkTires.set(newValue);
	}
	
	public int getCheckTires(){
		return this.checkTires.get();
	}
	
	public void setCheckTools(int newValue){
		this.checkTools.set(newValue);
	}
	
	public int getCheckTools(){
		return this.checkTools.get();
	}
	
	public void setDoc(int newValue){
		this.doc.set(newValue);
	}
	
	public int getDoc(){
		return this.doc.get();
	}
	
	public void setRemarks(String newValue){
		this.remarks.set(newValue);
	}
	
	public String getRemarks(){
		return this.remarks.get();
	}
	
	public void setDate(String newValue){
		this.date.set(newValue);
	}
	
	public String getDate() {
		return this.date.get();
	}
}
