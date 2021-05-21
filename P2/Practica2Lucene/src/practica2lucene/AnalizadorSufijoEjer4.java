
package practica2lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LetterTokenizer;


public class AnalizadorSufijoEjer4 extends Analyzer{

    @Override
    protected TokenStreamComponents createComponents(String string) {
        final Tokenizer tokenizer = (Tokenizer)new LetterTokenizer();
        return new Analyzer.TokenStreamComponents(tokenizer, (TokenStream)new filtroSufijosEjer4((TokenStream)tokenizer));
    }
    
}
