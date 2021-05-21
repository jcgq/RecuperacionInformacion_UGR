
package indexacion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;


public class Indice {
    private static final String rutaIndice = "./indice";
    private static final String rutaFaceta = "./faceta";
    private HashMap<String, Analyzer> analyzerPerfield = new HashMap<String, Analyzer>();
    public static PerFieldAnalyzerWrapper miAnalyzer = null;
    private IndexWriter writer;
    private DirectoryTaxonomyWriter taxoWriter = null;
    FacetsConfig fconfig = null;
    
    public static String[] sinRepetir(String[]a){ //Esta funcion sirve para eliminar los elementos repetidos de las búsquedas
       Arrays.sort(a);                            //Cuando muestras los paises puedes tener alamcenado: Spain Spain Desconocido Desconocido...
       int len = a.length;                        //Y nos interesa mostrarlo al usuario de esta forma: Spain Desconocido Germany
       int j = 0;
       for(int i=0;i<len-1;i++){
           if(!a[i].equals(a[i+1])){
              a[j++] = a[i];
           }
       }
       a[j++] = a[len-1];
       String [] c = new String[j];
       for(int k = 0;k<j;k++){
           c[k] = a[k];
       }
       return c;
       }
    
    public Indice() throws IOException{
        
        //Asignamos a cada campo su analizador correspondiente
        Analyzer analizador = new SimpleAnalyzer();
        analyzerPerfield.put("autores", analizador);
        analyzerPerfield.put("paises", analizador);
        analyzerPerfield.put("titulo", new StandardAnalyzer());
        analyzerPerfield.put("universidad", analizador);
        analyzerPerfield.put("lugar", analizador);
        analyzerPerfield.put("texto", new EnglishAnalyzer());
        
        miAnalyzer = new PerFieldAnalyzerWrapper(new WhitespaceAnalyzer(), analyzerPerfield);
        
        //Establecemos los directorios donde se crearán los distintos indices y facetas
        FSDirectory dir = FSDirectory.open(Paths.get(rutaIndice));
        FSDirectory taxoDir = FSDirectory.open(Paths.get(rutaFaceta));
        IndexWriterConfig config = new IndexWriterConfig(miAnalyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        
        //Configuramos el taxo para las facetas
        fconfig = new FacetsConfig();
        fconfig.setMultiValued("autores", true);
        fconfig.setMultiValued("paises", true);
        fconfig.setMultiValued("universidad", true);
        fconfig.setMultiValued("lugar", true);
        
        
        writer = new IndexWriter(dir, config);
        taxoWriter = new DirectoryTaxonomyWriter(taxoDir);
    }
    
    public void closeIndex() throws IOException{
        //Sobreeescribe los indices y cierra los archivos
        try{
            writer.commit();
            writer.close();
            taxoWriter.commit();
            taxoWriter.close();
        }catch(IOException e){
            System.out.println("ERROR IN CLSE INDEX " + e);
        }
    }
    
    public void indexarNuevoDocumento(Ficovid file) throws IOException{
        Document doc = new Document();
        
            doc.add(new StringField("id_json", file.getId(), Field.Store.YES));
            doc.add(new TextField("titulo", file.getMetadata().getTitulo(), Field.Store.YES));

            for(int i=0; i<file.getAutores().size();i++){
                doc.add(new TextField("autores", file.getAutores().get(i).toString(), Field.Store.YES));
                doc.add(new TextField("paises", file.getAutores().get(i).getLocalizacion().getPais(), Field.Store.YES));
                doc.add(new TextField("universidad",file.getAutores().get(i).getInstitucion(), Field.Store.YES));
                doc.add(new TextField("lugar", file.getAutores().get(i).getLocalizacion().getLugar(), Field.Store.YES));
                
                //Añadimos las facetas
                doc.add(new FacetField("autores", file.getAutores().get(i).toString()));
                doc.add(new FacetField("paises", file.getAutores().get(i).getLocalizacion().getPais()));
                doc.add(new FacetField("universidad", file.getAutores().get(i).getInstitucion()));
                doc.add(new FacetField("lugar", file.getAutores().get(i).getLocalizacion().getLugar()));
            } 
            int longitud = file.getCuerpo().getTexto().length();
            doc.add(new SortedDocValuesField("sorted", new BytesRef(Integer.toString(10).getBytes())));
            doc.add(new TextField("texto", file.getCuerpo().getTexto(), Field.Store.YES));
            
            doc.add(new NumericDocValuesField("rangos", longitud));
            
            doc.add(new IntPoint("longitud",longitud));
            doc.add(new StoredField("longitud", longitud));
            doc.add(new FacetField("longitud", Integer.toString(longitud)));
            writer.addDocument(fconfig.build(taxoWriter, doc));
    }
    
    
    //Función para indexar socumentos
    public void indexarDocumentos( ArrayList<Ficovid> ficheros) throws IOException{
        
        for(Ficovid p : ficheros){
     
            Document doc = new Document();
            doc = new Document();
            doc.add(new StringField("id_json", p.getId(), Field.Store.YES));
            doc.add(new TextField("titulo", p.getMetadata().getTitulo(), Field.Store.YES));

            for(int i=0; i<p.getAutores().size();i++){
                doc.add(new TextField("autores", p.getAutores().get(i).toString(), Field.Store.YES));
                doc.add(new TextField("paises", p.getAutores().get(i).getLocalizacion().getPais(), Field.Store.YES));
                doc.add(new TextField("universidad", p.getAutores().get(i).getInstitucion(), Field.Store.YES));
                doc.add(new TextField("lugar", p.getAutores().get(i).getLocalizacion().getLugar(), Field.Store.YES));
                
                //Añadimos las facetas
                doc.add(new FacetField("autores", p.getAutores().get(i).toString()));
                doc.add(new FacetField("paises", p.getAutores().get(i).getLocalizacion().getPais()));
                doc.add(new FacetField("universidad", p.getAutores().get(i).getInstitucion()));
                doc.add(new FacetField("lugar", p.getAutores().get(i).getLocalizacion().getLugar()));
            } 
            int longitud = p.getCuerpo().getTexto().length();
            doc.add(new SortedDocValuesField("sorted", new BytesRef(Integer.toString(10).getBytes())));
            doc.add(new TextField("texto", p.getCuerpo().getTexto(), Field.Store.YES));
            
            doc.add(new NumericDocValuesField("rangos", longitud));
            
            doc.add(new IntPoint("longitud",longitud));
            doc.add(new StoredField("longitud", longitud));
            doc.add(new FacetField("longitud", Integer.toString(longitud)));
            writer.addDocument(fconfig.build(taxoWriter, doc));
            
        }
        
        closeIndex();
    }
    
    //Función para extraer de un Token el valor String (De la práctica 1)
    public static String convertirStreamAString(TokenStream stream) throws IOException {
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
    
}
