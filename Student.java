package College.app;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Formatter;
import java.util.Random;

/**
 * Created by S.Anthony on 2016-11-29.
 */
public class Student {

    private IntegerProperty studentNumber = new SimpleIntegerProperty(this, "studentNumber", 1);
    private StringProperty firstName = new SimpleStringProperty(this, "firstName", null);
    private StringProperty lastName = new SimpleStringProperty(this, "lastName", null);
    private StringProperty studentId = new SimpleStringProperty(this, "studentId", null);
    private StringProperty studentAge = new SimpleStringProperty(this, "studentAge", null);
    private StringProperty studentGender = new SimpleStringProperty(this, "studentGender", null);
    private StringProperty studentAddress = new SimpleStringProperty(this, "studentAddress", null);

    /**
     * Constructor.
     */
    public Student() {
        this(0, null,null,null,null,null,null);
    }

    /**
     * Default setters for the constructor.
     * @param fName
     * @param lName
     * @param sId
     * @param sAge
     * @param sGender
     * @param sAddress
     */
    public Student(int sNum, String fName, String lName, String sId, String sAge, String sGender, String sAddress) {
        studentNumber.set(sNum);
        firstName.set(fName);
        lastName.set(lName);
        studentId.set(sId);
        studentAge.set(sAge);
        studentGender.set(sGender);
        studentAddress.set(sAddress);
    }

    /**
     * Student number. P.S: Row number.
     * @return
     */
    public final int getStudentNumber() {
        return studentNumber.get();
    }

    public final void setStudentNumber(int value) {
        studentNumberProperty().set(value);
    }

    public final IntegerProperty studentNumberProperty() {
        return studentNumber;
    }

    /**
     * First Name.
     */
    public final String getFirstName() {
        return firstName.get();
    }

    public final void setFirstName(String value) {
        firstNameProperty().set(value);
    }

    public final StringProperty firstNameProperty() {
        return firstName;
    }


    /**
     * Last Name.
     */
    public final String getLastName() {
        return lastName.get();
    }

    public final void setLastName(String value) {
        lastNameProperty().set(value);
    }

    public final StringProperty lastNameProperty() {
        return lastName;
    }


    /**
     * Student ID.
     */
    public final String getStudentId() {
        return studentId.get();
    }

    public final void setStudentId(String value) {
        studentIdProperty().set(value);
    }

    public final StringProperty studentIdProperty() {
        return studentId;
    }

    /**
     * Student Age.
     */
    public final String getStudentAge() {
        return studentAge.get();
    }

    public final void setStudentAge(String value) {
        studentAgeProperty().set(value);
    }

    public final StringProperty studentAgeProperty() {
        return studentAge;
    }


    /**
     * Student Gender.
     */
    public final String getStudentGender() {
        return studentGender.get();
    }

    public final void setStudentGender(String value) {
        studentGenderProperty().set(value);
    }

    public final StringProperty studentGenderProperty() {
        return studentGender;
    }


    /**
     * Student Address.
     */
    public final String getStudentAddress() {
        return studentAddress.get();
    }

    public final void setStudentAddress(String value) {
        studentAddressProperty().set(value);
    }

    public final StringProperty studentAddressProperty() {
        return studentAddress;
    }


    /**
     * Returns a toString for the console only.
     * This method was created to show that all information was successfully
     * sent to the table view.
     * @return
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        String template = "%-20s %-20s %-14s %-6s %-6s %-30s%n";
        formatter.format(template, getFirstName(),getLastName(),getStudentId(),getStudentAge(),getStudentGender(),getStudentAddress());
        return stringBuilder.toString();
    }

}
