import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Payments;

import org.controlsfx.control.textfield.TextFields;

import util.DateUtil;
import util.Form;
import util.Name;
import util.Rounding;

public class PaymentsDialogController {

	private double lessSunday;
	private double lessHoliday;
	@FXML
	TextField driverField = new TextField();
	@FXML
	TextField unitNumberField = new TextField();

	// Payments
	@FXML
	Label vehicleLbl = new Label();
	@FXML
	Label carRatePLabel = new Label();
	@FXML
	Label driverLessLbl = new Label();
	@FXML
	CheckBox lessSundayChk = new CheckBox();
	@FXML
	CheckBox lessHolidayChk = new CheckBox();
	@FXML
	Label finalRateLbl = new Label();
	@FXML
	TextField cashBondPField = new TextField();
	@FXML
	TextField shortagePField = new TextField();
	@FXML
	TextField damagePField = new TextField();
	@FXML
	TextField loanPField = new TextField();
	@FXML
	TextField appSavingField = new TextField();
	@FXML
	TextField cashRemitanceField = new TextField();

	// Current Balances
	@FXML
	Label boundaryLabel = new Label();
	@FXML
	Label totalLabel = new Label();
	@FXML
	Label cbCashbondsLbl = new Label();
	@FXML
	Label cbDamagesLbl = new Label();
	@FXML
	Label cbShortagesLbl = new Label();
	@FXML
	Label cbLoansLbl = new Label();
	@FXML
	Label cbPenaltiesLbl = new Label();

	// Balances Payments
	@FXML
	Label cashbondsPLbl = new Label();
	@FXML
	Label damagesPLbl = new Label();
	@FXML
	Label shortagesPLbl = new Label();
	@FXML
	Label loansPLbl = new Label();
	@FXML
	Label penaltiesPLbl = new Label();

	// New Balances Payments
	@FXML
	Label cashbondsNBLbl = new Label();
	@FXML
	Label damagesNBLbl = new Label();
	@FXML
	Label shortagesNBLbl = new Label();
	@FXML
	Label loansNBLbl = new Label();
	@FXML
	Label penaltiesNBLbl = new Label();

	// Expenses
	@FXML
	DatePicker startDateField = new DatePicker();
	@FXML
	TextField startTimeField = new TextField();
	@FXML
	DatePicker endDateField = new DatePicker();
	@FXML
	TextField endTimeField = new TextField();
	@FXML
	TextField totalTimeRepairField = new TextField();
	@FXML
	TextField repairCostField = new TextField();
	@FXML
	TextField kilometersRunField = new TextField();
	@FXML
	TextField gasLitersField = new TextField();
	@FXML
	TextArea remarksField = new TextArea();
	@FXML
	TextField grabTaxiField = new TextField();

	// Computation Results
	@FXML
	Label shortLbl = new Label();
	@FXML
	Label changeLbl = new Label();

	@FXML
	Button computeBtn = new Button();

	private Stage dialogStage;
	private Payments payments;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 *
	 * @throws SQLException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void initialize() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {

	}

	// Sets the stage of this dialog.
	// @param dialogStage

	public void setDialogStage(Stage dialogStage) {

		this.dialogStage = dialogStage;
	}

	// Sets the payments to be edited in the dialog.
	// @param payments

