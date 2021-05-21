
package indexacion;

import com.fasterxml.jackson.databind.JsonNode;

//Clase para texto

class BodyText {
    String texto;
    
    BodyText(JsonNode _jsonNode){
        String aux = "";
        for(int i = 0; i< _jsonNode.at("/body_text").size(); i++){
            aux += _jsonNode.at("/body_text").get(i).at("/text").toString() + " ";
        }
        texto=aux;
        texto=texto.replace("\"","");
    }
    
    
    public String getTexto(){
        return texto;
    }
}
