
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Vehicles;
import util.DateUtil;
import util.Form;

import com.opencsv.CSVWriter;

public class VehiclesController implements Initializable {

	private Main main = new Main();

	private ObservableList<Vehicles> data = FXCollections.observableArrayList();

	@FXML
	Button newVehiclesBtn = new Button();
	@FXML
	Button editVehiclesBtn = new Button();
	@FXML
	Button deleteVehiclesBtn = new Button();
	@FXML
	TextField searchField = new TextField();

	@FXML
	private TableView<Vehicles> table = new TableView<Vehicles>();

	private static String timeCheck;
	TimeChecker tc = new TimeChecker();

	Vehicles vehicles;
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@FXML
	private void createTableColumn() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException,
			ParseException {
		DataManipulator dataman = new DataManipulator();

		// Create our TableModel
		/*
		String[] columnNames = { Constants.DB_VEHICLE_TBL + ".*",
				"vehicle_specifications.car_brand",
				"vehicle_specifications.car_rate" };

		String[] joins = { "vehicle_specifications on "
				+ Constants.DB_VEHICLE_TBL + ".id = vehicle_specifications.id" };
		*/
		// Lets create our TableModel
		String query = "SELECT * FROM " + Constants.DB_VEHICLE_TBL + "";
		ResultSet rs = dataman.generalQuery(query);
		
		table.setEditable(true);

		while (rs.next()) {
			data.add(new Vehicles(rs.getInt("id"), 
					rs.getInt("unit_number"),
					rs.getString("plate_number"), 
					rs.getString("case_number"),
					rs.getString("ccn"),
					rs.getString("chasis_number"),
					rs.getString("brand"), 
					rs.getInt("rate"),
					rs.getString("engine_number"),
					DateUtil.formatDate(rs.getString("plate_registration")),
					DateUtil.formatDate(rs.getString("reseal_date")), 
					DateUtil.formatDate(rs.getString("cpc_registration")),
					DateUtil.formatDate(rs.getString("eot_registration")),
					rs.getString("status")));
		}
		
		TableColumn unitNumberCol = new TableColumn<Vehicles, String>(
				"Unit Number");
		unitNumberCol.setMinWidth(100);
		unitNumberCol.setCellValueFactory(new PropertyValueFactory<>(
				"unitNumber"));

		TableColumn plateNumberCol = new TableColumn<Vehicles, String>(
				"Plate Number");
		plateNumberCol.setMinWidth(100);
		plateNumberCol.setCellValueFactory(new PropertyValueFactory<>(
				"plateNumber"));
		
		TableColumn caseNumberCol = new TableColumn<Vehicles, String>("Case Number");
		caseNumberCol.setMinWidth(100);
		caseNumberCol.setCellValueFactory(new PropertyValueFactory<>("caseNumber"));
		
		TableColumn ccnCol = new TableColumn<Vehicles, String>("CCN");
		ccnCol.setMinWidth(100);
		ccnCol.setCellValueFactory(new PropertyValueFactory<>("ccn"));
		
		TableColumn chasisNumberCol = new TableColumn<Vehicles, String>("Chasis Number");
		chasisNumberCol.setMinWidth(100);
		chasisNumberCol.setCellValueFactory(new PropertyValueFactory<>("chasisNumber"));

		TableColumn carBrandCol = new TableColumn<Vehicles, String>("Car Brand");
		carBrandCol.setMinWidth(100);
		carBrandCol.setCellValueFactory(new PropertyValueFactory<>("carBrand"));

		TableColumn carRateCol = new TableColumn<Vehicles, String>("Car Rate");
		carRateCol.setMinWidth(100);
		carRateCol.setCellValueFactory(new PropertyValueFactory<>("carRate"));

		TableColumn engineNumberCol = new TableColumn<Vehicles, String>("Engine Number");
		engineNumberCol.setMinWidth(100);
		engineNumberCol.setCellValueFactory(new PropertyValueFactory<>("engineNumber"));
		
		TableColumn regesteredDateCol = new TableColumn<Vehicles, String>("Plate Registration");
		regesteredDateCol.setMinWidth(100);
		regesteredDateCol.setCellValueFactory(new PropertyValueFactory<>(
				"plateRegistration"));
		// Custom rendering of the table cell.
		regesteredDateCol.setCellFactory(column -> {
			return new TableCell<Vehicles, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// clear any custom styles
					this.getStyleClass().remove("expired");
					this.getStyleClass().remove("warning");
					 if (empty) {
			                setText(null);
			                setGraphic(null);
			            } else {
			            	// update the item and set a custom style if necessary
							if (item != null) {
								setText(item.toString());
								try {
									timeCheck = TimeChecker.checkExpire(
											DateUtil.reformatDate(this.getText()),
											DateUtil.newDateNormal());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (timeCheck.equals("expired")) {
									this.getStyleClass().add("expired");
								}
								if (timeCheck.equals("warning")) {
									this.getStyleClass().add("warning");
								}

							}
			          }
					
				}
			};
		});

