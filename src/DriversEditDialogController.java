
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import util.DateUtil;
import util.Form;
import model.Drivers;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * Dialog to edit details of a drivers.
 * 
 * @author Yves Gonzaga
 */
public class DriversEditDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField midNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField licenseNumField;
    @FXML
    private TextField lessRateField;
    @FXML
    private TextField birtPlaceTxt;
    @FXML
    private TextField prevCompanyTxt;
    @FXML
    private TextField contactNumberTxt;
    @FXML
    private TextField spouseTxt;
    @FXML
    private TextField referralTxt;
    
    @FXML
    private DatePicker birthDateDp;
    @FXML
    private DatePicker licenseExpDp;
    
    @FXML
    Label ageLbl = new Label();
    
    @FXML 
    private ComboBox<String> driverTypeCmb = new ComboBox<String>();
    @FXML 
    private ComboBox<String> dispatchStatusCmb = new ComboBox<String>();
	@FXML
    private ComboBox<String> empStatusCmb = new ComboBox<String>();
    @FXML
    private  ComboBox<String> maritalStatusCmb = new ComboBox<String>();
    
    private Stage dialogStage;
    private Drivers drivers;
    private boolean okClicked = false;

    
    
    
    
    
    
   
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    	driverTypeCmb.getItems().addAll(Drivers.DRIVER_TYPE);
    	dispatchStatusCmb.getItems().addAll(Drivers.DISPATCH_STATUS);
    	maritalStatusCmb.getItems().addAll(Drivers.MARITAL_STATUS);
    	empStatusCmb.getItems().addAll(Drivers.EMP_STATUS);
    	// Sets the days,month,year format of the DatePicker
    	licenseExpDp.setConverter(new StringConverter<LocalDate>() {
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
    	birthDateDp.setConverter(new StringConverter<LocalDate>() {
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

    
    
    
    
    
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the drivers to be edited in the dialog.
     * 
     * @param drivers
     * @throws ParseException 
     */
    public void setDrivers(Object object){
        this.drivers = (Drivers) object;
        
        firstNameField.setText(drivers.getFirstName());
        midNameField.setText(drivers.getMidName());
        lastNameField.setText(drivers.getLastName());
        addressField.setText(drivers.getAddress());
        licenseNumField.setText(drivers.getLicenseNum());
        birtPlaceTxt.setText(drivers.getBirthPlace());
        prevCompanyTxt.setText(drivers.getPrevCompany());
        contactNumberTxt.setText(drivers.getContactNumber());
        spouseTxt.setText(drivers.getSpouse());
        referralTxt.setText(drivers.getReferral());
        lessRateField.setText(Double.toString(drivers.getLessRate()));
        // Set birth date value
        if(drivers.getBirthDate() == null) {
        	
        	String dateString;
			try {
				dateString = DateUtil.reformatDate(DateUtil.newDate()); // Reverse the string from its database format.
				birthDateDp.setValue(DateUtil.parse(dateString));		// Format the string into LocalDate format.
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        	
        } else {
	        try {

				String dateString = DateUtil.reformatDate(drivers.getBirthDate()); // Reverse the string from its database format.
				birthDateDp.setValue(DateUtil.parse(dateString));	// Format the string into LocalDate format.
				
		        
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    	this.setAge();
        
        // Set license expire date
        if(drivers.getLicenseExp() == null) {
        	
            licenseExpDp.setValue(null);
        	
        } else {
	        try {

				String dateString = DateUtil.reformatDate(drivers.getLicenseExp()); // Reverse the string from its database format.
				licenseExpDp.setValue(DateUtil.parse(dateString));	// Format the string into LocalDate format.
				
		        
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        driverTypeCmb.setValue(drivers.getDriverType());
        dispatchStatusCmb.setValue(drivers.getDispatchStatus());
        empStatusCmb.setValue(drivers.getEmploymentStatus());
        maritalStatusCmb.setValue(drivers.getMaritalStatus());
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
     */
    @FXML
    private void handleOk() throws ParseException {
    	
        if (isInputValid()) {
        	
        	drivers.setFirstName(firstNameField.getText());
            drivers.setFirstName(firstNameField.getText());
            drivers.setMidName(midNameField.getText());
            drivers.setLastName(lastNameField.getText());
            drivers.setBirtDate(DateUtil.formatDate(DateUtil.format(birthDateDp.getValue())));
            drivers.setBirthPlace(birtPlaceTxt.getText());
            drivers.setAge(Integer.parseInt(ageLbl.getText()));
            drivers.setLessRate(Double.parseDouble(lessRateField.getText()));
            drivers.setPrevCompany(prevCompanyTxt.getText());
            drivers.setContactNumber(contactNumberTxt.getText());
            drivers.setMaritalStatus(maritalStatusCmb.getValue());
            drivers.setSpouse(spouseTxt.getText());
            drivers.setReferral(referralTxt.getText());
            drivers.setAddress(addressField.getText());
            drivers.setLicenseNum(licenseNumField.getText());
            drivers.setLicenseExp(DateUtil.formatDate(DateUtil.format(licenseExpDp.getValue())));
            drivers.setDriverType(driverTypeCmb.getValue());
            drivers.setDispatchStatus(dispatchStatusCmb.getValue());
            drivers.setEmploymentStatus(empStatusCmb.getValue());
            
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
    
    
    
    
    
    @FXML
    private void setAge() {
    	int age = 0;
		try {
			
				age = TimeChecker.checkAge(DateUtil.format(birthDateDp.getValue()));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		drivers.setAge(age);
        ageLbl.setText(Integer.toString(drivers.getAge()));
	}

 	@FXML
 	private void disableSpouse()
 	{
 		if (maritalStatusCmb.getValue().equals("single"))
 		{
 			spouseTxt.setDisable(true);
 		} else {
 			spouseTxt.setDisable(false);
 		}
 		
 	}
 	

 	

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (addressField.getText() == null || addressField.getText().length() == 0) {
            errorMessage += "No valid address!\n"; 
        }
        if (licenseNumField.getText() == null || licenseNumField.getText().length() == 0) {
            errorMessage += "No valid license number!\n"; 
        }

        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
        	Form.dialog(AlertType.WARNING, "Error", null, errorMessage);
            return false;
        }
    }
}