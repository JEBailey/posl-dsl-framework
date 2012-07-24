package posl.editorkit;

import java.io.Reader;
import java.util.List;
import java.util.logging.Logger;

import posl.editorkit.token.ILexer;
import posl.editorkit.token.IToken;

public class DocumentLexer extends ILexer {

	static Logger log = Logger.getLogger(DocumentLexer.class.getName());



	@Override
	public void tokenize(Reader is, List<IToken> tokens) {

		
	}

}
