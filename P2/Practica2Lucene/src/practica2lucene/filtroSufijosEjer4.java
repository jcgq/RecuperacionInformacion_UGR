package practica2lucene;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.lucene.analysis.CharacterUtils;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class filtroSufijosEjer4 extends TokenFilter{
    
    private final CharTermAttribute termAtt;
    
    public filtroSufijosEjer4(final TokenStream in) {
        super(in);
        this.termAtt = (CharTermAttribute)this.addAttribute((Class)CharTermAttribute.class);
    }
    
    @Override
    public final boolean incrementToken() throws IOException {
        if(this.input.incrementToken()){
            if(this.termAtt.length()>4){//Si el tamaño es mayor a 4, obtiene solo los últimos 4 caracteres
                char aux1 = termAtt.buffer()[termAtt.length()-1];
                char aux2 = termAtt.buffer()[termAtt.length()-2];
                char aux3 = termAtt.buffer()[termAtt.length()-3];
                char aux4 = termAtt.buffer()[termAtt.length()-4];
                termAtt.setEmpty();
                termAtt.append(aux4);
                termAtt.append(aux3);
                termAtt.append(aux2);
                termAtt.append(aux1);
                return true;
            }
            if(this.termAtt.length()<4){//Si el tamaño es menor a 4 lo elimina
                termAtt.setEmpty();
            }
            return true;//Si el tamaño es 4 directamente lo aceptará
        }
        return false;
    }
}