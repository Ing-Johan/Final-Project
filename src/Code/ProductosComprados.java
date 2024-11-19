package Code;

/**
 *
 * @authores Johan & Yonier & Sebastián
 */

public class ProductosComprados {
    public NodoDoble<Producto> tope;

    public ProductosComprados() {
        tope = null;
    }

    public boolean getEsPilaVacia() {
        return tope == null;
    }
    
    public float getTotal(){
        if (getEsPilaVacia()) return 0;

        NodoDoble<Producto> p = tope;
        float cont = 0;

        do {
            cont = cont + p.datos.precioProd;
            p = p.sig;
        } while (p != tope);

        return cont;
    }

    public int getLongPila() {
        if (getEsPilaVacia()) return 0;

        NodoDoble<Producto> p = tope;
        int cont = 0;

        do {
            cont++;
            p = p.sig;
        } while (p != tope);

        return cont;
    }

    public NodoDoble<Producto> getBuscarId(String id) {
        if (getEsPilaVacia()) return null;

        NodoDoble<Producto> p = tope;

        do {
            if (p.datos.idProd.equals(id)) return p;
            p = p.sig;
        } while (p != tope);

        return null;
    }

    public NodoDoble<Producto> getBase() {
        if (getEsPilaVacia()) return null;

        NodoDoble<Producto> p = tope;

        while (p.sig != tope) {
            p = p.sig;
        }

        return p;
    }

    public void setPush(Producto producto) {
        NodoDoble<Producto> nuevoNodo = new NodoDoble<>(producto);

        if (getEsPilaVacia()) {
            tope = nuevoNodo;
            tope.sig = tope;
            tope.ant = tope;
        } else {
            NodoDoble<Producto> base = getBase();

            nuevoNodo.sig = tope;
            nuevoNodo.ant = base;
            base.sig = nuevoNodo;
            tope.ant = nuevoNodo;
            tope = nuevoNodo;
        }
    }

    public void setPop() {
        if (getEsPilaVacia()) {
            return;
        }

        if (tope.sig == tope) { 
            tope = null;
        } else {
            NodoDoble<Producto> base = getBase();
            NodoDoble<Producto> p = tope;

            tope = tope.sig;
            tope.ant = base;
            base.sig = tope;

            p.sig = null;
            p.ant = null;
            p = null;
        }
 
    }
    
    public void generarHistorialDeCompras(String correoUsuario) {
        if (getEsPilaVacia()) return;

        NodoDoble<Producto> p = tope;
        do {
            Producto producto = p.datos;
            ArchivoHistorial.agregarCompra(correoUsuario, producto);
            p = p.sig;
        } while (p != tope);
    }
    
}