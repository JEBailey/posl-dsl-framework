package posl.editorkit;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.PlainView;
import javax.swing.text.Segment;
import javax.swing.text.Utilities;

import posl.editorkit.token.IToken;


/**
 * View that uses the lexical information to determine the style characteristics
 * of the text that it renders. This simply colors the various tokens
 */
class PoslView extends PlainView {

	/**
	 * Construct a simple colored view of text.
	 */
	PoslView(Element elem) {
		super(elem);
	}

	/**
	 * Renders the given range in the model as normal unselected text. This is
	 * implemented to paint colors based upon the token-to-color translations.
	 * 
	 * @param g
	 *            the graphics context
	 * @param x
	 *            the starting X coordinate
	 * @param y
	 *            the starting Y coordinate
	 * @param start
	 *            the beginning position in the model
	 * @param end
	 *            the ending position in the model
	 * @returns the location of the end of the range
	 * @exception BadLocationException
	 *                if the range is invalid
	 */
	protected int drawUnselectedText(Graphics g, int x, int y, int start,
			int end) throws BadLocationException {
		DocumentImpl doc = (DocumentImpl) getDocument();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		int mark = start;
		Segment text = new Segment();
		for (IToken token : doc.getTokensInRange(start, end)) {
			int endPosition = Math.min(token.getEndOffset(), end);
			token.consume(g2);
			doc.getText(mark, endPosition - mark, text);
			x = Utilities.drawTabbedText(text, x, y, g, this, mark);
			mark = endPosition;
		}
		//tokens may not reach to the end of the area we want rendered
		//so this will make up the remaining space
		if (end != mark) {
			doc.getText(mark, end - mark, text);
			x = Utilities.drawTabbedText(text, x, y, g, this, mark);
		}
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN));
		return x;
	}

	protected int drawSelectedText(Graphics g, int x, int y, int p0, int p1)
			throws BadLocationException {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		return super.drawSelectedText(g2, x, y, p0, p1);
	}



}