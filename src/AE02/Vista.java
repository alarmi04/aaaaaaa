package AE02;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUser;
	private JTextField txtContra;
	private JButton btnLog;
	private JTextField txtConsulta;
	private JButton btnCerrarSesion;
	private JButton btnConsulta;
	private JTextArea txtaConsulta;
	private JComboBox cmbConsulta;
	private JButton btnCerrarAplicacion;
	private JPanel pnlConsulta;
	private JPanel pnlUsuari;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	*/
	/**
	 * Create the frame.
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 844, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnCerrarAplicacion = new JButton("CERRAR APLICACION");
		btnCerrarAplicacion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCerrarAplicacion.setBounds(580, 34, 193, 85);
		contentPane.add(btnCerrarAplicacion);
		
		pnlConsulta = new JPanel();
		pnlConsulta.setBounds(23, 190, 772, 234);
		contentPane.add(pnlConsulta);
		pnlConsulta.setLayout(null);
		
		txtaConsulta = new JTextArea();
		txtaConsulta.setBounds(10, 118, 740, 116);
		pnlConsulta.add(txtaConsulta);
		
		txtConsulta = new JTextField();
		txtConsulta.setBounds(206, 60, 544, 38);
		pnlConsulta.add(txtConsulta);
		txtConsulta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtConsulta.setColumns(10);
		
		cmbConsulta = new JComboBox();
		cmbConsulta.setBounds(105, 61, 91, 38);
		pnlConsulta.add(cmbConsulta);
		
		JLabel lblConsulta = new JLabel("Consulta:");
		lblConsulta.setBounds(10, 59, 96, 38);
		pnlConsulta.add(lblConsulta);
		lblConsulta.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnCerrarSesion = new JButton("Tancar sessió");
		btnCerrarSesion.setBounds(10, 10, 157, 29);
		pnlConsulta.add(btnCerrarSesion);
		btnCerrarSesion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnConsulta = new JButton("Fer consulta");
		btnConsulta.setBounds(593, 10, 157, 29);
		pnlConsulta.add(btnConsulta);
		btnConsulta.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		pnlUsuari = new JPanel();
		pnlUsuari.setBounds(23, 10, 375, 181);
		contentPane.add(pnlUsuari);
		pnlUsuari.setLayout(null);
		
		btnLog = new JButton("Accedir");
		btnLog.setBounds(10, 133, 327, 38);
		pnlUsuari.add(btnLog);
		btnLog.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		txtContra = new JTextField();
		txtContra.setBounds(122, 84, 215, 38);
		pnlUsuari.add(txtContra);
		txtContra.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtContra.setColumns(10);
		
		txtUser = new JTextField();
		txtUser.setBounds(122, 24, 215, 38);
		pnlUsuari.add(txtUser);
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtUser.setColumns(10);
		
		JLabel lblUser = new JLabel("Usuari:");
		lblUser.setBounds(10, 24, 96, 38);
		pnlUsuari.add(lblUser);
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblContra = new JLabel("Contrasenya:");
		lblContra.setBounds(10, 85, 96, 38);
		pnlUsuari.add(lblContra);
		lblContra.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlConsulta.setVisible(false);
		btnCerrarAplicacion.setVisible(false);
		
		setVisible(true);
	}
	public JPanel getPnlUsuari() {
		return pnlUsuari;
	}
	
	public JTextField getTxtUser() {
		return txtUser;
	}
	
	public JTextField getTxtContra() {
		return txtContra;
	}
	public JButton getBtnLog() {
		return btnLog;
	}
	public JComboBox getCmbConsulta() {
		return cmbConsulta;
	}
	public JTextArea getTxtaConsulta() {
		return txtaConsulta;
	}
	public JButton getBtnConsulta() {
		return btnConsulta;
	}
	public JButton getBtnCerrarSesion() {
		return btnCerrarSesion;
	}
	public JButton getBtnCerrarAplicacion() {
		return btnCerrarAplicacion;
	}
	public JTextField getTxtConsulta() {
		return txtConsulta;
	}
	
	public JPanel getPnlConsulta() {
		return pnlConsulta;
	}
}
