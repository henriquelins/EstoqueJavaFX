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
			st = conn.prepareStatement("SELECT * FROM produto WHERE id_produto = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("id_produto"));
				produto.setNome(rs.getString("nome_produto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setSetor(rs.getString("setor"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setQuantidade(rs.getInt("quantidade"));
				return produto;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			
		}
	}

	@Override
	public List<Produto> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM produto ORDER BY id_produto");
			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("id_produto"));
				produto.setNome(rs.getString("nome_produto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setSetor(rs.getString("setor"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setQuantidade(rs.getInt("quantidade"));
				list.add(produto);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			
		}
	}

	@Override
	public void insert(Produto produto) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO produto (nome_produto, descricao, setor, categoria, quantidade)" + " VALUES (?, ?, ? ,?, ?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			st.setString(1, produto.getNome());
			st.setString(2, produto.getDescricao());
			st.setString(3, produto.getSetor());
			st.setString(4, produto.getCategoria());
			st.setInt(5, produto.getQuantidade());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					produto.setIdProduto(id);
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
	public void update(Produto produto) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement(
					"UPDATE produto SET nome_produto = ?, descricao = ?, setor = ?, categoria = ?, quantidade = ? WHERE id_produto = ?");

			st.setString(1, produto.getNome());
			st.setString(2, produto.getDescricao());
			st.setString(3, produto.getSetor());
			st.setString(4, produto.getCategoria());
			st.setInt(5, produto.getQuantidade());
			st.setInt(6, produto.getIdProduto());

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
			st = conn.prepareStatement("DELETE FROM produto WHERE id_produto = ?");

			st.setInt(1, id);

			st.executeUpdate();	
		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			
		}
	}

	@Override
	public void updateEstoqueAtual(Integer estoqueAtual, Integer idProduto) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement(
					"UPDATE produto SET quantidade = ? WHERE id_produto = ?");

			st.setInt(1, estoqueAtual);
			st.setInt(2, idProduto);
			

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			
		}
		
	}
}
