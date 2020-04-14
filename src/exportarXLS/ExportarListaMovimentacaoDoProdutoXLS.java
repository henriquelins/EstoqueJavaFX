package exportarXLS;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import gui.util.Constraints;
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
import model.entities.Movimentacao;

public class ExportarListaMovimentacaoDoProdutoXLS {

	private WritableCellFormat tahomaBoldUnderline;
	private WritableCellFormat tahoma;

	// Método responsável pela definição das labels
	private void criaLabel(WritableSheet sheet, String nomeProduto, LocalDate localDateInicial,
			LocalDate localDateFinal) throws WriteException {
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
		sheet.mergeCells(0, 1, 7, 1);

		addCaption(sheet, 0, 0, "Movimentacao do Produto " + nomeProduto);

		addCaption(sheet, 0, 1,
				"Pesquisa - Data inicial: " + localDateInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
						+ "     Data final: " + localDateFinal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		addCaption(sheet, 0, 2, "#");
		addCaption(sheet, 1, 2, "Movimentação");
		addCaption(sheet, 2, 2, "Estoque anterior");
		addCaption(sheet, 3, 2, "Valor da movimentação");
		addCaption(sheet, 4, 2, "Estoque Atual");
		addCaption(sheet, 5, 2, "Observações");
		addCaption(sheet, 6, 2, "Usuário");
		addCaption(sheet, 7, 2, "Data do movimento");

	}

	private void defineConteudo(WritableSheet sheet, List<Movimentacao> listaMovimentacao)
			throws WriteException, RowsExceededException {

		int i = 3;
		int contador = 1;

		for (Movimentacao m : listaMovimentacao) {

			NumberFormat tresDigitos = new DecimalFormat("000");

			addLabel(sheet, 0, i, String.valueOf(tresDigitos.format(contador)));
			addLabel(sheet, 1, i, m.getTipo().toUpperCase());
			addLabel(sheet, 2, i, String.valueOf(tresDigitos.format(m.getQuantidadeAnterior())));
			addLabel(sheet, 3, i, String.valueOf(tresDigitos.format(m.getValorMovimento())));
			addLabel(sheet, 4, i, String.valueOf(tresDigitos.format(m.getEstoqueAtual())));
			addLabel(sheet, 5, i, m.getObservacoesMovimentacao().toUpperCase());
			addLabel(sheet, 6, i, m.getUsuario().getNome().toUpperCase());
			addLabel(sheet, 7, i, Constraints.setDateFormat(m.getDataDaTransacao()));

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

	public void exportarListaMovimentacaoDoProdutoXLS(String inputArquivo,
			ObservableList<Movimentacao> listaMovimentacao, String nomeProduto, LocalDate localDateInicial,
			LocalDate localDateFinal) throws IOException, WriteException {

		// Cria um novo arquivo
		File arquivo = new File(inputArquivo);
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("pt", "BR"));
		WritableWorkbook workbook = Workbook.createWorkbook(arquivo, wbSettings);

		// Define um nome para a planilha
		workbook.createSheet("Movimentação do Produto", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		criaLabel(excelSheet, nomeProduto, localDateInicial, localDateFinal);
		defineConteudo(excelSheet, listaMovimentacao);

		workbook.write();
		workbook.close();

		Runtime.getRuntime().exec("cmd.exe /C start excel.exe " + inputArquivo);

	}

}
