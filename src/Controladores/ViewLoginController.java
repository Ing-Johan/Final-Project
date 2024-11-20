package Controladores;


import Code.ArchivoUsuarios;
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
public class ViewLoginController implements Initializable {

    @FXML
    private Button btn;

    @FXML
    private TextField txtCorreo, txtArea, txtUser;

    @FXML
    private PasswordField txtPass, passConfirm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sugerirInicioSesion();
    }

    public void iniciarSesion() {
        String correo = txtCorreo.getText();
        String nombreUsuario = txtUser.getText();
        String contrasena = txtPass.getText();

        if (ArchivoUsuarios.validarInicioSesion(correo, nombreUsuario, contrasena)) 
            txtArea.setText("Inicio de sesión exitoso.");
        else txtArea.setText("Correo, Usuario o Contaseña errada");
          
    }

    public void registrarUsuario() {
        String correo = txtCorreo.getText();
        String nombreUsuario = txtUser.getText();
        String contrasena = txtPass.getText();

        if (ArchivoUsuarios.correoExiste(correo)) {
           iniciarSesion();
        } else {
            ArchivoUsuarios.guardarUsuario(correo, nombreUsuario, contrasena);
            txtArea.setText("¡Usuario registrado exitosamente!");
        }
    }
    
    public void sugerirInicioSesion() {
        String[] datosUsuario = ArchivoUsuarios.leerUsuario();
        if (datosUsuario != null) {
            txtCorreo.setText(datosUsuario[0]);
            txtUser.setText(datosUsuario[1]);
            txtPass.setText(datosUsuario[2]);
        }
    }

    @FXML
    private void actionEvent(ActionEvent event) {
        Object evt = event.getSource();

        if (evt.equals(btn)) {
            String mensajeValidacion = validarCampos();
            if (!mensajeValidacion.isEmpty()) {
                txtArea.setText(mensajeValidacion);
                return;
            }

            registrarUsuario();
            
            if (txtArea.getText().equals("¡Usuario registrado exitosamente!")) {
                JOptionPane.showMessageDialog(null, "¡Usuario registrado exitosamente!\n¡Un gusto tenerte por aquí!","Has ingresado",JOptionPane.PLAIN_MESSAGE);
                loadStage(event);
            }else if(txtArea.getText().equals("Inicio de sesión exitoso.")){
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso\n¡Bienvenido de nuevo!","Has ingresado",JOptionPane.PLAIN_MESSAGE);
                loadStage(event);
            }
        }
    }

    private String validarCampos() {
        if (txtCorreo.getText().isEmpty() || txtUser.getText().isEmpty() || txtPass.getText().isEmpty() || passConfirm.getText().isEmpty()) {
            return "¡Debes llenar todos los campos!";
        }else if (!validarCorreo(txtCorreo.getText())) {
            return "¡Correo inválido!";
        }else if (txtCorreo.getText().contains(" ") || txtUser.getText().contains(" ") || txtPass.getText().contains(" ") || passConfirm.getText().contains(" ")) {
            return "¡No se aceptan espacios en blanco!";
        }else if (txtPass.getText().length() < 8) {
            return "¡La contraseña debe ser más larga!";
        }else if (!txtPass.getText().equals(passConfirm.getText())) {
            return "¡La contraseña no coincide!";
        }
        return "";
    }

    private boolean validarCorreo(String correo) {
        String regex = "^[A-Za-z\\d_.-]+@[A-Za-z\\d.-]+\\.(com|net|org|edu|co)$";
        return correo.matches(regex);
    }

    public void loadStage(Event event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewSexo.fxml"));
            Parent root = loader.load();

            ViewSexoController main = loader.getController();
            main.name = txtUser.getText();
            main.password = txtPass.getText();
            main.email = txtCorreo.getText();

            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.show();

        } catch (IOException e) {}
    }
}
