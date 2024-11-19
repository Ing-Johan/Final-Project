package Code;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoHistorial {

    private static final String RUTA_ARCHIVO = "historial_compras.txt";

    public static void agregarCompra(String correoUsuario, Producto producto) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            bw.write(correoUsuario + "," + producto.idProd + "," + producto.nombreProd + "," + producto.precioProd + "," + producto.rutaIMG);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar el historial: " + e.getMessage());
        }
    }
    
    public static List<String[]> cargarHistorialPorUsuario(String correo) {
        List<String[]> historial = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[0].equals(correo)) {
                    historial.add(new String[]{datos[1], datos[2], datos[3]});
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar historial: " + e.getMessage());
        }

        return historial;
    }
    
}


