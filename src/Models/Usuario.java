package Models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    //PROPIEDADES
    private int id;
    private String nombre;
    private String email;
    private int edad;

    private List<Integer> departamentos; //Listado id's de departamentos
    private List<String> roles; //Listado id's de roles

    //CONSTRUCTOR
    public Usuario(int id, String nombre, String email, int edad, List<Integer> departamentos, List<String> roles) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.departamentos = departamentos;
        this.roles = roles;
    }

    //GETTERS

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getEdad() {
        return edad;
    }

    public List<Integer> getDepartamentos() {
        return departamentos;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "Usuario{id='" + id + "', nombre='" + nombre + "', email='" + email + "', edad=" + edad + "}";
    }
}
