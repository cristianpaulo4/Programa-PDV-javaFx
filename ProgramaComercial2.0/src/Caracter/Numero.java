package Caracter;

import javafx.scene.control.TextField;

public class Numero extends TextField{

    @Override
    public void replaceText(int start, int end, String text) {
        if(text.matches("[0-9]") || text.isEmpty()){
            super.replaceText(start, end, text);            
        }
    }
    
}
