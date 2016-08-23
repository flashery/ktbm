
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import com.sun.javafx.scene.control.skin.ProgressBarSkin;

import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressIndicator;
import model.Config;
import model.Dispatch;
import model.Drivers;
import model.Inventory;
import model.Payments;
import util.DateUtil;
import util.Form;
import util.Name;

public class DataManipulator {

    public final String CONFIG_FILE = "config.xml";

    private Connection connection = null;
    private Config config = new Config();

    public DataManipulator() throws ClassNotFoundException, java.sql.SQLException, InstantiationException, IllegalAccessException {

        //StaXParser read = new StaXParser();
        //List<Config> readConfig = read.readConfig(CONFIG_FILE);	// Get the settings configuration
		/*
         String connectionURL 	= config.getConnectionUrl() + config.getDatabaseName();
         String user 			= config.getUsername();
         String password 		= config.getPassword();
         */
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //connection = DriverManager.getConnection("jdbc:mysql://MYSQL5009.myWindowsHosting.com:3306/db_9ceff8_ktbm", "9ceff8_ktbm", "tEafjbBaBKtL5hEW");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ktbm", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }
    }

    public ResultSet generalQuery(String query) {
        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.createStatement();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        try {
            rs = statement.executeQuery(query);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        return rs;

    }

    public boolean generalUpdate(String query) {
        Statement statement = null;
        int result = 0;
        boolean success = false;

        try {
            statement = connection.createStatement();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        try {

            result = statement.executeUpdate(query);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        } finally {

            if (result == 1) {
                success = true;
            } else {
                success = false;
            }
        }

        return success;
    }

    /**
     * ***************************************************************
     * ============================ SELECT ALL =========================
     * ***************************************************************
     */
    public ResultSet getAllData(String table_name) {

        Statement statement = null;
        ResultSet rs = null;
        if (!table_name.equals("")) {
            // initializing the query statement
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }

            // executing query to database
            try {

                rs = statement.executeQuery("SELECT * FROM " + table_name);

            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }

        }

        return rs;
    }

    /**
     * ***************************************************************
     * ================= Get data by column or by row ==================
     * ***************************************************************
     */
    protected ResultSet getByColumn(String table_name, String columnNames[], int id) {
        Statement statement = null;
        ResultSet rs = null;
        String result = "";
        if (!table_name.equals("")) {
            // initializing the query statement
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }

            // executing query to database
            try {

                String query = "";

                for (int i = 0; i < columnNames.length; i++) {

                    if (i != (columnNames.length - 1)) {
                        query += columnNames[i] + ", ";
                    } else {
                        query += columnNames[i];
                    }

                }

                rs = statement.executeQuery("SELECT " + query + " FROM " + table_name + " WHERE id = " + id + "");

            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }
        }

        return rs;
    }

