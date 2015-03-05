package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Seriado")
public class Seriado {

	@Id
	@GeneratedValue
	private long id;

	private String nome;
	@OneToMany
	private List<Temporada> temporadas;

	public Seriado() {
		temporadas = new ArrayList<Temporada>();
	}

	public Seriado(String nome) throws Exception {
		this();
		setNome(nome);
	}

	public List<Temporada> getTemporadas() {
		return temporadas;
	}
	
	public void adicionaTemporada(Temporada temporada) {
		temporadas.add(temporada);
	}

	public void adicionaEpisodio(int temporada, Episodio episodio) throws Exception {
		getTemporadaPorNumero(temporada).adicionaEpisodio(episodio);	
	}
	
	public List<Episodio> getEpisodios() {
		List<Episodio> episodios = new ArrayList<Episodio>();
		for (int i = 0; i < temporadas.size(); i++) {
			List<Episodio> e = temporadas.get(i).getEpisodios();
			for (int j = 0; j < e.size(); j++) {
				episodios.add(e.get(j));
			}
			
		}
		return episodios;
	}

	private Temporada getTemporadaPorNumero(int temporada) {
		for (Temporada t : temporadas) {
			if (t.getNumero() == temporada) {
				return t;
			}
		}
		return null;
	}

	public void setTemporadas(List<Temporada> temporadas) {
		this.temporadas = temporadas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws Exception {
		if (nome == null) {
			throw new Exception("Nome do seriado nao pode ser nulo");
		}
		if (nome.trim().equals("")) {
			throw new Exception("Nome do seriado nao pode ser vazio");
		}
		this.nome = nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Seriado) {
			Seriado s = (Seriado) obj;
			return getNome().equals(s.getNome());
		}
		return false;
	}

	@Override
	public String toString() {
		return "Seriado [nome=" + nome + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


}
