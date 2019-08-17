package TelaDeEstoque;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Objetos.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaDeEstoqueController implements Initializable {

    @FXML
    private TextField cxBusca;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private TextField cxCodigo;
    @FXML
    private TextField cxNumeroN;
    @FXML
    private TextField cxDescricao;
    @FXML
    private TextField cxMarca;
    @FXML
    private TextField cxUndMed;
    @FXML
    private TextField cxMed;
    @FXML
    private TextField cxCategoria;
    @FXML
    private TextField cxQuatMinimo;
    @FXML
    private TextField cxQuant;
    @FXML
    private TextField cxValorCusto;
    @FXML
    private TextField cxFornecedor;
    @FXML
    private TextArea cxObs;
    @FXML
    private TableView<Produto> tabProdutos;
    @FXML
    private TableColumn<Produto, Integer> colunCodigo;
    @FXML
    private TableColumn<Produto, String> colunDescricao;
    @FXML
    private TableColumn<Produto, Double> colunCusto;
    @FXML
    private TableColumn<Produto, Integer> colunQuant;
    @FXML
    private TableColumn<Produto, String> colunValidade;
    @FXML
    private TableColumn<Produto, Double> colunValorVenda;
    @FXML
    private TextField cxValidade;
    @FXML
    private TextField cxValorVenda;
    @FXML
    private MenuItem btnExcluir;
    @FXML
    private TextField cxBuscarNome;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        carregarTipo();
        carregarProduto();

        cbTipo.setValue("Em estoque");

        // click na tabela
        tabProdutos.setOnMouseClicked((event) -> {
            int x = tabProdutos.getSelectionModel().getSelectedItem().getCodigo();
            buscarProduto(x);
        });

        // relise na tabela
        tabProdutos.setOnKeyReleased((event) -> {
            int x = tabProdutos.getSelectionModel().getSelectedItem().getCodigo();
            buscarProduto(x);
        });

        // buscar por codigo
        cxBusca.setOnKeyReleased((event) -> {
            buscar(cxBusca.getText());
            limpar();
            cxBuscarNome.setText("");
        });

        // Buscar por nome
        cxBuscarNome.setOnKeyReleased((event) -> {
            BuscarNome(cxBuscarNome.getText());
            cxBusca.setText("");
        });

    }

    // abrir tela de estoque
    public Parent Abrir() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaDeEstoque.fxml"));
        return root;

    }

    // carregar tipos
    private void carregarTipo() {
        ArrayList<String> tipo = new ArrayList<>();

        tipo.add("Em estoque");
        tipo.add("Sem estoque");
        tipo.add("Vencidos");

        ObservableList<String> obs = FXCollections.observableArrayList(tipo);
        cbTipo.setItems(obs);

    }

    // carregar Produtos
    private void carregarProduto() {
        Produto pro = new Produto();
        ArrayList<Produto> lis = pro.listarProduto();
        colunCodigo.setCellValueFactory(new PropertyValueFactory<>("Codigo"));
        colunDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        colunValidade.setCellValueFactory(new PropertyValueFactory<>("Validade"));
        colunCusto.setCellValueFactory(new PropertyValueFactory<>("valorDeCusto"));
        colunQuant.setCellValueFactory(new PropertyValueFactory<>("quant"));
        colunValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorDeVenda"));
        ObservableList<Produto> listarP = FXCollections.observableArrayList(lis);
        tabProdutos.setItems(listarP);
    }

    
    
    // buscar produto
    public void buscarProduto(int cod) {
        Produto pro = new Produto();
        Marca m = new Marca();
        Fornecedor f = new Fornecedor();
        Categoria c = new Categoria();
        pro = pro.buscarProdutoCod(cod);
        cxCodigo.setText(Integer.toString(pro.getCodigo()));
        cxNumeroN.setText(Integer.toString(pro.getNumNota()));
        cxDescricao.setText(pro.getDescricao());
        m = m.buscarMarca(pro.getMarca());
        cxMarca.setText(m.getNome());
        cxUndMed.setText(Integer.toString(pro.getVolume()));
        cxMed.setText(pro.getUnidMedida());
        c = c.buscarCategoria(pro.getCategoria());
        cxCategoria.setText(c.getNome());
        cxValidade.setText(pro.getValidade());
        cxQuatMinimo.setText(Integer.toString(pro.getQuantMinimo()));
        cxQuant.setText(Integer.toString(pro.getQuant()));
        cxValorCusto.setText(Double.toString(pro.getValorDeCusto()));
        cxValorVenda.setText(Double.toString(pro.getValorDeVenda()));
        f = f.buscarFornecedorCodigo(pro.getFornecedor());
        cxFornecedor.setText(f.getNome());
        cxObs.setText(pro.getObservacao());

    }

    // buscar produto
    public void buscar(String cod) {       
        limpar();
        Produto pro = new Produto();
        ArrayList<Produto> busca = pro.buscarProduto(cod);       
        ObservableList<Produto> p = FXCollections.observableArrayList(busca);
        tabProdutos.setItems(p);
    }

    
    
    // excluir produto
    @FXML
    public void excluir() {
        Produto pro = new Produto();
        int cod = tabProdutos.getSelectionModel().getSelectedItem().getCodigo();
        pro.Excluir(cod);
        carregarProduto();
    }

    // editar
    @FXML
    public void editar() {
        cxDescricao.setEditable(true);
        cxMarca.setText("");
        cxUndMed.setText("");
        cxMed.setText("");
        cxCategoria.setText("");
        cxValidade.setText("");
        cxQuatMinimo.setText("");
        cxQuant.setText("");
        cxValorCusto.setText("");
        cxValorVenda.setText("");
        cxFornecedor.setText("");
        cxObs.setText("");

    }

    // buscar por nome
    public void BuscarNome(String nome) {
        Produto pro = new Produto();
        colunCodigo.setCellValueFactory(new PropertyValueFactory<>("Codigo"));
        colunDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        colunValidade.setCellValueFactory(new PropertyValueFactory<>("Validade"));
        colunCusto.setCellValueFactory(new PropertyValueFactory<>("valorDeCusto"));
        colunQuant.setCellValueFactory(new PropertyValueFactory<>("quant"));
        colunValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorDeVenda"));
        ArrayList<Produto> list = pro.listarProdutoNome(nome);
        ObservableList<Produto> ls = FXCollections.observableArrayList(list);
        tabProdutos.setItems(ls);

    }

    // limpar tela
    public void limpar() {

        cxCodigo.setText("");
        cxNumeroN.setText("");
        cxDescricao.setText("");
        cxMarca.setText("");
        cxUndMed.setText("");
        cxMed.setText("");
        cxCategoria.setText("");
        cxValidade.setText("");
        cxQuatMinimo.setText("");
        cxQuant.setText("");
        cxValorCusto.setText("");
        cxValorVenda.setText("");
        cxFornecedor.setText("");
        cxObs.setText("");
    }

}
