package Controladores;

import Code.CarritoCompras;
import Code.CarritoManager;
import Code.DeseosManager;
import Code.ListaDeseos;
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
    
    public String password,sexo,email;
    public Label label = new Label("Todo el contenido de este sitio es propiedad de los ingenieros Johan Hernández, Yonier Cavadía y Sebastián Madera, y está protegido por derechos de autor.  ©2024");
    private final ListaProductos listaProductos = new ListaProductos();
    public CarritoCompras miCarrito = CarritoManager.getCarritoCompras();
    public ListaDeseos miLista = DeseosManager.getListaDeseo();
    
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
    
    public HBox crearBotones(Producto producto) {
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
        HBox.setMargin(btnCarrito, new Insets(0,20,0,0));
        hboxBtns.getChildren().addAll(btnCarrito, btnDeseo);
        return hboxBtns;
    }

    public void configurarBotonCarrito(Button btnCarrito, Producto producto) {
        btnCarrito.setOnAction(event -> {
            ImageView imageView = (ImageView) btnCarrito.getGraphic();
            if (miCarrito.contieneProducto(producto.idProd)) {
                miCarrito.setAtender(producto.idProd);
                imageView.setImage(new Image("/images/carrito-de-compras.png"));
            } else {
                miCarrito.setAddColaH(producto);
                imageView.setImage(new Image("/images/carrito-de-compras-agg.png"));
            }
        });
    }

    public void configurarBotonDeseo(Button btnDeseo, Producto producto) {
        btnDeseo.setOnAction(event -> {
            ImageView imageView = (ImageView) btnDeseo.getGraphic();
            if (miLista.contieneProducto(producto.idProd)) {
                miLista.setAtender(producto.idProd);
                imageView.setImage(new Image("/images/amar.png"));
            } else {
                miLista.setAddColaH(producto);
                imageView.setImage(new Image("/images/amor.png"));
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
            imagen.setFitWidth(80);
            imagen.setFitHeight(80);
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
        
        if(listaProductos.tamEncontrados>0) ContenedorPrincipal.getChildren().addAll(gridPane,btnBack);
        
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
        imgUser.setFitWidth(88);
        imgUser.setFitHeight(80);
        imgUser.setPreserveRatio(true); 
        btnUser.setGraphic(imgUser);
    } 
    
    public void loadStage(Event event){
        
        try{
            
            ((Node)(event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/viewUserInfo.fxml"));
            Parent root = loader.load();
            ViewUserInfoController controller = loader.getController();
            
            controller.nameUserInfo.setText(this.nameUser.getText());
            Image img = this.imgUser.getImage();
            controller.imgUserInfo.setImage(img);
            controller.genero = this.sexo;
            controller.contraseña = this.password;
            controller.correo = this.email;
            
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setTitle("AGROINSUMOS EP");
            newStage.show();
            
        }catch(IOException e){}
    }
    
    private void configurarPaginacion() {
    int totalProductos = listaProductos.tamaño; 
    int totalPaginas = (int) Math.ceil((double) totalProductos / 12);

    paginas.setPageCount(totalPaginas);
    paginas.setPageFactory(this::crearPagina);
    
    ContenedorPrincipal.getChildren().clear();
    ContenedorPrincipal.getChildren().addAll(paginas,label);
    VBox.setMargin(paginas, new Insets(0, 0, 10, 0));
}

    private GridPane crearPagina(int paginaIndex) {
        GridPane grid = new GridPane();
        mostrarProductosEnGrid(grid, listaProductos, paginaIndex, 12);
        return grid;
    }
    
    private void mostrarProductosEnGrid(GridPane grid, ListaProductos lista, int paginaIndex, int productosPorPagina) {
        grid.getChildren().clear();

        int inicio = paginaIndex * productosPorPagina;
        int fila = 0;
        int columna = 0;

        Nodo<Producto> p = lista.cabeza;
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
        listaProductos.agregarProducto(new Producto("001", "Fertilizante Orgánico", 20000, "/images/001.jpg"));
        listaProductos.agregarProducto(new Producto("002", "Insecticida Biológico", 25000, "/images/002.jpg"));
        listaProductos.agregarProducto(new Producto("003", "Herbicida Natural", 30000, "/images/003.jpg"));
        listaProductos.agregarProducto(new Producto("004", "Semilla de Maíz", 15000, "/images/004.jpg"));
        listaProductos.agregarProducto(new Producto("005", "Semilla de Arroz", 16000, "/images/005.jpg"));
        listaProductos.agregarProducto(new Producto("006", "Semilla de Trigo", 17000, "/images/006.jpg"));
        listaProductos.agregarProducto(new Producto("007", "Pesticida Orgánico", 27000, "/images/007.jpg"));
        listaProductos.agregarProducto(new Producto("008", "Controlador de Plagas", 35000, "/images/008.jpg"));
        listaProductos.agregarProducto(new Producto("009", "Abono Foliar", 28000, "/images/009.jpg"));
        listaProductos.agregarProducto(new Producto("010", "Bioestimulante", 33000, "/images/010.jpg"));
        listaProductos.agregarProducto(new Producto("011", "Semilla de Soya", 18000, "/images/011.jpg"));
        listaProductos.agregarProducto(new Producto("012", "Semilla de Cebada", 19000, "/images/012.jpg"));
        listaProductos.agregarProducto(new Producto("013", "Semilla de Frijol", 20000, "/images/013.jpg"));
        listaProductos.agregarProducto(new Producto("014", "Corrector de Suelo", 21000, "/images/014.jpg"));
        listaProductos.agregarProducto(new Producto("015", "Inoculante", 32000, "/images/015.jpg"));
        listaProductos.agregarProducto(new Producto("016", "Aminoácidos", 34000, "/images/016.jpg"));
        listaProductos.agregarProducto(new Producto("017", "Enraizante", 29000, "/images/017.jpg"));
        listaProductos.agregarProducto(new Producto("018", "Vermicompost", 36000, "/images/018.jpg"));
        listaProductos.agregarProducto(new Producto("019", "Control de Maleza", 37000, "/images/019.jpg"));
        listaProductos.agregarProducto(new Producto("020", "Herbicida Orgánico", 38000, "/images/020.jpg"));
        listaProductos.agregarProducto(new Producto("021", "Abono de Estiércol", 39000, "/images/021.jpg"));
        listaProductos.agregarProducto(new Producto("022", "Insecticida Natural", 44000, "/images/022.jpg"));
        listaProductos.agregarProducto(new Producto("023", "Aislador Eléctrico", 45000, "/images/023.jpg"));
        listaProductos.agregarProducto(new Producto("024", "Malla para Cultivo", 46000, "/images/024.jpg"));
        listaProductos.agregarProducto(new Producto("025", "Estaca de Madera", 47000, "/images/025.jpg"));
        listaProductos.agregarProducto(new Producto("026", "Riego por Goteo", 48000, "/images/026.jpg"));
        listaProductos.agregarProducto(new Producto("027", "Aspersor Agrícola", 49000, "/images/027.jpg"));
        listaProductos.agregarProducto(new Producto("028", "Kit de Riego", 50000, "/images/028.jpg"));
        listaProductos.agregarProducto(new Producto("029", "Trampa para Plagas", 51000, "/images/029.jpg"));
        listaProductos.agregarProducto(new Producto("030", "Cinta para Injertos", 52000, "/images/030.jpg"));
        listaProductos.agregarProducto(new Producto("031", "Control de Hormigas", 53000, "/images/031.jpg"));
        listaProductos.agregarProducto(new Producto("032", "Protector Solar para Cultivo", 54000, "/images/032.jpg"));
        listaProductos.agregarProducto(new Producto("033", "Cinta Agrícola", 55000, "/images/033.jpg"));
        listaProductos.agregarProducto(new Producto("034", "Varilla de Tutoreo", 56000, "/images/034.jpg"));
        listaProductos.agregarProducto(new Producto("035", "Tijera de Poda", 57000, "/images/035.jpg"));
        listaProductos.agregarProducto(new Producto("036", "Pinzas para Plantas", 58000, "/images/036.jpg"));
        listaProductos.agregarProducto(new Producto("037", "Pulverizador Manual", 59000, "/images/037.jpg"));
        listaProductos.agregarProducto(new Producto("038", "Insecticida para Ácaros", 60000, "/images/038.jpg"));
        listaProductos.agregarProducto(new Producto("039", "Rastrillo Agrícola", 61000, "/images/039.jpg"));
        listaProductos.agregarProducto(new Producto("040", "Pala Agrícola", 62000, "/images/040.jpg"));
        listaProductos.agregarProducto(new Producto("041", "Maceta Grande", 63000, "/images/041.jpg"));
        listaProductos.agregarProducto(new Producto("042", "Maceta Mediana", 64000, "/images/042.jpg"));
        listaProductos.agregarProducto(new Producto("043", "Maceta Pequeña", 65000, "/images/043.jpg"));
        listaProductos.agregarProducto(new Producto("044", "Sustrato para Plantas", 66000, "/images/044.jpeg"));
        listaProductos.agregarProducto(new Producto("045", "Bandeja para Semillas", 67000, "/images/045.jpeg"));
        listaProductos.agregarProducto(new Producto("046", "Bolsa de Vivero", 68000, "/images/046.jpg"));
        listaProductos.agregarProducto(new Producto("047", "Sustrato de Coco", 69000, "/images/047.jpg"));
        listaProductos.agregarProducto(new Producto("048", "Perlita para Plantas", 70000, "/images/048.jpg"));
        listaProductos.agregarProducto(new Producto("049", "Vermiculita para Plantas", 71000, "/images/049.jpg"));
        listaProductos.agregarProducto(new Producto("050", "Aislante Térmico", 72000, "/images/050.jpg"));
        listaProductos.agregarProducto(new Producto("051", "Cuerda para Amarre", 73000, "/images/051.jpg"));
        listaProductos.agregarProducto(new Producto("052", "Funda para Cultivos", 74000, "/images/052.jpg"));
        listaProductos.agregarProducto(new Producto("053", "Alambre Galvanizado", 75000, "/images/053.jpg"));
        listaProductos.agregarProducto(new Producto("054", "Estaca de Bambú", 76000, "/images/054.jpg"));
        listaProductos.agregarProducto(new Producto("055", "Red Anti Pájaro", 77000, "/images/055.jpg"));
        listaProductos.agregarProducto(new Producto("056", "Fertilizante Líquido", 78000, "/images/056.jpg"));
        listaProductos.agregarProducto(new Producto("057", "Corrector de pH", 79000, "/images/057.jpg"));
        listaProductos.agregarProducto(new Producto("058", "Abono de Compost", 80000, "/images/058.jpg"));
        listaProductos.agregarProducto(new Producto("059", "Enmienda Orgánica", 81000, "/images/059.jpeg"));
        listaProductos.agregarProducto(new Producto("060", "Fungicida Natural", 82000, "/images/060.jpg"));
        listaProductos.agregarProducto(new Producto("061", "Desinfectante de Suelo", 83000, "/images/061.jpg"));
        listaProductos.agregarProducto(new Producto("062", "Bomba de Aspersión", 84000, "/images/062.jpg"));
        listaProductos.agregarProducto(new Producto("063", "Medidor de Humedad", 85000, "/images/063.jpg"));
        listaProductos.agregarProducto(new Producto("064", "Termómetro de Suelo", 86000, "/images/064.jpg"));
        listaProductos.agregarProducto(new Producto("065", "Medidor de pH", 87000, "/images/065.jpg"));
        listaProductos.agregarProducto(new Producto("066", "Caja de Germinación", 88000, "/images/066.jpg"));
        listaProductos.agregarProducto(new Producto("067", "Hidratante para Suelos", 89000, "/images/067.jpg"));
        listaProductos.agregarProducto(new Producto("068", "Cinta de Goteo", 90000, "/images/068.jpg"));
        listaProductos.agregarProducto(new Producto("069", "Kit de Fertilización", 91000, "/images/069.jpg"));
        listaProductos.agregarProducto(new Producto("070", "Malla Sombra", 92000, "/images/070.jpg"));
        listaProductos.agregarProducto(new Producto("071", "Saco de Arpillera", 93000, "/images/071.jpg"));
        listaProductos.agregarProducto(new Producto("072", "Carretilla de Mano", 94000, "/images/072.jpg"));
        listaProductos.agregarProducto(new Producto("073", "Mochila Aspersora", 95000, "/images/073.jpg"));
        listaProductos.agregarProducto(new Producto("074", "Pértiga Telescópica", 96000, "/images/074.jpg"));
        listaProductos.agregarProducto(new Producto("075", "Pinzas para Tutoreo", 97000, "/images/075.jpg"));
        listaProductos.agregarProducto(new Producto("076", "Cinta Medidora", 98000, "/images/076.jpg"));
        listaProductos.agregarProducto(new Producto("077", "Control Biológico", 99000, "/images/077.jpg"));
        listaProductos.agregarProducto(new Producto("078", "Luminaria Agrícola", 100000, "/images/078.jpg"));
        listaProductos.agregarProducto(new Producto("079", "Estufa de Desinfección", 101000, "/images/079.jpg"));
        listaProductos.agregarProducto(new Producto("080", "Sensor de Riego", 102000, "/images/080.jpg"));
        listaProductos.agregarProducto(new Producto("081", "Herbicida Preemergente", 103000, "/images/081.jpg"));
        listaProductos.agregarProducto(new Producto("082", "Barra de Sujeción", 104000, "/images/082.jpg"));
        listaProductos.agregarProducto(new Producto("083", "Toldo Retráctil", 105000, "/images/083.jpg"));
        listaProductos.agregarProducto(new Producto("084", "Caldera de Vapor", 106000, "/images/084.jpg"));
        listaProductos.agregarProducto(new Producto("085", "Cámara de Secado", 107000, "/images/085.jpg"));
        listaProductos.agregarProducto(new Producto("086", "Sistema de Nebulización", 108000, "/images/086.jpg"));
        listaProductos.agregarProducto(new Producto("087", "Ventilador Agrícola", 109000, "/images/087.jpg"));
        listaProductos.agregarProducto(new Producto("088", "Tubo PVC Agrícola", 110000, "/images/088.jpg"));
        listaProductos.agregarProducto(new Producto("089", "Cañón de Riego", 111000, "/images/089.jpg"));
        listaProductos.agregarProducto(new Producto("090", "Manómetro de Suelo", 112000, "/images/090.jpg"));
        listaProductos.agregarProducto(new Producto("091", "Cuchilla para Poda", 113000, "/images/091.jpg"));
        listaProductos.agregarProducto(new Producto("092", "Regadera de Mano", 114000, "/images/092.jpg"));
        listaProductos.agregarProducto(new Producto("093", "Lámpara de Cultivo", 115000, "/images/093.jpg"));
        listaProductos.agregarProducto(new Producto("094", "Silo para Granos", 116000, "/images/094.jpg"));
        listaProductos.agregarProducto(new Producto("095", "Invernadero Modular", 117000, "/images/095.jpg"));
        listaProductos.agregarProducto(new Producto("096", "Plástico para Invernadero", 118000, "/images/096.jpg"));
        listaProductos.agregarProducto(new Producto("097", "Estufa de Cultivo", 119000, "/images/097.jpg"));
        listaProductos.agregarProducto(new Producto("098", "Controlador de Clima", 120000, "/images/098.jpg"));
        listaProductos.agregarProducto(new Producto("099", "Medidor de Fertilizante", 121000, "/images/099.jpg"));
        listaProductos.agregarProducto(new Producto("100", "Sustrato para Hidroponía", 122000, "/images/100.jpg"));
        listaProductos.agregarProducto(new Producto("101", "Sensor de Luz", 123000, "/images/101.jpg"));
        listaProductos.agregarProducto(new Producto("102", "Kit de Herramientas", 124000, "/images/102.jpg"));
        listaProductos.agregarProducto(new Producto("103", "Controlador de Riego", 125000, "/images/103.jpg"));
        listaProductos.agregarProducto(new Producto("104", "Panel Solar Agrícola", 126000, "/images/104.jpg"));
        listaProductos.agregarProducto(new Producto("105", "Tanque de Almacenamiento", 127000, "/images/105.jpg"));
        listaProductos.agregarProducto(new Producto("106", "Valvula de Riego", 128000, "/images/106.jpg"));
        listaProductos.agregarProducto(new Producto("107", "Pala de Jardín", 129000, "/images/107.jpg"));
        listaProductos.agregarProducto(new Producto("108", "Sierra de Mano", 130000, "/images/108.jpg"));
        listaProductos.agregarProducto(new Producto("109", "Cinta para Injertos", 131000, "/images/109.jpg"));
        listaProductos.agregarProducto(new Producto("110", "Extractor de Miel", 132000, "/images/110.jpg"));
        listaProductos.agregarProducto(new Producto("111", "Filtro para Fertirriego", 133000, "/images/111.jpg"));
        listaProductos.agregarProducto(new Producto("112", "Medidor de Conductividad", 134000, "/images/112.jpg"));
        listaProductos.agregarProducto(new Producto("113", "Cubeta de Plástico", 135000, "/images/113.jpg"));
        listaProductos.agregarProducto(new Producto("114", "Calibrador de Semillas", 136000, "/images/114.jpg"));
        listaProductos.agregarProducto(new Producto("115", "Sistema de Goteo", 137000, "/images/115.jpg"));
        listaProductos.agregarProducto(new Producto("116", "Medidor de Lluvia", 138000, "/images/116.jpg"));
        listaProductos.agregarProducto(new Producto("117", "Sistema Hidropónico", 139000, "/images/117.jpg"));
        listaProductos.agregarProducto(new Producto("118", "Maceta de Cultivo", 140000, "/images/118.jpg"));
        listaProductos.agregarProducto(new Producto("119", "Semillas de Frutas", 141000, "/images/119.jpg"));
        listaProductos.agregarProducto(new Producto("120", "Semillas de Hortalizas", 142000, "/images/120.jpg"));
        listaProductos.agregarProducto(new Producto("121", "Semillas de Flores", 143000, "/images/121.jpg"));
        listaProductos.agregarProducto(new Producto("122", "Planta de Aloe Vera", 144000, "/images/122.jpg"));
        listaProductos.agregarProducto(new Producto("123", "Orquídea Tropical", 145000, "/images/123.jpg"));
        listaProductos.agregarProducto(new Producto("124", "Planta de Tomate", 146000, "/images/124.jpg"));
        listaProductos.agregarProducto(new Producto("125", "Arbusto de Fresa", 147000, "/images/125.jpg"));
        listaProductos.agregarProducto(new Producto("126", "Arbusto de Mora", 148000, "/images/126.jpg"));
        listaProductos.agregarProducto(new Producto("127", "Planta de Maíz", 149000, "/images/127.jpg"));
        listaProductos.agregarProducto(new Producto("128", "Planta de Papa", 150000, "/images/128.jpg"));
        listaProductos.agregarProducto(new Producto("129", "Planta de Yuca", 151000, "/images/129.jpg"));
        listaProductos.agregarProducto(new Producto("130", "Planta de Caña", 152000, "/images/130.jpg"));
        listaProductos.agregarProducto(new Producto("131", "Árbol Frutal de Mango", 153000, "/images/131.jpg"));
        listaProductos.agregarProducto(new Producto("132", "Árbol Frutal de Naranja", 154000, "/images/132.jpg"));
        listaProductos.agregarProducto(new Producto("133", "Árbol Frutal de Limón", 155000, "/images/133.jpg"));
        listaProductos.agregarProducto(new Producto("134", "Árbol Frutal de Aguacate", 156000, "/images/134.jpg"));
        listaProductos.agregarProducto(new Producto("135", "Árbol Frutal de Guayaba", 157000, "/images/135.jpg"));
        listaProductos.agregarProducto(new Producto("136", "Árbol Frutal de Manzana", 158000, "/images/136.jpg"));
        listaProductos.agregarProducto(new Producto("137", "Árbol Frutal de Pera", 159000, "/images/137.jpg"));
        listaProductos.agregarProducto(new Producto("138", "Árbol Frutal de Durazno", 160000, "/images/138.jpg"));
        listaProductos.agregarProducto(new Producto("139", "Árbol Frutal de Uva", 161000, "/images/139.jpg"));
        listaProductos.agregarProducto(new Producto("140", "Árbol Frutal de Ciruela", 162000, "/images/140.jpg")); 
    }
}