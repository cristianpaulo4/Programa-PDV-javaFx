package TelaCaixa;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import Auxiliares.*;
import java.awt.Color;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import programaHome.*;
import Objetos.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.control.TextField;
import Auxiliares.*;

public class TelaCaixaController implements Initializable {

    Caixa caixa = new Caixa();
    static boolean controleDeOpcao = true;
    ArrayList<ProdutoVendido> listaProduto = new ArrayList<>();
    ArrayList<Compra> listaCompra = new ArrayList<>();

    ProdutoVendido pro = new ProdutoVendido();
    double dinheiro, cartao, cheque;
    double caixaAtual, caixaSaida, lucro;

    @FXML
    private Label lblCaixaInicial;
    @FXML
    private Label lblCaixaAtual;
    @FXML
    private Label lblbCaixaSaida;
    @FXML
    private Button btnCaixa;
    @FXML
    private Label lblSituacao;
    @FXML
    private TextField cxVendedor;
    @FXML
    private TextField cxData;
    @FXML
    private TextField cxCaixaTerminal;

    @FXML
    private Label lblDinheiro;

    @FXML
    private Label lblCartao;

    @FXML
    private Label lblCheque;
    @FXML
    private Label lblLucro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            buscarCaixa();
        } catch (Exception e) {
            System.out.println("erro");
        }

    }

    // abrir tela caixa
    public Parent abrirTelaCaixa() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaCaixa.fxml"));
        return root;
    }

    // retirar do caixa
    @FXML
    public void retirarDoCaixa() {
        String x = JOptionPane.showInputDialog(null, "Digite o Valor", "Retirar do Caixa", 1);

        try {
            double valor = Double.parseDouble(x);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não é um numero");
        }
    }

    // abrir o caixa
    @FXML
    public void abrirCaixa() {
        Caixa caixa2 = new Caixa();

       
            // se o caixa ainda não for aberto       
            if (btnCaixa.getText().equals("Abrir Caixa") && !caixa2.verificar()) {
                try {
                    Caixa caixa = new Caixa();
                    String x = JOptionPane.showInputDialog(null, "Digite o Valor", "Abrir Caixa", 1);

                    double valor = Double.parseDouble(x);
                    lblCaixaInicial.setText(Conversores.converterDoubleString(valor));
                    btnCaixa.setText("Fechar Caixa");
                    lblSituacao.setText("Aberto");
                    Home.setAltorizado(true);

                    Date data = new Date();
                    SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
                    formatador.format(data);
                    String dataAtual = formatador.format(data);

                    System.out.println(dataAtual);

                    caixa.setValorInicial(valor);
                    caixa.setData(dataAtual);
                    caixa.setCaixaTerminal(Home.getCaixa());
                    caixa.setCodVendedor(Home.getCodVendedor());
                    caixa.setValorAtual(0);
                    caixa.setValorSaida(0);
                    caixa.setValorLucro(0);
                    caixa.setSituacao(true);
                    caixa.AbrirCaixa(caixa);                   
                    listarProdutos();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Não é um numero");
                }

            } else {
                // fechamento do caixa
                if (btnCaixa.getText().equals("Fechar Caixa")) {
                    fecharCaixa();

                    // reabrir caixa
                } else {
                    System.out.println("OK");
                    caixa.situacaoCaixa(true);
                    //listaCompra.clear();
                    buscarCaixa();

                }

            }

     

    }

    // fechar caixa
    public void fecharCaixa() {
        int x = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja fechar o caixa?", "Fechar Caixa", 0, 3);
        if (x == 0) {
            Caixa caixa2 = new Caixa();
            caixa2.situacaoCaixa(false);

            caixa2.setValorAtual(Conversores.converterDoubleDoisDecimais(Double.parseDouble(lblCaixaAtual.getText())));
            caixa2.setValorSaida(Conversores.converterDoubleDoisDecimais(Double.parseDouble(lblbCaixaSaida.getText())));
            caixa2.setValorLucro(Conversores.converterDoubleDoisDecimais(Double.parseDouble(lblLucro.getText())));

            caixa2.AtualizarDados(caixa2, Home.getCodVendedor());

            lblCaixaInicial.setText(Conversores.converterDoubleString(00.00));
            lblCaixaAtual.setText(Conversores.converterDoubleString(00.00));
            lblbCaixaSaida.setText(Conversores.converterDoubleString(00.00));
            lblDinheiro.setText(Conversores.converterDoubleString(00.00));
            lblCartao.setText(Conversores.converterDoubleString(00.00));
            lblCheque.setText(Conversores.converterDoubleString(00.00));
            lblLucro.setText("00.00");
            lblSituacao.setText("Fechado");
            btnCaixa.setText("Abrir Caixa");
            Home.setAltorizado(false);

            listaCompra.clear();
            listaProduto.clear();
            buscarCaixa();

        } else {
            buscarCaixa();
        }
    }

    // buscar por data atual
    public void buscarCaixa() {
        Caixa c = new Caixa();

        c = c.BuscarCaixaPorData();
        Vendedor vendedor = new Vendedor();
        vendedor = vendedor.Buscar(c.getCodVendedor());
        
      
             if (c.getSituacao()) {

            lblSituacao.setText("Aberto");
            btnCaixa.setText("Fechar Caixa");

            cxVendedor.setText(vendedor.getNome());
            cxData.setText(c.getData());
            cxCaixaTerminal.setText(Integer.toString(c.getCaixaTerminal()));

            lblCaixaInicial.setText(Conversores.converterDoubleString(c.getValorInicial()));
            lblCaixaAtual.setText(Conversores.converterDoubleString(c.getValorAtual()));
            lblbCaixaSaida.setText(Conversores.converterDoubleString(c.getValorSaida()));

            listarProdutos();
            Home.setAltorizado(true);

        } else {
            lblSituacao.setText("Fechado");
            btnCaixa.setText("Abrir Caixa");
            Home.setAltorizado(false);
        }
           
            
        

       
    }

    // carregar produtos vendidos 
    public void listarProdutos() {
        dinheiro = 0;
        cartao = 0;
        cheque = 0;
        caixaAtual = 0;

        try {
            Date data = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            formatador.format(data);
            String dataAtual = formatador.format(data);

            Compra compra = new Compra();
            listaCompra = compra.listarCompraData(dataAtual);

            // lista de compras com a data de hoje
            listaCompra.forEach((t) -> {
                if (t.getQuitada() && t.getCodVendedor() == Home.getCodVendedor()) {
                    ProdutoVendido proVend = new ProdutoVendido();
                    proVend = proVend.BuscarProdutoVendidoPeloCodVenda(t.getCodVenda());
                    listaProduto.add(proVend);

                    if (t.getFormaPag().equals("Dinheiro")) {
                        dinheiro += t.getTotalCompra();
                    } else if (t.getFormaPag().equals("Cartão")) {
                        cartao += t.getTotalCompra();
                    } else if (t.getFormaPag().equals("Cheque")) {
                        cheque += t.getTotalCompra();
                    }

                }

            });

            // lista de produto separados por data
            listaProduto.forEach((t) -> {
                caixaAtual += t.getTotal();
            });

            caixaAtual += Double.parseDouble(lblCaixaInicial.getText());

            lblCaixaAtual.setText(Conversores.converterDoubleString(caixaAtual));
            lblDinheiro.setText(Conversores.converterDoubleString(dinheiro));
            lblCartao.setText(Conversores.converterDoubleString(cartao));
            lblCheque.setText(Conversores.converterDoubleString(cheque));

            lucro = caixaAtual - Double.parseDouble(lblCaixaInicial.getText());
            lblLucro.setText(Conversores.converterDoubleString(lucro));

        } catch (Exception e) {
            System.out.println("Erro");
        }

    }

}
