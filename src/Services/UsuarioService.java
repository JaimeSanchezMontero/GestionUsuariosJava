package Services;

import Models.Usuario;
import Repositorios.UsuarioRepository;
import java.util.List;
import Utils.CsvReader;

public class UsuarioService {
    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(int id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> obtenerUsuariosPorDepartamento(int departamentoId) {
        return usuarioRepository.findByDepartamento(departamentoId);
    }

    public void crearUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
        CsvReader.escribirUsuario(usuario);  // Agregar el nuevo usuario al archivo CSV
    }

    public void actualizarUsuario(Usuario usuarioActualizado) {
        usuarioRepository.update(usuarioActualizado);
        // Escribir todos los usuarios de nuevo al archivo CSV
        CsvReader.sobreEscribirUsuarios(usuarioRepository.getUsuarios());
    }

    public void eliminarUsuario(int id) {
        usuarioRepository.delete(id);
    }

    public List<Usuario> buscarUsuariosPorNombre(String nombre) {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
    }
}
