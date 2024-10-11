package Models;

public class Permiso {
    //PROPIEDADES
    private String nombre;

    //CONSTRUCTOR
    public Permiso(String nombre) {
        this.nombre = nombre;
    }

    //GETTER
    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Permiso{nombre='" + nombre + "'}";
    }
}
