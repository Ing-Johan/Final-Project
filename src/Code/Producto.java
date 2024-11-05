package Code;

/**
 *
 * @authores Johan & Yonier & Sebastian
 */
public class Producto {
    public String nombreProd,idProd,rutaIMG;
    public float precioProd;

    public Producto( String idProd, String nombreProd,float precioProd,String rutaIMG) {
        this.nombreProd = nombreProd;
        this.idProd = idProd;
        this.precioProd = precioProd;
        this.rutaIMG = rutaIMG;
    }

    @Override
    public String toString() {
        return """
               Producto
               
               ID : """ + idProd + "\nNombre :"  + nombreProd +  "\nPrecio :" + precioProd ;
    }
    
    
}
