
package indexacion;

import com.fasterxml.jackson.databind.JsonNode;

class Localizacion {
    String lugar;
    String pais;
    
    Localizacion(){
        lugar = "Desconocido";
        pais = "Desconocido";
    }
    
    public void setLugar(String _lugar){
        lugar=_lugar;
    }
    
     public void setPais(String _pais){
        pais=_pais;
    }
     
    public String getLugar(){
        return lugar;
    }
    
    public String getPais(){
        return pais;
    }
}


