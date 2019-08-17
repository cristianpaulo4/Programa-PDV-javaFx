package Objetos;
import Conexao.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public  class ProdutoVendido {
    Conecta con = new Conecta();
    
    
    private int codigo;
    private String descricao;
    private double valorUnit;
    private int quant;
    private double total;
    private int codVenda;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorUnit() {
        return valorUnit;
    }

    public void setValorUnit(double valorUnit) {
        this.valorUnit = valorUnit;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

  
    
    public int getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(int codVenda) {
        this.codVenda = codVenda;
    }
    
    
    
    
    // cadastrar produto 
    public void cadProdutoVendido(ProdutoVendido pro){
        try {
            PreparedStatement cad = con.conectar().prepareStatement("insert into ProdutoVendido (codigo, descricao, valorUnitario, Quant, totalProduto, Venda_idVenda) values (?,?,?,?,?,?)");
            cad.setInt(1, pro.getCodigo());
            cad.setString(2, pro.getDescricao());
            cad.setDouble(3, pro.getValorUnit());
            cad.setInt(4, pro.getQuant());
            cad.setDouble(5, pro.getTotal());
            cad.setInt(6, pro.getCodVenda());
            cad.execute();
            System.out.println("Produto Vendido!");            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao Vender Produto:\n"+ ex);
        }
        
    }

    
    
    
    // listar produtos vendidos 
    public ArrayList<ProdutoVendido> listarProduto(){
        ArrayList<ProdutoVendido> produto = new ArrayList<>();
        
        try {
            PreparedStatement list = con.conectar().prepareStatement("select *from ProdutoVendido");
            ResultSet res = list.executeQuery();
            
            while (res.next()) {  
                ProdutoVendido pro = new ProdutoVendido();
                pro.setCodigo(res.getInt(2));
                pro.setDescricao(res.getString(3));
                pro.setValorUnit(res.getDouble(4));
                pro.setQuant(res.getInt(5));
                pro.setTotal(res.getDouble(6));
                pro.setCodVenda(res.getInt(7));                
                produto.add(pro);
                
            }
            
            return produto;   
            
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoVendido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
        
    
    // historico de vendas    
    public ArrayList<ProdutoVendido> historico(int codVenda){
        ArrayList<ProdutoVendido> produto = new ArrayList<>();
        try {
            PreparedStatement his = con.conectar().prepareStatement("select * from ProdutoVendido where Venda_idVenda ="+codVenda);
            ResultSet res = his.executeQuery();
            
            while (res.next()) {
                ProdutoVendido pro = new ProdutoVendido();
                pro.setCodigo(res.getInt(2));
                pro.setDescricao(res.getString(3));
                pro.setValorUnit(res.getDouble(4));
                pro.setQuant(res.getInt(5));
                pro.setTotal(res.getDouble(6));
                pro.setCodVenda(res.getInt(7));                
                produto.add(pro);
                
            }            
            return produto;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoVendido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    
    // historico de vendas    
    public ProdutoVendido BuscarProdutoVendidoPeloCodVenda(int codVenda){
        
        try {
            PreparedStatement his = con.conectar().prepareStatement("select * from ProdutoVendido where Venda_idVenda ="+codVenda);
            ResultSet res = his.executeQuery();
            
            while (res.next()) {
                ProdutoVendido pro = new ProdutoVendido();
                pro.setCodigo(res.getInt(2));
                pro.setDescricao(res.getString(3));
                pro.setValorUnit(res.getDouble(4));
                pro.setQuant(res.getInt(5));
                pro.setTotal(res.getDouble(6));
                pro.setCodVenda(res.getInt(7));        
                return pro;            
                
            }            
          
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoVendido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    
    
    
    
    
    
}
