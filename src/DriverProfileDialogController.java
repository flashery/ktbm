
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Balance;
import model.Drivers;
import util.DateUtil;
import util.Form;

public class DriverProfileDialogController implements Initializable {

	
	private Stage dialogStage;

	private Image profileImage;
	@FXML
	ImageView profileImageView = new ImageView();
	private Image signImage;
	@FXML
	ImageView signImageView = new ImageView();
	@FXML
	private Label empStatusLbl = new Label();
	@FXML
	private Label nameLabel = new Label();
	@FXML
	private Label driverTypeLbl = new Label();
	@FXML
	private Label nameExpLabel = new Label();
	@FXML
	private Label licenseLabel = new Label();
	@FXML
	private Label licenseExpLabel = new Label();
	@FXML
	private Label birthDateLbl;
	@FXML
	private Label birthPlaceLbl;
	@FXML
	private Label ageLbl = new Label();
	@FXML
	private Label prevCompanyLbl;
	@FXML
	private Label contactNumberLbl;
	@FXML
	private Label maritalStatusLbl;
	@FXML
	private Label spouseLbl;
	@FXML
	private Label referralLbl;
	@FXML
	TextArea addressTxtArea = new TextArea();
	
	// Dispatch Status
	@FXML
	Label dataLabel = new Label();
	@FXML
	Label dispatchStatusLabel = new Label();
	@FXML
	Label vehicleLabel = new Label();
	@FXML
	Label timeInLabel = new Label();
	@FXML
	Label timeOutLabel = new Label();

	// Payments
	@FXML
	Label pendingsLabel = new Label();
	@FXML
	Label backJobsLabel = new Label();
	@FXML
	Label cashbondsLabel = new Label();
	@FXML
	Label shortagesLabel = new Label();
	@FXML
	Label damagesLabel = new Label();
	@FXML
	Label penaltiesLabel = new Label();
	@FXML
	Label participationsLabel = new Label();
	@FXML
	Label addonsLabel = new Label();
	@FXML
	Label loansLabel = new Label();
	@FXML
	Label totalBalanceLabel = new Label();
	@FXML
	Pane imageViewPane = new Pane();

	Main main = new Main();

