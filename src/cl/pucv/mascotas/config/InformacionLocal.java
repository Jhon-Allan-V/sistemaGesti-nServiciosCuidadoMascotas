package cl.pucv.mascotas.config;

public class InformacionLocal {
    
    private String nombre;
    private String direccion;
    private String telefono;
    private boolean abierto;

    public InformacionLocal(String nombre, String direccion, String telefono, boolean abierto){
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.abierto = abierto;
    }

    public String getNombre(){return nombre;}
    public String getDireccion(){return direccion;}
    public String getTelefono(){return telefono;}
    public boolean getAbierto(){return abierto;}

    public void setNombre(String nombre){this.nombre = nombre;}
    public void setDireccion(String direccion){this.direccion = direccion;}
    public void setTelefono(String telefono){this.telefono = telefono;}
    public void setAbierto(boolean abierto){this.abierto = abierto;}
}
