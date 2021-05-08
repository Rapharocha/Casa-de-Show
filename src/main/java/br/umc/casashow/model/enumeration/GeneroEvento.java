package br.umc.casashow.model.enumeration;

public enum GeneroEvento {

	AXE("Axé"),
    SERTANEJO("Sertanejo"),
    ROCK("Rock"),
    FUNK("Funk"),
    POP("Pop"),
    ELETRONICO("Eletrônico"),
    SAMBA("Samba"),
	PAGODE("Pagode");
	
	//atributo
	private String estilo;
	
	//construtor
    GeneroEvento(String estilo){
    	this.estilo = estilo;
    }

    //getter
	public String getEstilo() {
		return estilo;
	}
}
