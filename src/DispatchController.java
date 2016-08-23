
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

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Dispatch;
import model.Drivers;
import model.Payments;
import util.DateUtil;
import util.Form;
import util.Name;

import com.opencsv.CSVWriter;

public class DispatchController implements Initializable {

	/*
	 * TODO: 1). Add admin user column 2). Set Unconstrained table
	 */
	private final String CSV_FILE_NAME = "C:\\Dispatch.csv";

	private Main main = new Main();

	private ObservableList<Dispatch> data = FXCollections.observableArrayList();

	@FXML
	Button newDispatchBtn = new Button();
	@FXML
	Button receivePaymentsBtn = new Button();
	@FXML
	Button editDispatchBtn = new Button();
	@FXML
	Button deleteDispatchBtn = new Button();

	@FXML
	ProgressIndicator progInd = new ProgressIndicator();

	@FXML
	TextField searchField = new TextField();
	@FXML
	DatePicker dateField = new DatePicker();
	@FXML
	Label dateLabel = new Label();
	@FXML
	Label dodLabel = new Label();
	@FXML
	Label adLabel = new Label();
	
	@FXML
	public TableView<Dispatch> table = new TableView<Dispatch>();

	private String timeCheck;

	private Stage dialogStage;

	TableColumn <Dispatch, String> driverCol;
	TableColumn <Dispatch, String> unitNumberCol;
	TableColumn <Dispatch, String> vehicle;
	TableColumn <Dispatch, String> adminCol;
	TableColumn <Dispatch, String> timeOut;
	TableColumn <Dispatch, String> carBrandCol;
	TableColumn <Dispatch, String> boundaryRateCol;
	TableColumn <Dispatch, String> contactNumberCol;
	TableColumn <Dispatch, String> statusCol;
	TableColumn <Dispatch, String> dateCol;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Let's disable the buttons if not login as Admin

		progInd.setOpacity(0.0);
		dateField.setValue(DateUtil.parse(DateUtil.newDateNormal()));
		
		if (Constants.USERTYPE.equals("dispatcher")) {
			receivePaymentsBtn.setDisable(true);
			editDispatchBtn.setDisable(true);

		}
		if (Constants.USERTYPE.equals("cashier")) {
			newDispatchBtn.setDisable(true);
			editDispatchBtn.setDisable(true);
			deleteDispatchBtn.setDisable(true);
		}
		
		this.createTableColumn();
		createObsData(); 
		
		// Initialize absent and on duty drivers
		this.setDriverOnDuty();
		this.setAbsentDriver();

