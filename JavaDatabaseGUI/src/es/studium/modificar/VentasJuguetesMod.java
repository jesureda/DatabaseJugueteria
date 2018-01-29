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

import es.studium.conectar.Conexion;
import es.studium.proyecto.DiaCorrecto;
import es.studium.proyecto.DiaError;

public class VentasJuguetesMod extends JFrame implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;
	Statement statement = null;
	Connection connection = Conexion.getConexion();
	ResultSet rs = null;
	String s = "", s2 = "", s3 = "";
	String FK, FK2;
	String idParaModificar = "", idParaModificar2 = "";
	Choice lista = new Choice();

	// Dialogo
	JPanel panel1 = new JPanel(new GridLayout(1, 2));
	JPanel panel2 = new JPanel(new GridLayout(1, 2));
	JLabel label1 = new JLabel("idVentaFK: ", JLabel.CENTER);
	JLabel label2 = new JLabel("idJugueteFK: ", JLabel.CENTER);
	Choice listaFK = new Choice();
	Choice listaFK2 = new Choice();
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

	public VentasJuguetesMod() {
		setTitle("Modificar VentasJuguetes");
		setLayout(new FlowLayout());
		setSize(350, 200);
		setResizable(false);
		add(lista);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

		// Dialogo
		dlgMod.setResizable(false);
		dlgMod.setSize(300, 120);
		dlgMod.setLayout(new GridLayout(3, 1));
		dlgMod.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dlgMod.setVisible(false);
		dlgMod.setLocationRelativeTo(null);
		panel1.add(label1);
		panel1.add(listaFK);
		panel2.add(label2);
		panel2.add(listaFK2);
		panelBoton.add(confirmar);
		panelBoton.add(limpiar);
		dlgMod.add(panel1);
		dlgMod.add(panel2);
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
		listaFK.addItemListener(this);
		listaFK2.addItemListener(this);
		lista.addItemListener(this);
		lista.add("Seleccionar la entrada a modificar");

		// Preparar el statement para rellenar el choice
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT * FROM VentasJuguetes");
			while (rs.next()) {
				s = Integer.toString(rs.getInt(1));
				s = s + "-" + Integer.toString(rs.getInt(2));
				lista.add(s);
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Error en la sentencia SQL:" + e.toString());
		}
		// Preparar el statement para rellenar el choice de la FK1
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT * FROM Ventas");
			while (rs.next()) {
				s2 = Integer.toString(rs.getInt(1));
				s2 = s2 + "-" + rs.getString(2);
				listaFK.add(s2);
				rs.close();
				statement.close();
			}
		} catch (SQLException e) {
			System.out.println("Error en la sentencia SQL:" + e.toString());
		}
		// Preparar el statement para rellenar el choice de la FK2
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT * FROM Juguetes");
			while (rs.next()) {
				s3 = Integer.toString(rs.getInt(1));
				s3 = s3 + "-" + rs.getString(2);
				listaFK2.add(s3);
				rs.close();
				statement.close();
			}
		} catch (SQLException e) {
			System.out.println("Error en la sentencia SQL:" + e.toString());
		}
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		// Cuando elegimos una entrada nos rellena el dialogo con sus datos
		String[] array = lista.getSelectedItem().toString().split("-");
		idParaModificar = array[0];
		idParaModificar2 = array[1];

		if (dlgMod.isVisible() == false) {
			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = statement.executeQuery("SELECT * FROM Ventas WHERE idVenta=" + idParaModificar);
				rs.next();
				listaFK.select(rs.getInt(1) - 1);
				rs = statement.executeQuery("SELECT * FROM Juguetes WHERE idJuguete=" + idParaModificar2);
				rs.next();
				listaFK2.select(rs.getInt(1) - 1);
				rs.close();
				statement.close();
			} catch (SQLException e) {
				System.out.println("Error en la sentencia SQL:" + e.toString());
			}
		}
		dlgMod.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (confirmar.equals(ae.getSource())) {
			diaSeguro.setVisible(true);
		}
		if (btnNo.equals(ae.getSource())) {
			diaSeguro.setVisible(false);
		}
		if (btnSi.equals(ae.getSource())) {
			String[] array2 = listaFK.getSelectedItem().toString().split("-");
			FK = array2[0];
			String[] array3 = listaFK2.getSelectedItem().toString().split("-");
			FK2 = array3[0];
			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate("UPDATE VentasJuguetes SET idVentaFK=" + FK + ",idJugueteFK=" + FK2
						+ " WHERE idVentaFK=" + idParaModificar + " AND idJugueteFK=" + idParaModificar2);
				dlgMod.setVisible(false);
				diaSeguro.setVisible(false);
				new DiaCorrecto();
				statement.close();
				lista.removeAll();
				lista.add("Seleccionar la entrada a modificar");
				try {
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs = statement.executeQuery("SELECT * FROM VentasJuguetes");
					while (rs.next()) {
						s = Integer.toString(rs.getInt(1));
						s = s + "-" + Integer.toString(rs.getInt(2));
						lista.add(s);
					}
					rs.close();
					statement.close();
				} catch (SQLException e) {
					System.out.println("Error en la sentencia SQL:" + e.toString());
				}
			} catch (SQLException se) {
				System.out.println(se.getMessage());
				new DiaError();
			}

		}
		if (limpiar.equals(ae.getSource())) {
			listaFK.select(0);
			listaFK2.select(0);
		}

	}

	public static void main(String[] args) {
	}

}
