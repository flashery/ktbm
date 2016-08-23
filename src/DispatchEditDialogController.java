
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Dispatch;
import model.Drivers;

import org.controlsfx.control.textfield.TextFields;

import util.DateUtil;
import util.Form;
import util.Name;

/**
 * Dialog to edit details of a dispatch.
 * 
 * @author Yves Gonzaga
 */
public class DispatchEditDialogController {

    //@FXML
    //private ComboBox<String> driverCmb;
    //@FXML
    //private ComboBox<String> vehicleCmb;
	@FXML
	private TextField driverField;
	@FXML
	private TextField unitNumField;
	//@FXML
	//private TextField vehicleField;
    //@FXML
    //private TextField timeInField;
    @FXML
    private TextField timeOutField;
    //@FXML
    //private TextField carBrandField;
    //@FXML
    //private TextField carRateField;
    @FXML
    private Label vehiclePlateLbl;
    @FXML
    private Label carBrandLbl;
    @FXML
    private Label carRateLbl;
    @FXML 
    private DatePicker dateField;
    
    private Stage dialogStage;
    private Dispatch dispatch;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @throws SQLException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     */
    @FXML
    private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
    	
    	if(!Constants.USERTYPE.equals("admin"))
    	{
    		dateField.setDisable(true);
    	}
    	
