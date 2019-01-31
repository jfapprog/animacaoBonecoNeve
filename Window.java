package packBase;

import java.awt.BorderLayout;

import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {

	// construtor
	public Window(String title, GLCanvas canvas, int width, int height) {
		super(title);
		this.add(canvas, BorderLayout.CENTER);
		this.setupWindow(width, height);
	}

	// configurar a janela
	public void setupWindow(int width, int height) {
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}