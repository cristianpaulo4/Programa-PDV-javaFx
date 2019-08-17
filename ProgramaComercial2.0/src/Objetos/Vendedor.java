package Objetos;
import Conexao.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Vendedor extends Endereco{
    Conecta con = new Conecta();
    
    
    
    private int codigo;
    private String cpf;
    private String nome;
    private double salario;
    private double adiantamento;
    private String telefone1;
    private String telefone2;
    private String email;
    private String usuario;
    private String senha;
    private boolean admin;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public double getAdiantamento() {
        return adiantamento;
    }

    public void setAdiantamento(double adiantamento) {
        this.adiantamento = adiantamento;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    
    
    
    // cadastrar vendedor
    public void cadVendedor(Vendedor vend){
        try {
            PreparedStatement cad = con.conectar().prepareStatement("insert into Vendedor (cpf, nome, salario, cidade, bairro, rua, numero, telefone1, telefone2, email, usuario, senha, admin) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            cad.setString(1, vend.getCpf());
            cad.setString(2, vend.getNome());
            cad.setDouble(3, vend.getSalario());            
            cad.setString(4, vend.getCidade());
            cad.setString(5, vend.getBairro());
            cad.setString(6, vend.getRua());
            cad.setInt(7, vend.getNumero());
            cad.setString(8, vend.getTelefone1());
            cad.setString(9, vend.getTelefone2());
            cad.setString(10, vend.getEmail());
            cad.setString(11, vend.getUsuario());
            cad.setString(12, vend.getSenha());
            cad.setBoolean(13, vend.getAdmin());
            cad.execute();
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
        
        
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "Erro ao cadastrar:\n"+ex);
        }
    }
    
    
    
    // listando vendedor
    public ArrayList<Vendedor> listar(){
        ArrayList<Vendedor> list = new ArrayList<>();
        
        try {
            PreparedStatement lista = con.conectar().prepareStatement("select * from Vendedor");
            ResultSet res = lista.executeQuery();
            
            while (res.next()) {                
                Vendedor vend = new Vendedor();
                vend.setCodigo(res.getInt(1));
                vend.setCpf(res.getString(2));
                vend.setNome(res.getString(3));
                vend.setSalario(res.getDouble(4));
                vend.setAdiantamento(res.getDouble(5));
                vend.setCidade(res.getString(6));
                vend.setBairro(res.getString(7));
                vend.setRua(res.getString(8));
                vend.setNumero(res.getInt(9));
                vend.setTelefone1(res.getString(10));
                vend.setTelefone2(res.getString(11));
                vend.setEmail(res.getString(12));
                vend.setUsuario(res.getString(13));
                vend.setSenha(res.getString(14));                
                list.add(vend);
            }
            
            return list;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar:\n"+ex);
        }
        return null;
        
    }
    
    
    
    
    // verificando vendedor
    public Vendedor Verificar(String usuario, String senha){        
        try {
            PreparedStatement lista = con.conectar().prepareStatement("select * from Vendedor where usuario ='"+usuario+"'and senha ='"+senha+"'");
            ResultSet res = lista.executeQuery();
            
            while (res.next()) {                
                Vendedor vend = new Vendedor();
                vend.setCodigo(res.getInt(1));
                vend.setCpf(res.getString(2));
                vend.setNome(res.getString(3));
                vend.setSalario(res.getDouble(4));
                vend.setAdiantamento(res.getDouble(5));
                vend.setCidade(res.getString(6));
                vend.setBairro(res.getString(7));
                vend.setRua(res.getString(8));
                vend.setNumero(res.getInt(9));
                vend.setTelefone1(res.getString(10));
                vend.setTelefone2(res.getString(11));
                vend.setEmail(res.getString(12));
                vend.setUsuario(res.getString(13));
                vend.setSenha(res.getString(14));     
                vend.setAdmin(res.getBoolean(15));
                return vend;
            }
            
            
            Vendedor v = new Vendedor();
            v.setNome("nao");            
            return v;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao altenticar:\n"+ex);
        }
        return null;
        
    }
    
    
    
    // buscar vendedor
    public Vendedor Buscar(int cod){        
        try {
            PreparedStatement lista = con.conectar().prepareStatement("select * from Vendedor where idVendedor ="+cod);
            ResultSet res = lista.executeQuery();
            
            while (res.next()) {                
                Vendedor vend = new Vendedor();
                vend.setCodigo(res.getInt(1));
                vend.setCpf(res.getString(2));
                vend.setNome(res.getString(3));
                vend.setSalario(res.getDouble(4));
                vend.setAdiantamento(res.getDouble(5));
                vend.setCidade(res.getString(6));
                vend.setBairro(res.getString(7));
                vend.setRua(res.getString(8));
                vend.setNumero(res.getInt(9));
                vend.setTelefone1(res.getString(10));
                vend.setTelefone2(res.getString(11));
                vend.setEmail(res.getString(12));
                vend.setUsuario(res.getString(13));
                vend.setSenha(res.getString(14));     
                vend.setAdmin(res.getBoolean(15));
                return vend;
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Buscar:\n"+ex);
        }
        return null;
        
    }
    
    
    
    
    
    
    
}
