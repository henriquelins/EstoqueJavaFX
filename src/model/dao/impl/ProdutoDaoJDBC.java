package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
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

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM produto where id_produto = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			while (rs.next()) {

				Produto produto = instantiateProduto(rs);
				return produto;

			}

			conn.commit();

			return null;

		} catch (SQLException e) {

			try {

				conn.rollback();
				throw new DbException("Transaction rolled back. Cause by: " + e.getLocalizedMessage());

			} catch (SQLException e1) {

				throw new DbException("Error trying to rollback. Cause by: " + e.getLocalizedMessage());
			}

		} finally {

			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}

	}

	@Override
	public List<Produto> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<Produto> list = new ArrayList<>();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM produto order by id_produto");
			rs = st.executeQuery();

			while (rs.next()) {

				Produto produto = instantiateProduto(rs);
				list.add(produto);

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
			DB.closeResultSet(rs);

		}

	}

	@Override
	public void insert(Produto produto) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"INSERT INTO produto (nome_produto, descricao, setor, categoria, quantidade, estoque_minimo, foto)"
							+ " VALUES (?, ?, ? ,?, ?, ?, ?)");

			st.setString(1, produto.getNome());
			st.setString(2, produto.getDescricao());
			st.setString(3, produto.getSetor());
			st.setString(4, produto.getCategoria());
			st.setInt(5, produto.getQuantidade());
			st.setInt(6, produto.getEstoqueMinimo());
			st.setBytes(7, produto.getFoto());

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
	public void update(Produto produto) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"UPDATE produto SET nome_produto = ?, descricao = ?, setor = ?, categoria = ?, quantidade = ?, estoque_minimo = ? , foto = ? WHERE id_produto = ?");

			st.setString(1, produto.getNome());
			st.setString(2, produto.getDescricao());
			st.setString(3, produto.getSetor());
			st.setString(4, produto.getCategoria());
			st.setInt(5, produto.getQuantidade());
			st.setInt(6, produto.getEstoqueMinimo());
			st.setBytes(7, produto.getFoto());
			st.setInt(8, produto.getIdProduto());

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

			st = conn.prepareStatement("DELETE FROM produto WHERE id_produto = ?");

			st.setInt(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {

				throw new DbException("Erro ao deletar o produto");

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
	public void updateEstoqueAtual(Integer estoqueAtual, Integer idProduto) {

		PreparedStatement st = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("UPDATE produto SET quantidade = ? WHERE id_produto = ?");

			st.setInt(1, estoqueAtual);
			st.setInt(2, idProduto);

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
	public List<Produto> findNomeProduto(String nomeProduto) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM produto where nome_produto ilike ? order by id_produto");
			st.setString(1, "%" + nomeProduto + "%");
			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();

			while (rs.next()) {

				Produto produto = instantiateProduto(rs);
				list.add(produto);

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
			DB.closeResultSet(rs);

		}

	}

	@Override
	public List<Produto> findNomeSetor(String nomeSetor) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM produto where setor = ? order by id_produto");
			st.setString(1, nomeSetor.toUpperCase());
			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();

			while (rs.next()) {

				Produto produto = instantiateProduto(rs);
				list.add(produto);

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
			DB.closeResultSet(rs);

		}

	}

	private Produto instantiateProduto(ResultSet rs) throws SQLException {

		Produto produto = new Produto();

		produto.setIdProduto(rs.getInt("id_produto"));
		produto.setNome(rs.getString("nome_produto"));
		produto.setDescricao(rs.getString("descricao"));
		produto.setSetor(rs.getString("setor"));
		produto.setCategoria(rs.getString("categoria"));
		produto.setQuantidade(rs.getInt("quantidade"));
		produto.setEstoqueMinimo(rs.getInt("estoque_minimo"));
		produto.setFoto(rs.getBytes("foto"));

		return produto;

	}

	@Override
	public List<Produto> findEstoqueBaixo() {
	
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Produto> list = new ArrayList<>();

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM produto WHERE quantidade <= (estoque_minimo*3)");
			rs = st.executeQuery();

			

			while (rs.next()) {

				Produto produto = instantiateProduto(rs);
				list.add(produto);

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
			DB.closeResultSet(rs);

		}

		
	}
}
