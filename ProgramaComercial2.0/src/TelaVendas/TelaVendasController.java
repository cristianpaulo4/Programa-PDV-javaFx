package TelaVendas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import Objetos.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import programaHome.Home;
import Auxiliares.*;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TelaVendasController implements Initializable {

    // objetos 
    Conversores converter = new Conversores();

    Produto produto = new Produto();
    Cliente cliente = new Cliente();
    Home home = new Home();
    Compra compra = new Compra();
    ProdutoVendido proVenda = new ProdutoVendido();
    ArrayList<Produto> listaProduto = new ArrayList<>();
    ArrayList<ProdutoVendido> listProdutoVendidos = new ArrayList<>();

    // variaveis
    double valorUnit, valorParcial, valorTotal;
    int quant;
    int codigoDoCliente;

    @FXML
    ToggleGroup formaPagamento;
    boolean x;
    int codCliente;

    @FXML
    private AnchorPane TelaVenda;
    @FXML
    private TableView<ProdutoVendido> tabProduto;
    @FXML
    private TableColumn<ProdutoVendido, Integer> coluCodigo;
    @FXML
    private TableColumn<Produto, String> coluDescricao;
    @FXML
    private TableColumn<ProdutoVendido, Double> coluValorUnitario;
    @FXML
    private TableColumn<ProdutoVendido, Integer> coluQuant;
    @FXML
    private TableColumn<ProdutoVendido, Double> coluTotal;
    @FXML
    private TextField cxBuscar;
    @FXML
    private TextField cxQuantidade;

    @FXML
    private Label lblProduto;
    @FXML
    private Label lblVendedor;
    @FXML
    private Label lblTotalCompra;
    @FXML
    private Label lblValorUnitario;
    @FXML
    private Label lblValorParcial;
    @FXML
    private RadioButton aPrazo;
    @FXML
    private RadioButton aVista;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Label cxCaixa;
    @FXML
    private ToggleGroup grupo;
    @FXML
    private VBox telaFinalizar;
    @FXML
    private RadioButton dinheiro;
    @FXML
    private RadioButton cartao;
    @FXML
    private RadioButton cheque;
    @FXML
    private TextField cxValorPago;
    @FXML
    private Label lblTotal2;
    @FXML
    private VBox telaVendaPrazo;
    @FXML
    private TextField cxTrocoCliente;
    @FXML
    private TableView<Cliente> tabCliente;
    @FXML
    private TableColumn<Cliente, String> coluNome;
    @FXML
    private TextField cxBuscarCliente;
    @FXML
    private TextField cxCliente;
    @FXML
    private TextField cxEndereco;
    @FXML
    private TextField cxTelefone;
    @FXML
    private TextField cxDebito;
    @FXML
    private Label lblTotal3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        telaFinalizar.toBack();
        telaVendaPrazo.toBack();

        // numero do caixa
        cxCaixa.setText(Integer.toString(Home.getCaixa()));

        // nome do usuario
        lblVendedor.setText(Home.getUsuario());

        cxQuantidade.setText("1");

        // adicionando produto
        btnAdicionar.setOnAction((event) -> {
            quant = Integer.parseInt(cxQuantidade.getText());
            valorParcial = valorUnit * quant;
            buscarProduto();
            adicionarProduto();
        });

        // calculando valor parcial
        cxQuantidade.setOnKeyReleased((event) -> {
            try {
                quant = Integer.parseInt(cxQuantidade.getText());
                buscarProduto();
                valorParcial = valorUnit * quant;
                valorParcial = converter.converterDoubleDoisDecimais(valorParcial);
                lblValorParcial.setText(converter.converterDoubleString(valorParcial));

            } catch (Exception e) {
                lblValorParcial.setText("00.00");
            }

        });

    }

    // abrir Tela
    public Parent Abrir() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaVendas.fxml"));
        return root;

    }

    // buscar produto
    public void buscarProduto() {
        produto = produto.buscarProdutoCod(Integer.parseInt(cxBuscar.getText()));

        if (!produto.getDescricao().equals("N")) {

            boolean existe = true;

            for (int i = 0; i < listProdutoVendidos.size(); i++) {
                if (listProdutoVendidos.get(i).getCodigo() == Integer.parseInt(cxBuscar.getText())) {
                    JOptionPane.showMessageDialog(null, "Esse produto já está na lista!\ncaso queira adicionar uma maior quantidade exclua e passe o produto novamente!", "Produto já existe", 0);
                    existe = false;
                    lblProduto.setText("");
                    lblValorUnitario.setText("00.00");
                    lblValorParcial.setText("00.00");
                    cxBuscar.setText("");
                    cxQuantidade.setText("1");
                }
            }

            if (existe) {
                lblProduto.setText(produto.getDescricao());
                lblValorUnitario.setText(converter.converterDoubleString(produto.getValorDeVenda()));
                valorUnit = produto.getValorDeVenda();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Produto não existe", "Produto não encontrado", JOptionPane.ERROR_MESSAGE);
            cxBuscar.setText("");
        }

    }

    // adicionando produto
    public void adicionarProduto() {
        ProdutoVendido pro = new ProdutoVendido();

        if (quant > produto.getQuant() && produto.getQuant() > 0) {
            JOptionPane.showMessageDialog(null, "Não existe essa quantidade!\nRestam:" + produto.getQuant(), "Quantidade não permitido", 2);

        } else if (produto.getQuant() <= 0) {
            JOptionPane.showMessageDialog(null, "Produto sem estoque!", "Sem estoque!", 0);
        } else if (quant <= produto.getQuant()) {
            pro.setCodigo(produto.getCodigo());
            pro.setDescricao(produto.getDescricao());
            pro.setValorUnit(produto.getValorDeVenda());
            pro.setQuant(Integer.parseInt(cxQuantidade.getText()));
            pro.setTotal(Double.parseDouble(lblValorParcial.getText()));
            listProdutoVendidos.add(pro);

            valorTotal += valorParcial;
            valorTotal = converter.converterDoubleDoisDecimais(valorTotal);
            lblTotalCompra.setText(converter.converterDoubleString(valorTotal));

            carregarTabela();

        }

        lblProduto.setText("");
        lblValorUnitario.setText("00.00");
        lblValorParcial.setText("00.00");
        cxBuscar.setText("");
        cxQuantidade.setText("1");

    }

    // excluir produto
    @FXML
    public void excluir() {
        int cod = tabProduto.getSelectionModel().getSelectedIndex();
        valorTotal -= tabProduto.getSelectionModel().getSelectedItem().getTotal();
        lblTotalCompra.setText(Double.toString(valorTotal));
        listProdutoVendidos.remove(cod);
        carregarTabela();
    }

    // carregar Tabela
    public void carregarTabela() {

        coluCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        coluDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        coluValorUnitario.setCellValueFactory(new PropertyValueFactory<>("valorUnit"));
        coluQuant.setCellValueFactory(new PropertyValueFactory<>("quant"));
        coluTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        ObservableList<ProdutoVendido> obsPro = FXCollections.observableArrayList(listProdutoVendidos);
        tabProduto.setItems(obsPro);

    }

    // tipo de venda se é a vista ou a prazo
    @FXML
    public void TipoCompra() {
        x = aVista.selectedProperty().get();

        if (x) {
            telaFinalizar.toFront();
            lblTotal2.setText(Conversores.converterDoubleString(valorTotal));
        } else {
            carregarNomesDeCliente();
            lblTotal3.setText(Conversores.converterDoubleString(valorTotal));
            telaFinalizar.toBack();
            telaVendaPrazo.toFront();
        }

    }

    // dar baixar no produto
    @FXML
    public void finalizarCompra() throws IOException {

        if (valorTotal > 0) {

            RadioButton nomeTipo = (RadioButton) formaPagamento.getSelectedToggle();

            x = aVista.selectedProperty().get();

            Date data = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            formatador.format(data);
            String dataAtual = formatador.format(data);

            if (x) {

                compra.setCaixa(Home.getCaixa());
                compra.setTotalCompra(valorTotal);
                compra.setDataVenda(dataAtual);
                compra.setTipo(x);
                compra.setCodVendedor(Home.getCodVendedor());
                compra.setFormaPag(nomeTipo.getText());               
                compra.cadCompraSemCliente(compra);
                

                System.out.println(compra.getCaixa());

                // cadastrando produtos vendidos
                listProdutoVendidos.forEach((t) -> {
                    ProdutoVendido vendido = new ProdutoVendido();
                    Compra c = new Compra();
                    int codCompra = c.ultimaCompra();
                    System.out.println(codCompra);

                    vendido.setCodVenda(codCompra);
                    vendido.setCodigo(t.getCodigo());
                    vendido.setDescricao(t.getDescricao());
                    vendido.setValorUnit(t.getValorUnit());
                    vendido.setQuant(t.getQuant());
                    vendido.setTotal(t.getTotal());
                    vendido.cadProdutoVendido(vendido);

                });

                SubtrairEstoque();

                // compras a prazo
            } else {

                compra.setCaixa(Home.getCaixa());
                compra.setTotalCompra(valorTotal);
                compra.setDataVenda(dataAtual);
                compra.setTipo(x);
                compra.setCodVendedor(Home.getCodVendedor());
                compra.setCodCliente(codigoDoCliente);
                compra.setFormaPag("A receber");
                compra.cadCompra(compra);
                // adicionando o debito para o cliente

                cliente.debitoDoCliente(codigoDoCliente, valorTotal);

                System.out.println(compra.getCaixa());

                // cadastrando produtos vendidos
                listProdutoVendidos.forEach((t) -> {
                    ProdutoVendido vendido = new ProdutoVendido();
                    Compra c = new Compra();
                    int codCompra = c.ultimaCompra();
                    System.out.println(codCompra);

                    vendido.setCodVenda(codCompra);
                    vendido.setCodigo(t.getCodigo());
                    vendido.setDescricao(t.getDescricao());
                    vendido.setValorUnit(t.getValorUnit());
                    vendido.setQuant(t.getQuant());
                    vendido.setTotal(t.getTotal());
                    vendido.cadProdutoVendido(vendido);

                });
                SubtrairEstoque();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Não há produtos!");
        }

    }

    // subritrair produtos do estoque
    public void SubtrairEstoque() {

        listProdutoVendidos.forEach((t) -> {
            Produto pro = new Produto();
            pro = pro.buscarProdutoCod(t.getCodigo());
            pro.BaixaEstoque(t.getCodigo(), pro.getQuant() - t.getQuant());

        });
        telaFinalizar.toBack();
        telaVendaPrazo.toBack();

        listProdutoVendidos.clear();
        valorTotal = 0;
        LimparTela();
        LimparTabela();

    }

    // Cancelar compra
    @FXML
    public void cancelar() {
        LimparTabela();
        LimparTela();
        listProdutoVendidos.clear();
        telaFinalizar.toBack();
        telaVendaPrazo.toBack();
        valorTotal = 0;
        cxValorPago.setText("");
        cxTrocoCliente.setText("");

    }

    // limpar tabela
    public void LimparTabela() {
        tabProduto.setItems(null);
        lblValorUnitario.setText("00.00");
        lblValorParcial.setText("00.00");
        lblTotalCompra.setText("00.00");
        cxBuscar.setText("");
        cxQuantidade.setText("1");
    }

    // limpar tela
    public void LimparTela() {
        listProdutoVendidos.clear();
        tabProduto.setItems(null);
        lblValorUnitario.setText("00.00");
        lblValorParcial.setText("00.00");
        lblTotalCompra.setText("00.00");
        cxBuscar.setText("");
        cxQuantidade.setText("1");
    }

    //===============================================================================================    
    // Troco do cliente
    @FXML
    public void TelaTroco() {
        try {
            double troco = Double.parseDouble(cxValorPago.getText()) - valorTotal;
            cxTrocoCliente.setText(Conversores.converterDoubleString(troco));
            System.out.println("OK");

        } catch (Exception e) {
            System.out.println("Espaço em branco");
        }

    }

    // limpar troco do cliente
    @FXML
    public void telaTroco() {
        cxTrocoCliente.setText("");
    }

    // carregar nomes dos clientes para venda a prazo
    public void carregarNomesDeCliente() {
        Cliente cli = new Cliente();

        coluNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        ObservableList<Cliente> list = FXCollections.observableArrayList(cli.listar());
        tabCliente.setItems(list);

    }

    // buscar nomes dos cliente
    @FXML
    public void buscarNomesDeCliente() {
        Cliente cli = new Cliente();
        coluNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        ObservableList<Cliente> list = FXCollections.observableArrayList(cli.buscarNome(cxBuscarCliente.getText()));
        tabCliente.setItems(list);
        
        cxCliente.setText("");
        cxEndereco.setText("");
        cxTelefone.setText("");
        cxDebito.setText("");
        
        
    }

    // selecionar cliente
    @FXML
    public void selecionarCliente() {
        codigoDoCliente = tabCliente.getSelectionModel().getSelectedItem().getCodigo();
        Cliente cli = new Cliente();
        cli = cli.buscar(codigoDoCliente);

        cxCliente.setText(cli.getNome());
        cxEndereco.setText(cli.getRua() + ", " + cli.getNumero());
        cxTelefone.setText(cli.getTelefone1() + " / " + cli.getTelefone2());
        cxDebito.setText(Conversores.converterDoubleString(cli.getDebito()));

    }

}
