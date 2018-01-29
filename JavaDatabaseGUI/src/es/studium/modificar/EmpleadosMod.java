package es.studium.modificar;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
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

public class EmpleadosMod extends JFrame implements ItemListener, ActionListener {
	private static final long serialVersionUID = 1L;
	Statement statement = null;
	Connection connection = Conexion.getConexion();
	ResultSet rs = null;
	String s = "";
	String idParaModificar = "";
	Choice lista = new Choice();

	// Dialogo
	JPanel panel1 = new JPanel(new GridLayout(1, 2));
	JPanel panel2 = new JPanel(new GridLayout(1, 2));
	JPanel panel3 = new JPanel(new GridLayout(1, 2));
	JPanel panel4 = new JPanel(new GridLayout(1, 2));
	JPanel panel5 = new JPanel(new GridLayout(1, 2));
	JPanel panel0 = new JPanel(new GridLayout(1, 2));
	JLabel label0 = new JLabel("Identificador: ", JLabel.CENTER);
	JLabel label1 = new JLabel("Nombre: ", JLabel.CENTER);
	JLabel label2 = new JLabel("Apellidos: ", JLabel.CENTER);
	JLabel label3 = new JLabel("Dni: ", JLabel.CENTER);
	JLabel label4 = new JLabel("Teléfono: ", JLabel.CENTER);
	JLabel label5 = new JLabel("Turno: ", JLabel.CENTER);
	JTextField text0 = new JTextField();
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	JTextField text3 = new JTextField();
	JTextField text4 = new JTextField();
	JTextField text5 = new JTextField();
	JPanel panelBoton = new JPanel(new GridLayout(1, 2));
	JButton confirmar = new JButton("Aceptar");
	JButton limpiar = new JButton("Limpiar");
	JDialog dlgMod = new JDialog(this, "Modificar", true);

	// DialogEmergente
	URL resource1 = getClass().getClassLoader().getResource("exclam.png");
	JLabel seguro = new JLabel("¿Está seguro de eliminar esta entrada? ", JLabel.CENTER);
	JLabel img = new JLabel(new ImageIcon(resource1));
	JButton btnSi = new JButton("Sí");
	JButton btnNo = new JButton("No");
	JPanel pnlBotones = new JPanel(new GridLayout(1, 2));
	JDialog diaSeguro = new JDialog(this, "Confirmación", true);

	public EmpleadosMod() {
		setTitle("Modificar Clientes");
		setLayout(new FlowLayout());
		setSize(350, 200);
		setResizable(false);
		add(lista);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

		// Dialogo
		dlgMod.setResizable(false);
		dlgMod.setSize(300, 200);
		dlgMod.setLayout(new GridLayout(7, 1));
		dlgMod.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dlgMod.setVisible(false);
		dlgMod.setLocationRelativeTo(null);
		panel0.add(label0);
		panel0.add(text0);
		text0.setEditable(false);
		panel1.add(label1);
		panel1.add(text1);
		panel2.add(label2);
		panel2.add(text2);
		panel3.add(label3);
		panel3.add(text3);
		panel4.add(label4);
		panel4.add(text4);
		panel5.add(label5);
		panel5.add(text5);
		panelBoton.add(confirmar);
		panelBoton.add(limpiar);
		dlgMod.add(panel0);
		dlgMod.add(panel1);
		dlgMod.add(panel2);
		dlgMod.add(panel3);
		dlgMod.add(panel4);
		dlgMod.add(panel5);
		dlgMod.add(panelBoton);

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
		lista.add("Seleccionar la entrada a modificar");

		// Preparar el statement para rellenar el choice
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
		// Cuando elegimos una entrada nos rellena el dialogo con sus datos
		String[] array = ie.getItem().toString().split("-");
		idParaModificar = array[0];
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT * FROM Empleados WHERE idEmpleado=" + idParaModificar);
			rs.next();
			// Poner en los TextField los valores obtenidos
			text0.setText(Integer.toString(rs.getInt(1)));
			text1.setText(rs.getString(2));
			text2.setText(rs.getString(3));
			text3.setText(rs.getString(4));
			text4.setText(Integer.toString(rs.getInt(5)));
			text5.setText(rs.getString(6));
			rs.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Error en la sentencia SQL:" + e.toString());
		}
		dlgMod.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// Al confirmar realiza el UPDATE, al limpiar vacía los textfield
		if (confirmar.equals(ae.getSource())) {
			diaSeguro.setVisible(true);
		}
		if (btnNo.equals(ae.getSource())) {
			diaSeguro.setVisible(false);
		}
		if (btnSi.equals(ae.getSource())) {
			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate("UPDATE Empleados SET nombre='" + text1.getText() + "' , apellidos='"
						+ text2.getText() + "' , dni='" + text3.getText() + "' , telefono=" + text4.getText()
						+ " , turno='" + text5.getText() + "' WHERE idEmpleado=" + idParaModificar);
				dlgMod.setVisible(false);
				diaSeguro.setVisible(false);
				new DiaCorrecto();
				statement.close();
			} catch (SQLException se) {
				// System.out.println(se.getMessage());
				new DiaError();
			}
			lista.removeAll();
			lista.add("Seleccionar la entrada a modificar");
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
		if (limpiar.equals(ae.getSource())) {
			text1.setText("");
			text2.setText("");
			text3.setText("");
			text4.setText("");
			text5.setText("");
		}
	}

	public static void main(String[] args) {

	}
}
