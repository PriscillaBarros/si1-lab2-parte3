package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Temporada {

	@Id
	@GeneratedValue
	private long id;

	private int numero;

	@OneToMany
	private List<Episodio> episodios;

	public Temporada() {
		episodios = new ArrayList<Episodio>();
	}

	public Temporada(int valor) throws Exception {
		this();
		setNumero(valor);
	}
	
	public void adicionaEpisodio(Episodio episodio) {
		episodios.add(episodio);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Episodio> getEpisodios() {
		return episodios;
	}

	public void setEpisodios(List<Episodio> episodios) {
		this.episodios = episodios;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) throws Exception {
		if (numero < 1) {
			throw new Exception("Numero de temporada incorreto");
		}
		this.numero = numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((episodios == null) ? 0 : episodios.hashCode());
		result = prime * result + numero;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Temporada) {
			Temporada t = (Temporada) obj;
			return getNumero() == t.getNumero()
					&& getEpisodios().equals(t.getEpisodios());
		}
		return false;
	}

}
