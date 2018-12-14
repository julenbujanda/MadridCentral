package ga.julen.madridcentral;

public class Criterio {

    private int id;
    private String nombre;
    private String detalle;

    public Criterio(int id, String nombre, String detalle) {
        this.id = id;
        this.nombre = nombre;
        this.detalle = detalle;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDetalle() {
        return detalle;
    }

}
