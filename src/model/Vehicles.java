package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/*
 * Parent class for different type of vehicles account
 * 
 */

public class Vehicles {

	/*
	 * Protected class member only accessible by subclasses
	 */

	private SimpleIntegerProperty id;
	private SimpleIntegerProperty unitNumber;
	private SimpleStringProperty plateNumber;
	private SimpleStringProperty caseNumber;
	private SimpleStringProperty ccn;
	private SimpleStringProperty chasisNumber;
	private SimpleStringProperty carBrand;
	private SimpleIntegerProperty carRate;
	private SimpleStringProperty engineNumber;
	protected SimpleStringProperty plateRegistration;
	protected SimpleStringProperty resealDate;
	protected SimpleStringProperty cpcRegistration;
	protected SimpleStringProperty eotRegistration;
	protected SimpleStringProperty status;

	/**
	 * Default constructor.
	 */
	public Vehicles() {
		this(0, 0, null, null, null, null, null, 0, null, null, null, null, null, null);
	}

	public Vehicles(int id, int unitNumber, String plateNumber, String caseNumber, String ccn, String chasisNumber, String carBrand, int carRate,
			String engineNumber, String plateRegistration, String resealDate, String cpcReg, String eotReg, String status) {

		this.id = new SimpleIntegerProperty(id);
		this.unitNumber = new SimpleIntegerProperty(unitNumber);
		this.plateNumber = new SimpleStringProperty(plateNumber);
		this.caseNumber = new SimpleStringProperty(caseNumber);
		this.ccn = new SimpleStringProperty(ccn);
		this.chasisNumber = new SimpleStringProperty(chasisNumber);
		this.carBrand = new SimpleStringProperty(carBrand);
		this.carRate = new SimpleIntegerProperty(carRate);
		this.engineNumber = new SimpleStringProperty(engineNumber);
		this.plateRegistration = new SimpleStringProperty(plateRegistration);
		this.resealDate = new SimpleStringProperty(resealDate);
		this.cpcRegistration = new SimpleStringProperty(cpcReg);
		this.eotRegistration = new SimpleStringProperty(eotReg);
		this.status = new SimpleStringProperty(status);
	}

	public void setId(int id)
	{
		this.id.set(id);
	}
	
	public int getId()
	{
		return this.id.get();
	}
	
	public void setUnitNumber(int unitNumber) {
		this.unitNumber.set(unitNumber);
	}

	public int getUnitNumber() {
		return this.unitNumber.get();
	}
	
	public void setPlateNumber(String plateNumber) {
		this.plateNumber.set(plateNumber);
	}

	public String getPlateNumber() {
		return this.plateNumber.get();
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber.set(caseNumber);
	}

	public String getCaseNumber() {
		return this.caseNumber.get();
	}
	
	public void setCcn(String ccn) {
		this.ccn.set(ccn);
	}

	public String getCcn() {
		return this.ccn.get();
	}
	
	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber.set(chasisNumber);
	}

	public String getChasisNumber() {
		return this.chasisNumber.get();
	}

	public void setCarBrand(String carBrand)
	{
		this.carBrand.set(carBrand);
	}
	
	public String getCarBrand()
	{
		return this.carBrand.get();
	}
	
	public void setEngineNumber(String engineNumber)
	{
		this.engineNumber.set(engineNumber);
	}
	
	public String getEngineNumber()
	{
		return this.engineNumber.get();
	}
	
	public void setPlateRegistration(String plateRegistration) {
		this.plateRegistration.set(plateRegistration);
	}
	
	public String getPlateRegistration() {
		return this.plateRegistration.get();
	}

	public void setCarRate(int carRate)
	{
		this.carRate.set(carRate);
	}
	
	public int getCarRate()
	{
		return this.carRate.get();
	}
	
	public void setResealDate(String resealDate) {
		this.resealDate.set(resealDate);
	}

	public String getResealDate() {
		return this.resealDate.get();
	}

	public void setCpcRegistration(String cpcReg) {
		this.cpcRegistration.set(cpcReg);
	}

	public String getCpcRegistration() {
		return this.cpcRegistration.get();
	}
	
	public void setEotRegistration(String eotReg) {
		this.eotRegistration.set(eotReg);
	}

	public String getEotRegistration() {
		return this.eotRegistration.get();
	}
	
	public void setStatus(String status) {
		this.status.set(status);
	}

	public String getStatus() {
		return this.status.get();
	}
}
