package Balanco;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class BalancoController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    
    
    // abrir balanço
    public Parent tela() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Balanco.fxml"));
        return root;
    }

}
