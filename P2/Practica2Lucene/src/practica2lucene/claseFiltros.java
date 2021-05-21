
package practica2lucene;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.commongrams.CommonGramsFilter;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import static org.apache.lucene.analysis.standard.ClassicAnalyzer.STOP_WORDS_SET;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.CharsRef;
import org.tartarus.snowball.ext.SpanishStemmer;
import static practica2lucene.PracticaRI.lector;



public class claseFiltros {
    String texto;

    public claseFiltros(String _texto) {
        texto = _texto;
    }
    
    //Dado un filtro, lo aplica y añade el resultado a un String   
    public String convertirStreamAString(TokenStream stream) throws IOException {
        stream.reset();
        
        String st = "";
        while(stream.incrementToken()){
            if(!stream.getAttribute(CharTermAttribute.class).toString().isEmpty()){
                st += stream.getAttribute(CharTermAttribute.class) + " ";
            } 
        }
        stream.end();
        stream.close();
        
        return st;
    }
    
    
    public ArrayList<String> usarFiltros() throws IOException {
        ArrayList<String> filtros = new ArrayList();

        Tokenizer source = new StandardTokenizer();
        source.setReader(new StringReader(texto));
        
        
        File archivoPrueba = new File("textoPrueba.txt");
        String textoPrueba = archivoPrueba.getAbsolutePath().toString();
        StringBuilder textoProbando = lector(textoPrueba);
        
        //Elimina las palabras vacías.
        filtros.add("StopFilter -> "+convertirStreamAString(new StopFilter(source, STOP_WORDS_SET)));
        source.setReader(new StringReader(texto));
        
        //Pone en minúsculas todas las palabras.
        filtros.add("LowerCaseFilter -> "+convertirStreamAString(new LowerCaseFilter(source)));
        source.setReader(new StringReader(texto));
      
        
        //Coge palabras en pares (Explicado con más detalle en el pdf)
        filtros.add("ShingleFilter -> "+convertirStreamAString(new ShingleFilter(source, 2)));
        source.setReader(new StringReader(texto));
        
        //Añade sinonimos dado un mapa de sinonimos
        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        builder.add(new CharsRef("cañones"), new CharsRef("(disparadores)"), true);
        builder.add(new CharsRef("barco"), new CharsRef("(velero)"), true);
        builder.add(new CharsRef("diez"), new CharsRef("(10)"), true);
        builder.add(new CharsRef("mar"), new CharsRef("(playa)"), true);
        
        SynonymMap map = builder.build();
        filtros.add("SynonymFilter -> "+convertirStreamAString(new SynonymFilter(source, map, true)));
        source.setReader(new StringReader(texto));
        
        //Realiza un proceso de Stemming con un conjunto de palabras en español.
        filtros.add("SnowballFilter -> "+convertirStreamAString(new SnowballFilter(source, new SpanishStemmer())));
        source.setReader(new StringReader(texto));
        
        //Filtros cuyo funcionamiento se explica en el pdf
        filtros.add("CommonGramsFilter -> "+convertirStreamAString(new CommonGramsFilter(source, STOP_WORDS_SET)));
        source.setReader(new StringReader(texto));
        filtros.add("NGramTokenFilter -> "+convertirStreamAString(new NGramTokenFilter(source,6)));
        source.setReader(new StringReader(texto));
        filtros.add("EdgeNGramTokenFilter -> "+convertirStreamAString(new EdgeNGramTokenFilter(source,3)));
 
        return filtros;
    }
    
    public String filtroEjer3() throws IOException{
        String st = "";
        
        Analyzer ana = CustomAnalyzer.builder(Paths.get("."))
        .withTokenizer(StandardTokenizerFactory.class)
        .addTokenFilter(LowerCaseFilterFactory.class)//Convertimos todas las palabras a minúscula
        .addTokenFilter(StopFilterFactory.class, "ignoreCase", "false", "words", "stopwords.txt", "format", "wordset")//Eliminamos las palabras que se encuentran en el txt
        .build();
        
        TokenStream stream = ana.tokenStream (null, new StringReader(texto));
        stream.reset();
        
        while (stream.incrementToken())
            st += stream.getAttribute(CharTermAttribute.class) + " ";
                
        stream.end();
        stream.close();         
         
        return (st);
    }
    
}
