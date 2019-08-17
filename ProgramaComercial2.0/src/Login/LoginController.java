package Login;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import programaHome.Home;
import Objetos.*;
import TelaCaixa.TelaCaixaController;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginController implements Initializable {

    Vendedor vendedor = new Vendedor();

    static Stage stage = new Stage();

    @FXML
    private TextField cxUsuario;
    @FXML
    private PasswordField cxSenha;
    @FXML
    private Button btnEntrar;

    // abrir tela de login
    public void abrirTela() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    // abrir tela do home 
    @FXML
    public void AbrirHome() throws IOException {
        String usuario = cxUsuario.getText();
        String senha = cxSenha.getText();

        vendedor = vendedor.Verificar(usuario, senha);

        System.out.println(vendedor.getUsuario() + vendedor.getSenha());

        if (usuario.equals("admin") && senha.equals("admin") || usuario.equals(vendedor.getUsuario()) && senha.equals(vendedor.getSenha())) {
            Home h = new Home();
            Home.setUsuario(vendedor.getUsuario());
            Home.setAdmin(vendedor.getAdmin());
            Home.setCodVendedor(vendedor.getCodigo());

            if (usuario.equals("admin")) {
                Home.setAdmin((true));
            }
            System.out.println(vendedor.getAdmin());

            h.abrirHome();
            stage.close();
        } else {
            JOptionPane.showMessageDialog(null, "Senha Incorreta");
            cxUsuario.setText("");
            cxSenha.setText("");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnEntrar.setOnKeyPressed((event) -> {
            
            try {
                AbrirHome();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

}
