package Repositorios;
import Models.Grupo;
import Utils.CsvReader;
import java.util.ArrayList;
import java.util.List;


public class GrupoRepository {
    private List<Grupo> grupos;

    // Constructor que carga los grupos desde el archivo CSV
    public GrupoRepository() {
        // Inicializar la lista con los grupos leídos del archivo CSV
        this.grupos = CsvReader.leerGrupos("Data/grupos.csv");
    }

    // Metodo para obtener todos los grupos
    public List<Grupo> findAll() {
        return grupos;
    }

    //Metodo para filtrar grupos por id
    public Grupo findById(int id) {
        for (Grupo grupo : grupos) {
            if (grupo.getId() == id) {
                return grupo;
            }
        }
        return null;
    }

    //Metodo para obtener lista de usuarios que pertenecen a un grupo
    public List<Grupo> findByIds(List<Integer> ids) {
        List<Grupo> gruposFiltrados = new ArrayList<>();
        for (int id : ids) {
            Grupo grupo = findById(id);
            if (grupo != null) {
                gruposFiltrados.add(grupo);
            }
        }
        return gruposFiltrados;
    }

    public void save(Grupo grupo) {
        grupos.add(grupo); // Agregar a la lista en memoria
        CsvReader.escribirGrupo(grupo); // Llamar al metodo para escribir en el CSV
    }

    public boolean update(Grupo grupoActualizado) {
        for (int i = 0; i < grupos.size(); i++) {
            if (grupos.get(i).getId() == grupoActualizado.getId()) {
                grupos.set(i, grupoActualizado);
                CsvReader.sobreEscribirGrupos(grupos); // Actualiza el archivo CSV
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) { // Cambia a boolean
        Grupo grupoAEliminar = findById(id);
        if (grupoAEliminar != null) {
            grupos.remove(grupoAEliminar); // Eliminar de la lista
            CsvReader.sobreEscribirGrupos(grupos); // Actualizar el archivo CSV
            return true;  // Grupo eliminado
        }
        return false; // Grupo no encontrado
    }

}
