package TelaNotas;

import Objetos.NotaEntrada;
import Objetos.Produto;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import Objetos.*;
import javafx.scene.control.MenuItem;
import javax.swing.JOptionPane;

public class TelaNotaController implements Initializable {

    @FXML
    private TableView<NotaEntrada> tabNotas;
    @FXML
    private TextField cxPesquisaNota;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField lblValorNota;
    @FXML
    private TableView<Produto> tabProduto;
    @FXML
    private TableColumn<?, ?> coluCodigo;
    @FXML
    private TableColumn<?, ?> coloDescricao;
    @FXML
    private TableColumn<?, ?> coluValidade;
    @FXML
    private TableColumn<?, ?> coluValorCusto;
    @FXML
    private TableColumn<?, ?> coluQuant;
    @FXML
    private TableColumn<?, ?> coluValorVenda;
    @FXML
    private TableColumn<NotaEntrada, Integer> coluTodasNotas;
    @FXML
    private TextField cxFornecedor;
    @FXML
    private TextField cxValorProduto;
    @FXML
    private Label lblFinaliza;
    @FXML
    private MenuItem btnExcluir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listarNotas();

        // click na tabela
        tabNotas.setOnMouseClicked((event) -> {
            int x = tabNotas.getSelectionModel().getSelectedItem().getNumero();
            listarProdutos(x);

        });

        // relise na tabela
        tabNotas.setOnKeyReleased((event) -> {
            int x = tabNotas.getSelectionModel().getSelectedItem().getNumero();
            listarProdutos(x);
        });

        cxPesquisaNota.setOnKeyPressed((event) -> {
            listarNotas();
            tabProduto.setItems(null);
        });

    }

    // abrir tela de Nota
    public Parent Abrir() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaNota.fxml"));
        return root;

    }

    // listando todas as notas
    private void listarNotas() {
        NotaEntrada nota = new NotaEntrada();
        ArrayList<NotaEntrada> lis = nota.ListandoNota();
        coluTodasNotas.setCellValueFactory(new PropertyValueFactory<>("numero"));
        ObservableList<NotaEntrada> listarP = FXCollections.observableArrayList(lis);
        tabNotas.setItems(listarP);
    }

    // carregando  produto
    private void listarProdutos(int num) {
        Produto pro = new Produto();
        NotaEntrada n = new NotaEntrada();
        Fornecedor f = new Fornecedor();

        n = n.buscar(num);
        f = f.buscarFornecedorCodigo(n.getFonecedor());

        cxFornecedor.setText(f.getNome());
        lblValorNota.setText(Double.toString(n.getValor()));
        cxValorProduto.setText(Double.toString(n.getValorProduto()));

        if (n.getValor() > n.getValorProduto()) {
            lblFinaliza.setText("Não Finalizado");
        } else {
            lblFinaliza.setText("Finalizado");
        }

        ArrayList<Produto> lis = pro.listarProdutoComNota(num);
        coluCodigo.setCellValueFactory(new PropertyValueFactory<>("Codigo"));
        coloDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        coluValidade.setCellValueFactory(new PropertyValueFactory<>("Validade"));
        coluValorCusto.setCellValueFactory(new PropertyValueFactory<>("valorDeCusto"));
        coluQuant.setCellValueFactory(new PropertyValueFactory<>("quant"));
        coluValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorDeVenda"));
        ObservableList<Produto> listarP = FXCollections.observableArrayList(lis);
        tabProduto.setItems(listarP);

    }

    @FXML   // buscar nota
    public void buscarNota() {
        tabNotas.setItems(null);
        tabProduto.setItems(null);
        NotaEntrada nota = new NotaEntrada();
        nota = nota.buscar(Integer.parseInt(cxPesquisaNota.getText()));
        ArrayList<NotaEntrada> lis = new ArrayList<>();
        lis.add(nota);
        coluTodasNotas.setCellValueFactory(new PropertyValueFactory<>("numero"));
        ObservableList<NotaEntrada> listarP = FXCollections.observableArrayList(lis);
        tabNotas.setItems(listarP);

    }

    // excluir
    @FXML
    public void Excluir() {
        JOptionPane.showMessageDialog(null, "Ateção você só poderá apagar a nota se ela não tiver mais produto.", "Ateção", 2);      
        NotaEntrada n = new NotaEntrada();                
        int num = tabNotas.getSelectionModel().getSelectedItem().getNumero();        
        n.Excluir(num);
        listarNotas();
    }

}
