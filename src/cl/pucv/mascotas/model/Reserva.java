package cl.pucv.mascotas.model;

import java.time.LocalDate;

public class Reserva {

    private int idReserva;
    private String rutCliente;
    private String idMascota;
    private int codigoServicio;
    private LocalDate fechaReserva;
    private String estado;

    public Reserva(){}

    public Reserva(
        int idReserva, String rutCliente, String idMascota, 
        int codigoServicio, LocalDate fechaReserva, String estado){

        this.idReserva = idReserva;
        this.rutCliente = rutCliente;
        this.idMascota = idMascota;
        this.codigoServicio = codigoServicio;
        this.fechaReserva = fechaReserva;
        this.estado = estado;   
    }

    public int getIdReserva(){return idReserva;}
    public String getRutCliente(){return rutCliente;}
    public String getIdMascota(){return idMascota;}
    public int getCodigoServicio(){return codigoServicio;}
    public LocalDate getFechaReserva(){return fechaReserva;}
    public String getEstado(){return estado;}

    public void setIdReserva(int idReserva){this.idReserva = idReserva;}
    public void setRutCliente(String rutCliente){this.rutCliente = rutCliente;}
    public void setIdMascota(String idMascota){this.idMascota = idMascota;}
    public void setCodigoServicio(int codigoServicio){this.codigoServicio = codigoServicio;}
    public void setFechaReserva(LocalDate fechaReserva){this.fechaReserva = fechaReserva;}
    public void setEstado(String estado){this.estado = estado;}
    
}
