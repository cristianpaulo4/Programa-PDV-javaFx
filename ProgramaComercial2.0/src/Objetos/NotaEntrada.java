package Objetos;

import java.sql.PreparedStatement;
import Conexao.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class NotaEntrada {

    Conecta con = new Conecta();

    private int numero;
    private int fonecedor;
    private double valor;
    private double valorProduto;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getFonecedor() {
        return fonecedor;
    }

    public void setFonecedor(int fonecedor) {
        this.fonecedor = fonecedor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(double valorProduto) {
        this.valorProduto = valorProduto;
    }

    // cadastrar nota
    public void Cadastrar(NotaEntrada nota) {
        try {
            PreparedStatement cad = con.conectar().prepareStatement("insert into Nota (idNota, Fornecedor_idFornecedor, valorNota) values (?,?,?)");
            cad.setInt(1, nota.getNumero());
            cad.setInt(2, nota.getFonecedor());
            cad.setDouble(3, nota.getValor());
            cad.execute();
            System.out.println("Nota cadastrada");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar Nota:\n" + ex);
        }

    }

    // buscar nota de entrada
    public NotaEntrada buscar(int num) {
        try {
            PreparedStatement buscar = con.conectar().prepareStatement("select * from Nota where idNota = " + num);
            ResultSet res = buscar.executeQuery();

            while (res.next()) {
                this.setNumero(res.getInt(1));                
                this.setValor(res.getDouble(2));
                this.setValorProduto(res.getDouble(3));
                this.setFonecedor(res.getInt(4));
                return this;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar nota:\n" + ex);
        }
        return null;

    }

    // listar nota de entrada
    public ArrayList<NotaEntrada> ListandoNota() {
        ArrayList<NotaEntrada> lista = new ArrayList<>();

        try {
            PreparedStatement listar = con.conectar().prepareStatement("select * from Nota");
            ResultSet res = listar.executeQuery();

            while (res.next()) {
                NotaEntrada n = new NotaEntrada();

                n.setNumero(res.getInt(1));
                n.setFonecedor(res.getInt(2));
                n.setValor(res.getDouble(3));
                n.setValorProduto(res.getDouble(4));
                lista.add(n);
            }

            return lista;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Listar nota:\n" + ex);
        }
        return null;

    }

    // alterando valor dos produtos nota
    public void alterar(double num, int idNota) {
        try {
            PreparedStatement alterar = con.conectar().prepareStatement("update Nota set valorProduto =" + num + "where idNota =" + idNota);
            alterar.execute();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar nota:\n" + ex);
        }

    }

    // verificar se nota existe
    public boolean existe(int num) {
        try {
            PreparedStatement buscar = con.conectar().prepareStatement("select * from Nota where idNota = " + num);
            ResultSet res = buscar.executeQuery();
            while (res.next()) {               
                return true;
            }
            return false;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar se nota existe:\n" + ex);
        }
        return false;
    }

    
    
    // excluir
    public void Excluir(int cod) {
        int op = JOptionPane.showConfirmDialog(null, "Deseja excluir essa Nota?", "Excluir", JOptionPane.YES_NO_OPTION, 3);
        PreparedStatement ex;
        
        
        if (op == 0) {
            try {
                ex = con.conectar().prepareStatement("delete from Nota where idNota = " + cod);
                ex.execute();
                JOptionPane.showMessageDialog(null, "Excluido com sucesso!", "Excluido", 2);

            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar excluir a Nota!", "Erro", 0);
            }

        }

    }

}
