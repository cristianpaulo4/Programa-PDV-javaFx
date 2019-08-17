package Objetos;
import Conexao.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Marca {
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

     
    // cadastrando marca
    public void cadMarca(String marca){
        try {
            PreparedStatement cad = con.conectar().prepareStatement("insert into Marca (nome) values (?)");
            cad.setString(1, marca);
            cad.execute();
            System.out.println("Marca cadastrada!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar Marca:\n"+ex);
        }        
    }
    
    
    
    
    
    
    
    // verificar marca
    public boolean verificarMarca(String mar){
        try {
            PreparedStatement veri = con.conectar().prepareStatement("select * from Marca where nome = '"+mar+"'");
            ResultSet res = veri.executeQuery();            
            while (res.next()) {                
                return true;
            }
            
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "Erro ao verificar marca:\n"+ex);
        }
        return false;        
    }
    
    
    
    // Buscar marca
    public Marca buscarMarca(int cod){
        try {
            PreparedStatement veri = con.conectar().prepareStatement("select * from Marca where idMarca ="+cod);
            ResultSet res = veri.executeQuery();            
            while (res.next()) {                
                Marca m = new Marca();                
                m.setCodigo(res.getInt(1));
                m.setNome(res.getString(2));
                return m;
            }
            
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "Erro ao buscar marca:\n"+ex);
        }
        return null;
    }
    
    
    
    
    
    // listar marca
    public ArrayList<Marca> listar() throws SQLException{
        ArrayList<Marca> marca = new ArrayList<>();
        
        PreparedStatement listar = con.conectar().prepareStatement("select * from Marca");
        ResultSet res = listar.executeQuery();
        
        while (res.next()) { 
            Marca m = new Marca();            
            m.setCodigo(res.getInt(1));
            m.setNome(res.getString(2));
            marca.add(m);
        }        
        return marca;        
    }

    @Override
    public String toString() {
        return nome;
    }
    
    
    
    
    
}
