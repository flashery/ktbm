
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

import com.opencsv.CSVWriter;

import util.DateUtil;
import util.Form;
import model.Admins;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
//import dialog.Dialog;
import javafx.util.StringConverter;

public class AdminsController implements Initializable {

	private Main main = new Main();
	private ObservableList<Admins> data = FXCollections.observableArrayList();

	@FXML
	Button newAdminBtn = new Button();
	@FXML
	Button editBtn = new Button();
	@FXML
	Button deleteBtn = new Button();

	@FXML
	TextField searchField = new TextField();
	
	@FXML
	private TableView<Admins> table = new TableView<Admins>();

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@FXML
	private void createTableColumn() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException,
			ParseException {
		DataManipulator dataman = new DataManipulator();

		// Lets create our TableModel
		ResultSet rs = dataman.getAllData(Constants.DB_ADMIN_TBL);
		ResultSetMetaData rs_meta = rs.getMetaData();

		table.setEditable(true);

		while (rs.next()) {

			data.add(new Admins(rs.getInt(1), rs.getString(2), null, rs.getString(4), rs
					.getString(5), rs.getString(6), rs.getString(7), rs
					.getString(8), DateUtil.formatDate(rs.getString(9)), rs.getString(10), rs.getInt(11),
					rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16),
					rs.getString(17), DateUtil.formatDate(rs.getString(18)), Admins.EMP_STATUS[rs.getInt(19)]));
			
		}

		TableColumn usernameCol = new TableColumn<Admins, String>("Username");
		usernameCol.setMinWidth(100);
		usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

		TableColumn firstNameCol = new TableColumn<Admins, String>("First Name");
		firstNameCol.setMinWidth(100);
		firstNameCol
				.setCellValueFactory(new PropertyValueFactory<>("firstName"));

		TableColumn midNameCol = new TableColumn<Admins, String>("Middle Name");
		midNameCol.setMinWidth(100);
		midNameCol.setCellValueFactory(new PropertyValueFactory<>("midName"));

		TableColumn lastNameCol = new TableColumn<Admins, String>("Last Name");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

		TableColumn emailCol = new TableColumn<Admins, String>("Email");
		emailCol.setMinWidth(200);
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

		TableColumn birthDateCol = new TableColumn<Admins, String>("Birth Date");
		birthDateCol.setMinWidth(200);
		birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		
		TableColumn birthPlaceCol = new TableColumn<Admins, String>("Birth Place");
		birthPlaceCol.setMinWidth(200);
		birthPlaceCol.setCellValueFactory(new PropertyValueFactory<>("birthPlace"));
		
		TableColumn ageCol = new TableColumn<Admins, String>("Age");
		ageCol.setMinWidth(200);
		ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
		
		TableColumn prevCompanyCol = new TableColumn<Admins, String>("Previous Company");
		prevCompanyCol.setMinWidth(200);
		prevCompanyCol.setCellValueFactory(new PropertyValueFactory<>("prevCompany"));
		
		TableColumn contactNumberCol = new TableColumn<Admins, String>("Contact Number");
		contactNumberCol.setMinWidth(200);
		contactNumberCol.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

		TableColumn maritalStatusCol = new TableColumn<Admins, String>("Marital Status");
		maritalStatusCol.setMinWidth(200);
		maritalStatusCol.setCellValueFactory(new PropertyValueFactory<>("maritalStatus"));
		
		TableColumn spouseCol = new TableColumn<Admins, String>("Spouse");
		spouseCol.setMinWidth(200);
		spouseCol.setCellValueFactory(new PropertyValueFactory<>("spouse"));
		
		TableColumn referralCol = new TableColumn<Admins, String>("Referral");
		referralCol.setMinWidth(200);
		referralCol.setCellValueFactory(new PropertyValueFactory<>("referral"));
		
		TableColumn userTypeCol = new TableColumn<Admins, String>("User Type");
		userTypeCol.setMinWidth(100);
		userTypeCol.setCellValueFactory(new PropertyValueFactory<>("userType"));

		TableColumn dateJoinedCol = new TableColumn<Admins, String>(
				"Date Joined");
		dateJoinedCol.setMinWidth(100);
		dateJoinedCol.setCellValueFactory(new PropertyValueFactory<>(
				"dateJoined"));
		
		TableColumn employmentStatusCol = new TableColumn<Admins, String>("Employment Status");
		employmentStatusCol.setMinWidth(200);
		employmentStatusCol.setCellValueFactory(new PropertyValueFactory<>("employmentStatus"));

		table.setItems(data);

		table.getColumns().addAll(usernameCol, firstNameCol, midNameCol,
								lastNameCol, emailCol, birthDateCol, 
								birthPlaceCol, ageCol, prevCompanyCol, 
								spouseCol, referralCol, userTypeCol, 
								dateJoinedCol, employmentStatusCol);

