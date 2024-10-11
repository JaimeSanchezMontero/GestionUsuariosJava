//package Services;
//
//import Models.Grupo;
//import Repositorios.GrupoRepository;
//
//import java.util.List;
//public class GrupoService {
//    private GrupoRepository grupoRepository = new GrupoRepository();
//
//    public List<Grupo> obtenerTodosLosGrupos() {
//        return grupoRepository.findAll();
//    }
//
//    public Grupo buscarGrupoPorId(int id) {
//        return grupoRepository.findById(id);
//    }
//
//    public void crearGrupo(Grupo grupo) {
//        grupoRepository.save(grupo);
//    }
//
//    public void actualizarGrupo(Grupo grupoActualizado) {
//        grupoRepository.update(grupoActualizado);
//    }
//
//    public void eliminarGrupo(int id) {
//        grupoRepository.delete(id);
//    }
//}
