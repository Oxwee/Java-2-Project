package College.app;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

/**
 * Created by S.Anthony on 2016-11-30.
 */
public class ValidationUtil {

    private static final String LETTERS_PATTERN = "^[a-zA-Z]*$";
    private static final String NUMBERS_PATTERN = "^[0-9]*$";
    private static final String ADDRESS_PATTERN = "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";

    private static boolean valid = true;

    public static boolean getIsValid() {
        return valid;
    }

    public static void setIsValid(boolean isValid) {
        valid = isValid;
    }

    /**
     * Checks if String is empty or contains only letters.
     * @param field
     * @return
     */
    protected static boolean isNameValid(TextField field) {
        String errorMessage = "";
        boolean isValid = true;

        if(field == null || field.getText().trim().isEmpty()) {
            isValid = false;
            errorMessage += "\nName is empty.\nPlease enter student name.";
        } else if(field != null && !field.getText().matches(LETTERS_PATTERN)) {
            isValid = false;
            errorMessage += "\nStudent name (" + field.getText() + ") contains invalid symbols.\n\nPlease use ONLY letters.";
        }

        getAlert(errorMessage);
        setIsValid(isValid);
        return isValid;
    }

    /**
     * Checks if String is emty or contains only numbers.
     * @param field
     * @return
     */
    protected static boolean isNumberValid(TextField field) {
        String errorMessage = "";
        boolean isValid = true;
        int num;

        if(field == null || field.getText().trim().isEmpty()) {
            isValid = false;
            errorMessage += "\nAge field is empty.\nPlease enter student age.";
        } else if(field != null && !field.getText().matches(NUMBERS_PATTERN)) {
            isValid = false;
            errorMessage += "\nAge contains invalid symbols.\nPlease use only numbers.";
        }else if(Integer.parseInt(field.getText()) < 1 || Integer.parseInt(field.getText()) > 99) {
            isValid = false;
            errorMessage += "\nAge can't be smaller then 0 and greater then 99.";
        }

        getAlert(errorMessage);
        setIsValid(isValid);
        return isValid;
    }

    /**
     * Checks if student ID has 9 digits. If not, alert with a propert
     * error message will popup.
     * @param field
     * @return
     */
    protected static boolean isStudentIdValid(TextField field) {
        String errorMessage = "";
        boolean isValid = true;

        if(field == null || field.getText().trim().isEmpty()) {
            isValid = false;
            errorMessage += "\nStudent ID can't be empty.\nPlease enter student ID (ex. 999999999)";
        } else if(field != null && !field.getText().matches(NUMBERS_PATTERN)) {
            isValid = false;
            errorMessage += "\nStudent ID contains invalid symbols, or the following format is not correct. (" + field.getText() + ").\nPlease use only numbers and proper format (ex. 999999999).";
        }

        if(field.getText().length() != 9) {
            isValid = false;
            errorMessage += "\nStudent ID should have only 9 digits.\nPlease, use a proper format (ex. 999999999).";
        }

        getAlert(errorMessage);
        setIsValid(isValid);
        return isValid;
    }

    /**
     * Checks if the user enter 'male' or 'female'.
     * @param field
     * @return
     */
    protected static boolean isStudentGenderValid(TextField field) {
        String errorMessage = "";
        boolean isValid = true;

        if(field == null || field.getText().trim().isEmpty()) {
            isValid = false;
            errorMessage += "\nStudent gender is empty.\nPlease fill in the following field.";
        } else if(field != null && !field.getText().toLowerCase().matches("male") && !field.getText().toLowerCase().matches("female")) {
            isValid = false;
            errorMessage += "\nStudent gender has invalid input.\nPlease use 'male' or 'female' as a proper input.";
        }

        getAlert(errorMessage);
        setIsValid(isValid);
        return isValid;
    }

    /**
     * Checks if Student address follow the pattern ### Street.
     * If not, alarm will popup with the specific error message.
     * @param field
     * @return
     */
    protected static boolean isStudentAddressValid(TextField field) {
        String errorMessage = "";
        boolean isValid = true;

        if(field == null || field.getText().trim().isEmpty()) {
            isValid = false;
            errorMessage += "Address field is empty.\nPlease fill in the address field.";
        } else if(field != null && !field.getText().matches(ADDRESS_PATTERN)) {
            isValid = false;
            errorMessage += "Address contains a bad input.\nHere is an example of the input: 123 Street\n\nPlease follow the format.";
        }

        getAlert(errorMessage);
        setIsValid(isValid);
        return isValid;
    }

    /**
     * Returns an alert if validation failed.
     * @param value
     */
    public static void getAlert(String value) {
        TextField field = new TextField();

        if (value.length() > 0) {
            boolean isValid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText(value);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK) {
                alert.close();
                field.clear();
                field.requestFocus();
            }
        }
    }

}
