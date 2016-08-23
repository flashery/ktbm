import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Dispatch;
import model.Payments;
import util.DateUtil;
import util.Form;
import util.Name;
import util.Rounding;

import com.opencsv.CSVWriter;

public class PaymentsController implements Initializable {

	private Main main = new Main();

	private ObservableList<Payments> data = FXCollections.observableArrayList();

	@FXML
	DatePicker dateField = new DatePicker();

	@FXML
	Button addPaymentsBtn = new Button();
	@FXML
	Button editPaymentsBtn = new Button();
	@FXML
	Button deletePaymentsBtn = new Button();

	@FXML
	TextField searchField = new TextField();
	@FXML
	Label reportDateLabel = new Label();
	@FXML
	private TableView<Payments> table = new TableView<Payments>();
	@FXML
	Label damagesBalanceLbl = new Label();
	@FXML
	Label damagesAddonsLbl = new Label();
	@FXML
	Label damagesTotalLbl = new Label();
	@FXML
	Label damagesPaymentsLbl = new Label();
	@FXML
	Label damagesGenTotalLbl = new Label();
	@FXML
	Label shortagesBalanceLbl = new Label();
	@FXML
	Label shortagesAddonsLbl = new Label();
	@FXML
	Label shortagesTotalLbl = new Label();
	@FXML
	Label shortagesPaymentsLbl = new Label();
	@FXML
	Label shortagesGenTotalLbl = new Label();
	@FXML
	Label shortageslBalanceLbl = new Label();
	@FXML
	Label cashbondsBalanceLbl = new Label();
	@FXML
	Label cashbondsAddonsLbl = new Label();
	@FXML
	Label cashbondsTotalLbl = new Label();
	@FXML
	Label cashbondsPaymentsLbl = new Label();
	@FXML
	Label cashbondsGenTotalLbl = new Label();
	@FXML
	Label cashbondslBalanceLbl = new Label();

	@FXML
	TextField thousandsPcsField = new TextField();
	@FXML
	TextField fiveHundredsPcsField = new TextField();
	@FXML
	TextField twoHundredsPcsField = new TextField();
	@FXML
	TextField hundredsPcsField = new TextField();
	@FXML
	TextField fiftiesPcsField = new TextField();
	@FXML
	TextField twentiesPcsField = new TextField();
	@FXML
	TextField tensPcsField = new TextField();
	@FXML
	TextField fivesPcsField = new TextField();
	@FXML
	TextField pesosPcsField = new TextField();
	@FXML
	TextField fiftyCentsPcsField = new TextField();
	@FXML
	TextField twentyFiveCentsPcsField = new TextField();
	@FXML
	TextField tenCentsPcsField = new TextField();
	@FXML
	TextField fiveCentsPcsField = new TextField();

	@FXML
	Label thousandsAmountLbl = new Label();
	@FXML
	Label fiveHundredsAmountLbl = new Label();
	@FXML
	Label twoHundredsAmountLbl = new Label();
	@FXML
	Label hundredsAmountLbl = new Label();
	@FXML
	Label fiftyAmountLbl = new Label();
	@FXML
	Label twentyAmountLbl = new Label();
	@FXML
	Label tenAmountLbl = new Label();
	@FXML
	Label fiveAmountLbl = new Label();
	@FXML
	Label oneAmountLbl = new Label();
	@FXML
	Label fiftyCentsAmountLbl = new Label();
	@FXML
	Label twentyFiveCentsAmountLbl = new Label();
	@FXML
	Label tenCentsAmountLbl = new Label();
	@FXML
	Label fiveCentsAmountLbl = new Label();

	@FXML
	Label totalBillLbl = new Label();

	@FXML
	Button thousandsAdd = new Button();
	@FXML
	Button fiveHundredsAdd = new Button();
	@FXML
	Button twoHundredsAdd = new Button();
	@FXML
	Button hundredsAdd = new Button();
	@FXML
	Button fiftiesAdd = new Button();
	@FXML
	Button twentiesAdd = new Button();
	@FXML
	Button tensAdd = new Button();
	@FXML
	Button fivesAdd = new Button();
	@FXML
	Button pesosAdd = new Button();
	@FXML
	Button fiftyCentsAdd = new Button();
	@FXML
	Button twentyFiveCentsAdd = new Button();
	@FXML
	Button tenCentsAdd = new Button();
	@FXML
	Button fiveCentsAdd = new Button();
	@FXML
	Button thousandsMinus = new Button();
	@FXML
	Button fiveHundredsMinus = new Button();
	@FXML
	Button twoHundredsMinus = new Button();
	@FXML
	Button hundredsMinus = new Button();
	@FXML
	Button fiftiesMinus = new Button();
	@FXML
	Button twentiesMinus = new Button();
	@FXML
	Button tensMinus = new Button();
	@FXML
	Button fivesMinus = new Button();
	@FXML
	Button pesosMinus = new Button();
	@FXML
	Button fiftyCentsMinus = new Button();
	@FXML
	Button twentyFiveCentsMinus = new Button();
	@FXML
	Button tenCentsMinus = new Button();
	@FXML
	Button fiveCentsMinus = new Button();

	@FXML
	Label nOUPLbl = new Label();
	@FXML
	Label tOILbl = new Label();
	@FXML
	TextField expensesField = new TextField();
	@FXML
	TextField allowancedField = new TextField();
	@FXML
	TextField rebatesField = new TextField();
	@FXML
	TextField cashOutField = new TextField();
	@FXML
	Label netTotalLbl = new Label();

	private ResultSet rs;

	public boolean ok = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		dateField.setValue(DateUtil.parse(DateUtil.newDateNormal()));

		// Let's disable the buttons if not login as Admin
		if (!Constants.USERTYPE.equals("admin")) {
			this.editPaymentsBtn.setDisable(true);
			this.deletePaymentsBtn.setDisable(true);

		}

