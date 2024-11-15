package Controladores;

import Code.CarritoCompras;
import Code.Nodo;
import Code.Producto;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @authores Johan & Yonier & Sebastián
 */
public class ViewUserInfoController implements Initializable {
    
    ViewMainController main = new ViewMainController();
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
    public Pagination paginas = new Pagination();
    
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewComponents.fxml"));
            Parent root = loader.load();
            
            ViewComponentsController controller = loader.getController();
            controller.contraseña = this.contraseña;
            controller.email = this.correo;
            controller.nombre = this.nameUserInfo.getText();
            controller.sexo = this.genero;
            controller.lComponents.setText("Mi Carrito de Compras");
            controller.imgComponents.setImage(new Image("/images/carrito-de-compras-agg.png"));
            controller.imgLoad = imgUserInfo.getImage();
            controller.contenedor.getChildren().clear();
            configurarPaginacion(controller.contenedor);
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setMaximized(true);
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {}
    }
    
    @FXML
    private void miLista(ActionEvent event){
       try {
           
           ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewComponents.fxml"));
            Parent root = loader.load();
            
            ViewComponentsController controller = loader.getController();
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
            newStage.show();
        } catch (IOException e) {}
    }
    
    @FXML
    private void misCompras(ActionEvent event){
       try {
           
           ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewComponents.fxml"));
            Parent root = loader.load();
            
            ViewComponentsController controller = loader.getController();
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
            newStage.show();
        } catch (IOException e) {}
    }
    
    private void mostrarProductosEnCarrito(GridPane grid, CarritoCompras carritoCompras, int paginaIndex, int productosPorPagina) {
        grid.getChildren().clear();

        int inicio = paginaIndex * productosPorPagina;
        int fila = 0;
        int columna = 0;

        Nodo<Producto> p = carritoCompras.inicio;
        int contador = 0;

        while (contador < inicio && p != null) {
            p = p.sig;
            contador++;
        }

        for (int i = 0; i < productosPorPagina && p != null; i++) {
            Producto producto = p.datos;
            
            VBox vboxProducto = new VBox();
            vboxProducto.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-border-color: #cccccc; -fx-border-width: 1;");
            vboxProducto.setPrefWidth(Control.USE_COMPUTED_SIZE);

            ImageView imagen = new ImageView(new Image(producto.rutaIMG));
            imagen.setFitWidth(100);
            imagen.setFitHeight(100);
            imagen.setPreserveRatio(true);

            Label nombre = new Label(producto.nombreProd);
            nombre.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

            Label precio = new Label("$" + producto.precioProd);
            precio.setStyle("-fx-font-size: 12; -fx-text-fill: #555555;");
            
            ImageView carrito = new ImageView(new Image("/images/carrito-de-compras.png"));
            Button btnCarrito = new Button("", carrito);
            carrito.setFitWidth(30);
            carrito.setFitHeight(30);
            carrito.setPreserveRatio(true);
            btnCarrito.setStyle("-fx-background-color: ffffff01");
            main.configurarBotonCarrito(btnCarrito, producto);
            
            HBox hboxBtns = new HBox();
            hboxBtns.getChildren().add(btnCarrito);
            
            vboxProducto.getChildren().addAll(imagen, nombre, precio, hboxBtns);
        
            grid.add(vboxProducto, columna, fila);
            GridPane.setHgrow(vboxProducto, Priority.ALWAYS);
            GridPane.setVgrow(vboxProducto, Priority.ALWAYS); 
            
            columna++;
            if (columna == 4) { 
                columna = 0;
                fila++;
            }

            p = p.sig;
        }
    }
    
    private void configurarPaginacion(VBox contenedor) {
        int totalProductos = main.miCarrito.getLongitudCola(); 
        int totalPaginas = (int) Math.ceil((double) totalProductos / 16);

        paginas.setPageCount(totalPaginas);
        paginas.setPageFactory(this::crearPagina);

        contenedor.getChildren().clear();
        contenedor.getChildren().addAll(paginas,main.label);
        VBox.setMargin(paginas, new Insets(0, 0, 20, 0));
    }

    private GridPane crearPagina(int paginaIndex) {
        GridPane grid = new GridPane();
        mostrarProductosEnCarrito(grid, main.miCarrito, paginaIndex, 16);
        return grid;
    }
}
