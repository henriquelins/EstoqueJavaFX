package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Produto;

public class ProdutoService {
	
	Produto produto = new Produto();
	
	public List<Produto> findAll(){
		
		List <Produto> list = new ArrayList<>();
		list.add(new Produto(1,"001", "Garrafa", "Garrafa de coca-cola", 10));
		list.add(new Produto(2,"051", "Copo", "Copo de cervaja", 05));
		
		return list;
		
		
	}

}
