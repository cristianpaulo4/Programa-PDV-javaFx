package programaHome;

import CadProduto.CadProdutoController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import TelaVendas.*;
import Balanco.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import TelaFonecedor.*;
import TelaNotas.*;
import TelaDeEstoque.*;
import CadCliente.*;
import TelaRepresentante.*;
import TelaDebito.*;
import javafx.scene.layout.VBox;
import TelaVendedor.*;
import Login.*;
import javax.swing.JOptionPane;
import TelaHistorico.*;
import TelaRelatorios.*;
import TelaCaixa.*;
import Objetos.*;


public class Home implements Initializable {
public static boolean teste = true;
    static Stage stage = new Stage();
   

    private static String usuario;
    private static boolean admin;
    private static int codVendedor;
    private static int caixa=1;
    private static boolean altorizado=false;

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String aUsuario) {
        usuario = aUsuario;
    }

    public static boolean getAdmin() {
        return admin;
    }

    public static void setAdmin(boolean aAdmin) {
        admin = aAdmin;
    }

    public static int getCodVendedor() {
        return codVendedor;
    }

    public static void setCodVendedor(int aCodVendedor) {
        codVendedor = aCodVendedor;
    }

    public static int getCaixa() {
        return caixa;
    }

    public static void setCaixa(int aCaixa) {
        caixa = aCaixa;
    }

    public static boolean getAltorizado() {
        return altorizado;
    }

    public static void setAltorizado(boolean aAltorizado) {
        altorizado = aAltorizado;
    }

    @FXML
    private StackPane areaTrabalho;
    @FXML
    private VBox cadPro;
    @FXML
    private VBox esto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {               
        try {         
             telaCaixa();
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // abrir Home
    public void abrirHome() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Programa de Vendas 2.0");
        stage.setMinWidth(900);
        stage.setMinHeight(685);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @FXML // Abrir Tela de notas
    public void Nota() throws IOException {
        areaTrabalho.getChildren().clear();
        CadProdutoController c = new CadProdutoController();
        areaTrabalho.getChildren().add(c.tela());
    }

    // abrir tela balanço
    @FXML
    public void telaBalanco() throws IOException {
        areaTrabalho.getChildren().clear();
        BalancoController b = new BalancoController();
        areaTrabalho.getChildren().add(b.tela());
    }

    @FXML // abrir tela de vendas    
    public void telaVendas() throws IOException {        
        if (altorizado) {
            areaTrabalho.getChildren().clear();
            TelaVendasController t = new TelaVendasController();
            areaTrabalho.getChildren().add(t.Abrir());

        } else {
            JOptionPane.showMessageDialog(null, "Para realizar Vendas você deverá abrir o caixa com um valor inicial!");
        }

    }

    // abrir tela de fonecedor 
    @FXML
    public void telaFornecedor() throws IOException {
        areaTrabalho.getChildren().clear();
        FornecedorController f = new FornecedorController();
        areaTrabalho.getChildren().add(f.Abrir());
    }

    // abrir tela de notas 
    @FXML
    public void telaNotas() throws IOException {
        areaTrabalho.getChildren().clear();
        TelaNotaController f = new TelaNotaController();
        areaTrabalho.getChildren().add(f.Abrir());
    }

    // abrir tela de estoque 
    @FXML
    public void telaEstoque() throws IOException {
        areaTrabalho.getChildren().clear();
        TelaDeEstoqueController f = new TelaDeEstoqueController();
        areaTrabalho.getChildren().add(f.Abrir());
    }

    // abrir tela de cliente
    @FXML
    public void telaCliente() throws IOException {
        areaTrabalho.getChildren().clear();
        TelaClienteController c = new TelaClienteController();
        areaTrabalho.getChildren().add(c.abri());
    }

    // abrir tela debito
    @FXML
    public void telaDebito() throws IOException {
        areaTrabalho.getChildren().clear();
        TelaDebitoController t = new TelaDebitoController();
        areaTrabalho.getChildren().add(t.abrir());

    }

    // abrir tela de representante
    @FXML
    public void telaRepresentante() throws IOException {
        areaTrabalho.getChildren().clear();
        TelaRepresentanteController r = new TelaRepresentanteController();
        areaTrabalho.getChildren().add(r.abrir());
    }

    // abrir tela de vendedores
    @FXML
    public void telaVendedores() throws IOException {
        if (!admin) {
            JOptionPane.showMessageDialog(null, "Você não tem acesso!", "Acesso negado", 0);
            System.out.println(admin);
            areaTrabalho.getChildren().clear();
        } else {
            areaTrabalho.getChildren().clear();
            TelaVendedoresController vendedor = new TelaVendedoresController();
            areaTrabalho.getChildren().add(vendedor.abrirTela());

        }

    }

    // tela historico
    @FXML
    public void telaHistorico() {
        TelaHistoricoController h = new TelaHistoricoController();
        try {
            areaTrabalho.getChildren().clear();
            areaTrabalho.getChildren().add(h.abrirTela());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não Existe Vendas!");
        }

    }

    // tela caixa
    @FXML
    public void telaCaixa() throws IOException {
        TelaCaixaController c = new TelaCaixaController();
        areaTrabalho.getChildren().clear();
        areaTrabalho.getChildren().add(c.abrirTelaCaixa());
    }

    // tela relatorio
    @FXML
    public void telaRelatorio() throws IOException {
        areaTrabalho.getChildren().clear();
        TelaRelatorioController r = new TelaRelatorioController();
        areaTrabalho.getChildren().add(r.abrirTela());
    }

    // fazer login
    @FXML
    public void fazerLogin() throws IOException {
        stage.close();
        LoginController l = new LoginController();
        l.abrirTela();
        setAltorizado(false);
    }

}
