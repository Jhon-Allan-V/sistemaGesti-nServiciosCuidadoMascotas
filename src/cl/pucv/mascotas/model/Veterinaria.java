package cl.pucv.mascotas.model;

import java.time.LocalDate;

public class Veterinaria extends Servicio {

    private String nombreVeterinario;
    private String especialidad;
    private String licencia;

    public Veterinaria(int codigo, String tipo, String descripcion,
            double costo, LocalDate fecha, String nombreVeterinario,
            String especialidad, String licencia) {

        super(codigo, tipo, descripcion, costo, fecha);
        this.nombreVeterinario = nombreVeterinario;
        this.especialidad = especialidad;
        this.licencia = licencia;
    }

    @Override
    public float calcularPrecioServicio() {
        return 1.2f;
    }

    public void atenderMascota() {
    }

    public void emitirDiagnostico() {
    }

    public String getNombreVeterinario() {
        return nombreVeterinario;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }
}