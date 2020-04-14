package model.dao;

import java.sql.Date;
import java.util.List;

import model.entities.Movimentacao;

public interface MovimentacaoDao {
	
	void insert(Movimentacao movimentacao);
	void update(Movimentacao movimentacao);
	void deleteById(Integer id);
	Movimentacao findById(Integer id);
	List<Movimentacao> findAll();
	List<Movimentacao> findNomeProduto(String pesquisarProduto);
	List<Movimentacao> findNomeSetor(String pesquisarSetor);
	List<Movimentacao> verMovimentacao(Date dataInicial, Date dataFinal, int id_produto);
	
}
