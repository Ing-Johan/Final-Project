
package Code;

import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @authores Johan & Yonier & Sebastian
 */
public class ListaDeseos {
    
    public Nodo<Producto> inicio;
    HashMap<String, Nodo<Producto>> indiceProd;
    
    public ListaDeseos(){
        inicio = null;
        indiceProd = new HashMap<>();
    }
    
     public boolean getEsColaVacia() {
        return inicio == null;
    }

    public int getLongitudCola() {
        if (getEsColaVacia()) {
            return 0;
        } else {
            int cont = 0;
            Nodo<Producto> p = inicio;
            do {
                cont++;
                p = p.sig;
            } while (p != inicio);
            return cont;
        }
    }

    public Nodo<Producto> getBuscarProd(String id) {
        return indiceProd.get(id);
    }
    
    public boolean contieneProducto(String idProd) {
        return indiceProd.containsKey(idProd);
    }
    
    public void setAddColaH(Producto producto) {
        try {
            
            if (contieneProducto(producto.idProd)) {
                return; 
            }

            if (producto != null) {
                Nodo<Producto> a = new Nodo<>(producto);
                if (getEsColaVacia()) {
                    inicio = a;
                    inicio.sig = inicio; 
                } else {
                    Nodo<Producto> p = inicio;
                    while (p.sig != inicio) {
                        p = p.sig;
                    }
                    p.sig = a;
                    a.sig = inicio;
                }
            indiceProd.put(producto.idProd, a);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
        }
    }
    
    public void setAtender(String id) {
         if (getEsColaVacia()) {
            JOptionPane.showMessageDialog(null,"La lista de deseos está vacía.");
            return;
        }

        Nodo<Producto> a = inicio;
        Nodo<Producto> p = null;
        boolean encontrado = false;

        do {
            if (a.datos.idProd.equals(id)) {
                encontrado = true;
                if (a == inicio && inicio.sig == inicio) {  
                    inicio = null;
                } else if (a == inicio) {
                    Nodo<Producto> ultimo = inicio;
                    while (ultimo.sig != inicio) {
                        ultimo = ultimo.sig;
                    }
                    inicio = inicio.sig;
                    ultimo.sig = inicio;
                } else {  
                    p.sig = a.sig;
                }
                indiceProd.remove(id);
                break;
            }
            p = a;
            a = a.sig;
        } while (a != inicio);

        if (!encontrado) {
            JOptionPane.showMessageDialog(null,"Producto no encontrado en la lista de deseos.");
        }
    }
}
