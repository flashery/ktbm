
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.Rounding;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main application class.
 */
public class Main extends Application {

	public Stage primaryStage;
	
	@FXML
	Label userLabel = new Label();

	@Override
	public void start(Stage stage) throws Exception {
		

		loadLoginScene();

		if(LoginController.loginSuccess)
		{

			this.primaryStage = stage;
			this.primaryStage.setTitle(Constants.SOFTWARE_TITLE);
			// Set the application icon
			this.primaryStage.getIcons().add(new Image("file:" + Constants.IMAGE_PATH + Constants.DEFAULT_ICON));

			this.primaryStage.setScene(createScene(loadMainPane()));
			this.primaryStage.setMaximized(true);
			//this.primaryStage.sizeToScene();
			//this.primaryStage.setFullScreen(true);
			this.primaryStage.show();
			
		} else {
			System.exit(0);
		}
		
		
	}
	
	/**
	 * Creates the main application scene.
	 *
	 * @param mainPane the main application layout.
	 *
	 * @return the created scene.
	 */
	private Scene createScene(Pane mainPane) {
		Scene scene = new Scene(mainPane);

		scene.getStylesheets().setAll(getClass().getResource("vista.css").toExternalForm());

		return scene;
	}

	
	/**
	 * Loads the main fxml layout. Sets up the vista switching VistaNavigator.
	 * Loads the first vista into the fxml layout.
	 *
	 * @return the loaded pane.
	 * @throws IOException
	 *             if the pane could not be loaded.
	 */
	private Pane loadMainPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();

		Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(VistaNavigator.VISTAS[0]));

		MainController mainController = loader.getController();

		VistaNavigator.setMainController(mainController);
		VistaNavigator.loadVista(VistaNavigator.VISTAS[1]);

		return mainPane;
	}
	
	public void close() {
		primaryStage.hide();
	}
	public void loadLoginScene() throws IOException
	{	
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("Login.fxml"));

			AnchorPane loginLayout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			// Create the dialog Stage.
			Stage loginStage = new Stage();
			loginStage.setTitle("Login Form");
			loginStage.initModality(Modality.APPLICATION_MODAL);
			// Set the application icon
			loginStage.getIcons().add(new Image("file:" + Constants.IMAGE_PATH + Constants.DEFAULT_ICON));
			loginStage.initOwner(primaryStage);
			
			Scene scene = new Scene(loginLayout);
			scene.getStylesheets().add(Main.class.getResource("vista.css").toExternalForm());
			loginStage.setScene(scene); //
			//loginStage.sizeToScene();
			loginStage.initStyle(StageStyle.UNDECORATED);
			
			LoginController controller = loader.getController();
			controller.setDialogStage(loginStage);				// Set the stage so that we know what stage to close or needs an action
			
			loginStage.showAndWait();
			
	}
	
	public void loadMainScene() throws IOException
	{
		
	}
	

	/*
	 * This function will show the Edit Dialog window
	 */
	public boolean showEditDialog(Object object, String fxml) {
		boolean isOkClicked = false;
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(fxml));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit/Add");
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.sizeToScene();

                    // Set the controller.
                    switch (fxml) {
                        case "AdminEditDialog.fxml":
                            {
                                AdminEditDialogController controller = loader.getController();
                                controller.setDialogStage(dialogStage);
                                controller.setAdmins(object);
                                // Show the dialog and wait until the user closes it
                                dialogStage.showAndWait();
                                isOkClicked = controller.isOkClicked();
                                break;
                            }
                        case "DriversEditDialog.fxml":
                            {
                                DriversEditDialogController controller = loader.getController();
                                controller.setDialogStage(dialogStage);
                                controller.setDrivers(object);
                                // Show the dialog and wait until the user closes it
                                dialogStage.showAndWait();
                                isOkClicked = controller.isOkClicked();
                                break;
                            }
                        case "VehiclesEditDialog.fxml":
                            {
                                VehiclesEditDialogController controller = loader.getController();
                                controller.setDialogStage(dialogStage);
                                controller.setVehicles(object);
                                // Show the dialog and wait until the user closes it
                                dialogStage.showAndWait();
                                isOkClicked = controller.isOkClicked();
                                break;
                            }
                        case "DispatchEditDialog.fxml":
                            {
                                DispatchEditDialogController controller = loader.getController();
                                controller.setDialogStage(dialogStage);
                                controller.setDispatch(object);
                                // Show the dialog and wait until the user closes it
                                dialogStage.showAndWait();
                                isOkClicked = controller.isOkClicked();
                                break;
                            }
                        case "InventoryEditDialog.fxml":
                            {
                                InventoryEditDialogController controller = loader.getController();
                                controller.setDialogStage(dialogStage);
                                controller.setInventory(object);
                                // Show the dialog and wait until the user closes it
                                dialogStage.showAndWait();
                                isOkClicked = controller.isOkClicked();
                                break;
                            }
                        default:
                            break;
                    }
			
			return isOkClicked;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * This function will show the Edit Dialog window
	 */
	public boolean showPaymentsDialog(Object object, String fxml, boolean edit) {
		boolean isOkClicked = false;
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(fxml));

			AnchorPane loginLayout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			// Create the dialog Stage.
			Stage paymentsStage = new Stage();
			paymentsStage.setTitle("Payments Dialog");
			paymentsStage.initModality(Modality.APPLICATION_MODAL);
			paymentsStage.initOwner(primaryStage);
			
			Scene scene = new Scene(loginLayout);
			scene.getStylesheets().add(Main.class.getResource("vista.css").toExternalForm());
			paymentsStage.setScene(scene); 
			
			PaymentsDialogController controller = loader.getController();
			controller.setDialogStage(paymentsStage);				// Set the stage so that we know what stage to close or needs an action
			controller.setPayments(object, edit);
			paymentsStage.showAndWait();
			
			isOkClicked = controller.isOkClicked();
			
			return isOkClicked;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean showBalanceDialog(Object object, String fxml)
	{	
		boolean isOkClicked = false;
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(fxml));

			AnchorPane loginLayout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			// Create the dialog Stage.
			Stage balanceStage = new Stage();
			balanceStage.setTitle("Balance Dialog");
			balanceStage.initModality(Modality.APPLICATION_MODAL);
			balanceStage.initOwner(primaryStage);
			
			Scene scene = new Scene(loginLayout);
			scene.getStylesheets().add(Main.class.getResource("vista.css").toExternalForm());
			balanceStage.setScene(scene); 
			
			BalanceDialogController controller = loader.getController();
			controller.setDialogStage(balanceStage);				// Set the stage so that we know what stage to close or needs an action
			controller.setBalances(object);
			balanceStage.showAndWait();
			
			isOkClicked = controller.isOkClicked();
			
			return isOkClicked;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
			
	}
	
	/*
	 * This function will show the Edit Dialog window
	 */
	public boolean showProfileDialog(Object object, String fxml) {
		boolean isOkClicked = false;
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(fxml));

			AnchorPane profileLayout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			// Create the dialog Stage.
			Stage profileStage = new Stage();
			profileStage.setTitle("Profile Form");
			profileStage.initModality(Modality.APPLICATION_MODAL);
			profileStage.initOwner(primaryStage);
			
			Scene scene = new Scene(profileLayout);
			scene.getStylesheets().add(Main.class.getResource("vista.css").toExternalForm());
			profileStage.setScene(scene); 
			
			if (fxml.equals("AdminProfileDialog.fxml")) {

				AdminProfileDialogController controller = loader.getController();
				controller.setDialogStage(profileStage);				// Set the stage so that we know what stage to close or needs an action
				controller.setProfile(object);
				profileStage.showAndWait();
				isOkClicked = controller.isOkClicked();
			} else if (fxml.equals("DriverProfileDialog.fxml")) {
				
				DriverProfileDialogController controller = loader.getController();
				controller.setDialogStage(profileStage);				// Set the stage so that we know what stage to close or needs an action
				controller.setProfile(object);
				profileStage.show();
				controller.checkBlacklisted();
				isOkClicked = controller.isOkClicked();
			}
			
			return isOkClicked;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	private static void test() {
		String dateStart = "01/14/2012 06:00:00";
		String dateStop = "01/14/2012 22:25:00";
 
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
 
		Date d1 = null;
		Date d2 = null;
 
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
 
			//in milliseconds
			long diff = d2.getTime() - d1.getTime();
 
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
 
			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");
			String totalTR = diffHours + "." + diffMinutes;
			System.out.print("TOTAL REPAIR: " + Rounding.round(Double.parseDouble(totalTR), 2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * This is the main function of the program,
	 * the program starts here using the "launch(args);"
	 * statment
	 */
	public static void main(String[] args) {
		test();
		launch(args);
	}
}