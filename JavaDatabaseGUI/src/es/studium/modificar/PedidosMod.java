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

public class PedidosMod extends JFrame implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;
	Statement statement = null;
	Connection connection = Conexion.getConexion();
	ResultSet rs = null;
	String s = "", s2 = "", fk = "";
	String idParaModificar = "";
	Choice lista = new Choice();

	// Dialogo
	JPanel panel0 = new JPanel(new GridLayout(1, 2));
	JLabel label0 = new JLabel("Identificador: ", JLabel.CENTER);
	JTextField text0 = new JTextField();
	JPanel panel1 = new JPanel(new GridLayout(1, 2));
	JPanel panel2 = new JPanel(new GridLayout(1, 2));
	JPanel panel3 = new JPanel(new GridLayout(1, 2));
	JLabel label1 = new JLabel("Fecha: ", JLabel.CENTER);
	JLabel label2 = new JLabel("Urgente: ", JLabel.CENTER);
	JLabel label3 = new JLabel("idClienteFK: ", JLabel.CENTER);
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	Choice listaFK = new Choice();
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

	public PedidosMod() {
		setTitle("Modificar Pedidos");
		setLayout(new FlowLayout());
		setSize(350, 200);
		setResizable(false);
		add(lista);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

		// Dialogo
		dlgMod.setResizable(false);
		dlgMod.setSize(300, 150);
		dlgMod.setLayout(new GridLayout(5, 1));
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
		panel3.add(listaFK);
		panelBoton.add(confirmar);
		panelBoton.add(limpiar);
		dlgMod.add(panel0);
		dlgMod.add(panel1);
		dlgMod.add(panel2);
		dlgMod.add(panel3);
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
		listaFK.addItemListener(this);
		lista.add("Seleccionar la entrada a modificar");

		// Preparar el statement para rellenar el choice
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT * FROM Pedidos");
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
		// Preparar el statement para rellenar el choice de la FK
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT * FROM Clientes");
			while (rs.next()) {
				s2 = Integer.toString(rs.getInt(1));
				s2 = s2 + "-" + rs.getString(2);
				listaFK.add(s2);
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
		String[] array = lista.getSelectedItem().toString().split("-");
		idParaModificar = array[0];
		// Si el dialogo está invisible rellena los valores
		if (dlgMod.isVisible() == false) {
			try {
				Connection connection = Conexion.getConexion();
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = statement.executeQuery("SELECT * FROM Pedidos WHERE idPedido=" + idParaModificar);
				rs.next();
				// Poner en los TextField los valores obtenidos del 1º
				text0.setText(Integer.toString(rs.getInt(1)));
				text1.setText(rs.getString(2));
				text2.setText(rs.getString(3));
				String FK = Integer.toString(rs.getInt(4));
				rs = statement.executeQuery("SELECT * FROM Clientes WHERE idCliente=" + FK);
				rs.next();
				listaFK.select(rs.getInt(1) - 1);
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
		// Al confirmar realiza el UPDATE, al limpiar vacía los textfield y
		// listaFK a indice 0
		if (confirmar.equals(ae.getSource())) {
			diaSeguro.setVisible(true);
		}
		if (btnNo.equals(ae.getSource())) {
			diaSeguro.setVisible(false);
		}
		if (btnSi.equals(ae.getSource())) {
			String[] array2 = listaFK.getSelectedItem().toString().split("-");
			fk = array2[0];
			Connection connection = Conexion.getConexion();
			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate("UPDATE Pedidos SET fecha='" + text1.getText() + "' ,  urgente='"
						+ text2.getText() + "' , idClienteFK=" + fk + " WHERE idPedido=" + idParaModificar);
				dlgMod.setVisible(false);
				diaSeguro.setVisible(false);
				new DiaCorrecto();
				statement.close();
			} catch (SQLException se) {
				System.out.println(se.getMessage());
				new DiaError();
			}
			lista.removeAll();
			lista.add("Seleccionar la entrada a modificar");
			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = statement.executeQuery("SELECT * FROM Pedidos");
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
			listaFK.select(0);
		}

	}

	public static void main(String[] args) {
		new PedidosMod();
	}

}
