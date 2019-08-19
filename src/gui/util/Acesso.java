package gui.util;

public class Acesso {

	public boolean concederAcesso(Integer acesso, String tela) {

		boolean concedio = false;

		if (acesso == 1 && tela.equalsIgnoreCase(Strings.getSetorNovoView())) {

			concedio = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getCategoriaNovoView())) {

			concedio = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getProdutoNovoView())) {

			concedio = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getProdutoEditarView())) {

			concedio = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getExcluirProduto())) {

			concedio = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getUsuarioView())) {

			concedio = true;

		} else if ((acesso == 1 && tela.equalsIgnoreCase(Strings.getMovimentacaoListView())) || (acesso == 2 && tela.equalsIgnoreCase(Strings.getMovimentacaoListView()))) {

			concedio = true;

		} else if ((acesso == 1 && tela.equalsIgnoreCase(Strings.getMovimentacaoView())) || (acesso == 2 && tela.equalsIgnoreCase(Strings.getMovimentacaoView()))) {

			concedio = true;

		} else {

			concedio = false;

		}

		return concedio;

	}

}
