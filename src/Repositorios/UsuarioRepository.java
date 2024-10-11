package Repositorios;

import Models.Usuario;
import Utils.CsvReader;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private List<Usuario> usuarios = new ArrayList<>();

    // Constructor que carga los usuarios desde el archivo CSV
    public UsuarioRepository() {
        // Inicializar la lista con los usuarios leídos del archivo CSV
        this.usuarios = CsvReader.leerUsuarios("Data/usuarios.csv");
    }

    public List<Usuario> findAll() {
        return usuarios;
    }

    public Usuario findById(int id) {  // Cambiado de String a int
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) { // Comparar directamente como int
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> findByDepartamento(int departamentoId) {
        return usuarios.stream()
                .filter(usuario -> usuario.getDepartamentos().contains(departamentoId))
                .toList();
    }

    public void save(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> getUsuarios() {
        return usuarios; //
    }

    public void update(Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuarioActualizado.getId()) {
                usuarios.set(i, usuarioActualizado);
                return;
            }
        }
    }

    public void delete(int id) {
        usuarios.removeIf(usuario -> usuario.getId() == id); // Elimina el usuario de la lista
        CsvReader.sobreEscribirUsuarios(usuarios);  // Actualiza el archivo CSV
    }
}
