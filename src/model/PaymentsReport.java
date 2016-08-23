package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PaymentsReport extends Payments {
	
	private SimpleIntegerProperty thousands;
	private SimpleIntegerProperty fiveHundreds;
	private SimpleIntegerProperty twoHundreds;
	private SimpleIntegerProperty hundreds;
	private SimpleIntegerProperty fifties;
	private SimpleIntegerProperty twenties;
	private SimpleIntegerProperty tens;
	private SimpleIntegerProperty fives;
	private SimpleIntegerProperty pesos;
	private SimpleIntegerProperty fiftyCentavos;
	private SimpleIntegerProperty twentyFiveCentavos;
	private SimpleIntegerProperty tenCentavos;
	private SimpleIntegerProperty fiveCentavos;
	private SimpleDoubleProperty damagesPayments;
	private SimpleDoubleProperty damagesAddons;
	private SimpleDoubleProperty damagesGenTotal;
	private SimpleDoubleProperty shortagesPayments;
	private SimpleDoubleProperty shortagesAddons;
	private SimpleDoubleProperty shortagesGenTotal;
	private SimpleDoubleProperty cashbondsPayments;
	private SimpleDoubleProperty cashbondsAddons;
	private SimpleDoubleProperty cashbondsGenTotal;
	
	
	public PaymentsReport()
	{
		this(0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		
	}
	
	public PaymentsReport(int thousands, int fiveHundreds, int twoHundreds, int hundreds, int fifties, int twenties, int tens, int fives,
						 int pesos, int fiftyCentavos, int twentyFiveCentavos, int tenCentavos, int fiveCentavos) {
		
		this.thousands = new SimpleIntegerProperty(thousands);
		this.fiveHundreds = new SimpleIntegerProperty(fiveHundreds);
		this.twoHundreds = new SimpleIntegerProperty(twoHundreds);
		this.hundreds = new SimpleIntegerProperty(hundreds);
		this.fifties = new SimpleIntegerProperty(fifties);
		this.twenties = new SimpleIntegerProperty(twenties);
		this.tens = new SimpleIntegerProperty(tens);
		this.fives = new SimpleIntegerProperty(fives);
		this.pesos = new SimpleIntegerProperty(pesos);
		this.fiftyCentavos = new SimpleIntegerProperty(fiftyCentavos);
		this.twentyFiveCentavos = new SimpleIntegerProperty(twentyFiveCentavos);
		this.tenCentavos = new SimpleIntegerProperty(tenCentavos);
		this.fiveCentavos = new SimpleIntegerProperty(fiveCentavos);
		
	}
	
	public void setThousands(int thousands) {
		this.thousands.set(thousands);
	}
	
	public int getThousands() {
		return this.thousands.get();
	}
	
	public void setFiveHundreds(int fiveHundreds) {
		this.fiveHundreds.set(fiveHundreds);
	}
	
	public int getFiveHundreds() {
		return this.fiveHundreds.get();
	}
	
	public void setTwoHundreds(int twoHundreds) {
		this.twoHundreds.set(twoHundreds);
	}
	
	public int getTwoHundreds() {
		return this.twoHundreds.get();
	}
	
	public void setHundreds(int hundreds) {
		this.hundreds.set(hundreds);
	}
	
	public int getHundreds() {
		return this.hundreds.get();
	}
	
	public void setFifties(int fifties) {
		this.fifties.set(fifties);
	}
	
	public int getFifties() {
		return this.fifties.get();
	}
	
	public void setTwenties(int twenties) {
		this.twenties.set(twenties);
	}
	
	public int getTwenties() {
		return this.twenties.get();
	}
	
	public void setTens(int tens) {
		this.tens.set(tens);
	}
	
	public int getTens() {
		return this.tens.get();
	}
	
	public void setFives(int fives) {
		this.fives.set(fives);
	}
	
	public int getFives() {
		return this.fives.get();
	}

	public void setPesos(int pesos) {
		this.pesos.set(pesos);
	}
	
	public int getPesos() {
		return this.pesos.get();
	}
	
	public void setFiftyCentavos(int fiftyCentavos) {
		this.fiftyCentavos.set(fiftyCentavos);
	}
	
	public int getFiftyCentavos() {
		return this.fiftyCentavos.get();
	}
	
	public void setTwentyFiveCentavos(int twentyFiveCentavos) {
		this.twentyFiveCentavos.set(twentyFiveCentavos);
	}
	
	public int getTwentyFiveCentavos() {
		return this.twentyFiveCentavos.get();
	}
	
	public void setTenCentavos(int tenCentavos) {
		this.tenCentavos.set(tenCentavos);
	}
	
	public int getTenCentavos() {
		return this.tenCentavos.get();
	}
	
	public void setFiveCentavos(int fiveCentavos) {
		this.fiveCentavos.set(fiveCentavos);
	}
	
	public int getFiveCentavos() {
		return this.fiveCentavos.get();
	}
}
