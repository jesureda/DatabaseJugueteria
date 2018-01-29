package es.studium.conectar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MetaTablas extends JFrame {
	private static final long serialVersionUID = 1L;
	Connection connection=Conexion.getConexion();
	DatabaseMetaData dbmd;
	JPanel pnl1 = new JPanel (new GridLayout(1,3));
	JLabel lblMeta1 = new JLabel("Campo: ");
	JLabel lblMeta2 = new JLabel("Tipo: ");
	JLabel lblMeta3 = new JLabel("Tamaño: ");

	public MetaTablas(String tabla)
	{
		setLayout(new FlowLayout());
		setTitle("MetaTabla: "+tabla);
		pnl1.add(lblMeta1);
		pnl1.add(lblMeta2);
		pnl1.add(lblMeta3);
		lblMeta1.setPreferredSize(new Dimension(90, 20)); 
		lblMeta2.setPreferredSize(new Dimension(90, 20)); 
		lblMeta3.setPreferredSize(new Dimension(90, 20)); 
		lblMeta1.setForeground(new Color(0, 0, 150));
		lblMeta2.setForeground(new Color(0, 0, 150));
		lblMeta3.setForeground(new Color(0, 0, 150));
		add(pnl1);
		try {
			dbmd = connection.getMetaData();
			ResultSet rs = dbmd.getColumns(null,null,tabla,null);
			while(rs.next())
			{
				JPanel pnl = new JPanel (new GridLayout(1,3));
				JLabel lblTab1 = new JLabel(rs.getString("COLUMN_NAME"));
				JLabel lblTab2 = new JLabel(rs.getString("TYPE_NAME"));
				JLabel lblTab3 = new JLabel(rs.getString("COLUMN_SIZE"));
				pnl.add(lblTab1);
				pnl.add(lblTab2);
				pnl.add(lblTab3);
				lblTab1.setPreferredSize(new Dimension(90, 20)); 
				lblTab2.setPreferredSize(new Dimension(90, 20)); 
				lblTab3.setPreferredSize(new Dimension(90, 20)); 
				add(pnl);
			}
			if (tabla.equals("Juguetes"))
			{
				setSize(320, 240);
			}
			else
				{setSize(300, 210);}
			setLocationRelativeTo(null);
			setVisible(true);
			setResizable(false);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
	}
}
