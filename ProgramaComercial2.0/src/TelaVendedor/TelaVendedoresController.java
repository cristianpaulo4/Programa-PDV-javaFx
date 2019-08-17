package TelaVendedor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Objetos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaVendedoresController implements Initializable {

    Vendedor vendedor = new Vendedor();

    @FXML
    private TextField cxCpf;
    @FXML
    private CheckBox checkAdmin;
    @FXML
    private TextField cxNome;
    @FXML
    private TextField cxSalario;
    @FXML
    private TextField cxCidade;
    @FXML
    private TextField cxBairro;
    @FXML
    private TextField cxRua;
    @FXML
    private TextField cxNumero;
    @FXML
    private TextField cxTelefone1;
    @FXML
    private TextField cxTelefone2;
    @FXML
    private TextField cxEmail;
    @FXML
    private TextField cxUsuario;
    @FXML
    private TextField cxSenha;
    @FXML
    private Button btnSalvar;
    @FXML
    private TableView<Vendedor> tabVendedor;
    @FXML
    private TableColumn<Vendedor, String> coluNome;
    @FXML
    private TableColumn<Vendedor, Double> coluSalario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listarVendedor();
        
               
    }

    // abrir Tela
    public Parent abrirTela() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaVendedores.fxml"));
        return root;
    }

    // limpar campos
    public void limpar() {
        cxCpf.setText("");
        checkAdmin.selectedProperty().set(false);
        cxNome.setText("");
        cxSalario.setText("");
        cxCidade.setText("");
        cxBairro.setText("");
        cxRua.setText("");
        cxNumero.setText("");
        cxTelefone1.setText("");
        cxTelefone2.setText("");
        cxEmail.setText("");
        cxUsuario.setText("");
        cxSenha.setText("");
    }

    // cadastrar vendedor
    public void cadVendedor() {
        vendedor = pegarDados();
        vendedor.cadVendedor(vendedor);
        listarVendedor();
        limpar();
    }

    // vendedor
    public Vendedor pegarDados() {
        Vendedor vendedor1 = new Vendedor();
        vendedor1.setCpf(cxCpf.getText());
        vendedor1.setAdmin(checkAdmin.selectedProperty().get());
        vendedor1.setNome(cxNome.getText());
        vendedor1.setSalario(Double.parseDouble(cxSalario.getText()));
        vendedor1.setCidade(cxCidade.getText());
        vendedor1.setBairro(cxBairro.getText());
        vendedor1.setRua(cxRua.getText());
        vendedor1.setNumero(Integer.parseInt(cxNumero.getText()));
        vendedor1.setTelefone1(cxTelefone1.getText());
        vendedor1.setTelefone2(cxTelefone2.getText());
        vendedor1.setEmail(cxEmail.getText());
        vendedor1.setUsuario(cxUsuario.getText());
        vendedor1.setSenha(cxSenha.getText());
        return vendedor1;
    }

    // listando vendedor
    public void listarVendedor() {
        coluNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        ObservableList<Vendedor> obs = FXCollections.observableArrayList(vendedor.listar());
        tabVendedor.setItems(obs);
    }

}
