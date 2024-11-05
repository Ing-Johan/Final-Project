package Controladores;

import Code.ListaProductos;
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
import javafx.scene.control.TextField;
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
public class ViewMainController implements Initializable {
    
    String password,sexo;
    
    Label label = new Label("Todo el contenido de este sitio es propiedad de los ingenieros Johan Hernández, Yonier Cavadía y Sebastián Madera, y está protegido por derechos de autor.  ©2024");
    
    private final ListaProductos listaProductos = new ListaProductos();
    private final ListaProductos carritoCompras = new ListaProductos();
    private final ListaProductos listaDeseos = new ListaProductos();
    
    @FXML
    private Pagination paginas;
    
    @FXML
    public TextField nameUser,txtBuscar;
    
    @FXML
    private VBox ContenedorPrincipal;
    
    @FXML
    private Button btnUser,btnSearch,btnBack;
    
    @FXML
    private final ImageView imgUser = new ImageView();
    
    @FXML
    private void eventAction(ActionEvent event){
        
        Object evt = event.getSource();
        
        if(evt.equals(btnUser)){
            loadStage(event);
            
        }else if(evt.equals(btnBack)){
            ContenedorPrincipal.getChildren().clear();
            configurarPaginacion();
            btnBack.setVisible(false);
            txtBuscar.clear();
            
        }else if(evt.equals(btnSearch)){
            String searchText = txtBuscar.getText();
            if(searchText.equals("")){
                JOptionPane.showMessageDialog(null,"Escribe un producto");
            }else productosFiltrados(searchText);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarProductos();
        configurarPaginacion();
    }
    
    private HBox crearBotones(Producto producto) {
        HBox hboxBtns = new HBox();
        hboxBtns.setStyle("-fx-alignment: center;");
        hboxBtns.setPrefWidth(Control.USE_COMPUTED_SIZE);

        ImageView carrito = new ImageView(new Image("/images/carrito-de-compras.png"));
        ImageView deseo = new ImageView(new Image("/images/amar.png"));
        Button btnCarrito = new Button("", carrito);
        Button btnDeseo = new Button("", deseo);
        carrito.setFitWidth(30);
        carrito.setFitHeight(30);
        carrito.setPreserveRatio(true);
        deseo.setFitWidth(30);
        deseo.setFitHeight(30);
        deseo.setPreserveRatio(true);
        btnCarrito.setStyle("-fx-background-color: ffffff01");
        btnDeseo.setStyle("-fx-background-color: ffffff01");
        configurarBotonCarrito(btnCarrito, producto);
        configurarBotonDeseo(btnDeseo, producto);
        hboxBtns.setMargin(btnCarrito, new Insets(0,20,0,0));
        hboxBtns.getChildren().addAll(btnCarrito, btnDeseo);
        return hboxBtns;
    }

    private void configurarBotonCarrito(Button btnCarrito, Producto producto) {
        btnCarrito.setOnAction(event -> {
            ImageView imageView = (ImageView) btnCarrito.getGraphic();
            if (carritoCompras.contieneProducto(producto)) {
                carritoCompras.eliminarProducto(producto);
                imageView.setImage(new Image("/images/carrito-de-compras.png"));
                JOptionPane.showMessageDialog(null, "Producto eliminado del carrito.");
            } else {
                carritoCompras.agregarProducto(producto);
                imageView.setImage(new Image("/images/carrito-de-compras-agg.png"));
                JOptionPane.showMessageDialog(null, "Producto agregado al carrito.");
            }
        });
    }

    private void configurarBotonDeseo(Button btnDeseo, Producto producto) {
        btnDeseo.setOnAction(event -> {
            ImageView imageView = (ImageView) btnDeseo.getGraphic();
            if (listaDeseos.contieneProducto(producto)) {
                listaDeseos.eliminarProducto(producto);
                imageView.setImage(new Image("/images/amar.png"));
                JOptionPane.showMessageDialog(null, "Producto eliminado de la lista de deseos.");
            } else {
                listaDeseos.agregarProducto(producto);
                imageView.setImage(new Image("/images/amor.png"));
                JOptionPane.showMessageDialog(null, "Producto agregado a la lista de deseos.");
            }
        });
    }
    
    private void productosFiltrados(String searchText) {
        
        ContenedorPrincipal.getChildren().clear();
        
        GridPane gridPane = new GridPane();
        

        Nodo<Producto> productosFiltrados = listaProductos.buscarProducto(searchText);
    
        int fila = 0;
        int columna = 0;
        

        while (productosFiltrados != null) {
            Producto producto = productosFiltrados.datos;

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
            
            HBox hboxBtns = crearBotones(producto);

            vboxProducto.getChildren().addAll(imagen, nombre, precio, hboxBtns);
        
            GridPane.setHgrow(vboxProducto, Priority.ALWAYS);
            GridPane.setVgrow(vboxProducto, Priority.ALWAYS); 
            gridPane.add(vboxProducto, columna, fila++);

            if (fila == 4) { 
                fila = 0;
                columna++;
            }

            productosFiltrados = productosFiltrados.sig; 
        }
        
        btnBack.setVisible(true);
        ContenedorPrincipal.setMargin(btnBack, new Insets(20, 0, 0, 0));
        
        if(listaProductos.tamEncontrados>0) 
            ContenedorPrincipal.getChildren().addAll(gridPane,btnBack);
        
        else {
            Image image = new Image("/images/extraviado.png");
            ImageView imgS = new ImageView(image);
            Label labelSearch = new Label("No encontramos coincidencias",imgS);
            imgS.setFitWidth(100);
            imgS.setFitHeight(100);
            labelSearch.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
            ContenedorPrincipal.getChildren().addAll(labelSearch,btnBack);
        }
    }
    
    public void UserIMG(String sex) {
        String image;
        
        switch(sex) {
            case "Masculino" -> image = "/images/men.jpeg";
            case "Femenino" -> image = "/images/women.jpeg";
            default -> image = "/images/user.jpeg";
        }
        Image imagen = new Image(image);
        imgUser.setImage(imagen);
        imgUser.setFitWidth(116);
        imgUser.setFitHeight(105);
        imgUser.setPreserveRatio(true); 
        btnUser.setGraphic(imgUser);
        
    } 
    
    public void loadStage(Event event){
        
        try{
            
            ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewUserInfo.fxml"));
            Parent root = loader.load();
            ViewUserInfoController controller = loader.getController();
            
            controller.nameUser.setText(this.nameUser.getText());
            Image img = this.imgUser.getImage();
            controller.imgUserInfo.setImage(img);
            controller.sexo = this.sexo;
            controller.contraseña = this.password;
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
            
            
        }catch(IOException e){}
    }
    
    private void configurarPaginacion() {
    int totalProductos = listaProductos.tamaño; 
    int totalPaginas = (int) Math.ceil((double) totalProductos / 16);

    paginas.setPageCount(totalPaginas);
    paginas.setPageFactory(this::crearPagina);
    
    ContenedorPrincipal.getChildren().clear();
    ContenedorPrincipal.getChildren().addAll(paginas,label);
    VBox.setMargin(paginas, new Insets(0, 0, 20, 0));
}

    private GridPane crearPagina(int paginaIndex) {
        GridPane grid = new GridPane();
        mostrarProductosEnGrid(grid, listaProductos, paginaIndex, 16);
        return grid;
    }
    
    private void mostrarProductosEnGrid(GridPane grid, ListaProductos lista, int paginaIndex, int productosPorPagina) {
        grid.getChildren().clear();

        int inicio = paginaIndex * productosPorPagina;
        int fila = 0;
        int columna = 0;

        Nodo<Producto> p = listaProductos.cabeza;
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
            
            HBox hboxBtns = crearBotones(producto);
            
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
    
    private void inicializarProductos() {
        listaProductos.agregarProducto(new Producto("001", "Fertilizante Orgánico", 20000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("002", "Insecticida Biológico", 25000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("003", "Herbicida Natural", 30000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("004", "Semilla de Maíz", 15000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("005", "Semilla de Arroz", 16000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("006", "Semilla de Trigo", 17000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("007", "Fertilizante Compuesto", 22000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("008", "Pesticida Orgánico", 27000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("009", "Controlador de Plagas", 35000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("010", "Abono Foliar", 28000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("011", "Fertilizante Granulado", 23000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("012", "Fertilizante Líquido", 24000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("013", "Abono Orgánico", 26000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("014", "Bioestimulante", 33000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("015", "Semilla de Soya", 18000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("016", "Semilla de Cebada", 19000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("017", "Semilla de Frijol", 20000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("018", "Corrector de Suelo", 21000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("019", "Inoculante", 32000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("020", "Aminoácidos", 34000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("021", "Enraizante", 29000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("022", "Vermicompost", 36000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("023", "Control de Maleza", 37000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("024", "Herbicida Orgánico", 38000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("025", "Abono de Estiércol", 39000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("026", "Fertilizante para Maíz", 40000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("027", "Fertilizante para Trigo", 41000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("028", "Fertilizante para Arroz", 42000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("029", "Fertilizante para Frijol", 43000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("030", "Insecticida Natural", 44000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("031", "Aislador Eléctrico", 45000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("032", "Malla para Cultivo", 46000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("033", "Estaca de Madera", 47000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("034", "Riego por Goteo", 48000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("035", "Aspersor Agrícola", 49000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("036", "Kit de Riego", 50000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("037", "Trampa para Plagas", 51000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("038", "Cinta para Injertos", 52000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("039", "Control de Hormigas", 53000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("040", "Protector Solar para Cultivo", 54000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("041", "Cinta Agrícola", 55000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("042", "Varilla de Tutoreo", 56000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("043", "Tijera de Poda", 57000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("044", "Pinzas para Plantas", 58000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("045", "Pulverizador Manual", 59000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("046", "Insecticida para Ácaros", 60000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("047", "Rastrillo Agrícola", 61000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("048", "Pala Agrícola", 62000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("049", "Maceta Grande", 63000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("050", "Maceta Mediana", 64000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("051", "Maceta Pequeña", 65000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("052", "Sustrato para Plantas", 66000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("053", "Bandeja para Semillas", 67000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("054", "Bolsa de Vivero", 68000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("055", "Sustrato de Coco", 69000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("056", "Perlita para Plantas", 70000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("057", "Vermiculita para Plantas", 71000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("058", "Aislante Térmico", 72000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("059", "Plástico Agrícola", 73000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("060", "Protector de Cultivo", 74000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("061", "Riego Automático", 75000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("062", "Control de Temperatura", 76000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("063", "Fungicida Biológico", 77000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("064", "Insecticida para Moscas", 78000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("065", "Control de Babosas", 79000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("066", "Red para Protección de Cultivo", 80000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("067", "Fertilizante para Hortalizas", 81000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("068", "Pesticida para Rosales", 82000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("069", "Fertilizante para Flores", 83000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("070", "Abono Ecológico", 84000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("071", "Malla para Sombra", 85000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("072", "Manguera de Riego", 86000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("073", "Regadera Manual", 87000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("074", "Abono para Césped", 88000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("075", "Controlador de Malezas", 89000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("076", "Nutriente para Plantas", 90000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("077", "Fertilizante Multiusos", 91000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("078", "Estimulante de Raíces", 92000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("079", "Fertilizante para Árboles Frutales", 93000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("080", "Fertilizante para Cactus", 94000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("081", "Insecticida para Orquídeas", 95000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("082", "Fertilizante para Bonsái", 96000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("083", "Abono para Huerto", 97000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("084", "Insecticida para Hormigas", 98000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("085", "Control de Plagas de Caracoles", 99000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("086", "Malla Protectora de Viento", 100000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("087", "Estimulador de Crecimiento", 101000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("088", "Herbicida para Pasto", 102000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("089", "Fertilizante para Semillas", 103000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("090", "Insecticida para Pulgones", 104000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("091", "Aislador para Invernaderos", 105000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("092", "Cinta para Reparar Cultivos", 106000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("093", "Fertilizante para Frutales", 107000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("094", "Abono para Plantas de Interior", 108000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("095", "Fertilizante de Liberación Lenta", 109000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("096", "Nutriente Líquido para Plantas", 110000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("097", "Malla Antiáfidos", 111000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("098", "Malla Antiheladas", 112000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("099", "Protector de Cultivo UV", 113000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("100", "Riego Automático Inteligente", 114000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("101", "Cinta de Tutoreo", 115000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("102", "Pesticida para Viñas", 116000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("103", "Insecticida para Fresas", 117000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("104", "Fertilizante para Uvas", 118000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("105", "Control de Pulgones", 119000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("106", "Sustrato para Semillas", 120000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("107", "Bolsa para Maceta", 121000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("108", "Controlador de Plagas para Jardín", 122000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("109", "Sustrato Orgánico", 123000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("110", "Fertilizante para Palmera", 124000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("111", "Fertilizante para Cítricos", 125000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("112", "Estimulador de Germinación", 126000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("113", "Insecticida para Hortalizas", 127000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("114", "Insecticida para Árboles Frutales", 128000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("115", "Aminoácidos para Plantas", 129000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("116", "Nutriente Complejo para Cultivo", 130000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("117", "Insecticida para Plantas de Interior", 131000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("118", "Controlador de Humedad para Suelo", 132000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("119", "Regulador de PH para Cultivo", 133000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("120", "Fertilizante Hidrosoluble", 134000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("121", "Enraizante para Esquejes", 135000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("122", "Controlador de Mosquitos", 136000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("123", "Nutrición para Cultivo Orgánico", 137000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("124", "Micorrizas para Suelo", 138000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("125", "Sustrato para Bonsái", 139000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("126", "Fertilizante para Jardín", 140000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("127", "Insecticida para Granos", 141000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("128", "Inoculante Biológico para Cultivo", 142000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("129", "Fertilizante para Plantas Acuáticas", 143000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("130", "Sustrato para Acuaponia", 144000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("131", "Controlador de Sales para Suelo", 145000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("132", "Estimulador de Resistencia", 146000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("133", "Nutriente para Cultivos Verticales", 147000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("134", "Fertilizante para Cultivo Hidropónico", 148000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("135", "Aminoácidos para Floración", 149000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("136", "Insecticida para Árboles Ornamentales", 150000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("137", "Sustrato para Cactus", 151000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("138", "Protector de Frutas", 152000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("139", "Abono para Vivero", 153000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("140", "Controlador de Fungos", 154000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("141", "Fertilizante para Rosales", 155000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("142", "Nutriente Complejo para Hortalizas", 156000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("143", "Aislante para Suelo", 157000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("144", "Insecticida para Césped", 158000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("145", "Controlador de Caracoles", 159000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("146", "Fertilizante para Raíces", 160000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("147", "Fertilizante de Alta Concentración", 161000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("148", "Estimulador de Floración", 162000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("149", "Regulador de pH Orgánico", 163000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("150", "Controlador de Insectos para Jardín", 164000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("151", "Estimulador de Crecimiento Radicular", 165000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("152", "Controlador de pH para Acuaponía", 166000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("153", "Micronutrientes para Suelo", 167000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("154", "Protector de Cultivo para Temperaturas Extremas", 168000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("155", "Fertilizante para Bonsái de Interior", 169000, "/images/user.jpeg"));
        listaProductos.agregarProducto(new Producto("156", "Controlador de Algas", 170000, "/images/women.jpeg"));
        listaProductos.agregarProducto(new Producto("157", "Nutriente Hidrosoluble Complejo", 171000, "/images/men.jpeg"));
        listaProductos.agregarProducto(new Producto("158", "Fertilizante para Césped de Jardín", 172000, "/images/user.jpeg")); 
    }
}