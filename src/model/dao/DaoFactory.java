package model.dao;

import db.DB;
import model.dao.impl.CategoriaDaoJDBC;
import model.dao.impl.MovimentacaoDaoJDBC;
import model.dao.impl.ProdutoDaoJDBC;
import model.dao.impl.SetorDaoJDBC;
import model.dao.impl.UsuarioDaoJDBC;

public class DaoFactory {

	public static UsuarioDao createUsuarioDao() {
		return new UsuarioDaoJDBC(DB.getConnection());
	}
	
	public static ProdutoDao createProdutoDao() {
		return new ProdutoDaoJDBC(DB.getConnection());
	}
	
	public static MovimentacaoDao createMovimentacaoDao() {
		return new MovimentacaoDaoJDBC(DB.getConnection());
	}
	
	public static SetorDao createSetorDao() {
		return new SetorDaoJDBC(DB.getConnection());
	}
	
	public static CategoriaDao createCategoriaDao() {
		return new CategoriaDaoJDBC(DB.getConnection());
	}
}
