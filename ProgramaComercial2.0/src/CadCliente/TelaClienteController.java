package CadCliente;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Objetos.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaClienteController implements Initializable {

    @FXML
    private TextField cxCPF;
    @FXML
    private TextField cxNome;
    @FXML
    private TextField cxRua;
    @FXML
    private TextField cxNumero;
    @FXML
    private TextField cxBairro;
    @FXML
    private TextField cxComplemento;
    @FXML
    private TextField cxTelefone1;
    @FXML
    private TextField cxTelefone2;
    @FXML
    private TextField cxEmail;
    @FXML
    private TableView<Cliente> tabCliente;
    @FXML
    private TableColumn<Cliente, Integer> coluCodigo;
    @FXML
    private TableColumn<Cliente, String> coluNome;
    @FXML
    private TableColumn<Cliente, String> coluCPF;

    Cliente cli = new Cliente();
    @FXML
    private Button btnSalvar;
    @FXML
    private MenuItem btnEditar;
    @FXML
    private MenuItem btnExcluir;
    @FXML
    private TextField cxBuscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // selecionando cliene
        tabCliente.setOnMouseClicked((event) -> {
            selecionarCliente();
        });

        listar();

        // buscando com o nome do cliente
        cxBuscar.setOnKeyReleased((event) -> {
            System.out.println(cxBuscar.getText());
            
            Cliente cli2 = new Cliente();
            ArrayList<Cliente> res = cli2.buscarNome(cxBuscar.getText());
            coluCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            coluCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
            coluNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            ObservableList<Cliente> list = FXCollections.observableArrayList(res);
            tabCliente.setItems(list);
          
        });
        
      
    }

    // abrir tela
    public Parent abri() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TelaCliente.fxml"));
        return root;
    }

    // cadastrar cliente
    public void cadCliente() {
        cli.setCpf(cxCPF.getText());
        cli.setNome(cxNome.getText());
        cli.setRua(cxRua.getText());
        cli.setNumero(Integer.parseInt(cxNumero.getText()));
        cli.setBairro(cxBairro.getText());
        cli.setComplemento(cxComplemento.getText());
        cli.setTelefone1(cxTelefone1.getText());
        cli.setTelefone2(cxTelefone2.getText());
        cli.setEmail(cxEmail.getText());
        cli.cadastrar(cli);
        listar();
        limpar();
    }

    // cadastrar cliente
    @FXML
    public void limpar() {
        cxCPF.setText("");
        cxNome.setText("");
        cxRua.setText("");
        cxNumero.setText("");
        cxBairro.setText("");
        cxComplemento.setText("");
        cxTelefone1.setText("");
        cxTelefone2.setText("");
        cxEmail.setText("");
        btnSalvar.setText("Salvar");
        bloquear(true);
    }

    // bloquear / desbloquear campos
    public void bloquear(boolean op) {
        cxCPF.setEditable(op);
        cxNome.setEditable(op);
        cxRua.setEditable(op);
        cxNumero.setEditable(op);
        cxBairro.setEditable(op);
        cxComplemento.setEditable(op);
        cxTelefone1.setEditable(op);
        cxTelefone2.setEditable(op);
        cxEmail.setEditable(op);
    }

    // listando na tabela
    public void listar() {
        coluCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        coluCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        coluNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        ObservableList<Cliente> list = FXCollections.observableArrayList(cli.listar());
        tabCliente.setItems(list);
    }

    // carregando cliente na tabela
    public void selecionarCliente() {
        int cod = tabCliente.getSelectionModel().getSelectedItem().getCodigo();
        cli = cli.buscar(cod);

        cxCPF.setText(cli.getCpf());
        cxNome.setText(cli.getNome());
        cxRua.setText(cli.getRua());
        cxNumero.setText(Integer.toString(cli.getNumero()));
        cxBairro.setText(cli.getBairro());
        cxComplemento.setText(cli.getComplemento());
        cxTelefone1.setText(cli.getTelefone1());
        cxTelefone2.setText(cli.getTelefone2());
        cxEmail.setText(cli.getEmail());
        btnSalvar.setText("Novo");
        bloquear(false);
    }

    // editar 
    @FXML
    public void editar() {
        bloquear(true);
        btnSalvar.setText("Alterar");
        cxBuscar.setText("");
       
    }

    // opcao do botão
    @FXML
    public void opBotao() {
        if (btnSalvar.getText().equals("Salvar")) {
            cadCliente();
            cxBuscar.setText("");

        } else if (btnSalvar.getText().equals("Novo")) {
            limpar();
            bloquear(true);
            btnSalvar.setText("Salvar");
            cxBuscar.setText("");
        } else {
            // alterando
            int cod = tabCliente.getSelectionModel().getSelectedItem().getCodigo();
            cli.setCpf(cxCPF.getText());
            cli.setNome(cxNome.getText());
            cli.setRua(cxRua.getText());
            cli.setNumero(Integer.parseInt(cxNumero.getText()));
            cli.setBairro(cxBairro.getText());
            cli.setComplemento(cxComplemento.getText());
            cli.setTelefone1(cxTelefone1.getText());
            cli.setTelefone2(cxTelefone2.getText());
            cli.setEmail(cxEmail.getText());
            cli.alterar(cli, cod);
            listar();
            limpar();
            btnSalvar.setText("Salvar");
            cxBuscar.setText("");
        }

    }

    // excluir cliente
    @FXML
    public void excluir() {
        int cod = tabCliente.getSelectionModel().getSelectedItem().getCodigo();
        cli.excluir(cod);
        listar();
        cxBuscar.setText("");

    }

}
