package model.services;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.scene.control.Alert.AlertType;
import model.entities.Produto;

public class ProdutoService {
	
	Produto produto = new Produto();
	
	/*public List<Produto> findAll(){
		
		/*List <Produto> list = new ArrayList<>();
		list.add(new Produto(1,"001", "Garrafa", "Garrafa de coca-cola", 10));
		list.add(new Produto(2,"051", "Copo", "Copo de cervaja", 05));
		
		return list;
		
		
	}*/
	
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
