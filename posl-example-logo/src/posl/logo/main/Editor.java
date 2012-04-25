package posl.logo.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import posl.editorkit.DocumentImpl;
import posl.editorkit.PoslEditorKit;
import posl.engine.error.PoslException;
import posl.logo.ui.LogoWindow;

public class Editor {

	private JFrame frmPologoEditor;
	private LogoWindow window = new LogoWindow();// display window
	private JFileChooser fileChooser = new JFileChooser();
	private File selectedFile;
	private DocumentImpl doc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor window = new Editor();
					window.frmPologoEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Editor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPologoEditor = new JFrame();
		frmPologoEditor.setTitle("PoLogo Editor");
		frmPologoEditor.setBounds(100, 100, 450, 640);
		frmPologoEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmPologoEditor.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		mnFile.add(mntmNew);

		JMenuItem mntmOpen = new JMenuItem("Open");

		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_MASK));
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save");

		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save As ...");
		mnFile.add(mntmSaveAs);

		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setMnemonic('E');
		menuBar.add(mnEdit);

		JMenuItem mntmUndo = new JMenuItem("Undo");
		mnEdit.add(mntmUndo);

		JMenuItem mntmRedo = new JMenuItem("Redo");
		mnEdit.add(mntmRedo);

		JMenuItem mntmCopy = new JMenuItem("Copy");
		mntmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COPY, 0));
		mnEdit.add(mntmCopy);

		JMenu mnRun = new JMenu("Run");
		mnRun.setMnemonic('u');
		menuBar.add(mnRun);

		JMenuItem mntmProgram = new JMenuItem("Program");

		mntmProgram.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				InputEvent.CTRL_MASK));
		mnRun.add(mntmProgram);

		JScrollPane scrollPane = new JScrollPane();
		frmPologoEditor.getContentPane().add(scrollPane, BorderLayout.CENTER);

		final JEditorPane editorPane = new JEditorPane();
		editorPane.setEditorKit(new PoslEditorKit());
		editorPane.setContentType("text/posl");
		scrollPane.setViewportView(editorPane);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
				null));
		frmPologoEditor.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		final JLabel statusLabel = new JLabel(" ");
		panel.add(statusLabel);

		doc = (DocumentImpl) editorPane.getDocument();

		editorPane.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		doc.addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				changedUpdate(arg0);

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				changedUpdate(arg0);
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				updateTitle(true);
			}
		});

		// undo and redo events are handled by document created actions
		mntmUndo.setAction(((DocumentImpl) doc).undoAction);
		mntmRedo.setAction(((DocumentImpl) doc).redoAction);

		mntmNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectedFile = null;
				editorPane.setText("");
				updateTitle(false);
				doc.getUndoManager().discardAllEdits();
			}
		});

		// Run the program
		mntmProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = editorPane.getText();
				window.setVisible(true);
				window.runLogo(text, new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (evt.getPropertyName().equals("error")) {
							PoslException pe = (PoslException) evt
									.getNewValue();
							statusLabel.setText(pe.toString());
						}

					}
				});
			}
		});

		// Open dialog
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int retVal = fileChooser.showOpenDialog(frmPologoEditor);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					try {
						selectedFile = fileChooser.getSelectedFile();
						editorPane.setText("");
						editorPane
								.read(new FileInputStream(selectedFile), null);
						updateTitle(false);
					} catch (FileNotFoundException e) {
						// should not happen
						e.printStackTrace();
					} catch (IOException e) {
						// should not happen
						e.printStackTrace();
					}
				}
			}
		});

		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int retVal = JFileChooser.APPROVE_OPTION;
				if (selectedFile == null) {
					retVal = fileChooser.showSaveDialog(frmPologoEditor);
					if (retVal == JFileChooser.APPROVE_OPTION) {
						selectedFile = fileChooser.getSelectedFile();
					} else {
						return;
					}
				}
				try {
					FileWriter writer = new FileWriter(selectedFile);
					editorPane.write(writer);
				} catch (IOException ioex) {
					JOptionPane.showMessageDialog(frmPologoEditor, ioex);
				}
				updateTitle(false);
			}
		});

		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int retVal = fileChooser.showSaveDialog(frmPologoEditor);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					try {
						FileWriter writer = new FileWriter(selectedFile);
						editorPane.write(writer);
						updateTitle(false);
					} catch (IOException ioex) {
						JOptionPane.showMessageDialog(frmPologoEditor, ioex);
					}
				}
			}
		});

	}

	private String title = "PoLogo Editor";

	private void updateTitle(boolean changed) {
		StringBuffer buffer = new StringBuffer(title);
		if (this.selectedFile != null) {
			buffer.append("(");
			buffer.append(this.selectedFile.getName());
			if (changed) {
				buffer.append('*');
			}
			buffer.append(")");
		}
		frmPologoEditor.setTitle(buffer.toString());
	}

}
