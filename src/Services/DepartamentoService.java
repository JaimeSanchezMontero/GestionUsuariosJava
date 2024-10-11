package Services;

import Models.Departamento;
import Repositorios.DepartamentoRepository;
import Utils.CsvReader;
import java.util.List;
import java.util.Optional;


public class DepartamentoService {
    private DepartamentoRepository departamentoRepository = new DepartamentoRepository();

    public List<Departamento> obtenerTodosLosDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Departamento buscarDepartamentoPorId(int id) {
        // Llama al metodo del repositorio para buscar el departamento por ID
        return departamentoRepository.findById(id);
    }

    public List<Departamento> buscarDepartamentosPorNombre(String nombre) {
        return departamentoRepository.buscarPorNombre(nombre);
    }

    public List<Departamento> obtenerDepartamentosPorUsuario(int usuarioId) {
        // Cambiar el metodo para llamar a findByUsuario en lugar de obtenerPorUsuario
        return departamentoRepository.findByUsuario(usuarioId);
    }

    public void crearDepartamento(Departamento departamento) {
        departamentoRepository.crearDepartamento(departamento);
    }

    public void actualizarDepartamento(Departamento departamentoActualizado) {
        // Lógica para actualizar el departamento en la lista en memoria
        List<Departamento> departamentos = departamentoRepository.findAll();
        for (int i = 0; i < departamentos.size(); i++) {
            if (departamentos.get(i).getId() == departamentoActualizado.getId()) {
                departamentos.set(i, departamentoActualizado);
                break;
            }
        }
        // Sobrescribir el archivo CSV con la lista actualizada
        CsvReader.sobrescribirDepartamentos(departamentos);
    }

    public void eliminarDepartamento(int id) {
        departamentoRepository.delete(id);
    }

}
