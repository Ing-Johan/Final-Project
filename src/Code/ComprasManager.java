package Code;

/**
 *
 * @authores Johan & Yonier & Sebastián
 */
public class ComprasManager {
    private static ProductosComprados compras = new ProductosComprados();

    public static ProductosComprados getCompras() {
        return compras;
    }
    
    public static void setCompras(ProductosComprados compra) {
        compras = compra;
    }
}
