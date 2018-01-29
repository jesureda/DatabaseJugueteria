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
import es.studium.conectar.Conexion;
import es.studium.proyecto.DiaCorrecto;
import es.studium.proyecto.DiaError;

public class VentasJuguetesIns extends JFrame implements ItemListener, ActionListener {

	private static final long serialVersionUID = 1L;
	Statement statement = null;
	Connection connection = Conexion.getConexion();
	ResultSet rs = null;
	String s, s2;
	String FK, FK2;
	JPanel panel1 = new JPanel(new GridLayout(1, 2));
	JPanel panel2 = new JPanel(new GridLayout(1, 2));
	JLabel label1 = new JLabel("idVentaFK: ", JLabel.CENTER);
	JLabel label2 = new JLabel("idJugueteFK: ", JLabel.CENTER);
	Choice lista = new Choice();
	Choice lista2 = new Choice();
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

	public VentasJuguetesIns() {
		// Ventana
		setTitle("VentasJuguetes");
		setResizable(false);
		setSize(300, 120);
		setLayout(new GridLayout(3, 1));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		panel1.add(label1);
		panel1.add(lista);
		panel2.add(label2);
		panel2.add(lista2);
		panelBoton.add(confirmar);
		panelBoton.add(limpiar);
		add(panel1);
		add(panel2);
		add(panelBoton);
		lista.add("");
		lista2.add("");
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
		lista2.addItemListener(this);

		// Rellena el choice de la FK con los Ventas
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT * FROM Ventas");
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
		// Rellena el choice de la FK con los Juguetes
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT * FROM Juguetes");
			while (rs.next()) {
				s2 = Integer.toString(rs.getInt(1));
				s2 = s2 + "-" + rs.getString(2);
				lista2.add(s2);
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
		// del array, posteriormente volcamos dicho valor en las variable
		// FK y FK2 para utilizarlo al insertar
		String[] array = lista.getSelectedItem().toString().split("-");
		FK = array[0];

		String[] array2 = lista2.getSelectedItem().toString().split("-");
		FK2 = array2[0];
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// Al confirmar rellena la sentencia con lo seleccionado en las listas y
		// a continuación, las reinicia.
		if (confirmar.equals(ae.getSource())) {
			diaSeguro.setVisible(true);
		}
		if (btnSi.equals(ae.getSource())) {
			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate(
						"INSERT INTO VentasJuguetes (idVentaFK, idJugueteFK) VALUES (" + FK + "," + FK2 + ")");
				lista.select(0);
				lista2.select(0);
				diaSeguro.setVisible(false);
				new DiaCorrecto();
				statement.close();
			} catch (SQLException se) {
				new DiaError();
			}
		}
		if (btnNo.equals(ae.getSource())) {
			diaSeguro.setVisible(false);
		}
		// Si pulsamos Limpiar pone las listas en indice 0
		if (limpiar.equals(ae.getSource())) {
			lista.select(0);
			lista2.select(0);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
