//package Services;
//
//import Models.Departamento;
//import Repositorios.DepartamentoRepository;
//import java.util.List;
//
//public class DepartamentoService {
//    private DepartamentoRepository departamentoRepository = new DepartamentoRepository();
//
//    public List<Departamento> obtenerTodosLosDepartamentos() {
//        return departamentoRepository.findAll();
//    }
//
//    public Departamento buscarDepartamentoPorId(int id) {
//        return departamentoRepository.findById(id);
//    }
//
//    public void crearDepartamento(Departamento departamento) {
//        departamentoRepository.save(departamento);
//    }
//
//    public void actualizarDepartamento(Departamento departamentoActualizado) {
//        departamentoRepository.update(departamentoActualizado);
//    }
//
//    public void eliminarDepartamento(int id) {
//        departamentoRepository.delete(id);
//    }
//}
