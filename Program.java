package packBase;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

import com.sun.opengl.util.FPSAnimator;

//import com.sun.opengl.util.FPSAnimator;

public class Program {

	// teste à classe Point0
	@SuppressWarnings("unused")
	private static void testPoint0() {
		int width = 1200;
		int height = 700;

		final GLCapabilities capabilities = new GLCapabilities();

		final GLCanvas canvas = new GLCanvas(capabilities);
		Point0 point0 = new Point0(canvas, (double) width, (double) height);
		canvas.addGLEventListener(point0);

		final Window window = new Window("Test J1_0_Point", canvas, width, height);
		window.setVisible(true);
	}

	@SuppressWarnings("unused")
	private static void testCircle5() {
		int width = 1200;
		int height = 700;

		final GLCapabilities capabilities = new GLCapabilities();
		capabilities.setDoubleBuffered(true);
		final GLCanvas canvas = new GLCanvas(capabilities);
		Circle5 circle5 = new Circle5(canvas, (double) width, (double) height);
		canvas.addGLEventListener(circle5);

		final Window window = new Window("Test J1_5_Circle", canvas, width, height);
		window.setVisible(true);

		final FPSAnimator animator = new FPSAnimator(canvas, 60, true);
		animator.start();
	}

	@SuppressWarnings("unused")
	private static void testCone5() {
		int width = 1200;
		int height = 700;

		final GLCapabilities capabilities = new GLCapabilities();
		capabilities.setDoubleBuffered(false);
		final GLCanvas canvas = new GLCanvas(capabilities);
		Cone5 cone = new Cone5(canvas, (double) width, (double) height);
		canvas.addGLEventListener(cone);

		final Window window = new Window("Test J2_5_Cone", canvas, width, height);
		window.setVisible(true);

		final FPSAnimator animator = new FPSAnimator(canvas, 60, true);
		animator.start();
	}

	@SuppressWarnings("unused")
	private static void testCylinder6() {
		int width = 1200;
		int height = 700;

		final GLCapabilities capabilities = new GLCapabilities();
		capabilities.setDoubleBuffered(false);
		final GLCanvas canvas = new GLCanvas(capabilities);
		Cylinder6 cylinder = new Cylinder6(canvas, (double) width, (double) height);
		canvas.addGLEventListener(cylinder);

		final Window window = new Window("Test J2_6_Cylinder", canvas, width, height);
		window.setVisible(true);

		final FPSAnimator animator = new FPSAnimator(canvas, 60, true);
		animator.start();
	}

	@SuppressWarnings("unused")
	private static void testSphere7() {
		int width = 1200;
		int height = 700;

		final GLCapabilities capabilities = new GLCapabilities();
		capabilities.setDoubleBuffered(false);
		final GLCanvas canvas = new GLCanvas(capabilities);
		Sphere7 sphere = new Sphere7(canvas, (double) width, (double) height);
		canvas.addGLEventListener(sphere);

		final Window window = new Window("Test J2_7_Sphere", canvas, width, height);
		window.setVisible(true);

		final FPSAnimator animator = new FPSAnimator(canvas, 60, true);
		animator.start();
	}
	
	@SuppressWarnings("unused")
	private static void executeProgram() {
		int width = 1200;
		int height = 700;

		final GLCapabilities capabilities = new GLCapabilities();
		capabilities.setDoubleBuffered(false);
		final GLCanvas canvas = new GLCanvas(capabilities);
		Snowman snowman = new Snowman(canvas, (double) width, (double) height);
		canvas.addGLEventListener(snowman);

		final Window window = new Window("Snowman", canvas, width, height);
		window.addKeyListener(snowman);
		window.setVisible(true);

		final FPSAnimator animator = new FPSAnimator(canvas, 60, true);
		animator.start();
	}

	public static void main(String[] args) {
		// testPoint0();
		// testCircle5();
		// testCone5();
		// testCylinder6();
		// testSphere7();
		executeProgram();
	}

}
