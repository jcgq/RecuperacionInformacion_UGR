
package practicari;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.collections4.iterators.ReverseListIterator;
import org.apache.tika.exception.TikaException;
import org.apache.tika.sax.Link;
import org.xml.sax.SAXException;


public class PracticaRI{
    
public static ArrayList<String> conseguirRuta(String dir){
     File fichero = null;
     ArrayList<String> rutas = new ArrayList<String>();
     String[] rutas_aux = null;

     try{
       //Con esta funcion obtenemos todos los ficheros que se encuentra dentro de la carpeta a examinar
       fichero = new File(dir);

       String aux = fichero.getAbsolutePath();//Obtenemos la tura absoluta de donde se encuentran (FUNCION YA DADA)
       rutas_aux = fichero.list();
       rutas.add(aux);
       for(String p : rutas_aux){
    	   rutas.add(p);//Los guardamos en la variable rutas
       }
       //System.out.println(rutas.toString());
     } catch (Exception e){
        e.printStackTrace();//Sirve para mostrar la excepción por el try
     }
     return rutas;
   }
	
  public static void imprimeDatos(ArrayList<claseFichero> ficheros) throws FileNotFoundException, UnsupportedEncodingException {
    //Para imprimir los datos en forma de tabla  
	  System.out.println("| NOMBRE | IDIOMA | CODIFICACION | TIPO |");
	  System.out.println("|--------|--------|---------|---------|");
	  for(int i=0; i<ficheros.size(); i++){
	   	System.out.println(" | "+ ficheros.get(i).getNombre()+" | "+ ficheros.get(i).getLenguaje()+
				" | "+ ficheros.get(i).getCodificacion() +" | " + ficheros.get(i).getTipo()+" | " );
                System.out.println("|------------|------------|--------------|--------------|");
	  }
  }
  
  public static void imprimeLinks(ArrayList<claseFichero> ficheros) throws FileNotFoundException, UnsupportedEncodingException {
	  for(int i=0; i<ficheros.size(); i++) {
	  	List<Link> links = ficheros.get(i).getLinks();
	  	for(Link enlace : links)
	  		System.out.println(enlace.toString());
	  }	
  }
  
  public static void cuentaPalabras(ArrayList<claseFichero> ficheros) throws FileNotFoundException, UnsupportedEncodingException {
        File archivoAux = new File("CSV");
        String ruta = archivoAux.getAbsolutePath();
        ruta=ruta+"\\";
	  for(int i=0; i<ficheros.size(); i++) {
		  PrintWriter writer = new PrintWriter(ruta+ficheros.get(i).getNombre()+ ".csv", "UTF-8");
                  System.out.println(ruta+ficheros.get(i).getNombre()+ ".csv");
		  Iterator it = new ReverseListIterator(ficheros.get(i).lista);
                  writer.println("Text/Size");
		  while(it.hasNext()) {
                        String valor=it.next().toString();//Al usar la función de una librería. Nos añade las palabras separadas por un =
                        String aux=valor.replace('=', ';');
                        if(aux.charAt(0)!=';'){//Eliminamos el espacio, que lo cuenta como caracter. Ya que se encuentra dentro del Código ASCII
                            writer.println(aux);
                        }
		  }
		  writer.close();
	  }
  }

  public static void main(String[] args) throws IOException, SAXException, TikaException{
    boolean continuar = true;
    
    ArrayList<claseFichero> ficheros = new ArrayList<claseFichero>();
	try {
	    ArrayList<String> rutas = conseguirRuta("documentos");//Obtenemos todas las rutas de los ficheros))
           
            for(int i=1; i<rutas.size(); i++){//Obtenemos los ficheros, con las rutas
                claseFichero f = new claseFichero(rutas.get(0) + "/" + rutas.get(i));
	    	ficheros.add(f);
	    }
            while(continuar) {
	    	Scanner sc = new Scanner(System.in);
	    	System.out.println("Escriba -d para obtener la información de los archivos");
                System.out.println("Escriba -l para obtener todos los enlaces que se encuentren");
                System.out.println("Escriba -t para obtener los archivos con el recuento de palabras");
                System.out.println("Para salir pulse cualquier tecla");
	    	String eleccion = sc.next();
	    	
                if(eleccion.equals("-d")) {
	    	    imprimeDatos(ficheros);
	    	}
	    	else{
                    if(eleccion.equals("-l")) {
	    	    imprimeLinks(ficheros);
	    	}
                    else{
                        if(eleccion.equals("-t")) {
	    	    cuentaPalabras(ficheros);
	    	}
                        else{
                            continuar = false;
                        }	
                    }
		}
            }
        }        
	catch (Exception e) //Error propieo del TRY
    {
        System.err.println("Error:" + e.getMessage());
                    e.printStackTrace();
    }
	  

  }
}

