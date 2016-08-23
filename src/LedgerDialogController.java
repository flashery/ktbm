import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Drivers;
import util.DateUtil;
import util.Ledger;
import util.Name;


public class LedgerDialogController{

	private Ledger ledger;
	private Drivers drivers;
	private ObservableList<Ledger> data = FXCollections.observableArrayList();
	
	@FXML
	TextField driverField = new TextField();
	@FXML
	Label balanceTypeLbl = new Label();
	@FXML
	Button exportCsvBtn = new Button();
	@FXML
	TableView<Ledger> table = new TableView<Ledger>();
	
	private Stage dialogSage;
	
	public void setLedger(Object obj, String ledgerType, String ledgerSubType){
		drivers = (Drivers) obj;
		driverField.setText(Name.createFullName(drivers.getFirstName(), drivers.getMidName(), drivers.getLastName()));
		balanceTypeLbl.setText(ledgerSubType);
		createTable();
		createObsData();
	}
	
	@SuppressWarnings("unchecked")
	private void createTable() {
		table.setEditable(true);
		
		TableColumn<Ledger, String> dateCol = new TableColumn<>("Date");
		dateCol.setMinWidth(100);
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		TableColumn<Ledger, String> typeCol = new TableColumn<>("Type");
		typeCol.setMinWidth(100);
		typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
		
		TableColumn<Ledger, String> creditCol = new TableColumn<>("Credit");
		creditCol.setMinWidth(100);
		creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));
		
		TableColumn<Ledger, String> debitCol = new TableColumn<>("Debit");
		debitCol.setMinWidth(100);
		debitCol.setCellValueFactory(new PropertyValueFactory<>("debit"));
		
		TableColumn<Ledger, String> totalBalanceCol = new TableColumn<>("Total Balance");
		totalBalanceCol.setMinWidth(100);
		totalBalanceCol.setCellValueFactory(new PropertyValueFactory<>("totalBalance"));
		
		table.setItems(data);
		
		table.getColumns().addAll(dateCol, typeCol, creditCol,  debitCol, totalBalanceCol);
	}
	
	private void createObsData() {
		DataManipulator dataman;
		ResultSet rs = null;
		try {
			dataman = new DataManipulator();
			System.out.println(this.query());
			rs = dataman.generalQuery(this.query());
			
			while(rs.next()){
				double total = rs.getDouble("credit") - rs.getDouble("debit");
				data.add(new Ledger(DateUtil.formatDate(rs.getString("date")), 
						balanceTypeLbl.getText(), 
						rs.getDouble("debit"), 
						rs.getDouble("credit"),
						total));
			}
			
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void createCSV(){
		
	}
	
	private String query(){
		
		String query = 
                        "select drivers.first_name, drivers.mid_name, drivers.last_name, b.date, 'cashbonds' as type, b.cashbonds as credit from drivers\n" +
                        "inner join balance as b on b.driver_id = drivers.id\n" +
                        "inner join payments as p on p.driver_id = drivers.id\n" +
                        "where drivers.id = " + drivers.getId() + " group by b.date\n" +
                        "\n" +
                        "union all\n" +
                        "\n" +
                        "select drivers.first_name, drivers.mid_name, drivers.last_name, b.date, 'damages' as type, b.damages as credit from drivers\n" +
                        "inner join balance as b on b.driver_id = drivers.id\n" +
                        "inner join payments as p on p.driver_id = drivers.id\n" +
                        "where drivers.id = " + drivers.getId() +" group by b.date\n" +
                        "union all\n" +
                        "\n" +
                        "select drivers.first_name, drivers.mid_name, drivers.last_name, b.date, 'loans' as type, b.loans as credit from drivers\n" +
                        "inner join balance as b on b.driver_id = drivers.id\n" +
                        "inner join payments as p on p.driver_id = drivers.id\n" +
                        "where drivers.id = " + drivers.getId() +" group by b.date\n" +
                        "\n" +
                        "union all\n" +
                        "\n" +
                        "select drivers.first_name, drivers.mid_name, drivers.last_name, b.date, 'participations' as type, b.participations as credit from drivers\n" +
                        "inner join balance as b on b.driver_id = drivers.id\n" +
                        "inner join payments as p on p.driver_id = drivers.id\n" +
                        "where drivers.id = " + drivers.getId() +" group by b.date";
		
		return query;
		
	}

	public void setDialogStage(Stage ledgerStage) {
		dialogSage = ledgerStage;
	}

}