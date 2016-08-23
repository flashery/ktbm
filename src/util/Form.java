package util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;

public class Form {
	public static void dialog(AlertType type, String title, String header, String message) {

		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public static boolean dialogOkNo(String header, String message) {
		boolean answer = false;
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(header);
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		   answer = true;
		} else {
			answer = false;
		}
		
		return answer;
	}
	public static void setDatePicker(DatePicker datePick, Object obj) {
		

	}
}
