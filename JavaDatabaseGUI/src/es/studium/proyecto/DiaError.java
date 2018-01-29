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

public class DiaError extends JFrame{

	private static final long serialVersionUID = 1L;
	URL resource1 = getClass().getClassLoader().getResource("error.png");
	URL resource2 = getClass().getClassLoader().getResource("dialogos.wav");
	
	JLabel opErronea = new JLabel("¡La operación no se pudo realizar!");
	JLabel opErronea1 = new JLabel("Revise la información introducida");
	JDialog diaError = new JDialog(this, "Operación Actualización", true);
	JLabel background = new JLabel(new ImageIcon(resource1));
	public DiaError()
	{
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(resource2);
			clip.open(ais);
			clip.loop(0);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		diaError.setTitle("Error");
		diaError.setLayout(new FlowLayout());
		diaError.add(opErronea);
		diaError.add(opErronea1);
		diaError.add(background);
		diaError.setSize(250, 160);
		diaError.setLocationRelativeTo(null);
		diaError.setResizable(false);
		diaError.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		opErronea.setForeground (Color.red);
		diaError.setVisible(true);
		
	}
	public static void main(String[] args) {
		new DiaError();
	}
}
