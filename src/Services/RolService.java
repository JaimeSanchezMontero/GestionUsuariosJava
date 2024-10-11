//package Services;
//
//import Models.Rol;
//import Repositorios.RolRepository;
//import java.util.List;
//
//public class RolService {
//    private RolRepository rolRepository = new RolRepository();
//
//    public List<Rol> obtenerTodosLosRoles() {
//        return rolRepository.findAll();
//    }
//
//    public Rol buscarRolPorId(int id) {
//        return rolRepository.findById(id);
//    }
//
//    public void crearRol(Rol rol) {
//        rolRepository.save(rol);
//    }
//
//    public void actualizarRol(Rol rolActualizado) {
//        rolRepository.update(rolActualizado);
//    }
//
//    public void eliminarRol(int id) {
//        rolRepository.delete(id);
//    }
//}
