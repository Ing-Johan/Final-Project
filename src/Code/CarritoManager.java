package Code;

/**
 *
 * @authores Johan & Yonier & Sebastian
 */
public class CarritoManager {
    private static CarritoCompras carritoCompras = new CarritoCompras();

    public static CarritoCompras getCarritoCompras() {
        return carritoCompras;
    }
    
    public static void setCarritoCompras(CarritoCompras carrito) {
        carritoCompras = carrito;
    }
}
