package Code;

/**
 *
 * @author JOHAN
 */
public class NodoDoble<P> {
    public P datos;
    public NodoDoble<Producto> sig;
    public NodoDoble<Producto> ant;

    public NodoDoble(P datos) {
        this.datos = datos;
        this.sig = null;
        this.ant = null;
    }
}