		// Let's disable the buttons if not login as Admin
		if (Constants.USERTYPE.equals("dispatcher")) {
			this.addPaymentsBtn.setDisable(true);

		}
		this.createTableColumn();
		createObsData();
		filteredData();
	}

	@SuppressWarnings({"rawtypes", "unchecked" })
	@FXML
	private void createTableColumn(){


		TableColumn driverCol = new TableColumn<Payments, String>("Driver");
		driverCol.setCellValueFactory(new PropertyValueFactory<>("driver"));

		TableColumn unitNumberCol = new TableColumn<Payments, String>("Unit Number");
		unitNumberCol.setCellValueFactory(new PropertyValueFactory<>("unitNumber"));

		TableColumn vehicleCol = new TableColumn<Payments, String>("Vehicle");
		vehicleCol.setCellValueFactory(new PropertyValueFactory<>("vehicle"));

		TableColumn carRateCol = new TableColumn<Payments, String>("Car Rate");
		carRateCol.setCellValueFactory(new PropertyValueFactory<>("carRate"));

		TableColumn adminUserCol = new TableColumn<Payments, String>(
				"Dispatcher");
		adminUserCol
				.setCellValueFactory(new PropertyValueFactory<>("adminUser"));

		TableColumn boundariesCol = new TableColumn<Payments, String>(
				"Boundary");
		boundariesCol.setCellValueFactory(new PropertyValueFactory<>(
				"boundaries"));

		TableColumn cashbondPaymentsCol = new TableColumn<Payments, String>(
				"Cashbonds Payments");
		cashbondPaymentsCol.setCellValueFactory(new PropertyValueFactory<>(
				"cashbondPayments"));

		TableColumn shortagePaymentsCol = new TableColumn<Payments, String>(
				"Shortage Payments");
		shortagePaymentsCol.setCellValueFactory(new PropertyValueFactory<>(
				"shortagePayments"));

		TableColumn damagePaymentsCol = new TableColumn<Payments, String>(
				"Damage Payments");
		damagePaymentsCol.setCellValueFactory(new PropertyValueFactory<>(
				"damagePayments"));

		TableColumn loansCol = new TableColumn<Payments, String>("Loans");
		loansCol.setCellValueFactory(new PropertyValueFactory<>("loans"));

		TableColumn appSavingCol = new TableColumn<Payments, String>(
				"Apprehension Saving");
		appSavingCol.setCellValueFactory(new PropertyValueFactory<>("penalties"));

		TableColumn shortagesCol = new TableColumn<Payments, String>(
				"Shortages");
		shortagesCol
				.setCellValueFactory(new PropertyValueFactory<>("shortages"));

		TableColumn lessSundayCol = new TableColumn<Payments, String>(
				"Less Sunday");
		lessSundayCol
				.setCellValueFactory(new PropertyValueFactory<>("lessSunday"));

		TableColumn lessHolidaydayCol = new TableColumn<Payments, String>(
				"Less Holiday");
		lessHolidaydayCol
				.setCellValueFactory(new PropertyValueFactory<>("lessHoliday"));

		TableColumn driverLessCol = new TableColumn<Payments, String>(
				"Driver Less Rate");
		driverLessCol
				.setCellValueFactory(new PropertyValueFactory<>("driverLess"));

		TableColumn timeRepairStartCol = new TableColumn<Payments, String>(
				"Time Repair Start");
		timeRepairStartCol.setCellValueFactory(new PropertyValueFactory<>(
				"timeRepairStart"));

		TableColumn timeRepairEndCol = new TableColumn<Payments, String>(
				"Time Repair End");
		timeRepairEndCol.setCellValueFactory(new PropertyValueFactory<>(
				"timeRepairEnd"));

		TableColumn totalTimeRepairCol = new TableColumn<Payments, String>(
				"Total Time Repair");
		totalTimeRepairCol.setCellValueFactory(new PropertyValueFactory<>(
				"totalTimeRepair"));

		TableColumn repairCostCol = new TableColumn<Payments, String>(
				"Repair Cost");
		repairCostCol.setCellValueFactory(new PropertyValueFactory<>(
				"repairCost"));

		TableColumn grabTaxiCol = new TableColumn<Payments, String>("Grab Taxi Passenger");
		grabTaxiCol.setCellValueFactory(new PropertyValueFactory<>("grabTaxi"));

		TableColumn kilometersRunCol = new TableColumn<Payments, String>(
				"Kilometers Run");
		kilometersRunCol.setCellValueFactory(new PropertyValueFactory<>(
				"kilometersRun"));

		TableColumn gasLitersCol = new TableColumn<Payments, String>(
				"Gas Liters");
		gasLitersCol
				.setCellValueFactory(new PropertyValueFactory<>("gasLiters"));

		TableColumn remarksCol = new TableColumn<Payments, String>("Remarks");
		remarksCol.setCellValueFactory(new PropertyValueFactory<>("remarks"));

		TableColumn totalCol = new TableColumn<Payments, String>(
				"Total Payments");
		totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

		TableColumn dateCol = new TableColumn<Payments, String>("Date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

		// Add filtered data to the table
		table.setItems(data);

		table.getColumns().addAll(driverCol, unitNumberCol, vehicleCol, carRateCol,
				adminUserCol, boundariesCol, cashbondPaymentsCol,
				shortagePaymentsCol, damagePaymentsCol, loansCol, appSavingCol,
				shortagesCol, lessSundayCol, lessHolidaydayCol, driverLessCol, timeRepairStartCol, timeRepairEndCol,
				totalTimeRepairCol, repairCostCol, grabTaxiCol, kilometersRunCol, gasLitersCol, remarksCol,
				totalCol, dateCol);

	}

	private void createObsData() {
		try {
			DataManipulator dataman = new DataManipulator();
			// Create our TableModel
			rs = dataman.generalQuery(this.query());

			table.setEditable(true);
			while (rs.next()) {
				String driverName = Name.createFullName(
						rs.getString("driver_first_name"),
						rs.getString("driver_mid_name"),
						rs.getString("driver_last_name"));
				String adminName = Name.createFullName(rs.getString("admin_first_name"), rs.getString("admin_mid_name"), rs.getString("admin_last_name"));

				// Format datetime
				String tRStart = rs.getString(15) == null ? "" : DateUtil.formatStringDT(rs.getString("time_repair_start"));
				String tREnd = rs.getString(16) == null ? "" : DateUtil.formatStringDT(rs.getString("time_repair_end"));

				data.add(new Payments(rs.getInt(1),
						driverName,
						rs.getInt("unit_number"),
						rs.getString("vehicle_plate_number"),
						rs.getDouble("vehicle_car_rate"),
						adminName,
						rs.getDouble(6),
						rs.getDouble(7),
						rs.getDouble("shortage_payments"),
						rs.getDouble(9),
						rs.getDouble(10),
						rs.getDouble(11),
						rs.getDouble(12),
						rs.getDouble(13),
						rs.getDouble(14),
						rs.getDouble("driver_less_rate"),
						tRStart,
						tREnd,
						rs.getDouble(17),
						rs.getDouble(18),
						rs.getInt("grab_taxi"),
						rs.getDouble(20),
						rs.getDouble(21),
						rs.getString("remarks"),
						rs.getDouble(23),
						rs.getString("payments_date")));

			}

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

    @FXML
    private void refreshTable() {

    	data.removeAll(data);
		createObsData();
    }

	private ObservableList<Payments> getpaymentsData() {
		return this.data;
	}


	// Get Drivers unique id in the database
	private int getDriverId(Payments payments) {

		DataManipulator dataman;
		String query = "";
		int driverId = 0;
		ResultSet rs = null;

		try {
			dataman = new DataManipulator();
			// Get driver's id

			query = "SELECT id FROM drivers "
					+ "WHERE drivers.first_name = \"" + Name.getFirstName(payments.getDriver()) + "\" "
					+ "AND drivers.last_name = \"" + Name.getLastName(payments.getDriver()) + "\"";

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

	// Get Drivers unique id in the database
	private int getAdminId(Payments payments) {

			DataManipulator dataman;
			int adminId = 0;
			ResultSet rs = null;

			try {
				dataman = new DataManipulator();

				rs = dataman.generalQuery("SELECT id FROM admins WHERE username = \""  + Constants.USERNAME + "\"" );
				while (rs.next()) {
					adminId = rs.getInt("id");
				}
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return adminId;

	}

	/*
	 * Initialize methods
	 */

	// Filtering method
	private void filteredData() {
		// 1. Wrap the ObservableList in a FilteredList (initially display all
		// data).
		FilteredList<Payments> filteredData = new FilteredList<>(data,
				p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		searchField.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					filteredData.setPredicate(payments -> {
						// If filter text is empty, display all Dispatchs.
							if (newValue == null || newValue.isEmpty()) {
								return true;
							}

							// Compare first name and last name of every
							// Dispatch with filter text.
							String lowerCaseFilter = newValue.toLowerCase();

							if (payments.getDriver().toLowerCase()
									.contains(lowerCaseFilter)) {
								return true; // Filter matches driver name.
							} else if (payments.getVehicle().toLowerCase()
									.contains(lowerCaseFilter)) {
								return true; // Filter matches vehicle
												// plate_number.
							} else if (payments.getAdminUser().toLowerCase()
									.contains(lowerCaseFilter)) {
								return true; // Filter matches vehicle
												// plate_number.
							} else if (payments.getDate().toLowerCase()
									.contains(lowerCaseFilter)) {
								return true; // Filter matches vehicle
												// plate_number.
							}
							return false; // Does not match.
						});
				});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Payments> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(table.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		table.setItems(sortedData);

	}

	@FXML
	public void writeCSV() {

		File file = new File("C:\\Payments.csv");

		try {

			CSVWriter writer = new CSVWriter(new FileWriter(file));

			DataManipulator dataman = new DataManipulator();

			// Create our TableModel
			rs = dataman.generalQuery(this.query());
			// Table Headers
			String header = "Driver # Vehicle # Car Rate# Dispatcher # Boundary # Casbonds Payments# Shortage Payments # Damage Payments # "
					+ "Loans # Penalties # Shortages # Time Repair Start # Time Repair End # Total Time Repair # Kilometers Run # "
					+ "Gas Liters # Remars # Total Payments # Date";
			String[] headers = header.split("#");
			writer.writeNext(headers);

			// Table Rows
			while (rs.next()) {
				String driverName = Name.createFullName(rs.getString("d.first_name"), rs.getString("d.mid_name"), rs.getString("d.last_name"));
				String adminName = Name.createFullName(rs.getString("a.first_name"), rs.getString("a.mid_name"), rs.getString("a.last_name"));

				// Format datetime
				String tRStart = rs.getString(15) == null ? "" : DateUtil.formatStringDT(rs.getString("time_repair_start"));
				String tREnd = rs.getString(16) == null ? "" : DateUtil.formatStringDT(rs.getString("time_repair_end"));


				String entry = driverName + "#"
						+ rs.getString("vehicle_plate_number") + "#"
						+ rs.getDouble("vehicle_car_rate") + "#" + adminName
						+ "#" + rs.getDouble(6) + "#" + rs.getDouble(7) + "#"
						+ rs.getDouble("shortage_payments") + "#"
						+ rs.getDouble(9) + "#" + rs.getDouble(10) + "#"
						+ rs.getDouble(11) + "#" + rs.getDouble(12) + "#"
						+ tRStart + "#" + tREnd + "#" + rs.getInt(15) + "#"
						+ rs.getDouble(16) + "#" + rs.getDouble(17) + "#"
						+ rs.getString("remarks") + "#" + rs.getDouble(19)
						+ "#" + rs.getString("payments_date");

				String[] entries = entry.split("#");

				writer.writeNext(entries);

			}

			writer.flush();
			writer.close();

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException | IOException
				| ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Form.dialog(AlertType.INFORMATION, "Info", null, "Please close the Payments.csv file");
		} finally {
			openFile(file);
		}
	}

	private void writeCasheirReportCSV() {

		File file = new File("C:\\CasheirReport.csv");

		try {

			CSVWriter writer = new CSVWriter(new FileWriter(file));

			String header = "Peso Bills #  Pieces # Amount" + "#" + " " + "#" + " " +  "#" + " " + "#" + "General Damages";
			String[] headers = header.split("#");
			writer.writeNext(headers);

			String content1 = "";
			content1 =  "1000" + "#" + thousandsPcsField.getText() + "#" + thousandsAmountLbl.getText() + "#" + " " + "#" + "Current Damages" + "#" + damagesBalanceLbl.getText() +"#" + "Addons" +  "#" + damagesAddonsLbl.getText();
			String [] contents1 = content1.split("#");
			writer.writeNext(contents1);

			String content2= "";
			content2 =  "500" + "#" + fiveHundredsPcsField.getText() + "#" + fiveHundredsAmountLbl.getText() + "#" + " " + "#" + "Total" + "#" +  damagesTotalLbl.getText() + "#" + "Drivers Payment" + "#" +  damagesPaymentsLbl.getText();
			String [] contents2 = content2.split("#");
			writer.writeNext(contents2);

			String content3= "";
			content3 =  "200" + "#" + twoHundredsPcsField.getText() + "#" + twoHundredsAmountLbl.getText() + "#" + " " + "#" + "Shortages Gen. Total" + "#" + damagesGenTotalLbl.getText();
			String [] contents3 = content3.split("#");
			writer.writeNext(contents3);

			String content4= "";
			content4 =  "100" + "#" + hundredsPcsField.getText() + "#" + hundredsAmountLbl.getText()  + "#" + " " + "#" + " " +  "#" + " " + "#" +  " " + "#" +  " " + "#" + " " + "#" + "Nos. of units operating" + "#" + nOUPLbl.getText();
			String [] contents4 = content4.split("#");
			writer.writeNext(contents4);

			String content5 = "";
			content5 =  "50" + "#" + fiftiesPcsField.getText() + "#" + fiftyAmountLbl.getText()  + "#" + " " + "#" + " " +  "#" + " " + "#" + "General Shortages" + "#" +  " " + "#" + " " + "#" + "Total gross income" + "#" + tOILbl.getText();
			String [] contents5 = content5.split("#");
			writer.writeNext(contents5);

			String content6= "";
			content6 =  "20" + "#" + twentiesPcsField.getText() + "#" + twentyAmountLbl.getText() + "#" + " " + "#" + "Current Shortages" + "#" + shortagesBalanceLbl.getText() +"#" + "Addons" +  "#" + shortagesAddonsLbl.getText()  + "#" +  " " + "#" + "Expenses" + "#" + expensesField.getText();
			String [] contents6 = content6.split("#");
			writer.writeNext(contents6);

			String content7= "";
			content7 =  "10" + "#" + tensPcsField.getText() + "#" + tenAmountLbl.getText() + "#" + " " + "#" + "Total" + "#" +  shortagesTotalLbl.getText() + "#" + "Drivers Payment" + "#" +  shortagesPaymentsLbl.getText()  + "#" +  " " + "#" + "Allowanced" + "#" + allowancedField.getText();
			String [] contents7 = content7.split("#");
			writer.writeNext(contents7);

			String content8 = "";
			content8 =  "5" + "#" + fivesPcsField.getText() + "#" + fiveAmountLbl.getText() + "#" + " " + "#" + "Shortages Gen. Total" + "#" + shortagesGenTotalLbl.getText() +  "#" + " " + "#" +  " " + "#" +  " " + "#" + "Rebates" + "#" + rebatesField.getText();
			String [] contents8 = content8.split("#");
			writer.writeNext(contents8);

			String content9= "";
			content9 =  "1" + "#" + pesosPcsField.getText() + "#" + oneAmountLbl.getText() + "#" + " " + "#" + " " +  "#" + " " + "#" +  " " + "#" +  " " + "#" + " " + "#" + "Cash out" + "#" + cashOutField.getText();
			String [] contents9 = content9.split("#");
			writer.writeNext(contents9);

			String content10= "";
			content10 =  "0.50" + "#" + fiftyCentsPcsField.getText() + "#" + fiftyCentsAmountLbl.getText() + "#" + " " + "#" + " " +  "#" + " " + "#" +  " " + "#" +  " " + "#" + " " + "#" + "Net total" + "#" + netTotalLbl.getText();
			String [] contents10 = content10.split("#");
			writer.writeNext(contents10);

			String content11= "";
			content11 =  "0.25" + "#" + twentyFiveCentsPcsField.getText() + "#" + twentyFiveCentsAmountLbl.getText()  + "#" + " " + "#" + " " +  "#" + " " + "#" + "General Cashbonds";
			String [] contents11 = content11.split("#");
			writer.writeNext(contents11);

			String content12 = "";
			content12 =  "0.10" + "#" + tenCentsPcsField.getText() + "#" + tenCentsAmountLbl.getText() + "#" + " " + "#" + "Current Cashbonds" + "#" + cashbondsBalanceLbl.getText() +"#" + "Addons" +  "#" + cashbondsAddonsLbl.getText();
			String [] contents12 = content12.split("#");
			writer.writeNext(contents12);

			String content13= "";
			content13 =  "0.05" + "#" + fiveCentsPcsField.getText() + "#" + fiveCentsAmountLbl.getText() + "#" + " " + "#" + "Total" + "#" + cashbondsTotalLbl.getText() + "#" + "Drivers Payment" + "#" + cashbondsPaymentsLbl.getText();
			String [] contents13 = content13.split("#");
			writer.writeNext(contents13);

			String content14= "";
			content14 =  " " + "#" + "Total"+ "#" + totalBillLbl.getText() + "#" + " " + "#" + "Cashbonds Gen. Total" + "#" + cashbondsGenTotalLbl.getText();
			String [] contents14 = content14.split("#");
			writer.writeNext(contents14);

			writer.close();
			writer.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Form.dialog(AlertType.INFORMATION, "Info", null, "CasheirReport.csv is open. Please close!");
		} finally {
			openFile(file);
		}

	}

	/*
	 * Opening file
	 */
	private void openFile(File file) {
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(file);
		} catch (IOException ex) {
			Logger.getLogger(AdminsController.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/************************************************************************
	 * Database update, delete, insert
	 * *********************************************************************
	 * Called when the user hit add payments button
	 */
	@FXML
	public void showPayments() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {
		Payments tempPayments = new Payments();
		boolean okClicked = main.showPaymentsDialog(tempPayments, "PaymentsDialog.fxml", false);
		this.ok = okClicked;
		if (okClicked) {
			this.getpaymentsData().add(tempPayments);

			DataManipulator dataman = new DataManipulator();

			dataman.insertPaymentsData(Constants.DB_PAYMENTS_TBL,
					tempPayments.getDriver(), tempPayments.getVehicle(),
					tempPayments.getCarRate(), tempPayments.getAdminUser(),
					tempPayments.getBoundaries(),
					tempPayments.getCashbondPayments(),
					tempPayments.getShortagePayments(),
					tempPayments.getDamagePayments(), tempPayments.getLoans(),
					tempPayments.getAppSaving(), tempPayments.getShortages(),
					tempPayments.getLessHoliday(), tempPayments.getLessSunday(),
					tempPayments.getTimeRepairStart(),
					tempPayments.getTimeRepairEnd(),
					tempPayments.getTotalTimeRepair(),
					tempPayments.getRepairCost(),
					tempPayments.getGrabTaxi(),
					tempPayments.getKilometersRun(),
					tempPayments.getGasLiters(), tempPayments.getRemarks(),
					tempPayments.getTotal(), tempPayments.getDate());

		}

		updateTotalBalance(tempPayments);
	}

	private void updateTotalBalance(Payments payments) {
		try {
			DataManipulator dataman = new DataManipulator();
			ResultSet rs = dataman.generalQuery("SELECT total_cashbonds, total_damages, "
										+ "total_loans, total_participations, "
										+ "total_penalties, total_shortages, "
										+ "total_apprehension_saving "
										+ "FROM total_balance "
										+ "WHERE driver_id = " + getDriverId(payments));
			double tCashB = 0;
			double tDamages = 0;
			double tLoans = 0;
			double tPenalties = 0;
			double tParticipations = 0;
			double tShortages = 0;
			double tAppreS = 0;
			if(rs.next()) {
				tCashB = rs.getDouble("total_cashbonds");
				tDamages = rs.getDouble("total_damages");
				tLoans = rs.getDouble("total_loans");
				tParticipations = rs.getDouble("total_participations");
				tPenalties = rs.getDouble("total_penalties");
				tShortages = rs.getDouble("total_shortages");
				tAppreS = rs.getDouble("total_apprehension_saving");
			}
			// Insert new total balance
			dataman.updateTotalBalance(getDriverId(payments),
					(tCashB + payments.getCashbondPayments()),
					(tDamages - payments.getDamagePayments()),
					(tLoans - payments.getLoans()),
					(tParticipations - 0.0),
					(tPenalties - payments.getAppSaving()),
					(tShortages - payments.getShortagePayments()),
					(tAppreS - 0.0));


		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void computeTotalIncome()  {
		try {

			DataManipulator dataman = new DataManipulator();
			ResultSet rs = null;
			String query = "";
			query = "SELECT id FROM dispatch WHERE date = \"" + DateUtil.newDateNormal() + "\"";
			int count = 0;
			rs = dataman.generalQuery(query);
			while(rs.next()) {
				count++;
			}
			nOUPLbl.setText(Integer.toString(count));

			query = "SELECT SUM(total_payments) AS total_payments FROM payments WHERE date = \"" + DateUtil.newDateNormal() + "\"";
			rs = dataman.generalQuery(query);

			while(rs.next()) {
				tOILbl.setText(rs.getString("total_payments"));
			}

			System.out.println(tOILbl.getText());
			if(tOILbl.getText() != null) {

				double totalExpenses = Double.parseDouble(expensesField.getText()) + Double.parseDouble(allowancedField.getText()) + Double.parseDouble(cashOutField.getText());
				double netTotal = Double.parseDouble(tOILbl.getText()) - totalExpenses;

				netTotalLbl.setText(Double.toString(netTotal));
			} else {
				System.out.println("");
			}

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 *
	 * @throws SQLException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void handleDeletePayments() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {

		Payments selectedPayments = table.getSelectionModel().getSelectedItem();
		int selectedIndex = table.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {

			//table.getItems().remove(selectedIndex);

			DataManipulator dataman = new DataManipulator();
			dataman.deleteData(Constants.DB_PAYMENTS_TBL, "",
					selectedPayments.getId());
			 refreshTable();
		} else {
			Form.dialog(AlertType.INFORMATION, "Information", null, "Please select payment row to delete");
		}
	}



	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditPayments() {

		Payments selectedpayments = table.getSelectionModel().getSelectedItem();

		if (selectedpayments != null) {
			if(selectedpayments.getDriver().trim().equals("General Total")) {
		        	Form.dialog(AlertType.INFORMATION, "Information Message", null, "Can't edit General Total");

		    } else {
				boolean okClicked = main.showPaymentsDialog(selectedpayments,"PaymentsDialog.fxml", true);
				if (okClicked) {
					DataManipulator dataman;
					try {
						dataman = new DataManipulator();

						dataman.updatePaymentsData(Constants.DB_PAYMENTS_TBL, selectedpayments);

					} catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					refreshTable();

				}
		    }

		} else {
			// Nothing selected.
			Form.dialog(AlertType.INFORMATION, "Information", null, "Please select payment row to edit.");
		}
	}

	// /////////////////////////////////////////////////////////////
	// ///////////////// REPORTS TABLE /////////////////////////////
	// /////////////////////////////////////////////////////////////
	@FXML
	private void createReportsTable() {
		reportDateLabel.setText(DateUtil.newDateNormal());

		DataManipulator dataman;
		try {

			dataman = new DataManipulator();
			ResultSet rs = dataman
					.generalQuery("SELECT * FROM casheir_report WHERE date = \""
							+ DateUtil.newDateNormal() + "\"");

			if (rs.next()) {

				thousandsPcsField.setText(rs.getString("thousands"));
				fiveHundredsPcsField.setText(rs.getString("five_hundreds"));
				twoHundredsPcsField.setText(rs.getString("two_hundreds"));
				hundredsPcsField.setText(rs.getString("hundreds"));
				fiftiesPcsField.setText(rs.getString("fifties"));
				twentiesPcsField.setText(rs.getString("twenties"));
				tensPcsField.setText(rs.getString("tens"));
				fivesPcsField.setText(rs.getString("fives"));
				pesosPcsField.setText(rs.getString("pesos"));
				fiftyCentsPcsField.setText(rs.getString("fifty_centavos"));
				twentyFiveCentsPcsField.setText(rs
						.getString("twentyfive_centavos"));
				tenCentsPcsField.setText(rs.getString("ten_centavos"));
				fiveCentsPcsField.setText(rs.getString("five_centavos"));

			} else {
				String query = "INSERT INTO casheir_report "
						+ "(thousands, five_hundreds, two_hundreds, hundreds, fifties, "
						+ "twenties, tens, fives, pesos, fifty_centavos, twentyfive_centavos, "
						+ "ten_centavos, five_centavos, date)"
						+ "VALUES (0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, \""
						+ DateUtil.newDateNormal() + "\")";

				if (dataman.generalUpdate(query)) {
					rs = dataman
							.generalQuery("SELECT * FROM casheir_report WHERE date = \""
									+ DateUtil.newDateNormal() + "\"");

					if (rs.next()) {
						thousandsPcsField.setText(rs.getString("thousands"));
						fiveHundredsPcsField.setText(rs
								.getString("five_hundreds"));
						twoHundredsPcsField.setText(rs
								.getString("two_hundreds"));
						hundredsPcsField.setText(rs.getString("hundreds"));
						fiftiesPcsField.setText(rs.getString("fifties"));
						twentiesPcsField.setText(rs.getString("twenties"));
						tensPcsField.setText(rs.getString("tens"));
						fivesPcsField.setText(rs.getString("fives"));
						pesosPcsField.setText(rs.getString("pesos"));
						fiftyCentsPcsField.setText(rs
								.getString("fifty_centavos"));
						twentyFiveCentsPcsField.setText(rs
								.getString("twentyfive_centavos"));
						tenCentsPcsField.setText(rs.getString("ten_centavos"));
						fiveCentsPcsField
								.setText(rs.getString("five_centavos"));
					}
				}
			}

			thousandsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					1000, Double.parseDouble(thousandsPcsField.getText()))));
			fiveHundredsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					500, Double.parseDouble(fiveHundredsPcsField.getText()))));
			twoHundredsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					200, Double.parseDouble(twoHundredsPcsField.getText()))));
			hundredsAmountLbl.setText(Double.toString(multiplyBillsPieces(100,
					Double.parseDouble(hundredsPcsField.getText()))));
			fiftyAmountLbl.setText(Double.toString(multiplyBillsPieces(50,
					Double.parseDouble(fiftiesPcsField.getText()))));
			twentyAmountLbl.setText(Double.toString(multiplyBillsPieces(20,
					Double.parseDouble(twentiesPcsField.getText()))));
			tenAmountLbl.setText(Double.toString(multiplyBillsPieces(10,
					Double.parseDouble(tensPcsField.getText()))));
			fiveAmountLbl.setText(Double.toString(multiplyBillsPieces(5,
					Double.parseDouble(fivesPcsField.getText()))));
			oneAmountLbl.setText(Double.toString(multiplyBillsPieces(1,
					Double.parseDouble(pesosPcsField.getText()))));
			fiftyCentsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					0.50, Double.parseDouble(fiftiesPcsField.getText()))));
			twentyFiveCentsAmountLbl.setText(Double
					.toString(multiplyBillsPieces(0.25, Double
							.parseDouble(twentyFiveCentsPcsField.getText()))));
			tenCentsAmountLbl.setText(Double.toString(multiplyBillsPieces(0.10,
					Double.parseDouble(tenCentsPcsField.getText()))));
			fiveCentsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					0.05, Double.parseDouble(fiveCentsPcsField.getText()))));

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getReportBalance();
		getTotalBill();

		computeTotalIncome();

		initializeBillsField();
		initializeButtons();
		initializeTotalIncomeFields();
	}

	private void initializeTotalIncomeFields() {
		expensesField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							computeTotalIncome();
						}
					}
				});
		allowancedField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							computeTotalIncome();
						}
					}
				});
		rebatesField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							computeTotalIncome();
						}
					}
				});
		cashOutField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							computeTotalIncome();
						}
					}
				});
	}

	private void getReportBalance() {
		DataManipulator dataman;
		try {
			// Query and total add-ons balances today
			dataman = new DataManipulator();
			ResultSet rs = dataman.generalQuery("SELECT SUM(damages) AS damages_addons, "
													+ "SUM(shortages) AS shortages_addons,  "
													+ "SUM(cashbonds) AS cashbonds_addons "
													+ "FROM balance "
													+ "WHERE date = \"" + DateUtil.newDateNormal() + "\"");

			while(rs.next()) {
				damagesAddonsLbl.setText(Double.toString(Rounding.round(rs.getDouble("damages_addons"), 2)));
				shortagesAddonsLbl.setText(Double.toString(Rounding.round(rs.getDouble("shortages_addons"),2)));
				cashbondsAddonsLbl.setText(Double.toString(Rounding.round(rs.getDouble("cashbonds_addons"),2)));
			}

			// Query and total payments today
			rs = dataman.generalQuery("SELECT SUM(damage_payments) AS damages_payments, "
								+ "SUM(shortage_payments) AS shortages_payments,  "
								+ "SUM(cashbond_payments) AS cashbonds_payments "
								+ "FROM payments "
								+ "WHERE date = \"" + DateUtil.newDateNormal() + "\"");
			while(rs.next()) {
				damagesPaymentsLbl.setText(Double.toString(Rounding.round(rs.getDouble("damages_payments"),2)));
				shortagesPaymentsLbl.setText(Double.toString(Rounding.round(rs.getDouble("shortages_payments"),2)));
				cashbondsPaymentsLbl.setText(Double.toString(Rounding.round(rs.getDouble("cashbonds_payments"),2)));
			}

			// Query and total all the balances except today
			rs = dataman.generalQuery("SELECT SUM(damages) AS damages, SUM(shortages) AS shortages,  SUM(cashbonds) AS cashbonds "
					+ "FROM balance WHERE date <> \"" + DateUtil.newDateNormal() + "\"");

			while (rs.next()) {
				damagesBalanceLbl.setText(Double.toString(Rounding.round(rs.getDouble("damages"),2)));
				shortagesBalanceLbl.setText(Double.toString(Rounding.round(rs.getDouble("shortages"),2)));
				cashbondsBalanceLbl.setText(Double.toString(Rounding.round(rs.getDouble("cashbonds"),2)));
			}

			// Partial Balances Total
			double damagesTotal = Double.parseDouble(damagesBalanceLbl.getText()) + Double.parseDouble(damagesAddonsLbl.getText());
			double shortagesTotal = Double.parseDouble(shortagesBalanceLbl.getText()) + Double.parseDouble(shortagesAddonsLbl.getText());
			double cashbondsTotal = Double.parseDouble(cashbondsBalanceLbl.getText()) + Double.parseDouble(cashbondsAddonsLbl.getText());
			damagesTotalLbl.setText(Double.toString(Rounding.round(damagesTotal,2)));
			shortagesTotalLbl.setText(Double.toString(Rounding.round(shortagesTotal,2)));
			cashbondsTotalLbl.setText(Double.toString(Rounding.round(cashbondsTotal,2)));

			// Final Balances Total
			double damagesGenTotal = damagesTotal - Double.parseDouble(damagesPaymentsLbl.getText());
			double shortagesGenTotal = shortagesTotal - Double.parseDouble(shortagesPaymentsLbl.getText());
			double cashbondsGenTotal = cashbondsTotal - Double.parseDouble(cashbondsPaymentsLbl.getText());
			damagesGenTotalLbl.setText(Double.toString(Rounding.round(damagesGenTotal,2)));
			shortagesGenTotalLbl.setText(Double.toString(Rounding.round(shortagesGenTotal,2)));
			cashbondsGenTotalLbl.setText(Double.toString(Rounding.round(cashbondsGenTotal,2)));

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initializeButtons() {

		thousandsAdd.setOnAction((event) -> {
			addOneBill(thousandsPcsField);
			thousandsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					1000, Double.parseDouble(thousandsPcsField.getText()))));
			getTotalBill();
		});
		fiveHundredsAdd.setOnAction((event) -> {
			addOneBill(fiveHundredsPcsField);
			fiveHundredsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					500, Double.parseDouble(fiveHundredsPcsField.getText()))));
			getTotalBill();
		});
		twoHundredsAdd.setOnAction((event) -> {
			addOneBill(twoHundredsPcsField);
			twoHundredsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					200, Double.parseDouble(twoHundredsPcsField.getText()))));
			getTotalBill();
		});
		hundredsAdd.setOnAction((event) -> {
			addOneBill(hundredsPcsField);
			hundredsAmountLbl.setText(Double.toString(multiplyBillsPieces(100,
					Double.parseDouble(hundredsPcsField.getText()))));
			getTotalBill();
		});
		fiftiesAdd.setOnAction((event) -> {
			addOneBill(fiftiesPcsField);
			fiftyAmountLbl.setText(Double.toString(multiplyBillsPieces(50,
					Double.parseDouble(fiftiesPcsField.getText()))));
			getTotalBill();
		});
		twentiesAdd.setOnAction((event) -> {
			addOneBill(twentiesPcsField);
			twentyAmountLbl.setText(Double.toString(multiplyBillsPieces(20,
					Double.parseDouble(twentiesPcsField.getText()))));
			getTotalBill();
		});
		tensAdd.setOnAction((event) -> {
			addOneBill(tensPcsField);
			tenAmountLbl.setText(Double.toString(multiplyBillsPieces(10,
					Double.parseDouble(tensPcsField.getText()))));
			getTotalBill();
		});
		fivesAdd.setOnAction((event) -> {
			addOneBill(fivesPcsField);
			fiveAmountLbl.setText(Double.toString(multiplyBillsPieces(5,
					Double.parseDouble(fivesPcsField.getText()))));
			getTotalBill();
		});
		pesosAdd.setOnAction((event) -> {
			addOneBill(pesosPcsField);
			oneAmountLbl.setText(Double.toString(multiplyBillsPieces(1,
					Double.parseDouble(pesosPcsField.getText()))));
			getTotalBill();
		});
		fiftyCentsAdd.setOnAction((event) -> {
			addOneBill(fiftyCentsPcsField);
			fiftyAmountLbl.setText(Double.toString(multiplyBillsPieces(0.50,
					Double.parseDouble(fiftyCentsPcsField.getText()))));
			getTotalBill();
		});
		twentyFiveCentsAdd.setOnAction((event) -> {
			addOneBill(twentyFiveCentsPcsField);
			twentyFiveCentsAmountLbl.setText(Double
					.toString(multiplyBillsPieces(0.25, Double
							.parseDouble(twentyFiveCentsPcsField.getText()))));
			getTotalBill();
		});
		tenCentsAdd.setOnAction((event) -> {
			addOneBill(tenCentsPcsField);
			tenCentsAmountLbl.setText(Double.toString(multiplyBillsPieces(0.10,
					Double.parseDouble(tenCentsPcsField.getText()))));
			getTotalBill();
		});
		fiveCentsAdd.setOnAction((event) -> {
			addOneBill(fiveCentsPcsField);
			fiveCentsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					0.05, Double.parseDouble(fiveCentsPcsField.getText()))));
			getTotalBill();
		});

		// Minus one every click of a button
		thousandsMinus.setOnAction((event) -> {
			minusOneBill(thousandsPcsField);
			thousandsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					1000, Double.parseDouble(thousandsPcsField.getText()))));
			getTotalBill();
		});
		fiveHundredsMinus.setOnAction((event) -> {
			minusOneBill(fiveHundredsPcsField);
			fiveHundredsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					500, Double.parseDouble(fiveHundredsPcsField.getText()))));
			getTotalBill();
		});
		twoHundredsMinus.setOnAction((event) -> {
			minusOneBill(twoHundredsPcsField);
			twoHundredsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					200, Double.parseDouble(twoHundredsPcsField.getText()))));
			getTotalBill();
		});
		hundredsMinus.setOnAction((event) -> {
			minusOneBill(hundredsPcsField);
			hundredsAmountLbl.setText(Double.toString(multiplyBillsPieces(100,
					Double.parseDouble(hundredsPcsField.getText()))));
			getTotalBill();
		});
		fiftiesMinus.setOnAction((event) -> {
			minusOneBill(fiftiesPcsField);
			fiftyAmountLbl.setText(Double.toString(multiplyBillsPieces(50,
					Double.parseDouble(fiftiesPcsField.getText()))));
			getTotalBill();
		});
		twentiesMinus.setOnAction((event) -> {
			minusOneBill(twentiesPcsField);
			twentyAmountLbl.setText(Double.toString(multiplyBillsPieces(20,
					Double.parseDouble(twentiesPcsField.getText()))));
			getTotalBill();
		});
		tensMinus.setOnAction((event) -> {
			minusOneBill(tensPcsField);
			tenAmountLbl.setText(Double.toString(multiplyBillsPieces(10,
					Double.parseDouble(tensPcsField.getText()))));
			getTotalBill();
		});
		fivesMinus.setOnAction((event) -> {
			minusOneBill(fivesPcsField);
			fiveAmountLbl.setText(Double.toString(multiplyBillsPieces(5,
					Double.parseDouble(fivesPcsField.getText()))));
			getTotalBill();
		});
		pesosMinus.setOnAction((event) -> {
			minusOneBill(pesosPcsField);
			oneAmountLbl.setText(Double.toString(multiplyBillsPieces(1,
					Double.parseDouble(pesosPcsField.getText()))));
			getTotalBill();
		});
		fiftyCentsMinus.setOnAction((event) -> {
			minusOneBill(fiftyCentsPcsField);
			fiftyAmountLbl.setText(Double.toString(multiplyBillsPieces(0.50,
					Double.parseDouble(fiftyCentsPcsField.getText()))));
			getTotalBill();
		});
		twentyFiveCentsMinus.setOnAction((event) -> {
			minusOneBill(twentyFiveCentsPcsField);
			twentyFiveCentsAmountLbl.setText(Double
					.toString(multiplyBillsPieces(0.25, Double
							.parseDouble(twentyFiveCentsPcsField.getText()))));
			getTotalBill();
		});
		tenCentsMinus.setOnAction((event) -> {
			minusOneBill(tenCentsPcsField);
			tenCentsAmountLbl.setText(Double.toString(multiplyBillsPieces(0.10,
					Double.parseDouble(tenCentsPcsField.getText()))));
			getTotalBill();
		});
		fiveCentsMinus.setOnAction((event) -> {
			minusOneBill(fiveCentsPcsField);
			fiveCentsAmountLbl.setText(Double.toString(multiplyBillsPieces(
					0.05, Double.parseDouble(fiveCentsPcsField.getText()))));
			getTotalBill();
		});
	}

	private void initializeBillsField() {
		thousandsPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							thousandsAmountLbl.setText(Double
									.toString(multiplyBillsPieces(1000, Double
											.parseDouble(thousandsPcsField
													.getText()))));
						}
					}
				});
		fiveHundredsPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							fiveHundredsAmountLbl.setText(Double
									.toString(multiplyBillsPieces(500, Double
											.parseDouble(fiveHundredsPcsField
													.getText()))));
						}
					}
				});
		twoHundredsPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							twoHundredsAmountLbl.setText(Double
									.toString(multiplyBillsPieces(200, Double
											.parseDouble(twoHundredsPcsField
													.getText()))));
						}
					}
				});
		hundredsPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							hundredsAmountLbl.setText(Double
									.toString(multiplyBillsPieces(100, Double
											.parseDouble(hundredsPcsField
													.getText()))));
						}
					}
				});
		fiftiesPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							fiftyAmountLbl.setText(Double
									.toString(multiplyBillsPieces(50, Double
											.parseDouble(fiftiesPcsField
													.getText()))));
						}
					}
				});
		twentiesPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							twentyAmountLbl.setText(Double
									.toString(multiplyBillsPieces(20, Double
											.parseDouble(twentiesPcsField
													.getText()))));
						}
					}
				});
		tensPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							tenAmountLbl.setText(Double
									.toString(multiplyBillsPieces(10,
											Double.parseDouble(tensPcsField
													.getText()))));
						}
					}
				});
		fivesPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							fiveAmountLbl.setText(Double
									.toString(multiplyBillsPieces(5, Double
											.parseDouble(fivesPcsField
													.getText()))));
						}
					}
				});
		pesosPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							oneAmountLbl.setText(Double
									.toString(multiplyBillsPieces(1, Double
											.parseDouble(pesosPcsField
													.getText()))));
						}
					}
				});
		fiftyCentsPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							fiftyCentsAmountLbl.setText(Double
									.toString(multiplyBillsPieces(0.50, Double
											.parseDouble(fiftyCentsPcsField
													.getText()))));
						}
					}
				});
		twentyFiveCentsPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							twentyFiveCentsAmountLbl.setText(Double.toString(multiplyBillsPieces(
									0.25,
									Double.parseDouble(twentyFiveCentsPcsField
											.getText()))));
						}
					}
				});
		tenCentsPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							tenCentsAmountLbl.setText(Double
									.toString(multiplyBillsPieces(0.10, Double
											.parseDouble(tenCentsPcsField
													.getText()))));
						}
					}
				});
		fiveCentsPcsField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							fiveCentsAmountLbl.setText(Double
									.toString(multiplyBillsPieces(0.05, Double
											.parseDouble(fiveCentsPcsField
													.getText()))));
						}
					}
				});

	}

	private double multiplyBillsPieces(double pesoBill, double pcs) {
		double amount = pesoBill * pcs;
		amount = Math.round(amount * 100);
		amount = amount / 100;
		return Rounding.round(amount,2);
	}

	private void addOneBill(TextField txt) {
		int pcs = Integer.parseInt(txt.getText());
		pcs += 1;
		txt.setText(Integer.toString(pcs));
	}

	private void minusOneBill(TextField txt) {
		int pcs = Integer.parseInt(txt.getText());
		pcs -= 1;
		txt.setText(Integer.toString(pcs));
	}

	private void getTotalBill() {
		double total = Double.parseDouble(thousandsAmountLbl.getText())
				+ Double.parseDouble(fiveHundredsAmountLbl.getText())
				+ Double.parseDouble(twoHundredsAmountLbl.getText())
				+ Double.parseDouble(hundredsAmountLbl.getText())
				+ Double.parseDouble(fiftyAmountLbl.getText())
				+ Double.parseDouble(twentyAmountLbl.getText())
				+ Double.parseDouble(tenAmountLbl.getText())
				+ Double.parseDouble(fiveAmountLbl.getText())
				+ Double.parseDouble(oneAmountLbl.getText())
				+ Double.parseDouble(fiftyCentsAmountLbl.getText())
				+ Double.parseDouble(twentyFiveCentsAmountLbl.getText())
				+ Double.parseDouble(tenCentsAmountLbl.getText())
				+ Double.parseDouble(fiveCentsAmountLbl.getText());

		//total = Math.round(total * 100);
		//total = total / 100;
		totalBillLbl.setText(Double.toString(Rounding.round(total,2)));
	}

	@FXML
	private void addBills(ActionEvent e) {

	}

	@FXML
	private void minusBills() {

	}

	@FXML
	private void createNewReport() {
		//
		// UPDATE OR INSERT NEW DATA
		//
		try {
			DataManipulator dataman = new DataManipulator();

			String query = "SELECT id FROM casheir_report WHERE date = \""
					+ DateUtil.newDateNormal() + "\"";
			ResultSet rs = dataman.generalQuery(query);

			if (rs.next()) {
				System.out.println("UPDATING");
				query = "UPDATE casheir_report SET " + "thousands			= "
						+ thousandsPcsField.getText() + ", "
						+ "five_hundreds		= " + fiveHundredsPcsField.getText()
						+ ", " + "two_hundreds			= "
						+ twoHundredsPcsField.getText() + ", "
						+ "hundreds				= " + hundredsPcsField.getText() + ", "
						+ "fifties				= " + fiftiesPcsField.getText() + ", "
						+ "twenties				= " + twentiesPcsField.getText() + ", "
						+ "tens					= " + tensPcsField.getText() + ", "
						+ "fives				= " + fivesPcsField.getText() + ", "
						+ "pesos				= " + pesosPcsField.getText() + ", "
						+ "fifty_centavos		= " + fiftyCentsPcsField.getText()
						+ ", " + "twentyfive_centavos	= "
						+ twentyFiveCentsPcsField.getText() + ", "
						+ "ten_centavos			= " + tenCentsPcsField.getText()
						+ ", " + "five_centavos 		= "
						+ fiveCentsPcsField.getText() + " " + "WHERE date = \""
						+ DateUtil.newDateNormal() + "\"";
			} else {
				System.out.println("INSERTING");
				query = "INSERT INTO casheir_report "
						+ "(thousands, five_hundreds, two_hundreds, hundreds, fifties, twenties, tens, "
						+ "fives, pesos, fifty_centavos, twentyfive_centavos, ten_centavos, five_centavos, date) "
						+ "VALUES ("
						+ thousandsPcsField.getText()
						+ ", "
						+ fiveHundredsPcsField.getText()
						+ ", "
						+ twoHundredsPcsField.getText()
						+ ", "
						+ hundredsPcsField.getText()
						+ ", "
						+ fiftiesPcsField.getText()
						+ ", "
						+ twentiesPcsField.getText()
						+ ", "
						+ tensPcsField.getText()
						+ ", "
						+ fivesPcsField.getText()
						+ ", "
						+ pesosPcsField.getText()
						+ ", "
						+ fiftyCentsPcsField.getText()
						+ ", "
						+ twentyFiveCentsPcsField.getText()
						+ ", "
						+ tenCentsPcsField.getText()
						+ ", "
						+ fiveCentsPcsField.getText() + " "
						+ "\"" + DateUtil.newDateNormal() + "\")";
			}

			if(dataman.generalUpdate(query))
			{
				writeCasheirReportCSV();
			}

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void handelEditReport() {

	}

	private String query() {
		return "SELECT "
				+ "payments.id, "
				+ "payments.driver_id, "
				+ "payments.vehicle_specifications_id, "
				+ "payments.vehicle_id, "
				+ "payments.admins_id, "
				+ "payments.boundaries, "
				+ "payments.cashbond_payments, "
				+ "payments.shortage_payments, "
				+ "payments.damage_payments, "
				+ "payments.loans, "
				+ "payments.penalties, "
				+ "payments.shortages, "
				+ "payments.less_sunday, "
				+ "payments.less_holiday, "
				+ "payments.time_repair_start, "
				+ "payments.time_repair_end, "
				+ "payments.total_time_repair, "
				+ "payments.repair_cost, "
				+ "payments.grab_taxi, "
				+ "payments.kilometers_run, "
				+ "payments.gas_liters, "
				+ "payments.remarks, "
				+ "payments.total_payments, "
				+ "payments.date, "
				+ "a.first_name AS admin_first_name, "
				+ "a.mid_name AS admin_mid_name, "
				+ "a.last_name AS admin_last_name, "
				+ "payments.date AS payments_date, "
				+ "d.first_name AS driver_first_name, "
				+ "d.mid_name AS driver_mid_name, "
				+ "d.last_name AS driver_last_name, "
				+ "d.less_rate AS driver_less_rate, "
				+ "v.plate_number AS vehicle_plate_number, "
				+ "v.unit_number AS unit_number, "
				+ "v.rate AS vehicle_car_rate "
				+ "FROM payments "
				+ "INNER JOIN admins AS a ON payments.admins_id = a.id "
				+ "INNER JOIN drivers AS d ON payments.driver_id = d.id "
				+ "INNER JOIN vehicles AS v ON payments.vehicle_id = v.id  "
				+ "WHERE date = \"" + dateField.getValue() + "\" "
				+ "GROUP BY payments.id "

				+ "UNION ALL "

				+ "SELECT "
				+ "'' AS id, "
				+ "'' AS driver_id, "
				+ "'' AS vehicle_specifications_id, "
				+ "'' AS vehicle_id, "
				+ "'' AS admins_id, "
				+ "SUM(payments.boundaries), "
				+ "SUM(payments.cashbond_payments), "
				+ "SUM(payments.shortage_payments), "
				+ "SUM(payments.damage_payments), "
				+ "SUM(payments.loans), "
				+ "SUM(payments.penalties), "
				+ "SUM(payments.shortages), "
				+ "SUM(payments.less_sunday), "
				+ "SUM(payments.less_holiday), "
				+ "null AS t_repair_start, "
				+ "null AS t_repair_end, "
				+ "SUM(payments.total_time_repair), "
				+ "SUM(payments.repair_cost), "
				+ "SUM(payments.grab_taxi), "
				+ "SUM(payments.kilometers_run), "
				+ "SUM(payments.gas_liters), "
				+ "'' AS remarks, "
				+ "SUM(payments.total_payments), "
				+ "'' AS dispatch_id, "
				+ "'' AS admin_first_name, "
				+ "'' AS admin_mid_name, "
				+ "'' AS admin_last_name, "
				+ "'' AS payments_date, "
				+ "'General Total' AS driver_first_name, "
				+ "'' AS driver_mid_name, "
				+ "'' AS driver_last_name, "
				+ "SUM(d.less_rate) AS driver_less_rate, "
				+ "'' AS vehicle_plate_number, "
				+ "null AS unit_number, "
				+ "SUM(v.rate) "
				+ "FROM payments "
				+ "INNER JOIN admins AS a ON payments.admins_id = a.id "
				+ "INNER JOIN drivers AS d ON payments.driver_id = d.id "
				+ "INNER JOIN vehicles AS v ON payments.vehicle_id = v.id "
				+ "WHERE date = \"" + dateField.getValue() + "\"";

	}

}
