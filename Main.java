package College.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Author: Anton Sergeev
 * Date: November 29, 2016
 *
 * This project was created for Java 2 class.
 *
 * 1. Saving to the file works throe the Menu Bar. The user has to
 *    click 'Save as' and save student list as a .txt file
 *
 * 2. Editing the file works throe the double click on each row. For
 *    example, if you need to change the first name, simple click on it,
 *    change and hit Enter.
 *
 * 3. All the information loads from the .txt file and presented for
 *    the demo only. If you don't need this tested data, go to
 *    StudentListController and comment out readFile() method.
 *    -> Also, in the initialize() method, change studentTable.setItems(studentData)
 *    This will allow you to use a basic functions only.
 */
public class Main extends Application {

    private static Stage primaryStage;
    private static AnchorPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ABC College");
        showMainView();
    }

    /**
     * Returns root layout studentListLayout.fxml.
     */
    private static void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("studentListLayout.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(IOException ex) {
            ex.getCause().printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
