//Hemos realizado esta clase, para facilitar e individualizar cada uno de los ficheros que se examinarán.
//Nos ha resultado más fácil y sencillo para luego poder trabajar con ellos.
package practicari;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.sax.TeeContentHandler;

public class claseFichero{
  String nombre;
  ArrayList<String> metadatos;
  String lenguaje;
  String tipo; //Si es pdf, word, pwp...
  String codificacion;
  String contenido;
  List<Link> links;
  static HashMap<String,Integer> recuento_palabras; //Mapa para almacenar las palabras y el número de repeticiones
  List lista;

public claseFichero(String ruta) throws IOException, SAXException, TikaException{//Constructor
    Tika tika = new Tika();
    File f = new File(ruta);
    nombre = f.getName();
    tipo = tika.detect(f);
    codificacion = asignarCodificacion(f,tika);
    metadatos = new ArrayList<String>();
    links = new ArrayList<Link>();
    recuento_palabras = new HashMap<String, Integer>();
    getMetadatos(f);
    cuentaPalabras();
    lenguaje = identificadorDeLenguaje(contenido);
    lista = ordena();
}

  private String asignarCodificacion(File f, Tika tika) throws FileNotFoundException{
	FileInputStream inputstream = new FileInputStream(f);//Clase obtiene los bytes de lectura de un fichero. Es ideal para la lectura de streams de datos de ficheros
	AutoDetectReader detector = null;
	try {
		detector = new AutoDetectReader(inputstream);//Un lector de flujo de entrada que detecta automáticamente la codificación de caracteres que se utilizará para convertir los bytes en caracteres.
	} catch (IOException e) {//Para cada entrada, su errror del try
		e.printStackTrace();
	} catch (TikaException e) {
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

  public String getLenguaje(){
    return lenguaje;
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
  
  private String identificadorDeLenguaje(String text){
      //Usamos las funcione que nos permite esta librería, para obtener el lenguaje de un texto
	LanguageDetector identificadorLenguaje  = new  OptimaizeLangDetector().loadModels();//Clase para facilitar la extracción del lenguaje
        LanguageResult idioma = identificadorLenguaje.detect(text);//Función que devuelve el lenguaje del texto
        return idioma.getLanguage();
  }

  private void cuentaPalabras() {
	  String[] cadenaTexto = contenido.split("\\s");//Cada vez que aparezca un espacio, con la funcion split, conseguimos que se introduzca un intro
	  for(String st : cadenaTexto) {
            st=st.replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚÜüñÑàìòùÀÈÌÒÙ]", "");//Eliminamos todo lo que no sea relacionado con palabras
		  if(recuento_palabras.containsKey(st)) {
			int num = recuento_palabras.get(st) + 1; //Sino al encontrar solo una palabra, el resultado era de 0
			recuento_palabras.put(st, num);
		  }
		  else
			recuento_palabras.put(st, 1);
	  }
  }
  
  private static List ordena() {//Usamos la clase List, para ordenar las palabras de forma decreciente
	  List lista = new LinkedList(recuento_palabras.entrySet());//La función ya viene prácticamente hecha
	  Collections.sort(lista, new Comparator() {
		  	public int compare(Object o1, Object o2) {
		  		return ((Comparable) (((Map.Entry) (o1)).getValue())).compareTo(((Map.Entry) (o2)).getValue());
		  	}
	  });
	  
	  return lista;
  }
  
  public HashMap<String, Integer> getPalabras(){
	  return recuento_palabras;
  }

  
}
