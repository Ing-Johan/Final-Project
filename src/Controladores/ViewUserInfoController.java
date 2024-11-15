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
import javafx.scene.image.Image;
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
    
    String genero,contraseña,correo;
    private Node centroInicial;
    
    @FXML
    private BorderPane borderPane;
                
    @FXML
    private Button salirBtn,carritoUser,deseosUser,regresarBtn;
    
    @FXML
    public Label nameUserInfo = new Label();
    
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
                stage.setResizable(false);
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
            
            controller.UserIMG(this.genero);
            controller.nameUser.setText(this.nameUserInfo.getText());
            controller.sexo = this.genero;
            controller.password = contraseña;
            controller.email = this.correo;
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setMaximized(true);
            newStage.setResizable(false);
            newStage.setTitle("AGROINSUMOS EP");
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
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewRePassword.fxml"));
            Parent nuevoCentro = loader.load();
            ViewRePasswordController controller = loader.getController();
            controller.setMain(borderPane);
            controller.setMiUser(this);
            borderPane.setCenter(nuevoCentro);
            controller.contraseña = this.contraseña;
            controller.email = this.correo;
        } catch (IOException e) {}
    }
    
    @FXML 
    private void miCarrito(ActionEvent event){
       try {
           
           ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewCarrito.fxml"));
            Parent root = loader.load();
            
            ViewCarritoController controller = loader.getController();
            controller.contraseña = this.contraseña;
            controller.email = this.correo;
            controller.nombre = this.nameUserInfo.getText();
            controller.sexo = this.genero;
            controller.lComponents.setText("Mi Carrito de Compras");
            controller.imgComponents.setImage(new Image("/images/carrito-de-compras-agg.png"));
            controller.imgLoad = imgUserInfo.getImage();
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setMaximized(true);
            newStage.setResizable(false);
            newStage.setTitle("AGROINSUMOS EP");
            newStage.show();
        } catch (IOException e) {}
    }
    
    @FXML
    private void miLista(ActionEvent event){
       try {
           
           ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewDeseos.fxml"));
            Parent root = loader.load();
            
            ViewCarritoController controller = loader.getController();
            controller.contraseña = this.contraseña;
            controller.email = this.correo;
            controller.nombre = this.nameUserInfo.getText();
            controller.sexo = this.genero;
            controller.lComponents.setText("Mi Lista de Deseos");
            controller.imgComponents.setImage(new Image("/images/amar.png"));
            controller.imgLoad = imgUserInfo.getImage();
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setMaximized(true);
            newStage.setResizable(false);
            newStage.setTitle("AGROINSUMOS EP");
            newStage.show();
        } catch (IOException e) {}
    }
    
    @FXML
    private void misCompras(ActionEvent event){
       try {
           
           ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewCompras.fxml"));
            Parent root = loader.load();
            
            ViewCarritoController controller = loader.getController();
            controller.contraseña = this.contraseña;
            controller.email = this.correo;
            controller.nombre = this.nameUserInfo.getText();
            controller.sexo = this.genero;
            controller.lComponents.setText("Mis Compras");
            controller.imgComponents.setImage(new Image("/images/amor.png"));
            controller.imgLoad = imgUserInfo.getImage();
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setMaximized(true);
            newStage.setResizable(false);
            newStage.setTitle("AGROINSUMOS EP");
            newStage.show();
        } catch (IOException e) {}
    }

}