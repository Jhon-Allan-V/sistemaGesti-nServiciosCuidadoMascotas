package cl.pucv.mascotas.model;

import java.time.LocalDate;

public class Veterinaria extends Servicio{

    private String nombreVeterinario;
    private String especialidad;
    private String licencia;

    public Veterinaria(int codigo, String tipo, String descripcion, 
        double costo, LocalDate fecha, String nombreVeterinario, 
        String especialidad, String licencia){

        super(codigo, tipo, descripcion, costo, fecha);
        this.nombreVeterinario = nombreVeterinario;
        this.especialidad = especialidad;
        this.licencia = licencia;
    }
    
    @Override
    public float calcularPrecioServicio(){
        return 1.2f;
    }

    public void atenderMascota(){}
    public void emitirDiagnostico(){}
}
