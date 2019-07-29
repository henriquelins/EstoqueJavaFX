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
import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import model.dao.ProdutoDao;
import model.entities.Foto;
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

			st = conn.prepareStatement("SELECT * FROM foto WHERE id_foto = ?");

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

			conn.setAutoCommit(false);

			st = conn.prepareStatement("SELECT * FROM produto as pr inner join foto as ft on pr.id_produto = ft.id_produto order by pr.id_produto");
			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();

			while (rs.next()) {

				Produto produto = new Produto();
				Foto foto = new Foto();

				produto.setIdProduto(rs.getInt("id_produto"));
				produto.setNome(rs.getString("nome_produto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setSetor(rs.getString("setor"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setQuantidade(rs.getInt("quantidade"));

				foto.setIdFoto(rs.getInt("id_foto"));
				foto.setFoto(rs.getBytes("foto"));
				foto.setLocal(rs.getString("local"));

				produto.setFoto(foto);
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

		}

	}

	@Override
	public void insert(Produto produto) {

		PreparedStatement st = null;
		ResultSet rs = null;
		int id = 0;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"INSERT INTO produto (nome_produto, descricao, setor, categoria, quantidade, estoque_minimo)"
							+ " VALUES (?, ?, ? ,?, ?, ?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);

			st.setString(1, produto.getNome());
			st.setString(2, produto.getDescricao());
			st.setString(3, produto.getSetor());
			st.setString(4, produto.getCategoria());
			st.setInt(5, produto.getQuantidade());
			st.setInt(6, produto.getEstoqueMinimo());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {

				rs = st.getGeneratedKeys();

				if (rs.next()) {

					id = rs.getInt(1);
					produto.setIdProduto(id);

				}

				insertFoto(produto);

				conn.commit();

			} else {

				new DbException("Unexpected error! No rows affected!");

			}

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

			st = conn.prepareStatement("UPDATE produto SET quantidade = ? WHERE id_produto = ?");

			st.setInt(1, estoqueAtual);
			st.setInt(2, idProduto);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);

		}

	}

	@Override
	public void insertFoto(Produto produto) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO foto (id_produto, foto, local)" + " VALUES (?, ?, ?)");

			st.setInt(1, produto.getIdProduto());
			st.setBytes(2, produto.getFoto().getFoto());
			st.setString(3, produto.getFoto().getLocal());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {

				Alerts.showAlert("Inserir foto", null, "ok", AlertType.ERROR);

			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void updateFoto(Produto foto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteByIdFoto(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Produto> findNomeProduto(String nomeProduto) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"SELECT * FROM produto as pr inner join foto as ft on pr.id_produto = ft.id_produto where pr.nome_produto like ? order by pr.id_produto");
			st.setString(1, "%" + nomeProduto + "%");
			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();

			while (rs.next()) {

				Produto produto = new Produto();
				Foto foto = new Foto();

				produto.setIdProduto(rs.getInt("id_produto"));
				produto.setNome(rs.getString("nome_produto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setSetor(rs.getString("setor"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setQuantidade(rs.getInt("quantidade"));

				foto.setIdFoto(rs.getInt("id_foto"));
				foto.setFoto(rs.getBytes("foto"));
				foto.setLocal(rs.getString("local"));

				produto.setFoto(foto);
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

		}

	}

	@Override
	public List<Produto> findNomeSetor(String nomeSetor) {
		
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			conn.setAutoCommit(false);

			st = conn.prepareStatement(
					"SELECT * FROM produto as pr inner join foto as ft on pr.id_produto = ft.id_produto where pr.setor = ? order by pr.id_produto");
			st.setString(1, nomeSetor );
			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();

			while (rs.next()) {

				Produto produto = new Produto();
				Foto foto = new Foto();

				produto.setIdProduto(rs.getInt("id_produto"));
				produto.setNome(rs.getString("nome_produto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setSetor(rs.getString("setor"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setQuantidade(rs.getInt("quantidade"));

				foto.setIdFoto(rs.getInt("id_foto"));
				foto.setFoto(rs.getBytes("foto"));
				foto.setLocal(rs.getString("local"));

				produto.setFoto(foto);
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

		}
		
	}
}