	public void setPayments(Object object, boolean edit) {

		this.payments = (Payments) object;

		this.cashRemitanceField.setText("0.0");
		// this.carRatePLabel.textProperty().addListener((observable, oldValue,
		// newValue) -> setBoundary());
		try {

			this.setDriverAutoField(edit);
			this.setVehicleAutoField(edit);

			this.initializeTxtFields();

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Set payments fields

		this.cashBondPField.setText(Double.toString(payments
				.getCashbondPayments()));
		this.shortagePField.setText(Double.toString(payments
				.getShortagePayments()));
		this.damagePField
				.setText(Double.toString(payments.getDamagePayments()));
		this.loanPField.setText(Double.toString(payments.getLoans()));
		this.appSavingField.setText(Double.toString(payments.getAppSaving()));
		this.shortLbl.setText(Double.toString(payments.getShortages()));
		this.boundaryLabel.setText(Double.toString(payments.getBoundaries()));

		// Set Expenses fields
		if (payments.getTimeRepairStart() == ""
				|| payments.getTimeRepairEnd() == ""
				|| payments.getTimeRepairStart() ==null
				|| payments.getTimeRepairEnd() == null) {

			startDateField.setValue(null);
			startTimeField.setText(null);

			endDateField.setValue(null);
			endTimeField.setText(null);

		} else {

			String [] timeStartarr = null;
			String[] timeEndArr = null;
			try {
				timeStartarr = DateUtil.reformatStringDT(payments.getTimeRepairStart()).split(" ");
				timeEndArr = DateUtil.reformatStringDT(payments.getTimeRepairEnd()).split(" ");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			startDateField.setValue(DateUtil.parse(timeStartarr[0]));
			startTimeField.setText(timeStartarr[1]);

			endDateField.setValue(DateUtil.parse(timeEndArr[0]));
			endTimeField.setText(timeEndArr[1]);
		}

		this.totalTimeRepairField.setText(Double.toString(payments
				.getTotalTimeRepair()));
		this.grabTaxiField.setText(Integer.toString(payments.getGrabTaxi()));
		this.kilometersRunField.setText(Double.toString(payments
				.getKilometersRun()));
		this.gasLitersField.setText(Double.toString(payments.getGasLiters()));
		this.remarksField.setText(payments.getRemarks());
		this.totalLabel.setText(Double.toString(payments.getTotal()));

	}

	// Returns true if the user clicked OK, false otherwise.
	// @return

	public boolean isOkClicked() {
		return okClicked;

	}

	// Called when the user clicks ok.
	@FXML
	private void handleOk() {

		// Show a warming message
		if (checkCash()) {
			String message = "";

			message += "Boundary: " + boundaryLabel.getText() + "\n";
			message += "Total: " + totalLabel.getText() + "\n";
			message += "\n";
			message += "Do you want to continue?";

			boolean answer = Form.dialogOkNo(null, message);
			if (answer) {
				if (isInputValid()) {

					// Set payments
					payments.setDriver(driverField.getText());
					payments.setUnitNumber(Integer.parseInt(unitNumberField.getText()));
					payments.setVehicle(vehicleLbl.getText());

					try {
						this.setAdminUser();
					} catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					payments.setCarRate(Double.parseDouble(carRatePLabel.getText()));
					payments.setCashbondPayments(Double.parseDouble(cashBondPField.getText()));
					payments.setShortagePayments(Double.parseDouble(shortagePField.getText()));
					payments.setDamagePayments(Double.parseDouble(damagePField.getText()));
					payments.setLoans(Double.parseDouble(loanPField.getText()));
					payments.setAppSaving(Double.parseDouble(appSavingField.getText()));
					payments.setShortages(Double.parseDouble(shortLbl.getText()));
					payments.setLessHoliday(lessHoliday);
					payments.setLessSunday(lessSunday);
					payments.setDriverLess(Double.parseDouble(driverLessLbl.getText()));
					payments.setBoundaries(Double.parseDouble(boundaryLabel
							.getText()));
					payments.setTotal(Double.parseDouble(totalLabel.getText()));

					String timeStart = startDateField.getValue() + " "
							+ startTimeField.getText();
					String timeEnd = endDateField.getValue() + " "
							+ endTimeField.getText();

					if (startDateField.getValue() == null
							|| startTimeField.getText() == null) {
						payments.setTimeRepairStart(null);
						payments.setTimeRepairEnd(null);

					} else if (endDateField.getValue() == null
							|| endTimeField.getText() == null) {
						payments.setTimeRepairStart(null);
						payments.setTimeRepairEnd(null);

					} else {

						payments.setTimeRepairStart(timeStart);
						payments.setTimeRepairEnd(timeEnd);
					}
					payments.setTotalTimeRepair(Double
							.parseDouble(totalTimeRepairField.getText()));
					payments.setRepairCost(Double.parseDouble(repairCostField
							.getText()));
					payments.setGrabTaxi(Integer.parseInt(grabTaxiField.getText()));
					payments.setKilometersRun(Double
							.parseDouble(kilometersRunField.getText()));
					payments.setGasLiters(Double.parseDouble(gasLitersField
							.getText()));
					payments.setRemarks(remarksField.getText());

					okClicked = true;

					dialogStage.close();
				}

			}
		} else {
			Form.dialog(AlertType.WARNING, "Warning", null, "Can't continue no cash received!");
		}
	}

	// Called when the user clicks cancel.

	private boolean checkCash() {
		boolean result = false;

			if(Double.parseDouble(totalTimeRepairField.getText()) == 16) {
				result = true;
			} else {
				if(Double.parseDouble(cashRemitanceField.getText().trim()) != 0.0
						&& !cashRemitanceField.getText().trim().equals(null)
						&& !cashRemitanceField.getText().equals("")
						&& !cashRemitanceField.getText().isEmpty()) {
					return true;
				} else {
					return false;
				}
			}
		return result;
	}

	@FXML
	private void handleCancel() {

		dialogStage.close();

	}

	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	////////////////// Payments Computation //////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	@FXML
	private void paymentsCompute() {
		double timeRepairCost = 0;
		double paymentsTotal = 0;
		double cashWithshortages = 0.0;
		double totalTimeRepair = Double.parseDouble(this.totalTimeRepairField
				.getText());
		double carRate = Double.parseDouble(this.carRatePLabel.getText());
		double driverLess = Double.parseDouble(driverLessLbl.getText());
		double cash = Double.parseDouble(this.cashRemitanceField.getText());
		// Check for holiday or Sunday
		lessSunday = lessSundayChk.isSelected() ? 100.0 : 0.0;
		lessHoliday = lessHolidayChk.isSelected() ? 100.0 : 0.0;

		// Compute for driver's less
		double totalLess = driverLess + lessSunday + lessHoliday;
		double finalRate = carRate - totalLess;
		finalRateLbl.setText(Double.toString(finalRate));

		// Get the total payment
		paymentsTotal = Double.parseDouble(this.cashBondPField.getText())
				+ Double.parseDouble(this.shortagePField.getText())
				+ Double.parseDouble(this.damagePField.getText())
				+ Double.parseDouble(loanPField.getText())
				+ Double.parseDouble(this.appSavingField.getText()) + cash
				+ timeRepairCost;

		totalLabel.setText(Double.toString(Rounding.round(paymentsTotal, 2)));
		// totalLabel.setText(Double.toString(Math.round(paymentsTotal)));

		cashWithshortages = finalRate - cash;
		/*
		if (totalTimeRepair != 0.0) {

			/*
			 * FORMULA:
			 * timeRepairCost = carRate - 10dispatchPay = 1290 /
			 * 16rental hrs x totalRepairTime
			 *
			if(totalTimeRepair >= 16) {
				timeRepairCost = Rounding.round((((finalRate) / 16) * totalTimeRepair),2);
				repairCostField.setText(Double.toString(Rounding.round(timeRepairCost, 2)));
			} else {
				timeRepairCost = Rounding.round((((finalRate - 10) / 16) * totalTimeRepair),2);
				repairCostField.setText(Double.toString(timeRepairCost));
			}
			cashWithshortages -= timeRepairCost;

		//} else {
			//timeRepairCost = 0;
			//repairCostField.setText(Double.toString(Rounding.round(timeRepairCost, 2)));
		//}*/

		timeRepairCost = Double.parseDouble(repairCostField.getText());
		cashWithshortages -= timeRepairCost;

		shortLbl.setText(Double.toString(Rounding.round(cashWithshortages, 2)));
		boundaryLabel.setText(Double.toString(Rounding.round(cash, 2)));

		System.out.println("boundary "+cash);
		// shortLbl.setText(Double.toString(Math.round(cashWithshortages)));
		// boundaryLabel.setText(Double.toString(Math.round(cash)));

		cashbondsPLbl.setText(cashBondPField.getText());
		shortagesPLbl.setText(shortagePField.getText());
		damagesPLbl.setText(damagePField.getText());
		loansPLbl.setText(loanPField.getText());
		penaltiesPLbl.setText(appSavingField.getText());

		double nbCb = (Double.parseDouble(cbCashbondsLbl.getText()) + Double.parseDouble(cashbondsPLbl.getText()));
		double sbCb = (Double.parseDouble(cbShortagesLbl.getText()) - Double.parseDouble(shortagesPLbl.getText()));
		double dbCb = (Double.parseDouble(cbDamagesLbl.getText()) - Double.parseDouble(damagesPLbl.getText()));
		double lbCb = (Double.parseDouble(cbLoansLbl.getText()) - Double.parseDouble(loansPLbl.getText()));
		double pbCb = (Double.parseDouble(cbPenaltiesLbl.getText()) - Double.parseDouble(penaltiesPLbl.getText()));

		cashbondsNBLbl.setText(Double.toString(Rounding.round(nbCb, 2)));
		shortagesNBLbl.setText(Double.toString(Rounding.round(sbCb,2)));
		damagesNBLbl.setText(Double.toString(Rounding.round(dbCb,2)));
		loansNBLbl.setText(Double.toString(Rounding.round(lbCb, 2)));
		penaltiesNBLbl.setText(Double.toString(Rounding.round(pbCb, 2)));

	}

	private void initializeCbLbl() throws SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

		DataManipulator dataman = new DataManipulator();
		String query = "";

		ResultSet rs = null;

		// Get current balance
		query = "SELECT SUM(cashbonds) AS cashbonds, SUM(shortages) AS shortages, "
				+ "SUM(damages) AS damages, SUM(loans) AS loans, "
				+ "SUM(penalties) AS penalties FROM balance WHERE driver_id = "
				+ getDriverId();

		rs = dataman.generalQuery(query);

		while (rs.next()) {
			cbCashbondsLbl.setText(Double.toString(Rounding.round(rs.getDouble("cashbonds"),2)));
			cbDamagesLbl.setText(Double.toString(Rounding.round(rs.getDouble("damages"), 2)));
			cbShortagesLbl.setText(Double.toString(Rounding.round(rs.getDouble("shortages"), 2)));
			cbLoansLbl.setText(Double.toString(Rounding.round(rs.getDouble("loans"), 2)));
			cbPenaltiesLbl.setText(Double.toString(Rounding.round(rs.getDouble("penalties"), 2)));
		}
	}

	// Compute for total time repair
	@FXML
	private void dateTimeCompute() throws ParseException {
		double date = 0.0;
		if (startDateField.getValue() != null
				&& startTimeField.getText() != null
				&& endDateField.getValue() != null
				&& endTimeField.getText() != null) {

			Date startRepair = DateUtil.formatDateTime(startDateField
					.getValue() + " " + startTimeField.getText());
			Date endRepair = DateUtil.formatDateTime(endDateField.getValue()
					+ " " + endTimeField.getText());
			try {
				long diff = endRepair.getTime() - startRepair.getTime();

				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;

				String totalTR = diffHours + "." + diffMinutes;

				date = Rounding.round(Double.parseDouble(totalTR), 2);


				if (date < 0) {
					Form.dialog(AlertType.INFORMATION, "Info", null, "Time repair end should be greater than time repair start");
				} else {

					//totalTimeRepairLabel.setText(Double.toString(TimeUnit.MILLISECONDS.toHours(date)));
					totalTimeRepairField.setText(Double.toString(date));
					paymentsCompute();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			totalTimeRepairField.setText("0");
			paymentsCompute();
		}

	}

	// Get the drivers less for boundary
	private void setDriverLess() {
		try {
			DataManipulator dataman = new DataManipulator();
			String query = "";
			ResultSet rs = null;
			int driverId = getDriverId();

			query = "SELECT less_rate FROM drivers "
					+ "INNER JOIN dispatch as d ON drivers.id = d.driver_id "
					+ "WHERE d.driver_id = " + driverId;
			System.out.println(query);

			rs = dataman.generalQuery(query);
			while (rs.next()) {
				driverLessLbl.setText(rs.getString("less_rate"));
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Get Drivers unique id in the database
	private int getDriverId() {

		DataManipulator dataman;
		String query = "";
		String driverName = "";
		int driverId = -1;
		ResultSet rs = null;

		try {
			driverName = driverField.getText();

			dataman = new DataManipulator();
			// Get driver's id

			query = "SELECT id FROM drivers "
					+ "WHERE drivers.first_name = \"" + Name.getFirstName(driverName) + "\" "
					+ "AND drivers.last_name = \"" + Name.getLastName(driverName) + "\"";

			rs = dataman.generalQuery(query);

			while (rs.next()) {
				driverId = rs.getInt("id");
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driverId;
	}

	private void setValues() {

		cashBondPField.setText("0.0");
		shortagePField.setText("0.0");
		damagePField.setText("0.0");
		loanPField.setText("0.0");
		appSavingField.setText("0.0");

		startDateField.setValue(null);
		startTimeField.setText(null);
		endDateField.setValue(null);
		endTimeField.setText(null);
		totalTimeRepairField.setText("0");
		kilometersRunField.setText("0.0");
		gasLitersField.setText("0.0");
		remarksField.setText("");

		// Get current balance
		try {
			setDriverLess();
			initializeCbLbl();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setDriverAutoField(boolean edit)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, SQLException {
		DataManipulator dataman = new DataManipulator();

		String query = "SELECT first_name, mid_name, last_name FROM drivers WHERE dispatch_status = 1";

		ResultSet rs = dataman.generalQuery(query);

		ObservableList<String> driversList = FXCollections
				.observableArrayList();

		while (rs.next()) {
			driversList.add(Name.createFullName(rs.getString("first_name"),
					rs.getString("mid_name"), rs.getString("last_name")));
		}

		TextFields.bindAutoCompletion(driverField, driversList);

		driverField.setText(payments.getDriver());



		if(driverField.getText() != null)
		{
			initializeCbLbl();
		}
	}

	private void setVehicleAutoField(boolean edit)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, SQLException {
		/*
		if (edit) {

			vehicleField.setText(payments.getVehicle());

		} else {
			DataManipulator dataman = new DataManipulator();

			String query = "SELECT * FROM " + Constants.DB_VEHICLE_TBL
					+ " WHERE status = \"on dispatch\"";

			ResultSet rs = dataman.generalQuery(query);

			ObservableList<String> vehicleList = FXCollections
					.observableArrayList();

			while (rs.next()) {

				vehicleList.add(rs.getString("plate_number"));
			}

			TextFields.bindAutoCompletion(vehicleField, vehicleList);
		}*/

		DataManipulator dataman = new DataManipulator();

		String query = "SELECT unit_number FROM " + Constants.DB_VEHICLE_TBL
				+ " WHERE status = \"on dispatch\"";

		ResultSet rs = dataman.generalQuery(query);

		ObservableList<String> unitNumberList = FXCollections.observableArrayList();

		while (rs.next()) {

			unitNumberList.add(rs.getString("unit_number"));
		}

		TextFields.bindAutoCompletion(unitNumberField, unitNumberList);
		unitNumberField.setText(Integer.toString(payments.getUnitNumber()));
		if(unitNumberField.getText() != null){
			setCarRate();
		}
	}

	private void setCarRate() {
		try {
			DataManipulator dataman = new DataManipulator();
			ResultSet rs = null;

			rs = dataman.generalQuery("SELECT plate_number, rate FROM "
					+ Constants.DB_VEHICLE_TBL + " WHERE unit_number = \""
					+ unitNumberField.getText() + "\"");

			while (rs.next()) {
				vehicleLbl.setText(rs.getString("plate_number"));
				carRatePLabel.setText(Double.toString(rs.getDouble("rate")));
			}

			boundaryLabel.setText(carRatePLabel.getText());
			totalLabel.setText(carRatePLabel.getText());

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setAdminUser() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {
		DataManipulator dataman = new DataManipulator();
		String query = "SELECT first_name, mid_name, last_name FROM "
				+ Constants.DB_ADMIN_TBL + " WHERE username = \""
				+ Constants.USERNAME + "\"";

		ResultSet rs = dataman.generalQuery(query);

		String adminName = "";
		while (rs.next()) {
			// Create middle initial
			String midName = rs.getString("mid_name").substring(0, 1) + ".";

			// Concatenate the driver's name
			adminName = rs.getString("first_name") + " " + midName + " "
					+ rs.getString("last_name");

		}

		payments.setAdminUser(adminName);
	}

	// Validates the user input in the text fields.
	// @return true if the input is valid

	private boolean isInputValid() {
		String errorMessage = "";

		if (driverField.getText().equals(null)
				|| driverField.getText().length() == 0) {
			errorMessage += "No valid driver name!\n";
		}
		if (unitNumberField.getText().equals(null)
				|| unitNumberField.getText().length() == 0) {
			errorMessage += "No valid vehicle!\n";
		}
		if (carRatePLabel.getText() == null
				|| carRatePLabel.getText().length() == 0) {
			errorMessage += "No valid car rate!\n";
		}

		if (Double.parseDouble(totalTimeRepairField.getText()) != 0.0
				&& (remarksField.getText() == null || remarksField.getText()
						.length() == 0)) {

			errorMessage += "Please input remarks about the shortages and others!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {

			// Show the error message.
			Form.dialog(AlertType.INFORMATION, "Info", null, errorMessage);
			return false;
		}
	}

	private void initializeTxtFields() throws SQLException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {

		driverField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							setValues();
						}
					}
				});
		unitNumberField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							setCarRate();
						}
					}
				});
		repairCostField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							paymentsCompute();
						}
					}

				});
		totalTimeRepairField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							paymentsCompute();
						}
					}

				});
		cashBondPField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							paymentsCompute();
						}
					}

				});
		damagePField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							try {
								if (Double.parseDouble(damagePField.getText()
										.trim()) != 0.0) {

									if (!checkBalance("damages")) {
										damagePField.setText("0.0");
									}
								} else if (damagePField.getText().isEmpty()) {

									System.out.println(damagePField.getText());
									damagePField.setText("0.0");
								}
							} catch (ClassNotFoundException
									| InstantiationException
									| IllegalAccessException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});

		shortagePField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							try {
								if (Double.parseDouble(shortagePField.getText()
										.trim()) != 0.0) {

									if (!checkBalance("shortages")) {
										shortagePField.setText("0.0");
										;
									}
								}
							} catch (ClassNotFoundException
									| InstantiationException
									| IllegalAccessException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
		loanPField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean oldPropertyValue, Boolean newPropertyValue) {
				if (!newPropertyValue) {
					try {
						if (Double.parseDouble(loanPField.getText().trim()) != 0.0) {
							if (!checkBalance("loans")) {
								loanPField.setText("0.0");
								;
							}
						}
					} catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		appSavingField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							paymentsCompute();
						}
					}
				});

		cashRemitanceField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							paymentsCompute();

						}
					}
				});

		startTimeField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							try {
								dateTimeCompute();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
		endTimeField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							try {
								dateTimeCompute();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});

	}

	private boolean checkBalance(String balanceName)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, SQLException {
		boolean hasBalance = false;
		DataManipulator dataman = new DataManipulator();
		String query = "";

		if (driverField.getText() != null) {

			ResultSet rs = null;

			// Get driver's id
			query = "SELECT id FROM drivers " + "WHERE drivers.first_name = \""
					+ Name.getFirstName(driverField.getText()) + "\" AND drivers.last_name = \""
					+ Name.getLastName(driverField.getText()) + "\"";
			rs = dataman.generalQuery(query);
			int driverId = 0;
			while (rs.next()) {
				driverId = rs.getInt("id");
			}

			query = "SELECT SUM(" + balanceName
					+ ") AS " + balanceName + " FROM balance WHERE driver_id = " + driverId;

			rs = dataman.generalQuery(query);
			while (rs.next()) {
				if (rs.getDouble(balanceName) == 0.0) {
					String errorMessage = driverField.getText()
							+ " has no current " + balanceName + " balance";
					Form.dialog(AlertType.INFORMATION, "Information", null, errorMessage);
					hasBalance = false;
				} else {
					paymentsCompute();
					hasBalance = true;
				}
			}
		}

		return hasBalance;

	}

}
