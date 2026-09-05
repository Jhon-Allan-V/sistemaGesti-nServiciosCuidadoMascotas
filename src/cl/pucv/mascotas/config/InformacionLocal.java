package cl.pucv.mascotas.config;

public class InformacionLocal {
    
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;

    public InformacionLocal(String nombre, String direccion, String telefono, String correo){
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombre(){return nombre;}
    public String getDireccion(){return direccion;}
    public String getTelefono(){return telefono;}
    public String getCorreo(){return correo;}

    public void setNombre(String nombre){this.nombre = nombre;}
    public void setDireccion(String direccion){this.direccion = direccion;}
    public void setTelefono(String telefono){this.telefono = telefono;}
    public void setCorreo(String correo){this.correo = correo;}
}
