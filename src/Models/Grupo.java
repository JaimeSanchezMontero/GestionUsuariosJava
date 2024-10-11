package Models;

import java.util.ArrayList;
import java.util.List;

public class Grupo {
    //PROPIEDADES
    private int id;
    private String nombre;
    private String descripcion;
    private List<String> usuarios; //Listado de id's de los usuarios

    //CONSTRUCTOR
    public Grupo(int id, String nombre, String descripcion, List<String> usuarios) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.usuarios = usuarios;
    }

    //GETTERS
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<String> getUsuarios() {
        return usuarios;
    }

    @Override
    public String toString() {
        return "Grupo{id='" + id + "', nombre='" + nombre + "', descripcion='" + descripcion + "}";
    }
}
