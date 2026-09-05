package cl.pucv.mascotas.model;

import java.time.LocalDate;

public class Peluqueria extends Servicio{

    private String tipoCorte;
    private int duracionCorte;
    
    public Peluqueria(
        int codigo, String tipo, String descripcion, 
        double costo, LocalDate fecha, String tipoCorte, 
        int duracionCorte){

        super(codigo, tipo, descripcion, costo, fecha);
        this.tipoCorte = tipoCorte;
        this.duracionCorte = duracionCorte;
    }

    @Override
    public float calcularPrecioServicio() {
        return 0;
    }

    public void realizarCorte(){}
    public void lavarMascota(){}
}
