
package Objetos;
import Conexao.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Representante {
   private Conecta con = new Conecta();
   
   private int codigo;
   private String nome;   
   private String telefone1;
   private String email;
   private String telefone2;

    public Conecta getCon() {
        return con;
    }

    public void setCon(Conecta con) {
        this.con = con;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }
   
   
      public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    
    // cadastrar representante
    public void CadRepresentante(Representante re){
       try {
           PreparedStatement cad = con.conectar().prepareStatement("insert into Representante (nome, email, telefone1, telefone2) values (?,?,?,?)");
           cad.setString(1, re.getNome());
           cad.setString(2, re.getEmail());
           cad.setString(3, re.getTelefone1());
           cad.setString(4, re.getTelefone2());
           cad.execute();           
           System.out.println("Representate Salvo");
           
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro ao salvar:\n"+ex);
       }
        
    }
    
    
    // alterar representante
    public void alterarRepresentante(Representante re, int cod){
       try {
           PreparedStatement cad = con.conectar().prepareStatement("update Representante set nome=?, email=?, telefone1=?, telefone2=? where idRepresentante ="+cod);
           cad.setString(1, re.getNome());
           cad.setString(2, re.getEmail());
           cad.setString(3, re.getTelefone1());
           cad.setString(4, re.getTelefone2());
           cad.execute();           
           System.out.println("Representate Alterado");
           
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro ao Alterar:\n"+ex);
       }        
    }
    
    
    
    // buscar representante
    public Representante BuscarRepresentante(String nome){
        Representante rep = new Representante();
        
       try {
           PreparedStatement list = con.conectar().prepareStatement("select * from Representante where nome ='"+nome+"'");
           ResultSet res = list.executeQuery(); 
           
           while (res.next()) {
               rep.setCodigo(res.getInt(1));
               rep.setNome(res.getString(2));
               rep.setEmail(res.getString(3));
               rep.setTelefone1(res.getString(4));
               rep.setTelefone2(res.getString(5));
               return rep;               
           }
           
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro ao buscar Representante:\n"+ex);
       }
       return null;                
    }
    
    
    // buscar representante pelo codigo
    public Representante BuscarRepresentantePeloCodigo(int cod){
        Representante rep = new Representante();
        
       try {
           PreparedStatement list = con.conectar().prepareStatement("select * from Representante where idRepresentante ="+cod);
           ResultSet res = list.executeQuery(); 
           
           while (res.next()) {
               rep.setCodigo(res.getInt(1));
               rep.setNome(res.getString(2));
               rep.setEmail(res.getString(3));
               rep.setTelefone1(res.getString(4));
               rep.setTelefone2(res.getString(5));
               return rep;               
           }
           
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro ao buscar Representante:\n"+ex);
       }
       return null;                
    }

    
    
    
    
    // listar representante
    public ArrayList<Representante> listar(){
        ArrayList<Representante> lista = new ArrayList<>();       
        
       try {
           PreparedStatement listar = con.conectar().prepareStatement("select * from Representante");
           ResultSet res = listar.executeQuery();
           
           while (res.next()) {               
               Representante r = new Representante();               
               r.setCodigo(res.getInt(1));
               r.setNome(res.getString(2));
               r.setEmail(res.getString(3));  
               r.setTelefone1(res.getString(4));
               r.setTelefone2(res.getString(5));                          
               lista.add(r);           
           }
           
           return lista;
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro ao listar Representante:\n"+ex);
       }
       return null;        
    }
  
    
    // excluir representante
    public void Excluir(int cod){
        PreparedStatement excluir;
       try {
           excluir = con.conectar().prepareStatement("delete from Representante where idRepresentante = "+cod);
           excluir.execute();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!", "Excluir", 3);
       } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Erro ao tentar excluir!\n Ainda existe Fornecedores com esse Representante", "Excluir", 3);
       }
        
        
    }

    @Override
    public String toString() {
        return getNome();
    }
    
    
}
