
package indexacion;

import com.fasterxml.jackson.databind.JsonNode;

//Clase para crear los autores

class Autores {
    private String primerNombre;
    private String segundoNombre;
    private String institucion;
    private final Localizacion localizacion;

    Autores(JsonNode _jsonNode) {
        localizacion = new Localizacion();
        primerNombre="Anonimo";
        segundoNombre="";
        institucion="Desconocida";
    }
    
    public void setPrimerNombre(String _nombre){
        _nombre=_nombre.replace("\"","");
        primerNombre = _nombre;
    }
    public void setSegundoNombre(String _last){
        _last=_last.replace("\"","");
        segundoNombre = _last;
    }
    public void setInstitucion(String _institucion){
        institucion=_institucion;
    }
    
    public void setLocalizacion(String lugar, String pais){
        lugar=lugar.replace("\"","");
        pais=pais.replace("\"","");

        if(!lugar.equals("null")){
            localizacion.setLugar(lugar); 
        }
        else{
            localizacion.setLugar("Desconocido");
        }
        
        if(!pais.equals("null"))
            localizacion.setPais(pais);
        else
            localizacion.setPais("Desconocido");
    }
    
    public String getPrimerNombre(){
        return primerNombre;
    }
    
     public String getInstitucion(){
        return institucion;
    }
    
    public Localizacion getLocalizacion(){
        return localizacion;
    }
    
    @Override
    public String toString(){
        return primerNombre + " " + segundoNombre;
    }
}
