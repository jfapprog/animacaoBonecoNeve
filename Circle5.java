package packBase;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;

public class Circle5 extends Point0 {

	// variáveis
	protected int radius;
	protected int flip; // 1 -> crescer, -1 -> decrescer
	protected int depth; // número de subdivisões
	protected float[][] vertices;
	protected float[][] colorVertices;

	// construtor
	public Circle5(GLCanvas canvas, double width, double height) {
		super(canvas, width, height);
		this.radius = 2;
		this.flip = 1;
		this.depth = 0;
		this.vertices = new float[][] { { 1.0f, 0.0f, 0.0f }, { 0.0f, 1.0f, 0.0f }, { -1.0f, 0.0f, 0.0f },
				{ 0.0f, -1.0f, 0.0f } };
		this.colorVertices = new float[][] { { 1.0f, 0.0f, 0.0f }, { 0.0f, 1.0f, 0.0f }, { 0.0f, 0.0f, 1.0f } };
	}

	// desenhar um triângulo
	protected void drawTriangle(float[] v1, float[] v2, float[] v3) {
		// desenhar triângulo - uma cor por vértice
		this.gl.glBegin(GL.GL_TRIANGLES);
		this.gl.glColor3f(this.colorVertices[0][0], this.colorVertices[0][1], this.colorVertices[0][2]);
		this.gl.glVertex3f(v1[0], v1[1], v1[2]);
		this.gl.glColor3f(this.colorVertices[1][0], this.colorVertices[1][1], this.colorVertices[1][2]);
		this.gl.glVertex3f(v2[0], v2[1], v2[2]);
		this.gl.glColor3f(this.colorVertices[2][0], this.colorVertices[2][1], this.colorVertices[2][2]);
		this.gl.glVertex3f(v3[0], v3[1], v3[2]);
		this.gl.glEnd();
	}

	// normalizar um vetor
	protected void normalize(float[] v) {
		float norm = (float) (Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]));
		if (norm == 0)
			return;
		v[0] /= norm;
		v[1] /= norm;
		v[2] /= norm;
	}

	// subdividir um circulo em triângulos - recursivo
	protected void subDivideCircle(float[] v1, float[] v2, int numberSplit) {
		float[] v11 = new float[3];
		float[] v22 = new float[3];
		float[] v00 = new float[] { 0.0f, 0.0f, 0.0f };
		float[] v12 = new float[3];

		// condição de paragem
		if (numberSplit == 0) {
			for (int i = 0; i < 3; i++) {
				v11[i] = v1[i] * this.radius;
				v22[i] = v2[i] * this.radius;
			}
			this.drawTriangle(v11, v22, v00);
			return;
		}

		// parte recursiva
		for (int i = 0; i < 3; i++)
			v12[i] = v1[i] + v2[i];
		this.normalize(v12);
		this.subDivideCircle(v1, v12, numberSplit - 1);
		this.subDivideCircle(v12, v2, numberSplit - 1);
	}

	// desenhar o polígono/circulo
	protected void drawCircle(int numberSplit) {
		this.subDivideCircle(this.vertices[0], this.vertices[1], numberSplit);
		this.subDivideCircle(this.vertices[1], this.vertices[2], numberSplit);
		this.subDivideCircle(this.vertices[2], this.vertices[3], numberSplit);
		this.subDivideCircle(this.vertices[3], this.vertices[0], numberSplit);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// quando o raio é muito grande ou muito pequeno, alterar direção de
		// crescimento
		if (this.radius >= (this.height / 2) || this.radius <= 1) {
			this.flip = -this.flip;
			this.depth++;
			this.depth = this.depth % 7;
		}
		// incrementar ou decrementar o raio
		this.radius += this.flip;

		// apagar circulo anterior
		this.gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// desenhar polígono/circulo
		this.drawCircle(this.depth);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// alterar variáveis
		this.width = (double) width;
		this.height = (double) height;
		// especificar a área de desenho
		this.gl.glMatrixMode(GL.GL_PROJECTION);
		this.gl.glLoadIdentity();
		this.gl.glOrtho(-this.width / 2.0, this.width / 2.0, -this.height / 2.0, this.height / 2.0, -1.0, 1.0);
		// mudar para a matriz do modelo
		this.gl.glMatrixMode(GL.GL_MODELVIEW);
		this.gl.glLoadIdentity();
	}

}
