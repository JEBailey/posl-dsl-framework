package posl.editorkit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.PlainView;
import javax.swing.text.Segment;
import javax.swing.text.Utilities;

import posl.engine.token.Token;

/**
 * View that uses the lexical information to determine the style characteristics
 * of the text that it renders. This simply colors the various tokens
 */
class PoslView extends PlainView {

	/**
	 * Construct a simple colored view of posl text.
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
		List<Token> tokens = doc.getTokensInRange(start, end);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		int mark = start;
		for (Token token : tokens) {
			int endPosition = Math.min(token.getEndOffset(), end);
			g2.setFont(this.getFont(token, g2.getFont()));
			g2.setColor(getForeground(token));
			Segment text = new Segment();
			doc.getText(mark, endPosition - mark, text);
			x = Utilities.drawTabbedText(text, x, y, g, this, mark);
			// x = super.drawUnselectedText(g, x, y,mark , endPosition);
			mark = endPosition;
		}
		//tokens may not reach to the end of the area we want rendered
		//so this will make up the remaining space
		if (end != mark) {
			Segment text = new Segment();
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

	/**
	 * Fetch the foreground color to use for a lexical token with the given
	 * value.
	 * 
	 * @param attr
	 *            attribute set from a token element that has a Token in the
	 *            set.
	 */
	public Color getForeground(Token token) {
		DocAttributes attr = (DocAttributes) token.getAttribute();
		Color reply = Color.black;
		switch (token.type) {
		case COMMENT:
			reply = Color.gray;
			break;
		case NUMBER:
		case STRING:
			reply = Color.green.darker().darker();
			break;
		case GRAMMAR:
			reply = Color.blue;
			break;
		}
		if (attr.isCommand()) {
			reply = new Color(147, 0, 71);// Color.red.darker().darker();
		}

		return reply;
	}

	public Font getFont(Token token, Font font) {
		DocAttributes attr = (DocAttributes) token.getAttribute();
		return font.deriveFont(attr.isCommand() ? Font.BOLD : Font.PLAIN);
	}

}