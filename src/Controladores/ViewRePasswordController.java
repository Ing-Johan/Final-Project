package Controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javax.swing.JOptionPane;
/**
 * FXML Controller class
 *
 * @authores Johan & Yonier & Sebastián
 */
public class ViewRePasswordController implements Initializable {
    
    String contraseña,email;
    
    ViewUserInfoController miUser;
    
    private BorderPane main ;
    
    @FXML
    private PasswordField txtPass,txtNewPass;
    
    @FXML
    private Button btnCambiar,btnOlvidar,btnCancelar;
    
    @FXML
    private void guardarInfo(ActionEvent event){
        
        Object evt = event.getSource();
            if(evt.equals(btnCambiar)){
            if(!contraseña.equals(txtPass.getText())){
                JOptionPane.showMessageDialog(null, "Contraseña Incorrecta!");
                txtPass.setText("");
            }else if(txtNewPass.getText().contains(" ")){
                JOptionPane.showMessageDialog(null,"No debe contener espacios en blanco");
                txtNewPass.requestFocus();
            }else if(txtNewPass.getText().length() < 8){
                JOptionPane.showMessageDialog(null,"Debe ser más larga");
                txtNewPass.requestFocus();
            }else if(txtPass.getText().equals(txtNewPass.getText())){
                JOptionPane.showMessageDialog(null,"Las contraseñas no debe ser igual a la anterior");
                txtNewPass.requestFocus();
            }else{
                JOptionPane.showMessageDialog(null, "Contraseña cambiada correctamente");
                if(miUser != null) {
                    miUser.restore();
                    miUser.contraseña = txtNewPass.getText();
                }
            }
        }else if(evt.equals(btnOlvidar)){
            String emailU = JOptionPane.showInputDialog(null,"Ingresa tu correo");
            
            if(emailU.equals(this.email)){
                String NewPass = JOptionPane.showInputDialog(null,"Ingresa contraseña nueva");
                if(NewPass.contains(" ")){
                    JOptionPane.showMessageDialog(null,"No debe contener espacios en blanco");
                }else if(NewPass.equals(this.contraseña)){
                    JOptionPane.showMessageDialog(null,"La contraseña no debe ser igual a la anterior");
                }else if(NewPass.length() < 8){
                    JOptionPane.showMessageDialog(null,"Debe ser más larga");
                }else{
                    JOptionPane.showMessageDialog(null, "Contraseña cambiada correctamente");
                    if(miUser != null) {
                        miUser.restore();
                        miUser.contraseña = NewPass;
                        miUser.correo = emailU;
                    }
                }
            }else JOptionPane.showMessageDialog(null,"Correo incorrecto");
        }else if(evt.equals(btnCancelar)){
            miUser.restore();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setMain(BorderPane borderPane){
        this.main = borderPane;
    }
    public void setMiUser(ViewUserInfoController controller) {
        this.miUser = controller;
    }
   
}