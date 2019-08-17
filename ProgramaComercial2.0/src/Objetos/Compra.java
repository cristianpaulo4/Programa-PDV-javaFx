package Objetos;

import Conexao.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Compra {

    Conecta con = new Conecta();

    private int codVenda;
    private int caixa;
    private int codVendedor;
    private double totalCompra;
    private String dataVenda;
    private int codCliente;
    private boolean tipo;
    private String formaPag;
    private boolean quitada;

    public int getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(int codVenda) {
        this.codVenda = codVenda;
    }

    public int getCaixa() {
        return caixa;
    }

    public void setCaixa(int caixa) {
        this.caixa = caixa;
    }

    public int getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(int codVendedor) {
        this.codVendedor = codVendedor;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public boolean getTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public String getFormaPag() {
        return formaPag;
    }

    public void setFormaPag(String formaPag) {
        this.formaPag = formaPag;
    }

     public boolean getQuitada() {
        return quitada;
    }

    public void setQuitada(boolean quitada) {
        this.quitada = quitada;
    }
    

    
    // cadastrar Venda a prazo COM CLIENTE
    public void cadCompra(Compra compra) {
        try {
            PreparedStatement cad = con.conectar().prepareStatement("insert into Compra (caixa, total, dataVenda, tipoVenda, Vendedor_idVendedor, Cliente_idCliente, formaPag,Quitada) values (?,?,?,?,?,?,?,?)");
            cad.setInt(1, compra.getCaixa());
            cad.setDouble(2, compra.getTotalCompra());
            cad.setString(3, compra.getDataVenda());
            cad.setBoolean(4, compra.getTipo());
            cad.setInt(5, compra.getCodVendedor());
            cad.setInt(6, compra.getCodCliente());
            cad.setString(7, compra.getFormaPag());
            cad.setBoolean(8, false);

            cad.execute();
            System.out.println("Compra realizada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao compra:\n" + ex);
        }
    }

    // cadastrar Venda a prazo SEM CLIENTE
    public void cadCompraSemCliente(Compra compra) {
        try {
            PreparedStatement cad = con.conectar().prepareStatement("insert into Compra (caixa, total, dataVenda, tipoVenda, Vendedor_idVendedor, formaPag, Quitada) values (?,?,?,?,?,?,?)");
            cad.setInt(1, compra.getCaixa());
            cad.setDouble(2, compra.getTotalCompra());
            cad.setString(3, compra.getDataVenda());
            cad.setBoolean(4, compra.getTipo());
            cad.setInt(5, compra.getCodVendedor());
            cad.setString(6, compra.getFormaPag());
            cad.setBoolean(7, true);
            cad.execute();
            System.out.println("Compra realizada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao compra:\n" + ex);
        }
    }

    // ultima compra
    public int ultimaCompra() {
        try {
            PreparedStatement ult = con.conectar().prepareStatement("select max(idCompra) from Compra");
            ResultSet res = ult.executeQuery();

            while (res.next()) {
                this.codVenda = res.getInt(1);
                return codVenda;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao procurar ultima compra:\n" + ex);
        }
        return 0;
    }

    // listar compra     
    public ArrayList<Compra> listar() {
        ArrayList<Compra> compra = new ArrayList<>();
        try {
            PreparedStatement lista = con.conectar().prepareStatement("select * from Compra");
            ResultSet res = lista.executeQuery();
            while (res.next()) {
                Compra com = new Compra();
                com.setCodVenda(res.getInt(1));
                com.setCaixa(res.getInt(2));
                com.setTotalCompra(res.getDouble(3));
                com.setDataVenda(res.getString(4));
                com.setTipo(res.getBoolean(5));
                com.setCodVendedor(res.getInt(6));
                com.setCodCliente(res.getInt(7));
                com.setFormaPag(res.getString(8));

                compra.add(com);
            }

            return compra;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar compra:\n" + ex);
        }
        return null;
    }

    // listar compra por data    
    public ArrayList<Compra> listarCompraData(String data) {
        ArrayList<Compra> compra = new ArrayList<>();
        try {
            PreparedStatement lista = con.conectar().prepareStatement("select * from Compra where dataVenda ='" + data + "'");
            ResultSet res = lista.executeQuery();
            while (res.next()) {
                Compra com = new Compra();
                com.setCodVenda(res.getInt(1));
                com.setCaixa(res.getInt(2));
                com.setTotalCompra(res.getDouble(3));
                com.setDataVenda(res.getString(4));
                com.setTipo(res.getBoolean(5));
                com.setCodVendedor(res.getInt(6));
                com.setCodCliente(res.getInt(7));
                com.setFormaPag(res.getString(8));
                com.setQuitada(res.getBoolean(9));
                compra.add(com);
            }
            return compra;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao procurar compra por data:\n" + ex);
        }
        return null;
    }

    // listar compra por codigo    
    public Compra listarCompraCodigo(int cod) {
        try {
            PreparedStatement lista = con.conectar().prepareStatement("select * from Compra where idCompra =" + cod);
            ResultSet res = lista.executeQuery();
            while (res.next()) {
                Compra com = new Compra();
                com.setCodVenda(res.getInt(1));
                com.setCaixa(res.getInt(2));
                com.setTotalCompra(res.getDouble(3));
                com.setDataVenda(res.getString(4));
                com.setTipo(res.getBoolean(5));
                com.setCodVendedor(res.getInt(6));
                com.setCodCliente(res.getInt(7));
                com.setFormaPag(res.getString(8));
                com.setQuitada(res.getBoolean(9));
                return com;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao procurar compra por data:\n" + ex);
        }
        return null;
    }

    
    
    // buscar compra por cliente  
    public ArrayList<Compra> BuscarCompraPorCliente(int codigoCliente) {
        ArrayList<Compra> compra = new ArrayList<>();
        try {
            PreparedStatement lista = con.conectar().prepareStatement("select * from Compra where Cliente_idCliente="+codigoCliente);
            ResultSet res = lista.executeQuery();
            while (res.next()) {
                Compra com = new Compra();
                com.setCodVenda(res.getInt(1));
                com.setCaixa(res.getInt(2));
                com.setTotalCompra(res.getDouble(3));
                com.setDataVenda(res.getString(4));
                com.setTipo(res.getBoolean(5));
                com.setCodVendedor(res.getInt(6));
                com.setCodCliente(res.getInt(7));
                com.setFormaPag(res.getString(8));
                com.setQuitada(res.getBoolean(9));
                compra.add(com);
            }

            return compra;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar compra:\n" + ex);
        }
        return null;

    }
    
    
    
    // quitar conta
    public void quitarConta(int cod, boolean quita){
        try {
            PreparedStatement quitar = con.conectar().prepareStatement("update Compra set Quitada =? where Cliente_idCliente = " + cod);
            quitar.setBoolean(1, quita);
            quitar.execute();
            System.out.println("Compra Quitada");
        } catch (SQLException ex) {
            System.out.println("Erro ao Quitar Compra");
        }
        
        
    }

   
}
