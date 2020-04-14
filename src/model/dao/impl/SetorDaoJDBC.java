package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SetorDao;
import model.entities.Setor;

public class SetorDaoJDBC implements SetorDao {

	private Connection conn;

	public SetorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Setor setor) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("INSERT INTO setor (nome) VALUES (?)");

			st.setString(1, setor.getNome().toUpperCase());
			
			int linhas = st.executeUpdate(); 
			
			if (linhas == 0) {
				
				throw new DbException("Erro ao inserir o setor!");
			}

			conn.commit();

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transaction rolled back. Cause by: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Error trying to rollback. Cause by: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);

		}

	}

	@Override
	public void update(Setor setor) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("UPDATE setor SET nome = ? WHERE id_setor = ?");

			st.setString(1, setor.getNome().toUpperCase());
			st.setInt(2, setor.getIdSetor());

			st.executeUpdate();

			conn.commit();

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transaction rolled back. Cause by: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Error trying to rollback. Cause by: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);

		}

	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		
		try {
			
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement("DELETE FROM setor WHERE id_setor = ?");

			st.setInt(1, id);

			st.executeUpdate();
			
			conn.commit();
		
		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transaction rolled back. Cause by: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Error trying to rollback. Cause by: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);

		}


	}

	@Override
	public List<Setor> findAllNome() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("SELECT * FROM setor ORDER BY nome");

			rs = st.executeQuery();

			List<Setor> list = new ArrayList<>();

			while (rs.next()) {
				
				Setor setor = instantiateSetor(rs);
				list.add(setor);
				
			}
			
			return list;
			
		} catch (SQLException e) {
			
			throw new DbException(e.getMessage());
			
		} finally {
			
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
	}

	@Override
	public List<Setor> findAllId() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("SELECT * FROM setor ORDER BY id_setor");

			rs = st.executeQuery();

			List<Setor> list = new ArrayList<>();

			while (rs.next()) {
				
				Setor setor = instantiateSetor(rs);
				list.add(setor);
				
			}
			
			return list;
			
		} catch (SQLException e) {
			
			throw new DbException(e.getMessage());
			
		} finally {
			
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
	}

	private Setor instantiateSetor(ResultSet rs) throws SQLException {
		
		Setor setor = new Setor();
		setor.setIdSetor(rs.getInt("id_setor"));
		setor.setNome(rs.getString("nome").toUpperCase());
		return setor;
		
	}

	@Override
	public Integer findIdSetor(String nomeSetor) {

		PreparedStatement st = null;
		ResultSet rs = null;
		int id_setor = 0;

		try {

			st = conn.prepareStatement("SELECT id_setor FROM setor where nome = ?");
			st.setString(1, nomeSetor.toUpperCase());

			rs = st.executeQuery();

			while (rs.next()) {

				id_setor = rs.getInt("id_setor");

			}

			return id_setor;

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {

			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}

	}

}