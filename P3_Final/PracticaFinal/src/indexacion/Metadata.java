
package indexacion;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;

class Metadata {
    private String titulo;
    ArrayList<Autores> autores;

    Metadata(JsonNode _jsonNode) {
        titulo = _jsonNode.at("/metadata/title").toString();
        titulo=titulo.replace('"', ' ');
        autores = new ArrayList<Autores>();
        
        //Comprobamos que hay autores. Si hay autores
        if(_jsonNode.at("/metadata/authors").size() > 0){
            //Para cada autor
            for(int i = 0; i < _jsonNode.at("/metadata/authors").size();i++){
                Autores auxiliar = new Autores(_jsonNode);
                
                //Obtenemos nombre
                String auxPrimer = _jsonNode.at("/metadata/authors").get(i).at("/first").toString();
                auxPrimer = auxPrimer.replaceAll("[!&(-;.:/_+*^@!#$%&*)]", "Anonimo");
                
                if(!auxPrimer.contentEquals("null")){
                    auxiliar.setPrimerNombre(auxPrimer);
                }
                else{
                    auxiliar.setPrimerNombre("Anonimo");
                }
                
                //Obtenemos apellidos
                auxPrimer = _jsonNode.at("/metadata/authors").get(i).at("/last").toString();
                auxPrimer = auxPrimer.replaceAll("[!&(-;.:/_+*^@!#$%&*)]", "");
                if(!auxPrimer.contentEquals("null")){
                    auxiliar.setSegundoNombre(auxPrimer);
                }
                else{
                    auxiliar.setSegundoNombre("");
                }
                
                //Obtenemos institucion y pais
                auxPrimer = _jsonNode.at("/metadata/authors").get(i).at("/affiliation/institution").toString();
                if(!auxPrimer.equals("null") && !auxPrimer.isEmpty() && auxPrimer.length()>2 ){
                    String aux = auxPrimer.replaceAll("\"", "");
                    auxiliar.setInstitucion(aux);
                }
                else{
                    auxiliar.setInstitucion("Desconocida");
                }
                String auxLugar = _jsonNode.at("/metadata/authors").get(i).at("/affiliation/location/settlement").toString();
                String auxPais = _jsonNode.at("/metadata/authors").get(i).at("/affiliation/location/country").toString();
                auxiliar.setLocalizacion(auxLugar, auxPais);
                autores.add(auxiliar);
            }  
        }
        else{
            //En caso de que no haya autores (que en realidad alguien ha escrito ese articulo) Lo creamos como anonimo
            Autores auxiliar = new Autores(_jsonNode);
            auxiliar.setPrimerNombre("Anonimo");
            auxiliar.setSegundoNombre("");
            auxiliar.setLocalizacion("Desconocido", "Desconocido");
            auxiliar.setInstitucion("Desconocida");
            autores.add(auxiliar);
        }
        
        
    }
    
    public String getTitulo(){
        return titulo;
    }
    
    public ArrayList<Autores> getAutor(){
        return autores;
    }

    public Boolean hayAutores(){
        Boolean hay = true;
        if(autores.isEmpty()){
            hay = false;
        }
        return hay;
    }
}
