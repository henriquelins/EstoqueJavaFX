package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.ProdutoDao;
import model.entities.Produto;

public class ProdutoDaoJDBC implements ProdutoDao {

	private Connection conn;
	
	public ProdutoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Produto findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM produto WHERE idProduto = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setSetor(rs.getString("setor"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setQuantidade(rs.getInt("quantidade"));
				return produto;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Produto> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM produto ORDER BY idProduto");
			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setSetor(rs.getString("setor"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setQuantidade(rs.getInt("quantidade"));
				list.add(produto);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void insert(Produto produto) {
		PreparedStatement st = null;
		try {
			int index = 1;
			
			st = conn.prepareStatement(
				"INSERT INTO produto (nome, descricao, setor, categoria, quantidade)"
				+ " VALUES (?, ?, ? ,?, ?)", 
				java.sql.Statement.RETURN_GENERATED_KEYS);
			
			st.setString(index, produto.getNome());
			st.setString(index++, produto.getDescricao());
			st.setString(index++, produto.getSetor());
			st.setString(index++, produto.getCategoria());
			st.setInt(index++, produto.getQuantidade());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					produto.setIdProduto(id);
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Produto produto) {
		PreparedStatement st = null;
		try {
			int index = 1;
			
			st = conn.prepareStatement(
				"UPDATE produto SET name = ?,  descricao = ?, setor = ?, categoria = ?, quantidade = ? WHERE IdProduto = ?");

			st.setString(index, produto.getNome());
			st.setString(index++, produto.getDescricao());
			st.setString(index++, produto.getSetor());
			st.setString(index++, produto.getCategoria());
			st.setInt(index++, produto.getQuantidade());
			st.setInt(index++, produto.getIdProduto());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM produto WHERE idProduto = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}
}