		filteredData();
	}

	
	@SuppressWarnings({"unchecked" })
	@FXML
	private void createTableColumn() {

		driverCol = new TableColumn<Dispatch, String>("Driver");
		driverCol.setMinWidth(100);
		driverCol.setCellValueFactory(new PropertyValueFactory<>("driver"));

		unitNumberCol = new TableColumn<Dispatch, String>("Unit Number");
		unitNumberCol.setMinWidth(100);
		unitNumberCol.setCellValueFactory(new PropertyValueFactory<>("unitNumber"));
		
		vehicle = new TableColumn<Dispatch, String>("Vehicle");
		vehicle.setMinWidth(100);
		vehicle.setCellValueFactory(new PropertyValueFactory<>("vehicle"));
		
		adminCol = new TableColumn<Dispatch, String>("Dispatcher");
		adminCol.setMinWidth(100);
		adminCol.setCellValueFactory(new PropertyValueFactory<>("admin"));
		
		timeOut = new TableColumn<Dispatch, String>("Time Out");
		timeOut.setMinWidth(100);
		timeOut.setCellValueFactory(new PropertyValueFactory<>("timeOut"));
		timeOut.setCellFactory(column -> {
			return new TableCell<Dispatch, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					// clear any custom styles
					this.getTableRow().getStyleClass().remove("late");
					this.getTableRow().getStyleClass().remove("warning");
					// update the item and set a custom style if necessary
					if (empty) {
						setText(null);
						setGraphic(null);
					} else {
						if (item != null) {
							setText(item.toString());
							try {
								timeCheck = TimeChecker.checkLate(DateUtil.reformatStringDT(this.getText()), DateUtil.newDateTime());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

				}
			};
		});

		carBrandCol = new TableColumn<Dispatch, String>("Car Brand");
		carBrandCol.setMinWidth(100);
		carBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));

		boundaryRateCol = new TableColumn<Dispatch, String>(
				"Car Rate");
		boundaryRateCol.setMinWidth(100);
		boundaryRateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));

		contactNumberCol = new TableColumn<Dispatch, String>(
				"Contact Number");
		contactNumberCol.setMinWidth(100);
		contactNumberCol.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
		
		statusCol = new TableColumn<Dispatch, String>("Status");
		statusCol.setMinWidth(100);
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

		// Custom rendering of the table cell.
		statusCol.setCellFactory(column -> {
			return new TableCell<Dispatch, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// clear any custom styles
					this.getTableRow().getStyleClass().remove("expired");
					this.getTableRow().getStyleClass().remove("warning");
					this.getTableRow().getStyleClass().remove("on-dispatch");
					this.getTableRow().getStyleClass().remove("off-dispatch");
					// update the item and set a custom style if necessary
					if (empty) {
						setText(null);
						setGraphic(null);
					} else {
						// update the item and set a custom style if necessary
						if (item != null) {
							setText(item.toString());
							// System.out.println("Late? " + timeCheck);
							if (item.equals("on dispatch")) {
								if (timeCheck.equals("late")) {
									this.getTableRow().getStyleClass()
											.add("late");
								} else if (timeCheck.equals("warning")) {
									this.getTableRow().getStyleClass()
											.add("warning");
								} else {
									this.getTableRow().getStyleClass()
											.add("on-dispatch");
								}

							} else {
								this.getTableRow().getStyleClass()
										.add("off-dispatch");
							}
						}
					}

				}
			};
		});

		dateCol = new TableColumn<Dispatch, String>("Date");
		dateCol.setMinWidth(100);
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		// Add filtered data to the table
		table.setItems(data);

		table.getColumns().addAll(driverCol, unitNumberCol, vehicle, adminCol, timeOut,
				carBrandCol, boundaryRateCol, contactNumberCol, statusCol, dateCol);

	}

	private void createObsData() {
		try {
			DataManipulator dataman = new DataManipulator();

			// Create our TableModel

			ResultSet rs = dataman.generalQuery(this.query());
			
			while (rs.next()) {
				String driverName = Name.createFullName(rs.getString("d.first_name"), rs.getString("d.mid_name"), rs.getString("d.last_name"));
				String adminName = Name.createFullName(rs.getString("a.first_name"), rs.getString("a.mid_name"), rs.getString("a.last_name"));

				// Che0ck if time out is null
				String timeOut = "";
				if (rs.getString(5) == null) {
					timeOut = null;
				} else {
					timeOut = DateUtil.formatStringDT(rs.getString(5));
				}


				// TODO collected should be inner join
				data.add(new Dispatch(rs.getInt(1), 
						driverName, 
						rs.getInt("v.unit_number"), 
						rs.getString("v.plate_number"), 
						adminName, 
						timeOut, 
						rs.getString("v.brand"), 
						rs.getDouble("v.rate"), 
						rs.getString("d.contact_number"), 
						rs.getString(7), 
						DateUtil.formatDate(rs.getString(8))));

			}
			
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
    @FXML
    private void refresh() {

    	data.removeAll(data);
    	
		//createTableColumn();
		createObsData(); 
		setAbsentDriver();
	    setDriverOnDuty();
    }
    
	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {

		this.dialogStage = dialogStage;
	}

	private ObservableList<Dispatch> getDispatchData() {

		return this.data;
	}
	
	
	

	
	

	/*
	 * 
	 * Initialize methods
	 * 
	 */

	/*
	 * Initialize methods
	 */

	// Filtering method
	private void filteredData() {
		// 1. Wrap the ObservableList in a FilteredList (initially display all
		// data).
		FilteredList<Dispatch> filteredData = new FilteredList<>(data,
				d -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		searchField.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					filteredData.setPredicate(dispatch -> {
						// If filter text is empty, display all Dispatchs.
							if (newValue == null || newValue.isEmpty()) {
								return true;
							}

							// Compare first name and last name of every
							// Dispatch with filter text.
							String lowerCaseFilter = newValue.toLowerCase();

							if (dispatch.getDriver().toLowerCase()
									.contains(lowerCaseFilter)) {
								return true; // Filter matches driver name.
							} else if (dispatch.getVehicle().toLowerCase()
									.contains(lowerCaseFilter)) {
								return true; // Filter matches vehicle
												// plate_number.
							} else if (dispatch.getAdmin().toLowerCase()
									.contains(lowerCaseFilter)) {
								return true; // Filter matches vehicle
												// plate_number.
							} else if (dispatch.getDate().toLowerCase()
									.contains(lowerCaseFilter)) {
								return true; // Filter matches vehicle
												// plate_number.
							}
							return false; // Does not match.
						});
				});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Dispatch> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(table.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		table.setItems(sortedData);

	}

	
	
	
	
	
	
	
	@FXML
	public void writeCSV() {
	
		File file = new File(CSV_FILE_NAME);
		
		try {

	        
		    CSVWriter writer = new CSVWriter(new FileWriter(file));
		
		    DataManipulator dataman = new DataManipulator();
	    	 
			ResultSet rs = dataman.generalQuery(this.query());
			
			// Table Headers
			String[] headers = "Driver # Vehicle # Dispatcher # Time Out # Car Brand # Car Rate # Status # Date".split("#");
			writer.writeNext(headers);
			
			// Table Rows
			while (rs.next()) {
				String driverName = Name.createFullName(rs.getString("d.first_name"), rs.getString("d.mid_name"), rs.getString("d.last_name"));
				String adminName = Name.createFullName(rs.getString("a.first_name"), rs.getString("a.mid_name"), rs.getString("a.last_name"));

				// Check if time out is null
				String timeOut = "";
				if (rs.getString(5) == null) {
					timeOut = null;
				} else {
					timeOut = DateUtil.formatStringDT(rs.getString(5));
				}

				// TODO collected should be inner join
				String entry = driverName + "#" + rs.getString("v.plate_number") + "#" + adminName + "#" + 
							   timeOut + "#" + rs.getString("v.brand") + "#" + rs.getInt("v.rate") + "#" + rs.getString(7) + "#" +
							   DateUtil.formatDate(rs.getString(8));
				String[] entries = entry.split("#");
				
				writer.writeNext(entries);

			}
			
			writer.flush();
		    writer.close();
		    
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | IOException | ParseException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String message = "Please close the Dispatch.csv file";
			Form.dialog(AlertType.INFORMATION, "Information Message",null, message);
		}  finally {
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
	            Logger.getLogger(
	               AdminsController.class.getName()).log(
	                    Level.SEVERE, null, ex
	                );
	        }
	}
	
	
	
	
	
	
	/*
	 * Initialize methods
	 */
	
	public void setDriverOnDuty() {

		try {

			DataManipulator dataman = new DataManipulator();
			ResultSet rs = dataman.generalQuery("SELECT drivers.id FROM drivers "
					+ "INNER JOIN dispatch AS d ON drivers.id = d.driver_id "
					+ "WHERE dispatch_status = 1 AND d.date = \"" + dateField.getValue() + "\"");
			int i = 0;
			while (rs.next()) {
				i++;
			}
			dodLabel.setText(Integer.toString(i));
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setAbsentDriver() {
		
		try {
			DataManipulator dataman = new DataManipulator();
			ResultSet rs = dataman.generalQuery("SELECT drivers.id FROM drivers "
					+ "WHERE dispatch_status = 0");

			int i = 0;
			while (rs.next()) {
				i++;
			}

			adLabel.setText(Integer.toString(i));
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void showPayments() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {
		
		PaymentsController paymentsController = new PaymentsController();
		paymentsController.showPayments();
		
		// Refresh table if ok is clicked on payments dialog
		this.refresh();
	}

	/************************************************************************
	 * Database update delete insert
	 * *********************************************************************
	 * Called when the user hit new dispatch
	 */
	@FXML
	public void createNewDispatch() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException,
			ParseException {
		Dispatch tempDispatch = new Dispatch();
		boolean okClicked = main.showEditDialog(tempDispatch,
				"DispatchEditDialog.fxml");
		if (okClicked) {

			this.getDispatchData().add(tempDispatch);

			DataManipulator dataman = new DataManipulator();
			dataman.insertDispatchData(progInd, Constants.DB_DISPATCH_TBL,
					tempDispatch.getDriver(), tempDispatch.getVehicle(),
					tempDispatch.getAdmin(),
					DateUtil.reformatStringDT(tempDispatch.getTimeOut()), tempDispatch.getRate(),
					tempDispatch.getStatus(),
					DateUtil.reformatDate(tempDispatch.getDate()));

		}
		
		refresh();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				progInd.setOpacity(0);
			}
		});
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteDispatch() {
		Dispatch selecterDispatch = table.getSelectionModel().getSelectedItem(); 
		int selectedIndex = table.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			
			String message = "Are you sure you want to add this new data?";
			boolean answer = Form.dialogOkNo(null, message);
			if (answer) {
				//table.getItems().remove(selectedIndex);
				DataManipulator dataman;
				try {
					dataman = new DataManipulator();
					dataman.deleteData(Constants.DB_DISPATCH_TBL,"", selecterDispatch.getId());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		} else {
			Form.dialog(AlertType.WARNING, "Error", null, "No Selected Driver");
		}
		refresh();
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected Dispatch.
	 */
	@FXML
	private void handleReceiveDispatch() {

		Dispatch selectedDispatch = table.getSelectionModel().getSelectedItem();

		if (selectedDispatch != null) {
			boolean okClicked = main.showEditDialog(selectedDispatch,
					"DispatchEditDialog.fxml");
			if (okClicked) {
				// Refresh the table
				table.getColumns().get(0).setVisible(false);
				table.getColumns().get(0).setVisible(true);
			}

		} else {
			// Nothing selected.
			System.out.print("WEW");

		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected Dispatch.
	 */
	@FXML
	private void handleEditDispatch() {

		Dispatch selectedDispatch = table.getSelectionModel().getSelectedItem();
		dateField.setEditable(true);
		if (selectedDispatch != null) {
			boolean okClicked = main.showEditDialog(selectedDispatch,
					"DispatchEditDialog.fxml");
			if (okClicked) {

				DataManipulator dataman;
				try {
					
					dataman = new DataManipulator();
					dataman.updateDispatchData(selectedDispatch);

					table.getColumns().get(0).setVisible(false);
					table.getColumns().get(0).setVisible(true);
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | SQLException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				this.setDriverOnDuty();
				this.setAbsentDriver();
			}

		} else {
			// Nothing selected.
			System.out.print("WEW");
		}
	}

	private String query()
	{
		String finalQuery = "";
		// Create our TableModel

		String[] columnNames = { Constants.DB_DISPATCH_TBL + ".*",
						"a.first_name", "a.mid_name", "a.last_name", "d.first_name", 
						"d.mid_name", "d.last_name", "d.contact_number","v.unit_number",  "v.plate_number", "v.rate",
						"v.brand" };

		String[] joins = { "admins AS a on dispatch.admin_id = a.id",
						"drivers AS d on dispatch.driver_id = d.id",
						"vehicles As v on dispatch.vehicle_id = v.id"};
				
				// Get columns
		String query = "";
				
		for(int i = 0; i < columnNames.length; i++)
		{
		    		
		    		
		    		if(i != (columnNames.length -1))
		    		{
		    			query += columnNames[i] + ", ";
		    		} else {
		    			query += columnNames[i];
		    		}
		    			
		}
		    	
		String join = "";
		
		for (int i = 0; i < joins.length; i++)
		{
		    join += "INNER JOIN " + joins[i] + " ";
		}
		
		finalQuery = "SELECT " + query + " FROM " + Constants.DB_DISPATCH_TBL + " " + join + " WHERE date = \"" + dateField.getValue() + "\" ORDER BY v.unit_number";
		System.out.println(finalQuery);
		return finalQuery;
	}
}
