package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conecta {

     
    private final String url="jdbc:mysql://localhost:3306/MercadoBD";
    private final String usuario = "cristian1";
    private final String senha = "nunes@142536";
   
    
   
    // conexao 
    public Connection conectar(){       
              
        try {
            Connection con = DriverManager.getConnection(url, usuario, senha);
            //JOptionPane.showMessageDialog(null, "Conectado");
            return con;                    

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar:\n" + ex);
        }

        return null;

    }
    
    
    
    
    

}
