
package indexacion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.document.Document;
import static busqueda.funcionesBusqueda.conseguirRuta;
import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;


public class indexar {

    public static void main(String[] args) throws IOException {
        
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        File archivoAAniadir;
 
        while (!salir) {
 
            System.out.println("1. Indexar colección completa");
            System.out.println("2. Añadir un archivo al índice");
            System.out.println("3. Salir");
 
            try {
 
                System.out.println("Escribe una de las opciones");
                opcion = sn.nextInt();
 
                switch (opcion) {
                    case 1:
                        int contador=0;
                        Indice indiceLucene = new Indice();
                        ArrayList<String> rutas = conseguirRuta("pdf_json");
                        System.out.println("El tamaño de las rutas es " + rutas.size());
                        for(int i=1; i<rutas.size(); i++){
                            String rutaaux = rutas.get(0) + "\\" + rutas.get(i);
                            byte[] jsonData = Files.readAllBytes(Paths.get(rutaaux));
                            ObjectMapper objectMapper = new ObjectMapper();
                            JsonNode rootNode = objectMapper.readTree(jsonData);
                            Ficovid archivo = new Ficovid(rootNode);
                            contador++;
                            System.out.println("Creando archivo e indexando" + contador);
                            indiceLucene.indexarNuevoDocumento(archivo);
                        }
                        indiceLucene.closeIndex();

                        
                        
                        break;
                    case 2:
                       final JFrame frame = new JFrame();
                       frame.setVisible(true);
                       frame.setExtendedState(JFrame.ICONIFIED);
                       frame.setExtendedState(JFrame.NORMAL);
                       JFileChooser fc = new JFileChooser();
                       fc.addChoosableFileFilter(new FileNameExtensionFilter("json", "json"));
                       fc.setAcceptAllFileFilterUsed(false);
                       if(JFileChooser.APPROVE_OPTION==fc.showOpenDialog(null)){
                            frame.setVisible(false);
                            archivoAAniadir = fc.getSelectedFile();
                            byte[] jsonData = Files.readAllBytes(Paths.get(archivoAAniadir.getAbsolutePath()));
                            ObjectMapper objectMapper = new ObjectMapper();
                            JsonNode rootNode = objectMapper.readTree(jsonData);
                            Ficovid archivo = new Ficovid(rootNode);
                            Indice indiceAux = new Indice();
                            indiceAux.indexarNuevoDocumento(archivo);
                            archivoAAniadir.renameTo(new File("./pdf_json/" + archivoAAniadir.getName()));
                            indiceAux.closeIndex();
                       }
                       else{
                           System.out.println("Nada fue seleccionado. Por favor, inténtelo de nuevo");
                       }
                       frame.dispose();
                       
                        break;
                    case 3:
                        salir = true;
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 3");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                sn.next();
            }
        }
    }
}

