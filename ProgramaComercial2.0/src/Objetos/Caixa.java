package Objetos;

import java.util.Date;
import Conexao.*;
import Auxiliares.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.Data;
import programaHome.Home;

public class Caixa {

    Conecta con = new Conecta();

    private int codVendedor;
    private int codCaixa;
    private double valorInicial;
    private double valorAtual;
    private double valorSaida;
    private double valorLucro;
    private String data;
    private int caixaTerminal;
    private boolean situacao;
    String formaPagamento;

    public int getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(int codVendedor) {
        this.codVendedor = codVendedor;
    }

    public int getCodCaixa() {
        return codCaixa;
    }

    public void setCodCaixa(int codCaixa) {
        this.codCaixa = codCaixa;
    }

    public double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public double getValorSaida() {
        return valorSaida;
    }

    public void setValorSaida(double valorSaida) {
        this.valorSaida = valorSaida;
    }

    public double getValorLucro() {
        return valorLucro;
    }

    public void setValorLucro(double valorLucro) {
        this.valorLucro = valorLucro;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCaixaTerminal() {
        return caixaTerminal;
    }

    public void setCaixaTerminal(int caixaTerminal) {
        this.caixaTerminal = caixaTerminal;
    }

    public boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    // abrir Caixa
    public void AbrirCaixa(Caixa caixa) {
        try {
            PreparedStatement abrir = con.conectar().prepareStatement("insert into Caixa (valorInicial, valorAtual, valorSaida, valorLucro, dataCaixa,caixaTerminal, Vendedor_idVendedor, situacao) values (?,?,?,?,?,?,?,?)");
            abrir.setDouble(1, caixa.getValorInicial());
            abrir.setDouble(2, caixa.getValorAtual());
            abrir.setDouble(3, caixa.getValorSaida());
            abrir.setDouble(4, caixa.getValorLucro());
            abrir.setString(5, caixa.getData());
            abrir.setInt(6, caixa.getCaixaTerminal());
            abrir.setInt(7, caixa.getCodVendedor());
            abrir.setBoolean(8, caixa.getSituacao());
            abrir.execute();
            System.out.println("Caixa Aberto com sucesso!");

        } catch (SQLException ex) {
            System.out.println("Erro ao Abrir Caixa");
        }

    }

    // abrir Caixa
    public Caixa BuscarCaixaPorData() {
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        formatador.format(data);
        String dataAtual = formatador.format(data);

        try {
            PreparedStatement buscar = con.conectar().prepareStatement("select *from Caixa where dataCaixa='" + dataAtual + "'");
            ResultSet res = buscar.executeQuery();

            while (res.next()) {
                Caixa caixa = new Caixa();
                caixa.setCodVendedor(res.getInt(8));
                if (caixa.getCodVendedor() == Home.getCodVendedor()) {
                  
                    caixa.setCodCaixa(res.getInt(1));
                    caixa.setValorInicial(res.getInt(2));
                    caixa.setValorAtual(res.getInt(3));
                    caixa.setValorSaida(res.getInt(4));
                    caixa.setValorLucro(res.getInt(5));
                    caixa.setData(res.getString(6));
                    caixa.setCaixaTerminal(res.getInt(7));
                    caixa.setCodVendedor(res.getInt(8));
                    caixa.setSituacao(res.getBoolean(9));
                    return caixa;

                }

            }

        } catch (SQLException ex) {
            System.out.println("Erro ao Buscar  Caixa");
        }
        return null;

    }

    // fechar caixa
    public void situacaoCaixa(boolean situacao) {
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        formatador.format(data);
        String dataAtual = formatador.format(data);
        Home.setAltorizado(situacao);
        PreparedStatement fechar;
        try {
            fechar = con.conectar().prepareStatement("update Caixa set situacao =? where dataCaixa='" + dataAtual + "'");
            fechar.setBoolean(1, situacao);
            fechar.execute();

            if (situacao) {
                System.out.println("Caixa Aberto");
            } else {
                System.out.println("Caixa fechado");

            }

        } catch (SQLException ex) {
            System.out.println("Erro ao fechar o caixa");
        }

    }
    
    
    

    // fechar caixa
    public void AtualizarDados(Caixa caixa, int codVendedor) {
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        formatador.format(data);
        String dataAtual = formatador.format(data);

        PreparedStatement atualiza;
        try {
            atualiza = con.conectar().prepareStatement("update Caixa set valorAtual =?, valorSaida=?, valorLucro=? where dataCaixa='" + dataAtual + "' and Vendedor_idVendedor ="+codVendedor);
            atualiza.setDouble(1, caixa.getValorAtual());
            atualiza.setDouble(2, caixa.getValorSaida());
            atualiza.setDouble(3, caixa.getValorLucro());

            atualiza.execute();

            System.out.println("Atualizador com sucesso");

        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar o caixa");
        }

    }

    // verificando se existe caixa iguais
    public boolean verificar() {
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        formatador.format(data);
        String dataAtual = formatador.format(data);
        try {
            PreparedStatement buscar = con.conectar().prepareStatement("select *from Caixa where dataCaixa='" + dataAtual + "'");
            ResultSet res = buscar.executeQuery();

            while (res.next()) {
                Caixa caixa = new Caixa();
                caixa.setCodVendedor(res.getInt(8));
                if (caixa.getCodVendedor() == Home.getCodVendedor()) {
                    return true;
                }
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao Buscar  Caixa");
        }
        return false;

    }

}
