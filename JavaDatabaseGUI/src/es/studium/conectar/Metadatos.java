package es.studium.conectar;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Metadatos extends JFrame {
	private static final long serialVersionUID = 1L;
	JLabel lblMeta1 = new JLabel("Nombre del producto: ",JLabel.CENTER);
	JLabel lblMeta2 = new JLabel("Versión del producto: ",JLabel.CENTER);
	JLabel lblMeta3 = new JLabel("Usuario conectado: ",JLabel.CENTER);
	JLabel lblMeta4 = new JLabel("Driver JDBC: ",JLabel.CENTER);
	JLabel lblMeta5 = new JLabel("Versión del Driver: ",JLabel.CENTER);
	Connection connection=Conexion.getConexion();
	static DatabaseMetaData dbmd;
	public Metadatos()
	{
		setLayout(new GridLayout(5, 1));
		setTitle("Metadatos");
		add(lblMeta1);
		add(lblMeta2);
		add(lblMeta3);
		add(lblMeta4);
		add(lblMeta5);
		setSize(320, 175);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		try {

			dbmd = connection.getMetaData();
			lblMeta1.setText(lblMeta1.getText() + dbmd.getDatabaseProductName());
			lblMeta2.setText(lblMeta2.getText() + dbmd.getDatabaseProductVersion());
			lblMeta3.setText(lblMeta3.getText() + dbmd.getUserName());
			lblMeta4.setText(lblMeta4.getText() + dbmd.getDriverName());
			lblMeta5.setText(lblMeta5.getText() + dbmd.getDriverVersion());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
