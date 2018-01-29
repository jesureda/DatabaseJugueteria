package es.studium.proyecto;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import es.studium.borrar.BorrarDatos;
import es.studium.conectar.Conexion;
import es.studium.conectar.MetaTablas;
import es.studium.conectar.Metadatos;
import es.studium.insertar.ClientesIns;
import es.studium.insertar.EmpleadosIns;
import es.studium.insertar.JuguetesIns;
import es.studium.insertar.PedidosIns;
import es.studium.insertar.PedidosJuguetesIns;
import es.studium.insertar.ProveedoresIns;
import es.studium.insertar.VentasIns;
import es.studium.insertar.VentasJuguetesIns;
import es.studium.modificar.ClientesMod;
import es.studium.modificar.EmpleadosMod;
import es.studium.modificar.JuguetesMod;
import es.studium.modificar.PedidosJuguetesMod;
import es.studium.modificar.PedidosMod;
import es.studium.modificar.ProveedoresMod;
import es.studium.modificar.VentasJuguetesMod;
import es.studium.modificar.VentasMod;
import es.studium.recorrido.ClientesRec;
import es.studium.recorrido.EmpleadosRec;
import es.studium.recorrido.JuguetesRec;
import es.studium.recorrido.PedidosJuguetesRec;
import es.studium.recorrido.PedidosRec;
import es.studium.recorrido.ProveedoresRec;
import es.studium.recorrido.VentasJuguetesRec;
import es.studium.recorrido.VentasRec;

