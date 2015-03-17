package controllers;

import java.util.LinkedList;
import java.util.List;

import models.Episodio;
import models.Seriado;
import dao.GenericDAO;
import dao.GenericDAOImpl;
import play.*;
import play.db.jpa.Transactional;
import play.mvc.*;

public class Application extends Controller {
	
	private static GenericDAO dao = new GenericDAOImpl();

	@Transactional
    public static Result index() {
		List<Seriado> seriados = dao.findAllByClassName("Seriado");
/*		List<Seriado> assistindo = new LinkedList<Seriado>();
        List<Seriado> naoAssistidos = new LinkedList<Seriado>();

        for (Seriado seriado: seriados) {
            if(!seriado.Assistindo()) {
                naoAssistidos.add(seriado);
            } else {
                assistindo.add(seriado);
            }
        }
        */
        return ok(views.html.index.render(seriados));
        
    }
    
    @Transactional
    public static Result listarEpisodios(long id) {
    	Episodio ep = dao.findByEntityId(Episodio.class, id);
        dao.merge(ep);
        dao.flush();
        return redirect(routes.Application.index());

    }
    
    @Transactional
    public static Result assistir(long id) {
    	Seriado seriado = dao.findByEntityId(Seriado.class, id);
    	seriado.setAssistindo(true);
        dao.merge(seriado);
        dao.flush();
        return redirect(routes.Application.index());

    }
    
    @Transactional
	protected static void salvaObjeto(Object obj) {
		dao.persist(obj);
		dao.merge(obj);
		dao.flush();
	}
}
