package Controladores;

import Code.ArchivoHistorial;
import Code.ComprasManager;
import Code.NodoDoble;
import Code.Producto;
import Code.ProductosComprados;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
/**
 * FXML Controller class
 *
 * @authores Johan & Yonier & Sebastián
 */
public class ViewComprasController implements Initializable {
   
   ViewMainController main = new ViewMainController();
   ProductosComprados misCompras = ComprasManager.getCompras();
    
   String contraseña,email,nombre,sexo;
   
   Image imgLoad;
   
   @FXML
   VBox contenedor;
   
   @FXML
   Label lComponents, txtInfo, txtTotal;
   
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
        txtTotal.setText("Total gastado: "+misCompras.getTotal());
        
    }
   
        private void mostrarProductos(GridPane grid, ProductosComprados compras, int paginaIndex, int productosPorPagina) {
        grid.getChildren().clear();

        if (compras.getEsPilaVacia()) {
            return;
        }

        int tamaño = compras.getLongPila();
        int inicio = paginaIndex * productosPorPagina;

        if (inicio >= tamaño) {
            System.out.println("No hay más productos para mostrar.");
            return;
        }

        NodoDoble<Producto> p = compras.tope;

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
            name.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

            Label precio = new Label("$" + producto.precioProd);
            precio.setStyle("-fx-font-size: 14; -fx-text-fill: #555555;");

            vboxProducto.getChildren().addAll(imagen, name, precio);

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
        int totalProductos = misCompras.getLongPila(); 
        int totalPaginas = (int) Math.ceil((double) totalProductos / 12);

        paginas.setPageCount(totalPaginas);
        paginas.setPageFactory(this::crearPagina);

        contenedor.getChildren().clear();
        contenedor.getChildren().addAll(paginas,txtInfo);
        VBox.setMargin(paginas, new Insets(0, 0, 10, 0));
    }

    private GridPane crearPagina(int paginaIndex) {
        GridPane grid = new GridPane();
        mostrarProductos(grid, misCompras, paginaIndex, 12);
        return grid;
    }
    
    public void cargarHistorialYMostrar(String correo, ProductosComprados productosComprados) {
        List<String[]> historial = ArchivoHistorial.cargarHistorialPorUsuario(correo);

        for (String[] compra : historial) {
            String idProducto = compra[0];
            String nombreProducto = compra[1];
            float precio = Float.parseFloat(compra[2]);

            Producto producto = new Producto(idProducto, nombreProducto, precio, "/images/" + idProducto + ".jpg");
            productosComprados.setPush(producto);
        }
        
        GridPane grid = new GridPane();
        mostrarProductos(grid, productosComprados, 0, 9);
    }
}
