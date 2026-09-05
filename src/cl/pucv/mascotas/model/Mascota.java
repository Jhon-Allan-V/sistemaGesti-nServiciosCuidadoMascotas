package cl.pucv.mascotas.model;

import cl.pucv.mascotas.model.Mascota;

public class Mascota {
    
    private String id;
    private String rutDueno;
    private String nombre;
    private String raza;
    private int edad;
    private float peso;
    private float altura;
    private String tratoEspecial; //idea: aqui se almacenara alguna indicacion en especifico del cliente con respecto al trato que se le debe de dar a la mascota.

    public Mascota(String id, String rutDueno, String nombre, String raza, int edad, float peso, float altura){
        this.id = id;
        this.rutDueno = rutDueno;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
        this.tratoEspecial = null;
    }

    public Mascota(String id, String rutDueno, String nombre, String raza, int edad, float peso, float altura, String tratoEspecial){
        this.id = id;
        this.rutDueno = rutDueno;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
        this.tratoEspecial = tratoEspecial;
    }

    public String getId(){return id;}
    public String getRutDueno(){return rutDueno;}
    public String getNombre(){return nombre;}
    public String getRaza(){return raza;}
    public int getEdad(){return edad;}
    public float getPeso(){return peso;}
    public float getAltura(){return altura;}
    public String getTratoEspecial(){return tratoEspecial;}

    public void setId(String id){this.id = id;}
    public void setRutDueno(String rutDueno){this.rutDueno = rutDueno;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setRaza(String raza){this.raza = raza;}
    public void setEdad(int edad){this.edad = edad;}
    public void setPeso(float peso){this.peso = peso;}
    public void setAltura(float altura){this.altura = altura;}
    public void setTratoEspecial(String tratoEspecial){this.tratoEspecial = tratoEspecial;}
}
