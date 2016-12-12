package College.app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by S.Anthony on 2016-11-29.
 */
public class StudentListController implements Initializable {

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> firstNameColumn;
    @FXML private TableColumn<Student, String> lastNameColumn;
    @FXML private TableColumn<Student, String> studentIdColumn;
    @FXML private TableColumn<Student, String> studentAgeColumn;
    @FXML private TableColumn<Student, String> studentGenderColumn;
    @FXML private TableColumn<Student, String> studentAddressColumn;
    @FXML private TableColumn<Student, Number> studentRowColumn;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField studentIdField;
    @FXML private TextField studentAgeField;
    @FXML private TextField studentGenderField;
    @FXML private TextField studentAddressField;
    @FXML private TextField searchField;

    @FXML private Button addButton;

    private int rowNum = 1;
    private ObservableList<Student> studentData = FXCollections.observableArrayList();

    /**
     * Loads when FXML file has been loaded.
     * Populate the student table with values. Allows some functionality
     * such as editing and etc.
     * @param location
     * @param rb
     */
    @FXML
    public void initialize(URL location, ResourceBundle rb) {
        searchField.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                handleSearchField((String) oldValue, (String) newValue);
            }
        });

        studentTable.setEditable(true);
        firstNameColumn.setOnEditCommit(e -> firstNameColumnOnEdit(e));
        lastNameColumn.setOnEditCommit(e -> lastNameColumnOnEdit(e));
        studentIdColumn.setOnEditCommit(e -> studentIdColumnOnEdit(e));
        studentAgeColumn.setOnEditCommit(e -> studentAgeColumnOnEdit(e));
        studentGenderColumn.setOnEditCommit(e -> studentGenderColumnEdit(e));
        studentAddressColumn.setOnEditCommit(e -> studentAddressColumnEdit(e));

        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentAgeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentGenderColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentAddressColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        studentRowColumn.setCellValueFactory(cellData -> cellData.getValue().studentNumberProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        studentIdColumn.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty());
        studentAgeColumn.setCellValueFactory(cellData -> cellData.getValue().studentAgeProperty());
        studentGenderColumn.setCellValueFactory(cellData -> cellData.getValue().studentGenderProperty());
        studentAddressColumn.setCellValueFactory(cellData -> cellData.getValue().studentAddressProperty());

        studentTable.setFocusTraversable(false);
        addButton.setDisable(true);
        studentTable.setItems(readFile());
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        studentTable.setPlaceholder(new Label("No Students."));

        firstNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (firstNameField.isFocused()) {
                    addButton.setDisable(false);
                }
            }
        });

    }

    /**
     * Saves all the inputs from text fields and add it to the
     * ObservableList as a new Student. If any of the text fields
     * empty, the alert will popup.
     * @param event
     */

    @FXML
    public void handleAddButton(ActionEvent event) {
        if (studentData.size() < 100) {
            if (isInputValid(event)) {
                Student newStudent = new Student();
                newStudent.setStudentNumber(rowNum++);
                newStudent.setFirstName(firstNameField.getText());
                newStudent.setLastName(lastNameField.getText());
                newStudent.setStudentId(studentIdField.getText());
                newStudent.setStudentAge(studentAgeField.getText());
                newStudent.setStudentGender(studentGenderField.getText());
                newStudent.setStudentAddress(studentAddressField.getText());
                studentData.add(newStudent);

                System.out.println(newStudent.toString()); // for console only.

                firstNameField.clear();
                lastNameField.clear();
                studentIdField.clear();
                studentAgeField.clear();
                studentGenderField.clear();
                studentAddressField.clear();
            } // end of if(isInputValid());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            alert.setContentText("You've reached the limit of student list.");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(owner);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                alert.close();
                firstNameField.clear();
                lastNameField.clear();
                studentIdField.clear();
                studentAgeField.clear();
                studentGenderField.clear();
                studentAddressField.clear();
            } // end of if(alert.getResult());
        } // end of if(studentData.length() < 100);
    }

    /**
     * Deletes selected record from the student table. Conformation
     * alert will popup when delete button clicked. Also, if student
     * has not been selected, alert will popup and ask to choose the record.
     * @param event
     */
    @FXML
    public void handleDeleteButton(ActionEvent event) {

        int selectedIndex = studentTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            studentTable.getItems().remove(selectedIndex);
        } else if (selectedIndex != 0){
            Alert alert = new Alert(Alert.AlertType.WARNING, "No selection", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            alert.setContentText("Please select one or more student(s).");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(owner);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        }
    }

    /**
     * Clear all text fields.
     */
    @FXML
    public void handleClearButton() {
        firstNameField.clear();
        lastNameField.clear();
        studentIdField.clear();
        studentAgeField.clear();
        studentGenderField.clear();
        studentAddressField.clear();
    }

    /**
     * Filter First and Last name base on the Search Text Filed input.
     * @param oldValue
     * @param newValue
     */
    public void handleSearchField(String oldValue, String newValue) {
        ObservableList<Student> filteredList = FXCollections.observableArrayList();
        if(filteredList == null || (newValue.length() < oldValue.length()) || newValue == null) {
            studentTable.setItems(studentData);
        } else {
            newValue = newValue.toUpperCase();
            for(Student students : studentTable.getItems()) {
                String filterFirstName = students.getFirstName();
                String filterLastName = students.getLastName();
                if(filterFirstName.toUpperCase().contains(newValue) || filterLastName.toUpperCase().contains(newValue)) {
                    filteredList.add(students);
                }
            }
            studentTable.setItems(filteredList);
        }
    }

    /**
     * Checks if all the inputs were filled correctly.
     * @param event
     */
    public boolean isInputValid(ActionEvent event) {
        boolean validInput = true;

        ValidationUtil.isNameValid(firstNameField);
        ValidationUtil.isNameValid(lastNameField);
        ValidationUtil.isNumberValid(studentAgeField);
        ValidationUtil.isStudentIdValid(studentIdField);
        ValidationUtil.isStudentGenderValid(studentGenderField);
        ValidationUtil.isStudentAddressValid(studentAddressField);

        validInput = ValidationUtil.getIsValid();
        System.out.println(validInput);
        return validInput;
    }


    /**
     * Reads file from the specific path and returns it as an
     * ObservableList of students.
     * @return
     */
    private ObservableList<Student> readFile() {
        String filePath = "src/College/app/studentData.txt";
        String fileDelimiter = " # ";
        String line = "";

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while((line = br.readLine()) != null) {
                String[] field = line.split(fileDelimiter, -1);
                Student student = new Student(rowNum++, field[0],field[1],field[2],field[3],field[4],field[5]);
                studentData.add(student);
            }
            return studentData;
        } catch (FileNotFoundException ex) {
            ex.getCause().printStackTrace();
            return null;
        } catch (IOException ex) {
            ex.getCause().printStackTrace();
            return null;
        }
    }

    /**
     * Creates a new Stage for the 'Save' dialog.
     * @param event
     */
    public void handleSaveButton(ActionEvent event) {
        Stage saveStage = new Stage();
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Student List");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));

        File file = fc.showSaveDialog(saveStage);
        if(file != null) {
            saveFile(studentTable.getItems(), file);
        }
    }

    /**
     * Grab the values from studentData ObservableList and saves it
     * as a file.
     * @param studentData
     * @param file
     */
    public void saveFile(ObservableList<Student> studentData, File file) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for(Student students : studentData) {
                bw.write(students.toString());
                bw.newLine();
            }
            System.out.println(studentData.toString()); // console purposes only.
            bw.close();
        } catch(IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong!", ButtonType.OK);
            alert.setContentText("Sorry. An error has occurred.");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK) {
                alert.close();
            }
        }
    }

    /**
     * Handlers for column edits.
     * @param e
     */
    public void firstNameColumnOnEdit(Event e) {
        TableColumn.CellEditEvent<Student, String>  cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, String>) e;
        Student student = cellEditEvent.getRowValue();
        student.setFirstName(cellEditEvent.getNewValue());
    }

    public void lastNameColumnOnEdit(Event e) {
        TableColumn.CellEditEvent<Student, String> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, String>) e;
        Student student = cellEditEvent.getRowValue();
        student.setLastName(cellEditEvent.getNewValue());
    }

    public void studentIdColumnOnEdit(Event e) {
        TableColumn.CellEditEvent<Student, String>  cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, String>) e;
        Student student = cellEditEvent.getRowValue();
        student.setStudentId(cellEditEvent.getNewValue());
    }

    public void studentAgeColumnOnEdit(Event e) {
        TableColumn.CellEditEvent<Student, String> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, String>) e;
        Student student = cellEditEvent.getRowValue();
        student.setStudentAge(cellEditEvent.getNewValue());
    }

    public void studentGenderColumnEdit(Event e) {
        TableColumn.CellEditEvent<Student, String> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, String>) e;
        Student student = cellEditEvent.getRowValue();
        student.setStudentGender(cellEditEvent.getNewValue());
    }

    public void studentAddressColumnEdit(Event e) {
        TableColumn.CellEditEvent<Student, String> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, String>) e;
        Student student = cellEditEvent.getRowValue();
        student.setStudentAddress(cellEditEvent.getNewValue());
    }

}
