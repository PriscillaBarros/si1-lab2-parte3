import play.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import models.Episodio;
import models.Seriado;
import models.Temporada;
import dao.GenericDAO;
import dao.GenericDAOImpl;
import play.db.jpa.JPA;

public class Global extends GlobalSettings {

	private static GenericDAO dao = new GenericDAOImpl();
	private final static String FINAL_LINHA = ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
	private final static String SEPARADOR_PAREMETRO = ",";
	private final static String ARQUIVO = "./public/file/seriesFinalFile.csv";

	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if (!(dao.findAllByClassName("Seriado").size() > 0)) {
					leArquivoCSV();
				}
			}

			@SuppressWarnings("resource")
			private void leArquivoCSV() {

				Scanner scanner = null;
				Scanner linha = null;
				FileReader csv = null;

				try {
					csv = new FileReader(ARQUIVO);

					scanner = new Scanner(csv).useDelimiter(FINAL_LINHA);

					String nomeSeriado = "";
					String numeroTemporada = "";
					String numeroEpisodio = "";
					String nomeEpisodio = "";

					Seriado tmpSeriado = null;
					Temporada tmpTemporada = null;

					while (scanner.hasNext()) {

						linha = scanner.useDelimiter(SEPARADOR_PAREMETRO);
						nomeSeriado = linha.next();
						numeroTemporada = linha.next();
						numeroEpisodio = linha.next();
						nomeEpisodio = linha.next();
						
						tmpSeriado = adicionaSeriado(nomeSeriado, tmpSeriado);


						tmpTemporada = adicionaTemporada(Integer.parseInt(numeroTemporada), tmpSeriado, tmpTemporada);
						
						
						adicionaEpisodio(Integer.parseInt(numeroEpisodio), nomeEpisodio, tmpSeriado, tmpTemporada);
						scanner.nextLine();


					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					scanner.close();
					linha.close();

					try {
						csv.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			}

			private void adicionaEpisodio(int numeroEpisodio,
					String nomeEpisodio, Seriado tmpSeriado,
					Temporada tmpTemporada) throws Exception {
				Episodio e = new Episodio(numeroEpisodio, nomeEpisodio, tmpTemporada.getNumero());
				salvaObjeto(e);

				tmpSeriado.adicionaEpisodio(tmpTemporada.getNumero(), e);

				salvaObjeto(tmpTemporada);
				salvaObjeto(tmpSeriado);
			}

			private Temporada adicionaTemporada(int numeroTemporada, Seriado tmpSeriado, Temporada tmpTemporada)
				throws Exception {
				if (tmpTemporada == null || tmpTemporada.getNumero() != numeroTemporada) {
					
					tmpTemporada = new Temporada(numeroTemporada);
					salvaObjeto(tmpTemporada);

					tmpSeriado.adicionaTemporada(tmpTemporada);
					salvaObjeto(tmpSeriado);
				}
				return tmpTemporada;
			}

			private Seriado adicionaSeriado(String nomeSeriado, Seriado tmpSeriado) throws Exception {
				
				if (tmpSeriado == null || !nomeSeriado.equals(tmpSeriado.getNome())) {
					tmpSeriado = new Seriado(nomeSeriado);
					salvaObjeto(tmpSeriado);
				}
				return tmpSeriado;
			}

			private void salvaObjeto(Object obj) {
				dao.persist(obj);
				dao.merge(obj);
				dao.flush();
			}
		});
	}
}