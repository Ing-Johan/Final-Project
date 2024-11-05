package Code;

import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @authores Johan & Yonier & Sebastian
 */
public class CarritoCompras {
    Nodo<Producto> inicio;
    HashMap<String, Nodo<Producto>> indiceProd;
    
    public CarritoCompras(){
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
    
    public void setAddColaH(Producto producto) {
        try {
            
            if (indiceProd.containsKey(producto.idProd)) {
                JOptionPane.showMessageDialog(null,"Error: Ya existe una cuenta con ese número. Ingrese un número de cuenta diferente.");
                
                return; 
            }

            if (producto != null) {
                Nodo<Producto> info = new Nodo<>(producto);
                if (getEsColaVacia()) {
                    inicio = info;
                } else {
                    info.sig = inicio;
                    info = inicio;
                }
                // Agrega el nodo a la tabla hash para que sea fácil de buscar
                indiceProd.put(producto.idProd, info);
                JOptionPane.showMessageDialog(null, "Elemento registrado correctamente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
        }
    }
    
    public void setAtender() {
        if (getEsColaVacia()) {
            JOptionPane.showMessageDialog(null,"El carrito esta vacío, sin nada que atender!");
        } else {
            Nodo<Producto> p = inicio;
            String id = p.datos.idProd;
            if (inicio.sig == inicio) {
                inicio = null;
            } else {
                inicio = inicio.sig;
                p.sig = null;
                p = null;
            }
            indiceProd.remove(id);
            JOptionPane.showMessageDialog(null,"Producto atendido!");
        }
    }
}