		TableColumn resealDateCol = new TableColumn<Vehicles, String>(
				"Reseal Date");
		resealDateCol.setMinWidth(100);
		resealDateCol.setCellValueFactory(new PropertyValueFactory<>(
				"resealDate"));
		// Custom rendering of the table cell.
		resealDateCol.setCellFactory(column -> {
			return new TableCell<Vehicles, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// clear any custom styles
					this.getStyleClass().remove("expired");
		            this.getStyleClass().remove("warning");
					// update the item and set a custom style if necessary
					 if (empty) {
			                setText(null);
			                setGraphic(null);
			            } else {
							if (item != null) {
								setText(item.toString());
								try {
									timeCheck = TimeChecker.checkExpire(
											DateUtil.reformatDate(this.getText()),
											DateUtil.newDateNormal());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (timeCheck.equals("expired")) {
									this.getStyleClass().add("expired");
								}
								if (timeCheck.equals("warning")) {
									this.getStyleClass().add("warning");
								}

							}
			            }
					
				}
			};
		});
		
		TableColumn cpcRegCol = new TableColumn<Vehicles, String>(
				"CPC Registration");
		cpcRegCol.setMinWidth(100);
		cpcRegCol.setCellValueFactory(new PropertyValueFactory<>(
				"cpcRegistration"));
		// Custom rendering of the table cell.
		cpcRegCol.setCellFactory(column -> {
			return new TableCell<Vehicles, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// clear any custom styles
					this.getStyleClass().remove("expired");
		            this.getStyleClass().remove("warning");
					// update the item and set a custom style if necessary
					 if (empty) {
			                setText(null);
			                setGraphic(null);
			            } else {
							if (item != null) {
								setText(item.toString());
								try {
									timeCheck = TimeChecker.checkExpire(
											DateUtil.reformatDate(this.getText()),
											DateUtil.newDateNormal());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (timeCheck.equals("expired")) {
									this.getStyleClass().add("expired");
								}
								if (timeCheck.equals("warning")) {
									this.getStyleClass().add("warning");
								}

							}
			            }
					
				}
			};
		});
		
		TableColumn eotRegCol = new TableColumn<Vehicles, String>(
				"EOT Registration");
		eotRegCol.setMinWidth(100);
		eotRegCol.setCellValueFactory(new PropertyValueFactory<>(
				"eotRegistration"));
		// Custom rendering of the table cell.
		eotRegCol.setCellFactory(column -> {
			return new TableCell<Vehicles, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// clear any custom styles
					this.getStyleClass().remove("expired");
		            this.getStyleClass().remove("warning");
					// update the item and set a custom style if necessary
					 if (empty) {
			                setText(null);
			                setGraphic(null);
			            } else {
							if (item != null) {
								setText(item.toString());
								try {
									timeCheck = TimeChecker.checkExpire(
											DateUtil.reformatDate(this.getText()),
											DateUtil.newDateNormal());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (timeCheck.equals("expired")) {
									this.getStyleClass().add("expired");
								}
								if (timeCheck.equals("warning")) {
									this.getStyleClass().add("warning");
								}

							}
			            }
					
				}
			};
		});

		TableColumn statusCol = new TableColumn<Vehicles, String>("Status");
		statusCol.setMinWidth(100);
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

		table.setItems(data);

		table.getColumns().addAll(unitNumberCol, plateNumberCol, caseNumberCol, ccnCol, chasisNumberCol, carBrandCol,
				carRateCol, engineNumberCol, regesteredDateCol, resealDateCol, cpcRegCol, eotRegCol, statusCol);

	}

	private ObservableList<Vehicles> getVehiclesData() {
		return this.data;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Let's disable the buttons if not login as Admin
		if (!Constants.USERTYPE.equals("admin")) {

			this.newVehiclesBtn.setDisable(true);
			this.editVehiclesBtn.setDisable(true);
			this.deleteVehiclesBtn.setDisable(true);

		}

		try {

			this.createTableColumn(); // Create table column

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		filteredData();
	}

	

	/*
	 * Initialize methods
	 */
	private void filteredData()
	{
		  // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Vehicles> filteredData = new FilteredList<>(data, v -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(vehicles -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (vehicles.getPlateNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches driver name.
                } else if (vehicles.getChasisNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches  vehicle plate_number.
                } else if (vehicles.getCarBrand().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches  vehicle plate_number.
                } 
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Vehicles> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
		
	}
	
	@FXML
	public void writeCSV() throws Exception {
	   CSVWriter writer = null;
	    try {
	        File file = new File("C:\\Vehicles.csv.");
	        writer = new CSVWriter(new FileWriter(file));
	        DataManipulator dataman = new DataManipulator();

			String[] columnNames = { Constants.DB_VEHICLE_TBL + ".*",
					"vehicle_specifications.car_brand",
					"vehicle_specifications.car_rate" };

			String[] joins = { "vehicle_specifications on "
					+ Constants.DB_VEHICLE_TBL + ".id = vehicle_specifications.id" };

			// Lets create our TableModel
			ResultSet rs = dataman.innerJoin(Constants.DB_VEHICLE_TBL, columnNames,
					joins);

			// Table Headers
			String[] headers = "Plate Number # Body Number # Car Brand # Car Rate # Plate Registration # Reseal Date # CPC Registration # EOT Registration # Status".split("#");
			writer.writeNext(headers);

			while (rs.next()) {
				
				String entry = 	rs.getString("plate_number") + "#" + 
								rs.getString("body_number") + "#" +  
								rs.getString("case_number")  + "#" + 
								rs.getString("car_brand") + "#" + 
								rs.getInt("car_rate")  + "#" + 
								DateUtil.formatDate(rs.getString("plate_registration"))  + "#" + 
								DateUtil.formatDate(rs.getString("reseal_date"))  + "#" +  
								DateUtil.formatDate(rs.getString("cpc_registration"))  + "#" + 
								DateUtil.formatDate(rs.getString("eot_registration"))  + "#" + 
								rs.getString("status");
				
				
				String[] entries = entry.split("#");
				writer.writeNext(entries);

			}
			
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    finally {

	        writer.flush();
	         writer.close();
	    } 
	}
	
	
	@FXML
	public void createNewVehicles() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException,
			ParseException {
		Vehicles tempVehicles = new Vehicles();
		boolean okClicked = main.showEditDialog(tempVehicles,
				"VehiclesEditDialog.fxml");

		if (okClicked) {
			this.getVehiclesData().add(tempVehicles);
			DataManipulator dataman = new DataManipulator();
			dataman.insertVehiclesData(Constants.DB_VEHICLE_TBL,
					tempVehicles.getId(),
					tempVehicles.getUnitNumber(),
					tempVehicles.getPlateNumber(),
					tempVehicles.getCaseNumber(), 
					tempVehicles.getCcn(), 
					tempVehicles.getChasisNumber(), 
					tempVehicles.getCarBrand(),
					tempVehicles.getCarRate(),
					tempVehicles.getEngineNumber(), 
					DateUtil.reformatDate(tempVehicles.getPlateRegistration()),
					DateUtil.reformatDate(tempVehicles.getResealDate()), 
					DateUtil.reformatDate(tempVehicles.getCpcRegistration()), 
					DateUtil.reformatDate(tempVehicles.getEotRegistration()), 
					tempVehicles.getStatus());
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
	private void handleDeleteVehicles() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {
		Vehicles selectedVehicles = table.getSelectionModel().getSelectedItem();
		int selectedIndex = table.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			table.getItems().remove(selectedIndex);
			DataManipulator dataman = new DataManipulator();
			dataman.deleteData(Constants.DB_VEHICLE_TBL, "",
					selectedVehicles.getId());
		} else {
			Form.dialog(AlertType.INFORMATION, "INFORMATION", null, "Please select row to delete.");
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 * 
	 * @throws SQLException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 */
	@FXML
	private void handleEditVehicles() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException,
			ParseException {

		Vehicles selectedVehicles = table.getSelectionModel().getSelectedItem();

		if (selectedVehicles != null) {
			boolean okClicked = main.showEditDialog(selectedVehicles,
					"VehiclesEditDialog.fxml");
			if (okClicked) {
				// Refresh the table
				table.getColumns().get(0).setVisible(false);
				table.getColumns().get(0).setVisible(true);

				DataManipulator dataman = new DataManipulator();
				dataman.updateVehiclesData(Constants.DB_VEHICLE_TBL,
						selectedVehicles.getId(),
						selectedVehicles.getUnitNumber(),
						selectedVehicles.getPlateNumber(),
						selectedVehicles.getCaseNumber(),
						selectedVehicles.getCcn(),
						selectedVehicles.getChasisNumber(),
						selectedVehicles.getCarBrand(),
						selectedVehicles.getCarRate(),
						selectedVehicles.getEngineNumber(),
						DateUtil.reformatDate(selectedVehicles.getPlateRegistration()),
						DateUtil.reformatDate(selectedVehicles.getResealDate()),
						DateUtil.reformatDate(selectedVehicles.getCpcRegistration()),
						DateUtil.reformatDate(selectedVehicles.getEotRegistration()),
						selectedVehicles.getStatus());
				;
			}

		} else {
			// Nothing selected.
			Form.dialog(AlertType.INFORMATION, "INFORMATION", null, "Please select row to edit.");
		}
	}

}