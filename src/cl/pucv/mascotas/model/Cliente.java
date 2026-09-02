package cl.pucv.mascotas.model;

public class Cliente {
    
    private String rut; //atributo obligatorio
    private String nombre; //atributo obligatorio
    private String correo; //opcional
    private String telefono; //opcional 
    private String direccion; //opcional

    //constructores + sobrecarga
    public Cliente(String rut, String nombre){
        this.rut = rut;
        this.nombre = nombre;
        this.correo = null;
        this.telefono = null;
        this.direccion = null;    
    }

    public Cliente(String rut, String nombre, String correo){
        this.rut = rut;
        this.nombre = nombre;
        this.correo = correo; 
        this.telefono = null;
        this.direccion = null;         
    }

    public Cliente(String rut, String nombre, String correo, String telefono){
        this.rut = rut;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = null;
    }

    public Cliente(String rut, String nombre, String correo, String telefono, String direccion){
        this.rut = rut;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    //getters
    public String getRutCliente(){return rut;}
    public String getNombreCliente(){return nombre;}
    public String getCorreoCliente(){return correo;}
    public String getTelefono(){return telefono;}
    public String getDireccion(){return direccion;}

    //setters
    public void setRutCliente(String rut){this.rut = rut;}
    public void setNombreCliente(String nombre){this.nombre = nombre;}
    public void setCorreoCliente(String correo){this.correo = correo;}
    public void setTelefono(String telefono){this.telefono = telefono;}
    public void setDireccion(String direccion){this.direccion = direccion;}

}
