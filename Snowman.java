package packBase;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;

import com.sun.opengl.util.GLUT;

public class Snowman extends Sphere7 implements KeyListener {

	// vari�veis
	protected float alpha; // �ngulo de rota��o do tren� + boneco de neve
	protected float beta; // �ngulo de rota��o da lua
	protected float factor; // fator de escala da lua
	protected float[] gama; // �ngulos de rota��o dos flocos de neve
	protected float[] gamaIncr; // incremento ao �ngulo de rota��o dos flocos
	protected float[][] position; // posi��o dos flocos de neve
	protected GLUT glut;

	// construtor
	public Snowman(GLCanvas canvas, double width, double height) {
		super(canvas, width, height);
		this.alpha = 0.0f;
		this.beta = 0.0f;
		this.factor = 1.0f;
		this.gama = new float[40];
		this.gamaIncr = new float[40];
		this.position = new float[40][2];
		// inicializar par�metros de cada floco de neve
		for (int i = 0; i < 40; i++)
			this.initializeParametersSnowflake(i);
		this.glut = new GLUT();
	}

	// desenha o fundo
	protected void drawBackground() {
		// copiar matriz atual
		this.gl.glPushMatrix();
		{
			// desenhar lua
			// copiar matriz atual
			this.gl.glPushMatrix();
			{
				// rodar a lua de acordo com o "controlo" do utilizador
				this.gl.glRotatef(beta, 0.0f, 1.0f, 0.0f);
				// realizar a transla��o para o canto superior esquerdo
				float[] translateMoon = new float[] { (float) (this.width / 4.0f), (float) (this.height / 4.0f),
						(float) (-this.width / 2.0f) };
				this.gl.glTranslatef(translateMoon[0], translateMoon[1], translateMoon[2]);
				// aumentar a lua - o factor � "controlado" pelo utilizador
				float scalarMoon = (float) (this.width / 10.0f) * this.factor;
				this.gl.glScalef(scalarMoon, scalarMoon, scalarMoon);
				// cor dos v�rtices, um � cinzento para ficar com um efeito real
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (i == 2)
							this.colorVertices[i][j] = 0.9f;
						else
							this.colorVertices[i][j] = 1.0f;
					}
				}
				// desenhar a esfera
				this.drawSphere(4);
			}
			// restaurar matriz anterior
			this.gl.glPopMatrix();
			// desenhar ch�o
			// copiar matriz atual
			this.gl.glPushMatrix();
			{
				// desenhar ret�ngulo branco
				this.gl.glBegin(GL.GL_POLYGON);
				{
					this.gl.glColor3f(1.0f, 1.0f, 1.0f);
					this.gl.glVertex3f((float) (-this.width / 2.0f), (float) (-this.height / 2.0f),
							(float) (-this.width / 2.0f));
					this.gl.glVertex3f((float) (this.width / 2.0f), (float) (-this.height / 2.0f),
							(float) (-this.width / 2.0f));
					this.gl.glVertex3f((float) (this.width / 2.0f), (float) (-this.height / 4.0f),
							(float) (-this.width / 2.0f));
					this.gl.glVertex3f((float) (-this.width / 2.0f), (float) (-this.height / 4.0f),
							(float) (-this.width / 2.0f));
				}
				this.gl.glEnd();
				// desenhar monte branco
				// realizar a transla��o para o canto inferior esquerdo
				float[] translateMount = new float[] { (float) (-this.width / 2.0f), (float) (-this.height / 1.8f),
						(float) (-this.width / 2.0f) };
				this.gl.glTranslatef(translateMount[0], translateMount[1], translateMount[2]);
				// aumentar monte
				float scalarMount = (float) (this.width / 4.0f);
				this.gl.glScalef(scalarMount, scalarMount, scalarMount);
				// cor dos v�rtices - branco
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++)
						this.colorVertices[i][j] = 1.0f;
				}
				// desenhar a esfera
				this.drawSphere(4);
			}
			// restaurar matriz anterior
			this.gl.glPopMatrix();
		}
		// restaurar matriz anterior
		this.gl.glPopMatrix();
	}

	// inicia os par�metros do floco de neve i
	protected void initializeParametersSnowflake(int i) {
		// �ngulo inicial
		this.gama[i] = 120.0f;
		float randomNumber;
		// gerar incremento aleatoriamente
		do {
			randomNumber = (float) Math.random();
			this.gamaIncr[i] = randomNumber;
		} while (randomNumber < 0.2 || randomNumber > 0.4);
		// iniciar posi��o aleatoriamente
		if (i % 2 == 0)
			this.position[i][0] = (float) (this.width / 2.0f * Math.random());
		else
			this.position[i][0] = (float) (-this.width / 2.0f * Math.random());
		this.position[i][1] = (float) (this.height / 2.0f * Math.random());
	}

	// desenha um floco de neve
	protected void drawSnowflake(float teta, float x, float y) {
		// copiar matriz atual
		this.gl.glPushMatrix();
		{
			// rodar em torno de x
			this.gl.glRotatef(teta, 1.0f, 0.0f, 0.0f);
			// realizar transla��o para a posi��o do floco
			this.gl.glTranslatef(x, y, (float) (-this.width / 2.0f));
			// aumentar esfera
			float scalarSphere = (float) (this.width / 500.0f);
			this.gl.glScalef(scalarSphere, scalarSphere, scalarSphere);
			// cor da esfera
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++)
					this.colorVertices[i][j] = 1.0f;
			}
			this.drawSphere(2);
		}
		// restaurar matriz anterior
		this.gl.glPopMatrix();
	}

	// desenha tren�
	protected void drawSleigh() {
		// copiar matriz atual
		this.gl.glPushMatrix();
		{
			// desenhar cilindro 1
			// copiar matriz atual
			this.gl.glPushMatrix();
			{
				// realizar a transla��o para o ch�o
				float[] translateCylinder = new float[] { (float) (-this.width / 4.0f), (float) (-this.height / 3.0f),
						0.0f };
				this.gl.glTranslatef(translateCylinder[0], translateCylinder[1], translateCylinder[2]);
				// aumentar cilindro
				float scalarCylinder = (float) (this.width / 80.0f);
				this.gl.glScalef(scalarCylinder, scalarCylinder, 10.0f * scalarCylinder);
				// cor do cilindro
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (j == 1)
							this.colorVertices[i][j] = 0.6f;
						else if (i == 2)
							this.colorVertices[i][j] = 0.3f;
						else
							this.colorVertices[i][j] = 0.0f;
					}
				}
				this.drawCylinder(4);
			}
			// restaurar matriz atual
			this.gl.glPopMatrix();
			// desenhar cilindro 2
			// copiar matriz atual
			this.gl.glPushMatrix();
			{
				// realizar a transla��o para o ch�o
				float[] translateCylinder = new float[] { (float) (-this.width / 5.0f), (float) (-this.height / 3.0f),
						0.0f };
				this.gl.glTranslatef(translateCylinder[0], translateCylinder[1], translateCylinder[2]);
				// aumentar cilindro
				float scalarCylinder = (float) (this.width / 80.0f);
				this.gl.glScalef(scalarCylinder, scalarCylinder, 10.0f * scalarCylinder);
				// cor do cilindro
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (j == 1)
							this.colorVertices[i][j] = 0.6f;
						else if (i == 2)
							this.colorVertices[i][j] = 0.3f;
						else
							this.colorVertices[i][j] = 0.0f;
					}
				}
				this.drawCylinder(4);
			}
			// restaurar matriz atual
			this.gl.glPopMatrix();
			// desenhar prisma - base superior do tren�
			// copiar matriz atual
			this.gl.glPushMatrix();
			{
				// realizar a transla��o para o ch�o
				float[] translateCylinder = new float[] { (float) (-this.width / 4.5f), (float) (-this.height / 3.45f),
						0.0f };
				this.gl.glTranslatef(translateCylinder[0], translateCylinder[1], translateCylinder[2]);
				// aumentar cilindro
				float scalarCylinder = (float) (this.width / 80.0f);
				this.gl.glScalef(5.0f * scalarCylinder, scalarCylinder, 10.0f * scalarCylinder);
				// cor do cilindro
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (j == 0)
							this.colorVertices[i][j] = 0.6f;
						else if (i == 2)
							this.colorVertices[i][j] = 0.3f;
						else
							this.colorVertices[i][j] = 0.0f;
					}
				}
				this.drawCylinder(1);
			}
			// restaurar matriz atual
			this.gl.glPopMatrix();
		}
		// restaurar matriz anterior
		this.gl.glPopMatrix();
	}

	// desenha boneco de neve
	protected void drawSnowman() {
		// copiar matriz atual
		this.gl.glPushMatrix();
		{
			// desenhar "pernas"
			// copiar matriz atual
			this.gl.glPushMatrix();
			{
				// realizar a transla��o para o tren�
				float[] translateSphere = new float[] { (float) (-this.width / 4.5f), (float) (-this.height / 5.5f),
						(float) (this.width / 15.0f) };
				this.gl.glTranslatef(translateSphere[0], translateSphere[1], translateSphere[2]);
				// aumentar esfera
				float scalarSphere = (float) (this.width / 20.0f);
				this.gl.glScalef(scalarSphere, scalarSphere, scalarSphere);
				// cor do cilindro
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (j == 2)
							this.colorVertices[i][j] = 0.8f;
						else
							this.colorVertices[i][j] = 1.0f;
					}
				}
				this.drawSphere(4);
			}
			// restaurar matriz anterior
			this.gl.glPopMatrix();
			// desenhar "corpo"
			// copiar matriz atual
			this.gl.glPushMatrix();
			{
				// realizar a transla��o para o tren�
				float[] translateSphere = new float[] { (float) (-this.width / 4.5f), (float) (-this.height / 15.0f),
						(float) (this.width / 15.0f) };
				this.gl.glTranslatef(translateSphere[0], translateSphere[1], translateSphere[2]);
				// aumentar esfera
				float scalarSphere = (float) (this.width / 25.0f);
				this.gl.glScalef(scalarSphere, scalarSphere, scalarSphere);
				// cor do cilindro
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (j == 2)
							this.colorVertices[i][j] = 0.85f;
						else
							this.colorVertices[i][j] = 1.0f;
					}
				}
				this.drawSphere(4);
			}
			// restaurar matriz anterior
			this.gl.glPopMatrix();
			// desenhar "cabe�a"
			// copiar matriz atual
			this.gl.glPushMatrix();
			{
				// realizar a transla��o para o tren�
				float[] translateSphere = new float[] { (float) (-this.width / 4.5f), (float) (this.height / 50.0f),
						(float) (this.width / 15.0f) };
				this.gl.glTranslatef(translateSphere[0], translateSphere[1], translateSphere[2]);
				// aumentar esfera
				float scalarSphere = (float) (this.width / 30.0f);
				this.gl.glScalef(scalarSphere, scalarSphere, scalarSphere);
				// cor do cilindro
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (j == 2)
							this.colorVertices[i][j] = 0.9f;
						else
							this.colorVertices[i][j] = 1.0f;
					}
				}
				this.drawSphere(4);
			}
			// restaurar matriz anterior
			this.gl.glPopMatrix();
			// desenhar "nariz"
			// copiar matriz atual
			this.gl.glPushMatrix();
			{
				// realizar a transla��o para o tren�
				float[] translateCone = new float[] { (float) (-this.width / 4.5f), (float) (this.height / 50.0f),
						(float) (this.width / 15.0f) };
				this.gl.glTranslatef(translateCone[0], translateCone[1], translateCone[2]);
				// aumentar esfera
				float scalarCone = (float) (this.width / 100.0f);
				this.gl.glScalef(scalarCone, scalarCone, 7.0f * scalarCone);
				// cor do cone
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (j == 0)
							this.colorVertices[i][j] = 1.0f;
						else if (j == 1)
							this.colorVertices[i][j] = 0.3f;
						else
							this.colorVertices[i][j] = 0.0f;
					}
				}
				this.drawCone(4);
			}
			// restaurar matriz anterior
			this.gl.glPopMatrix();
			// desenhar "olho esquerdo"
			// copiar matriz atual
			this.gl.glPushMatrix();
			{
				// realizar a transla��o para o tren�
				float[] translateSphere = new float[] { (float) (-this.width / 4.8f), (float) (this.height / 30.0f),
						(float) (this.width / 11.0f) };
				this.gl.glTranslatef(translateSphere[0], translateSphere[1], translateSphere[2]);
				// aumentar esfera
				float scalarSphere = (float) (this.width / 200.0f);
				this.gl.glScalef(scalarSphere, scalarSphere, scalarSphere);
				// cor da esfera
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++)
						this.colorVertices[i][j] = 0.0f;
				}
				this.drawSphere(4);
			}
			// restaurar matriz anterior
			this.gl.glPopMatrix();
			// desenhar "olho direito"
			// copiar matriz atual
			this.gl.glPushMatrix();
			{
				// realizar a transla��o para o tren�
				float[] translateSphere = new float[] { (float) (-this.width / 4.23f), (float) (this.height / 30.0f),
						(float) (this.width / 11.0f) };
				this.gl.glTranslatef(translateSphere[0], translateSphere[1], translateSphere[2]);
				// aumentar esfera
				float scalarSphere = (float) (this.width / 200.0f);
				this.gl.glScalef(scalarSphere, scalarSphere, scalarSphere);
				// cor da esfera
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++)
						this.colorVertices[i][j] = 0.0f;
				}
				this.drawSphere(4);
			}
			// restaurar matriz anterior
			this.gl.glPopMatrix();
			// desenhar chap�u
			// copiar matriz atual
			this.gl.glPushMatrix();
			{

				// realizar a transla��o para o tren�
				float[] translateCylinder = new float[] { (float) (-this.width / 4.5f), (float) (this.height / 7.0f),
						(float) (this.width / 15.0f) };
				this.gl.glTranslatef(translateCylinder[0], translateCylinder[1], translateCylinder[2]);
				this.gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
				// aumentar cilindro
				float scalarCylinder = (float) (this.width / 50.0f);
				this.gl.glScalef(scalarCylinder, scalarCylinder, 2.0f * scalarCylinder);
				// cor do cilindro
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++)
						if (j == 2)
							this.colorVertices[i][j] = 0.8f;
						else if (i == 2)
							this.colorVertices[i][j] = 0.3f;
						else
							this.colorVertices[i][j] = 0.0f;
				}
				this.drawCylinder(4);
			}
			// restaurar matriz anterior
			this.gl.glPopMatrix();
		}
		// restaurar matriz anterior
		this.gl.glPopMatrix();
	}

	// escreve as instru��es para o utilizador
	protected void writeInstructions() {
		this.gl.glColor3f(0.0f, 0.3f, 0.8f);
		this.gl.glRasterPos3d(-this.width / 2.0 + this.width / 50.0f, -this.height / 2.0f + this.height / 50.0, 0.0f);
		this.glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18,
				"Mover lua: setas direita e esquerda       Ampliar/reduzir lua: setas para cima e para baixo");
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// limpar buffers
		this.gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		// desenhar fundo
		this.drawBackground();
		// desenhar flocos de neve
		for (int i = 0; i < 40; i++) {
			// se o �ngulo de rota��o ultrapassar 200 graus, inicializar
			// par�metros do floco
			if (this.gama[i] > 200.0f)
				this.initializeParametersSnowflake(i);
			// incrementar �ngulo de rota��o do floco de neve
			this.gama[i] += this.gamaIncr[i];
			this.drawSnowflake(this.gama[i], this.position[i][0], this.position[i][1]);
		}
		// incrementar �ngulo de rota��o do tren� + boneco de neve
		(this.alpha)++;
		// copiar matriz atual
		this.gl.glPushMatrix();
		{
			// inclinar em rela��o a X
			this.gl.glRotatef(10.0f, 1.0f, 0.0f, 0.0f);
			// rodar em torno do eixo Y
			this.gl.glRotatef(this.alpha, 0.0f, 1.0f, 0.0f);
			// desenhar tren�
			this.drawSleigh();
			// desenhar boneco de neve
			this.drawSnowman();
		}
		// restaurar matriz anterior
		this.gl.glPopMatrix();
		// escrever instru��es para o utilizador
		this.writeInstructions();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			this.factor *= 1.1f; // zoom - in da lua
			if (this.factor > 2.0f)
				this.factor = 2.0f;
			break;
		case KeyEvent.VK_DOWN:
			this.factor *= 0.9f; // zoom - out da lua
			if (this.factor < 0.2f)
				this.factor = 0.2f;
			break;
		case KeyEvent.VK_LEFT:
			this.beta += 1; // rodar sobre o eixo y - sentido positivo
			break;
		case KeyEvent.VK_RIGHT:
			this.beta -= 1; // rodar sobre o eixo y - sentido negativo
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