	Drivers drivers;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {

		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setProfile(Object object) {

		this.drivers = (Drivers) object;

		try {
			setProfilePicture();
			setSignPicture();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | FileNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setProfileData();

		try {
			setDispatchStatus();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setDriverPayments();
		
	}

	private void setProfilePicture() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException,
			FileNotFoundException {
		DataManipulator dataman = new DataManipulator();

		ResultSet rs = dataman.generalQuery("SELECT profile_picture FROM drivers WHERE id = " + drivers.getId());

		while (rs.next()) {
			if (rs.getString("profile_picture") == null) {
				profileImage = new Image(new FileInputStream(
						Constants.PROFILE_IMAGE_PATH
								+ Constants.DEFAULT_PROFILEPIC), 160, 0, true,
						true);
			} else {
				profileImage = new Image(new FileInputStream(
						Constants.PROFILE_IMAGE_PATH
								+ rs.getString("profile_picture")), 160, 0,
						true, true);
			}
		}

		profileImageView.setImage(profileImage);
		profileImageView.setFitWidth(160);
		profileImageView.setPreserveRatio(true);
		profileImageView.setSmooth(true);
		profileImageView.setCache(true);

	}

	@FXML
	private void uploadPhoto() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {
		final FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(dialogStage);

		File dest = null;

		if (file != null) {

			try {

				dest = new File(Constants.PROFILE_IMAGE_PATH + file.getName());

				DataManipulator dataman = new DataManipulator();
				dataman.generalUpdate("UPDATE drivers SET profile_picture = \""
						+ file.getName() + "\" WHERE id = " + drivers.getId());

				// Copy image to our profile image folder
				Files.copy(file.toPath(), dest.toPath(), REPLACE_EXISTING);

				// Close this stage and show again with a new profile picture
				this.dialogStage.close();
				main.showProfileDialog(this.drivers, "DriverProfileDialog.fxml");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	private void setSignPicture() throws ClassNotFoundException,
	InstantiationException, IllegalAccessException, SQLException,
	FileNotFoundException {
		DataManipulator dataman = new DataManipulator();
		
		ResultSet rs = dataman
				.generalQuery("SELECT sign_picture FROM drivers WHERE id = "
						+ drivers.getId());
		
		while (rs.next()) {
			if (rs.getString("sign_picture") == null) {
				signImage = new Image(new FileInputStream(
						Constants.SIGN_PATH
								+ Constants.DEFAULT_SIGN), 260, 0, true,
						true);
			} else {
				signImage = new Image(new FileInputStream(
						Constants.SIGN_PATH
								+ rs.getString("sign_picture")), 220, 0,
						true, true);
			}
		}
		signImageView.setImage(signImage);
		signImageView.setFitWidth(220);
		signImageView.setPreserveRatio(true);
		signImageView.setSmooth(true);
		signImageView.setCache(true);

	}
	
	@FXML
	private void uploadSign() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {
		final FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(dialogStage);

		File dest = null;

		if (file != null) {

			try {

				dest = new File(Constants.SIGN_PATH + file.getName());

				DataManipulator dataman = new DataManipulator();
				dataman.generalUpdate("UPDATE drivers SET sign_picture = \""
						+ file.getName() + "\" WHERE id = " + drivers.getId());

				// Copy image to our profile image folder
				Files.copy(file.toPath(), dest.toPath(), REPLACE_EXISTING);

				// Close this stage and show again with a new profile picture
				this.dialogStage.close();
				main.showProfileDialog(this.drivers, "DriverProfileDialog.fxml");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void setProfileData() {
		empStatusLbl.setText(drivers.getEmploymentStatus());
		// Get middle initial
		String midInit = drivers.getMidName().substring(0, 1);
		String driverName = drivers.getFirstName() + " " + midInit + " "
				+ drivers.getLastName();
		nameLabel.setText(driverName);
		driverTypeLbl.setText(drivers.getDriverType());
		birthDateLbl.setText(drivers.getBirthDate());
		birthPlaceLbl.setText(drivers.getBirthPlace());
		ageLbl.setText(Integer.toString(drivers.getAge()));
		prevCompanyLbl.setText(drivers.getPrevCompany());
		contactNumberLbl.setText(drivers.getContactNumber());
		maritalStatusLbl.setText(drivers.getMaritalStatus());
		spouseLbl.setText(drivers.getSpouse());
		referralLbl.setText(drivers.getReferral());
		licenseLabel.setText(drivers.getLicenseNum());
		licenseExpLabel.setText(drivers.getLicenseExp());
		addressTxtArea.setText(drivers.getAddress());
	}

	private void setDispatchStatus() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException,
			ParseException {

		dataLabel.setText(DateUtil.newDate());
		dispatchStatusLabel.setText(drivers.getDispatchStatus());

		DataManipulator dataman = new DataManipulator();

		ResultSet rs = dataman
				.generalQuery("SELECT vehicles.plate_number, dispatch.time_out FROM dispatch "
						+ "INNER JOIN vehicles ON  dispatch.vehicle_id = vehicles.id "
						+ "INNER JOIN drivers ON  dispatch.driver_id = drivers.id "
						+ "WHERE drivers.id = " + drivers.getId() + " "
						+ "AND dispatch.date = \"" + DateUtil.newDateNormal() + "\"");
		while (rs.next()) {
			vehicleLabel.setText(rs.getString("plate_number"));
			//timeInLabel.setText(rs.getString("time_in"));
			timeOutLabel.setText(rs.getString("time_out"));
		}
	}

	private void setDriverPayments() {
		
		Balance balance = null;
		try {
			
			DataManipulator dataman = new DataManipulator();
			String query = "SELECT id, total_cashbonds, total_damages, total_loans, total_participations, "
					+ "total_penalties, total_shortages FROM total_balance WHERE driver_id = " + drivers.getId() + "";
			ResultSet rs = dataman.generalQuery(query);			
			if(rs.next())
			{
				double totaBalance = rs.getDouble("total_cashbonds") + rs.getDouble("total_damages") 
									+ rs.getDouble("total_loans") + rs.getDouble("total_participations")
									+ rs.getDouble("total_penalties") + rs.getDouble("total_shortages");
				balance = new Balance(rs.getInt("id"), null, null, rs.getDouble("total_cashbonds"), 
							rs.getDouble("total_damages"), rs.getDouble("total_loans"), rs.getDouble("total_participations"), 
							rs.getDouble("total_penalties"), rs.getDouble("total_shortages"), totaBalance);
			} else {
				balance = new Balance();
			}
			
			cashbondsLabel.setText(Double.toString(balance.getCashbonds()));
			shortagesLabel.setText(Double.toString(balance.getShortages()));
			damagesLabel.setText(Double.toString(balance.getDamages()));
			penaltiesLabel.setText(Double.toString(balance.getPenalties()));
			participationsLabel.setText(Double.toString(balance.getParticipations()));
			loansLabel.setText(Double.toString(balance.getLoans()));
			totalBalanceLabel.setText(Double.toString(balance.getTotalBalance()));
		
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkBlacklisted() {

		if(drivers.getEmploymentStatus() == "blacklisted")
		{
			Form.dialog(AlertType.WARNING, 
					"Blacklisted Warning", 
					"This driver is blacklisted!", 
					"Please inform admin about this before continuing");
		}
	}
}
