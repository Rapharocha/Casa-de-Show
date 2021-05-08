package br.umc.casashow.error;

public class Erro {

	private static String message;
	
	private Erro() {}

	public static String getMessage() {
		return message;
	}

	public static void setMessage(String message) {
		Erro.message = message;
	}
}
