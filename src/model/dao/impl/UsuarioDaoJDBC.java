package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.UsuarioDao;
import model.entities.Usuario;

public class UsuarioDaoJDBC implements UsuarioDao {

	private Connection conn;

	public UsuarioDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Usuario usuario) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO usuario " + "(nome_usuario, login, senha) " + "VALUES " + "(?, ?, ?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			st.setString(1, usuario.getNome());
			st.setString(2, usuario.getLogin());
			st.setString(3, usuario.getSenha());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					usuario.setIdUsuario(id);
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
	public void update(Usuario usuario) {
		PreparedStatement st = null;
		try {
			st = conn
					.prepareStatement("UPDATE usuario " + "SET nome_usuario = ?, login = ?, senha = ?" + "WHERE id_usuario = ?");

			st.setString(1, usuario.getNome());
			st.setString(2, usuario.getLogin());
			st.setString(3, usuario.getSenha());
			st.setInt(4, usuario.getIdUsuario());

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
			st = conn.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Usuario findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM usuario WHERE id_usuario = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Usuario usuario = instantiateUsuario(rs);
				return usuario;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Usuario instantiateUsuario(ResultSet rs) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(rs.getInt("id_usuario"));
		usuario.setNome(rs.getString("nome_usuario"));
		usuario.setLogin(rs.getString("login"));
		usuario.setSenha(rs.getString("senha"));
		return usuario;
	}

	@Override
	public List<Usuario> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM usuario ORDER BY nome_usuario");

			rs = st.executeQuery();

			List<Usuario> list = new ArrayList<>();

			while (rs.next()) {
				Usuario usuario = instantiateUsuario(rs);
				list.add(usuario);
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
	public Usuario login(Usuario usuario) {
		PreparedStatement st = null;
		ResultSet rs = null;
		Usuario logado = null;
		try {
			st = conn.prepareStatement("SELECT * FROM usuario WHERE login = ? AND senha = ?");
			st.setString(1, usuario.getLogin());
			st.setString(2, usuario.getSenha());
			rs = st.executeQuery();
			while (rs.next()) {
				logado = instantiateUsuario(rs);
			}
			return logado;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

}
