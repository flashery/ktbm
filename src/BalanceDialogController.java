import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Balance;
import model.Drivers;

import org.controlsfx.control.textfield.TextFields;

import util.DateUtil;
import util.Form;
import util.Name;

public class BalanceDialogController extends MainController implements Initializable {

	//@FXML
	//private ComboBox<String> balanceTypeCmb = new ComboBox<>();
	//@FXML
	//private ComboBox<String> driverCmb = new ComboBox<>();
	@FXML
	private TextField driverField = new TextField();
	@FXML
	private TextField balanceTypeField = new TextField();
	@FXML
	private TextField cashbondsField = new TextField();
	@FXML
	private TextField damagesField = new TextField();
	@FXML
	private TextField loansField = new TextField();
	@FXML
	private TextField participationsField = new TextField();
	@FXML
	private TextField penaltiesField = new TextField();
	@FXML
	private TextField shortagesField = new TextField();
	@FXML
	private DatePicker dateField = new DatePicker();
	/*
	@FXML
	private Label currentBalanceLbl = new Label();
	@FXML
	private TextField amountTxtField = new TextField();
	@FXML
	private Label labelForTotal = new Label();
	@FXML
	private Label totalBalanceLbl = new Label();*/

	private Stage dialogStage;
	private boolean okClicked = false;
	
	private Balance balances;
	private Drivers drivers;
	private String balanceType;
	private int adminId;
	
