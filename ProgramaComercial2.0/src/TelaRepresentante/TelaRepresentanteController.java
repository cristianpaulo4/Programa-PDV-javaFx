package TelaRepresentante;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Objetos.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaRepresentanteController implements Initializable {

    @FXML
    private TableView<Representante> tabRepresentante;
    @FXML
    private TableColumn<Representante, String> coluNome;
    @FXML
    private TableColumn<Representante, String> coluTelefone;
    @FXML
    private TextField cxNome;
    @FXML
    private TextField cxTel1;
    @FXML
    private TextField cxTel2;
    @FXML
    private TextField cxEmail;
    @FXML
    private TextField cxEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            Listar();

    }

    // abrir tela 
    public Parent abrir() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaRepresentante.fxml"));
        return root;

    }

    // listar representantes
    public void Listar() {
        Representante r = new Representante();
        ArrayList<Representante> list = r.listar();
        coluNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone1"));
        ObservableList<Representante> res = FXCollections.observableArrayList(list);
        tabRepresentante.setItems(res);
    }

    // excluir representante
    @FXML
    public void excluir() throws SQLException {
        Representante r = new Representante();
        int cod = tabRepresentante.getSelectionModel().getSelectedItem().getCodigo();
        r.Excluir(cod);
        Listar();
    }

    // carregar detalhes
    @FXML
    public void carregarDetalhes() {
        Representante r = new Representante();
        Fornecedor f = new Fornecedor();

        int cod = tabRepresentante.getSelectionModel().getSelectedItem().getCodigo();
        r=r.BuscarRepresentantePeloCodigo(cod);
        cxNome.setText(r.getNome());
        cxTel1.setText(r.getTelefone1());
        cxTel2.setText(r.getTelefone2());
        cxEmail.setText(r.getEmail());
        
        f = f.buscarFornecedorRepresentante(r.getCodigo());
        cxEmpresa.setText(f.getNome());

    }

}