    public ResultSet innerJoin(String table_name, String[] columnNames, String[] joins) {
        Statement statement = null;
        ResultSet rs = null;

        if (!table_name.equals("")) {
            // initializing the query statement
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }

            // executing query to database
            try {
                // Get columns
                String query = "";

                for (int i = 0; i < columnNames.length; i++) {

                    if (i != (columnNames.length - 1)) {
                        query += columnNames[i] + ", ";
                    } else {
                        query += columnNames[i];
                    }

                }
                String join = "";
                for (int i = 0; i < joins.length; i++) {
                    if (i != (columnNames.length - 1)) {
                        join += "INNER JOIN " + joins[i] + " ";
                    } else {

                    }
                }
                rs = statement.executeQuery("SELECT " + query + " FROM " + table_name + " " + join);

            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }
        }
        return rs;
    }

    protected ResultSet leftJoin(String table_name, String[] columnNames, String[] joins) {
        Statement statement = null;
        ResultSet rs = null;

        if (!table_name.equals("")) {
            // initializing the query statement
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }

            // executing query to database
            try {
                // Get columns
                String query = "";

                for (int i = 0; i < columnNames.length; i++) {

                    if (i != (columnNames.length - 1)) {
                        query += columnNames[i] + ", ";
                    } else {
                        query += columnNames[i];
                    }

                }

                String join = "";
                for (int i = 0; i < joins.length; i++) {
                    join += "LEFT JOIN " + joins[i] + " ";
                }

                rs = statement.executeQuery("SELECT " + query + " FROM " + table_name + " " + join + "");

            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }
        }
        return rs;
    }

    /**
     * ***************************************************************
     * ================= DELETE DATA ON DATABASE =====================
     * ***************************************************************
     */
    public void deleteData(String table_name, String username, int id) {
        Statement statement = null;

        // initializing the query statement
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        if (!username.equals("")) {
            if (validateData(table_name, username)) {

                // executing query to database
                try {
                    // If the amount are equal or greater than the current amount from the database delete it
                    statement.executeUpdate("DELETE FROM " + table_name + " WHERE username = \"" + username + "\"");
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                    Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
                }
            } else {
                Form.dialog(AlertType.WARNING, "Failed", null, username + " is not found in our Database list");
            }
        } else if (id != 0) {
            // executing query to database
            try {

                // If the amount are equal or greater than the current amount from the database delete it
                statement.executeUpdate("DELETE FROM " + table_name + " WHERE id = \"" + id + "\"");

                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }
        } else {
            Form.dialog(AlertType.WARNING, "Failed", null, "No valid data to delete!");
        }
    }


    /* ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     */
    /**
     * ***************************************************************
     * ============== UPDATE ADMINS DATA ON THE DATABASE =============
     * ***************************************************************
     */
    public void updateAdminData(String table_name, String username, String password, String first_name, String mid_name, String last_name, String email,
            String bDay, String bPlace, int age, String prevComp, String contactNum, String maritalStat, String spouse, String referral,
            String user_type, String employmentStatus) {
        if (validateData(table_name, username)) {
            Statement statement = null;

            // initializing the query statement
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }

            int empStat = employmentStatus == "inactive" ? 0 : 1;

            // executing query to database
            try {

                statement.executeUpdate("UPDATE " + table_name + " SET "
                        + "username = 			\"" + username + "\", "
                        + "password = 			\"" + password + "\", "
                        + "first_name = 		\"" + first_name + "\", "
                        + "mid_name = 			\"" + mid_name + "\", "
                        + "last_name = 			\"" + last_name + "\", "
                        + "email = 			\"" + email + "\", "
                        + "birth_date = 		\"" + bDay + "\", "
                        + "birth_place = 		\"" + bPlace + "\", "
                        + "age = 			\"" + age + "\", "
                        + "prev_company = 		\"" + prevComp + "\", "
                        + "contact_number = 		\"" + contactNum + "\", "
                        + "marital_status = 		\"" + maritalStat + "\", "
                        + "spouse = 			\"" + spouse + "\", "
                        + "referral = 			\"" + referral + "\", "
                        + "user_type = 			\"" + user_type + "\", "
                        + "employment_status =          " + empStat + " "
                        + "WHERE username = 		\"" + username + "\"");
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }
        } else {
            Form.dialog(AlertType.WARNING, "Failed", null, username + " is not found in our Database list");
        }

    }

    /**
     * ***************************************************************
     * ============== INSERT ADMIN DATA ON THE DATABASE ==============
     * ***************************************************************
     */
    public void insertAdminData(String table_name, String username, String password, String first_name, String mid_name, String last_name, String email,
            String bDay, String bPlace, int age, String prevComp, String contactNum, String maritalStat, String spouse, String referral,
            String user_type, String employmentStatus) {
        if (!validateData(table_name, username)) {
            Statement statement = null;

            // initializing the query statement
            try {

                statement = connection.createStatement();

            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }

            int empStat = employmentStatus == "inactive" ? 0 : 1;

            // executing query to database
            try {

                statement.executeUpdate("INSERT INTO " + table_name + " (username, password, profile_picture, first_name, mid_name, last_name, email, "
                        + "birth_date, birth_place, age, prev_company, contact_number, marital_status, spouse, referral, user_type, "
                        + "date_joined, employment_status) "
                        + "VALUES (\"" + username + "\", \"" + password + "\", \"" + Constants.DEFAULT_PROFILEPIC + "\", \"" + first_name + "\", "
                        + "\"" + mid_name + "\", " + "\"" + last_name + "\", \"" + email + "\", \"" + bDay + "\", \"" + bPlace + "\" "
                        + ", " + age + ", \"" + prevComp + "\", \"" + contactNum + "\", \"" + maritalStat + "\", \"" + spouse + "\""
                        + ", \"" + referral + "\", \"" + user_type + "\", now(), " + empStat + ")");
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
                Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
            }
        } else {
            String message = "Sorry that data is already existed \n"
                    + username + " is already listed in our Database list";
            Form.dialog(AlertType.WARNING, "Failed", null, message);
        }
    }

    /* ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     */
    /**
     * ***************************************************************
     * ============= UPDATE DRIVERS DATA ON THE DATABASE =============
     * ***************************************************************
     */
    public void updateDriversData(String table_name, Drivers drivers) {
        Statement statement = null;

        // initializing the query statement
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        // Perform switch to get the int value of employment status
        int empStat = 0;
        switch (drivers.getEmploymentStatus()) {
            case "inactive":
                empStat = 0;
                break;
            case "active":
                empStat = 1;
                break;
            case "blacklisted":
                empStat = 2;
                break;
            default:
                break;
        }

        int dispStat = drivers.getDispatchStatus() == "off duty" ? 0 : 1;

        // executing query to database
        try {

            statement.executeUpdate("UPDATE " + table_name + " SET "
                    + "first_name = 			\"" + drivers.getFirstName() + "\", "
                    + "mid_name = 			\"" + drivers.getMidName() + "\", "
                    + "last_name = 			\"" + drivers.getLastName() + "\", "
                    + "less_rate = 			\"" + drivers.getLessRate() + "\", "
                    + "birth_date = 			\"" + DateUtil.reformatDate(drivers.getBirthDate()) + "\", "
                    + "birth_place = 		\"" + drivers.getBirthPlace() + "\", "
                    + "age = 				\"" + drivers.getAge() + "\", "
                    + "prev_company = 		\"" + drivers.getPrevCompany() + "\", "
                    + "contact_number = 		\"" + drivers.getContactNumber() + "\", "
                    + "marital_status = 		\"" + drivers.getMaritalStatus() + "\", "
                    + "spouse = 				\"" + drivers.getSpouse() + "\", "
                    + "referral = 			\"" + drivers.getReferral() + "\", "
                    + "address = 			\"" + drivers.getAddress() + "\", "
                    + "license_num = 		\"" + drivers.getLicenseNum() + "\", "
                    + "license_expire = 		\"" + DateUtil.reformatDate(drivers.getLicenseExp()) + "\", "
                    + "driver_type = 		\"" + drivers.getDriverType() + "\", "
                    + "employment_status = 	" + empStat + ","
                    + "dispatch_status = 	" + dispStat + " "
                    + "WHERE id = 			" + drivers.getId() + "");
            connection.close();

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }
    }

    /**
     * ***************************************************************
     * ============== INSERT DRIVERS DATA ON THE DATABASE ============
     * ***************************************************************
     */
    public void insertDriversData(String table_name, Drivers drivers) {
        Statement statement = null;

        // initializing the query statement
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        // Perform switch to get the int value of employment status
        int empStat = 0;
        switch (drivers.getEmploymentStatus()) {
            case "inactive":
                empStat = 0;
                break;
            case "active":
                empStat = 1;
                break;
            case "blacklisted":
                empStat = 2;
                break;
            default:
                break;
        }

        // executing query to database
        try {
            statement.executeUpdate("INSERT INTO " + table_name + " (first_name, mid_name, last_name, less_rate, birth_date, birth_place, "
                    + "age, prev_company, contact_number, marital_status, spouse, referral, address, license_num, license_expire, "
                    + "driver_type, employment_status, dispatch_status) "
                    + "VALUES (\"" + drivers.getFirstName() + "\", \"" + drivers.getMidName() + "\", \"" + drivers.getLastName() + "\", "
                    + "\"" + drivers.getLessRate() + "\", \"" + DateUtil.reformatDate(drivers.getBirthDate()) + "\", "
                    + "\"" + drivers.getAge() + "\", " + drivers.getAge() + ", \"" + drivers.getPrevCompany() + "\", "
                    + "\"" + drivers.getContactNumber() + "\", \"" + drivers.getMaritalStatus() + "\", \"" + drivers.getSpouse() + "\", "
                    + "\"" + drivers.getReferral() + "\", \"" + drivers.getAddress() + "\", \"" + drivers.getLicenseNum() + "\", "
                    + "\"" + DateUtil.reformatDate(drivers.getLicenseExp()) + "\", \"" + drivers.getDriverType() + "\", \"" + empStat + "\", 0)");

            connection.close();

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }
    }

    /* ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     */
    /**
     * ***************************************************************
     * ============= UPDATE VEHICLE DATA ON THE DATABASE =============
     * ***************************************************************
     */
    public void updateVehiclesData(String table_name, int id, int unitNum, String plateNum, String caseNum, String ccn,
            String chasisNum, String carBrand, int carRate, String engineNum, String plateReg, String resDate, String cpcReg, String eotReg, String status) {
        Statement statement = null;

        // initializing the query statement
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        // executing query to database
        try {
            System.out.println("UPDATE " + table_name + " SET "
                    + "unit_number = 		\"" + unitNum + "\", "
                    + "plate_number = 		\"" + plateNum + "\", "
                    + "case_number = 		\"" + caseNum + "\", "
                    + "ccn = 				\"" + ccn + "\", "
                    + "chasis_number = 		\"" + chasisNum + "\", "
                    + "brand = 			\"" + carBrand + "\", "
                    + "rate = " + carRate + ", "
                    + "plate_registration = 	\"" + plateReg + "\", "
                    + "reseal_date = 		\"" + resDate + "\", "
                    + "cpc_registration = 	\"" + cpcReg + "\", "
                    + "eot_registration = 	\"" + eotReg + "\", "
                    + "status = 				\"" + status + "\""
                    + "WHERE id = 		  	  " + id + "");

            statement.executeUpdate("UPDATE " + table_name + " SET "
                    + "unit_number = 		\"" + unitNum + "\", "
                    + "plate_number = 		\"" + plateNum + "\", "
                    + "case_number = 		\"" + caseNum + "\", "
                    + "ccn = 				\"" + ccn + "\", "
                    + "chasis_number = 		\"" + chasisNum + "\", "
                    + "brand = 			\"" + carBrand + "\", "
                    + "rate = " + carRate + ", "
                    + "plate_registration = 	\"" + plateReg + "\", "
                    + "reseal_date = 		\"" + resDate + "\", "
                    + "cpc_registration = 	\"" + cpcReg + "\", "
                    + "eot_registration = 	\"" + eotReg + "\", "
                    + "status = 				\"" + status + "\""
                    + "WHERE id = 		  	  " + id + "");

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }
    }

    /**
     * ***************************************************************
     * ============== INSERT VEHICLE DATA ON THE DATABASE ============
     * ***************************************************************
     */
    public void insertVehiclesData(String table_name, int id, int unitNum, String plateNum, String caseNum, String ccn,
            String chasisNum, String carBrand, int carRate, String engineNum, String plateReg, String resDate, String cpcReg, String eotReg, String status) {

        Statement statement = null;

        // initializing the query statement
        try {

            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // executing query to database
        try {

            statement.executeUpdate("INSERT INTO " + table_name + " (unit_number, plate_number, case_number, ccn, chasis_number, "
                    + "vehicle_specifications_id, engine_number, plate_registration, reseal_date, cpc_registration, eot_registration, status) "
                    + "VALUES (" + unitNum + ", \"" + plateNum + "\", \"" + caseNum + "\", \"" + ccn + "\", \"" + chasisNum + "\", "
                    + "(SELECT id FROM vehicle_specifications WHERE car_brand = \"" + carBrand + "\" AND car_rate = " + carRate + "), "
                    + "\"" + engineNum + "\", \"" + plateReg + "\", \"" + resDate + "\", \"" + cpcReg + "\", \"" + eotReg + "\", \"" + status + "\")");

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }
    }

    /* ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     */
    /**
     * ***************************************************************
     * ============= UPDATE DISPACTH DATA ON THE DATABASE ============ * @throws
     * ParseException
     * **************************************************************
     */
    protected void updateDispatchData(Dispatch selectedDispatch) throws ParseException {

        String table_name = Constants.DB_DISPATCH_TBL;
        int id = selectedDispatch.getId();
        String driver = selectedDispatch.getDriver();
        String vehicle = selectedDispatch.getVehicle();
        String admin = selectedDispatch.getAdmin();
        String timeOut = DateUtil.reformatStringDT(selectedDispatch.getTimeOut());
        double rate = selectedDispatch.getRate();
        String status = selectedDispatch.getStatus();
        String date = DateUtil.reformatDate(selectedDispatch.getDate());

        Statement statement = null;

        // initializing the query statement
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        // executing query to database
        try {
            // Driver ID query
            ResultSet rsDriver = statement.executeQuery("(SELECT id FROM drivers WHERE first_name = \"" + Name.getFirstName(driver) + "\" "
                    + "AND last_name = \"" + Name.getLastName(driver) + "\")");

            int driverId = 0;

            while (rsDriver.next()) {
                driverId = rsDriver.getInt("id");
            }

            // Dispatch ID query
            ResultSet rsVehicle = statement.executeQuery("(SELECT id FROM vehicles WHERE plate_number = \"" + vehicle + "\")");

            int vehicleId = 0;

            while (rsVehicle.next()) {
                vehicleId = rsVehicle.getInt("id");
            }

            // Admin ID query
            ResultSet rsAdmin = statement.executeQuery("(SELECT id FROM admins WHERE first_name = \"" + Name.getFirstName(admin) + "\" "
                    + "AND last_name = \"" + Name.getLastName(admin) + "\")");

            int adminId = 0;

            while (rsAdmin.next()) {
                adminId = rsAdmin.getInt("id");
            }

            String query = "UPDATE " + table_name + " SET "
                    + "driver_id = 	  " + driverId + ", "
                    + "vehicle_id = 	  " + vehicleId + ", "
                    + "admin_id = 	  " + adminId + ", "
                    + "time_out = 	\"" + timeOut + "\", "
                    + "vehicle_rate =  " + rate + ", "
                    + "status = 		\"" + status + "\" "
                    + "WHERE id = 	  " + id + " "
                    + "AND date = 	\"" + date + "\"";

            System.out.println(query);

            int result = statement.executeUpdate(query);

            if (result == 1) {
                // Update driver's status to on duty
                statement.executeUpdate("UPDATE drivers SET dispatch_status = 1 WHERE id = " + driverId + "");
                // Update vehicle's status to on dispatch
                statement.executeUpdate("UPDATE vehicles SET status = \"on dispatch\" WHERE id = " + vehicleId + "");
            } else {
                Form.dialog(AlertType.WARNING, "Failed", null, "Failed to update vehicle and driver status");
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

    }

    /**
     * ***************************************************************
     * ============= INSERT DISPACTH DATA ON THE DATABASE ============
     * ***************************************************************
     */
    public void insertDispatchData(ProgressIndicator progInd, String table_name, String driver, String vehicle, String admin, String timeOut,
            double rate, String status, String date) {

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                progInd.setOpacity(1);
            }
        });
        Statement statement = null;

        // initializing the query statement
        try {

            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        // executing query to database
        try {
            // Driver ID query
            ResultSet rsDriver = statement.executeQuery("(SELECT id FROM drivers WHERE first_name = \"" + Name.getFirstName(driver) + "\" "
                    + "AND last_name = \"" + Name.getLastName(driver) + "\")");

            int driverId = 0;

            while (rsDriver.next()) {
                driverId = rsDriver.getInt("id");
            }

            // Dispatch ID query
            ResultSet rsVehicle = statement.executeQuery("(SELECT id FROM vehicles WHERE plate_number = \"" + vehicle + "\")");

            int vehicleId = 0;

            while (rsVehicle.next()) {
                vehicleId = rsVehicle.getInt("id");
            }

            // Admin ID query
            ResultSet rsAdmin = statement.executeQuery("(SELECT id FROM admins WHERE first_name = \"" + Name.getFirstName(admin) + "\" "
                    + "AND last_name = \"" + Name.getLastName(admin) + "\")");

            int adminId = 0;

            while (rsAdmin.next()) {
                adminId = rsAdmin.getInt("id");
            }

            String query = "INSERT INTO " + table_name + " (driver_id, vehicle_id, admin_id, time_out, vehicle_rate, status, date) "
                    + "VALUES (" + driverId + ", "
                    + "" + vehicleId + ", "
                    + "" + adminId + ", "
                    + "\"" + timeOut + "\", "
                    + "" + rate + ", "
                    + "\"" + status + "\", \"" + date + "\")";

            int result = statement.executeUpdate(query);
            if (result == 1) {
                // Update driver's status to on duty
                statement.executeUpdate("UPDATE drivers SET dispatch_status = 1 WHERE id = " + driverId + "");
                // Update vehicle's status to on dispatch
                statement.executeUpdate("UPDATE vehicles SET status = \"on dispatch\" WHERE id = " + vehicleId + "");
            } else {
                Form.dialog(AlertType.WARNING, "Failed", null, "Failed to update vehicle and driver status");
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

    }

    /* ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     */
    /**
     * ***************************************************************
     * ============ UPDATE PAYMENTS DATA ON THE DATABASE =============
     * ***************************************************************
     */
    protected void updatePaymentsData(String table_name, Payments payments) {

        Statement statement = null;

        // initializing the query statement
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        // executing query to database
        try {
            // Driver ID query
            ResultSet rsDriver = statement.executeQuery("(SELECT id FROM drivers "
                    + "WHERE first_name = \"" + Name.getFirstName(payments.getDriver()) + "\" "
                    + "AND last_name = \"" + Name.getLastName(payments.getDriver()) + "\")");

            int driverId = 0;

            while (rsDriver.next()) {
                driverId = rsDriver.getInt("id");
            }

            // Dispatch ID query
            ResultSet rsDispatch = statement.executeQuery("(SELECT id FROM dispatch WHERE driver_id = " + driverId + ")");

            int dispatchId = 0;

            while (rsDispatch.next()) {
                dispatchId = rsDispatch.getInt("id");
            }

            // Vehicle ID query
            ResultSet rsVehicle = statement.executeQuery("(SELECT id FROM vehicles WHERE plate_number = \"" + payments.getVehicle() + "\")");

            int vehicleId = 0;

            while (rsVehicle.next()) {
                vehicleId = rsVehicle.getInt("id");
            }
            ResultSet rsAdmin = statement.executeQuery("(SELECT id FROM admins WHERE first_name = \"" + Name.getFirstName(payments.getAdminUser()) + "\" "
                    + "AND last_name = \"" + Name.getLastName(payments.getAdminUser()) + "\")");

            int adminId = 0;

            while (rsAdmin.next()) {
                adminId = rsAdmin.getInt("id");
            }

             String tStart = payments.getTimeRepairStart() == null ? null :  "\"" + payments.getTimeRepairStart() +"\"";
             String tEnd = payments.getTimeRepairEnd()== null ? null : "\"" + payments.getTimeRepairEnd() + "\"";

            System.out.println("UPDATE " + table_name + " SET "
                    + "driver_id = " + driverId + ", "
                    + "vehicle_id = " + vehicleId + ", "
                    + "admins_id = " + adminId + ", "
                    + "boundaries = " + payments.getBoundaries() + ", "
                    + "cashbond_payments = " + payments.getCashbondPayments() + ", "
                    + "shortage_payments = " + payments.getShortagePayments() + ", "
                    + "damage_payments = " + payments.getDamagePayments() + ", "
                    + "loans = " + payments.getLoans() + ", "
                    + "shortages = " + payments.getShortages() + ", "
                    + "less_sunday = " + payments.getLessSunday() + ", "
                    + "less_holiday = " + payments.getLessHoliday() + ", "
                    + "time_repair_start = " + tStart + ", "
                    + "time_repair_end = " + tEnd + ", "
                    + "total_time_repair = " + payments.getTotalTimeRepair() + ", "
                    + "repair_cost = " + payments.getRepairCost() + ", "
                    + "kilometers_run = " + payments.getKilometersRun() + ", "
                    + "gas_liters = " + payments.getGasLiters() + ", "
                    + "remarks =  \"" + payments.getRemarks() + "\", "
                    + "total_payments = " + payments.getTotal() + ", "
                    + "date =  \"" + payments.getDate() + "\" "
                    + "WHERE id = " + payments.getId() + " "
                    + "AND date = \"" + payments.getDate() + "\"");
            statement.executeUpdate("UPDATE " + table_name + " SET "
                    + "driver_id = " + driverId + ", "
                    + "vehicle_id = " + vehicleId + ", "
                    + "admins_id = " + adminId + ", "
                    + "boundaries = " + payments.getBoundaries() + ", "
                    + "cashbond_payments = " + payments.getCashbondPayments() + ", "
                    + "shortage_payments = " + payments.getShortagePayments() + ", "
                    + "damage_payments = " + payments.getDamagePayments() + ", "
                    + "loans = " + payments.getLoans() + ", "
                    + "shortages = " + payments.getShortages() + ", "
                    + "less_sunday = " + payments.getLessSunday() + ", "
                    + "less_holiday = " + payments.getLessHoliday() + ", "
                    + "time_repair_start = " + tStart + ", "
                    + "time_repair_end = " + tEnd + ", "
                    + "total_time_repair = " + payments.getTotalTimeRepair() + ", "
                    + "repair_cost = " + payments.getRepairCost() + ", "
                    + "kilometers_run = " + payments.getKilometersRun() + ", "
                    + "gas_liters = " + payments.getGasLiters() + ", "
                    + "remarks =  \"" + payments.getRemarks() + "\", "
                    + "total_payments = " + payments.getTotal() + ", "
                    + "date =  \"" + payments.getDate() + "\" "
                    + "WHERE id = " + payments.getId() + " "
                    + "AND date = \"" + payments.getDate() + "\"");

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

    }

    /**
     * ***************************************************************
     * ============ INSERT PAYMENTS DATA ON THE DATABASE =============
     * ***************************************************************
     */
    public void insertPaymentsData(String table_name, String driver, String vehicle, double rate, String admin, double boundaries, double cashbondPayments,
            double shortagePayments, double damagePayments, double loans, double appSaving, double shortages, double lessHoliday,
            double lessSunday, String timeRepairStart, String timeRepairEnd, double totalTimeRepair, double repairCost, int grabTaxi,
            double kilometersRun, double gasLiters, String remarks, double total, String date) {

        Statement statement = null;

        // initializing the query statement
        try {

            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }
        // executing query to database
        try {

            // Driver ID query
            ResultSet rsDriver = statement.executeQuery("(SELECT id FROM drivers WHERE first_name = \"" + Name.getFirstName(driver) + "\" AND last_name = \"" + Name.getLastName(driver) + "\")");

            int driverId = 0;

            while (rsDriver.next()) {
                driverId = rsDriver.getInt("id");
            }

            // Dispatch ID query
            ResultSet rsDispatch = statement.executeQuery("(SELECT id, date FROM dispatch WHERE driver_id = " + driverId + ")");

            int dispatchId = 0;

            while (rsDispatch.next()) {
                dispatchId = rsDispatch.getInt("id");
                date = rsDispatch.getString("date");
            }

            // Vehicle ID query
            ResultSet rsVehicle = statement.executeQuery("(SELECT id FROM vehicles WHERE plate_number = \"" + vehicle + "\")");

            int vehicleId = 0;

            while (rsVehicle.next()) {
                vehicleId = rsVehicle.getInt("id");
            }

            System.out.println(date);
            ResultSet rsAdmin = statement.executeQuery("(SELECT id FROM admins WHERE first_name = \"" + Name.getFirstName(admin) + "\" "
                    + "AND last_name = \"" + Name.getLastName(admin) + "\")");

            int adminId = 0;

            while (rsAdmin.next()) {
                adminId = rsAdmin.getInt("id");
            }
            String tStart = timeRepairStart == null ? null : "\"" + timeRepairStart + "\"";
            String tEnd = timeRepairEnd == null ? null : "\"" + timeRepairEnd + "\"";

            // Insert payments data into database
            statement.executeUpdate("INSERT INTO " + table_name + " (driver_id, vehicle_id, vehicle_specifications_id, admins_id,"
                    + "boundaries, cashbond_payments, shortage_payments, damage_payments, apprehension_saving, loans, penalties, shortages,"
                    + "less_sunday, less_holiday, time_repair_start, time_repair_end, total_time_repair, repair_cost, "
                    + "grab_taxi, kilometers_run, gas_liters, remarks, total_payments, date) "
                    + "VALUES ("
                    + "" + driverId + ", "
                    + "" + vehicleId + ", "
                    + "(SELECT id FROM vehicle_specifications WHERE car_rate = " + rate + "), "
                    + "" + adminId + ", "
                    + "" + boundaries + ", " + cashbondPayments + ", " + shortagePayments + ", " + damagePayments + ", "
                    + "" + appSaving + ", " + loans + ", " + 0 + ", " + shortages + ", "
                    + "" + lessSunday + ", " + "" + lessHoliday + ", "
                    + "" + tStart + ", "
                    + "" + tEnd + ", "
                    + "" + totalTimeRepair + ", " + "" + repairCost + ", " + grabTaxi + ", "
                    + "" + kilometersRun + ", " + gasLiters + ", \"" + remarks + "\", " + total + ", "
                    + "\"" + DateUtil.newDateNormal() + "\")");

            // Update dispatch status to dispatch
            int result = statement.executeUpdate("UPDATE dispatch SET status = \"off dispatch\" WHERE driver_id = " + driverId + " AND id = " + dispatchId + " "
                    + "AND date = \"" + date + "\"");
            if (result == 1) {
                //Form.dialog(AlertType.WARNING,  "Failed", null, e.getMessage());
            } else {
                Form.dialog(AlertType.WARNING, "Failed", null, "Dispatch not set");
            }
            // Update driver's dispatch status
            statement.executeUpdate("UPDATE drivers SET dispatch_status = 0 WHERE id = " + driverId + "");
            if (result == 1) {
                //JOptionPane.showMessageDialog(null, "Drivers set to off duty", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Form.dialog(AlertType.WARNING, "Failed", null, "Drivers not set");
            }
            // Update vehicle's dispatch status
            statement.executeUpdate("UPDATE vehicles SET status = \"available\" WHERE id = " + vehicleId + "");
            if (result == 1) {
                //JOptionPane.showMessageDialog(null, "Vehicles set to available", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Form.dialog(AlertType.WARNING, "Failed", null, "Vehicles not set");
            }
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());

        }

    }

    /* ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     */
    /**
     * ***************************************************************
     * ============ INSERT NEW BALANCE DATA ON THE DATABASE ==========
     * ***************************************************************
     */
    public void insertNewBalance(int driverId, int adminId, double cashbonds, double damages, double loans,
            double participations, double penalties, double shortages, double apprehensionS, String date) {
        Statement statement = null;

        // initializing the query statement
        try {

            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        // executing query to database
        try {

            // Check if we already have balance record for this driver
            // Compare driver id from the balance driver_id
            double total = cashbonds + damages + loans + participations + penalties + shortages;

            // Insert new data to balance table
            statement.executeUpdate("INSERT INTO balance (driver_id, admin_id, cashbonds, damages, "
                    + "loans, participations, penalties, shortages, apprehension_saving, total_balance, date) "
                    + "VALUES ("
                    + driverId + ", "
                    + adminId + ", "
                    + cashbonds + ", " // New driver default cashbond
                    + damages + ", "
                    + loans + ", "
                    + participations + ", "
                    + penalties + ", "
                    + shortages + ", "
                    + apprehensionS + ", "
                    + total + ", \""
                    + date + "\")");

            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());

        }
    }

    /* ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     * ==================================================================================================================================
     */
    /**
     * ***************************************************************
     * ======== INSERT NEW TOTAL BALANCE DATA ON THE DATABASE ========
     * ***************************************************************
     */
    public void insertTotalBalance(int driverId,
            double cashbonds,
            double damages,
            double loans,
            double participations,
            double penalties,
            double shotages,
            double apprehension) {

        Statement statement = null;

        // initializing the query statement
        try {

            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        // executing query to database
        try {
            System.out.println("INSERT INTO total_balance "
                    + "driver_id, total_cashbonds, total_damages, total_loans, "
                    + "total_participations, total_penalties, total_shortages, total_apprehension_saving "
                    + "VALUES "
                    + "(" + driverId + ", " + cashbonds + ", " + damages + ", " + loans + ", "
                    + participations + ", " + penalties + ", " + shotages + ")");
            statement.executeUpdate("INSERT INTO total_balance "
                    + "(driver_id, total_cashbonds, total_damages, total_loans, "
                    + "total_participations, total_penalties, total_shortages, total_apprehension_saving) "
                    + "VALUES "
                    + "(" + driverId + ", " + cashbonds + ", " + damages + ", " + loans + ", "
                    + participations + ", " + penalties + ", " + shotages + ", " + apprehension + ")");

            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());

        }
    }

    public void updateTotalBalance(int driverId,
            double cashbonds,
            double damages,
            double loans,
            double participations,
            double penalties,
            double shotages,
            double apprehension) {
        Statement statement = null;

        // initializing the query statement
        try {

            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        // executing query to database
        try {
            System.out.println("UPDATE total_balance SET "
                    + "total_cashbonds = " + cashbonds + ", "
                    + "total_damages = " + damages + ", "
                    + "total_loans = " + loans + ", "
                    + "total_participations = " + participations + ", "
                    + "total_penalties = " + penalties + ", "
                    + "total_shortages = " + shotages + ", "
                    + "total_apprehension_saving = " + apprehension + ", "
                    + "WHERE driver_id = " + driverId + "");
            statement.executeUpdate("UPDATE total_balance SET "
                    + "total_cashbonds = " + cashbonds + ", "
                    + "total_damages = " + damages + ", "
                    + "total_loans = " + loans + ", "
                    + "total_participations = " + participations + ", "
                    + "total_penalties = " + penalties + ", "
                    + "total_shortages = " + shotages + ", "
                    + "total_apprehension_saving = " + apprehension + " "
                    + "WHERE driver_id = " + driverId + "");

            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());

        }

    }

    public void insertInventoryData(String tableName, Inventory inventory) {
        int driverId = 0;
        int vehicleId = 0;
        Statement statement = null;

        // initializing the query statement
        try {

            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        // executing query to database
        try {
            ResultSet rs = statement.executeQuery("SELECT id FROM " + Constants.DB_DRIVER_TBL + " "
                    + "WHERE first_name = \"" + Name.getFirstName(inventory.getDriver()) + "\" "
                    + "AND last_name = \"" + Name.getLastName(inventory.getDriver()) + "\"");
            if (rs.next()) {
                driverId = rs.getInt("id");
            }

            rs = statement.executeQuery("SELECT id FROM " + Constants.DB_VEHICLE_TBL + " "
                    + "WHERE unit_number = " + inventory.getUnitNumber() + " ");
            if (rs.next()) {
                vehicleId = rs.getInt("id");
            }

            System.out.println("INSERT INTO " + tableName + " (driver_id, vehicle_id, trip, odometer, "
                    + "pms, gas_liters, check_tires, "
                    + "check_tools, doc, remarks, date) "
                    + "VALUES (" + driverId + ", "
                    + "" + vehicleId + ", "
                    + "" + inventory.getTrip() + ", "
                    //+ "" + inventory.getOdometer() + ", "
                    + "" + inventory.getPms() + ", "
                    + "" + inventory.getGasLtrs() + ", "
                    + "" + inventory.getCheckTires() + ", "
                    + "" + inventory.getCheckTools() + ", "
                    + "" + inventory.getDoc() + ", "
                    + "\"" + inventory.getRemarks() + "\", "
                    + "\"" + inventory.getDate() + "\")");
            statement.executeUpdate("INSERT INTO " + tableName + " (driver_id, vehicle_id, trip, odometer, "
                    + "pms, gas_liters, check_tires, "
                    + "check_tools, doc, remarks, date) "
                    + "VALUES (" + driverId + ", "
                    + "" + vehicleId + ", "
                    + "" + inventory.getTrip() + ", "
                    //+ "" + inventory.getOdometer() + ", "
                    + "" + inventory.getPms() + ", "
                    + "" + inventory.getGasLtrs() + ", "
                    + "" + inventory.getCheckTires() + ", "
                    + "" + inventory.getCheckTools() + ", "
                    + "" + inventory.getDoc() + ", "
                    + "\"" + inventory.getRemarks() + "\", "
                    + "\"" + inventory.getDate() + "\")");

            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();

            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());

        }
    }

    private boolean validateData(String table_name, String username) {
        ResultSet rs = null;
        boolean result = false;

        rs = this.getAllData(table_name);

        try {
            while (rs.next()) {
                if (rs.getString("username").equals(username)) {
                    result = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Form.dialog(AlertType.WARNING, "Failed", null, e.getMessage());
        }

        return result;
    }
}
