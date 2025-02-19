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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @authores Johan & Yonier & Sebastián
 */
public class ViewController_1 implements Initializable {
    
    @FXML
    private Button btn;
    
    @FXML
    private TextField txtCorreo,txtArea, txtUser;
    
    @FXML
    private PasswordField txtPass,passConfirm;
    
    @FXML
    private void actionEvent(ActionEvent event) throws IOException{
        
        Object evt = event.getSource();
        
        if(evt.equals(btn)){
            
            if(txtCorreo.getText().isEmpty() || txtUser.getText().isEmpty() || txtPass.getText().isEmpty() || passConfirm.getText().isEmpty()){
                txtArea.setText("¡Debes llenar todos los campos!");
            }else if(!txtCorreo.getText().contains("@")){
                txtArea.setText("¡Correo inválido!");
            }else if(!txtCorreo.getText().contains(".com")){
                txtArea.setText("¡Correo inválido!");
            }else if(txtCorreo.getText().contains(" ") || txtUser.getText().contains(" ") || txtPass.getText().contains(" ") || passConfirm.getText().contains(" ")){
                txtArea.setText("¡No se aceptan espacios en blanco!");
            }else if(txtPass.getText().length() < 8){
                txtArea.setText("!La contraseña debe ser más larga¡");
            }else if(!txtPass.getText().equals(passConfirm.getText())){
                txtArea.setText("¡La contraseña no coincide!");
            }else{
                JOptionPane.showMessageDialog(null, "¡Ingreso exitoso!");
                loadStage(event); 
            }
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void loadStage( Event event){
        
        try{
            
            ((Node) (event.getSource())).getScene().getWindow().hide();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewSexo.fxml")); 
            Parent root = loader.load();
            
            ViewMainController main = loader.getController();
            main.name = ""+txtUser.getText();
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
            
        }catch(IOException e){}
    }
}