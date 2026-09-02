package cl.pucv.mascotas.model;

public class Veterinaria extends Servicio{


    
    public Veterinaria(String tipo){
        super(tipo);
    }
    
    @Override
    public float calcularPrecioServicio(){
        return 1.2f;
    }
}
