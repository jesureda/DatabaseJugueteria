package es.studium.proyecto;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DiaCorrecto extends JFrame {

	private static final long serialVersionUID = 1L;
	URL resource1 = getClass().getClassLoader().getResource("success.png");
	URL resource2 = getClass().getClassLoader().getResource("dialogos.wav");
	
	JLabel opCorrecta = new JLabel("¡Operación realizada correctamente!");
	JDialog diaCorrecto = new JDialog(this, "Operación Actualización", true);
	JLabel background = new JLabel(new ImageIcon(resource1));

	public DiaCorrecto() {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(resource2);
			clip.open(ais);
			clip.loop(0);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		diaCorrecto.setTitle("Operación Exitosa");
		diaCorrecto.setLayout(new FlowLayout());
		diaCorrecto.add(opCorrecta);
		diaCorrecto.add(background);
		diaCorrecto.setSize(250, 150);
		diaCorrecto.setLocationRelativeTo(null);
		diaCorrecto.setResizable(false);
		diaCorrecto.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		opCorrecta.setForeground(new Color(0, 110, 0));
		diaCorrecto.setVisible(true);

	}

	public static void main(String[] args) {
		new DiaCorrecto();
	}
}
