package TelaFonecedor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Objetos.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class RepresentanteController implements Initializable {
    static Stage stage = new Stage();
    Representante representante = new Representante();
    @FXML
    private TextField cxNome;
    @FXML
    private TextField cxEmail;
    @FXML
    private TextField cxTel1;
    @FXML
    private TextField cxTel2;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        
        
    }    
    
    // abrir Tela
    public void AbrirTela() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Representante.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);        
        stage.show();
    }

    
    // fechar Tela
    @FXML
    public void fecharTela(){
        cadRepresentante();
        stage.close();
        
    }
    
    
    public void cadRepresentante(){
        representante.setNome(cxNome.getText());
        representante.setEmail(cxEmail.getText());
        representante.setTelefone1(cxTel1.getText());
        representante.setTelefone2(cxTel2.getText());
        representante.CadRepresentante(representante);
    }
    
    
    
}
