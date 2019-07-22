package model.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import model.dao.TesteDao;

public class TesteDaoJDBC implements TesteDao {

	private Connection conn;

	public TesteDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean teste() {
		
		boolean sucesso = false;
		
		try {
			
			sucesso = conn.isReadOnly();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return sucesso;
	}

}
