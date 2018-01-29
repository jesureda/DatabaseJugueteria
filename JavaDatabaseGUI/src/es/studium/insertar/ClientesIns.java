package es.studium.insertar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.studium.conectar.Conexion;
import es.studium.proyecto.DiaCorrecto;
import es.studium.proyecto.DiaError;

public class ClientesIns extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	Statement statement = null;
	Connection connection = Conexion.getConexion();
	JPanel panel1 = new JPanel(new GridLayout(1, 2));
	JPanel panel2 = new JPanel(new GridLayout(1, 2));
	JPanel panel3 = new JPanel(new GridLayout(1, 2));
	JPanel panel4 = new JPanel(new GridLayout(1, 2));
	JLabel label1 = new JLabel("Nombre: ", JLabel.CENTER);
	JLabel label2 = new JLabel("Apellidos: ", JLabel.CENTER);
	JLabel label3 = new JLabel("Dni: ", JLabel.CENTER);
	JLabel label4 = new JLabel("Teléfono: ", JLabel.CENTER);
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	JTextField text3 = new JTextField();
	JTextField text4 = new JTextField();
	JPanel panelBoton = new JPanel(new GridLayout(1, 2));
	JButton confirmar = new JButton("Aceptar");
	JButton limpiar = new JButton("Limpiar");

	// DialogEmergente
	JLabel seguro = new JLabel("¿Está seguro de insertar esta entrada? ", JLabel.CENTER);
	JLabel img = new JLabel(new ImageIcon("exclam.png"));
	JButton btnSi = new JButton("Sí");
	JButton btnNo = new JButton("No");
	JPanel pnlBotones = new JPanel(new GridLayout(1, 2));
	JDialog diaSeguro = new JDialog(this, "Confirmación", true);

	public ClientesIns() {
		// Ventana
		setTitle("Clientes");
		setResizable(false);
		setSize(300, 200);
		setLayout(new GridLayout(5, 1));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		panel1.add(label1);
		panel1.add(text1);
		panel2.add(label2);
		panel2.add(text2);
		panel3.add(label3);
		panel3.add(text3);
		panel4.add(label4);
		panel4.add(text4);
		panelBoton.add(confirmar);
		panelBoton.add(limpiar);
		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);
		add(panelBoton);
		// DialogEmergente
		diaSeguro.setLayout(new BorderLayout());
		pnlBotones.add(btnSi);
		pnlBotones.add(btnNo);
		diaSeguro.add("North", seguro);
		diaSeguro.add("Center", img);
		diaSeguro.add("South", pnlBotones);
		diaSeguro.setLocationRelativeTo(null);
		diaSeguro.setResizable(false);
		diaSeguro.setSize(260, 150);
		diaSeguro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		btnSi.addActionListener(this);
		btnNo.addActionListener(this);
		// Listeners
		confirmar.addActionListener(this);
		limpiar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// Al confirmar rellena la sentencia con lo escrito en los TextField y a
		// continuación, los limpia.
		if (confirmar.equals(ae.getSource())) {
			diaSeguro.setVisible(true);
		}
		if (btnNo.equals(ae.getSource())) {
			diaSeguro.setVisible(false);
		}
		if (btnSi.equals(ae.getSource())) {
			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate(
						"INSERT INTO Clientes (nombre, apellidos, dni, telefono) VALUES ('" + text1.getText() + "','"
								+ text2.getText() + "','" + text3.getText() + "'," + text4.getText() + ")");
				text1.setText("");
				text2.setText("");
				text3.setText("");
				text4.setText("");
				diaSeguro.setVisible(false);
				new DiaCorrecto();
				statement.close();
			} catch (SQLException se) {
				new DiaError();
			}
		}
		// Si pulsamos Limpiar vacía los TextField
		if (limpiar.equals(ae.getSource())) {
			text1.setText("");
			text2.setText("");
			text3.setText("");
			text4.setText("");
		}
	}

	public static void main(String[] args) {
	}
}
