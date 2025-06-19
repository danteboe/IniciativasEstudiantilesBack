package co.edu.uniandes.dse.iniciativasestudiantiles.exceptions;

public final class ErrorMessage {
	public static final String EVENTO_NOT_FOUND = "The event with the given id was not found";
    public static final String COMENTARIO_NOT_FOUND = "The comment with the given id was not found";
	public static final String MIEMBRO_NOT_FOUND = "The miembro with the given id was not found";
	public static final String PARTICIPANTE_NOT_FOUND = "The participante with the given id was not found";


    private ErrorMessage(){
		throw new IllegalStateException("Utility class");
	}
}