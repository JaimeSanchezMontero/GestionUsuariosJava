package Controllers;
import Models.Usuario;
import Models.Departamento;
import Models.Grupo;
import Models.Rol;
import Services.UsuarioService;
import Services.DepartamentoService;
import Services.GrupoService;
import Services.RolService;

import java.util.*;
import java.util.stream.Collectors;

public class MainController {
    private UsuarioService usuarioService;
    private GrupoService grupoService;
    private DepartamentoService departamentoService;
    private RolService rolService;

    private Scanner scanner;

    public MainController() {
        usuarioService = new UsuarioService();
        grupoService = new GrupoService();
        departamentoService = new DepartamentoService();
        rolService = new RolService();

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
                    manejarDepartamentos();
                    break;
                case 3:
                    manejarGrupos();
                    break;
                case 4:
                    manejarRoles();
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

    /////////////////////////////METODOS PARA USUARIOS//////////////////////////7

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
                    actualizarUsuario();
                    break;
                case 7:
                    eliminarUsuario();
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

        // Mostrar los grupos disponibles
        List<Grupo> gruposDisponibles = grupoService.obtenerTodosLosGrupos();
        System.out.println("Grupos disponibles:");
        for (Grupo grupo : gruposDisponibles) {
            System.out.println(grupo.getId() + ": " + grupo.getNombre());
        }

        System.out.print("Introduce el ID del grupo al que pertenece (dejar en blanco si no pertenece a ningún grupo): ");
        String grupoInput = scanner.nextLine();
        List<String> gruposUsuario = new ArrayList<>();

        if (!grupoInput.isEmpty()) {
            try {
                int grupoId = Integer.parseInt(grupoInput);
                // Verificar si el grupo existe
                if (gruposDisponibles.stream().anyMatch(grupo -> grupo.getId() == grupoId)) {
                    gruposUsuario.add(grupoInput); // Agregar el ID del grupo a la lista
                } else {
                    System.out.println("Error: El grupo con ID " + grupoId + " no existe.");
                    return; // Salir si el grupo no existe
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: El ID del grupo debe ser un número entero válido.");
                return; // Salir si la entrada no es válida
            }
        }


        // Obtener todos los roles disponibles
        List<Rol> rolesDisponibles = rolService.obtenerTodosLosRoles();
        System.out.println("Roles disponibles:");
        for (Rol rol : rolesDisponibles) {
            System.out.println(rol.getId() + ": " + rol.getNombre());
        }

        System.out.print("Introduce los IDs de los roles a asignar al usuario (separados por comas): ");
        String rolesInput = scanner.nextLine();
        List<String> rolesUsuario = Arrays.stream(rolesInput.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        // Crear un nuevo usuario con la lista de departamentos, grupos y roles
        Usuario nuevoUsuario = new Usuario(id, nombre, email, edad, departamentos, gruposUsuario, rolesUsuario);

        // Llamar al servicio para crear el usuario
        usuarioService.crearUsuario(nuevoUsuario);

        System.out.println("Usuario creado y agregado al CSV exitosamente.");
    }

    private void actualizarUsuario() {
        System.out.println("---- Actualizar Usuario ----");

        // Solicitar el ID del usuario a actualizar
        System.out.print("Introduce el ID del usuario a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consumir la línea nueva

        // Buscar el usuario por ID
        Usuario usuarioExistente = usuarioService.buscarUsuarioPorId(id);

        // Verificar si el usuario existe
        if (usuarioExistente == null) {
            System.out.println("No se encontró un usuario con ID: " + id);
            return; // Salir del método si el usuario no existe
        }

        // Mostrar información actual del usuario
        System.out.println("Usuario encontrado: " + usuarioExistente);

        // Pedir nueva información al usuario
        System.out.print("Introduce el nuevo nombre (dejar en blanco para no cambiar): ");
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.isEmpty()) {
            usuarioExistente.setNombre(nuevoNombre);
        }

        System.out.print("Introduce el nuevo email (dejar en blanco para no cambiar): ");
        String nuevoEmail = scanner.nextLine();
        if (!nuevoEmail.isEmpty()) {
            usuarioExistente.setEmail(nuevoEmail);
        }

        System.out.print("Introduce la nueva edad (0 para no cambiar): ");
        int nuevaEdad = scanner.nextInt();
        scanner.nextLine();  // Consumir la línea nueva
        if (nuevaEdad > 0) {
            usuarioExistente.setEdad(nuevaEdad);
        }

        System.out.print("Introduce los nuevos IDs de los departamentos (separados por comas, dejar en blanco para no cambiar): ");
        String departamentosInput = scanner.nextLine();
        if (!departamentosInput.isEmpty()) {
            List<Integer> nuevosDepartamentos = Arrays.stream(departamentosInput.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            usuarioExistente.setDepartamentos(nuevosDepartamentos);
        }

        // Llamar al servicio para actualizar el usuario
        usuarioService.actualizarUsuario(usuarioExistente);

        System.out.println("Usuario actualizado exitosamente.");
    }

    public void eliminarUsuario() {
        System.out.println("---- Eliminar Usuario ----");

        // Solicitar el ID del usuario a eliminar
        System.out.print("Introduce el ID del usuario a eliminar: ");
        int id = scanner.nextInt();  // Se asume que el usuario ingresa un int
        scanner.nextLine();  // Consumir la línea nueva

        // Llamar al servicio para eliminar el usuario
        try {
            usuarioService.eliminarUsuario(id);
            System.out.println("Usuario eliminado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    ///////////////////////////MÉTODOS PARA GRUPOS////////////////////////////////////////77


    private void manejarGrupos() {
        System.out.println("\n---- Gestión de Grupos ----");
        int opcion;
        do {
            mostrarMenuGrupos();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    listarGrupos();
                    break;
                case 2:
                    buscarGrupoPorId();
                    break;
                case 3:
                    buscarGruposPorUsuario();
                case 4:
                    crearGrupo();
                    break;
                case 5:
                    actualizarGrupo();
                    break;
                case 6:
                    eliminarGrupo();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuGrupos() {
        System.out.println("\n---- Menú de Grupos ----");
        System.out.println("1. Listar todos los grupos");
        System.out.println("2. Buscar grupo por ID");
        System.out.println("3. Buscar grupos por usuario");
        System.out.println("4. Crear nuevo grupo");
        System.out.println("5. Actualizar grupo");
        System.out.println("6. Eliminar grupo");
        System.out.println("0. Volver");
        System.out.print("Elige una opción: ");
    }

    private void listarGrupos() {
        List<Grupo> grupos = grupoService.obtenerTodosLosGrupos();
        System.out.println("Lista de Grupos:");
        for (Grupo grupo : grupos) {
            System.out.println(grupo);
        }
    }

    private void buscarGrupoPorId() {
        System.out.println("---- Buscar Grupo por ID ----");

        System.out.print("Introduce el ID del grupo: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Grupo grupo = grupoService.buscarGrupoPorId(id);

        if (grupo != null) {
            System.out.println("Grupo encontrado:");
            System.out.println("ID: " + grupo.getId());
            System.out.println("Nombre: " + grupo.getNombre());
            System.out.println("Descripción: " + grupo.getDescripcion());

        } else {
            System.out.println("Error: No se encontró un grupo con el ID " + id);
        }
    }

    private void buscarGruposPorUsuario() {
        System.out.println("---- Buscar Grupos por Usuario ----");

        System.out.print("Introduce el ID del usuario: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();

        // Llamamos al metodo en el GrupoService para obtener los grupos
        List<Grupo> grupos = grupoService.obtenerGruposPorUsuario(usuarioId);

        if (!grupos.isEmpty()) {
            System.out.println("Grupos a los que pertenece el usuario con ID " + usuarioId + ":");
            for (Grupo grupo : grupos) {
                System.out.println("ID: " + grupo.getId() + ", Nombre: " + grupo.getNombre() + ", Descripción: " + grupo.getDescripcion());
            }
        } else {
            System.out.println("Error: El usuario con ID " + usuarioId + " no pertenece a ningún grupo.");
        }
    }

    // Método para crear un nuevo grupo
    private void crearGrupo() {
        System.out.println("---- Crear Nuevo Grupo ----");

        System.out.print("Introduce el ID del grupo: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        System.out.print("Introduce el nombre del grupo: ");
        String nombre = scanner.nextLine();

        System.out.print("Introduce la descripción del grupo: ");
        String descripcion = scanner.nextLine();

        // Crear el nuevo grupo
        Grupo nuevoGrupo = new Grupo(id, nombre, descripcion, new ArrayList<>()); // Asumiendo que el constructor de Grupo acepta estos parámetros

        // Llamar al metodo en el servicio para crear el grupo
        grupoService.crearGrupo(nuevoGrupo);
        System.out.println("Grupo creado exitosamente.");
    }

    // Metodo para actualizar la información de un grupo
    private void actualizarGrupo() {
        System.out.println("---- Actualizar Grupo ----");
        System.out.print("Introduce el ID del grupo a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        Grupo grupoExistente = grupoService.buscarGrupoPorId(id);
        if (grupoExistente == null) {
            System.out.println("Error: No se encontró un grupo con el ID " + id);
            return;
        }

        // Pedir nuevos datos para actualizar
        System.out.print("Nuevo nombre (actual: " + grupoExistente.getNombre() + "): ");
        String nuevoNombre = scanner.nextLine();

        System.out.print("Nueva descripción (actual: " + grupoExistente.getDescripcion() + "): ");
        String nuevaDescripcion = scanner.nextLine();

        // Conservar los valores existentes si el usuario deja el campo vacío
        if (nuevoNombre.isEmpty()) {
            nuevoNombre = grupoExistente.getNombre(); // Conservar el nombre actual
        }
        if (nuevaDescripcion.isEmpty()) {
            nuevaDescripcion = grupoExistente.getDescripcion(); // Conservar la descripción actual
        }

        // Crear un nuevo objeto Grupo con los datos actualizados
        Grupo grupoActualizado = new Grupo(id, nuevoNombre, nuevaDescripcion, grupoExistente.getUsuarios()); // Preserva los usuarios

        // Llamar al servicio para actualizar el grupo
        boolean actualizado = grupoService.actualizarGrupo(grupoActualizado);
        if (actualizado) {
            System.out.println("Grupo actualizado exitosamente.");
        } else {
            System.out.println("Error: No se pudo actualizar el grupo.");
        }
    }

    private void eliminarGrupo() {
        System.out.println("---- Eliminar Grupo ----");

        System.out.print("Introduce el ID del grupo a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Llamar al servicio para eliminar el grupo
        boolean eliminado = grupoService.eliminarGrupo(id);

        if (eliminado) {
            System.out.println("Grupo eliminado exitosamente.");
        } else {
            System.out.println("Error: No se encontró un grupo con el ID " + id);
        }
    }

    //////////////////////////////////METODOS PARA DEPARTAMENTO////////////////////////////////

    private void manejarDepartamentos() {
        System.out.println("\n---- Gestión de Departamentos ----");
        int opcion;
        do {
            mostrarMenuDepartamentos();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    mostrarDepartamentos();
                    break;
                case 2:
                    buscarDepartamentoPorId();
                    break;
                case 3:
                    buscarDepartamentosPorNombre();
                    break;
                case 4:
                    buscarDepartamentosPorUsuario();
                case 5:
                    crearDepartamento();
                case 6:
                    actualizarDepartamento();
                    break;
                case 7:
                    eliminarDepartamento();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuDepartamentos() {
        System.out.println("\n---- Menú de Departamentos ----");
        System.out.println("1. Listar todos los departamentos");
        System.out.println("2. Buscar departamento por ID");
        System.out.println("3. Buscar departamento por nombre");
        System.out.println("4. Buscar departamento por usuario");
        System.out.println("5. Crear nuevo departamento");
        System.out.println("6. Actualizar departamento");
        System.out.println("7. Eliminar departamento");
        System.out.println("0. Volver");
        System.out.print("Elige una opción: ");
    }


    private void mostrarDepartamentos() {
        List<Departamento> departamentos = departamentoService.obtenerTodosLosDepartamentos();
        System.out.println("Lista de Departamentos:");
        for (Departamento departamento : departamentos) {
            System.out.println(departamento);
        }
    }

    private void buscarDepartamentoPorId() {
        System.out.print("Introduce el ID del departamento a buscar: ");
        int id = scanner.nextInt();
        Departamento departamento = departamentoService.buscarDepartamentoPorId(id);
        if (departamento != null) {
            System.out.println("Departamento encontrado: " + departamento);
        } else {
            System.out.println("Departamento no encontrado.");
        }
    }


    private void buscarDepartamentosPorNombre() {
        System.out.print("Introduce el nombre del departamento a buscar: ");
        String nombre = scanner.nextLine();

        // Llama al servicio para buscar los departamentos por nombre
        List<Departamento> departamentos = departamentoService.buscarDepartamentosPorNombre(nombre);

        if (departamentos.isEmpty()) {
            System.out.println("No se encontraron departamentos que coincidan con el nombre proporcionado.");
        } else {
            System.out.println("Departamentos encontrados:");
            for (Departamento departamento : departamentos) {
                System.out.println(departamento);
            }
        }
    }

    private void buscarDepartamentosPorUsuario() {
        System.out.print("Introduce el ID del usuario: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer del scanner

        // Llama al servicio para obtener los departamentos por usuario
        List<Departamento> departamentos = departamentoService.obtenerDepartamentosPorUsuario(usuarioId);

        // Comprobar si se encontraron departamentos
        if (departamentos.isEmpty()) {
            System.out.println("No se encontraron departamentos para el usuario con ID: " + usuarioId);
        } else {
            System.out.println("Departamentos encontrados para el usuario ID " + usuarioId + ":");
            for (Departamento departamento : departamentos) {
                System.out.println(departamento);
            }
        }
    }

    private void crearDepartamento() {
        System.out.print("Introduce el ID del nuevo departamento: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Verifica si el departamento con el ID ingresado ya existe
        while (departamentoService.buscarDepartamentoPorId(id) != null) {
            System.out.println("El ID del departamento ya existe. Por favor, introduce un ID único:");
            id = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
        }

        System.out.print("Introduce el nombre del nuevo departamento: ");
        String nombre = scanner.nextLine();

        System.out.print("Introduce la descripción del nuevo departamento: ");
        String descripcion = scanner.nextLine();

        // Inicializar la lista de usuarios como vacía (puedes cambiar esto si es necesario)
        List<String> usuarios = new ArrayList<>();

        // Crear el departamento
        Departamento nuevoDepartamento = new Departamento(id, nombre, descripcion, usuarios);

        // Llamar al servicio para crear el departamento
        departamentoService.crearDepartamento(nuevoDepartamento);

        System.out.println("Departamento creado con éxito.");
    }

    private void actualizarDepartamento() {
        System.out.print("Introduce el ID del departamento a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Busca el departamento por ID
        Departamento departamento = departamentoService.buscarDepartamentoPorId(id);

        if (departamento == null) {
            System.out.println("No se encontró el departamento con ID: " + id);
            return;
        }

        // Muestra los datos actuales del departamento
        System.out.println("Datos actuales del departamento: " + departamento);

        System.out.print("Introduce el nuevo nombre del departamento (o presiona Enter para mantener el actual): ");
        String nuevoNombre = scanner.nextLine();
        if (nuevoNombre.isEmpty()) {
            nuevoNombre = departamento.getNombre(); // Mantiene el nombre actual si no se ingresa uno nuevo
        }

        System.out.print("Introduce la nueva descripción del departamento (o presiona Enter para mantener la actual): ");
        String nuevaDescripcion = scanner.nextLine();
        if (nuevaDescripcion.isEmpty()) {
            nuevaDescripcion = departamento.getDescripcion(); // Mantiene la descripción actual si no se ingresa una nueva
        }

        // Crea un nuevo objeto Departamento con los datos actualizados
        Departamento departamentoActualizado = new Departamento(id, nuevoNombre, nuevaDescripcion, departamento.getUsuarios());

        // Llama al servicio para actualizar el departamento
        departamentoService.actualizarDepartamento(departamentoActualizado);

        System.out.println("Departamento actualizado con éxito.");
    }

    private void eliminarDepartamento() {
        System.out.print("Introduce el ID del departamento a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Llama al servicio para eliminar el departamento
        departamentoService.eliminarDepartamento(id);

        System.out.println("Departamento eliminado con éxito.");
    }


    ///////////////////////////////////////METODOS PARA ROLES//////////////////////////////////////////


    private void manejarRoles() {
        System.out.println("\n---- Gestión de Roles ----");
        int opcion;
        do {
            mostrarMenuRoles();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    mostrarRoles();
                    break;
                case 2:
                    buscarRolPorId();
                    break;
                case 3:
                    buscarRolesPorUsuario();
                    break;
                case 4:
                    crearRol();
                    break;
                case 5:
                    actualizarRol();
                    break;
                case 6:
                    eliminarRol();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuRoles() {
        System.out.println("\n---- Menú de Roles ----");
        System.out.println("1. Listar todos los roles");
        System.out.println("2. Buscar rol por ID");
        System.out.println("3. Buscar rol por usuario");
        System.out.println("4. Crear nuevo rol");
        System.out.println("5. Actualizar rol");
        System.out.println("6. Eliminar rol");
        System.out.println("0. Volver");
        System.out.print("Elige una opción: ");
    }

    private void mostrarRoles() {
        List<Rol> roles = rolService.obtenerTodosLosRoles(); // Llama al servicio

        if (roles.isEmpty()) {
            System.out.println("No se encontraron roles.");
        } else {
            System.out.println("Lista de roles:");
            for (Rol rol : roles) {
                System.out.println("ID: " + rol.getId() + ", Nombre: " + rol.getNombre() + ", Permisos: " + rol.getPermisos());
            }
        }
    }

    private void buscarRolPorId() {
        System.out.print("Introduce el ID del rol que quieres buscar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea

        Optional<Rol> rolOpt = rolService.buscarRolPorId(id);

        if (rolOpt.isPresent()) {
            Rol rol = rolOpt.get();
            System.out.println("ID: " + rol.getId() + ", Nombre: " + rol.getNombre() + ", Permisos: " + rol.getPermisos());
        } else {
            System.out.println("No se encontró ningún rol con el ID: " + id);
        }
    }

    private void buscarRolesPorUsuario() {
        System.out.print("Introduce el ID del usuario: ");
        int usuarioId = scanner.nextInt();
        scanner.nextLine();

        List<Rol> roles = rolService.obtenerRolesPorUsuario(usuarioId);

        if (roles.isEmpty()) {
            System.out.println("El usuario con ID " + usuarioId + " no tiene roles asignados o no existe.");
        } else {
            System.out.println("Roles asignados al usuario con ID " + usuarioId + ":");
            for (Rol rol : roles) {
                System.out.println("ID del Rol: " + rol.getId() + ", Nombre del Rol: " + rol.getNombre());
            }
        }
    }

    private void crearRol() {
        System.out.print("Ingrese el ID del nuevo rol: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Verifica si el rol con el ID ingresado ya existe
        while (rolService.buscarRolPorId(id).isPresent()) {
            System.out.println("El ID del rol ya existe. Por favor, introduce un ID único:");
            id = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
        }

        System.out.print("Ingrese el nombre del rol: ");
        String nombre = scanner.nextLine().trim(); // Captura el nombre del rol

        System.out.print("Ingrese los permisos del rol (separados por comas): ");
        String permisosInput = scanner.nextLine().trim(); // Captura los permisos como una cadena

        // Convertir la cadena de permisos en una lista
        List<String> permisos = List.of(permisosInput.split(","));

        // Crear el nuevo rol
        Rol nuevoRol = new Rol(id, nombre, permisos);

        // Llamar al método de crear rol en el servicio
        rolService.crearRol(nuevoRol);

        System.out.println("Rol creado exitosamente.");
    }

    private void actualizarRol() {
        System.out.print("Ingrese el ID del rol a actualizar: ");
        int id = Integer.parseInt(scanner.nextLine().trim()); // Captura el ID del rol

        Optional<Rol> rolExistente = rolService.buscarRolPorId(id);

        if (rolExistente.isPresent()) {
            Rol rol = rolExistente.get();

            System.out.print("Ingrese el nuevo nombre del rol (actual: " + rol.getNombre() + "): ");
            String nombre = scanner.nextLine().trim(); // Captura el nuevo nombre del rol

            System.out.print("Ingrese los nuevos permisos del rol (separados por comas) (actual: " + String.join(", ", rol.getPermisos()) + "): ");
            String permisosInput = scanner.nextLine().trim(); // Captura los permisos como una cadena

            // Convertir la cadena de permisos en una lista
            List<String> permisos = List.of(permisosInput.split(","));

            // Llamar al método de actualizar rol en el servicio
            rolService.actualizarRol(new Rol(id, nombre, permisos));
            System.out.println("Rol actualizado exitosamente.");
        } else {
            System.out.println("No se encontró un rol con el ID " + id);
        }
    }

    private void eliminarRol() {
        System.out.print("Introduce el ID del rol que deseas eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        rolService.eliminarRol(id);
        System.out.println("Rol eliminado exitosamente.");
    }



}

