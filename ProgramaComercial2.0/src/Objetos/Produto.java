package Objetos;

import Conexao.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Produto {

    Conecta con = new Conecta();

    private int numNota;
    private int codigo;
    private String descricao;
    private int marca;
    private int volume;
    private String UnidMedida;
    private int categoria;
    private String validade;
    private int quantMinimo;
    private int quant;
    private double valorDeCusto;
    private double valorDeVenda;
    private int fornecedor;
    private String observacao;

    public int getNumNota() {
        return numNota;
    }

    public void setNumNota(int numNota) {
        this.numNota = numNota;
    }

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

    public String getUnidMedida() {
        return UnidMedida;
    }

    public void setUnidMedida(String UnidMedida) {
        this.UnidMedida = UnidMedida;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public int getQuantMinimo() {
        return quantMinimo;
    }

    public void setQuantMinimo(int quantMinimo) {
        this.quantMinimo = quantMinimo;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public double getValorDeCusto() {
        return valorDeCusto;
    }

    public void setValorDeCusto(double valorDeCusto) {
        this.valorDeCusto = valorDeCusto;
    }

    public double getValorDeVenda() {
        return valorDeVenda;
    }

    public void setValorDeVenda(double valorDeVenda) {
        this.valorDeVenda = valorDeVenda;
    }

    public int getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(int fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    // cadastrar produto
    public void cadProduto(Produto pro) {
        try {
            PreparedStatement cad = con.conectar().prepareStatement("insert into Produto (idProduto, descricao,volume, unidadeMedida, validade, quantMinimo, quantidade, valorCusto, valorVenda, observacao, Categoria_idCategoria, Marca_idMarca, Nota_idNota,Fornecedor_idFornecedor) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            cad.setInt(1, pro.getCodigo());
            cad.setString(2, pro.getDescricao());
            cad.setInt(3, pro.getVolume());
            cad.setString(4, pro.getUnidMedida());
            cad.setString(5, pro.getValidade());
            cad.setInt(6, pro.getQuantMinimo());
            cad.setInt(7, pro.getQuant());
            cad.setDouble(8, pro.getValorDeCusto());
            cad.setDouble(9, pro.getValorDeVenda());
            cad.setString(10, pro.getObservacao());
            cad.setInt(11, pro.getCategoria());
            cad.setInt(12, pro.getMarca());
            cad.setInt(13, pro.getNumNota());
            cad.setInt(14, pro.getFornecedor());
            
            cad.execute();

            System.out.println("Produto salvo com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar Produto:\n" + ex);
        }

    }

    // listar produto por nota 
    public ArrayList<Produto> listarProdutoComNota(int num) {
        ArrayList<Produto> pro = new ArrayList<>();

        try {
            PreparedStatement list = con.conectar().prepareStatement("select * from Produto where Nota_idNota = " + num);
            ResultSet res = list.executeQuery();

            while (res.next()) {
                Produto produto = new Produto();
                produto.setCodigo(res.getInt(1));
                produto.setDescricao(res.getString(2));
                produto.setVolume(res.getInt(3));
                produto.setUnidMedida(res.getString(4));
                produto.setValidade(res.getString(5));
                produto.setQuantMinimo(res.getInt(6));
                produto.setQuant(res.getInt(7));
                produto.setValorDeCusto(res.getDouble(8));
                produto.setValorDeVenda(res.getDouble(9));
                produto.setObservacao(res.getString(10));
                produto.setCategoria(res.getInt(11));
                produto.setNumNota(res.getInt(12));
                produto.setFornecedor(res.getInt(13));
                produto.setMarca(res.getInt(14));
                pro.add(produto);

            }

            return pro;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar produtos");
        }
        return null;
    }
    
    
    
    
    // listar produto por nome 
    public ArrayList<Produto> listarProdutoNome(String nome) {
        ArrayList<Produto> pro = new ArrayList<>();

        try {
            PreparedStatement list = con.conectar().prepareStatement("select * from Produto where descricao like'" +nome+"%'");
            ResultSet res = list.executeQuery();

            while (res.next()) {
                Produto produto = new Produto();
                produto.setCodigo(res.getInt(1));
                produto.setDescricao(res.getString(2));
                produto.setVolume(res.getInt(3));
                produto.setUnidMedida(res.getString(4));
                produto.setValidade(res.getString(5));
                produto.setQuantMinimo(res.getInt(6));
                produto.setQuant(res.getInt(7));
                produto.setValorDeCusto(res.getDouble(8));
                produto.setValorDeVenda(res.getDouble(9));
                produto.setObservacao(res.getString(10));
                produto.setCategoria(res.getInt(11));
                produto.setNumNota(res.getInt(12));
                produto.setFornecedor(res.getInt(13));
                produto.setMarca(res.getInt(14));
                pro.add(produto);

            }

            return pro;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar produtos");
        }
        return null;
    }

    
    
    
    // buscar produto
    public ArrayList<Produto> buscarProduto(String cod) {
        ArrayList<Produto> lis = new ArrayList<>();       

        try {
            PreparedStatement list = con.conectar().prepareStatement("select * from Produto where idProduto like '" + cod+"%'");
            ResultSet res = list.executeQuery();

            while (res.next()) {
                Produto produto = new Produto();
                produto.setCodigo(res.getInt(1));
                produto.setDescricao(res.getString(2));
                produto.setVolume(res.getInt(3));
                produto.setUnidMedida(res.getString(4));
                produto.setValidade(res.getString(5));
                produto.setQuantMinimo(res.getInt(6));
                produto.setQuant(res.getInt(7));
                produto.setValorDeCusto(res.getDouble(8));
                produto.setValorDeVenda(res.getDouble(9));
                produto.setObservacao(res.getString(10));
                produto.setCategoria(res.getInt(11));
                produto.setNumNota(res.getInt(12));
                produto.setFornecedor(res.getInt(13));
                produto.setMarca(res.getInt(14));                
                lis.add(produto);
            }
            
            return lis;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Buscar produtos");
        }
        return null;
    }
    
    
    
    
    // buscar produto pelo codigo
    public Produto buscarProdutoCod(int cod) {        
        try {
            PreparedStatement list = con.conectar().prepareStatement("select * from Produto where idProduto =" + cod);
            ResultSet res = list.executeQuery();

            while (res.next()) {
                Produto produto = new Produto();
                produto.setCodigo(res.getInt(1));
                produto.setDescricao(res.getString(2));
                produto.setVolume(res.getInt(3));
                produto.setUnidMedida(res.getString(4));
                produto.setValidade(res.getString(5));
                produto.setQuantMinimo(res.getInt(6));
                produto.setQuant(res.getInt(7));
                produto.setValorDeCusto(res.getDouble(8));
                produto.setValorDeVenda(res.getDouble(9));
                produto.setObservacao(res.getString(10));
                produto.setCategoria(res.getInt(11));
                produto.setMarca(res.getInt(12));    
                produto.setNumNota(res.getInt(13));
                produto.setFornecedor(res.getInt(14));
                            
                return produto;
            }
            
            this.setDescricao("N");
            return this;            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Buscar produtos");
        }
        return null;
    }

    
    
    
    
    // dar baixar no estoque
    public void BaixaEstoque(int cod, int quant){
        try {
            PreparedStatement sub = con.conectar().prepareStatement("update Produto set quantidade= '"+quant+"'where idProduto ="+cod);
            sub.execute();
            
            System.out.println("Subtraido com sucesso!");
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao subtrair produto:\n"+ex);
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // listar produto 
    public ArrayList<Produto> listarProduto() {
        ArrayList<Produto> pro = new ArrayList<>();

        try {
            PreparedStatement list = con.conectar().prepareStatement("select * from Produto");
            ResultSet res = list.executeQuery();

            while (res.next()) {
                Produto produto = new Produto();
                produto.setCodigo(res.getInt(1));
                produto.setDescricao(res.getString(2));
                produto.setVolume(res.getInt(3));
                produto.setUnidMedida(res.getString(4));
                produto.setValidade(res.getString(5));
                produto.setQuantMinimo(res.getInt(6));
                produto.setQuant(res.getInt(7));
                produto.setValorDeCusto(res.getDouble(8));
                produto.setValorDeVenda(res.getDouble(9));
                produto.setObservacao(res.getString(10));
                produto.setCategoria(res.getInt(11));
                produto.setMarca(res.getInt(12));
                produto.setNumNota(res.getInt(13));
                produto.setFornecedor(res.getInt(14));
               
                pro.add(produto);

            }

            return pro;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Carregar produtos");
        }
        return null;
    }

    // excluir
    public void Excluir(int cod) {
        int op = JOptionPane.showConfirmDialog(null, "Deseja excluir esse produto?", "Excluir", JOptionPane.YES_NO_OPTION, 3);
        PreparedStatement ex;

        if (op == 0) {
            try {
                ex = con.conectar().prepareStatement("delete from Produto where idProduto = " + cod);
                ex.execute();
                JOptionPane.showMessageDialog(null, "Excluido com sucesso!", "Excluido", 2);

            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar excluir o produto!", "Erro", 0);
            }

        }

    }

}
