package posl.logo;

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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPologo = new JFrame();
		frmPologo.setTitle("PoLogo");
		frmPologo.setBounds(100, 100, 785, 417);
		frmPologo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				new Timer(20, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						repaint();

					}
				}).start();
			}

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
	}

	public void runLogo(String text,final PropertyChangeListener errorListener ) {
		if (logoWorker == null) {
			logoWorker =  new LogoWorker(text,bimage);
			frmPologo.setTitle("Processing");
			logoWorker.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getNewValue().equals(SwingWorker.StateValue.DONE)){
						frmPologo.setTitle("Done");
						logoWorker.removePropertyChangeListener(this);
						logoWorker.removePropertyChangeListener(errorListener);
						logoWorker = null;
					}
				}
				
			});
			logoWorker.addPropertyChangeListener(errorListener);
			logoWorker.execute();
		} else {
			frmPologo.setTitle("Unable to Run while processing");
		}
	}

	public void setVisible(boolean b) {
		frmPologo.setVisible(true);
	}

}
