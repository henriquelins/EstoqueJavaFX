package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.MovimentacaoDao;
import model.entities.Movimentacao;

public class MovimentacaoDaoJDBC implements MovimentacaoDao {

	@Override
	public Movimentacao findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movimentacao> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private Connection conn;

	public MovimentacaoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Movimentacao movimentacao) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO movimentacao "
					+ "(idProduto, idUsuario, tipo, valorMovimento, observacoesMovimentacao, quantidadeAnterior, dataDaTransacao) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)", java.sql.Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, movimentacao.getIdProduto());
			st.setInt(2, movimentacao.getIdUsuario());
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

	@Override
	public void update(Movimentacao movimentacao) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE usuario "
					+ "SET idProduto = ?, idUsuario = ?, tipo = ?, valorMovimento = ?, observacoesMovimentacao = ?, quantidadeAnterior = ?, dataDaTransacao = ?"
					+ "WHERE idMovimentacao = ?");

			st.setInt(1, movimentacao.getIdProduto());
			st.setInt(2, movimentacao.getIdUsuario());
			st.setString(3, movimentacao.getTipo());
			st.setInt(4, movimentacao.getValorMovimento());
			st.setString(5, movimentacao.getObservacoesMovimentacao());
			st.setInt(6, movimentacao.getQuantidadeAnterior());
			st.setDate(7, new java.sql.Date(movimentacao.getDataDaTransacao().getTime()));
			st.setInt(8, movimentacao.getIdMovimentacao());

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
			st = conn.prepareStatement("DELETE FROM usuario WHERE idUsuario = ?");

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	/*@Override
	public Usuario findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	/*
	 * @Override public List<Seller> findAll() { PreparedStatement st = null;
	 * ResultSet rs = null; try { st = conn.prepareStatement(
	 * "SELECT seller.*,department.Name as DepName " +
	 * "FROM seller INNER JOIN department " +
	 * "ON seller.DepartmentId = department.Id " + "ORDER BY Name");
	 * 
	 * rs = st.executeQuery();
	 * 
	 * List<Seller> list = new ArrayList<>(); Map<Integer, Department> map = new
	 * HashMap<>();
	 * 
	 * while (rs.next()) {
	 * 
	 * Department dep = map.get(rs.getInt("DepartmentId"));
	 * 
	 * if (dep == null) { dep = instantiateDepartment(rs);
	 * map.put(rs.getInt("DepartmentId"), dep); }
	 * 
	 * Seller obj = instantiateSeller(rs, dep); list.add(obj); } return list; }
	 * catch (SQLException e) { throw new DbException(e.getMessage()); } finally {
	 * DB.closeStatement(st); DB.closeResultSet(rs); } }
	 * 
	 * @Override public List<Seller> findByDepartment(Department department) {
	 * PreparedStatement st = null; ResultSet rs = null; try { st =
	 * conn.prepareStatement( "SELECT seller.*,department.Name as DepName " +
	 * "FROM seller INNER JOIN department " +
	 * "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " +
	 * "ORDER BY Name");
	 * 
	 * st.setInt(1, department.getId());
	 * 
	 * rs = st.executeQuery();
	 * 
	 * List<Seller> list = new ArrayList<>(); Map<Integer, Department> map = new
	 * HashMap<>();
	 * 
	 * while (rs.next()) {
	 * 
	 * Department dep = map.get(rs.getInt("DepartmentId"));
	 * 
	 * if (dep == null) { dep = instantiateDepartment(rs);
	 * map.put(rs.getInt("DepartmentId"), dep); }
	 * 
	 * Seller obj = instantiateSeller(rs, dep); list.add(obj); } return list; }
	 * catch (SQLException e) { throw new DbException(e.getMessage()); } finally {
	 * DB.closeStatement(st); DB.closeResultSet(rs); } }
	 */
}
