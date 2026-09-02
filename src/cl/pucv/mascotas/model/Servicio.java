package cl.pucv.mascotas.model;

import java.time.LocalDate;

public abstract class  Servicio {
    
    private int codigo;
    private String tipo; //tipo de servicio (peluqueria, veterinaria, etc.)
    private String descripcion;
    private double costo; //costo base del servicio o precio.
    private LocalDate fecha;
    //private Date fecha;

    public Servicio(int codigo, String tipo, String descripcion, double precio, LocalDate fecha){
        this.codigo = codigo;
        this.tipo = tipo;
        this.descripcion = descripcion;
        costo = precio;
        this.fecha = fecha;
    }

    public abstract float calcularPrecioServicio(); //todas las clases que hereden (Servicio) deben de implementar este metodo

    public int getCodigo(){return codigo;}
    public String getTipo(){return tipo;}
    public String getDescripcion(){return descripcion;}
    public double getCosto(){return costo;}
    public LocalDate fecha(){return fecha;}

    public void setCodigo(int codigo){this.codigo = codigo;}
    public void setTipo(String tipo){this.tipo = tipo;}
    public void setDescripcion(String descripcion){this.descripcion = descripcion;}
    public void setCosto(double costo){this.costo = costo;}
    public void setFecha(LocalDate fecha){this.fecha = fecha;}

}
