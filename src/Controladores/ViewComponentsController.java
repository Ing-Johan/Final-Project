package Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @authores Johan & Yonier & Sebastián
 */
public class ViewComponentsController implements Initializable {
   String contraseña,email,nombre,sexo;
   
   Image imgLoad;
   
   @FXML
   Label lComponents;
   
   @FXML
   Button btnComponents;
   
   @FXML
   ImageView imgComponents;
   
   @FXML
   private void actionComponent(ActionEvent event){
       try{
            
            ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewUserInfo.fxml"));
            Parent root = loader.load();
            ViewUserInfoController controller = loader.getController();
            
            controller.nameUser.setText(this.nombre);
            controller.imgUserInfo.setImage(imgLoad);
            controller.sexo = this.sexo;
            controller.contraseña = this.contraseña;
            controller.email = this.email;
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
            
            
        }catch(IOException e){}
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        }
   
   
}