package es.studium.recorrido;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import es.studium.conectar.Conexion;

public class JuguetesRec extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	Statement statement = null;
	Connection connection = Conexion.getConexion();
	ResultSet rs = null;
	JPanel panel1 = new JPanel(new GridLayout(1, 2));
	JPanel panel2 = new JPanel(new GridLayout(1, 2));
	JPanel panel3 = new JPanel(new GridLayout(1, 2));
	JPanel panel4 = new JPanel(new GridLayout(1, 2));
	JPanel panel5 = new JPanel(new GridLayout(1, 2));
	JPanel panel6 = new JPanel(new GridLayout(1, 2));
	JLabel label1 = new JLabel("Nombre: ", JLabel.CENTER);
	JLabel label2 = new JLabel("Descripción: ", JLabel.CENTER);
	JLabel label3 = new JLabel("Precio: ", JLabel.CENTER);
	JLabel label4 = new JLabel("Cantidad: ", JLabel.CENTER);
	JLabel label5 = new JLabel("Edad Rec: ", JLabel.CENTER);
	JLabel label6 = new JLabel("idProveedorFK: ", JLabel.CENTER);
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	JTextField text3 = new JTextField();
	JTextField text4 = new JTextField();
	JTextField text5 = new JTextField();
	JTextField text6 = new JTextField();
	JPanel panel0 = new JPanel(new GridLayout(1, 2));
	JLabel label0 = new JLabel("Identificador: ", JLabel.CENTER);
	JTextField text0 = new JTextField();
	JPanel panelBotones = new JPanel(new GridLayout(1, 4));
	JButton next = new JButton(">");
	JButton previous = new JButton("<");
	JButton first = new JButton("<<");
	JButton last = new JButton(">>");

	public JuguetesRec() {
		// Ventana
		setTitle("Juguetes");
		setResizable(false);
		setSize(500, 200);
		setLayout(new GridLayout(8, 1));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		panel0.add(label0);
		panel0.add(text0);
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
		panel6.add(label6);
		panel6.add(text6);
		panelBotones.add(first);
		panelBotones.add(previous);
		panelBotones.add(next);
		panelBotones.add(last);
		add(panel0);
		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);
		add(panel5);
		add(panel6);
		add(panelBotones);
		// Listeners
		first.addActionListener(this);
		last.addActionListener(this);
		next.addActionListener(this);
		previous.addActionListener(this);
		
		// Rellenamos los campos con los datos del Juguete
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT * FROM Juguetes");
			rs.next();
			// Poner en los TextField los valores obtenidos
			text0.setText(Integer.toString(rs.getInt(1)));
			text1.setText(rs.getString(2));
			text2.setText(rs.getString(3));
			text3.setText(Double.toString(rs.getDouble(4)));
			text4.setText(Integer.toString(rs.getInt(5)));
			text5.setText(rs.getString(6));
			text6.setText(Integer.toString(rs.getInt(7)));

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		try {
			if (next.equals(ae.getSource())) {
				// Si no hemos llegado al final
				if (rs.next()) {
					// Poner en los TextField los valores obtenidos
					text0.setText(Integer.toString(rs.getInt(1)));
					text1.setText(rs.getString(2));
					text2.setText(rs.getString(3));
					text3.setText(Double.toString(rs.getInt(4)));
					text4.setText(Integer.toString(rs.getInt(5)));
					text5.setText(rs.getString(6));
					text6.setText(Integer.toString(rs.getInt(7)));
				}
				// Al hacer rs.next, nos hemos salido de la lista de
				// resultados
				else if (rs.isAfterLast()) {
					rs.first();
					text0.setText(Integer.toString(rs.getInt(1)));
					text1.setText(rs.getString(2));
					text2.setText(rs.getString(3));
					text3.setText(Double.toString(rs.getInt(4)));
					text4.setText(Integer.toString(rs.getInt(5)));
					text5.setText(rs.getString(6));
					text6.setText(Integer.toString(rs.getInt(7)));
				}

			} else if (previous.equals(ae.getSource())) {
				// Si no hemos llegado al principio
				if (rs.previous()) {
					// Poner en los TextField los valores obtenidos
					text0.setText(Integer.toString(rs.getInt(1)));
					text1.setText(rs.getString(2));
					text2.setText(rs.getString(3));
					text3.setText(Double.toString(rs.getInt(4)));
					text4.setText(Integer.toString(rs.getInt(5)));
					text5.setText(rs.getString(6));
					text6.setText(Integer.toString(rs.getInt(7)));
				}
				// Al hacer rs.previous, nos hemos salido de la lista de
				// resultados
				else if (rs.isBeforeFirst()) {
					rs.last();
					text0.setText(Integer.toString(rs.getInt(1)));
					text1.setText(rs.getString(2));
					text2.setText(rs.getString(3));
					text3.setText(Double.toString(rs.getInt(4)));
					text4.setText(Integer.toString(rs.getInt(5)));
					text5.setText(rs.getString(6));
					text6.setText(Integer.toString(rs.getInt(7)));
				}
			} else if (first.equals(ae.getSource())) {
				rs.first();
				text0.setText(Integer.toString(rs.getInt(1)));
				text1.setText(rs.getString(2));
				text2.setText(rs.getString(3));
				text3.setText(Double.toString(rs.getInt(4)));
				text4.setText(Integer.toString(rs.getInt(5)));
				text5.setText(rs.getString(6));
				text6.setText(Integer.toString(rs.getInt(7)));

			} else if (last.equals(ae.getSource())) {
				rs.last();
				text0.setText(Integer.toString(rs.getInt(1)));
				text1.setText(rs.getString(2));
				text2.setText(rs.getString(3));
				text3.setText(Double.toString(rs.getInt(4)));
				text4.setText(Integer.toString(rs.getInt(5)));
				text5.setText(rs.getString(6));
				text6.setText(Integer.toString(rs.getInt(7)));

			}
		} catch (SQLException e) {
			System.out.println("Error en la sentencia SQL" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
