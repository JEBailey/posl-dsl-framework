package posl.logo.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class FindAndReplace extends JDialog {
	public FindAndReplace() {
		setTitle("Find and Replace");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 410, 440, 428, 406, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		Component verticalGlue = Box.createVerticalGlue();
		GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
		gbc_verticalGlue.insets = new Insets(0, 0, 5, 5);
		gbc_verticalGlue.gridx = 0;
		gbc_verticalGlue.gridy = 0;
		getContentPane().add(verticalGlue, gbc_verticalGlue);
		
		JLabel lblFind = new JLabel("Find");
		lblFind.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblFind = new GridBagConstraints();
		gbc_lblFind.gridwidth = 4;
		gbc_lblFind.anchor = GridBagConstraints.WEST;
		gbc_lblFind.insets = new Insets(0, 0, 5, 5);
		gbc_lblFind.gridx = 2;
		gbc_lblFind.gridy = 1;
		getContentPane().add(lblFind, gbc_lblFind);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setBackground(Color.WHITE);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 4;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 2;
		getContentPane().add(comboBox, gbc_comboBox);
		
		JLabel lblReplace = new JLabel("Replace");
		lblReplace.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblReplace = new GridBagConstraints();
		gbc_lblReplace.gridwidth = 4;
		gbc_lblReplace.anchor = GridBagConstraints.WEST;
		gbc_lblReplace.insets = new Insets(0, 0, 5, 5);
		gbc_lblReplace.gridx = 2;
		gbc_lblReplace.gridy = 3;
		getContentPane().add(lblReplace, gbc_lblReplace);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setEditable(true);
		comboBox_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.gridwidth = 4;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 4;
		getContentPane().add(comboBox_1, gbc_comboBox_1);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		GridBagConstraints gbc_verticalGlue_1 = new GridBagConstraints();
		gbc_verticalGlue_1.gridwidth = 4;
		gbc_verticalGlue_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalGlue_1.gridx = 2;
		gbc_verticalGlue_1.gridy = 5;
		getContentPane().add(verticalGlue_1, gbc_verticalGlue_1);
		
		JCheckBox chckbxWrapSearch = new JCheckBox("Wrap Search");
		GridBagConstraints gbc_chckbxWrapSearch = new GridBagConstraints();
		gbc_chckbxWrapSearch.gridwidth = 4;
		gbc_chckbxWrapSearch.anchor = GridBagConstraints.WEST;
		gbc_chckbxWrapSearch.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxWrapSearch.gridx = 2;
		gbc_chckbxWrapSearch.gridy = 6;
		getContentPane().add(chckbxWrapSearch, gbc_chckbxWrapSearch);
		
		JCheckBox chckbxMatchCase = new JCheckBox("Match case");
		GridBagConstraints gbc_chckbxMatchCase = new GridBagConstraints();
		gbc_chckbxMatchCase.gridwidth = 4;
		gbc_chckbxMatchCase.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxMatchCase.anchor = GridBagConstraints.WEST;
		gbc_chckbxMatchCase.gridx = 2;
		gbc_chckbxMatchCase.gridy = 7;
		getContentPane().add(chckbxMatchCase, gbc_chckbxMatchCase);
		
		JCheckBox chckbxMatchEntireWord = new JCheckBox("Match entire word");
		GridBagConstraints gbc_chckbxMatchEntireWord = new GridBagConstraints();
		gbc_chckbxMatchEntireWord.gridwidth = 4;
		gbc_chckbxMatchEntireWord.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxMatchEntireWord.anchor = GridBagConstraints.WEST;
		gbc_chckbxMatchEntireWord.gridx = 2;
		gbc_chckbxMatchEntireWord.gridy = 8;
		getContentPane().add(chckbxMatchEntireWord, gbc_chckbxMatchEntireWord);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 12));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 10;
		getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Replace All");
		btnNewButton_1.setFont(new Font("Dialog", Font.BOLD, 12));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 3;
		gbc_btnNewButton_1.gridy = 10;
		getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Replace");
		btnNewButton_2.setFont(new Font("Dialog", Font.BOLD, 12));
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_2.gridx = 4;
		gbc_btnNewButton_2.gridy = 10;
		getContentPane().add(btnNewButton_2, gbc_btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Find");
		btnNewButton_3.setFont(new Font("Dialog", Font.BOLD, 12));
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_3.gridx = 5;
		gbc_btnNewButton_3.gridy = 10;
		getContentPane().add(btnNewButton_3, gbc_btnNewButton_3);
	}

}
