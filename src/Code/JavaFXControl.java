package Code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @authores Johan & Yonier & Sebastian
 */
public class JavaFXControl extends Application{

    @Override
    public void start(Stage stage) throws Exception {
         
        Parent root = FXMLLoader.load(getClass().getResource("/Vistas/view.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("AGROINSUMOS EP");
    }
    
    public static void main(String [] args){
        launch(args);
    }
    
}
