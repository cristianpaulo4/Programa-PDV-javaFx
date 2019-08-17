package Objetos;
import Conexao.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Categoria {
    Conecta con = new Conecta();
    
    private int codigo;
    private String nome;

    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    // cadastrar categoria
    public void cadCategoria(String cat){
        try {
            PreparedStatement cad = con.conectar().prepareStatement("insert into Categoria (nome) values(?)");
            cad.setString(1, cat);
            cad.execute();            
            System.out.println("Categoria salvo com sucesso!");            
                        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao salvar a categoria:\n"+ex);
        }                
    }
    
    
    
    
     // listar categoria
    public ArrayList<Categoria> listar() throws SQLException {
        ArrayList<Categoria> categoria = new ArrayList<>();        
        PreparedStatement listar = con.conectar().prepareStatement("select * from Categoria");
        ResultSet res = listar.executeQuery();
        
        while (res.next()) {
            Categoria c = new Categoria();
            c.setCodigo(res.getInt(1));
            c.setNome(res.getString(2));
            categoria.add(c);
        }        
        return categoria;        
    }

    
       // verificar marca
    public boolean verificarCategoria(String cat){
        try {
            PreparedStatement veri = con.conectar().prepareStatement("select * from Categoria where nome = '"+cat+"'");
            ResultSet res = veri.executeQuery();            
            while (res.next()) {                
                return true;
            }
            
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "Erro ao verificar marca:\n"+ex);
        }
        return false;        
    }
    
    
     // buscar categoria
    public Categoria buscarCategoria(int cat){
        try {
            PreparedStatement veri = con.conectar().prepareStatement("select * from Categoria where idCategoria = "+cat);
            ResultSet res = veri.executeQuery();            
            while (res.next()) {                
                this.setCodigo(res.getInt(1));
                this.setNome(res.getString(2));
                return this;
            }
            
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "Erro ao buscar marca:\n"+ex);
        }
        return null;
           
    }
    
    
    
    
    
    
    // somente o nome da categoria
    @Override
    public String toString() {
        return nome;
    }
    
    
    
    
    
    
    
}
