package model.dao;

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
	
}
