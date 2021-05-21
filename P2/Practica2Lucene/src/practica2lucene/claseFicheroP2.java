//Hemos realizado esta clase, para facilitar e individualizar cada uno de los ficheros que se examinarán.
//Nos ha resultado más fácil y sencillo para luego poder trabajar con ellos.
package practica2lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;

import org.apache.tika.Tika;
import org.apache.tika.detect.AutoDetectReader;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.Link;
import org.apache.tika.sax.LinkContentHandler;
import org.xml.sax.SAXException;
import org.apache.tika.sax.TeeContentHandler;

public final class claseFicheroP2{
  String nombre;
  ArrayList<String> metadatos;
  String lenguaje;
  String tipo; //Si es pdf, word, pwp...
  String texto;
  String codificacion;
  String contenido;
  List<Link> links;
  static HashMap<String,Integer> recuento_palabras_simple; //Mapa para almacenar las palabras y el número de repeticiones
  static HashMap<String,Integer> recuento_palabras_white; //Mapa para almacenar las palabras y el número de repeticiones
  List lista_simple;
  List lista_white;

public claseFicheroP2(String ruta) throws IOException, SAXException, TikaException{//Constructor
    Tika tika = new Tika();
    File f = new File(ruta);
    nombre = f.getName();
    tipo = tika.detect(f);
    codificacion = asignarCodificacion(f,tika);
    metadatos = new ArrayList<>();
    links = new ArrayList<>();
    recuento_palabras_simple = new HashMap<>();
    recuento_palabras_white = new HashMap<>();
    getMetadatos(f);
    analizadorSimple();
    analizadorWhite();
    lista_simple = ordenaSimple();
    lista_white = ordenaWhite();
    texto=null;
}

  private String asignarCodificacion(File f, Tika tika) throws FileNotFoundException{
	FileInputStream inputstream = new FileInputStream(f);//Clase obtiene los bytes de lectura de un fichero. Es ideal para la lectura de streams de datos de ficheros
	AutoDetectReader detector = null;
	try {
		detector = new AutoDetectReader(inputstream);//Un lector de flujo de entrada que detecta automáticamente la codificación de caracteres que se utilizará para convertir los bytes en caracteres.
	} catch (IOException | TikaException e) {//Para cada entrada, su errror del try
		e.printStackTrace();
	}
    Charset codificacion = detector.getCharset(); //Usamos la clase CharSet, para almacenar mejor la codificacion
    return codificacion.toString();
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
        links = linkHandler.getLinks();
        String[] nombres = metadato.names();
        String aux;
        for(String name : nombres){
            aux = name + "; " + metadato.get(name);
            metadatos.add(aux);
        }
  }
  
  public String getNombre(){
    return nombre;
  }

  public String getTipo(){
    return tipo;
  }

  public String getCodificacion(){
    return codificacion;
  }

  public String getContenido(){
    return contenido;
  }

  public ArrayList<String> getMetadatos(){
    return metadatos;
  }
  
  public List<Link> getLinks(){
	  return links;
  }

  public String getTexto(){
      return texto;
  }
  
  public void analizadorSimple() throws IOException{
        Analyzer an = new SimpleAnalyzer();
      try (TokenStream stream = an.tokenStream(null, contenido)) {
          stream.reset();
          List<String> cadenaTexto = Analizador.tokenizeString(new SimpleAnalyzer(), contenido);
          cadenaTexto.forEach((st) -> {
              if(recuento_palabras_simple.containsKey(st)) {
                  int num = recuento_palabras_simple.get(st) + 1;
                  recuento_palabras_simple.put(st, num);
              }
              else
                  recuento_palabras_simple.put(st, 1);
            });
          stream.end();
      }
  }
  
  public void analizadorWhite() throws IOException{
        Analyzer an = new WhitespaceAnalyzer();
      try (TokenStream stream = an.tokenStream(null, contenido)) {
          stream.reset();
          List<String> cadenaTexto = Analizador.tokenizeString(new WhitespaceAnalyzer(), contenido);
          cadenaTexto.forEach((st) -> {
              if(recuento_palabras_white.containsKey(st)) {
                  int num = recuento_palabras_white.get(st) + 1;
                  recuento_palabras_white.put(st, num);
              }
              else
                  recuento_palabras_white.put(st, 1);
            });
          stream.end();
      }
  }  
  
  private static List ordenaSimple() {//Usamos la clase List, para ordenar las palabras de forma decreciente
	  List lista = new LinkedList(recuento_palabras_simple.entrySet());//La función ya viene prácticamente hecha
	  Collections.sort(lista, (Object o1, Object o2) -> ((Comparable) (((Map.Entry) (o1)).getValue())).compareTo(((Map.Entry) (o2)).getValue()));
	  
	  return lista;
  }
  
   private static List ordenaWhite() {//Usamos la clase List, para ordenar las palabras de forma decreciente
	  List lista = new LinkedList(recuento_palabras_white.entrySet());//La función ya viene prácticamente hecha
	  Collections.sort(lista, (Object o1, Object o2) -> ((Comparable) (((Map.Entry) (o1)).getValue())).compareTo(((Map.Entry) (o2)).getValue()));
	  
	  return lista;
  }
  
  
  public HashMap<String, Integer> getPalabrasSimple(){
	  return recuento_palabras_simple;
  }
  public HashMap<String, Integer> getPalabrasWhite(){
	  return recuento_palabras_white;
  }
  
}
