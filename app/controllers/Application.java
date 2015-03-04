package controllers;

import java.util.List;

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
        return ok(views.html.index.render(getSeriados()));
    }
    
    @Transactional
    public static Result listarEpisodios(String id) {
		return TODO;
    }
    
    private static Seriado getSeriado(Long id) {
		return getDAO().findByEntityId(Seriado.class, id);
	}
    
    private static List<Seriado> getSeriados() {
		return getDAO().findAllByClassName("Seriado");
	}
    
    @Transactional
	protected static void salvaObjeto(Object obj) {
		dao.persist(obj);
		dao.merge(obj);
		dao.flush();
	}

	protected static GenericDAO getDAO() {
		return dao;
	}

}
