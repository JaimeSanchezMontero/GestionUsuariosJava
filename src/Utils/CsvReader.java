package Utils;

import Models.Usuario;
import Models.Departamento;
import Models.Grupo;
import Models.Rol;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class CsvReader {
    public static List<Usuario> leerUsuarios(String ruta) {
        List<Usuario> usuarios = new ArrayList<>();

        final String RUTA_USUARIOS = "C:\\Users\\jaime\\Desktop\\Curso Java Academy\\GestionUsuariosJava\\src\\Data\\usuarios.csv";


        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_USUARIOS))) {
            String linea;
            boolean primeraLinea = true;  // Variable para verificar la primera línea (encabezados)

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;  // Saltar la primera línea (encabezados)
                    continue;
                }

                String[] campos = linea.split(",");

                // Verificar si la cantidad de campos es suficiente (4 campos)
                if (campos.length < 4) {
                    System.err.println("Línea incompleta o formato incorrecto: " + linea);
                    continue;  // Saltar esta línea y seguir con la siguiente
                }

                // Intentar convertir los campos necesarios
                try {
                    int id = Integer.parseInt(campos[0]); // Convertir a int
                    String nombre = campos[1];
                    String email = campos[2];
                    int edad = Integer.parseInt(campos[3]);

                    // Procesar los departamentos (separados por comas) y convertirlos a una lista de enteros
                    List<Integer> departamentos = Arrays.stream(campos[4].split(","))
                            .map(String::trim)  // Eliminar espacios en blanco
                            .map(Integer::parseInt)  // Convertir a enteros
                            .collect(Collectors.toList());

                    // Crear el usuario con los departamentos y agregarlo a la lista
                    usuarios.add(new Usuario(id, nombre, email, edad, departamentos, new ArrayList<>())); // Lista de roles vacía por ahora
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir un campo numérico: " + e.getMessage() + " en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }


    // Nuevo método para agregar un usuario al archivo CSV
    public static void escribirUsuario(Usuario usuario) {
        final String RUTA_USUARIOS = "C:\\Users\\jaime\\Desktop\\Curso Java Academy\\GestionUsuariosJava\\src\\Data\\usuarios.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_USUARIOS, true))) {  // Modo 'true' para agregar al archivo
            String usuarioCsv = convertirUsuarioACsv(usuario);
            bw.write(usuarioCsv);
            bw.newLine();  // Añadir una nueva línea después del usuario
        } catch (IOException e) {
            System.err.println("Error al escribir el usuario en el archivo CSV: " + e.getMessage());
        }
    }

    // Convierte un objeto Usuario a una línea en formato CSV
    private static String convertirUsuarioACsv(Usuario usuario) {
        String departamentos = usuario.getDepartamentos().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));  // Convertir la lista de departamentos a una cadena separada por comas
        return usuario.getId() + "," +
                usuario.getNombre() + "," +
                usuario.getEmail() + "," +
                usuario.getEdad() + "," +
                departamentos;  // Formato de salida CSV
    }
}
