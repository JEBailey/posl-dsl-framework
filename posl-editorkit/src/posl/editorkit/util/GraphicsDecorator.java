package posl.editorkit.util;

import java.awt.Graphics2D;

import posl.engine.api.TokenVisitor;

public interface GraphicsDecorator extends TokenVisitor {
	
	GraphicsDecorator setGraphics(Graphics2D g2d);

}
