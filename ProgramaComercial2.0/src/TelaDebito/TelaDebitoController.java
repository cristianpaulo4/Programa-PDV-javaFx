package TelaDebito;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import Objetos.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import Auxiliares.*;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;

public class TelaDebitoController implements Initializable {

    Compra compra = new Compra();
    ProdutoVendido produto = new ProdutoVendido();
    ArrayList<ProdutoVendido> ListaHistorico = produto.listarProduto();
    ArrayList<Compra> listaCompra = new ArrayList<>();

    Cliente cli = new Cliente();
    @FXML
    private TableView<Cliente> tabCliente;
    @FXML
    private TableColumn<Cliente, String> coluCliente;
    @FXML
    private TextField cxBuscar;
    @FXML
    private TableView<ProdutoVendido> tabProduto;
    @FXML
    private TableColumn<ProdutoVendido, Integer> coluCodigo;
    @FXML
    private TableColumn<ProdutoVendido, String> coluProduto;
    @FXML
    private TableColumn<ProdutoVendido, Integer> coluQuant;
    @FXML
    private TableColumn<ProdutoVendido, Double> coluValor;
    @FXML
    private Label lblDebito;
    @FXML
    private TextField cxNomeCliente;
    @FXML
    private TextField cxCPF;
    @FXML
    private TextField cxEndereco;
    @FXML
    private Label lblHaver;
    @FXML
    private VBox telaPagar;
    @FXML
    private RadioButton dinheiro;
    @FXML
    private ToggleGroup formaPagamento;
    @FXML
    private RadioButton cartao;
    @FXML
    private RadioButton cheque;
    @FXML
    private TextField cxValorPago;
    @FXML
    private TextField cxTrocoCliente;
    @FXML
    private Label lblTotal2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listar();
        telaPagar.toBack();

        // buscando por nome
        cxBuscar.setOnKeyReleased((event) -> {
            buscar(cxBuscar.getText());
        });

    }

    // abrir Tela de Debito
    public Parent abrir() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaDebito.fxml"));
        return root;
    }

    // listar clientes
    public void listar() {
        coluCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        ObservableList<Cliente> list = FXCollections.observableArrayList(cli.listar());
        tabCliente.setItems(list);

    }

    // listar por nome
    public void buscar(String nome) {
        coluCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        ObservableList<Cliente> list = FXCollections.observableArrayList(cli.buscarNome(nome));
        tabCliente.setItems(list);

    }

    // carregar debitos
    @FXML
    public void carregarDebitos() {

        int codCliente = tabCliente.getSelectionModel().getSelectedItem().getCodigo();
        cli = cli.buscar(codCliente);

        if (cli.getDebito() > 0) {
            CarregarTabelaProduto();
            
        } else {
            JOptionPane.showMessageDialog(null, "Este cliente não está devendo!");
            LimparDados();
            
            
        }

        cxNomeCliente.setText(cli.getNome());
        cxCPF.setText(cli.getCpf());
        cxEndereco.setText("Bairro: " + cli.getBairro() + " / Rua: " + cli.getRua() + " / Numero: " + cli.getNumero());

    }

    // carregar tabela de produtos
    public void CarregarTabelaProduto() {

        ArrayList<ProdutoVendido> ListaProdutoVendido = new ArrayList<>();
        // lista de compras        
        listaCompra = compra.BuscarCompraPorCliente(cli.getCodigo());

        // lista de produtos vendidos   
        listaCompra.forEach((t) -> {
            if (!t.getQuitada()) {
                for (int i = 0; i < ListaHistorico.size(); i++) {
                    if (ListaHistorico.get(i).getCodVenda() == t.getCodVenda()) {
                        ListaProdutoVendido.add(ListaHistorico.get(i));
                    }
                }

            }

        });

        lblDebito.setText(Conversores.converterDoubleString(cli.getDebito()));
        lblHaver.setText(Conversores.converterDoubleString(cli.getHaver()));
        coluCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        coluProduto.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        coluQuant.setCellValueFactory(new PropertyValueFactory<>("quant"));
        coluValor.setCellValueFactory(new PropertyValueFactory<>("total"));

        ObservableList<ProdutoVendido> obs = FXCollections.observableArrayList(ListaProdutoVendido);
        tabProduto.setItems(obs);

    }

    // limpar dados da tela    
    public void LimparDados() {
        tabProduto.setItems(null);
        lblDebito.setText("00.00");
        lblHaver.setText("00.00");

    }

    @FXML // botao pagar conta
    private void Pagar(ActionEvent event) {
        lblTotal2.setText(lblDebito.getText());
        telaPagar.toFront();

    }

    @FXML //adicionar haver
    private void adicionarHaver(ActionEvent event) {
        LimparDados();
        telaPagar.toBack();
        double haver2 = Double.parseDouble(cxValorPago.getText());
        cli.adicionarHaver(cli.getCodigo(), haver2);
        cxValorPago.setText("");
        cxTrocoCliente.setText("");

    }
    
    
    

    // troco do cliente
    @FXML
    public void Troco() {
        try {
            double x = Double.parseDouble(cxValorPago.getText()) - Double.parseDouble(lblDebito.getText());
            cxTrocoCliente.setText(Conversores.converterDoubleString(x));
            
            if(Double.parseDouble(cxTrocoCliente.getText())<0){
                cxTrocoCliente.setText("Sem Troco");
            }
            
        } catch (Exception e) {
            cxTrocoCliente.setText("");
        }

    }

    @FXML
    private void Cancelar(ActionEvent event) {
        telaPagar.toBack();
        cxTrocoCliente.setText("");
        cxValorPago.setText("");
    }

}
