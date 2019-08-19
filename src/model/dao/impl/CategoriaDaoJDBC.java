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

			conn.setAutoCommit(false);

			st = conn.prepareStatement("INSERT INTO categoria (nome, id_setor) VALUES (?,?)");

			st.setString(1, categoria.getNome());
			st.setInt(2, categoria.getIdSetor());

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
	public void update(Categoria categoria) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("UPDATE categoria SET nome = ?, id_setor = ? WHERE id_categoria = ?");

			st.setString(1, categoria.getNome());
			st.setInt(2, categoria.getIdSetor());
			st.setInt(3, categoria.getIdCategoria());

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

			st = conn.prepareStatement("DELETE FROM categoria WHERE id_categoria = ?");

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
	public List<Categoria> findAllNome() {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM categoria ORDER BY nome");

			rs = st.executeQuery();

			List<Categoria> list = new ArrayList<>();

			while (rs.next()) {

				Categoria categoria = instantiateCategoria(rs);

				list.add(categoria);

			}

			conn.commit();

			return list;

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
	public List<Categoria> findAllId() {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM categoria ORDER BY id_categoria");
			rs = st.executeQuery();

			List<Categoria> list = new ArrayList<>();

			while (rs.next()) {

				Categoria categoria = instantiateCategoria(rs);
				list.add(categoria);

			}

			conn.commit();

			return list;

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

	private Categoria instantiateCategoria(ResultSet rs) throws SQLException {

		Categoria categoria = new Categoria();
		categoria.setIdCategoria(rs.getInt("id_categoria"));
		categoria.setNome(rs.getString("nome"));
		categoria.setIdSetor(rs.getInt("id_setor"));
		return categoria;

	}

	@Override
	public List<Categoria> findIdSetor(Integer id_setor) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM categoria where id_setor = ? ORDER BY id_categoria");
			st.setInt(1, id_setor);
			rs = st.executeQuery();

			List<Categoria> list = new ArrayList<>();

			while (rs.next()) {

				Categoria categoria = instantiateCategoria(rs);
				list.add(categoria);

			}

			conn.commit();

			return list;

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

}
