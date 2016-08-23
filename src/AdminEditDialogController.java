
import java.text.ParseException;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Admins;
import util.DateUtil;
import util.Form;

/**
 * Dialog to edit details of a admins.
 * 
 * @author Yves Gonzaga
 */
public class AdminEditDialogController {

	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField password1Field;
	@FXML
	private PasswordField password2Field;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField middleNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
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
    Label ageLbl = new Label();
    
    @FXML
    private DatePicker birthDateDp;
    
    private Stage dialogStage;
    private Admins admins;
    private boolean okClicked = false;

	@FXML
    private ComboBox<String> userTypeChoice = new ComboBox<String>();
	@FXML
    private ComboBox<String> empStatusCmb = new ComboBox<String>();
    @FXML
    private  ComboBox<String> maritalStatusCmb = new ComboBox<String>();
    
    
    
    
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() 
    {
    	userTypeChoice.getItems().addAll(Admins.USERS);
    	empStatusCmb.getItems().addAll(Admins.EMP_STATUS);
    	maritalStatusCmb.getItems().addAll(Admins.MARITAL_STATUS);
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
     * Sets the admins to be edited in the dialog.
     * 
     * @param admins
     */
    public void setAdmins(Object object) {
    	
        this.admins = (Admins) object;
        
        usernameField.setText(admins.getUsername());
        firstNameField.setText(admins.getFirstName());
        middleNameField.setText(admins.getMidName());
        lastNameField.setText(admins.getLastName());
        emailField.setText(admins.getEmail());
        
        // Set birth date value
        if(admins.getBirthDate() == null) {
        	
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

				String dateString = DateUtil.reformatDate(admins.getBirthDate()); // Reverse the string from its database format.
				birthDateDp.setValue(DateUtil.parse(dateString));	// Format the string into LocalDate format.
				
		        
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        birtPlaceTxt.setText(admins.getBirthPlace());
        this.setAge();
        prevCompanyTxt.setText(admins.getPrevCompany());
        contactNumberTxt.setText(admins.getContactNumber());
        maritalStatusCmb.setValue(admins.getMaritalStatus());
        spouseTxt.setText(admins.getSpouse());
        referralTxt.setText(admins.getReferral());
        userTypeChoice.setValue(admins.getUserType());
        empStatusCmb.setValue(admins.getEmploymentStatus());
        
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
        	
        	admins.setUsername(usernameField.getText());
        	admins.setPassword(password1Field.getText());
            admins.setFirstName(firstNameField.getText());
            admins.setMidName(middleNameField.getText());
            admins.setLastName(lastNameField.getText());
            admins.setEmail(emailField.getText());
            admins.setBirthDate(DateUtil.formatDate(DateUtil.format(birthDateDp.getValue())));
            admins.setBirthPlace(birtPlaceTxt.getText());
            admins.setAge(Integer.parseInt(ageLbl.getText()));
            admins.setPrevCompany(prevCompanyTxt.getText());
            admins.setContactNumber(contactNumberTxt.getText());
            admins.setMaritaLStatus(maritalStatusCmb.getValue());
            admins.setSpouse(spouseTxt.getText());
            admins.setReferral(referralTxt.getText());
            admins.setUserType(userTypeChoice.getValue());
            admins.setEmploymentStatus(empStatusCmb.getValue());
            
            okClicked = true;
            dialogStage.close();
        }
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
		admins.setAge(age);
		ageLbl.setText(Integer.toString(age));
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
        
        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (password1Field.getText() == null || password1Field.getText().length() == 0)
        {
        	errorMessage += "No input found in password 1\n"; 
        }
        if (password2Field.getText() == null || password2Field.getText().length() == 0)
        {
        	errorMessage += "No input found in password 2\n"; 
        }
        if (!password1Field.getText().equals(password2Field.getText())) {
        	errorMessage += "Password 1 and Password2 don't match!\n";
        }
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (userTypeChoice.getValue() == null || userTypeChoice.getValue().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
        	Form.dialog(AlertType.INFORMATION, "Information Dialog", null, errorMessage);
            return false;
        }
    }
}