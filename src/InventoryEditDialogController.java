import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import util.DateUtil;
import util.Form;
import util.Name;
import model.Inventory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class InventoryEditDialogController implements Initializable {
	
	@FXML
	private TextField unitNumberField = new TextField();
	@FXML
	private TextField tripField = new TextField();
	@FXML
	private TextField presentOdoField = new TextField();
	@FXML
	private TextField gastLtrsField = new TextField();

	@FXML
	private Label driverLbl = new Label();
	@FXML
	private Label previousOdoLbl = new Label();
	@FXML
	private Label tripLbl = new Label();
	@FXML
	private Label carRatioLbl = new Label();
	
	@FXML
	private CheckBox pmsCheck = new CheckBox();
	@FXML
	private CheckBox checkTires = new CheckBox();
	@FXML
	private CheckBox checkTools = new CheckBox();
	@FXML
	private CheckBox docCheck = new CheckBox();

	@FXML
	private DatePicker dateField = new DatePicker();
	private Stage dialogStage;
	
	private boolean okClicked = false;
	Inventory inventory;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		setUnitNumberField();
		unitNumberField.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean oldPropertyValue, Boolean newPropertyValue) {
						if (!newPropertyValue) {
							setDriverLbl();
						}
					}

		});
	}

	public void setInventory(Object object) {
		inventory = (Inventory) object;
		driverLbl.setText(inventory.getDriver());
		unitNumberField.setText(Integer.toString(inventory.getUnitNumber()));
		tripField.setText(Double.toString(inventory.getTrip()));
		//presentOdoField.setText(Double.toString(inventory.getOdometer()));
		gastLtrsField.setText(Double.toString(inventory.getGasLtrs()));
		dateField.setValue(DateUtil.parse(DateUtil.newDateNormal()));
		setCheckBoxes();
	}
	
	private void setCheckBoxes() {

		if (inventory.getPms()==1) {
			pmsCheck.setSelected(true);
		}
		if (inventory.getCheckTires() == 1) {
			checkTires.setSelected(true);
		}
		if (inventory.getCheckTools() == 1) {
			checkTools.setSelected(true);
		}
		if (inventory.getDoc() == 1) {
			docCheck.setSelected(true);
		}
	}
	
	private void setDriverLbl()
    {
    	DataManipulator dataman;
		try {
			dataman = new DataManipulator();

	    	
	    	String query = "SELECT first_name, mid_name, last_name FROM " 
	    					+ Constants.DB_DRIVER_TBL + " WHERE dispatch_status = 1";
	    	
	    	ResultSet rs = dataman.generalQuery(query);
	    	
	    	if(rs.next())
	    	{
		    	driverLbl.setText(Name.createFullName(rs.getString("first_name"), rs.getString("mid_name"), rs.getString("last_name")));
	    	}
	    	
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void setUnitNumberField()
    {
    	DataManipulator dataman;
    	
		try {
			dataman = new DataManipulator();

	    	String query = "SELECT unit_number FROM " + Constants.DB_VEHICLE_TBL + " WHERE status = \"on dispatch\"";
	    	
	    	ResultSet rs = dataman.generalQuery(query);
	    	
	    	ObservableList<String> unitNumList =  FXCollections.observableArrayList();
	    	
	    	while(rs.next())
	    	{
	    		unitNumList.add((rs.getString("unit_number")).trim());
	    	}
	    	TextFields.bindAutoCompletion(unitNumberField, unitNumList);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		
	}
	
	 /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked; 
    }

    /**
     * Called when the user clicks ok.
     * @throws ParseException 
     * @throws SQLException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     */
    @FXML
    private void handleOk() throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        if (isInputValid()) {
        	inventory.setDriver(driverLbl.getText());
        	inventory.setUnitNumber(Integer.parseInt(unitNumberField.getText()));
        	inventory.setTrip(Double.parseDouble(tripField.getText()));
        	//inventory.setOdometer(Double.parseDouble(presentOdoField.getText()));
        	inventory.setGasLtrs(Double.parseDouble(gastLtrsField.getText()));
        	inventory.setDate(DateUtil.format(dateField.getValue()));
        	getCheckBoxes();
        	
        	okClicked = true;
        	dialogStage.close();
  
        }
    }

	private void getCheckBoxes() {

		if (pmsCheck.isSelected()) {
			inventory.setPms(1);
		}
		if (checkTires.isSelected()) {
			inventory.setCheckTires(1);
		}
		if (checkTools.isSelected()) {
			inventory.setCheckTools(1);
		}
		if (docCheck.isSelected()) {
			inventory.setDoc(1);
		}
	}

	/**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        /*
        if (driverField.getText() == null || driverField.getText().trim().length() == 0) {
            errorMessage += "No valid driver name!\n"; 
        }*/
        
        if (unitNumberField.getText() == null || unitNumberField.getText().trim().length() == 0 || unitNumberField.getText().equals("0")) {
            errorMessage += "No valid unit number!\n"; 
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
    		Form.dialog(AlertType.INFORMATION, "Information Message",null, errorMessage);
            return false;
        }
    }
}
