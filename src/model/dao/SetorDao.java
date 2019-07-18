package model.dao;

import java.util.List;

import model.entities.Setor;

public interface SetorDao {
	void insert(Setor setor);
	void update(Setor setor);
	void deleteById(Integer id);
	List<Setor> findAllId();
	List<Setor> findAllNome();
}
