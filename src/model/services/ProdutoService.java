package model.services;

import java.util.ArrayList;
import java.util.List;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.scene.control.Alert.AlertType;
import model.entities.Produto;
import model.entities.enums.Categoria;
import model.entities.enums.Setor;

public class ProdutoService {
	
	Produto produto = new Produto();
	
	public List<Produto> findAll(){
		
		List<Produto> listaProduto = new ArrayList<>();
		
		listaProduto.add(new Produto(1,"Tinta black", "Tinta pigmentada", Setor.CRACHAS.toString(),Categoria.TINTAS_CRACHAS.toString(),2));
		listaProduto.add(new Produto(2,"Protetor rígido", "Protetor para crachá transparente",Setor.CRACHAS.toString(),Categoria.INSUMOS_CRACHAS.toString(),20));
		listaProduto.add(new Produto(3,"Presilhas", "Presilhas leitosas para crachás",Setor.CRACHAS.toString(), Categoria.INSUMOS_CRACHAS.toString(),100));
	
		return null;
		
	}
	
	public void produtoNovoOuEditar(Produto produto) {

		if (produto.getIdProduto() == null) {

			Alerts.showAlert("Produto", null, "Novo Produto", AlertType.ERROR);
						
			Utils.fecharDialogAction();
		
		} else {

			Alerts.showAlert("Produto", null, "Editar Produto", AlertType.ERROR);
						
			Utils.fecharDialogAction();
		
		}

	}
	
	

}
