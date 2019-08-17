package Objetos;

import Conexao.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Auxiliares.*;

public class Cliente extends Endereco {

    Conecta con = new Conecta();

    private int codigo;
    private String cpf;
    private String nome;
    private double debito;
    private double haver;

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

    public double getDebito() {
        return debito;
    }

    public void setDebito(double debito) {
        this.debito = debito;
    }

      public double getHaver() {
        return haver;
    }

    public void setHaver(double haver) {
        this.haver = haver;
    }

    
    
    
    
    // cadastrar cliente
    public void cadastrar(Cliente cli) {
        try {
            PreparedStatement cad = con.conectar().prepareStatement("insert into Cliente (cpf, nome, rua, numero, bairro,complemento, telefone1, telefone2, email, debito, haver) values (?,?,?,?,?,?,?,?,?,?,?)");

            cad.setString(1, cli.getCpf());
            cad.setString(2, cli.getNome());
            cad.setString(3, cli.getRua());
            cad.setInt(4, cli.getNumero());
            cad.setString(5, cli.getBairro());
            cad.setString(6, cli.getComplemento());
            cad.setString(7, cli.getTelefone1());
            cad.setString(8, cli.getTelefone2());
            cad.setString(9, cli.getEmail());
            cad.setDouble(10, 0);
            cad.setDouble(11, 0);            
            cad.execute();
            //JOptionPane.showMessageDialog(null, "Cliente cadastrado!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente:\n" + ex, "Erro", 0);
        }

    }

    // listando cliente
    public ArrayList<Cliente> listar() {
        ArrayList<Cliente> listar = new ArrayList<>();
        try {
            PreparedStatement list = con.conectar().prepareStatement("select * from Cliente");
            ResultSet res = list.executeQuery();

            while (res.next()) {
                Cliente c = new Cliente();
                c.setCodigo(res.getInt(1));
                c.setCpf(res.getString(2));
                c.setNome(res.getString(3));
                c.setRua(res.getString(4));
                c.setNumero(res.getInt(5));
                c.setBairro(res.getString(6));
                c.setComplemento(res.getString(7));
                c.setTelefone1(res.getString(8));
                c.setTelefone2(res.getString(9));
                c.setEmail(res.getString(10));
                listar.add(c);
            }

            return listar;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar cliente:\n" + ex);
        }

        return null;

    }

    // buscando cliente
    public Cliente buscar(int cod) {
        try {
            PreparedStatement buscar = con.conectar().prepareStatement("select * from Cliente where idCliente = " + cod);
            ResultSet res = buscar.executeQuery();

            while (res.next()) {
                Cliente cliente = new Cliente();
                cliente.setCodigo(res.getInt(1));
                cliente.setCpf(res.getString(2));
                cliente.setNome(res.getString(3));
                cliente.setRua(res.getString(4));
                cliente.setNumero(res.getInt(5));
                cliente.setBairro(res.getString(6));
                cliente.setComplemento(res.getString(7));
                cliente.setTelefone1(res.getString(8));
                cliente.setTelefone2(res.getString(9));
                cliente.setEmail(res.getString(10));
                cliente.setDebito(res.getDouble(11));
                cliente.setHaver(res.getDouble(12));
                return cliente;
            }

            Cliente cli = new Cliente();
            cli.setNome("Sem Cliente");
            return cli;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar cliente:\n" + ex, "Erro", 0);
        }
        return null;
    }

    // buscando cliente pelo nome
    public ArrayList<Cliente> buscarNome(String nome) {
        ArrayList<Cliente> listar = new ArrayList<>();
        try {
            PreparedStatement buscar = con.conectar().prepareStatement("select * from Cliente where nome like '" + nome + "%'");
            ResultSet res = buscar.executeQuery();

            while (res.next()) {
                Cliente c = new Cliente();
                c.setCodigo(res.getInt(1));
                c.setCpf(res.getString(2));
                c.setNome(res.getString(3));
                c.setRua(res.getString(4));
                c.setNumero(res.getInt(5));
                c.setBairro(res.getString(6));
                c.setComplemento(res.getString(7));
                c.setTelefone1(res.getString(8));
                c.setTelefone2(res.getString(9));
                c.setEmail(res.getString(10));
                listar.add(c);

            }
            return listar;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar cliente:\n" + ex, "Erro", 0);
        }
        return null;
    }

    // alterar
    public void alterar(Cliente cli, int cod) {
        try {
            PreparedStatement altera = con.conectar().prepareStatement("update Cliente set cpf = ?, nome = ?, rua =?, numero = ?, bairro =?, complemento=?, telefone1=?, telefone2=?, email=? where idCliente = " + cod);
            altera.setString(1, cli.getCpf());
            altera.setString(2, cli.getNome());
            altera.setString(3, cli.getRua());
            altera.setInt(4, cli.getNumero());
            altera.setString(5, cli.getBairro());
            altera.setString(6, cli.getComplemento());
            altera.setString(7, cli.getTelefone1());
            altera.setString(8, cli.getTelefone2());
            altera.setString(9, cli.getEmail());
            altera.execute();

            JOptionPane.showMessageDialog(null, "Alterado com sucesso!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Alterar dados do cliente!:\n" + ex);
        }

    }

    // excluir
    public void excluir(int cod) {
        int op = JOptionPane.showConfirmDialog(null, "Deseja excluir?", "Excluir", JOptionPane.OK_CANCEL_OPTION);

        try {
            PreparedStatement excluir = con.conectar().prepareStatement("delete from Cliente where idCliente=" + cod);

            if (op == 0) {
                excluir.execute();
                JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Excluir:\n" + ex);
        }

    }

    // adicionando debito do cliente    
    public void debitoDoCliente(int codCli, double valor) {
        Cliente cli = new Cliente();
        cli = cli.buscar(codCli);
        double debito2 = cli.getDebito() + valor;
        PreparedStatement contaDebito;
        try {
            contaDebito = con.conectar().prepareStatement("update Cliente set debito =? where idCliente = " + codCli);            
            contaDebito.setDouble(1, debito2);            
            contaDebito.execute();
            System.out.println("Debito adicionado!");

        } catch (SQLException ex) {
            System.out.println("Erro ao adicionar debito para o cliente");
        }

    }
    
    
    
    // adicionando haver do cliente    
    public void adicionarHaver(int codCli, double valor) {
        Cliente cli = new Cliente();
        cli = cli.buscar(codCli);
        double debito2 = Conversores.converterDoubleDoisDecimais(cli.getDebito()) - valor;
        double haver2 = Conversores.converterDoubleDoisDecimais(cli.getHaver())+valor;
        
        if(debito2<=0){
            haver2=0;
            debito2=0;
            Compra c = new Compra();
            c.quitarConta(codCli, true);           
            
        }        
        
        PreparedStatement contaDebito;
        try {
            contaDebito = con.conectar().prepareStatement("update Cliente set debito =?, haver =? where idCliente = " + codCli);            
            contaDebito.setDouble(1, debito2);  
            contaDebito.setDouble(2, haver2);  
            
            contaDebito.execute();
            System.out.println("Haver adicionado!");

        } catch (SQLException ex) {
            System.out.println("Erro ao adicionar Haver para o cliente");
        }

    }

   

}
