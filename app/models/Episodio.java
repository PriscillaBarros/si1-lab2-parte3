package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Episodio {

	@Id
	@GeneratedValue
	private long id;

	private int numero;
	private String titulo;
	private boolean isAssistido;
	private int numTemporada;
	private String descricao;

	public Episodio() {
		isAssistido = false;
		descricao = "";
	}

	public Episodio(int numero, String titulo, int numTemporada) throws Exception {
		this();
		setNumero(numero);
		setTitulo(titulo);
	}

	public Episodio(int numero, String titulo, int numTemporada, String descricao)
			throws Exception {
		this(numero, titulo, numTemporada);
		setDescricao(descricao);
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) throws Exception {
		if (numero < 0) {
			throw new Exception("Numero do episodio incorreto");
		}
		this.numero = numero;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) throws Exception {
		if (titulo == null) {
			throw new Exception("Titulo do episodio nao pode ser nulo");
		}
		this.titulo = titulo;
	}

	public boolean isAssistido() {
		return isAssistido;
	}

	public void setAssistido(boolean isAssistido) {
		this.isAssistido = isAssistido;
	}
	
	public int getNumTemporada() {
		return numTemporada;
	}
	
	public void setNumTemporada(int numTemporada) {
		this.numTemporada = numTemporada;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) throws Exception {
		if (descricao == null) {
			throw new Exception("Descricao do epsodio nao pode ser nula");
		}
		this.descricao = descricao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numero;
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Episodio) {
			Episodio e = (Episodio) obj;
			return getNumero() == e.getNumero()
					&& getTitulo().equals(e.getTitulo());
		}
		return false;
	}

}