public class ProyectoDB extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	static Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	JMenuBar barra = new JMenuBar();
	JMenu mnDatos = new JMenu("Datos");
	JMenu mnMeta = new JMenu("Metadatos");
	JMenu mnMetaTab = new JMenu("Tablas");
	JMenu mi1 = new JMenu("Clientes");
	JMenu mi2 = new JMenu("Empleados");
	JMenu mi3 = new JMenu("Juguetes");
	JMenu mi4 = new JMenu("Pedidos");
	JMenu mi5 = new JMenu("Proveedores");
	JMenu mi6 = new JMenu("Ventas");
	JMenu mi7 = new JMenu("PedidosJuguetes");
	JMenu mi8 = new JMenu("VentasJuguetes");
	JMenuItem miTab1 = new JMenuItem("Clientes");
	JMenuItem miTab2 = new JMenuItem("Empleados");
	JMenuItem miTab3 = new JMenuItem("Proveedores");
	JMenuItem miTab4 = new JMenuItem("Juguetes");
	JMenuItem miGen = new JMenuItem("General");
	JMenuItem miReco1 = new JMenuItem("Recorrido");
	JMenuItem miInser1 = new JMenuItem("Inserción");
	JMenuItem miBorra1 = new JMenuItem("Borrado ID");
	JMenuItem miModif1 = new JMenuItem("Modificación ID");
	JMenuItem miReco2 = new JMenuItem("Recorrido");
	JMenuItem miInser2 = new JMenuItem("Inserción");
	JMenuItem miBorra2 = new JMenuItem("Borrado ID");
	JMenuItem miModif2 = new JMenuItem("Modificación ID");
	JMenuItem miReco3 = new JMenuItem("Recorrido");
	JMenuItem miInser3 = new JMenuItem("Inserción");
	JMenuItem miBorra3 = new JMenuItem("Borrado ID");
	JMenuItem miModif3 = new JMenuItem("Modificación ID");
	JMenuItem miReco4 = new JMenuItem("Recorrido");
	JMenuItem miInser4 = new JMenuItem("Inserción");
	JMenuItem miBorra4 = new JMenuItem("Borrado ID");
	JMenuItem miModif4 = new JMenuItem("Modificación ID");
	JMenuItem miReco5 = new JMenuItem("Recorrido");
	JMenuItem miInser5 = new JMenuItem("Inserción");
	JMenuItem miBorra5 = new JMenuItem("Borrado ID");
	JMenuItem miModif5 = new JMenuItem("Modificación ID");
	JMenuItem miReco6 = new JMenuItem("Recorrido");
	JMenuItem miInser6 = new JMenuItem("Inserción");
	JMenuItem miBorra6 = new JMenuItem("Borrado ID");
	JMenuItem miModif6 = new JMenuItem("Modificación ID");
	JMenuItem miReco7 = new JMenuItem("Recorrido");
	JMenuItem miInser7 = new JMenuItem("Inserción");
	JMenuItem miBorra7 = new JMenuItem("Borrado ID");
	JMenuItem miModif7 = new JMenuItem("Modificación ID");
	JMenuItem miReco8 = new JMenuItem("Recorrido");
	JMenuItem miInser8 = new JMenuItem("Inserción");
	JMenuItem miBorra8 = new JMenuItem("Borrado ID");
	JMenuItem miModif8 = new JMenuItem("Modificación ID");
	JMenuItem miSalir = new JMenuItem("Salir");
	
	URL resource1 = getClass().getClassLoader().getResource("Smyths.png");
	URL resource2 = getClass().getClassLoader().getResource("help.png");
	URL resource3 = getClass().getClassLoader().getResource("off.png");
	URL resource4 = getClass().getClassLoader().getResource("main.wav");
	
	JButton btnImg = new JButton(new ImageIcon(resource2));
	JButton btnImg2 = new JButton(new ImageIcon(resource3));
	JLabel lblCpr = new JLabel("Copyright©2017");
	JLabel lblAutor = new JLabel("Jesús Sureda Prieto");
	JLabel background = new JLabel(new ImageIcon(resource1));
	JDialog diaCampos = new JDialog(this, "Operación", true);
	JPanel panel = new JPanel(new GridLayout(2, 3));

	public ProyectoDB() {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(resource4);
			clip.open(ais);
			clip.loop(0);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		setSize(450, 380);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		getContentPane().setBackground(new Color(0,110,0)); 
		btnImg.setBackground(null);
		btnImg.setBorder(null);
		btnImg2.setBackground(null);
		btnImg2.setBorder(null);
		lblCpr.setForeground(Color.WHITE);
		lblCpr.setFont(new Font("Arial",Font.ITALIC, 12)); 
		lblAutor.setForeground(Color.WHITE);
		lblAutor.setFont(new Font("Arial",Font.ITALIC, 12)); 
		gbc.insets = new Insets(3, 0, 3, 0);
		
		gbc.weightx = 0.0;
		gbc.gridx = 2;
		gbc.gridy = 1;
		add(btnImg,gbc);
		
		gbc.weightx = 0.0;
		gbc.gridx = 3;
		gbc.gridy = 1;
		add(btnImg2,gbc);
		
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(background, gbc);
		
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		lblCpr.setHorizontalAlignment(JLabel.LEFT);
		add(lblCpr, gbc);

		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		lblAutor.setHorizontalAlignment(JLabel.RIGHT);
		add(lblAutor,gbc);
		
		setJMenuBar(barra);
		barra.add(mnDatos);
		barra.add(mnMeta);
		mnDatos.add(mi1);
		mnDatos.add(mi2);
		mnDatos.add(mi3);
		mnDatos.add(mi4);
		mnDatos.add(mi5);
		mnDatos.add(mi6);
		mnDatos.add(mi7);
		mnDatos.add(mi8);
		mnDatos.addSeparator();
		mnDatos.add(miSalir);
		mi1.add(miReco1);
		mi1.add(miInser1);
		mi1.add(miBorra1);
		mi1.add(miModif1);
		mi2.add(miReco2);
		mi2.add(miInser2);
		mi2.add(miBorra2);
		mi2.add(miModif2);
		mi3.add(miReco3);
		mi3.add(miInser3);
		mi3.add(miBorra3);
		mi3.add(miModif3);
		mi4.add(miReco4);
		mi4.add(miInser4);
		mi4.add(miBorra4);
		mi4.add(miModif4);
		mi5.add(miReco5);
		mi5.add(miInser5);
		mi5.add(miBorra5);
		mi5.add(miModif5);
		mi6.add(miReco6);
		mi6.add(miInser6);
		mi6.add(miBorra6);
		mi6.add(miModif6);
		mi7.add(miReco7);
		mi7.add(miInser7);
		mi7.add(miBorra7);
		mi7.add(miModif7);
		mi8.add(miReco8);
		mi8.add(miInser8);
		mi8.add(miBorra8);
		mi8.add(miModif8);
		mnMeta.add(miGen);
		mnMeta.add(mnMetaTab);
		mnMetaTab.add(miTab1);
		mnMetaTab.add(miTab2);
		mnMetaTab.add(miTab3);
		mnMetaTab.add(miTab4);
		
		miSalir.addActionListener(this);
		miGen.addActionListener(this);
		miReco1.addActionListener(this);
		miReco2.addActionListener(this);
		miReco3.addActionListener(this);
		miReco4.addActionListener(this);
		miReco5.addActionListener(this);
		miReco6.addActionListener(this);
		miReco7.addActionListener(this);
		miReco8.addActionListener(this);
		miBorra1.addActionListener(this);
		miBorra2.addActionListener(this);
		miBorra3.addActionListener(this);
		miBorra4.addActionListener(this);
		miBorra5.addActionListener(this);
		miBorra6.addActionListener(this);
		miBorra7.addActionListener(this);
		miBorra8.addActionListener(this);
		miInser1.addActionListener(this);
		miInser2.addActionListener(this);
		miInser3.addActionListener(this);
		miInser4.addActionListener(this);
		miInser5.addActionListener(this);
		miInser6.addActionListener(this);
		miInser7.addActionListener(this);
		miInser8.addActionListener(this);
		miModif1.addActionListener(this);
		miModif2.addActionListener(this);
		miModif3.addActionListener(this);
		miModif4.addActionListener(this);
		miModif5.addActionListener(this);
		miModif6.addActionListener(this);
		miModif7.addActionListener(this);
		miModif8.addActionListener(this);
		btnImg.addActionListener(this);
		btnImg2.addActionListener(this);
		miTab1.addActionListener(this);
		miTab2.addActionListener(this);
		miTab3.addActionListener(this);
		miTab4.addActionListener(this);
		Conexion.datosConexion();
	}

	public static void main(String[] args) {
		new ProyectoDB();
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		//Metadatos
		if (miGen.equals(ae.getSource())) {
			new Metadatos();
		}
		//MetaTablas
		if (miTab1.equals(ae.getSource())) {
			new MetaTablas("Clientes");
		}
		if (miTab2.equals(ae.getSource())) {
			new MetaTablas("Empleados");
		}
		if (miTab3.equals(ae.getSource())) {
			new MetaTablas("Proveedores");
		}
		if (miTab4.equals(ae.getSource())) {
			new MetaTablas("Juguetes");
		}
		// Tabla Clientes
		if (miBorra1.equals(ae.getSource())) {
			new BorrarDatos("Clientes");
		}
		if (miInser1.equals(ae.getSource())) {
			new ClientesIns();
		}
		if (miReco1.equals(ae.getSource())) {
			new ClientesRec();
		}
		if (miModif1.equals(ae.getSource())) {
			new ClientesMod();
		}

		// Tabla Empleados
		if (miBorra2.equals(ae.getSource())) {
			new BorrarDatos("Empleados");
		}
		if (miInser2.equals(ae.getSource())) {
			new EmpleadosIns();
		}
		if (miReco2.equals(ae.getSource())) {
			new EmpleadosRec();
		}
		if (miModif2.equals(ae.getSource())) {
			new EmpleadosMod();
		}

		// Tabla Juguetes
		if (miBorra3.equals(ae.getSource())) {
			new BorrarDatos("Juguetes");
		}
		if (miInser3.equals(ae.getSource())) {
			new JuguetesIns();
		}
		if (miReco3.equals(ae.getSource())) {
			new JuguetesRec();
		}
		if (miModif3.equals(ae.getSource())) {
			new JuguetesMod();
		}

		// Tabla Pedidos
		if (miBorra4.equals(ae.getSource())) {
			new BorrarDatos("Pedidos");
		}
		if (miInser4.equals(ae.getSource())) {
			new PedidosIns();
		}
		if (miReco4.equals(ae.getSource())) {
			new PedidosRec();
		}
		if (miModif4.equals(ae.getSource())) {
			new PedidosMod();
		}
		
		// Tabla Proveedores
		if (miBorra5.equals(ae.getSource())) {
			new BorrarDatos("Proveedores");
		}
		if (miInser5.equals(ae.getSource())) {
			new ProveedoresIns();
		}
		if (miReco5.equals(ae.getSource())) {
			new ProveedoresRec();
		}
		if (miModif5.equals(ae.getSource())) {
			new ProveedoresMod();
		}
		
		// Tabla Ventas
		if (miBorra6.equals(ae.getSource())) {
			new BorrarDatos("Ventas");
		}
		if (miInser6.equals(ae.getSource())) {
			new VentasIns();
		}
		if (miReco6.equals(ae.getSource())) {
			new VentasRec();
		}
		if (miModif6.equals(ae.getSource())) {
			new VentasMod();
		}
		
		// Tabla PedidosJuguetes
		if (miBorra7.equals(ae.getSource())) {
			new BorrarDatos("PedidosJuguetes");
		}
		if (miInser7.equals(ae.getSource())) {
			new PedidosJuguetesIns();
		}
		if (miReco7.equals(ae.getSource())) {
			new PedidosJuguetesRec();
		}
		if (miModif7.equals(ae.getSource())) {
			new PedidosJuguetesMod();
		}
		
		// Tabla VentasJuguetes
		if (miBorra8.equals(ae.getSource())) {
			new BorrarDatos("VentasJuguetes");
		}
		if (miInser8.equals(ae.getSource())) {
			new VentasJuguetesIns();
		}
		if (miReco8.equals(ae.getSource())) {
			new VentasJuguetesRec();
		}
		if (miModif8.equals(ae.getSource())) {
			new VentasJuguetesMod();
		}
		//Botones imagen
		if (btnImg.equals(ae.getSource())) {
			try {
				Desktop.getDesktop().browse(new URL("https://dev.mysql.com/doc/refman/5.7/en/introduction.html").toURI());
			} catch (Exception e) {
			}
		}
		if (btnImg2.equals(ae.getSource())) {
			System.exit(0);
		}
	}
}
