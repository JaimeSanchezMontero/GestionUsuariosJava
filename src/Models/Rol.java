package Models;

import java.util.ArrayList;
import java.util.List;

public class Rol {
    //PROPIEDADES
    private int id;
    private String nombre;
    private List <String> permisos; //Listado de permisos

    //CONSTRUCTOR
    public Rol(int id, String nombre, List<String> permisos) {
        this.id = id;
        this.nombre = nombre;
        this.permisos = permisos;
    }

    //GETTERS
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getPermisos() {
        return permisos;
    }
}
