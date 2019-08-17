package CadProduto;

import Objetos.*;
import Objetos.Fornecedor;
import Objetos.NotaEntrada;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class CadProdutoController implements Initializable {

    Fornecedor f = new Fornecedor();

    Stage stage = new Stage();
    @FXML
    private Label lbValor;
    @FXML
    private AnchorPane telacad;
    @FXML
    private TextField cxNumero;
    @FXML
    private TextField cxValor;
    @FXML
    private ComboBox<Fornecedor> cbFornecedor;
    @FXML
    private Button btnProximo;
    @FXML
    private Button btnCancelar;
    @FXML
    private SplitPane telaDeCadastro;
    @FXML
    private Label lblValorProduto;
    @FXML
    private Button btnFinalizar;
    @FXML
    private TextField cxCodigo;
    @FXML
    private TextField cxDescricao;
    @FXML
    private ComboBox<Marca> cxMarca;
    @FXML
    private TextField cxMedida;
    @FXML
    private ComboBox<Categoria> cxCategoria;
    @FXML
    private DatePicker cxValidade;
    @FXML
    private TextField cxQuatMinimo;
    @FXML
    private TextField cxQuantidade;
    @FXML
    private TextField cxValorCusto;
    @FXML
    private TextField cxPorcetagem;
    @FXML
    private TextField cxValorVenda;
    @FXML
    private TextField cxFornecedor;
    @FXML
    private TextArea cxObservacao;
    @FXML
    private Button btnSalvar;
    @FXML
    private TextField cadNumeroDaNota;
    @FXML
    private ComboBox<String> cbUnidade;
    @FXML
    private Button btnAddMarca1;
    @FXML
    private Button btnAddCategoria;
    @FXML
    private TableView<Produto> tabelaDeProduto;
    @FXML
    private TableColumn<Produto, Double> coluValorVenda;
    @FXML
    private TableColumn<Produto, Integer> coluQuant;
    @FXML
    private TableColumn<Produto, Double> coluValorCusto;
    @FXML
    private TableColumn<Produto, String> coluValidade;
    @FXML
    private TableColumn<Produto, String> coluDecricao;
    @FXML
    private TableColumn<Produto, Integer> coluCodigo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            carregarFornecedor();
            Unidade();
            categoria();
            marca();
        } catch (ClassNotFoundException ex) {

        }

        // tratar porcentagem
        cxPorcetagem.setOnKeyReleased((event) -> {
            int porcentagem;
            double custo = Double.parseDouble(cxValorCusto.getText());
            porcentagem = Integer.parseInt(cxPorcetagem.getText());
            double total = custo + (custo * porcentagem / 100);
            BigDecimal x = new BigDecimal(total).setScale(2, RoundingMode.CEILING.HALF_EVEN);
            cxValorVenda.setText(Double.toString(x.doubleValue()));

        });
        // apagando porcentagem
        cxPorcetagem.setOnKeyTyped((event) -> {
            cxValorVenda.setText("");
        });
        //apagando valor de custo
        cxValorCusto.setOnKeyReleased((event) -> {
            cxPorcetagem.setText("");
            cxValorVenda.setText("");
        });

    }

    // passar tela para abrir
    public Parent tela() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CadProduto.fxml"));
        return root;
    }

    @FXML // capturar dados do da nota
    private void capturarDados(MouseEvent event) throws ClassNotFoundException {
        Fornecedor fonecedor = new Fornecedor();
        NotaEntrada nota = new NotaEntrada();
        NotaEntrada n = nota.buscar(Integer.parseInt(cxNumero.getText()));
        boolean existe = nota.existe(Integer.parseInt(cxNumero.getText()));

        //verificando e a nota existe
        if (existe && n.getValor() >= n.getValorProduto()) {
            
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja continuar a nota?", "Nota já existe!", JOptionPane.YES_NO_OPTION,3);

            System.out.println(resposta);

            if (resposta == 0) {
                cadNumeroDaNota.setText(Integer.toString(n.getNumero()));
                lbValor.setText(Double.toString(n.getValor()));
                fonecedor = fonecedor.buscarFornecedorCodigo(n.getFonecedor());
                
                
                lblValorProduto.setText(Double.toString(n.getValorProduto()));
                System.out.println("aq");
                cxFornecedor.setText(fonecedor.getNome());
                System.out.println("proceguir");
                listarProdutos(Integer.parseInt(cxNumero.getText()));
                telaDeCadastro.toFront();
            } else {
                cxNumero.setText("");
                cxValor.setText("");
                cbFornecedor.setValue(null);
            }

        } else if (existe && n.getValor() <= n.getValorProduto()) {            
            JOptionPane.showMessageDialog(null, "Essa Nota ja foi lançada", "Nota Já Existe:", 0);
            cxNumero.setText("");
            cxValor.setText("");
            cbFornecedor.setValue(null);

        } else {
            fonecedor = fonecedor.buscarFornecedor(cbFornecedor.getValue().getNome());
            cadNumeroDaNota.setText(cxNumero.getText());
            lbValor.setText(cxValor.getText());
            cxFornecedor.setText(cbFornecedor.getValue().toString());
            // salvando dados
            nota.setFonecedor(fonecedor.getCodigo());
            nota.setNumero(Integer.parseInt(cxNumero.getText()));
            nota.setValor(Double.parseDouble(cxValor.getText()));
            nota.Cadastrar(nota);
            telaDeCadastro.toFront();
        }

    }

    // carregar fornecedor
    private void carregarFornecedor() throws ClassNotFoundException {
        ArrayList<Fornecedor> forne = f.listarFornecedor();
        ObservableList<Fornecedor> fornecedor = FXCollections.observableArrayList(forne);
        cbFornecedor.setItems(fornecedor);
    }

    // adicionar marca
    @FXML
    public void addMarca() throws ClassNotFoundException {
        Marca m = new Marca();
        String m1 = JOptionPane.showInputDialog("Digite o nome da marca:");
        System.out.println(m1);
        if (!m1.equals("")) {
            m.cadMarca(m1);
            cxMarca.setValue(m);
        }
        marca();
    }

    // adicionar categoria
    @FXML
    public void addCategoria() throws ClassNotFoundException {
        Categoria c = new Categoria();
        String m1 = JOptionPane.showInputDialog("Digite o nome da Categoria:");
        if (!m1.equals("")) {
            c.cadCategoria(m1);
        }
        categoria();
    }

    // cadastrar produto
    @FXML
    public void cadastrarProduto() throws ClassNotFoundException {
        double total;
        Produto pro = new Produto();
        NotaEntrada nota = new NotaEntrada();

        pro.setNumNota(Integer.parseInt(cxNumero.getText()));
        pro.setCodigo(Integer.parseInt(cxCodigo.getText()));
        pro.setDescricao(cxDescricao.getText());
        pro.setMarca(cxMarca.getValue().getCodigo());
        
     
        pro.setVolume(Integer.parseInt(cxMedida.getText()));
        pro.setUnidMedida(cbUnidade.getValue());
        pro.setCategoria(cxCategoria.getValue().getCodigo());

        pro.setValidade(cxValidade.getValue().toString());
        pro.setQuantMinimo(Integer.parseInt(cxQuatMinimo.getText()));
        pro.setQuant(Integer.parseInt(cxQuantidade.getText()));

        pro.setValorDeCusto(Double.parseDouble(cxValorCusto.getText()));
        pro.setValorDeVenda(Double.parseDouble(cxValorVenda.getText()));
          
        nota = nota.buscar(Integer.parseInt(cxNumero.getText()));
        pro.setFornecedor(nota.getFonecedor());
        pro.setObservacao(cxObservacao.getText());

        total = nota.getValorProduto() + pro.getValorDeCusto();

        nota.alterar(total, pro.getNumNota());
        pro.cadProduto(pro);
        lblValorProduto.setText(Double.toString(total));
        listarProdutos(Integer.parseInt(cxNumero.getText()));
        LiparCampos();

    }

    // unidade de medida
    private void Unidade() {
        ArrayList<String> unid = new ArrayList<>();

        unid.add("UN");
        unid.add("CX");
        unid.add("PC");
        unid.add("LT");
        unid.add("MT");
        unid.add("mm");
        unid.add("Kg");
        unid.add("ml");
        unid.add("g");

        ObservableList<String> unidade = FXCollections.observableArrayList(unid);
        cbUnidade.setItems(unidade);

    }

    // listar categoria
    private void categoria() throws ClassNotFoundException {
        Categoria cat = new Categoria();
        ArrayList<Categoria> cate;
        try {
            cate = cat.listar();
            ObservableList<Categoria> categoria = FXCollections.observableArrayList(cate);
            cxCategoria.setItems(categoria);

        } catch (SQLException ex) {
        }

    }

    // listar produto que falta na nota
    private void listarProdutos(int num) throws ClassNotFoundException {
        Produto pro = new Produto();
        ArrayList<Produto> lis = pro.listarProdutoComNota(num);

        coluCodigo.setCellValueFactory(new PropertyValueFactory<>("Codigo"));
        coluDecricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        coluValidade.setCellValueFactory(new PropertyValueFactory<>("Validade"));
        coluValorCusto.setCellValueFactory(new PropertyValueFactory<>("valorDeCusto"));
        coluQuant.setCellValueFactory(new PropertyValueFactory<>("quant"));
        coluValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorDeVenda"));
        ObservableList<Produto> listarP = FXCollections.observableArrayList(lis);
        tabelaDeProduto.setItems(listarP);

    }

    // listar categoria
    private void marca(){
        Marca mar = new Marca();
        ArrayList<Marca> marca;
        try {
            marca = mar.listar();
            ObservableList<Marca> marcas = FXCollections.observableArrayList(marca);
            cxMarca.setItems(marcas);
        } catch (SQLException ex) {
        }

    }

    public void LiparCampos() {
        cxCodigo.setText("");
        cxDescricao.setText("");
        cxMarca.setValue(null);
        cxMedida.setText("");
        cbUnidade.setValue(null);
        cxCategoria.setValue(null);
        cxValidade.setValue(null);
        cxQuatMinimo.setText("");
        cxQuantidade.setText("");
        cxValorCusto.setText("");
        cxPorcetagem.setText("");
        cxValorVenda.setText("");
        cxObservacao.setText("");
    }

}
