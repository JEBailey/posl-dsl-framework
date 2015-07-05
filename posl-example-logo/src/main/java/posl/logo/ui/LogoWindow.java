package posl.logo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import posl.engine.Interpreter;
import posl.engine.core.Context;
import posl.engine.error.PoslException;
import posl.engine.provider.PoslProvider;
import posl.logo.ImageUtil;
import posl.logo.LogoWorker;

public class LogoWindow {

	private JFrame frmPologo;

	private BufferedImage bimage = ImageUtil.getBufferedImage(800, 600);

	/**
	 * Create the application.
	 */
	public LogoWindow() {
		initialize();
	}

	private JPanel panel;

	private LogoWorker logoWorker;

	protected static boolean initialized;

	private enum STATE {
		ready, background, processingDone
	};

	private STATE currentState = STATE.ready;

	private Context context;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPologo = new JFrame();
		frmPologo.setTitle("PoLogo");
		frmPologo.setBounds(100, 100, 785, 417);
		frmPologo.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		panel = new JPanel() {

			private static final long serialVersionUID = 1L;

			{// static
				new Timer(30, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (currentState == STATE.processingDone){
							loop();
						}
						repaint();

					}
				}).start();
			}

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(bimage, 0, 0, null);
			}

		};
		panel.setPreferredSize(new Dimension(800, 600));
		panel.setMinimumSize(new Dimension(800, 600));
		panel.setMaximumSize(new Dimension(800, 600));
		panel.setBackground(Color.WHITE);
		frmPologo.getContentPane().add(panel, BorderLayout.CENTER);
		frmPologo.pack();
		frmPologo.setMinimumSize(frmPologo.getSize());
		frmPologo.setMaximumSize(frmPologo.getSize());
	}

	public void runLogo(String text, final PropertyChangeListener errorListener) {
		if (currentState != STATE.background) {
			currentState = STATE.background;
			context = PoslProvider.getContext("posl.logo");
			logoWorker = new LogoWorker(context,text, bimage);
			frmPologo.setTitle("Processing");
			logoWorker.addPropertyChangeListener(new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getNewValue().equals(SwingWorker.StateValue.DONE)) {
						frmPologo.setTitle("Running");
						currentState = STATE.processingDone;
						logoWorker.removePropertyChangeListener(this);
						logoWorker.removePropertyChangeListener(errorListener);
					}
				}

			});
			logoWorker.addPropertyChangeListener(errorListener);
			logoWorker.execute();
		} else {
			frmPologo.setTitle("Unable to Run while processing");
		}
	}
	
	private void loop(){
		try {
			if (context.containsKey("main")){
				Interpreter.process(context, "main");
			}
		} catch (PoslException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setVisible(boolean b) {
		frmPologo.setVisible(true);
	}

}