	private final double CASHBONDS = 4500;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		
		try {
			initializeTxtFields();
			//getCurrentBalance();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setBalances(Object object) {

		this.drivers = (Drivers) object;
		
		driverField.setText(createFullName(drivers.getFirstName(), drivers.getMidName(), drivers.getLastName()));
		balanceTypeField.setText("damages");

		setDriverAdminId();
		
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
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;

	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {

		// Show a warming message
		String message = "Are you sure to add this balance?";
		boolean answer = Form.dialogOkNo(null, message);
		if (answer) {
			if (isInputValid()) {
		
				try {
					
					DataManipulator dataman = new DataManipulator();
					System.out.println("SELECT driver_id FROM total_balance "
							+ "WHERE  driver_id = " + drivers.getId());
					ResultSet rs = dataman.generalQuery("SELECT driver_id FROM total_balance "
							+ "WHERE driver_id  = " + drivers.getId());
					
					if(rs.next()) {
						createNewBalance(false);
						System.out.println("Not new Driver dont create new total balance");
					} else {
						createNewBalance(true);
						System.out.println("New Driver create new total balance");
					}
					dialogStage.close();
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		}
	}
	
	
	// Insert balance daily records
	private void createNewBalance(boolean newDriver) {
		try {				
			DataManipulator dataman = new DataManipulator();
			
			// Insert new balance
			dataman.insertNewBalance(drivers.getId(), adminId, 
									Double.parseDouble(cashbondsField.getText()), Double.parseDouble(damagesField.getText()),
									Double.parseDouble(loansField.getText()), Double.parseDouble(participationsField.getText()),
									Double.parseDouble(penaltiesField.getText()), Double.parseDouble(shortagesField.getText()),
									0.0, DateUtil.format(dateField.getValue()));
							
	
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(newDriver){
			createNewTotalBalance();
		} else {
			updateTotalBalance();
		}
	}
	

	private void createNewTotalBalance() {
		try {		

			double cCashB = 0;
			double cDamages = 0;
			double cLoans = 0;
			double cPenalties = 0;
			double cParticipations = 0;
			double cShortages = 0;
			double cAppreS = 0;
			
			DataManipulator dataman = new DataManipulator();

			// Check for daily records
			ResultSet rs = dataman.generalQuery("SELECT SUM(cashbonds) AS cashbonds, "
								+ "SUM(damages) AS damages, SUM(loans) AS loans, "
								+ "SUM(participations) AS participations, "
								+ "SUM(penalties) AS penalties, SUM(shortages) AS shortages, "
								+ "SUM(apprehension_saving) AS apprehension_saving FROM balance "
								+ "WHERE driver_id = " + drivers.getId());
			
			while(rs.next())
			{
				cCashB = rs.getDouble("cashbonds");
				cDamages = rs.getDouble("damages");
				cLoans = rs.getDouble("loans");
				cPenalties = rs.getDouble("participations");
				cParticipations = rs.getDouble("penalties");
				cShortages = rs.getDouble("shortages");
				cAppreS = rs.getDouble("apprehension_saving");
			}
			
			// Insert new total balance
			dataman.insertTotalBalance(drivers.getId(), 
					(Double.parseDouble(cashbondsField.getText()) + cCashB), 
					(Double.parseDouble(damagesField.getText()) + cDamages),
					(Double.parseDouble(loansField.getText()) + cLoans), 
					(Double.parseDouble(participationsField.getText()) + cParticipations),
					(Double.parseDouble(penaltiesField.getText()) + cPenalties), 
					(Double.parseDouble(shortagesField.getText()) + cShortages),
					(0.0 + cAppreS));
			
		
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateTotalBalance() {
		try {				

			double tCashB = 0;
			double tDamages = 0;
			double tLoans = 0;
			double tPenalties = 0;
			double tParticipations = 0;
			double tShortages = 0;
			double tAppreS = 0;
			
			DataManipulator dataman = new DataManipulator();
			ResultSet rs = dataman.generalQuery("SELECT total_cashbonds, total_damages, "
										+ "total_loans, total_participations, "
										+ "total_penalties, total_shortages, "
										+ "total_apprehension_saving "
										+ "FROM total_balance "
										+ "WHERE driver_id = " + drivers.getId());
			
			if(rs.next()) {
				tCashB = rs.getDouble("total_cashbonds");
				tDamages = rs.getDouble("total_damages");
				tLoans = rs.getDouble("total_loans");
				tParticipations = rs.getDouble("total_participations");
				tPenalties = rs.getDouble("total_penalties");
				tShortages = rs.getDouble("total_shortages");
				tAppreS = rs.getDouble("total_apprehension_saving");
			}
			// Insert new total balance
			dataman.updateTotalBalance(drivers.getId(), 
					(tCashB + Double.parseDouble(cashbondsField.getText())), 
					(tDamages + Double.parseDouble(damagesField.getText())), 
					(tLoans + Double.parseDouble(loansField.getText())), 
					(tParticipations + Double.parseDouble(participationsField.getText())), 
					(tPenalties + Double.parseDouble(penaltiesField.getText())), 
					(tShortages + Double.parseDouble(shortagesField.getText())), 
					(tAppreS + 0.0));
			
		
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {

		dialogStage.close();

	}

	
	private void initializeTxtFields() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {
		
		DataManipulator dataman = new DataManipulator();
		ResultSet rs;
		
		ObservableList<String> driversList =  FXCollections.observableArrayList();
		
		// Add drivers
		rs = dataman.generalQuery("SELECT first_name, mid_name, last_name FROM drivers");
		while (rs.next()) {
			// Create middle initial
			driversList.add(createFullName(rs.getString("first_name"), rs.getString("mid_name"), rs.getString("last_name")));
			
		}
		
		TextFields.bindAutoCompletion(driverField, driversList);
		cashbondsField.setText("0.0");
		damagesField.setText("0.0");
	    loansField.setText("0.0");
		participationsField.setText("0.0");
		penaltiesField.setText("0.0");
		shortagesField.setText("0.0");
	}
	
	private void setDriverAdminId() 
	{
		try {
			DataManipulator dataman = new DataManipulator();
			ResultSet rs;
			
			// Set drivers id
			rs = dataman.generalQuery("SELECT id FROM drivers WHERE first_name = \"" + Name.getFirstName(driverField.getText()) + "\" AND last_name = \"" + Name.getLastName(driverField.getText()) + "\"");
			while (rs.next()) {
				// Create middle initial
				drivers.setId(rs.getInt("id"));
			}
			
			// Set admins id
			rs = dataman.generalQuery("SELECT id FROM admins WHERE username = \"" + Constants.USERNAME + "\"");
			while (rs.next()) {
				// Create middle initial
				adminId = rs.getInt("id");
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (driverField.getText() == null
				|| driverField.getText().length() == 0) {
			errorMessage += "Unknown driver!\n";
		}

		if(dateField.getValue() == null || dateField.getValue().lengthOfYear() == 0) {
			errorMessage += "Unknown date!\n";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Form.dialog(AlertType.INFORMATION, "Infomation", null, errorMessage);
			return false;
		}
	}
}
