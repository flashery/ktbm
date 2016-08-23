
import java.io.IOException;
import java.sql.SQLException;

import util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import model.Dispatch;

/**
 * Main controller class for the entire layout.
 */
public class MainController {

    /*
	 * TODO:
	 * 1. To get the username label do like other controller using the handleOK
     */
    /**
     * Holder of a switchable vista.
     */
    @FXML
    private StackPane vistaHolder;
    @FXML
    Button dispatchBtn = new Button();
    @FXML
    Button summaryBtn = new Button();
    @FXML
    Button paymentsBtn = new Button();
    @FXML
    Button driversBtn = new Button();
    @FXML
    Button vehiclesBtn = new Button();
    @FXML
    Button inventoryBtn = new Button();
    @FXML
    Button adminsBtn = new Button();
    @FXML
    Button profileBtn = new Button();
    @FXML
    public Label userLabel = new Label();

    Dispatch dispatch;
    Main main;

    // Set last click button menu
    Button lastBtn = dispatchBtn;
    LoginController login = new LoginController();

    private SimpleStringProperty currentUser;

    @FXML
    public void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        setLabelText(Constants.USERNAME);
    }

    public void initLabel() {
        login.setLabel(this);
    }

    public void setLabelText(String string) {
        // TODO Auto-generated method stub
        System.out.println("MainController");
        //userLabel.setText(string);
    }

    /**
     * Replaces the vista displayed in the vista holder with a new vista.
     *
     * @param node the vista node to be swapped in.
     */
    public void setVista(Node node) {
        vistaHolder.getChildren().setAll(node);
    }

    @FXML
    void setPane(ActionEvent event) {

        Button newBtn = (Button) event.getSource();
        if (event.getSource().equals(dispatchBtn)) {

            lastBtn.getStyleClass().remove("selected-menu");
            newBtn.getStyleClass().add("selected-menu");
            lastBtn = (Button) event.getSource();

            VistaNavigator.loadVista(VistaNavigator.VISTAS[1]);

        } else if (event.getSource().equals(summaryBtn)) {

            lastBtn.getStyleClass().remove("selected-menu");
            newBtn.getStyleClass().add("selected-menu");
            lastBtn = (Button) event.getSource();

            VistaNavigator.loadVista(VistaNavigator.VISTAS[2]);
        } else if (event.getSource().equals(paymentsBtn)) {

            lastBtn.getStyleClass().remove("selected-menu");
            newBtn.getStyleClass().add("selected-menu");
            lastBtn = (Button) event.getSource();

            VistaNavigator.loadVista(VistaNavigator.VISTAS[3]);
        } else if (event.getSource().equals(driversBtn)) {

            lastBtn.getStyleClass().remove("selected-menu");
            newBtn.getStyleClass().add("selected-menu");
            lastBtn = (Button) event.getSource();

            VistaNavigator.loadVista(VistaNavigator.VISTAS[4]);

        } else if (event.getSource().equals(vehiclesBtn)) {

            lastBtn.getStyleClass().remove("selected-menu");
            newBtn.getStyleClass().add("selected-menu");
            lastBtn = (Button) event.getSource();

            VistaNavigator.loadVista(VistaNavigator.VISTAS[5]);

        } else if (event.getSource().equals(inventoryBtn)) {

            lastBtn.getStyleClass().remove("selected-menu");
            newBtn.getStyleClass().add("selected-menu");
            lastBtn = (Button) event.getSource();

            VistaNavigator.loadVista(VistaNavigator.VISTAS[6]);

        } else if (event.getSource().equals(adminsBtn)) {

            lastBtn.getStyleClass().remove("selected-menu");
            newBtn.getStyleClass().add("selected-menu");
            lastBtn = (Button) event.getSource();

            VistaNavigator.loadVista(VistaNavigator.VISTAS[7]);
        } else if (event.getSource().equals(profileBtn)) {

            lastBtn.getStyleClass().remove("selected-menu");
            newBtn.getStyleClass().add("selected-menu");
            lastBtn = (Button) event.getSource();

            VistaNavigator.loadVista(VistaNavigator.VISTAS[8]);
        } else if (event.getSource().equals(adminsBtn)) {

            lastBtn.getStyleClass().remove("selected-menu");
            newBtn.getStyleClass().add("selected-menu");
            lastBtn = (Button) event.getSource();

            VistaNavigator.loadVista(VistaNavigator.VISTAS[9]);
        }
    }

    // For Dispatch table filter
    public void showDispatch(Object object) {
        this.dispatch = (Dispatch) object;
    }

    public Dispatch getsDispatch() {
        return this.dispatch;
    }

    @FXML
    public void showUserListMenu() {

    }

    @FXML
    public void doLogout() {
        //Constants.USERNAME = "NONE";
        //Constants.USERTYPE = "NONE";

        //this.setUserLabel(Constants.USERNAME);
        main = new Main();
        try {
            main.close();
            main.loadLoginScene();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String createFullName(String firstName, String midName, String lastName) {
        // Create middle initial
        String midInit = midName.substring(0, 1) + ".";
        // Concatenate the driver's name
        String fullName = firstName + " " + midInit + " " + lastName;

        return fullName;
    }

    public boolean today(String date) {
        //return DateUtil.newDateNormal() == date ? true : false;
        if (DateUtil.newDateNormal() == date) {
            return true;
        } else {
            return false;
        }

    }
}
