package br.umc.casashow.pageable;

public class Page {

	private int offsetProximo = 0;
	private int offsetAnterior = 0;
	
	public Page(int offsetProximo, int offsetAnterior) {
		this.offsetProximo = offsetProximo;
		this.offsetAnterior = offsetAnterior;
	}

	public int getOffsetProximo() {
		return offsetProximo;
	}

	public void setOffsetProximo(int offsetProximo) {
		this.offsetProximo = offsetProximo;
	}

	public int getOffsetAnterior() {
		return offsetAnterior;
	}

	public void setOffsetAnterior(int offsetAnterior) {
		this.offsetAnterior = offsetAnterior;
	}
	
	public void paginacaoPesquisa(int offset) {
		offsetProximo = offset + 10;
		if(offset > 0) {
		offsetAnterior = offset - 10;
		} else {
			offsetAnterior = 0;
		}
	}
	
	public void paginacaoIngresso(int offset) {
		offsetProximo = offset + 1;
		if(offset > 0) {
		offsetAnterior = offset - 1;
		} else {
			offsetAnterior = 0;
		}
	}
}
