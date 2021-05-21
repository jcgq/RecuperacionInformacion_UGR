
package practica2lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.xml.sax.SAXException;
import org.apache.tika.sax.TeeContentHandler;

public final class claseEjercicio4{
  String nombre;
  String contenido;
  String texto;

public claseEjercicio4(String ruta) throws IOException, SAXException, TikaException{//Constructor
    Tika tika = new Tika();
    File f = new File(ruta);
    nombre = f.getName();
    getMetadatos(f);
    texto="";
    analizadorSufijos();
}


  private void getMetadatos(File f) throws FileNotFoundException, IOException, SAXException, TikaException{
	//Ayudándonos del pdf que ha entregado el profesor, hemos creado esta función (adaptándola a nuestra idea) para obtener los metadatos
	FileInputStream inputstream = new FileInputStream(f);
	Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(-1);
        LinkContentHandler linkHandler = new LinkContentHandler();
        TeeContentHandler teeHandler = new TeeContentHandler(linkHandler,handler);
        Metadata metadato = new Metadata();
        ParseContext context = new ParseContext();
        parser.parse(inputstream, teeHandler, metadato, context);
        inputstream.close();
        contenido = handler.toString();
  }
  
  public String getNombre(){
    return nombre;
  }


  public String getTexto(){
      return texto;
  }

  
  public void analizadorSufijos() throws IOException{
      Analyzer an = new SimpleAnalyzer();
      try (TokenStream stream = an.tokenStream(null, contenido)) {
          stream.reset();
          List<String> cadenaTexto = Analizador.tokenizeString(new AnalizadorSufijoEjer4(), contenido);
          cadenaTexto.forEach((st) -> {
              if(!st.isEmpty()){
                  texto+=st.toString()+" ";
              }
            });
          stream.end();
      }
  }
  
}