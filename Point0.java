package packBase;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

public class Point0 implements GLEventListener {

	// variáveis
	protected double width;
	protected double height;
	protected GL gl;

	// construtor
	public Point0(GLCanvas canvas, double width, double height) {
		this.width = width;
		this.height = height;
		this.gl = canvas.getGL();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// desenhar ponto - vermelho
		this.gl.glPointSize(10);
		this.gl.glColor3f(1.0f, 0.0f, 0.0f);
		this.gl.glBegin(GL.GL_POINTS);
		this.gl.glVertex2d(this.width / 2.0, this.height / 2.0);
		this.gl.glEnd();
	}

	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// especificar a cor do fundo - preto
		this.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		this.gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// alterar variáveis
		this.width = (double) width;
		this.height = (double) height;
		// especificar a área de desenho
		this.gl.glMatrixMode(GL.GL_PROJECTION);
		this.gl.glLoadIdentity();
		this.gl.glOrtho(0.0, this.width, 0.0, this.height, -1.0, 1.0);
		// mudar para a matriz do modelo
		this.gl.glMatrixMode(GL.GL_MODELVIEW);
		this.gl.glLoadIdentity();
	}

}