package Repositorios;
import Models.Departamento;
import Models.Usuario;
import Utils.CsvReader;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoRepository {
    private List<Departamento> departamentos;

    // Constructor que carga los departamentos desde el archivo CSV
    public DepartamentoRepository() {
        this.departamentos = CsvReader.leerDepartamentos("Data/departamentos.csv");
    }

    public List<Departamento> findAll() {
        return departamentos;
    }

    public Departamento findById(int id) {
        for (Departamento departamento : departamentos) {
            if (departamento.getId() == id) {
                return departamento;
            }
        }
        return null;
    }

    public List<Departamento> buscarPorNombre(String nombre) {
        List<Departamento> resultados = new ArrayList<>();

        for (Departamento departamento : departamentos) {
            // Verifica si el nombre del departamento contiene la cadena proporcionada
            if (departamento.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(departamento);
            }
        }
        return resultados;
    }

    public List<Departamento> findByUsuario(int usuarioId) {
        List<Departamento> resultados = new ArrayList<>();

        for (Departamento departamento : departamentos) {
            // Comprobamos si el ID del usuario está en la lista de usuarios del departamento
            if (departamento.getUsuarios().contains(String.valueOf(usuarioId))) { // Convertimos usuarioId a String
                resultados.add(departamento);
            }
        }

        return resultados;
    }

    public void crearDepartamento(Departamento departamento) {
        // Añadir el departamento a la lista
        departamentos.add(departamento);
        // Escribir el nuevo departamento en el CSV
        CsvReader.escribirDepartamento(departamento);
    }

    public void update(Departamento departamentoActualizado) {
        for (int i = 0; i < departamentos.size(); i++) {
            if (departamentos.get(i).getId() == departamentoActualizado.getId()) {
                departamentos.set(i, departamentoActualizado);
                return;
            }
        }
    }

    public void delete(int id) {
        departamentos.removeIf(departamento -> departamento.getId() == id);
        // Sobrescribir el archivo CSV con la lista actualizada
        CsvReader.sobrescribirDepartamentos(departamentos);
    }
}
