package gui.util;

public class Acesso {

	public boolean concederAcesso(Integer acesso, String tela) {

		boolean concedido = false;

		if (acesso == 1 && tela.equalsIgnoreCase(Strings.getSetorNovoView())) {

			concedido = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getCategoriaNovoView())) {

			concedido = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getProdutoNovoView())) {

			concedido = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getProdutoEditarView())) {

			concedido = true;

		} else if ((acesso == 1 && tela.equalsIgnoreCase(Strings.getProdutoShowView()))
				|| (acesso == 2 && tela.equalsIgnoreCase(Strings.getProdutoShowView()))) {

			concedido = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getExcluirProduto())) {

			concedido = true;

		} else if (acesso == 1 && tela.equalsIgnoreCase(Strings.getUsuarioView())) {

			concedido = true;

		} else if ((acesso == 1 && tela.equalsIgnoreCase(Strings.getMovimentacaoListView()))
				|| (acesso == 2 && tela.equalsIgnoreCase(Strings.getMovimentacaoListView()))) {

			concedido = true;

		} else if ((acesso == 1 && tela.equalsIgnoreCase(Strings.getMovimentacaoView()))
				|| (acesso == 2 && tela.equalsIgnoreCase(Strings.getMovimentacaoView()))) {

			concedido = true;

		} else {

			concedido = false;

		}

		return concedido;

	}

}
