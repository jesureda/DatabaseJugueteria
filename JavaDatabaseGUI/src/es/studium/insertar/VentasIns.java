package es.studium.insertar;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

public class VentasIns extends JFrame implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;
	Connection connection = Conexion.getConexion();
	Statement statement = null;
	ResultSet rs = null;
	String s, FK;
	JPanel panel1 = new JPanel(new GridLayout(1, 2));
	JPanel panel2 = new JPanel(new GridLayout(1, 2));
	JPanel panel3 = new JPanel(new GridLayout(1, 2));
	JLabel label1 = new JLabel("Fecha Emisión: ", JLabel.CENTER);
	JLabel label2 = new JLabel("Importe: ", JLabel.CENTER);
	JLabel label3 = new JLabel("idEmpleadoFK: ", JLabel.CENTER);
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	Choice lista = new Choice();
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

	public VentasIns() {
		// Ventana
		setTitle("Ventas");
		setResizable(false);
		setSize(300, 125);
		setLayout(new GridLayout(4, 1));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		panel1.add(label1);
		panel1.add(text1);
		panel2.add(label2);
		panel2.add(text2);
		panel3.add(label3);
		panel3.add(lista);
		panelBoton.add(confirmar);
		panelBoton.add(limpiar);
		lista.add("");
		add(panel1);
		add(panel2);
		add(panel3);
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
		lista.addItemListener(this);

		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT * FROM Empleados");
			while (rs.next()) {
				s = Integer.toString(rs.getInt(1));
				s = s + "-" + rs.getString(2);
				lista.add(s);
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Error en la sentencia SQL:" + e.toString());
		}
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		// Almacena la id del elemento de lista seleccionado en la posición 0
		// del array, posteriormente volcamos dicho valor en la variable FK para
		// utilizarlo al insertar
		String[] array = ie.getItem().toString().split("-");
		FK = array[0];
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// Al confirmar rellena la sentencia con lo escrito en los TextField y a
		// continuación, los limpia.
		if (confirmar.equals(ae.getSource())) {
			diaSeguro.setVisible(true);
		}
		if (btnSi.equals(ae.getSource())) {
			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate("INSERT INTO Ventas (fechaEmision, importe, idEmpleadoFK) VALUES ('"
						+ text1.getText() + "'," + text2.getText() + "," + FK + ")");
				text1.setText("");
				text2.setText("");
				lista.select(0);
				diaSeguro.setVisible(false);
				new DiaCorrecto();
			} catch (SQLException se) {
				new DiaError();
			}
		}
		if (btnNo.equals(ae.getSource())) {
			diaSeguro.setVisible(false);
		}
		// Si pulsamos Limpiar vacía los TextField
		if (limpiar.equals(ae.getSource())) {
			text1.setText("");
			text2.setText("");
			lista.select(0);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
