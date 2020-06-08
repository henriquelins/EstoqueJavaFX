package exportarXLS;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import gui.util.Strings;
import javafx.collections.ObservableList;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import model.entities.Produto;

public class ExportarListaProdutosEstoqueXLS {

	private WritableCellFormat tahomaBoldUnderline;
	private WritableCellFormat tahoma;

	// Método responsável pela definição das labels
	private void criaLabel(WritableSheet sheet, String vendedor) throws WriteException {
		// Cria o tipo de fonte como TIMES e tamanho
		WritableFont Tahoma10pt = new WritableFont(WritableFont.TAHOMA, 10);

		// Define o formato da célula
		tahoma = new WritableCellFormat(Tahoma10pt);

		// Efetua a quebra automática das células
		// times.setWrap(true);

		// Cria a fonte em negrito com underlines
		WritableFont tahoma10ptBoldUnderline = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD, false);
		// UnderlineStyle.SINGLE);
		tahomaBoldUnderline = new WritableCellFormat(tahoma10ptBoldUnderline);

		// Efetua a quebra automática das células
		// timesBoldUnderline.setWrap(true);
		tahomaBoldUnderline.setAlignment(Alignment.CENTRE);
		tahomaBoldUnderline.setBackground(Colour.GRAY_25);

		CellView cv = new CellView();
		cv.setFormat(tahoma);// Bom pessoal, é isso ai, qualquer dúvida é só avisar.
		cv.setFormat(tahomaBoldUnderline);

		// Escreve os cabeçalhos

		// MESCLAR CÉLULAS: colunaInicial, linhaInicial, colunaFinal, linhaFinal
		sheet.mergeCells(0, 0, 7, 0);

		addCaption(sheet, 0, 0, "Lista de Produtos");

		addCaption(sheet, 0, 1, "#");
		addCaption(sheet, 1, 1, "Nome Produto");
		addCaption(sheet, 2, 1, "Setor");
		addCaption(sheet, 3, 1, "Categoria");
		addCaption(sheet, 4, 1, "Estoque Atual");
		addCaption(sheet, 5, 1, "Estoque Mínimo");
		addCaption(sheet, 6, 1, "Descrição");
		addCaption(sheet, 7, 1, "Status");

	}

	private void defineConteudo(WritableSheet sheet, List<Produto> listaProdutos)
			throws WriteException, RowsExceededException {

		int i = 2;
		int contador = 1;

		for (Produto p : listaProdutos) {

			NumberFormat tresDigitos = new DecimalFormat("000");

			addLabel(sheet, 0, i, String.valueOf(tresDigitos.format(contador)));
			addLabel(sheet, 1, i, p.getNome().toUpperCase());
			addLabel(sheet, 2, i, p.getSetor().toUpperCase());
			addLabel(sheet, 3, i, p.getCategoria().toUpperCase());
			addLabel(sheet, 4, i, String.valueOf(tresDigitos.format(p.getQuantidade())));
			addLabel(sheet, 5, i, String.valueOf(tresDigitos.format(p.getEstoqueMinimo())));
			addLabel(sheet, 6, i, p.getDescricao().toUpperCase());
			addLabel(sheet, 7, i, status(p.getEstoqueMinimo(), p.getQuantidade()));

			i++;
			contador++;
		}

	}

	public String status(Integer estoque_minimo, Integer quantidade) {

		String status = "";

		if (quantidade <= estoque_minimo) {

			status = Strings.getStatus1();

		} else if ((quantidade >= estoque_minimo * 3) || (quantidade <= estoque_minimo * 6)) {

			status = Strings.getStatus2();

		} else {

			status = Strings.getStatus3();

		}

		return status;

	}

// Adiciona cabecalho
	private void addCaption(WritableSheet planilha, int coluna, int linha, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(coluna, linha, s, tahomaBoldUnderline);
		planilha.addCell(label);
	}

	private void addLabel(WritableSheet planilha, int coluna, int linha, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(coluna, linha, s, tahoma);
		planilha.addCell(label);
	}

	public void exportarListaClientesXLS(String inputArquivo, ObservableList<Produto> listaProdutos)
			throws IOException, WriteException {

		// Cria um novo arquivo
		File arquivo = new File(inputArquivo);
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("pt", "BR"));
		WritableWorkbook workbook = Workbook.createWorkbook(arquivo, wbSettings);

		// Define um nome para a planilha
		workbook.createSheet("Lista Produtos Estoque", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		criaLabel(excelSheet, "Produtos");
		defineConteudo(excelSheet, listaProdutos);

		workbook.write();
		workbook.close();

		Runtime.getRuntime().exec("cmd.exe /C start excel.exe " + inputArquivo);

	}

}
