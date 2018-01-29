package es.studium.borrar;

import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.studium.conectar.Conexion;
import es.studium.proyecto.DiaCorrecto;
import es.studium.proyecto.DiaError;

public class BorrarDatos extends JFrame implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;
	Connection connection = Conexion.getConexion();
	DiaError error;
	String valorSelec = "";
	String tabla2 = "";
	Statement statement = null;
	ResultSet rs = null;
	String s = "";
	ResultSetMetaData rsmd;
	String idParaBorrar, idParaBorrar2;
	// Principal
	Choice lista = new Choice();
	// Dialogo emergente
	JLabel seguro = new JLabel("¿Está seguro de eliminar esta entrada?: ", JLabel.CENTER);
	JLabel entrada = new JLabel("", JLabel.CENTER);
	JLabel riesgos = new JLabel("Podrían eliminarse entradas relacionadas en: ", JLabel.CENTER);
	JLabel tablas = new JLabel("", JLabel.CENTER);
	JButton aceptar = new JButton("Sí");
	JButton cancelar = new JButton("No");
	JPanel botones = new JPanel(new GridLayout(1, 2));
	JDialog dialogo = new JDialog(this, "Borrado", true);

	public BorrarDatos(String tabla) {
		// Principal
		tabla2 = tabla;
		setLayout(new FlowLayout());
		setSize(350, 200);
		setResizable(false);
		add(lista);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		// Dialogo emergente
		entrada.setForeground(new Color(0, 110, 0));
		tablas.setForeground(new Color(0, 110, 0));
		botones.add(aceptar);
		botones.add(cancelar);
		dialogo.add(seguro);
		dialogo.add(entrada);
		dialogo.add(riesgos);
		dialogo.add(tablas);
		dialogo.add(botones);
		dialogo.setLayout(new GridLayout(5, 1));
		dialogo.setLocationRelativeTo(null);
		dialogo.setResizable(false);
		dialogo.setSize(300, 150);
		dialogo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// Listeners
		lista.addItemListener(this);
		lista.add("Seleccionar la entrada a eliminar");
		aceptar.addActionListener(this);
		cancelar.addActionListener(this);

		// Preparar la lista de opciones, variará si son las tablas N:M o no
		if ("PedidosJuguetes".equals(tabla) || ("VentasJuguetes".equals(tabla))) {
			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = statement.executeQuery("SELECT * FROM " + tabla);
				while (rs.next()) {
					s = Integer.toString(rs.getInt(1));
					s = s + "-" + Integer.toString(rs.getInt(2));
					lista.add(s);
				}
			} catch (SQLException e) {
				System.out.println("Error en la sentencia SQL:" + e.toString());
			}
		} else {
			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = statement.executeQuery("SELECT * FROM " + tabla);
				while (rs.next()) {
					s = Integer.toString(rs.getInt(1));
					s = s + "-" + rs.getString(2);
					lista.add(s);
				}
			} catch (SQLException e) {
				System.out.println("Error en la sentencia SQL:" + e.toString());
			}
		}
	}

	public void itemStateChanged(ItemEvent ie) {
		// Alamacenaremos los valores seleccionados en un array
		//para su posterior uso
		String[] array = ie.getItem().toString().split("-");
		idParaBorrar = array[0];
		idParaBorrar2 = array[1];
		entrada.setText(ie.getItem().toString());
		
		if (tabla2.equals("Clientes")) {
			tablas.setText("Pedidos, PedidosJuguetes");
		} else if (tabla2.equals("Empleados")) {
			tablas.setText("Ventas, VentasJuguetes");
		} else if (tabla2.equals("Juguetes")) {
			tablas.setText("PedidosJuguetes, VentasJuguetes");
		} else if (tabla2.equals("Pedidos")) {
			tablas.setText("PedidosJuguetes");
		} else if (tabla2.equals("Proveedores")) {
			tablas.setText("PedidosJuguetes, VentasJuguetes");
		} else if (tabla2.equals("Ventas")) {
			tablas.setText("VentasJuguetes");
		} else {
			tablas.setText("No hay tablas afectadas");
		}
		dialogo.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		// Si cancelamos se oculta el diálogo
		if (cancelar.equals(ae.getSource())) {
			dialogo.setVisible(false);
		}
		//Al aceptar, dependiendo de si se trata de las tablas N:M o no la sentencia variará
		else if (aceptar.equals(ae.getSource())) {
			if ("PedidosJuguetes".equals(tabla2) || ("VentasJuguetes".equals(tabla2))) {
				try {
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rsmd = rs.getMetaData();
					statement.executeUpdate(
							"DELETE FROM " + tabla2 + " WHERE " + rsmd.getColumnName(1) + " =" + idParaBorrar+" AND " + rsmd.getColumnName(2) + " =" + idParaBorrar2);
					dialogo.setVisible(false);
					new DiaCorrecto();
					valorSelec = lista.getSelectedItem();
					lista.remove(valorSelec);
					statement.close();

				} catch (SQLException se) {
					dialogo.setVisible(false);
					new DiaError();
					System.out.println("Error en la sentencia SQL:" + se.toString());
				}
			} else {
				try {
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rsmd = rs.getMetaData();
					statement.executeUpdate(
							"DELETE FROM " + tabla2 + " WHERE " + rsmd.getColumnName(1) + " =" + idParaBorrar);
					dialogo.setVisible(false);
					new DiaCorrecto();
					valorSelec = lista.getSelectedItem();
					lista.remove(valorSelec);
					statement.close();

				} catch (SQLException se) {
					dialogo.setVisible(false);
					new DiaError();
					System.out.println("Error en la sentencia SQL:" + se.toString());
				}
			}
		}
	}

	public static void main(String[] args) {
	}
}
