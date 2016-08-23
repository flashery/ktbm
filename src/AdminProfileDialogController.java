
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Admins;

public class AdminProfileDialogController implements Initializable {

	private Image profileImage;
	private Stage dialogStage;

	@FXML
	ImageView profileImageView = new ImageView();
	@FXML
	Label statusLabel = new Label();
	@FXML
	Label nameLabel = new Label();
	@FXML
	Label positionLabel = new Label();
	@FXML
	TextArea addressTxtArea = new TextArea();
	
	// Dispatch Status
	@FXML
	Label absentsLabel = new Label();
	@FXML
	Label memoLabel = new Label();
	@FXML
	Label othersLabel = new Label();
	@FXML
	Pane imageViewPane = new Pane();
	
	Main main = new Main();
	
	Admins admins;
	
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

		this.admins = (Admins) object;
		
		try {
			setProfilePicture();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | FileNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setProfileData();
		
	}
	

	private void setProfilePicture() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, FileNotFoundException {
		
		DataManipulator dataman = new DataManipulator();

		ResultSet rs = dataman.generalQuery("SELECT profile_picture FROM admins WHERE id = " + admins.getId());
		
		while(rs.next())
		{
			if(rs.getString("profile_picture") == null)
			{
				profileImage = new Image(new FileInputStream(Constants.PROFILE_IMAGE_PATH + Constants.DEFAULT_PROFILEPIC), 160, 0, true, true);
			} else {
				profileImage = new Image(new FileInputStream(Constants.PROFILE_IMAGE_PATH + rs.getString("profile_picture")), 160, 0, true, true);
			}
		}
		
		profileImageView.setImage(profileImage);
		profileImageView.setFitWidth(160);
		profileImageView.setPreserveRatio(true);
		profileImageView.setSmooth(true);
		profileImageView.setCache(true);

	}
	
	@FXML
	private void uploadPhoto() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
	{
		final FileChooser fileChooser = new FileChooser();
	    File file = fileChooser.showOpenDialog(dialogStage);
	    File dest = new File(Constants.PROFILE_IMAGE_PATH + file.getName());
	   
	    
	    
	    if (file != null) {
	        try {

				DataManipulator dataman = new DataManipulator();
				dataman.generalUpdate("UPDATE drivers SET profile_picture = \"" + file.getName() + "\" WHERE id = " + admins.getId());
			
				// Copy image to our profile image folder
				Files.copy(file.toPath(), dest.toPath(), REPLACE_EXISTING);
				
				// Close this stage and show again with a new profile picture
				this.dialogStage.close();
				main.showProfileDialog(this.admins,"DriverProfileDialog.fxml");
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }          
	}
	
	

	private void setProfileData()
	{
		statusLabel.setText("active");
		// Get middle initial
		String midInit = admins.getMidName().substring(0, 1);
		String driverName = admins.getFirstName() + " " + midInit + " " + admins.getLastName();
		nameLabel.setText(driverName);
		addressTxtArea.setText(null);
	}
	

}
