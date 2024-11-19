package Code;

import java.io.*;

public class ArchivoUsuarios {

    private static final String RUTA_ARCHIVO = "usuarios.txt";

    public static void guardarUsuario(String correo, String nombreUsuario, String contrasena) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            bw.write(correo + ";" + nombreUsuario + ";" + contrasena);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar el usuario: " + e.getMessage());
        }
    }
    
    public static String[] leerUsuario() {
        String[] datosUsuario = null;

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            String ultimaLinea = null;

            while ((linea = br.readLine()) != null) {
                ultimaLinea = linea;
            }

            if (ultimaLinea != null) {
                datosUsuario = ultimaLinea.split(";");
            }
        } catch (IOException e) {
            System.out.println("Error al leer usuario: " + e.getMessage());
        }

        return datosUsuario; 
    }

    public static boolean validarInicioSesion(String correo, String user, String contrasena) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 3 && datos[0].equals(correo) && datos[1].equals(user) && datos[2].equals(contrasena)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al validar inicio de sesión: " + e.getMessage());
        }

        return false;
    }

    public static boolean correoExiste(String correo) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 1 && datos[0].equals(correo)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al verificar correo: " + e.getMessage());
        }

        return false;
    }
}

