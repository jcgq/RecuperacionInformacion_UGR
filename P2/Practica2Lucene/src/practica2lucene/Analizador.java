package practica2lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class Analizador {

	public static List<String> tokenizeString(Analyzer analyzer, String string) {
	    List<String> result = new ArrayList<>();

	    String cad;
	    try {
	      TokenStream stream  = analyzer.tokenStream(null, new StringReader(string));
	      OffsetAttribute offsetAtt = stream.addAttribute(OffsetAttribute.class);
	      CharTermAttribute cAtt= stream.addAttribute(CharTermAttribute.class);
	      stream.reset();
	      while (stream.incrementToken()) {
	        result.add( cAtt.toString());
	      }
	      stream.end();
	    } catch (IOException e) {
	      throw new RuntimeException(e);
	    }
	    return result;
	  }
}