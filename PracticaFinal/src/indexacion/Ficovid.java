
package indexacion;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;


public class Ficovid {
	
	private String paper_id;
        private Metadata metadata;
        private BodyText cuerpo;
        
        public Ficovid(JsonNode _jsonNode){
            paper_id = _jsonNode.at("/paper_id").toString();
            paper_id=paper_id.replace("\"","");
            metadata = new Metadata(_jsonNode);
            cuerpo = new BodyText(_jsonNode);
        }
        
        public String getId(){
            return paper_id;
        }
        
        public Metadata getMetadata(){
            return metadata;
        }
        
        public ArrayList<Autores> getAutores(){
            if(metadata.getAutor().isEmpty())
                return null;
            else
                return metadata.autores;
        }
        
        public BodyText getCuerpo(){
            return cuerpo;
        }
	
}