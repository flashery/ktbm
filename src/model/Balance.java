package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Balance {
	public static final String [] BALANCE_TYPE = { "cashbonds", "damages",
			"loans", "participations", "penalties", "shortages" };
	
	private SimpleIntegerProperty id;
	private SimpleStringProperty driver;
	private SimpleStringProperty admin;
	private SimpleDoubleProperty cashbonds;
	private SimpleDoubleProperty damages;
	private SimpleDoubleProperty loans;
	private SimpleDoubleProperty participations;
	private SimpleDoubleProperty penalties;
	private SimpleDoubleProperty shortages;
	private SimpleDoubleProperty totalBalance;
	
	public Balance() {
		this(0, null, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	}
	
	public Balance(int id, String driver, String admin, double cashbonds,
			double damages,double loans,double participations,
			double penalties, double shortages, double totalBalance) {
		
		this.id = new SimpleIntegerProperty(id);
		this.driver = new SimpleStringProperty(driver);
		this.admin = new SimpleStringProperty(admin);
		this.cashbonds = new SimpleDoubleProperty(cashbonds);
		this.damages = new SimpleDoubleProperty(damages);
		this.loans = new SimpleDoubleProperty(loans);
		this.participations = new SimpleDoubleProperty(participations);
		this.penalties = new SimpleDoubleProperty(penalties);
		this.shortages = new SimpleDoubleProperty(shortages);
		this.totalBalance = new SimpleDoubleProperty(totalBalance);
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

	public void setAdmin(String admin)
	{
		this.admin.set(admin);
	}
	
	public String getAdmin()
	{
		return this.admin.get();
	}
	
	public void setCashbonds(double cashbonds)
	{
		this.cashbonds.set(cashbonds);
	}
	
	public double getCashbonds()
	{
		return this.cashbonds.get();
	}
	
	public void setDamages(double damages)
	{
		this.damages.set(damages);
	}
	
	public double getDamages()
	{
		return this.damages.get();
	}
	
	public void setLoans(double loans)
	{
		this.loans.set(loans);
	}
	
	public double getLoans()
	{
		return this.loans.get();
	}
	
	public void setParticipations(double participations)
	{
		this.participations.set(participations);
	}
	
	public double getParticipations()
	{
		return this.participations.get();
	}
	
	public void setPenalties(double penalties)
	{
		this.penalties.set(penalties);
	}
	
	public double getPenalties()
	{
		return this.penalties.get();
	}
	
	public void setShortages(double shortages)
	{
		this.shortages.set(shortages);
	}
	
	public double getShortages()
	{
		return this.shortages.get();
	}
	
	public void setTotalBalance(double totalBalance)
	{
		this.totalBalance.set(totalBalance);
	}
	
	public double getTotalBalance()
	{
		return this.totalBalance.get();
	}
}
