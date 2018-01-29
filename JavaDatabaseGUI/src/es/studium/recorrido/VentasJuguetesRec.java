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

public class VentasJuguetesRec extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	Statement statement = null;
	Connection connection = Conexion.getConexion();
	ResultSet rs = null;
	JPanel panel1 = new JPanel(new GridLayout(1, 2));
	JPanel panel2 = new JPanel(new GridLayout(1, 2));
	JLabel label1 = new JLabel("idVentaFK: ", JLabel.CENTER);
	JLabel label2 = new JLabel("idJugueteFK: ", JLabel.CENTER);
	JTextField text1 = new JTextField();
	JTextField text2 = new JTextField();
	JPanel panelBotones = new JPanel(new GridLayout(1, 4));
	JButton next = new JButton(">");
	JButton previous = new JButton("<");
	JButton first = new JButton("<<");
	JButton last = new JButton(">>");

	public VentasJuguetesRec() {
		// Ventana
		setTitle("VentasJuguetes");
		setResizable(false);
		setSize(300, 120);
		setLayout(new GridLayout(3, 1));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		panel1.add(label1);
		panel1.add(text1);
		panel2.add(label2);
		panel2.add(text2);
		panelBotones.add(first);
		panelBotones.add(previous);
		panelBotones.add(next);
		panelBotones.add(last);
		add(panel1);
		add(panel2);
		add(panelBotones);
		// Listeners
		first.addActionListener(this);
		last.addActionListener(this);
		next.addActionListener(this);
		previous.addActionListener(this);

		// Rellenamos los campos con los datos de la VentaJuguete
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery("SELECT * FROM VentasJuguetes");
			rs.next();
			// Poner en los TextField los valores obtenidos del 1º
			text1.setText(Integer.toString(rs.getInt(1)));
			text2.setText(Integer.toString(rs.getInt(2)));

		} catch (SQLException e) {
			System.out.println("Error en la sentencia SQL");
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
					text1.setText(Integer.toString(rs.getInt(1)));
					text2.setText(Integer.toString(rs.getInt(2)));
				}
				// Al hacer rs.next, nos hemos salido de la lista de
				// resultados
				else if (rs.isAfterLast()) {
					rs.first();
					text1.setText(Integer.toString(rs.getInt(1)));
					text2.setText(Integer.toString(rs.getInt(2)));
				}
			} else if (previous.equals(ae.getSource())) {
				// Si no hemos llegado al principio
				if (rs.previous()) {
					// Poner en los TextField los valores obtenidos
					text1.setText(Integer.toString(rs.getInt(1)));
					text2.setText(Integer.toString(rs.getInt(2)));
				}
				// Al hacer rs.previous, nos hemos salido de la lista de
				// resultados
				else if (rs.isBeforeFirst()) {
					rs.last();
					text1.setText(Integer.toString(rs.getInt(1)));
					text2.setText(Integer.toString(rs.getInt(2)));
				}
			} else if (first.equals(ae.getSource())) {
				rs.first();
				text1.setText(Integer.toString(rs.getInt(1)));
				text2.setText(Integer.toString(rs.getInt(2)));

			} else if (last.equals(ae.getSource())) {
				rs.last();
				text1.setText(Integer.toString(rs.getInt(1)));
				text2.setText(Integer.toString(rs.getInt(2)));
			}
		} catch (SQLException e) {
			System.out.println("Error en la sentencia SQL" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
