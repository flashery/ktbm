import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.DateUtil;
import util.Form;
import util.Name;

import com.opencsv.CSVWriter;

import model.Admins;
import model.Dispatch;
import model.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventoryController implements Initializable{
	
	private ObservableList<Inventory> data = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Inventory> dailyTable = new TableView<>();
	@FXML
	private TableView<Inventory> weeklyTable = new TableView<>();
	
	@FXML
	private Button editInventoryBtn = new Button();
	@FXML
	private Button deleteDriversBtn = new Button();
	@FXML
	private Button checkVehiclesBtn = new Button();
	
	@FXML
	private DatePicker dateField = new DatePicker();
	@FXML
	private TextField searchField = new TextField();
	
	Main main = new Main();
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@FXML
	private void createTableColumn() {
		

		TableColumn driverCol = new TableColumn<Inventory, String>("Driver");
		driverCol.setMinWidth(100);
		driverCol.setCellValueFactory(new PropertyValueFactory<>("driver"));

		TableColumn unitNumberCol = new TableColumn<Inventory, String>("Unit Number");
		unitNumberCol.setMinWidth(100);
		unitNumberCol.setCellValueFactory(new PropertyValueFactory<>("unitNumber"));

		TableColumn tripCol = new TableColumn<Inventory, String>("Trip");
		tripCol.setMinWidth(100);
		tripCol.setCellValueFactory(new PropertyValueFactory<>("trip"));

		TableColumn odometerCol = new TableColumn<Inventory, String>("Odometer");
		odometerCol.setMinWidth(100);
		odometerCol.setCellValueFactory(new PropertyValueFactory<>("odometer"));

		TableColumn pmsCol = new TableColumn<Inventory, String>("PMS");
		pmsCol.setMinWidth(100);
		pmsCol.setCellValueFactory(new PropertyValueFactory<>("pms"));

		TableColumn gasLtrsCol = new TableColumn<Inventory, String>("Gas Liters");
		gasLtrsCol.setMinWidth(100);
		gasLtrsCol.setCellValueFactory(new PropertyValueFactory<>("gasLtrs"));
		
		TableColumn checkTiresCol = new TableColumn<Inventory, String>("Check Tires");
		checkTiresCol.setMinWidth(100);
		checkTiresCol.setCellValueFactory(new PropertyValueFactory<>("checkTires"));
		
		TableColumn checkToolsCol = new TableColumn<Inventory, String>("Check Tools");
		checkToolsCol.setMinWidth(100);
		checkToolsCol.setCellValueFactory(new PropertyValueFactory<>("checkTools"));
		
		TableColumn docCol = new TableColumn<Inventory, String>("Documents");
		docCol.setMinWidth(100);
		docCol.setCellValueFactory(new PropertyValueFactory<>("doc"));
		
		TableColumn remarksCol = new TableColumn<Inventory, String>("Remarks");
		remarksCol.setMinWidth(100);
		remarksCol.setCellValueFactory(new PropertyValueFactory<>("remarks"));
		
		TableColumn dateCol = new TableColumn<Inventory, String>("Date");
		dateCol.setMinWidth(100);
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		dailyTable.setItems(data);

		dailyTable.getColumns().addAll(driverCol, unitNumberCol, tripCol,
								odometerCol, pmsCol, gasLtrsCol, 
								checkTiresCol, checkToolsCol, 
								docCol, remarksCol, dateCol);
	}
	private void createObsData() {
		DataManipulator dataman;
		try {
			dataman = new DataManipulator();

			// Lets create our TableModel
			ResultSet rs = dataman.generalQuery(this.query());
			while (rs.next()) {
				String driverName = Name.createFullName(rs.getString("d.first_name"), rs.getString("d.mid_name"), rs.getString("d.last_name"));
				
				// TODO collected should be inner join
				data.add(new Inventory(rs.getInt(1), 
						driverName, 
						rs.getInt("v.unit_number"),
						rs.getDouble(4),
						rs.getDouble(5),
						rs.getInt(6),
						rs.getDouble(7), 
						rs.getInt(8), 
						rs.getInt(9), 
						rs.getInt(10), 
						rs.getString(11), 
						DateUtil.formatDate(rs.getString(12))));

			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * private ObservableList<Admins> getAdminsData() { return this.data; }
	 */
	private ObservableList<Inventory> getInventoryData() {
		return this.data;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Let's disable the buttons if not login as Admin
		dateField.setValue(DateUtil.parse(DateUtil.newDateNormal()));
		if (!Constants.USERTYPE.equals("admin")) {

			this.editInventoryBtn.setDisable(true);
			this.deleteDriversBtn.setDisable(true);

		}

		this.createTableColumn();
		this.createObsData();
		filteredData();
	}
	
	

    @FXML
    private void refreshTable() {

    	data.removeAll(data);
		createObsData(); 
    }
	
	

	// Filtering method
	private void filteredData() {
		// 1. Wrap the ObservableList in a FilteredList (initially display all
		// data).
		FilteredList<Inventory> filteredData = new FilteredList<>(data, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		searchField.textProperty().addListener(
			(observable, oldValue, newValue) -> {
				filteredData.setPredicate(inventory -> {
				// If filter text is empty, display all persons.
						if (newValue == null || newValue.isEmpty()) {
							return true;
						}

						// Compare first name and last name of every person
						// with filter text.
						String lowerCaseFilter = newValue.toLowerCase();

						if (inventory.getDriver().toLowerCase()
									.contains(lowerCaseFilter)) {
							return true; // Filter matches admins username.
						}
							
						return false; // Does not match.
					});
				});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Inventory> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(dailyTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		dailyTable.setItems(sortedData);

	}
	
	@FXML
	public void writeCSV() throws Exception {
	   CSVWriter writer = null;
	   File file = null;
	    try {
	        file = new File("C:\\Inventory.csv");
	        writer = new CSVWriter(new FileWriter(file));
	        DataManipulator dataman = new DataManipulator();
	        ResultSet rs = dataman.getAllData(Constants.DB_ADMIN_TBL);
	        writer.writeAll(rs, true);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    finally {

	        writer.flush();
	        writer.close();
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
	 
	@FXML
	private void createNewInventory(){
		Inventory tempInventory = new Inventory();
		boolean okClicked = main.showEditDialog(tempInventory,
				"InventoryEditDialog.fxml");
		if (okClicked) {
			String message = "Are you sure you want to add this new data?";
			boolean answer = Form.dialogOkNo(null, message);
			if (answer) {
				this.getInventoryData().add(tempInventory);

				try {

					DataManipulator dataman = new DataManipulator();
					
					dataman.insertInventoryData(Constants.DB_INVENTORY_TBL, tempInventory);
					
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				createNewInventory();
			}
		}
	}
	
	@FXML
	private void handleEditInventory(){
		
	}
	
	@FXML
	private void handleDeleteInventory(){
		
	}
	
	private String query()
	{
		String finalQuery = "";
		// Create our TableModel

		String[] columnNames = { Constants.DB_INVENTORY_TBL + ".*",
						"d.first_name", "d.mid_name", "d.last_name",
						"v.unit_number"};

		String[] joins = { 
						"drivers AS d ON " + Constants.DB_INVENTORY_TBL + ".driver_id = d.id",
						"vehicles As v ON " + Constants.DB_INVENTORY_TBL + ".vehicle_id = v.id"};
				
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
		
		finalQuery = "SELECT " + query + " FROM " + Constants.DB_INVENTORY_TBL + " " + join + " WHERE date = \"" + dateField.getValue() + "\" ORDER BY v.unit_number";
		System.out.println(finalQuery);
		return finalQuery;
	}
}
