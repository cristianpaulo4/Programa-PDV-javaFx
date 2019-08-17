
package programaHome;
import TelaFonecedor.*;
import java.io.IOException;
import Login.*;
import Objetos.*;
import java.util.ArrayList;
import TelaCaixa.*;
import Objetos.*;
import com.sun.prism.impl.PrismSettings;



public class teste {
   
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Caixa caixa = new Caixa();
        
        caixa=caixa.BuscarCaixaPorData();
                   
        System.out.println(caixa.getValorInicial());
        //Home.setAltorizado(true);
        
        
        System.out.println(Home.teste);
     
        
    }
    
    
}
