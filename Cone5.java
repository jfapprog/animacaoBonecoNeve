package packBase;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;

public class Cone5 extends Circle5 {

	// construtor
	public Cone5(GLCanvas canvas, double width, double height) {
		super(canvas, width, height);
	}

	// subdividir os triângulos
	protected void subDivideCone(float[] v1, float[] v2, int numberSplit) {
		float[] v0 = new float[] { 0.0f, 0.0f, 0.0f };
		float[] v12 = new float[3];

		// condição de paragem
		if (numberSplit == 0) {
			// desenhar base
			this.drawTriangle(v1, v2, v0);
			// desenhar superfície lateral
			v0[2] = 1.0f;
			this.drawTriangle(v1, v2, v0);
			return;
		}

		// parte recursiva
		for (int i = 0; i < 3; i++)
			v12[i] = v1[i] + v2[i];
		this.normalize(v12);
		this.subDivideCone(v1, v12, numberSplit - 1);
		this.subDivideCone(v12, v2, numberSplit - 1);
	}

	// desenhar pirâmide/cone
	protected void drawCone(int numberSplit) {
		this.subDivideCone(this.vertices[0], this.vertices[1], numberSplit);
		this.subDivideCone(this.vertices[1], this.vertices[2], numberSplit);
		this.subDivideCone(this.vertices[2], this.vertices[3], numberSplit);
		this.subDivideCone(this.vertices[3], this.vertices[0], numberSplit);
	}

	@Override
	public void display(GLAutoDrawable drawable) {

		// limpar buffers
		this.gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		// quando o raio é muito grande ou muito pequeno, alterar direção de
		// crescimento
		if (this.radius >= (this.height / 2) || this.radius <= 1) {
			this.flip = -this.flip;
			this.depth++;
			this.depth = this.depth % 5;
			// gerar cores aleatórias para os vértices
			this.colorVertices[0][0] = (float) Math.random();
			this.colorVertices[0][1] = (float) Math.random();
			this.colorVertices[0][2] = (float) Math.random();
			this.colorVertices[1][0] = (float) Math.random();
			this.colorVertices[1][1] = (float) Math.random();
			this.colorVertices[1][2] = (float) Math.random();
			this.colorVertices[2][0] = (float) Math.random();
			this.colorVertices[2][1] = (float) Math.random();
			this.colorVertices[2][2] = (float) Math.random();
		}
		// incrementar ou decrementar o raio
		this.radius += this.flip;

		this.gl.glRotatef(1.0f, 1.0f, 1.0f, 1.0f);
		// copiar matriz atual
		this.gl.glPushMatrix();
		{
			// realizar a escala
			this.gl.glScalef(this.radius, this.radius, this.radius);
			// desenhar pirâmide/cone
			this.drawCone(this.depth);
		}
		// restaurar matriz anterior
		this.gl.glPopMatrix();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		this.width = (double) width;
		this.height = (double) height;

		// ativar o depth-buffer para eliminação de superfícies ocultas
		this.gl.glEnable(GL.GL_DEPTH_TEST);
		// especificar área de desenho
		this.gl.glMatrixMode(GL.GL_PROJECTION);
		this.gl.glLoadIdentity();
		this.gl.glOrtho(-this.width / 2.0, this.width / 2.0, -this.height / 2.0, this.height / 2.0, -this.width,
				this.width);
		// mudar para a matriz do modelo
		this.gl.glMatrixMode(GL.GL_MODELVIEW);
		this.gl.glLoadIdentity();
	}

}
