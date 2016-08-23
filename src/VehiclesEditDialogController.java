
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Vehicles;
import util.DateUtil;

/**
 * Dialog to edit details of a vehicles.
 * 
 * @author Yves Gonzaga
 */
public class VehiclesEditDialogController {

	@FXML
    private TextField unitNumberField = new TextField();
    @FXML
    private TextField plateNumberField = new TextField();;
    @FXML
    private TextField caseNumberField = new TextField();;
    @FXML
    private TextField ccnField = new TextField();;
    @FXML
    private TextField chasisNumberField = new TextField();;
    @FXML
    private TextField engineNumberField = new TextField();;
    @FXML
    private ComboBox<String> carBrandCmb = new ComboBox<String>();
    @FXML
    private TextField carRateField = new TextField();;
    @FXML
    private DatePicker plateRegDateField = new DatePicker();
    @FXML
    private DatePicker resealDateField = new DatePicker();
    @FXML
    private DatePicker cpcRegDateField = new DatePicker();
    @FXML
    private DatePicker eotRegDateField = new DatePicker();
    @FXML
    private ComboBox<String> statusCmb = new ComboBox<String>();

    private Stage dialogStage;
    private Vehicles vehicles;
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
    	this.formateDatePicker(plateRegDateField);
    	this.formateDatePicker(resealDateField);
    	this.formateDatePicker(cpcRegDateField);
    	this.formateDatePicker(eotRegDateField);
    	this.setCarBrandCmb();
    	this.statusCmb.getItems().addAll("available", "on repair", "on dispatch");
    }
    
 
    // Format date field display output
    private void formateDatePicker(DatePicker datepicker)
    {
    	
    	datepicker.setConverter(new StringConverter<LocalDate>() {
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

    private void setCarBrandCmb() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
    	DataManipulator dataman = new DataManipulator();
    	ResultSet rs = dataman.getAllData(Constants.DB_VEHICLECSPEC_TBL);
    	
    	while(rs.next())
    	{
	    	
	    	carBrandCmb.getItems().add(rs.getString("car_brand"));
    	}
    }
    
    @FXML
    private void setCarRateField() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException 
    {
    	DataManipulator dataman = new DataManipulator();
    	
    	ResultSet rs = dataman.generalQuery("SELECT car_rate FROM " + Constants.DB_VEHICLECSPEC_TBL + " WHERE car_brand = \"" + carBrandCmb.getValue() + "\""); 

    	while(rs.next())
    	{
    		carRateField.setText(rs.getString("car_rate"));
    	}
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
     * Sets the vehicles to be edited in the dialog.
     * 
     * @param vehicles
     */
    public void setVehicles(Object object) {
        this.vehicles = (Vehicles) object;
        unitNumberField.setText(Integer.toString(vehicles.getUnitNumber()));
        plateNumberField.setText(vehicles.getPlateNumber());
        caseNumberField.setText(vehicles.getCaseNumber());
        carBrandCmb.setValue(vehicles.getCarBrand());
        carRateField.setText(Integer.toString(vehicles.getCarRate()));
        ccnField.setText(vehicles.getCcn());
        chasisNumberField.setText(vehicles.getChasisNumber());
        engineNumberField.setText(vehicles.getEngineNumber());
        
        setDateField(plateRegDateField, vehicles.getPlateRegistration());
        setDateField(resealDateField, vehicles.getResealDate());
        setDateField(cpcRegDateField, vehicles.getCpcRegistration());
        setDateField(eotRegDateField, vehicles.getEotRegistration());
        
        statusCmb.setValue(vehicles.getStatus());
    }
    
    public void setDateField(DatePicker datepicker, String date)
    {
    	 if(date == null) {
         	
    		 datepicker.setValue(null);
         	
         } else {
 	        try {

 				String dateString = DateUtil.reformatDate(date); // Reverse the string from its database format.
 				datepicker.setValue(DateUtil.parse(dateString));	// Format the string into LocalDate format.
 				
 		        
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
     */
    @FXML
    private void handleOk() throws ParseException {
        if (isInputValid()) {
        	
        	vehicles.setUnitNumber(Integer.parseInt(unitNumberField.getText()));
        	vehicles.setPlateNumber(plateNumberField.getText());
        	vehicles.setCaseNumber(caseNumberField.getText());
        	vehicles.setCcn(ccnField.getText());
        	vehicles.setChasisNumber(chasisNumberField.getText());
        	vehicles.setCarBrand(carBrandCmb.getValue());
        	vehicles.setCarRate(Integer.parseInt(carRateField.getText()));
        	vehicles.setEngineNumber(engineNumberField.getText());
        	vehicles.setPlateRegistration(DateUtil.formatDate(DateUtil.format(plateRegDateField.getValue())));
	        vehicles.setResealDate(DateUtil.formatDate(DateUtil.format(resealDateField.getValue())));
	        vehicles.setCpcRegistration(DateUtil.formatDate(DateUtil.format(cpcRegDateField.getValue())));
	        vehicles.setEotRegistration(DateUtil.formatDate(DateUtil.format(eotRegDateField.getValue())));
            vehicles.setStatus(statusCmb.getValue());
            
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

        if (plateNumberField.getText() == null || plateNumberField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (caseNumberField.getText() == null || caseNumberField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (plateRegDateField.getValue() == null || plateRegDateField.getValue().lengthOfYear() == 0) {
            errorMessage += "No valid address!\n"; 
        }
        else {
           /* if (!DateUtil.validDate(regesteredDateField.getText())) {
                errorMessage += "No valid license registration date. Use the format dd-MM-yyyy!\n";
            }*/
        }
        if (resealDateField.getValue() == null || resealDateField.getValue().lengthOfYear() == 0) {
            errorMessage += "No valid license number!\n"; 
        }
        else {
        	/*
            if (!DateUtil.validDate(resealDateField.getText())) {
                errorMessage += "No valid license reseal date. Use the format dd-MM-yyyy!\n";
            }*/
        }
        if (statusCmb.getValue() == null || statusCmb.getValue().length() == 0) {
            errorMessage += "No valid license number!\n"; 
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            System.out.print(errorMessage);
            return false;
        }
    }
}