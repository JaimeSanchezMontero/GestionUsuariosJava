package Controllers;

import Models.Usuario;
import Models.Departamento;
import Models.Grupo;
import Models.Rol;
import Services.UsuarioService;
//import Services.DepartamentoService;
//import Services.GrupoService;
//import Services.RolService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainController {
    private UsuarioService usuarioService;
//    private DepartamentoService departamentoService;
//    private GrupoService grupoService;
//    private RolService rolService;
    private Scanner scanner;

    public MainController() {
        usuarioService = new UsuarioService();
//        departamentoService = new DepartamentoService();
//        grupoService = new GrupoService();
//        rolService = new RolService();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    manejarUsuarios();
                    break;
                case 2:
//                    manejarDepartamentos();
                    break;
                case 3:
//                    manejarGrupos();
                    break;
                case 4:
//                    manejarRoles();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n---- Menú Principal ----");
        System.out.println("1. Gestión de Usuarios");
        System.out.println("2. Gestión de Departamentos");
        System.out.println("3. Gestión de Grupos");
        System.out.println("4. Gestión de Roles");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    private void manejarUsuarios() {
        System.out.println("\n---- Gestión de Usuarios ----");
        // Implementar opciones de usuario aquí (listar, buscar, crear, actualizar, eliminar)
        int opcion;
        do {
            mostrarMenuUsuarios();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    listarUsuarios();
                    break;
                case 2:
                    buscarUsuarioPorId();
                    break;
                case 3:
                    buscarUsuariosPorNombre();
                    break;
                case 4:
                    buscarUsuariosPorDepartamento();
                    break;
                case 5:
                    crearUsuario();
                    break;
                case 6:
//                    actualizarUsuario();
                    break;
                case 7:
//                    eliminarUsuario();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuUsuarios() {
        System.out.println("\n---- Menú de Usuarios ----");
        System.out.println("1. Listar todos los usuarios");
        System.out.println("2. Buscar usuario por ID");
        System.out.println("3. Buscar usuarios por nombre");
        System.out.println("4. Buscar usuarios por departamento");
        System.out.println("5. Crear nuevo usuario");
        System.out.println("6. Actualizar usuario");
        System.out.println("7. Eliminar usuario");
        System.out.println("0. Volver");
        System.out.print("Elige una opción: ");
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        System.out.println("Lista de Usuarios:");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private void buscarUsuarioPorId() {
        System.out.print("Introduce el ID del usuario a buscar: ");
        int id = scanner.nextInt();
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario != null) {
            System.out.println("Usuario encontrado: " + usuario);
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private void buscarUsuariosPorNombre() {
        System.out.print("Introduce el nombre a buscar: ");
        String nombre = scanner.nextLine();

        // Busca los usuarios que coincidan parcialmente con el nombre
        List<Usuario> usuarios = usuarioService.buscarUsuariosPorNombre(nombre);

        System.out.println("Usuarios encontrados:");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private void buscarUsuariosPorDepartamento() {  //
        System.out.print("Introduce el ID del departamento a buscar: ");
        int departamentoId = scanner.nextInt();

        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorDepartamento(departamentoId);

        if (usuarios.isEmpty()) {
            System.out.println("No se encontraron usuarios en el departamento.");
        } else {
            System.out.println("Usuarios en el departamento " + departamentoId + ":");
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
        }
    }

    private void crearUsuario() {
        System.out.println("---- Crear Nuevo Usuario ----");

        System.out.print("Introduce el ID del usuario: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consumir la línea nueva

        // Verifica si el ID ya existe
        if (usuarioService.buscarUsuarioPorId(id) != null) {
            System.out.println("Error: Ya existe un usuario con el ID " + id);
            return; // Salir del método si el ID ya existe
        }

        System.out.print("Introduce el nombre del usuario: ");
        String nombre = scanner.nextLine();

        System.out.print("Introduce el email del usuario: ");
        String email = scanner.nextLine();

        System.out.print("Introduce la edad del usuario: ");
        int edad = scanner.nextInt();
        scanner.nextLine();  // Consumir la línea nueva

        System.out.print("Introduce los IDs de los departamentos a los que pertenece (separados por comas): ");
        String departamentosInput = scanner.nextLine();
        List<Integer> departamentos = Arrays.stream(departamentosInput.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // Crear un nuevo usuario con la lista de departamentos
        Usuario nuevoUsuario = new Usuario(id, nombre, email, edad, departamentos, new ArrayList<>());

        // Llamar al servicio para crear el usuario
        usuarioService.crearUsuario(nuevoUsuario);

        System.out.println("Usuario creado y agregado al CSV exitosamente.");
    }
}
