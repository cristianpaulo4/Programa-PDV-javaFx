package TelaRelatorios;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import Objetos.*;
import javafx.scene.chart.PieChart;

public class TelaRelatorioController implements Initializable {

    ProdutoVendido produto = new ProdutoVendido();
    Compra compra = new Compra();

    @FXML
    private AnchorPane RelatorioVenda;
    @FXML
    private LineChart<String, Double> grafico;
    @FXML
    private NumberAxis numero;
    @FXML
    private CategoryAxis categoria;
    @FXML
    private AnchorPane RelatorioSaida;
    @FXML
    private PieChart grafPizza;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //valores();

    }

    // Abrir Tela
    public Parent abrirTela() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaRelatorio.fxml"));
        return root;
    }

    // tela de relatorio de vendas
    @FXML
    public void RelaVendas() {
        RelatorioVenda.toFront();
        carregarDadosVenda();
    }

    // tela de relatorio de saida
    @FXML
    public void RelaSaida() {
        RelatorioSaida.toFront();
    }

    // carregar os dados do grafico de vendas
    public void carregarDadosVenda() {

    }

    public void valores() {
        ArrayList<Compra> listCompra = compra.listar();

        ArrayList<String> mes = new ArrayList<>();
        mes.add("janeiro");
        mes.add("fevereiro");
        mes.add("março");
        mes.add("abril");
        mes.add("maio");
        mes.add("junho");
        mes.add("julho");
        mes.add("agosto");
        mes.add("setembro");
        mes.add("outubro");
        mes.add("novembro");
        mes.add("dezembro");

        XYChart.Series<String, Double> serie = new XYChart.Series<>();
        serie.setName("2019");
        
            
        serie.getData().add(new XYChart.Data<>(mes.get(0), listCompra.get(0).getTotalCompra()));
        serie.getData().add(new XYChart.Data<>(mes.get(1), listCompra.get(1).getTotalCompra()));
        serie.getData().add(new XYChart.Data<>(mes.get(2), listCompra.get(2).getTotalCompra()));
        serie.getData().add(new XYChart.Data<>(mes.get(3), listCompra.get(3).getTotalCompra()));
               
            
        
        
         grafico.getData().add(serie);

    

    }

}
