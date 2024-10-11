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
    public static String RUTA_USUARIOS = "C:\\Users\\jaime\\Desktop\\Curso Java Academy\\GestionUsuariosJava\\src\\Data\\usuarios.csv";
    public static String RUTA_GRUPOS = "C:\\Users\\jaime\\Desktop\\Curso Java Academy\\GestionUsuariosJava\\src\\Data\\grupos.csv";

    ////////////////////////////LECTURA Y ESCRITURA DE USUARIOS////////////////////////////////////////

    public static List<Usuario> leerUsuarios(String ruta) {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_USUARIOS))) {
            String linea;
            boolean primeraLinea = true;  // Variable para verificar la primera línea (encabezados)

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;  // Saltar la primera línea (encabezados)
                    continue;
                }

                String[] campos = linea.split(",");

                // Verificar si la cantidad de campos es suficiente (5 campos)
                if (campos.length < 5) {
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
                            .map(String::trim)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    // Procesar los grupos (separados por comas) y convertirlos a una lista de strings
                    List<String> grupos = Arrays.stream(campos[5].split(",")) // Asegúrate de que el índice sea 5 para el campo de grupos
                            .map(String::trim)
                            .collect(Collectors.toList());

                    // Creamos el usuario con los departamentos y grupos y agregamos a la lista
                    usuarios.add(new Usuario(id, nombre, email, edad, departamentos, grupos, new ArrayList<>())); // Lista de grupos ya no está vacía
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir un campo numérico: " + e.getMessage() + " en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }


    // Metodo para agregar nuevo usuario
    public static void escribirUsuario(Usuario usuario) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_USUARIOS, true))) {  // Modo 'true' para agregar al archivo
            String usuarioCsv = convertirUsuarioACsv(usuario);
            bw.write(usuarioCsv);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir el usuario en el archivo CSV: " + e.getMessage());
        }
    }

    // Convierte un objeto Usuario a una línea en formato CSV
    private static String convertirUsuarioACsv(Usuario usuario) {
        String departamentos = usuario.getDepartamentos().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        // Agregar la lista de grupos también
        String grupos = usuario.getGrupos().stream()
                .collect(Collectors.joining(","));
        return usuario.getId() + "," +
                usuario.getNombre() + "," +
                usuario.getEmail() + "," +
                usuario.getEdad() + "," +
                departamentos + "," +
                grupos;
    }

    //Metodo para actualizar un usuario
    public static void sobreEscribirUsuarios(List<Usuario> usuarios) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_USUARIOS))) {
            // Escribir encabezados (si los tienes)
            bw.write("ID,Nombre,Email,Edad,Departamentos");
            bw.newLine();

            for (Usuario usuario : usuarios) {
                String usuarioCsv = convertirUsuarioACsv(usuario);
                bw.write(usuarioCsv);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir usuarios en el archivo CSV: " + e.getMessage());
        }
    }


    //////////////////////////////LECTURA Y ESCRITURA DE GRUPOS//////////////////////////////////////
    public static List<Grupo> leerGrupos(String ruta) {
        List<Grupo> grupos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_GRUPOS))) {
            String linea;
            boolean primeraLinea = true;  // Variable para verificar la primera línea (encabezados)

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;  // Saltar la primera línea (encabezados)
                    continue;
                }

                String[] campos = linea.split(",");

                // Verificar si la cantidad de campos es suficiente (3 campos)
                if (campos.length < 3) {
                    System.err.println("Línea incompleta o formato incorrecto: " + linea);
                    continue;  // Saltar esta línea y seguir con la siguiente
                }

                // Intentar convertir los campos necesarios
                try {
                    int id = Integer.parseInt(campos[0]); // Convertir a int
                    String nombre = campos[1];
                    String descripcion = campos[2];

                    // Crear el grupo y agregarlo a la lista, inicializando la lista de usuarios como vacía
                    grupos.add(new Grupo(id, nombre, descripcion, new ArrayList<>()));
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir un campo numérico: " + e.getMessage() + " en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grupos;
    }


    public static void escribirGrupo(Grupo grupo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_GRUPOS, true))) { // Modo 'true' para agregar al archivo
            String grupoCsv = convertirGrupoACsv(grupo);
            bw.write(grupoCsv);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir el grupo en el archivo CSV: " + e.getMessage());
        }
    }


    // Método para sobrescribir el archivo CSV con la lista actualizada de grupos
    public static void sobreEscribirGrupos(List<Grupo> grupos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_GRUPOS))) {
            // Escribir encabezados (si los tienes)
            bw.write("ID,Nombre,Descripción"); // Cambia esto según tus encabezados
            bw.newLine();

            for (Grupo grupo : grupos) {
                String grupoCsv = convertirGrupoACsv(grupo);
                bw.write(grupoCsv);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir grupos en el archivo CSV: " + e.getMessage());
        }
    }

    // Convierte un objeto Grupo a una línea en formato CSV
    private static String convertirGrupoACsv(Grupo grupo) {
        return grupo.getId() + "," + grupo.getNombre() + "," + grupo.getDescripcion();
    }
}
