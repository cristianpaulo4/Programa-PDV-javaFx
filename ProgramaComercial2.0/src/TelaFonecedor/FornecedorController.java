package TelaFonecedor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Objetos.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

public class FornecedorController implements Initializable {

    @FXML
    private TextField cxCnpj;
    @FXML
    private TextField cxNome;
    @FXML
    private TextField cxCidade;
    @FXML
    private ComboBox<String> cxEstado;
    @FXML
    private TextField cxBairro;
    @FXML
    private TextField cxRua;
    @FXML
    private TextField cxNumero;
    @FXML
    private TextField cxComplemento;
    @FXML
    private TextField cxEmail;
    @FXML
    private TextField cxTel1;
    @FXML
    private TextField cxTel2;
    @FXML
    private TextArea cxObs;
    @FXML
    private TableView<Fornecedor> tabFornecedor;
    @FXML
    private TableColumn<Fornecedor, String> coluCnpj;
    @FXML
    private TableColumn<Fornecedor, String> coluNome;
    @FXML
    private Button btnSalvar;
    @FXML
    private ComboBox<Representante> cbRepresentante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Listar();
        Estados();
        carregarRepresentantes();

    }

    int codigoFornecedor;
    int codigoRepresentante;

    // abrir tela de fornecedor
    public Parent Abrir() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Fornecedor.fxml"));
        return root;

    }

    // cadastrar fonecedor
    @FXML
    public void CadastrarFornecedor() {

        if (btnSalvar.getText().equals("Salvar")) {

            System.out.println("ok");
            Fornecedor forne = new Fornecedor();

            // fornecedor
            forne.setCnpj(cxCnpj.getText());
            forne.setNome(cxNome.getText());
            forne.setCidade(cxCidade.getText());
            forne.setEstado(cxEstado.getValue());
            forne.setBairro(cxBairro.getText());
            forne.setRua(cxRua.getText());
            forne.setNumero(Integer.parseInt(cxNumero.getText()));
            forne.setComplemento(cxComplemento.getText());
            forne.setEmail(cxEmail.getText());
            forne.setTelefone1(cxTel1.getText());
            forne.setTelefone2(cxTel2.getText());
            forne.setObservacao(cxObs.getText());
            forne.setRepresentante(cbRepresentante.getSelectionModel().getSelectedItem().getCodigo());

            forne.CadastrarFornecedor(forne);
            limparTela();
            Listar();

        } //alterando        
        else {
            Fornecedor forne = new Fornecedor();
            // fornecedor
            forne.setCnpj(cxCnpj.getText());
            forne.setNome(cxNome.getText());
            forne.setCidade(cxCidade.getText());
            forne.setEstado(cxEstado.getValue());
            forne.setBairro(cxBairro.getText());
            forne.setRua(cxRua.getText());
            forne.setNumero(Integer.parseInt(cxNumero.getText()));
            forne.setComplemento(cxComplemento.getText());
            forne.setEmail(cxEmail.getText());
            forne.setTelefone1(cxTel1.getText());
            forne.setTelefone2(cxTel2.getText());
            forne.setObservacao(cxObs.getText());

            forne.setRepresentante(cbRepresentante.getSelectionModel().getSelectedItem().getCodigo());
            forne.AlterarFornecedor(forne, codigoFornecedor);
            limparTela();
            Listar();
            cbRepresentante.setValue(null);
            btnSalvar.setText("Salvar");
        }

    }

    // limpar tela
    @FXML
    public void limparTela() {
        cxCnpj.setText("");
        cxNome.setText("");
        cxCidade.setText("");
        cxEstado.setValue("");
        cxBairro.setText("");
        cxRua.setText("");
        cxNumero.setText("");
        cxComplemento.setText("");
        cxEmail.setText("");
        cxTel1.setText("");
        cxTel2.setText("");
        cxObs.setText("");
        btnSalvar.setText("Salvar");
    }

    // listar fornecedores
    public void Listar() {
        Fornecedor f = new Fornecedor();
        ArrayList<Fornecedor> fo = f.listarFornecedor();
        ObservableList<Fornecedor> forne = FXCollections.observableArrayList(fo);
        coluCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        coluNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tabFornecedor.setItems(forne);
    }

    // excluir fornecedor
    @FXML
    public void excluir() {
        Fornecedor f = new Fornecedor();
        int cod = tabFornecedor.getSelectionModel().getSelectedItem().getCodigo();
        f.excluir(cod);
        Listar();
    }

    // alterar
    @FXML
    public void editar() {
        Fornecedor f = new Fornecedor();
        Representante r = new Representante();
        int cod = tabFornecedor.getSelectionModel().getSelectedItem().getCodigo();
        f = f.buscarFornecedorCodigo(cod);
        r = r.BuscarRepresentantePeloCodigo(f.getRepresentante());
        cxCnpj.setText(f.getCnpj());
        cxNome.setText(f.getNome());
        cxCidade.setText(f.getCidade());
        cxEstado.setValue(f.getEstado());
        cxBairro.setText(f.getBairro());
        cxRua.setText(f.getRua());
        cxNumero.setText(Integer.toString(f.getNumero()));
        cxComplemento.setText(f.getComplemento());
        cxEmail.setText(f.getEmail());
        cxTel1.setText(f.getTelefone1());
        cxTel2.setText(f.getTelefone2());
        cxObs.setText(f.getObservacao());

        // definindo o codigo do fornecedor
        codigoFornecedor = f.getCodigo();
        cbRepresentante.setValue(r.BuscarRepresentantePeloCodigo(f.getRepresentante()));
        btnSalvar.setText("Alterar");
    }

    // estados brasileiro
    private void Estados() {
        ArrayList<String> estados = new ArrayList<>();

        estados.add("Acre");
        estados.add("Alagoas");
        estados.add("Amapá");
        estados.add("Amazonas");
        estados.add("Bahia");
        estados.add("Ceará");
        estados.add("Distrito Federal");
        estados.add("Espírito Santo");
        estados.add("Goiás");
        estados.add("Maranhão");
        estados.add("Mato Grosso");
        estados.add("Mato Grosso do Sul");
        estados.add("Minas Gerais");
        estados.add("Pará");
        estados.add("Paraíba");
        estados.add("Paraná");
        estados.add("Pernambuco");
        estados.add("Piauí");
        estados.add("Rio de Janeiro");
        estados.add("Rio Grande do Norte");
        estados.add("Rio Grande do Sul");
        estados.add("Rondônia");
        estados.add("Roraima");
        estados.add("Santa Catarina");
        estados.add("São Paulo");
        estados.add("Sergipe");
        estados.add("Tocantins");

        ObservableList<String> estado = FXCollections.observableArrayList(estados);
        cxEstado.setItems(estado);

    }

    @FXML // cadastrar representante
    public void abrirRepresentante() throws IOException {
        RepresentanteController re = new RepresentanteController();
        re.AbrirTela();

    }

    public void carregarRepresentantes() {
        Representante re = new Representante();
        ObservableList<Representante> lis = FXCollections.observableArrayList(re.listar());
        cbRepresentante.setItems(lis);
    }

}
