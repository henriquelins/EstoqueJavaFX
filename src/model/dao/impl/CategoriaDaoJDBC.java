package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.CategoriaDao;
import model.entities.Categoria;

public class CategoriaDaoJDBC implements CategoriaDao {

	private Connection conn;

	public CategoriaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Categoria categoria) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO categoria " + "(nome) " + "VALUES " + "(?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			st.setString(1, categoria.getNome());
			
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					categoria.setIdCategoria(id);
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
	public void update(Categoria categoria) {
		PreparedStatement st = null;
		try {
			st = conn
					.prepareStatement("UPDATE categoria " + "SET nome = ?" + "WHERE id_categoria = ?");

			st.setString(1, categoria.getNome());
			st.setInt(2, categoria.getIdCategoria());

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
			st = conn.prepareStatement("DELETE FROM categoria WHERE id_categoria = ?");

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	
	@Override
	public List<Categoria> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM categoria ORDER BY nome");

			rs = st.executeQuery();

			List<Categoria> list = new ArrayList<>();

			while (rs.next()) {
				Categoria categoria = instantiateCategoria(rs);
				list.add(categoria);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}



	private Categoria instantiateCategoria(ResultSet rs) throws SQLException {
		Categoria categoria = new Categoria();
		categoria.setIdCategoria(rs.getInt("id_categoria"));
		categoria.setNome(rs.getString("nome"));
		return categoria;
	}
	
	

}
