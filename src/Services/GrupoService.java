package Services;

import Models.Grupo;
import Models.Usuario;
import Repositorios.GrupoRepository;
import Repositorios.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
public class GrupoService {
    private GrupoRepository grupoRepository = new GrupoRepository();
    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    public List<Grupo> obtenerTodosLosGrupos() {
        return grupoRepository.findAll();
    }

    public Grupo buscarGrupoPorId(int id) {
        return grupoRepository.findById(id);
    }

    // Método para obtener grupos por usuario
    public List<Grupo> obtenerGruposPorUsuario(int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId); // Obtener el usuario por ID
        List<Grupo> gruposDeUsuario = new ArrayList<>();

        if (usuario != null) {
            // Obtener los IDs de los grupos a los que pertenece
            List<String> gruposIds = usuario.getGrupos(); // Usar getGrupos() directamente
            for (String grupoId : gruposIds) {
                Grupo grupo = grupoRepository.findById(Integer.parseInt(grupoId)); // Convertir a int si es necesario
                if (grupo != null) {
                    gruposDeUsuario.add(grupo);
                }
            }
        }

        return gruposDeUsuario;
    }

    public void crearGrupo(Grupo grupo) {
        grupoRepository.save(grupo);
    }

    public boolean actualizarGrupo(Grupo grupoActualizado) {
        return grupoRepository.update(grupoActualizado); // Devuelve el resultado de la actualización
    }

    public boolean eliminarGrupo(int id) {  // Cambia a boolean
        return grupoRepository.delete(id);  // Devuelve el resultado de la eliminación
}
}
