package Objetos;

import Conexao.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Fornecedor extends Endereco{

    Conecta con = new Conecta();
    private String cnpj;
    private String nome;
    private int representante;
    private int codigo;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
      public int getRepresentante() {
        return representante;
    }

    public void setRepresentante(int representante) {
        this.representante = representante;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    

    // cadastrar fornecedor
    public void CadastrarFornecedor(Fornecedor fornecedor){
        try {
            PreparedStatement cad = con.conectar().prepareStatement("insert into Fornecedor (CNPJ,nome, cidade, estado, bairro, rua, numero, complemento, email, telefone1, telefone2, observacao, Representante_idRepresentante) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");

            cad.setString(1, fornecedor.getCnpj());
            cad.setString(2, fornecedor.getNome());
            cad.setString(3, fornecedor.getCidade());
            cad.setString(4, fornecedor.getEstado());
            cad.setString(5, fornecedor.getBairro());
            cad.setString(6, fornecedor.getRua());
            cad.setInt(7, fornecedor.getNumero());
            cad.setString(8, fornecedor.getComplemento());
            cad.setString(9, fornecedor.getEmail());
            cad.setString(10, fornecedor.getTelefone1());
            cad.setString(11, fornecedor.getTelefone2());
            cad.setString(12, fornecedor.getObservacao());
            cad.setInt(13, fornecedor.getRepresentante());
            cad.execute();

            JOptionPane.showMessageDialog(null, "Salvo");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar:\n" + ex);
        }

    }
    
    
    
    
    // alterar fornecedor
    public void AlterarFornecedor(Fornecedor fornecedor, int cod){
        try {
            PreparedStatement cad = con.conectar().prepareStatement("update Fornecedor set CNPJ=?,nome =?, cidade=?, estado=?, bairro=?, rua=?, numero=?, complemento=?, email=?, telefone1=?, telefone2=?, observacao=?, Representante_idRepresentante = ? where idFornecedor ="+cod);
            //"values (?,?,?,?,?,?,?,?,?,?,?,?,?)"

            cad.setString(1, fornecedor.getCnpj());
            cad.setString(2, fornecedor.getNome());
            cad.setString(3, fornecedor.getCidade());
            cad.setString(4, fornecedor.getEstado());
            cad.setString(5, fornecedor.getBairro());
            cad.setString(6, fornecedor.getRua());
            cad.setInt(7, fornecedor.getNumero());
            cad.setString(8, fornecedor.getComplemento());
            cad.setString(9, fornecedor.getEmail());
            cad.setString(10, fornecedor.getTelefone1());
            cad.setString(11, fornecedor.getTelefone2());
            cad.setString(12, fornecedor.getObservacao());
            cad.setInt(13, fornecedor.getRepresentante());
            cad.execute();

            JOptionPane.showMessageDialog(null, "Alterado com sucesso!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar:\n" + ex);
        }

    }

    
    
    
    
    // listar fornecedor
    public ArrayList<Fornecedor> listarFornecedor(){
        ArrayList<Fornecedor> fornecedor = new ArrayList<>();

        try {
            PreparedStatement listar = con.conectar().prepareStatement("select * from Fornecedor");
            ResultSet res = listar.executeQuery();

            while (res.next()) {
                Fornecedor forne = new Fornecedor();
                forne.setCodigo(res.getInt(1));
                forne.setCnpj(res.getString(2));
                forne.setNome(res.getString(3));
                forne.setCidade(res.getString(4));
                forne.setEstado(res.getString(5));
                forne.setBairro(res.getString(6));
                forne.setRua(res.getString(7));
                forne.setNumero(res.getInt(8));
                forne.setComplemento(res.getString(9));
                forne.setEmail(res.getString(10));
                forne.setTelefone1(res.getString(11));
                forne.setTelefone2(res.getString(12));
                forne.setObservacao(res.getString(13));
                forne.setRepresentante(res.getInt(14));

                fornecedor.add(forne);
            }

            return fornecedor;
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Erro ao Listar Fornecedor:"+ ex);
        }
        return null;

    }
    
    
    
    
    
    // Buscar fornecedor pelo nome
    public Fornecedor buscarFornecedor(String nome){
        
        try {
            PreparedStatement listar = con.conectar().prepareStatement("select * from Fornecedor where nome ='"+nome+"'");
            ResultSet res = listar.executeQuery();

            while (res.next()) {
                Fornecedor forne = new Fornecedor();
                forne.setCodigo(res.getInt(1));
                forne.setCnpj(res.getString(2));
                forne.setNome(res.getString(3));
                forne.setCidade(res.getString(4));
                forne.setEstado(res.getString(5));
                forne.setBairro(res.getString(6));
                forne.setRua(res.getString(7));
                forne.setNumero(res.getInt(8));
                forne.setComplemento(res.getString(9));
                forne.setEmail(res.getString(10));
                forne.setTelefone1(res.getString(11));
                forne.setTelefone2(res.getString(12));
                forne.setObservacao(res.getString(13));
                forne.setRepresentante(res.getInt(14)); 
                return forne;
            }
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Erro ao Buscar Fornecedor:"+ ex);
        }
        return null;
    }
    
    
    
    
    
    // Buscar fornecedor pelo codigo
    public Fornecedor buscarFornecedorCodigo(int codigo) {        
        try {
            PreparedStatement listar = con.conectar().prepareStatement("select * from Fornecedor where idFornecedor ="+codigo);
            ResultSet res = listar.executeQuery();

            while (res.next()) {
                Fornecedor forne = new Fornecedor();
                forne.setCodigo(res.getInt(1));
                forne.setCnpj(res.getString(2));
                forne.setNome(res.getString(3));
                forne.setCidade(res.getString(4));
                forne.setEstado(res.getString(5));
                forne.setBairro(res.getString(6));
                forne.setRua(res.getString(7));
                forne.setNumero(res.getInt(8));
                forne.setComplemento(res.getString(9));
                forne.setEmail(res.getString(10));
                forne.setTelefone1(res.getString(11));
                forne.setTelefone2(res.getString(12));
                forne.setObservacao(res.getString(13));
                forne.setRepresentante(res.getInt(14)); 
                return forne;
            }
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Erro ao Buscar Fornecedor:"+ ex);
        }
        return null;
    }
    
    
    
    // Buscar fornecedor pelo Representante
    public Fornecedor buscarFornecedorRepresentante(int codigo) {        
        try {
            PreparedStatement listar = con.conectar().prepareStatement("select * from Fornecedor where Representante_idRepresentante ="+codigo);
            ResultSet res = listar.executeQuery();

            while (res.next()) {
                Fornecedor forne = new Fornecedor();
                forne.setCodigo(res.getInt(1));
                forne.setCnpj(res.getString(2));
                forne.setNome(res.getString(3));
                forne.setCidade(res.getString(4));
                forne.setEstado(res.getString(5));
                forne.setBairro(res.getString(6));
                forne.setRua(res.getString(7));
                forne.setNumero(res.getInt(8));
                forne.setComplemento(res.getString(9));
                forne.setEmail(res.getString(10));
                forne.setTelefone1(res.getString(11));
                forne.setTelefone2(res.getString(12));
                forne.setObservacao(res.getString(13));
                forne.setRepresentante(res.getInt(14)); 
                return forne;
            }
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Erro ao Buscar Fornecedor:"+ ex);
        }
        return null;
    }
    
    
    
    
    // excluir fornecedor    
    public void excluir(int cod){
        try {
            PreparedStatement exclu = con.conectar().prepareStatement("delete from Fornecedor where idFornecedor ="+cod);
            exclu.execute();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir Fornecedor:\nSó poderá ser excluido quanto não tiver nenhuma nota cadastrado com esse fornecedor.\n Caso queira prosseguir Exclua primerio os produtos da nota, depois a nota e por fim o Fornecedor.");
        }

    }
    
    

  
    @Override
    public String toString() {       
       return nome;
    }
    
    


}
