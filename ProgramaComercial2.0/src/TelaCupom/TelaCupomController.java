
package TelaCupom;

import Auxiliares.Conversores;
import Objetos.Cliente;
import Objetos.Compra;
import Objetos.Produto;
import Objetos.ProdutoVendido;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import TelaVendas.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javax.swing.JOptionPane;
import programaHome.Home;

public class TelaCupomController implements Initializable {  
    static Stage stage;
    
    // listas
    public ArrayList<ProdutoVendido> listProdutoVendidos = new ArrayList<>();  
    static private boolean tipoVenda;
    static Compra compra = new Compra();
   

    
    
    
    

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

 

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
        
        
    }    
    
    
    // abrir tela
    public void abrirTela() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("TelaCupom.fxml"));
        Scene scene = new Scene(root);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.show();  
        stage = stage1;
        stage1.setResizable(false);
        
    }
    
      
    // finalizar compra
    @FXML
    public void finalizar() throws IOException{
        stage.close();
        
        if(dinheiro.selectedProperty().getValue()){         
           compra.setFormaPag("Dinheiro");
           JOptionPane.showMessageDialog(null, "1");
           //compra();
           //finalizarCompra();
           lista();
           
           
        }
        if(cartao.selectedProperty().getValue()){
            compra.setFormaPag("Cartão");
        }
        if(cheque.selectedProperty().getValue()){
            compra.setFormaPag("Cheque");
                      
        }
       
        
    }
    
    
    public void compra(){
        System.out.println(compra.getCaixa());
        System.out.println(compra.getCodCliente());
        System.out.println(compra.getCodVenda());
        System.out.println(compra.getTotalCompra());
        System.out.println(compra.getFormaPag());
        
    }
    
    public void lista(){
        System.out.println(listProdutoVendidos.size());
        listProdutoVendidos.forEach((t) -> {
            System.out.println(t.getDescricao());            
        });
        
        
    }
    
  
    
    
    
    // açao do botão finalizar
    public void finalizarCompra(){    
        JOptionPane.showMessageDialog(null, "2");
        Compra c = new Compra();
        c.cadCompra(compra);
        
        
        System.out.println(compra.getCodVendedor());
        System.out.println(compra.getCaixa());
        System.out.println(compra.getFormaPag());
               
        
        JOptionPane.showMessageDialog(null, "2.1");
                
        // cadastrando produtos vendidos
                listProdutoVendidos.forEach((t) -> {
                    ProdutoVendido vendido = new ProdutoVendido();                    
                    int codCompra = c.ultimaCompra();
                    System.out.println(codCompra);                    
                    vendido.setCodVenda(codCompra);
                    vendido.setCodigo(t.getCodigo());
                    vendido.setDescricao(t.getDescricao());
                    vendido.setValorUnit(t.getValorUnit());
                    vendido.setQuant(t.getQuant());
                    vendido.setTotal(t.getTotal());
                    vendido.cadProdutoVendido(vendido);                     
                    SubtrairEstoque();           
                    
                }); 
        
        
    }
    
    
    // subritrair produtos do estoque
    public void SubtrairEstoque() {
        JOptionPane.showMessageDialog(null, "3");        
        listProdutoVendidos.forEach((t) -> {
            Produto pro = new Produto();
            pro = pro.buscarProdutoCod(t.getCodigo());
            pro.BaixaEstoque(t.getCodigo(), pro.getQuant() - t.getQuant());
        });

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    // metodos get e set
    public ArrayList<ProdutoVendido> getListProdutoVendidos() {
        return listProdutoVendidos;
    }

    public void setListProdutoVendidos(ArrayList<ProdutoVendido> listProdutoVendidos) {
        this.listProdutoVendidos = listProdutoVendidos;
    }

 
    public boolean isTipoVenda() {
        return tipoVenda;
    }

    public void setTipoVenda(boolean tipoVenda) {
        this.tipoVenda = tipoVenda;
    }

    public Compra getVenda() {
        return compra;
    }

    public void setVenda(Compra compra) {
        this.compra = compra;
    }
        
    
    
    
    
    
    
    
    
}
