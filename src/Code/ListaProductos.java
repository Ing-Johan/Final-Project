package Code;

/**
 *
 * @authores Johan & Yonier & Sebastian
 */
public class ListaProductos {
    
    public Nodo<Producto> cabeza;
    public int tamaño,tamEncontrados;

    public ListaProductos() {
        this.cabeza = null;
        this.tamaño = 0;
    }
    
    public boolean esVacia(){
        return cabeza==null;
    }

    public void agregarProducto(Producto producto) {
        Nodo<Producto> a = new Nodo(producto);
        if (esVacia()) {
            cabeza = a;
        } else {
            ultimo().sig = a;
        }
        tamaño++;
    }
    
     public Nodo<Producto> buscarProducto(String nombre) {
        int cont=0;
        Nodo<Producto> p = cabeza;
        Nodo<Producto> resultados = null;

        while (p != null) {
            
            Producto producto =  p.datos;
            
            if (producto.nombreProd.toLowerCase().contains(nombre.toLowerCase())) {
                cont++;
                Nodo<Producto> nuevoNodo = new Nodo(producto);
                nuevoNodo.sig = resultados; 
                resultados = nuevoNodo;
            }
            p = p.sig;
        }
        tamEncontrados = cont;
        return resultados; 
    }
     
    public Nodo<Producto> ultimo(){ 
        
        Nodo<Producto> a = cabeza; 
   
        while(a != null){ 
            if(a.sig == null){ 
                break; 
            }else{ 
                a = a.sig; 
            } 
        } return a; 
    }
    
    public ListaProductos filtrarPorNombre(String searchText) {
        ListaProductos filtrados = new ListaProductos();
        Nodo<Producto> actual = cabeza;

        while (actual != null) {
            Producto producto = actual.datos;
            if (producto.nombreProd.toLowerCase().contains(searchText.toLowerCase())) {
                filtrados.agregarProducto(producto);
            }
            actual = actual.sig;
        }
        return filtrados;
    }
}
