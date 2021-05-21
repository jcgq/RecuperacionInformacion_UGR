
package practica2lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;


import org.apache.commons.collections4.iterators.ReverseListIterator;
import org.apache.tika.exception.TikaException;
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
     } catch (Exception e){
        e.printStackTrace();//Sirve para mostrar la excepción por el try
     }
     return rutas;
   }



public static StringBuilder lector(String archivo) {//Para extraer un texto y convertirlo en String (Funcion de la asignatura de mp)
    StringBuilder sb = new StringBuilder();
    String linea;
    File f = new File(archivo);
    FileReader fr;
    BufferedReader br = null;

    try {
        try {
            fr = new FileReader(f);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                sb.append(linea);
                sb.append('\n');
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("Archivo no encontrado");
    } catch (IOException e) {
        System.out.println("Error entrada/salida");
    }

    return sb;
}
  
  public static void cuentaPalabrasP2(ArrayList<claseFicheroP2> ficheros) throws FileNotFoundException, UnsupportedEncodingException {
        File archivoAux = new File("CSVP2");
        String ruta = archivoAux.getAbsolutePath();
        ruta=ruta+"\\";
	  for(int i=0; i<ficheros.size(); i++) {
		  PrintWriter writer = new PrintWriter(ruta+ficheros.get(i).getNombre()+ "P2_simple.csv", "UTF-8");//For para el analizador simple
		  Iterator it = new ReverseListIterator(ficheros.get(i).lista_simple);
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
          
          for(int i=0; i<ficheros.size(); i++) {
		  PrintWriter writer = new PrintWriter(ruta+ficheros.get(i).getNombre()+ "P2_white.csv", "UTF-8");//For para el analizador white
		  Iterator it = new ReverseListIterator(ficheros.get(i).lista_white);
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

  public static void main(String[] args) throws IOException, TikaException, FileNotFoundException, SAXException{
    File archivoPrueba = new File("textoPrueba.txt");
    String textoPrueba = archivoPrueba.getAbsolutePath().toString();
    StringBuilder textoProbando = lector(textoPrueba);
    
    ArrayList<claseFicheroP2> ficheros = new ArrayList<claseFicheroP2>();
	try {
             ArrayList<String> rutas = conseguirRuta("documentos");//Obtenemos todas las rutas de los ficheros))
            for(int i=1; i<rutas.size(); i++){//Obtenemos los ficheros, con las rutas
                claseFicheroP2 f = new claseFicheroP2(rutas.get(0) + "/" + rutas.get(i));
	    	ficheros.add(f);
	    }
            cuentaPalabrasP2(ficheros);
            claseFiltros tf = new claseFiltros(textoProbando.toString());
            ArrayList<String> filtros = new ArrayList();
            filtros = tf.usarFiltros();
            
            System.out.println("Ejercicio 2");
            for(String f : filtros)
                System.out.println(f.toString() + "\n");
           
            String str = tf.filtroEjer3();
            System.out.println ("\n\nEjercicio 3: \n" + str);        
            
                
            System.out.println("\n\nEjercicio 4: ");
            claseEjercicio4 f = new claseEjercicio4("textoPrueba.txt");
            String ejer4= f.getTexto();
            System.out.println("El texto aplicando el analizador del Ejercicio 4 será");
            System.out.println(ejer4);
            
        }        
	catch (Exception e){
            System.err.println("Error:" + e.getMessage());
                        e.printStackTrace();
        }
	  

  }
}

