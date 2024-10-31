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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @authores Johan & Yonier & Sebastián
 */
public class ViewMainController implements Initializable {
    
    String name;
    
    @FXML
    public TextField nameUser = new TextField();
    
    @FXML
    private Button btn2,btnUser,btnSex;
    
    @FXML
    private ToggleGroup sexo;
    
    @FXML
    private ImageView imgUser = new ImageView();
          
    public void UserIMG(String sex) {
        String image;
        
        switch(sex) {
            case "Masculino" -> image = "/images/men.jpeg";
            case "Femenino" -> image = "/images/women.jpeg";
            default -> image = "/images/user.jpeg";
        }
        Image imagen = new Image(image);
        imgUser.setImage(imagen);
        btnUser.setGraphic(imgUser);
        
    }

    
    @FXML
    private void eventAction(ActionEvent event){
        
        Object evt = event.getSource();
        
        if(evt.equals(btn2)){
            JOptionPane.showMessageDialog(null, "¡Nos vemos pronto!");
            loadStage("/Vistas/view.fxml",event);
            
        }else if(evt.equals(btnUser)){
            JOptionPane.showMessageDialog(null, "");
            loadStage("",event);
            
        }else if(evt.equals(btnSex)){
            loadMain(event);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
    }    
    
    public void loadStage(String url,Event event){
        
        try{
            
            ((Node) (event.getSource())).getScene().getWindow().hide();
            
            Parent root = FXMLLoader.load(getClass().getResource(url)); 
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
            
            
        }catch(IOException e){}
    }
    
    public void loadMain(Event event) {
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewMain.fxml"));
            Parent root = loader.load();
            ViewMainController controller = loader.getController();

            RadioButton selectedRadioButton = (RadioButton) sexo.getSelectedToggle();
            if (selectedRadioButton != null) {
                String selectedGender = selectedRadioButton.getText();
                controller.UserIMG(selectedGender);
            }
            
            controller.nameUser.setText(name);
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {}
    }
}