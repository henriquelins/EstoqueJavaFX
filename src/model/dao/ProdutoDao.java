package model.dao;

import java.util.List;

import model.entities.Produto;

public interface ProdutoDao {
	
	void insert(Produto produto);
	void update(Produto Produto);
	void updateEstoqueAtual(Integer estoqueAtual, Integer id);
	void deleteById(Integer id);
	Produto findById(Integer id);
	List<Produto> findAll();
	List<Produto> findNomeProduto(String nomeProduto);
	List<Produto> findNomeSetor(String nomeSetor);
	
	void insertFoto(Produto produto);
	void updateFoto(Produto foto);
	void deleteByIdFoto(Integer id);
	
	
}
