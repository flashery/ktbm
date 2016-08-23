package util;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Ledger {
	
	private SimpleStringProperty date;
	private SimpleStringProperty type;
	private SimpleDoubleProperty debit;
	private SimpleDoubleProperty credit;
	private SimpleDoubleProperty totalBalance;
	
	public Ledger() {
		this(null,null,0,0,0);
	}
	
	public Ledger(String date, String type, double debit, double credit, double totalBal) {
		this.date = new SimpleStringProperty(date);
		this.type = new SimpleStringProperty(type);
		this.debit = new SimpleDoubleProperty(debit);
		this.credit = new SimpleDoubleProperty(credit);
		this.totalBalance = new SimpleDoubleProperty(totalBal);
	}
	
	public void setDate(String date) {
		this.date.set(date);
	}
	
	public String getDate(){
		return this.date.get();
	}
	
	public void setType(String type) {
		this.type.set(type);
	}
	
	public String getType() {
		return this.type.get();
	}
	
	public void setDebit(double debit){
		this.debit.set(debit);
	}
	
	public double getDebit(){
		return this.debit.get();
	}
	
	public void setCredit(double credit) {
		this.credit.set(credit);
	}
	
	public double getCredit(){
		return this.credit.get();
	}
	
	public void setTotalBalance(double totalBal){
		this.totalBalance.set(totalBal);
	}
	
	public double getTotalBalance(){
		return this.totalBalance.get();
	}
}
