
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Drivers;
import util.DateUtil;
import util.Form;

import com.opencsv.CSVWriter;

public class DriversController implements Initializable {

    private final Main main = new Main();

    private final ObservableList<Drivers> data = FXCollections.observableArrayList();

    @FXML
    Button newDriversBtn = new Button();
    @FXML
    Button editDriversBtn = new Button();
    @FXML
    Button deleteDriversBtn = new Button();
    @FXML
    MenuItem editMenu = new MenuItem();
    @FXML
    MenuItem deleteMenu = new MenuItem();
    @FXML
    MenuItem bCashbondsMItem = new MenuItem();
    @FXML
    MenuItem bShortagesMItem = new MenuItem();
    @FXML
    MenuItem bDamagesMItem = new MenuItem();
    @FXML
    MenuItem pCashbondsMItem = new MenuItem();
    @FXML
    MenuItem pShortagesMItem = new MenuItem();
    @FXML
    MenuItem pDamagesMItem = new MenuItem();
    @FXML
    TextField searchField = new TextField();

    @FXML
    TableView<Drivers> table = new TableView<>();

    private String timeCheck;

    private Stage driverStage;

    @SuppressWarnings({"unused", "rawtypes", "unchecked"})
    @FXML
    private void createTableColumn() throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, SQLException, ParseException {
        DataManipulator dataman = new DataManipulator();
        // Lets create our TableModel
        ResultSet rs = dataman.getAllData(Constants.DB_DRIVER_TBL);

        table.setEditable(true);

        while (rs.next()) {
            System.out.println(rs.getFetchSize());
            data.add(new Drivers(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getDouble(7),
                    DateUtil.formatDate(rs.getString(8)),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getString(11),
                    rs.getString(12),
                    rs.getString(13),
                    rs.getString(14),
                    rs.getString(15),
                    rs.getString(16),
                    rs.getString(17),
                    DateUtil.formatDate(rs.getString(18)),
                    rs.getString(19),
                    rs.getString(20),
                    Drivers.EMP_STATUS[rs.getInt(21)],
                    Drivers.DISPATCH_STATUS[rs.getInt(22)])
            );

        }

        TableColumn firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn midNameCol = new TableColumn<>("Middle Name");
        midNameCol.setMinWidth(100);
        midNameCol.setCellValueFactory(new PropertyValueFactory<>("midName"));

        TableColumn lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn lessRateCol = new TableColumn<>("Less Rate");
        lessRateCol.setMinWidth(100);
        lessRateCol.setCellValueFactory(new PropertyValueFactory<>("lessRate"));

        TableColumn address = new TableColumn<>("Address");
        address.setMinWidth(100);
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn licenseNumber = new TableColumn<>("Licenses No.");
        licenseNumber.setMinWidth(100);
        licenseNumber.setCellValueFactory(new PropertyValueFactory<>("licenseNum"));

        TableColumn licenseExp = new TableColumn<>("Licenses Expire");
        licenseExp.setMinWidth(100);
        licenseExp.setCellValueFactory(new PropertyValueFactory<>("licenseExp"));
        licenseExp.setCellFactory(column -> {
            return new TableCell<Drivers, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    // clear any custom styles
                    this.getTableRow().getStyleClass().remove("expired");
                    this.getTableRow().getStyleClass().remove("warning");
                    // update the item and set a custom style if necessary
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else if (item != null) {

                        setText(item.toString());

                        try {
                            timeCheck = TimeChecker.checkExpire(DateUtil.reformatDate(this.getText()), DateUtil.newDateNormal());

                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (timeCheck.equals("expired")) {
                            this.getTableRow().getStyleClass().add("expired");
                        }
                        if (timeCheck.equals("warning")) {
                            this.getTableRow().getStyleClass().add("warning");
                        }

                    }

                }
            };
        });

        TableColumn driverType = new TableColumn<Drivers, String>("Driver Type");
        driverType.setMinWidth(100);
        driverType.setCellValueFactory(new PropertyValueFactory<>("driverType"));

        TableColumn backjob = new TableColumn<Drivers, String>("Backjob");
        backjob.setMinWidth(100);
        backjob.setCellValueFactory(new PropertyValueFactory<>("backjob"));

        TableColumn empStatus = new TableColumn<Drivers, String>("Employment Status");
        empStatus.setMinWidth(100);
        empStatus.setCellValueFactory(new PropertyValueFactory<>("employmentStatus"));

        TableColumn dispatchStatus = new TableColumn<Drivers, String>("Dispatch Status");
        dispatchStatus.setMinWidth(100);
        dispatchStatus.setCellValueFactory(new PropertyValueFactory<>("dispatchStatus"));

        // Add data to the table
        table.setItems(data);

