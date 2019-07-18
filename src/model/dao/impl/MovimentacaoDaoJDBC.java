package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.MovimentacaoDao;
import model.entities.Movimentacao;
import model.entities.Produto;
import model.entities.Usuario;

public class MovimentacaoDaoJDBC implements MovimentacaoDao {

	private Connection conn;

	public MovimentacaoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Movimentacao> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select mv.*,  pr.*, us.* from "
					+ "movimentacao as mv inner join produto pr on mv.idProduto = pr.idProduto inner "
					+ "join usuario as us on mv.idUsuario = us.idUsuario ORDER BY mv.idMovimentacao asc");

			rs = st.executeQuery();

			List<Movimentacao> listaMovimentacao = new ArrayList<>();

			while (rs.next()) {
				
				Produto prod = instantiateProduto(rs);
				Usuario user = instantiateUsuario(rs);
							
				Movimentacao mov = instantiateMovimentacao(rs, prod, user);
				listaMovimentacao.add(mov);
				
			}

			return listaMovimentacao;

		} catch (SQLException e) {
			
			throw new DbException(e.getMessage());
			
		} finally {
			
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
	}

	@Override
	public void insert(Movimentacao movimentacao) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO movimentacao "
					+ "(idProduto, idUsuario, tipo, valorMovimento, observacoesMovimentacao, quantidadeAnterior, dataDaTransacao) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)", java.sql.Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, movimentacao.getProduto().getIdProduto());
			st.setInt(2, movimentacao.getUsuario().getIdUsuario());
			st.setString(3, movimentacao.getTipo());
			st.setInt(4, movimentacao.getValorMovimento());
			st.setString(5, movimentacao.getObservacoesMovimentacao());
			st.setInt(6, movimentacao.getQuantidadeAnterior());
			st.setDate(7, new java.sql.Date(movimentacao.getDataDaTransacao().getTime()));

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				
				ResultSet rs = st.getGeneratedKeys();
				
				if (rs.next()) {
					int id = rs.getInt(1);
					movimentacao.setIdMovimentacao(id);
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

	private Movimentacao instantiateMovimentacao(ResultSet rs, Produto prod, Usuario user) throws SQLException {
		
		Movimentacao mov = new Movimentacao();
		mov.setIdMovimentacao(rs.getInt("IdMovimentacao"));
		mov.setTipo(rs.getString("tipo"));
		mov.setValorMovimento(rs.getInt("valorMovimento"));
		mov.setObservacoesMovimentacao(rs.getString("observacoesMovimentacao"));
		mov.setQuantidadeAnterior(rs.getInt("quantidadeAnterior"));
		mov.setDataDaTransacao(rs.getDate("dataDaTransacao"));

		if (mov.getTipo().equalsIgnoreCase("Entrada de produtos (+)")) {

			mov.setEstoqueAtual(mov.getQuantidadeAnterior() + mov.getValorMovimento());

		} else {
			
			mov.setEstoqueAtual(mov.getQuantidadeAnterior() - mov.getValorMovimento());

		}

		mov.setProduto(prod);
		mov.setUsuario(user);
		return mov;
		
	}

	private Produto instantiateProduto(ResultSet rs) throws SQLException {
		
		Produto prod = new Produto();
		prod.setIdProduto(rs.getInt("idProduto"));
		prod.setNome(rs.getString("nome"));
		prod.setDescricao(rs.getString("descricao"));
		prod.setSetor(rs.getString("setor"));
		prod.setCategoria(rs.getString("categoria"));
		prod.setQuantidade(rs.getInt("quantidade"));
		return prod;
		
	}

	private Usuario instantiateUsuario(ResultSet rs) throws SQLException {
		
		Usuario user = new Usuario();
		user.setIdUsuario(rs.getInt("idUsuario"));
		user.setNome(rs.getString("nome_usuario"));
		user.setLogin(rs.getString("login"));
		user.setSenha(rs.getString("senha"));
		return user;
		
	}

	@Override
	public void update(Movimentacao movimentacao) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Movimentacao findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
