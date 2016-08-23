
import java.awt.HeadlessException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginController implements Initializable{
	@FXML
	private TextField usernameField = new TextField();
	@FXML
	private PasswordField passwordField = new PasswordField();
	@FXML
	private Button loginBtn = new Button();
	@FXML
	private Button cancelBtn = new Button();
	@FXML
	private Label errorLabel = new Label();

	private Main main;

	private MainController mainController;

	private Stage stage;

	public static boolean loginSuccess = false;

	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//usernameField.setText(null);
		//passwordField.setText(null);
		
	}
	
	
	public void setApp(Main main) {
		this.setMain(main);

	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage stage) {
		this.setStage(stage);
	}

	// For enter key pressed
	@FXML
	public void handleEnterPressed(KeyEvent event)
	{

		if (event.getCode() == KeyCode.ENTER) {
			
			try {
				this.checkFields();
			} catch (HeadlessException | ClassNotFoundException
					| InstantiationException | IllegalAccessException
					| NoSuchAlgorithmException | InvalidKeySpecException
					| SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// For login button pressed
	@FXML
	private void confirmLogin(ActionEvent event) {

		if (event.getSource().equals(loginBtn)) {

			try {
				this.checkFields();
			} catch (HeadlessException | ClassNotFoundException
					| InstantiationException | IllegalAccessException
					| NoSuchAlgorithmException | InvalidKeySpecException
					| SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}

		if (event.getSource().equals(cancelBtn)) {
			System.exit(0);
		}
	}
	
	private void checkFields() throws ClassNotFoundException,
	InstantiationException, IllegalAccessException, SQLException,
	HeadlessException, NoSuchAlgorithmException,
	InvalidKeySpecException {
		DataManipulator dataman = new DataManipulator();
		
		String query = "";
		ResultSet rs = null;

		String username = "";
		String password = "";
		String userType = "";
		
		if(!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty() ) {
			
			query = "SELECT username, password, user_type FROM admins WHERE username = \""
					+ usernameField.getText() + "\"";
			rs = dataman.generalQuery(query);
			
			while (rs.next()) {
				
				username = rs.getString("username");
				password = rs.getString("password");
				userType = rs.getString("user_type");
				MainController mc = new MainController();
				mc.initialize();
			}
			
			if (usernameField.getText().equals(username) && PasswordEncryption.validatePassword(passwordField.getText(), password)) {

				loginSuccess = true;
				Constants.USERNAME = username;
				Constants.USERTYPE = userType;
				
				mainController = new MainController();
				mainController.initLabel();
						
					
				//this.setUserLabel(Constants.USERNAME);
				//this.changeUserLabel();
				stage.close();
				
			} else {

				errorLabel.setText("Wrong username/password!");
			}
			
		} else {
			
			errorLabel.setText("Empty username/password!");
		}
	}
	
	public void setLabel(MainController controller) {
        System.out.println("Connector.Connecting(): Called");
		controller.setLabelText("Bye World");
	}
	
	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}


}
