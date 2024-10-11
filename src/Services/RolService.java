package Services;
import Models.Rol;
import Models.Usuario;
import Repositorios.RolRepository;
import Repositorios.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RolService {
    private RolRepository rolRepository = new RolRepository();
    private UsuarioRepository usuarioRepository = new UsuarioRepository(); // Debes inicializar el UsuarioRepository



    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }

    public Optional<Rol> buscarRolPorId(int id) {
        return rolRepository.buscarRolPorId(id);
    }

    public List<Rol> obtenerRolesPorUsuario(int usuarioId) {
        // Buscar el usuario por su ID
        Usuario usuario = usuarioRepository.findById(usuarioId);

        // Verificar si el usuario existe
        if (usuario != null) {
            // Obtener los IDs de los roles del usuario
            List<String> rolesIds = usuario.getRoles();
            List<Rol> rolesDelUsuario = new ArrayList<>();

            // Iterar sobre los IDs de roles y buscar cada rol en el repositorio
            for (String rolIdStr : rolesIds) {
                try {
                    int rolId = Integer.parseInt(rolIdStr); // Convertir el ID de rol a int
                    Optional<Rol> rol = rolRepository.buscarRolPorId(rolId);

                    // Si se encuentra el rol, agregarlo a la lista
                    rol.ifPresent(rolesDelUsuario::add);
                } catch (NumberFormatException e) {
                    System.out.println("ID de rol no válido: " + rolIdStr);
                }
            }

            return rolesDelUsuario; // Devolver la lista de roles encontrados
        } else {
            // Si el usuario no existe, devolver una lista vacía
            System.out.println("Usuario con ID " + usuarioId + " no encontrado.");
            return List.of(); // Retornar lista vacía si no se encuentra el usuario
        }
    }

    public void crearRol(Rol rol) {
        // Verifica si ya existe un rol con el mismo ID
        Optional<Rol> rolExistente = buscarRolPorId(rol.getId());
        if (rolExistente.isPresent()) {
            System.out.println("Ya existe un rol con el ID " + rol.getId() + ". Por favor, elige otro ID.");
            return; // No se crea el rol si ya existe
        }
        // Si no existe, se llama al repositorio para crear el rol
        rolRepository.crearRol(rol);
    }

    public void actualizarRol(Rol rol) {
        rolRepository.actualizarRol(rol);
    }

    public void eliminarRol(int id) {
        rolRepository.eliminarRol(id);
    }

}
