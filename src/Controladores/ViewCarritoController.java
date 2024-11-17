package Controladores;

import Code.CarritoCompras;
import Code.CarritoManager;
import Code.ComprasManager;
import Code.Nodo;
import Code.Producto;
import Code.ProductosComprados;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
public class ViewCarritoController implements Initializable {
   
    ViewMainController main = new ViewMainController();
    ProductosComprados misCompras = ComprasManager.getCompras(); 
    CarritoCompras miCarrito = CarritoManager.getCarritoCompras();
    String contraseña,email,nombre,sexo;
   
   Image imgLoad;
   
   @FXML
   VBox contenedor;
   
   @FXML
   Label lComponents, txtInfo;
   
   @FXML
   Button btnComponents;
   
   @FXML
   ImageView imgComponents;
   
    @FXML
    public Pagination paginas;
   
   @FXML
   private void actionComponent(ActionEvent event){
       try{
            
            ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewUserInfo.fxml"));
            Parent root = loader.load();
            ViewUserInfoController controller = loader.getController();
            
            controller.nameUserInfo.setText(this.nombre);
            controller.imgUserInfo.setImage(imgLoad);
            controller.genero = this.sexo;
            controller.contraseña = this.contraseña;
            controller.correo = this.email;
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("AGROINSUMOS EP");
            newStage.show();
            
            
        }catch(IOException e){}
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtInfo.setText(""+main.label.getText());      
        configurarPaginacion(contenedor);
        
    }
    
    public void configurarBotonCompras(Button btn, Producto producto) {
        btn.setOnAction(event -> {
            int op = JOptionPane.showConfirmDialog(null,"¿Deseas comprar este artículo?","Elige una opción", JOptionPane.YES_NO_OPTION);
            if(op==0){
                String pass = JOptionPane.showInputDialog(null,"Ingresa la contraseña");
                if(pass.equals(contraseña)){
                    JOptionPane.showMessageDialog(null,"Compra exitosa. Puedes revisar el movimiento");
                    misCompras.setPush(producto);
                }else{
                    JOptionPane.showMessageDialog(null,"Contraseña incorrecta, revisa y vuelve a intentar");
                }
            }
        });
    }
    
    public void eliminarDelCarrito(Button btnQuitar, Producto producto){
        btnQuitar.setOnAction(event -> {
            miCarrito.setAtender(producto.idProd);
            configurarPaginacion(contenedor);
        });
    }
   
    private void mostrarProductos(GridPane grid, CarritoCompras carritoCompras, int paginaIndex, int productosPorPagina) {
        grid.getChildren().clear();

        if (carritoCompras.getEsColaVacia()) {
            return;
        }

        int tamaño = carritoCompras.getLongitudCola();
        int inicio = paginaIndex * productosPorPagina;

        if (inicio >= tamaño) {
            System.out.println("No hay más productos para mostrar.");
            return;
        }

        Nodo<Producto> p = carritoCompras.inicio;

        for (int i = 0; i < inicio; i++) {
            p = p.sig;
        }

        int fila = 0;
        int columna = 0;

        
        for (int i = 0; i < productosPorPagina && i + inicio < tamaño; i++) {
            Producto producto = p.datos;

            VBox vboxProducto = new VBox();
            vboxProducto.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-border-color: #cccccc; -fx-border-width: 1;");
            vboxProducto.setPrefWidth(Control.USE_COMPUTED_SIZE);

            ImageView imagen = new ImageView(new Image(producto.rutaIMG));
            imagen.setFitWidth(100);
            imagen.setFitHeight(100);
            imagen.setPreserveRatio(true);

            Label name = new Label(producto.nombreProd);
            name.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

            Label precio = new Label("$" + producto.precioProd);
            precio.setStyle("-fx-font-size: 12; -fx-text-fill: #555555;");

            ImageView comprar = new ImageView(new Image("/images/bolsa-de-la-compra.png"));
            Button btnComprar = new Button("", comprar);
            comprar.setFitWidth(30);
            comprar.setFitHeight(30);
            comprar.setPreserveRatio(true);
            btnComprar.setStyle("-fx-background-color: ffffff01");
            configurarBotonCompras(btnComprar,producto);
            
            ImageView quitar = new ImageView(new Image("/images/quitar-del-carrito.png"));
            Button btnQuitar = new Button("", quitar);
            quitar.setFitWidth(30);
            quitar.setFitHeight(30);
            quitar.setPreserveRatio(true);
            btnQuitar.setStyle("-fx-background-color: ffffff01");
            eliminarDelCarrito(btnQuitar,producto);
           

            HBox hboxBtns = new HBox();
            hboxBtns.getChildren().addAll(btnComprar,btnQuitar);
            hboxBtns.setStyle("-fx-alignment: center;");

            vboxProducto.getChildren().addAll(imagen, name, precio, hboxBtns);

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
        int totalProductos = miCarrito.getLongitudCola(); 
        int totalPaginas = (int) Math.ceil((double) totalProductos / 12);

        paginas.setPageCount(totalPaginas);
        paginas.setPageFactory(this::crearPagina);

        contenedor.getChildren().clear();
        contenedor.getChildren().addAll(paginas,txtInfo);
        VBox.setMargin(paginas, new Insets(0, 0, 10, 0));
    }

    private GridPane crearPagina(int paginaIndex) {
        GridPane grid = new GridPane();
        mostrarProductos(grid, miCarrito, paginaIndex, 12);
        return grid;
    }
    
}