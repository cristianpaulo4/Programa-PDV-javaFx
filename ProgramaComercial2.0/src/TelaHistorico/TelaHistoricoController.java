package TelaHistorico;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import Objetos.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import Auxiliares.*;

public class TelaHistoricoController implements Initializable {

    Compra compra = new Compra();
    ProdutoVendido produto = new ProdutoVendido();
    Conversores converter= new Conversores();
    int proximo = 0, anterior = 0;

    // Lista de dadas
    ArrayList<Compra> listaData = new ArrayList<>();
    ArrayList<ProdutoVendido> listaProduto = new ArrayList<>();
    ArrayList<Integer> listaCodigoCompra = new ArrayList<>();
    ArrayList<Compra> listaCompra = new ArrayList<>();

    @FXML
    private TableView<Compra> tabCompra;
    @FXML
    private TableColumn<String, String> coluCompra;
    @FXML
    private DatePicker cxData;
    @FXML
    private Label lblNomeVendedor;
    @FXML
    private Label lblCliente;
    @FXML
    private Label lblCaixa;
    @FXML
    private Label lblData;
    @FXML
    private TableView<ProdutoVendido> tabProdutos;
    @FXML
    private TableColumn<ProdutoVendido, Integer> coluCodigo;
    @FXML
    private TableColumn<ProdutoVendido, String> coluDescricao;
    @FXML
    private TableColumn<ProdutoVendido, Double> coluValorUnit;
    @FXML
    private TableColumn<ProdutoVendido, Integer> coluQuant;
    @FXML
    private TableColumn<ProdutoVendido, Double> coluTotal;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblTipoVenda;
    @FXML
    private Label lblSequenciaCompra;
    @FXML
    private Label lblCodigoCompra;
    @FXML
    private Label lblQuantDia;
    @FXML
    private Button btnProximo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listarDatas();

        // buscando produto pelo codigo de venda
        tabCompra.setOnMouseClicked((event) -> {
            lblData.setText(tabCompra.getSelectionModel().getSelectedItem().getDataVenda());
            listarCodigoVenda(lblData.getText());
            proximo = 0;
            anterior = 0;
            proximaCompra();
        });

    }

    // abrir tela
    public Parent abrirTela() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaHistorico.fxml"));
        return root;
    }

    // listar datas
    public void listarDatas() {

        ArrayList<Compra> listaCompra = compra.listar();
        compra = listaCompra.get(0);
        listaData.add(compra);

        String data = compra.getDataVenda();

        // lista compras
        for (int i = 0; i < listaCompra.size(); i++) {

            if (!data.equals(listaCompra.get(i).getDataVenda())) {
                listaData.add(listaCompra.get(i));
                data = listaCompra.get(i).getDataVenda();
            }

        }
        coluCompra.setCellValueFactory(new PropertyValueFactory<>("dataVenda"));
        ObservableList<Compra> obs = FXCollections.observableArrayList(listaData);
        tabCompra.setItems(obs);
    }

    @FXML // proxima compra do dia
    public void proximaCompra() {
        try {
            if (proximo <= listaCodigoCompra.size()) {
                int codVenda = listaCodigoCompra.get(proximo);
                listarProdutos(codVenda);
                lblSequenciaCompra.setText(Integer.toString(proximo + 1));
                proximo++;
                anterior = proximo - 1;
            }
        } catch (Exception e) {
            btnProximo.setDisable(false);
        }

    }

    @FXML // anterior compra do dia
    public void anteriorCompra() {
        if (anterior > 0) {
            anterior--;
            proximo = anterior + 1;
            lblSequenciaCompra.setText(Integer.toString(anterior + 1));
            int codVenda = listaCodigoCompra.get(anterior);
            listarProdutos(codVenda);
        }

    }

    // listando produto por data
    public void listarProdutos(int codVenda) {
        Compra c = new Compra().listarCompraCodigo(codVenda);
        Vendedor vem = new Vendedor().Buscar(c.getCodVendedor());
        Cliente cli = new Cliente().buscar(c.getCodCliente());
        listaProduto = produto.historico(codVenda);

        //lblTotal.setText(Double.toString(c.getTotalCompra()));
        lblTotal.setText(converter.converterDoubleString(c.getTotalCompra()));
        
        lblCaixa.setText(Integer.toString(c.getCaixa()));
        lblNomeVendedor.setText(vem.getNome());
        lblCodigoCompra.setText(Integer.toString(c.getCodVenda()));
        lblCliente.setText(cli.getNome());

        if (c.getTipo()) {
            lblTipoVenda.setText("À Vista");
        } else {
            lblTipoVenda.setText("À Prazo");
        }

        coluCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        coluDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        coluValorUnit.setCellValueFactory(new PropertyValueFactory<>("valorUnit"));
        coluQuant.setCellValueFactory(new PropertyValueFactory<>("quant"));
        coluTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        ObservableList<ProdutoVendido> obs = FXCollections.observableArrayList(listaProduto);
        tabProdutos.setItems(obs);

    }

    // todos os codigos das vendas
    public void listarCodigoVenda(String data) {
        listaCodigoCompra.clear();
        Compra com = new Compra();
        listaCompra = com.listarCompraData(data);

        for (int i = 0; i < listaCompra.size(); i++) {
            listaCodigoCompra.add(listaCompra.get(i).getCodVenda());
        }

        lblQuantDia.setText(Integer.toString(listaCompra.size()));
    }

}