        table.getColumns().addAll(firstNameCol, midNameCol, lastNameCol, lessRateCol, address, licenseNumber, licenseExp, driverType, backjob, empStatus, dispatchStatus);

    }

    private ObservableList<Drivers> getDriversData() {
        return data;
    }

    /*
	 * This method is the initializer of this controller
	 * (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Let's disable the buttons if not login as Admin
        if (!Constants.USERTYPE.equals("admin")) {

            this.newDriversBtn.setDisable(true);
            this.editDriversBtn.setDisable(true);
            this.deleteDriversBtn.setDisable(true);
            this.bCashbondsMItem.setDisable(true);

        }

        try {

            this.createTableColumn();	// Create the table column

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Initialize filter method
        this.filteredData();

    }

    /*
	 * Initialize methods
     */
    private void filteredData() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Drivers> filteredData = new FilteredList<>(data, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(drivers -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (drivers.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches driver name.
                } else if (drivers.getMidName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches  vehicle plate_number.
                } else if (drivers.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches  vehicle plate_number.
                } else if (drivers.getLicenseExp().toLowerCase().contains(lowerCaseFilter)) {
                    return false; // Filter matches  vehicle plate_number.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Drivers> sortedData = new SortedList<>(filteredData);
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
            file = new File("C:\\Drivers.csv");
            writer = new CSVWriter(new FileWriter(file));
            DataManipulator dataman = new DataManipulator();
            ResultSet rs = dataman.getAllData(Constants.DB_DRIVER_TBL);
            writer.writeAll(rs, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

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
    private void showLedgerView(ActionEvent e) {
        Drivers selectedDrivers = table.getSelectionModel().getSelectedItem();
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            if (e.getSource().equals(bCashbondsMItem)) {
                showLedgerDialog(selectedDrivers, "Balance", "cashbond");
            }
            if (e.getSource().equals(bShortagesMItem)) {
                showLedgerDialog(selectedDrivers, "Balance", "shortage");
            }
            if (e.getSource().equals(bDamagesMItem)) {
                showLedgerDialog(selectedDrivers, "Balance", "damage");
            }
            if (e.getSource().equals(pCashbondsMItem)) {
                showLedgerDialog(selectedDrivers, "Payments", "cashbond");
            }
            if (e.getSource().equals(pShortagesMItem)) {
                showLedgerDialog(selectedDrivers, "Payments", "shortage");
            }
            if (e.getSource().equals(pDamagesMItem)) {
                showLedgerDialog(selectedDrivers, "Payments", "damage");
            }
        } else {
            Form.dialog(AlertType.WARNING, "Error", null, "No Selected Driver");
        }

    }

    public void showLedgerDialog(Object object, String ledgerType, String ledgerSubType) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("LedgerDialog.fxml"));

            AnchorPane loginLayout = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            // Create the dialog Stage.
            Stage ledgerStage = new Stage();
            ledgerStage.setTitle("Ledger View Dialog");
            ledgerStage.initModality(Modality.APPLICATION_MODAL);
            ledgerStage.initOwner(driverStage);

            Scene scene = new Scene(loginLayout);
            scene.getStylesheets().add(getClass().getResource("vista.css").toExternalForm());
            ledgerStage.setScene(scene);

            LedgerDialogController controller = loader.getController();
            controller.setDialogStage(ledgerStage);				// Set the stage so that we know what stage to close or needs an action
            controller.setLedger(object, ledgerType, ledgerSubType);
            ledgerStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showProfile() {
        Drivers selectedDrivers = table.getSelectionModel().getSelectedItem();

        if (selectedDrivers != null) {

            main.showProfileDialog(selectedDrivers, "DriverProfileDialog.fxml");

            /*
		 	boolean okClicked = main.showProfileDialog(selectedDrivers,"DriverProfileDialog.fxml");
			if (okClicked) {

				int response = JOptionPane.showConfirmDialog(null, "Are you sure with this changes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(response == JOptionPane.YES_OPTION)
				{
					// Update Database
				}
			}
             */
        }
    }

    @FXML
    public void createNewDriver() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ParseException {

        Drivers tempDrivers = new Drivers();
        boolean okClicked = main.showEditDialog(tempDrivers, "DriversEditDialog.fxml");
        if (okClicked) {
            String message = "Are you sure you want to add this new data?";
            boolean answer = Form.dialogOkNo(null, message);
            if (answer) {
                this.getDriversData().add(tempDrivers);

                DataManipulator dataman = new DataManipulator();
                dataman.insertDriversData(Constants.DB_DRIVER_TBL, tempDrivers);
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
    private void handleDeleteDrivers() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Drivers selectedDrivers = table.getSelectionModel().getSelectedItem();
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {

            String message = "Are you sure you want to add this new data?";
            boolean answer = Form.dialogOkNo(null, message);
            if (answer) {
                //table.getItems().remove(selectedIndex);
                DataManipulator dataman = new DataManipulator();
                dataman.deleteData(Constants.DB_DRIVER_TBL, "", selectedDrivers.getId());

            }

        } else {
            Form.dialog(AlertType.WARNING, "Error", null, "No Selected Driver");
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected Drivers.
     *
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     * @throws ParseException
     */
    @FXML
    private void handleEditDrivers() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ParseException {

        Drivers selectedDrivers = table.getSelectionModel().getSelectedItem();

        if (selectedDrivers != null) {
            boolean okClicked = main.showEditDialog(selectedDrivers, "DriversEditDialog.fxml");
            if (okClicked) {

                String message = "Are you sure you want to add this new data?";
                boolean answer = Form.dialogOkNo(null, message);
                if (answer) {
                    // Refresh the table
                    table.getColumns().get(0).setVisible(false);
                    table.getColumns().get(0).setVisible(true);

                    DataManipulator dataman = new DataManipulator();
                    dataman.updateDriversData(Constants.DB_DRIVER_TBL, selectedDrivers);
                }
            }

        } else {
            // Nothing selected.
            Form.dialog(AlertType.WARNING, "Error", null, "No row selected");
        }
    }

    @FXML
    public void showBalanceDialog() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ParseException {

        Drivers selectedDrivers = table.getSelectionModel().getSelectedItem();

        if (selectedDrivers != null) {

            main.showBalanceDialog(selectedDrivers, "BalanceDialog.fxml");

        } else {
            Form.dialog(AlertType.WARNING, "Error", null, "Please select driver from the table");
        }
    }

}
