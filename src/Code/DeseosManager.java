package Code;

/**
 *
 * @authores Johan & Yonier & Sebastian
 */
public class DeseosManager {
    private static ListaDeseos deseos = new ListaDeseos();

    public static ListaDeseos getListaDeseo() {
        return deseos;
    }
    
    public static void setListaDeseo(ListaDeseos deseo) {
        deseos = deseo;
    }
}