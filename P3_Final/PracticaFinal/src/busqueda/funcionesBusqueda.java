

package busqueda;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import static indexacion.Indice.sinRepetir;
import indexacion.indexar;
import interfaz.interfaz;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.facet.DrillDownQuery;
import org.apache.lucene.facet.DrillSideways;
import org.apache.lucene.facet.DrillSideways.DrillSidewaysResult;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.LabelAndValue;
import org.apache.lucene.facet.range.LongRange;
import org.apache.lucene.facet.range.LongRangeFacetCounts;
import org.apache.lucene.facet.taxonomy.FastTaxonomyFacetCounts;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TotalHits;


public class funcionesBusqueda{
    private static FacetsConfig fconfig = new FacetsConfig();
    private static final String rutaIndice = "./indice";
    private static final String rutaFaceta = "./faceta";
    private static FSDirectory indexDir;
    private static FSDirectory taxoDir;
    private static int nDocs = 0;
    
    //Funcion de la primera practica para obtener las rutas de los ficheros a indexar
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
    
    
      public static String buscadorFrases(Analyzer analyzer, Similarity similarity, String campo, String consulta){
        IndexReader reader = null;
        String salida="";
        try{
            reader = DirectoryReader.open(FSDirectory.open(Paths.get(rutaIndice)));
            IndexSearcher searcher = new IndexSearcher(reader);
            searcher.setSimilarity(similarity);
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            Analyzer ana = new WhitespaceAnalyzer();
            PhraseQuery.Builder builder = new PhraseQuery.Builder();
            //Sirve para asignar el analizador que se usará en la consulta
            if(campo.contentEquals("autores") || campo.contentEquals("titulo") || campo.contentEquals("paises") || campo.contentEquals("universidad")){
                ana = new SimpleAnalyzer();
            }
            else{
                if(campo.contentEquals("texto")){
                    ana = new EnglishAnalyzer();
                }
            }
            //Sirve para coger los términos de la frase y convertirlos en un N-Grama (De String a Stream)
            TokenStream stream = ana.tokenStream(null, consulta);
            stream.reset();
            while(stream.incrementToken()){
                String palabra = "";
                palabra+= stream.getAttribute(CharTermAttribute.class);
                Term termino = new Term(campo, palabra);
                builder.add(termino);
            }
            stream.end();
            stream.close();
            PhraseQuery phrase = builder.build();
            SortField sf = new SortField("scored", SortField.Type.INT, true);
            sf.setMissingValue(0);
            Sort orden = new Sort(sf);
            TopDocs results = searcher.search(phrase, 10, orden);
            ScoreDoc[] hits = results.scoreDocs;

            salida = mostrarResultadosBusquedas(hits, searcher, results);
                
            
        } catch (IOException ex) {
            Logger.getLogger(indexar.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            reader.close();
        }catch(IOException e1){
            e1.printStackTrace();
        }
        return salida;
    }
      
    public static String buscadorPorTermino(Query queryCon, Analyzer analyzer, Similarity similarity, String Campo, String termino){
        IndexReader reader = null;
        String solucion = "";
        try{
            reader = DirectoryReader.open(FSDirectory.open(Paths.get(rutaIndice)));
            IndexSearcher searcher = new IndexSearcher(reader);
            searcher.setSimilarity(similarity);
            QueryParser parser = new QueryParser(Campo, new SimpleAnalyzer());

                
            Query query = null;
            try{
                query = parser.parse(termino);
                interfaz.setQuery(query);
            }catch(org.apache.lucene.queryparser.classic.ParseException e){
                System.out.println("Error en la cadena de consulta");
            }
            if(query!=null){
                System.out.println("La consulta es " + query.toString());
                TopDocs results = searcher.search(query, 10);
                ScoreDoc[] hits = results.scoreDocs;

                solucion = mostrarResultadosBusquedas(hits, searcher, results);
            }
            else{
                solucion="No hay";
            }

            
        reader.close();
            
        } catch (IOException ex) {
            Logger.getLogger(indexar.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            reader.close();
        }catch(IOException e1){
            e1.printStackTrace();
        }
        System.out.println("EN LA FUNVION queryCon es " + queryCon);
        
        return solucion;
    }
    
        public static String buscadorPorEntero(Analyzer analyzer, Similarity similarity, String Campo, int termino){
        IndexReader reader = null;
        String solucion = "";
        int entero1, entero2;
        try{
            if(termino>100){
                entero1=termino-100;
                entero2=termino+100;
            }
            else{
                entero1=termino/2;
                entero2=termino+100;
            }
            reader = DirectoryReader.open(FSDirectory.open(Paths.get(rutaIndice)));
            IndexSearcher searcher = new IndexSearcher(reader);
            searcher.setSimilarity(similarity);
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                
            Query query = null;
            //Busqueda por rango
            query = IntPoint.newRangeQuery("longitud", entero1,entero2);
            TopDocs results = searcher.search(query, 10);
            ScoreDoc[] hits = results.scoreDocs;
            solucion = mostrarResultadosBusquedas(hits, searcher, results);
            
        reader.close();
            
        } catch (IOException ex) {
            Logger.getLogger(indexar.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            reader.close();
        }catch(IOException e1){
            e1.printStackTrace();
        }
        return solucion;
    }
    
        public static String buscadorBooleano(String esta1, String esta2, String condicional, Analyzer analyzer, Similarity similarity, String campo1, String campo2, String consulta1, String consulta2){
        IndexReader reader = null;
        String solucion=null;
        try{
            reader = DirectoryReader.open(FSDirectory.open(Paths.get(rutaIndice)));
            IndexSearcher searcher = new IndexSearcher(reader);
            searcher.setSimilarity(similarity);
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            
            campo1 = campo1.toLowerCase();
            campo2 = campo2.toLowerCase();
            consulta1 = consulta1.toLowerCase();
            consulta2 = consulta2.toLowerCase();
                
            Query query1, query2;
            Term termino = new Term(campo1, consulta1);
            query1 = new TermQuery(termino);
            termino = new Term(campo2, consulta2);
            query2 = new TermQuery(termino);
            BooleanClause b1=null, b2=null;
            
            
                if(condicional.equals("and")){
                    if(esta1.equals("contenga")){
                        b1 = new BooleanClause(query1,BooleanClause.Occur.MUST);
                    }
                    else{
                        b1 = new BooleanClause(query1,BooleanClause.Occur.MUST_NOT);
                    }
                    
                    if(esta2.equals("contenga")){
                        b2 = new BooleanClause(query2,BooleanClause.Occur.MUST);
                    }
                    else{
                        b2 = new BooleanClause(query2,BooleanClause.Occur.MUST_NOT);
                    }
                    
                    BooleanQuery.Builder bBuilder = new BooleanQuery.Builder();
                    bBuilder.add(b1);
                    bBuilder.add(b2);
                    BooleanQuery bq = bBuilder.build();
                    TopDocs results = searcher.search(bq, 10);
                    ScoreDoc[] hits = results.scoreDocs;
                    solucion = mostrarResultadosBusquedas(hits, searcher, results);
                }
                else{
                    if((esta1.equals("no_contenga")) && (esta2.equals("no_contenga"))){
                        solucion=buscadorEspecialBooleano(analyzer, similarity, campo1, consulta1);
                        solucion+=buscadorEspecialBooleano(analyzer, similarity, campo2, consulta2);
                    }
                    else{
                        if(esta1.equals("contenga")){
                            b1 = new BooleanClause(query1,BooleanClause.Occur.SHOULD);
                        }
                        else{
                            b1 = new BooleanClause(query1,BooleanClause.Occur.MUST_NOT);
                        }

                        if(esta2.equals("contenga")){
                            b2 = new BooleanClause(query2,BooleanClause.Occur.SHOULD);
                        }
                        else{
                            b2 = new BooleanClause(query2,BooleanClause.Occur.MUST_NOT);
                        }
                        
                        BooleanQuery.Builder bBuilder = new BooleanQuery.Builder();
                        bBuilder.add(b1);
                        bBuilder.add(b2);
                        BooleanQuery bq = bBuilder.build();
                        TopDocs results = searcher.search(bq, 10);
                        ScoreDoc[] hits = results.scoreDocs;
                        solucion = mostrarResultadosBusquedas(hits, searcher, results);
                    }
                }       
            
        } catch (IOException ex) {
            Logger.getLogger(indexar.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            reader.close();
        }catch(IOException e1){
            e1.printStackTrace();
        }
        return solucion;
    }
        
        
    public static String buscadorEspecialBooleano(Analyzer analyzer, Similarity similarity, String campo, String consulta){
        IndexReader reader = null;
        String solucion=null;
        try{
            reader = DirectoryReader.open(FSDirectory.open(Paths.get(rutaIndice)));
            IndexSearcher searcher = new IndexSearcher(reader);
            searcher.setSimilarity(similarity);
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            campo = campo.toLowerCase();
            consulta = consulta.toLowerCase();
            Query query;
            Term termino = new Term(campo, consulta);
            query = new TermQuery(termino);
            BooleanClause b1=null;

            b1 = new BooleanClause(query,BooleanClause.Occur.MUST);   

            BooleanQuery.Builder bBuilder = new BooleanQuery.Builder();
            bBuilder.add(b1);
            BooleanQuery bq = bBuilder.build();
            TopDocs results = searcher.search(bq, 10);
            ScoreDoc[] hits = results.scoreDocs;

            solucion = mostrarResultadosBusquedas(hits, searcher, results);
            
        } catch (IOException ex) {
            Logger.getLogger(indexar.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            reader.close();
        }catch(IOException e1){
            e1.printStackTrace();
        }
        return solucion;
    }
        
        
    public static String busquedaFacetas(DefaultListModel modelo, Similarity similarity, String Campo, String aFacetar, boolean todosCampos, boolean conConsulta, String campoAMostrar) throws ParseException{
        IndexReader reader = null;
        String salida="No hay"; 
        
        try{
            reader = DirectoryReader.open(FSDirectory.open(Paths.get(rutaIndice)));
            IndexSearcher searcher = new IndexSearcher(reader);
            searcher.setSimilarity(similarity);
            FSDirectory taxoDir = FSDirectory.open(Paths.get(rutaFaceta));
            TaxonomyReader taxoReader = new DirectoryTaxonomyReader(taxoDir);
            FacetsCollector fc = new FacetsCollector();
            QueryParser parser = null;
            
            if(Campo.equals("todas")){
                parser = new QueryParser("autores", new SimpleAnalyzer());
            }
            else{
                parser = new QueryParser(Campo, new SimpleAnalyzer());
            }
            
            Query query = null;
            
            if(!conConsulta){//Se muestran todas las facetas disponibles
                query = new MatchAllDocsQuery();
                TopDocs tdc = FacetsCollector.search(searcher, query, 10, fc);
                Facets facetas = new FastTaxonomyFacetCounts(taxoReader, fconfig, fc);
                List<FacetResult> TodasDims = facetas.getAllDims(10);
                System.out.println("Categorias totales " + TodasDims.size());
                salida="";
                LongRange[] rangos = new LongRange[7];
                rangos[0] = new LongRange("1-1000", 1L, true, 1000L, false);
                rangos[1] = new LongRange("1000-5000", 1000L, true, 5000L, false);
                rangos[2] = new LongRange("5000-10000", 5000L, true, 10000L, false);
                rangos[3] = new LongRange("10000-25000", 10000L, true, 25000L, false);
                rangos[4] = new LongRange("25000-50000", 25000L, true, 50000L, false);
                rangos[5] = new LongRange("50000-100000", 50000L, true, 100000L, false);
                rangos[6] = new LongRange("50000-100000", 100000L, true, 1000000L, true);
                FacetsCollector fcRango = new FacetsCollector();
                FacetsCollector.search(searcher, new MatchAllDocsQuery(), 10, fcRango);
                LongRangeFacetCounts facetillas = new LongRangeFacetCounts("rangos", fcRango, rangos);
                List<FacetResult> TODAS = facetillas.getAllDims(10);
                Boolean escribir = true;
                if(todosCampos){
                    for(FacetResult fr: TodasDims){
                            salida += "Categoria " + fr.dim + "\n";
                            if(!fr.dim.equals("longitud")&&!fr.dim.equals("rangos")){
                                modelo.addElement(fr.dim);
                                escribir=true;
                            }
                            else{
                                escribir=false;
                            }
                            
                            for(LabelAndValue lv: fr.labelValues){
                                salida += "    Etiq: " + lv.label + " valor (#n)->" +lv.value + "\n";
                                if(escribir)
                                    modelo.addElement(lv.label);
                            }
                            modelo.addElement("<-------->");
                        }
                        for(FacetResult fr: TODAS){
                            salida += "Categoria " + fr.dim + "\n";
                            if(!fr.dim.equals("longitud")&&!fr.dim.equals("rangos")){
                                modelo.addElement(fr.dim);
                                escribir=true;
                            }
                            else{
                                escribir=false;
                            }
                            
                            for(LabelAndValue lv: fr.labelValues){
                                salida += "    Etiq: " + lv.label + " valor (#n)->" +lv.value + "\n";
                                if(escribir)
                                    modelo.addElement(lv.label);
                            } 
                            modelo.addElement("<-------->");
                        }
                }
                else{
                    if(campoAMostrar.equals("rangos")){
                        for(FacetResult fr: TODAS){
                            salida += "Categoria " + fr.dim + "\n";
                            if(!fr.dim.equals("longitud")&&!fr.dim.equals("rangos")){
                                modelo.addElement(fr.dim);
                                escribir=true;
                            }
                            else{
                                escribir=false;
                            }
                            
                            for(LabelAndValue lv: fr.labelValues){
                                salida += "    Etiq: " + lv.label + " valor (#n)->" +lv.value + "\n";
                                if(escribir)
                                    modelo.addElement(lv.label);
                            }
                            modelo.addElement("<-------->");
                        }
                    }
                    else{
                        for(FacetResult fr: TodasDims){
                            if(fr.dim.equals(campoAMostrar)){
                               salida += "Categoria " + fr.dim + "\n";
                               if(!fr.dim.equals("longitud")&&!fr.dim.equals("rangos")){
                                modelo.addElement(fr.dim);
                                escribir=true;
                            }
                            else{
                                escribir=false;
                            }
                            
                            for(LabelAndValue lv: fr.labelValues){
                                salida += "    Etiq: " + lv.label + " valor (#n)->" +lv.value + "\n";
                                if(escribir)
                                    modelo.addElement(lv.label);
                            } 
                                modelo.addElement("<-------->");
                            }
                        }
                    }
                    
                }
                
            }
            else{//Se realiza aplicando la consulta
                query = parser.parse(aFacetar);
                LongRange[] rangos = new LongRange[7];
                rangos[0] = new LongRange("1-1000", 1L, true, 1000L, false);
                rangos[1] = new LongRange("1000-5000", 1000L, true, 5000L, false);
                rangos[2] = new LongRange("5000-10000", 5000L, true, 10000L, false);
                rangos[3] = new LongRange("10000-25000", 10000L, true, 25000L, false);
                rangos[4] = new LongRange("25000-50000", 25000L, true, 50000L, false);
                rangos[5] = new LongRange("50000-100000", 50000L, true, 100000L, false);
                rangos[6] = new LongRange("50000-100000", 100000L, true, 1000000L, true);
                FacetsCollector fcRango = new FacetsCollector();
                FacetsCollector.search(searcher, query, 10, fcRango);
                LongRangeFacetCounts facetillas = new LongRangeFacetCounts("rangos", fcRango, rangos);
                List<FacetResult> TODAS = facetillas.getAllDims(10);
                salida="";
                TopDocs tdc = FacetsCollector.search(searcher, query, 10, fc);
                Facets facetas = new FastTaxonomyFacetCounts(taxoReader, fconfig, fc);
                List<FacetResult> TodasDims = facetas.getAllDims(10);
                System.out.println("Categorias totales " + TodasDims.size());
                Boolean escribir = true;
                if(todosCampos){
                    for(FacetResult fr: TodasDims){
                            salida += "Categoria " + fr.dim + "\n";
                            
                               if(!fr.dim.equals("longitud")&&!fr.dim.equals("rangos")){

                                modelo.addElement(fr.dim);
                                escribir=true;
                            }
                            else{
                                escribir=false;
                            }
                            
                            for(LabelAndValue lv: fr.labelValues){
                                salida += "    Etiq: " + lv.label + " valor (#n)->" +lv.value + "\n";
                                if(escribir)
                                    modelo.addElement(lv.label);
                            }
                            modelo.addElement("<-------->");
                        }
                    for(FacetResult fr: TODAS){
                        salida += "Categoria " + fr.dim + "\n";
                            if(!fr.dim.equals("longitud")&&!fr.dim.equals("rangos")){

                                modelo.addElement(fr.dim);
                                escribir=true;
                            }
                            else{
                                escribir=false;
                            }
                        for(LabelAndValue lv: fr.labelValues){
                            salida += "    Etiq: " + lv.label + " valor (#n)->" +lv.value + "\n";
                            if(escribir)
                                modelo.addElement(lv.label);
                        } 
                        modelo.addElement("<-------->");
                    }
                }
                else{
                    if(campoAMostrar.equals("rangos")){
                        for(FacetResult fr: TODAS){
                            salida += "Categoria " + fr.dim + "\n";
                            if(!fr.dim.equals("longitud")&&!fr.dim.equals("rangos")){

                                modelo.addElement(fr.dim);
                                escribir=true;
                            }
                            else{
                                escribir=false;
                            }
                            
                            for(LabelAndValue lv: fr.labelValues){
                                salida += "    Etiq: " + lv.label + " valor (#n)->" +lv.value + "\n";
                                if(escribir)
                                    modelo.addElement(lv.label);
                            }
                            modelo.addElement("<-------->");
                        }
                    }
                    else{
                        for(FacetResult fr: TodasDims){
                            if(fr.dim.equals(campoAMostrar)){
                               salida += "Categoria " + fr.dim + "\n";
                            if(!fr.dim.equals("longitud")&&!fr.dim.equals("rangos")){
                                modelo.addElement(fr.dim);
                                escribir=true;
                            }
                            else{
                                escribir=false;
                            }
                            
                            for(LabelAndValue lv: fr.labelValues){
                                salida += "    Etiq: " + lv.label + " valor (#n)->" +lv.value + "\n";
                                if(escribir)
                                    modelo.addElement(lv.label);
                            } 
                                modelo.addElement("<-------->");
                            }
                        }
                    }
                }
            }
            
        reader.close();
            
        } catch (IOException ex) {
            Logger.getLogger(indexar.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            reader.close();
        }catch(IOException e1){
            e1.printStackTrace();
        }
        
        return salida;
    }
    
    public static String busquedaAplicandoFaceta(Similarity similarity, Query query, String campo, String consulta) throws IOException{
        IndexReader reader = null;
        reader = DirectoryReader.open(FSDirectory.open(Paths.get(rutaIndice)));
        IndexSearcher searcher = new IndexSearcher(reader);
        searcher.setSimilarity(similarity);
        DrillDownQuery dq = new DrillDownQuery(fconfig, query);
        dq.add(campo, consulta);
        FSDirectory taxoDir = FSDirectory.open(Paths.get(rutaFaceta));
        TaxonomyReader taxoReader = new DirectoryTaxonomyReader(taxoDir);
        DrillSideways ds = new DrillSideways(searcher, fconfig, taxoReader);
        
        DrillSidewaysResult dsresult = ds.search(dq, 10);
        
        TopDocs results = searcher.search(dq, 10);
        ScoreDoc[] hits = results.scoreDocs;
        TotalHits numTotalHits = results.totalHits;
        nDocs = (int) numTotalHits.value;
        String solucion = mostrarResultadosBusquedas(hits, searcher, results);
        System.out.println(solucion);
        
        
        return solucion;
        
    }

    
    public static String mostrarResultadosBusquedas(ScoreDoc[] hits, IndexSearcher searcher, TopDocs results) throws IOException{
        TotalHits numTotalHits = results.totalHits;
        System.out.println(numTotalHits.value + " documentos encontrados");
        nDocs = (int) numTotalHits.value;
        String resultado="";
        for(int j = 0; j < hits.length; j++){
            Document doc = searcher.doc(hits[j].doc);
            String cuerpo = doc.get("titulo");
            String texto = doc.get("texto");
            if(texto.length()>200){
                texto= texto.substring(0,200);
            }
            String[] autores = doc.getValues("autores");
            String[] paises = doc.getValues("paises");
            String lon = doc.get("longitud");
            String[] uni = doc.getValues("universidad");
            String[] lugar = doc.getValues("lugar");
            String univer="";
            String lugares="";
            String autor = "", pais="";
            int aux = j+1;
            resultado +=  "Documento: " + aux +"\n";
            resultado +=  "Titulo: " + cuerpo + "\n";
            resultado += "Longitud: " + lon + "\n";
            for(int k = 0; k < autores.length; k++){
                if(k==autores.length-1)
                    autor += autores[k];
                else
                    autor += autores[k] + ", ";
            }
            paises = sinRepetir(paises);
            for(int k = 0; k < paises.length; k++){
                pais += paises[k] + ", ";
            }
            uni = sinRepetir(uni);
            for(int k = 0; k < uni.length; k++){
                univer += uni[k] + ", ";
            }
            lugar = sinRepetir(lugar);
            for(int k = 0; k < lugar.length; k++){
                lugares += lugar[k] + ", ";
            }
            resultado +=  "Universidad: " + univer + "\n"; 
            resultado +=  "Lugar: " + lugares + "\n";  
            resultado +=  "Autores: " + autor + "\n";  
            resultado +=  "Paises: " + pais + "\n";
            resultado+= "Texto: " + texto + "...\n";
            resultado += "\n";
        }
        return resultado;
    }
    
    public static int getNumDoc(){
        return nDocs;
    }
}