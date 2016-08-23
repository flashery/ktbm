package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Payments {
	
	private SimpleIntegerProperty id;
	private SimpleStringProperty driver;
	private SimpleIntegerProperty unitNumber;
	private SimpleStringProperty vehicle;
	private SimpleDoubleProperty carRate;
	private SimpleStringProperty adminUser;
	private SimpleDoubleProperty boundaries;
	private SimpleDoubleProperty cashbondPayments;
	private SimpleDoubleProperty shortagePayments; 
	private SimpleDoubleProperty damagePayments;
	private SimpleDoubleProperty loans;
	private SimpleDoubleProperty appSaving; 
	private SimpleDoubleProperty shortages;
	private SimpleDoubleProperty lessSunday;
	private SimpleDoubleProperty lessHoliday;
	private SimpleDoubleProperty driverLess;
	private SimpleStringProperty timeRepairStart;
	private SimpleStringProperty timeRepairEnd;
	private SimpleDoubleProperty totalTimeRepair;
	private SimpleDoubleProperty repairCost;
	private SimpleIntegerProperty grabTaxi;
	private SimpleDoubleProperty kilometersRun;
	private SimpleDoubleProperty gasLiters;
	private SimpleStringProperty remarks;
	private SimpleDoubleProperty total;
	private SimpleStringProperty date;
	
	public Payments()
	{
		this(0, null, 0, null, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, 0, 0, 0, 0, 0, null, 0, null);
		
	}
	
	public Payments(int id, String driver, int unitNumber, String vehicle, double carRate, String admin_user, double boundaries, double cbPayments, double sPayments, 
					double dPayments, double loans, double appSaving, double shortages, double lessSunday, double lessHoliday, double driverLess,
					String timeRepairStart, String timeRepairEnd, double totaTimeRepair, double repairCost, int grabTaxi, double kilomentersRun, double gasLiters, String remarks, 
					double total, String date) {
		
		this.id = new SimpleIntegerProperty(id);
		this.driver = new SimpleStringProperty(driver);
		this.unitNumber = new SimpleIntegerProperty(unitNumber);
		this.vehicle = new SimpleStringProperty(vehicle);
		this.carRate = new SimpleDoubleProperty(carRate);
		this.adminUser = new SimpleStringProperty(admin_user);
		this.boundaries = new SimpleDoubleProperty(boundaries);
		this.cashbondPayments = new SimpleDoubleProperty(cbPayments);
		this.shortagePayments = new SimpleDoubleProperty(sPayments);
		this.damagePayments = new SimpleDoubleProperty(dPayments);
		this.loans = new SimpleDoubleProperty(loans);
		this.appSaving = new SimpleDoubleProperty(appSaving);
		this.shortages = new SimpleDoubleProperty(shortages);
		this.lessSunday = new SimpleDoubleProperty(lessSunday);
		this.lessHoliday = new SimpleDoubleProperty(lessHoliday);
		this.driverLess = new SimpleDoubleProperty(driverLess);
		this.total = new SimpleDoubleProperty(total);
		this.timeRepairStart = new SimpleStringProperty(timeRepairStart);
		this.timeRepairEnd = new SimpleStringProperty(timeRepairEnd);
		this.totalTimeRepair = new SimpleDoubleProperty(totaTimeRepair);
		this.repairCost = new SimpleDoubleProperty(repairCost);
		this.grabTaxi = new SimpleIntegerProperty(grabTaxi);
		this.kilometersRun = new SimpleDoubleProperty(kilomentersRun);
		this.gasLiters = new SimpleDoubleProperty(gasLiters);
		this.remarks = new SimpleStringProperty(remarks);
		this.date = new SimpleStringProperty(date);
		
	}
	
	public void setId(int id)
	{
		this.id.set(id);
	}
	
	public int getId()
	{
		return this.id.get();
	}
	
	public void setDriver(String driver)
	{
		this.driver.set(driver);
	}
	
	public String getDriver()
	{
		return this.driver.get();
	}
	
	public void setUnitNumber(int unitNum) {
		this.unitNumber.set(unitNum);
	}
	
	public int getUnitNumber(){
		return this.unitNumber.get();
	}
	
	public void setVehicle(String vehicle)
	{
		this.vehicle.set(vehicle);
	}
	
	public String getVehicle()
	{
		return this.vehicle.get();
	}
	
	public void setCarRate(double carRate)
	{
		this.carRate.set(carRate);
	}
	
	public double getCarRate()
	{
		return this.carRate.get();
	}
	
	public void setAdminUser(String adminUser)
	{
		this.adminUser.set(adminUser);
	}
	
	public String getAdminUser()
	{
		return this.adminUser.get();
	}
	
	public void setBoundaries(double boundaries)
	{
		this.boundaries.set(boundaries);
	}
	
	public double getBoundaries()
	{
		return this.boundaries.get();
	}
	
	public void setCashbondPayments(double cbPayments)
	{
		this.cashbondPayments.set(cbPayments);
	}
	
	public double getCashbondPayments()
	{
		return this.cashbondPayments.get();
	}
	
	public void setShortagePayments(double sPayments)
	{
		this.shortagePayments.set(sPayments);
	}
	
	public double getShortagePayments()
	{
		return this.shortagePayments.get();
	}

	public void setDamagePayments(double dPayments)
	{
		this.damagePayments.set(dPayments);
	}
	
	public double getDamagePayments()
	{
		return this.damagePayments.get();
	}
	
	public void setLoans(double loans)
	{
		this.loans.set(loans);
	}
	
	public double getLoans()
	{
		return this.loans.get();
	}
	
	public void setAppSaving(double appSaving)
	{
		this.appSaving.set(appSaving);
	}
	
	public double getAppSaving()
	{
		return this.appSaving.get();
	}

	public void setShortages(double shortages)
	{
		this.shortages.set(shortages);
	}
	
	public double getShortages()
	{
		return this.shortages.get();
	}
	
	public void setLessSunday(double lessSunday)
	{
		this.lessSunday.set(lessSunday);
	}
	
	public double getLessSunday()
	{
		return this.lessSunday.get();
	}
	
	public void setLessHoliday(double lessHoliday)
	{
		this.lessHoliday.set(lessHoliday);
	}
	
	public double getLessHoliday()
	{
		return this.lessHoliday.get();
	}
	
	public void setDriverLess(double driverLess)
	{
		this.driverLess.set(driverLess);
	}
	
	public double getDriverLess()
	{
		return this.driverLess.get();
	}
	
	public void setTotal(double total)
	{
		this.total.set(total);
	}
	
	public double getTotal()
	{
		return this.total.get();
	}
	
	public void setTimeRepairStart(String trStart)
	{
		this.timeRepairStart.set(trStart);
	}
	
	public String getTimeRepairStart()
	{
		return this.timeRepairStart.get();
	}

	public void setTimeRepairEnd(String trEnd)
	{
		this.timeRepairEnd.set(trEnd);
	}
	
	public String getTimeRepairEnd()
	{
		return this.timeRepairEnd.get();
	}
	
	public void setTotalTimeRepair(double totalTimeRepair)
	{
		this.totalTimeRepair.set(totalTimeRepair);
	}
	
	public double getTotalTimeRepair()
	{
		return this.totalTimeRepair.get();
	}
	
	public void setRepairCost(double repairCost)
	{
		this.repairCost.set(repairCost);
	}
	
	public double getRepairCost()
	{
		return this.repairCost.get();
	}
	
	public void setGrabTaxi(int grabTaxi)
	{
		this.grabTaxi.set(grabTaxi);
	}
	
	public int getGrabTaxi()
	{
		return this.grabTaxi.get();
	}
	
	public void setKilometersRun(double klsRun)
	{
		this.kilometersRun.set(klsRun);
	}
	
	public double getKilometersRun()
	{
		return this.kilometersRun.get();
	}

	public void setGasLiters(double gasLtrs)
	{
		this.gasLiters.set(gasLtrs);
	}

	public double getGasLiters()
	{
		return this.gasLiters.get();
	}
	
	public void setRemarks(String remarks)
	{
		this.remarks.set(remarks);
	}
	
	public String getRemarks()
	{
		return this.remarks.get();
	}
	
	public void setDate(String date)
	{
		this.date.set(date);
	}
	
	public String getDate()
	{
		return this.date.get();
	}

	/**
	 * @param repairCost the repairCost to set
	 */
	public void setRepairCost(SimpleDoubleProperty repairCost) {
		this.repairCost = repairCost;
	}

}
