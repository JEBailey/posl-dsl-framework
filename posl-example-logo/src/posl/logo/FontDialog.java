package posl.logo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class FontDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FontDialog dialog = new FontDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FontDialog() {
		setTitle("Font Selection");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 173, 414, 45);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 207, 151);
		contentPanel.add(scrollPane);
		
		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {
				String[] values = GraphicsEnvironment.getLocalGraphicsEnvironment()
				        .getAvailableFontFamilyNames();
				
				public int getSize() {
					return values.length;
				}
				public String getElementAt(int index) {
					return values[index];
				}

		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(12, 1, 36, 1));
		spinner.setBounds(227, 12, 46, 20);
		contentPanel.add(spinner);
		
		JCheckBox chckbxUseAntiAlias = new JCheckBox("Use Anti Alias");
		chckbxUseAntiAlias.setBounds(223, 39, 201, 25);
		contentPanel.add(chckbxUseAntiAlias);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
