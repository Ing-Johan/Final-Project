package Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @authores Johan & Yonier & Sebastián
 */
public class ViewSexoController implements Initializable {
    
    String name,selectedGender = "",password,email;
     
    @FXML
    private Button btnSex;
        
    @FXML
    private ToggleGroup sexo;
    
    @FXML
    private void action(ActionEvent event){
        
        Object evt = event.getSource();
        
        if(evt.equals(btnSex)){
            loadMain(event);
        }
    }
    
    public String sexoEscogido(){
        RadioButton selectedRadioButton = (RadioButton) sexo.getSelectedToggle();
            if (selectedRadioButton != null) {
                selectedGender = selectedRadioButton.getText();
            }
            return selectedGender;
    }
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        }
   
    public void loadMain(Event event) {
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewMain.fxml"));
            Parent root = loader.load();
            ViewMainController controller = loader.getController();
            
            controller.UserIMG(sexoEscogido());
            controller.nameUser.setText(name);
            controller.password = this.password;
            controller.sexo = this.selectedGender;
            controller.email = this.email;
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setMaximized(true);
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {}
    }
}