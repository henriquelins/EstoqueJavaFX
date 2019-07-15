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

public class SetorDaoJDBC implements SetorDao  {
	
	private Connection conn;

	public SetorDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	

	@Override
	public void insert(Setor setor) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO setor " + "(Nome) " + "VALUES " + "(?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			st.setString(1, setor.getNome());
			
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					setor.setIdSetor(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Setor setor) {
		PreparedStatement st = null;
		try {
			st = conn
					.prepareStatement("UPDATE setor " + "SET nome = ?" + "WHERE id_setor = ?");

			st.setString(1, setor.getNome());
			st.setInt(2, setor.getIdSetor());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM setor WHERE id_setor = ?");

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	
	@Override
	public List<Setor> findAll() {
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



	private Setor instantiateSetor(ResultSet rs) throws SQLException {
		Setor setor = new Setor();
		setor.setIdSetor(rs.getInt("id_setor"));
		setor.setNome(rs.getString("nome"));
		return setor;
	}

	
}