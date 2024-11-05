package Code;

/**
 *
 * @authores Johan & Yonier & Sebastian
 */
public class Nodo <P>{
    public P datos;
    public Nodo<Producto> sig;
    
    public Nodo(P datos){
        this.datos = datos;
        this.sig = null;
    }
    
}
