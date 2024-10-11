package Repositorios;
import Models.Rol;
import Utils.CsvReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RolRepository {
    private List<Rol> roles;

    public RolRepository() {
        this.roles = CsvReader.leerRoles("Data/roles.csv");
    }

    // Método para obtener todos los roles
    public List<Rol> findAll() {
        return new ArrayList<>(roles);
    }

    public Optional<Rol> buscarRolPorId(int id) {
        return roles.stream()
                .filter(rol -> rol.getId() == id)
                .findFirst();  // Retorna el primer rol que coincida con el ID, si existe
    }

    public void crearRol(Rol rol) {
        // Llama al CsvReader para escribir el nuevo rol
        CsvReader.escribirRol(rol);
    }

    public void actualizarRol(Rol rol) {
        // Actualiza la lista de roles
        Optional<Rol> rolExistente = buscarRolPorId(rol.getId());
        if (rolExistente.isPresent()) {
            // Actualizamos el rol existente
            roles.remove(rolExistente.get());
            roles.add(rol);
            // Sobrescribimos el archivo CSV con la lista actualizada de roles
            CsvReader.sobrescribirRoles(roles);
        } else {
            System.out.println("No se encontró un rol con el ID " + rol.getId());
        }
    }

    public void eliminarRol(int id) {
        Optional<Rol> rolExistente = buscarRolPorId(id);
        if (rolExistente.isPresent()) {
            roles.remove(rolExistente.get()); // Elimina el rol de la lista
            CsvReader.sobrescribirRoles(roles); // Sobrescribe el archivo CSV con la lista actualizada
            System.out.println("Rol con ID " + id + " eliminado.");
        } else {
            System.out.println("No se encontró un rol con el ID " + id);
        }
    }

}
