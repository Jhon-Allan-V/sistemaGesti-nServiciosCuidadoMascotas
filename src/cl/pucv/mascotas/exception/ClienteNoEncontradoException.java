package cl.pucv.mascotas.exception;

//Excepcion personalizada para alertar cuando un usuario no existe.

public class ClienteNoEncontradoException extends Exception{
    
    public ClienteNoEncontradoException(String mensaje){
        super(mensaje);
    }
}
