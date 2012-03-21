package posl.editorkit.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.View;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class BoxHighlightPainter extends DefaultHighlightPainter {

	public BoxHighlightPainter(Color color) {
		super(color);
	}
	
	@Override
	public Shape paintLayer(Graphics g, int offs0,
			int offs1, Shape bounds, JTextComponent c,
			View view) {
		Rectangle alloc = null;
		g.setColor(Color.gray);
		try {
			alloc = (Rectangle) view.modelToView(offs0,
					Position.Bias.Forward, offs1,
					Position.Bias.Backward, bounds);
		} catch (BadLocationException e) {
			return null;
		}
		g.drawRect(alloc.x, alloc.y, alloc.width - 1,
				alloc.height - 1);

		return alloc;
	}

}