		rs.close();
	}

	/*
	 * private ObservableList<Admins> getAdminsData() { return this.data; }
	 */
	private ObservableList<Admins> getAdminsData() {
		return this.data;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Let's disable the buttons if not login as Admin
		if (!Constants.USERTYPE.equals("admin")) {

			this.newAdminBtn.setDisable(true);
			this.editBtn.setDisable(true);
			this.deleteBtn.setDisable(true);

		}

		try {

			this.createTableColumn();

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		filteredData();
	}
	
	

	// Filtering method
	private void filteredData() {
		// 1. Wrap the ObservableList in a FilteredList (initially display all
		// data).
		FilteredList<Admins> filteredData = new FilteredList<>(data, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		searchField.textProperty().addListener(
			(observable, oldValue, newValue) -> {
				filteredData.setPredicate(admins -> {
				// If filter text is empty, display all persons.
						if (newValue == null || newValue.isEmpty()) {
							return true;
						}

						// Compare first name and last name of every person
						// with filter text.
						String lowerCaseFilter = newValue.toLowerCase();

						if (admins.getUsername().toLowerCase()
									.contains(lowerCaseFilter)) {
							return true; // Filter matches admins username.
						} else 	if (admins.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
							return true; // Filter matches admins name.
						} else 	if (admins.getMidName().toLowerCase().contains(lowerCaseFilter)) {
							return true; // Filter matches admins name.
						} else 	if (admins.getLastName().toLowerCase().contains(lowerCaseFilter)) {
							return true; // Filter matches admins name.
						} else 	if (admins.getUserType().toLowerCase().contains(lowerCaseFilter)) {
							return true; // Filter matches admins name.
						} 
							
						return false; // Does not match.
					});
				});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Admins> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(table.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		table.setItems(sortedData);

	}
	
	@FXML
	public void writeCSV() throws Exception {
	   CSVWriter writer = null;
	   File file = null;
	    try {
	        file = new File("C:\\Admins.csv");
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
	public void showProfile() {
		Admins selectedAdmins = table.getSelectionModel().getSelectedItem();

		if (selectedAdmins != null) {
			boolean okClicked = main.showProfileDialog(selectedAdmins,
					"AdminProfileDialog.fxml");

			if (okClicked) {
				String message = "Are you sure with this changes?";
				boolean answer = Form.dialogOkNo(null, message);
				if (answer) {
					// Update Database
				}
			}
		}
	}

	@FXML
	public void createNewAdmin() {
		Admins tempAdmins = new Admins();
		boolean okClicked = main.showEditDialog(tempAdmins,
				"AdminEditDialog.fxml");
		if (okClicked) {
			String message = "Are you sure you want to add this new data?";
			boolean answer = Form.dialogOkNo(null, message);
			if (answer) {
				this.getAdminsData().add(tempAdmins);

				try {

					DataManipulator dataman = new DataManipulator();
					
					dataman.insertAdminData(Constants.DB_ADMIN_TBL, tempAdmins.getUsername(), PasswordEncryption.generateStrongPasswordHash(tempAdmins.getPassword()), 
							tempAdmins.getFirstName(), tempAdmins.getMidName(), tempAdmins.getLastName(), tempAdmins.getEmail(), DateUtil.reformatDate(tempAdmins.getBirthDate()), 
							tempAdmins.getBirthPlace(), tempAdmins.getAge(), tempAdmins.getPrevCompany(), tempAdmins.getContactNumber(), tempAdmins.getMaritalStatus(),
							tempAdmins.getSpouse(), tempAdmins.getReferral(),tempAdmins.getUserType(), tempAdmins.getEmploymentStatus());
					
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | SQLException
						| NoSuchAlgorithmException | InvalidKeySpecException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
	private void handleDeleteAdmin() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {
		
		Admins selectedAdmins = table.getSelectionModel().getSelectedItem();
		
		int selectedIndex = table.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {

			String message = "Are you sure you want to add this new data?";
			boolean answer = Form.dialogOkNo(null, message);
			if (answer) {
				DataManipulator dataman = new DataManipulator();
				dataman.deleteData(Constants.DB_ADMIN_TBL,
						selectedAdmins.getUsername(), 0);
				table.getItems().remove(selectedIndex);
			}

		} else {
			String message = "No Selected row";
			Form.dialog(AlertType.INFORMATION, "Error", null, message);
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
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws ParseException
	 */
	@FXML
	private void handleEditAdmin() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException,
			NoSuchAlgorithmException, InvalidKeySpecException, ParseException {

		Admins selectedAdmins = table.getSelectionModel().getSelectedItem();

		if (selectedAdmins != null) {
			boolean okClicked = main.showEditDialog(selectedAdmins,
					"AdminEditDialog.fxml");

			if (okClicked) {

				String message = "Are you sure with this changes?";
				boolean answer = Form.dialogOkNo(null, message);
				if (answer) {
					// Update Database
					DataManipulator dataman = new DataManipulator();
					dataman.updateAdminData(Constants.DB_ADMIN_TBL, selectedAdmins.getUsername(), PasswordEncryption.generateStrongPasswordHash(selectedAdmins.getPassword()), 
							selectedAdmins.getFirstName(), selectedAdmins.getMidName(), selectedAdmins.getLastName(), selectedAdmins.getEmail(), DateUtil.reformatDate(selectedAdmins.getBirthDate()), 
							selectedAdmins.getBirthPlace(), selectedAdmins.getAge(), selectedAdmins.getPrevCompany(), selectedAdmins.getContactNumber(), selectedAdmins.getMaritalStatus(),
							selectedAdmins.getSpouse(), selectedAdmins.getReferral(), selectedAdmins.getUserType(), selectedAdmins.getEmploymentStatus());

					// Refresh the table
					table.getColumns().get(0).setVisible(false);
					table.getColumns().get(0).setVisible(true);
				}
			}

		}
	}

}
