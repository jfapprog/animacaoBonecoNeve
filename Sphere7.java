package packBase;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;

public class Sphere7 extends Cylinder6 {

	// variáveis
	protected float[][] vertSph;

	// construtor
	public Sphere7(GLCanvas canvas, double width, double height) {
		super(canvas, width, height);
		vertSph = new float[][] { { 1.0f, 0.0f, 0.0f }, { 0.0f, 1.0f, 0.0f }, { 0.0f, 0.0f, 1.0f },
				{ -1.0f, 0.0f, 0.0f }, { 0.0f, -1.0f, 0.0f }, { 0.0f, 0.0f, -1.0f } };
	}

	// subdividir os triângulos
	protected void subDivideSphere(float[] v1, float[] v2, float[] v3, int numberSplit) {

		// condição de paragem
		if (numberSplit == 0) {
			this.drawTriangle(v1, v2, v3);
			return;
		}
		// parte recursiva
		float[] v12 = new float[3];
		float[] v23 = new float[3];
		float[] v31 = new float[3];
		for (int i = 0; i < 3; i++) {
			v12[i] = v1[i] + v2[i];
			v23[i] = v2[i] + v3[i];
			v31[i] = v3[i] + v1[i];
		}
		this.normalize(v12);
		this.normalize(v23);
		this.normalize(v31);
		this.subDivideSphere(v1, v12, v31, numberSplit - 1);
		this.subDivideSphere(v2, v23, v12, numberSplit - 1);
		this.subDivideSphere(v3, v31, v23, numberSplit - 1);
		this.subDivideSphere(v12, v23, v31, numberSplit - 1);
	}

	// desenhar octaedro/esfera
	protected void drawSphere(int numberSplit) {
		this.subDivideSphere(this.vertSph[0], this.vertSph[1], this.vertSph[2], numberSplit);
		this.subDivideSphere(this.vertSph[0], this.vertSph[2], this.vertSph[4], numberSplit);
		this.subDivideSphere(this.vertSph[0], this.vertSph[4], this.vertSph[5], numberSplit);
		this.subDivideSphere(this.vertSph[0], this.vertSph[5], this.vertSph[1], numberSplit);

		this.subDivideSphere(this.vertSph[3], this.vertSph[1], this.vertSph[5], numberSplit);
		this.subDivideSphere(this.vertSph[3], this.vertSph[5], this.vertSph[4], numberSplit);
		this.subDivideSphere(this.vertSph[3], this.vertSph[4], this.vertSph[2], numberSplit);
		this.subDivideSphere(this.vertSph[3], this.vertSph[2], this.vertSph[1], numberSplit);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// quando o raio é muito grande ou muito pequeno, alterar direção de
		// crescimento
		if (this.radius >= (this.height / 2) || this.radius <= 1) {
			this.flip = -this.flip;
			this.depth++;
			this.depth = this.depth % 5;
		}
		// incrementar ou decrementar o raio
		this.radius += this.flip;

		// limpar buffers
		this.gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		this.gl.glRotatef(1.0f, 1.0f, 1.0f, 1.0f);
		// copiar matriz atual
		this.gl.glPushMatrix();
		{
			// realizar a escala
			this.gl.glScalef(this.radius, this.radius, this.radius);
			// desenhar octaedro/esfera
			this.drawSphere(this.depth);
		}
		// restaurar matriz atual
		this.gl.glPopMatrix();
	}

}
