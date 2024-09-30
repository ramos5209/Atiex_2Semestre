package comandoCnc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ErroCod extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ErroCod dialog = new ErroCod();
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dialog.setVisible(true);
			//dialog.setLocationRelativeTo(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ErroCod() {
		setBounds(100, 100, 837, 243);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblAlert = new JLabel("Comando Inválido !!! Insira o Parâmetro correto.");
		lblAlert.setBackground(new Color(192, 192, 192));
		lblAlert.setFont(new Font("Calibri", Font.BOLD, 23));
		lblAlert.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlert.setBounds(135, 28, 552, 116);
		contentPanel.add(lblAlert);
		
					JButton cancelButton = new JButton("Close");
					cancelButton.setBounds(732, 164, 81, 31);
					contentPanel.add(cancelButton);
					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dispose();
						}
					});					
					cancelButton.setActionCommand("Cancel");
	}
}
