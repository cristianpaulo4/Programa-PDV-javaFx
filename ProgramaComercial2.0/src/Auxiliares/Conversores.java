
package Auxiliares;

import java.text.DecimalFormat;


public class Conversores {
    

public static String converterDoubleString(double precoDouble) {
    /*Transformando um double em 2 casas decimais*/
    DecimalFormat fmt = new DecimalFormat("0.00");   //limita o número de casas decimais    
    String string = fmt.format(precoDouble);
    String[] part = string.split("[,]");
    String preco = part[0]+"."+part[1];
    return preco;
}
 
public static double converterDoubleDoisDecimais(double precoDouble) {
    DecimalFormat fmt = new DecimalFormat("0.00");      
    String string = fmt.format(precoDouble);
    String[] part = string.split("[,]");
    String string2 = part[0]+"."+part[1];
        double preco = Double.parseDouble(string2);
    return preco;
}

}