    	this.setDriverField();
    	this.setUnitNumField();
    	this.setDateField();
    	this.initializeTxtFields();
    	
    }
    
   
    private void initializeTxtFields() {
    	driverField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(
					ObservableValue<? extends Boolean> arg0,
					Boolean oldPropertyValue, Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if(driverField.getText().trim().length() != 0){
						setContactNumber();
					}
				}
			}
		});
    	unitNumField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(
					ObservableValue<? extends Boolean> arg0,
					Boolean oldPropertyValue, Boolean newPropertyValue) {
				if (!newPropertyValue) {
					try {
						if(unitNumField.getText().trim().length() != 0){
							setCarRateBrand();
						}
					} catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}


	/**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the dispatch to be edited in the dialog.
     * 
     * @param dispatch
     */
    public void setDispatch(Object object) {
        this.dispatch = (Dispatch) object;

        driverField.setText(dispatch.getDriver());
        unitNumField.setText(Integer.toString(dispatch.getUnitNumber()));
        
        if(dispatch.getTimeOut() == null) {
        	System.out.println("AW aw aw");
			try {
				timeOutField.setText(DateUtil.formatStringDT(DateUtil.newDateTime()));		// Format the string into LocalDate format.
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        	
        } else {
        	System.out.println(dispatch.getTimeOut());
        	timeOutField.setText(dispatch.getTimeOut());
        }
        dispatch.setStatus(Dispatch.DISPATCH_STATUS[0]);
        vehiclePlateLbl.setText(dispatch.getVehicle());
        
       //timeOutField.setText(dispatch.getTimeOut());
        carBrandLbl.setText(dispatch.getBrand());
        carRateLbl.setText(Double.toString(dispatch.getRate()));
        
        if(dispatch.getDate() == null) {
        	
        	String dateString;
			try {
				dateString = DateUtil.reformatDate(DateUtil.newDate());
				dateField.setValue(DateUtil.parse(dateString));	// Format the string into LocalDate format.
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Reverse the string from its database format.
	       
        	
        } else {
	        try {

				String dateString = DateUtil.reformatDate(dispatch.getDate()); // Reverse the string from its database format.
		        dateField.setValue(DateUtil.parse(dateString));	// Format the string into LocalDate format.
				
		        
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
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
        		if(updateDriverStatus()){
        			dispatch.setDriver(driverField.getText());
        		}

	        	if(updateVehicleStatus()){
	        		dispatch.setUnitNumber(Integer.parseInt(unitNumField.getText()));
		            dispatch.setVehicle(vehiclePlateLbl.getText());
		            System.out.println(dispatch.getUnitNumber());
	        	}
	        	
	            this.setAdmin();
	            //dispatch.setTimeIn(timeInField.getText());
	            dispatch.setTimeOut(timeOutField.getText());
	            dispatch.setBrand(carBrandLbl.getText());
	            dispatch.setRate(Double.parseDouble(carRateLbl.getText()));
	            dispatch.setStatus(dispatch.getStatus());
	            dispatch.setDate(DateUtil.formatDate(DateUtil.format(dateField.getValue())));
	
	            okClicked = true;
	            dialogStage.close();
  
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

        if (driverField.getText() == null || driverField.getText().trim().length() == 0) {
            errorMessage += "No valid driver name!\n"; 
        }
        
        if (unitNumField.getText() == null || unitNumField.getText().trim().length() == 0 || unitNumField.getText().equals("0")) {
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
    
    
    public void setAdmin() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
    	DataManipulator dataman = new DataManipulator();
    	ResultSet rs = dataman.generalQuery("SELECT * FROM admins WHERE username = \"" + Constants.USERNAME + "\"");
    	String adminName = "";
    	while(rs.next())
    	{
    		// Create admin middle initial
			String adminMidName =  rs.getString("mid_name").substring(0, 1) + ".";
						
			// Concatenate the admin's name
			adminName = rs.getString("first_name") + " " + adminMidName + " " + rs.getString("last_name");
			
    	}

		dispatch.setAdmin(adminName);
    }
    
    private void setCarRateBrand() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
    		DataManipulator dataman = new DataManipulator();
	    		
	    	ResultSet rs = dataman.generalQuery("SELECT plate_number, rate, brand FROM " + Constants.DB_VEHICLE_TBL
						+ " WHERE unit_number = \"" + unitNumField.getText() +"\" "
						+ "AND status = \"available\"");
	    	
	    	if(rs.next())
	    	{
	    		vehiclePlateLbl.setText(rs.getString("plate_number"));
	    		carBrandLbl.setText(rs.getString("brand"));
	        	carRateLbl.setText(Double.toString(rs.getDouble("rate")));
	    	} else {
	    		String message = "This vehicle was already dispatched";
	    		Form.dialog(AlertType.INFORMATION, "Information Message",null, message);
	    	}
	    	//dispatch.setUnitNumber(Integer.parseInt(unitNumField.getText()));	
    }

    // SET old vehicle status to available
    private boolean updateVehicleStatus() {
    	boolean result = false;
		try {
			DataManipulator dataman = new DataManipulator();
	    	
	    	ResultSet rs;
	    	
	    	if(dispatch.getUnitNumber() == 0) {


	    		String message = "";
	    		message += "No unit number we need to add new\n";
	    		message += "New unit number = " +  unitNumField.getText().trim() + "\n";
	    		message +=  "Old unit number = " + dispatch.getUnitNumber();
	    		System.out.println(message);
	    		result = true;
	    		
	    	} else if(!unitNumField.getText().trim().equals(Integer.toString(dispatch.getUnitNumber()))) {
	    		
	    		System.out.println("Not the same unit number we need to update");
	    		System.out.println("New unit number = " +  unitNumField.getText().trim() + " Old unit number = " + dispatch.getUnitNumber());
	    		
	    		String query = "SELECT id FROM vehicles "
				        		+ "WHERE unit_number = " + dispatch.getUnitNumber() + "";
	    		
	    		rs = dataman.generalQuery(query);
	    		
	    		if(rs.next()) {
	    			
	    			query = "UPDATE vehicles SET status = \"available\" WHERE id = " + rs.getInt("id");
	    			
	    			String message = "";
	    			if(dataman.generalUpdate(query)) {
	    				message = "Succesfully updated";
	    				result = true;
	    			} else {
	    				 message = "Not succesfully updated";
	    				 result = false;
	    			}
	    			System.out.println(message);
	    		}
	    		
	    	} else if(dispatch.getDriver().equals(driverField.getText().trim())) {
	    		
	    		String message = "";
	    		message += "The same unit number no further action\n";
	    		message += "New unit number = " +  driverField.getText().trim() + "\n";
	    		message +=  "Old unit number = " + dispatch.getDriver();
	    		System.out.println(message);
	    		
	    		result = true;
	    	}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return result;
	}


	private void setDriverField() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
    	DataManipulator dataman = new DataManipulator();
    	
    	String query = "SELECT * FROM " + Constants.DB_DRIVER_TBL + " WHERE dispatch_status <> 1";
    	
    	ResultSet rs = dataman.generalQuery(query);
    	
    	ObservableList<String> driversList =  FXCollections.observableArrayList();
    	
    	while(rs.next())
    	{
	    	// Create middle initial
	    	String midName =  rs.getString("mid_name").substring(0, 1) + ".";
	    				
	    	// Concatenate the driver's name
	    	String driverName = rs.getString("first_name") + " " + midName + " " + rs.getString("last_name");
	    	
	    	driversList.add(driverName);
    	}
    	
    	TextFields.bindAutoCompletion(driverField, driversList);
    }
    
    private void setUnitNumField() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
    	DataManipulator dataman = new DataManipulator();
    	
    	String query = "SELECT unit_number FROM " + Constants.DB_VEHICLE_TBL + " WHERE status = \"available\"";
    	
    	ResultSet rs = dataman.generalQuery(query);
    	
    	ObservableList<String> unitNumList =  FXCollections.observableArrayList();
    	
    	while(rs.next())
    	{
    		unitNumList.add((rs.getString("unit_number")).trim());
    	}
    	TextFields.bindAutoCompletion(unitNumField, unitNumList);
    }
    
    private void setDateField()
    {
    	 dateField.setConverter(new StringConverter<LocalDate>() {
             DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");

             @Override 
             public String toString(LocalDate date) {
                 if (date != null) {
                     return dateFormatter.format(date);
                 } else {
                     return "";
                 }
             }

             @Override 
             public LocalDate fromString(String string) {
                 if (string != null && !string.isEmpty()) {
                     return LocalDate.parse(string, dateFormatter);
                 } else {
                     return null;
                 }
             }
         });
    }
    
    private void setContactNumber() {
    	
	    	DataManipulator dataman;
			try {
				
				dataman = new DataManipulator();
				ResultSet rs = dataman.generalQuery("SELECT contact_number FROM drivers "
						+ "WHERE first_name = \"" + Name.getFirstName(driverField.getText()) + "\" "
						+ "AND last_name = \"" + Name.getLastName(driverField.getText()) + "\"");
		    	
		    	while(rs.next())
		    	{
		    		dispatch.setContactNumber(rs.getString("contact_number"));
		    	}
		    	
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//showDriverProfile();
    	
    }
    
    private boolean updateDriverStatus() {
    	boolean result = false;
		try {
			DataManipulator dataman = new DataManipulator();
	    	
	    	ResultSet rs;
	    	
	    	if(dispatch.getDriver() == null) {

	    		String message = "";
	    		message += "No driver we need to add new\n";
	    		message += "New driver = " +  driverField.getText().trim() + "\n";
	    		message +=  "Old driver = " + dispatch.getDriver();
	    		System.out.println(message);
	    		result = true;
	    		
	    	} else if(!dispatch.getDriver().equals(driverField.getText().trim())) {
	    		
	    		System.out.println("Not the same driver we need to update");
	    		System.out.println("New driver = " +  driverField.getText().trim() + " Old driver = " + dispatch.getDriver());
	    		
	    		String query = "SELECT id FROM drivers "
				        		+ "WHERE first_name = \"" + Name.getFirstName(dispatch.getDriver()) + "\" "
				        		+ "AND last_name = \"" + Name.getLastName(dispatch.getDriver()) + "\"";
	    		
	    		rs = dataman.generalQuery(query);
	    		
	    		if(rs.next()) {
	    			
	    			query = "UPDATE drivers SET dispatch_status = 0 WHERE id = " + rs.getInt("id");
	    			
	    			String message = "";
	    			if(dataman.generalUpdate(query)) {
	    				message = "Succesfully updated";
	    				result = true;
	    			} else {
	    				 message = "Not succesfully updated";
	    				 result = false;
	    			}
	    			System.out.println(message);
	    		}
	    		
	    	} else if(dispatch.getDriver().equals(driverField.getText().trim())) {
	    		
	    		String message = "";
	    		message += "The same driver no further action\n";
	    		message += "New driver = " +  driverField.getText().trim() + "\n";
	    		message +=  "Old driver = " + dispatch.getDriver();
	    		System.out.println(message);
	    		//Form.dialog(AlertType.INFORMATION, "Update Info", null, message);
	    		
	    		result = true;
	    	}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return result;
	}


    
    private void showDriverProfile() 
    {
		try {
			DataManipulator dataman = new DataManipulator();
	    	Drivers driver = null;
	    	Main main = new Main();
	    	
	    	String query = "SELECT * FROM drivers "
	    			+ "WHERE first_name = \"" + Name.getFirstName(driverField.getText()) + "\" "
					+ "AND last_name = \"" + Name.getLastName(driverField.getText()) + "\"";
	    	
			ResultSet rs = dataman.generalQuery(query);

			if (rs.next())
			{
				 
				driver = new Drivers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), DateUtil.formatDate(rs.getString(8)), 
						rs.getString(9),rs.getInt(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16),
						rs.getString(17),  DateUtil.formatDate(rs.getString(18)), rs.getString(19), rs.getString(20), Drivers.EMP_STATUS[rs.getInt(21)], Drivers.DISPATCH_STATUS[rs.getInt(22)]);
				
			}
			
			main.showProfileDialog(driver,"DriverProfileDialog.fxml");
			
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}