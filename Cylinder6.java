package packBase;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;

public class Cylinder6 extends Cone5 {

	// construtor
	public Cylinder6(GLCanvas canvas, double width, double height) {
		super(canvas, width, height);
	}

	// subdividir os triângulos
	protected void subDivideCylinder(float[] v1, float[] v2, int numberSplit) {
		float[] v0 = new float[] { 0.0f, 0.0f, 0.0f };
		float[] v11 = new float[3];
		float[] v22 = new float[3];

		// condição de paragem
		if (numberSplit == 0) {
			// desenhar a base inferior
			this.drawTriangle(v1, v2, v0);
			// desenhar a base superior
			for (int i = 0; i < 2; i++) {
				v11[i] = v1[i];
				v22[i] = v2[i];
			}
			// altura do cilindro
			v11[2] = 1.0f;
			v22[2] = 1.0f;
			v0[2] = 1.0f;
			this.drawTriangle(v11, v22, v0);
			// desenhar a superfície lateral
			this.gl.glBegin(GL.GL_POLYGON);
			this.gl.glVertex3f(v11[0], v11[1], v11[2]);
			this.gl.glVertex3f(v22[0], v22[1], v22[2]);
			this.gl.glVertex3f(v2[0], v2[1], v2[2]);
			this.gl.glVertex3f(v1[0], v1[1], v1[2]);
			this.gl.glEnd();
			return;
		}

		float[] v12 = new float[3];
		// parte recursiva
		for (int i = 0; i < 3; i++)
			v12[i] = v1[i] + v2[i];
		this.normalize(v12);
		this.subDivideCylinder(v1, v12, numberSplit - 1);
		this.subDivideCylinder(v12, v2, numberSplit - 1);
	}

	// desenha prisma/cilindro
	protected void drawCylinder(int numberSplit) {
		this.subDivideCylinder(this.vertices[0], this.vertices[1], numberSplit);
		this.subDivideCylinder(this.vertices[1], this.vertices[2], numberSplit);
		this.subDivideCylinder(this.vertices[2], this.vertices[3], numberSplit);
		this.subDivideCylinder(this.vertices[3], this.vertices[0], numberSplit);
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
			// desenhar prisma/cilindro
			this.drawCylinder(this.depth);
		}
		// restaurar matriz anterior
		this.gl.glPopMatrix();
	}

}
