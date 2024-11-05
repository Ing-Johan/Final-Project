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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @authores Johan & Yonier & Sebastián
 */
public class ViewUserInfoController implements Initializable {
    
    String sexo,contraseña;
    
    private Node centroInicial;
    
    @FXML
    private BorderPane borderPane;
                
    @FXML
    private Button salirBtn,carritoUser,deseosUser,regresarBtn;
    
    @FXML
    public Label nameUser;
    
    @FXML
    public ImageView imgUserInfo; 
    
    @FXML
    private void UserInfo(ActionEvent event) throws IOException{
        
        Object evt = event.getSource();
        
        if(evt.equals(salirBtn)){
        
            int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas cerrar la sesión?\nTu cuenta se perderá","Confirmación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
            );

            if (opcion == 0) {
                ((Node)(event.getSource())).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/Vistas/view.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }
        }else if(evt.equals(regresarBtn)){
            loadMain(event);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        centroInicial = borderPane.getCenter();
    }
   
    public void loadMain(Event event) {
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewMain.fxml"));
            Parent root = loader.load();
            ViewMainController controller = loader.getController();
            
            controller.UserIMG(this.sexo);
            controller.nameUser.setText(this.nameUser.getText());
            controller.sexo = this.sexo;
            controller.password = contraseña;
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setMaximized(true);
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {}
    }
 
    
    @FXML
    private void newUser(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewReName.fxml"));
            Parent nuevoCentro = loader.load();
            ViewReNameController controller = loader.getController();
            controller.setMain(borderPane);
            controller.setMiUser(this);
            borderPane.setCenter(nuevoCentro);
            controller.contraseña = this.contraseña;
        } catch (IOException e) {}
        
    }
    
    public void restore(){
        borderPane.setCenter(centroInicial);
    }
    
    @FXML 
    private void newPass(ActionEvent event){
        JOptionPane.showMessageDialog(null, "Aún no agregas nada");
    }
}