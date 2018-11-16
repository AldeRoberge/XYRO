package oryx2D;

import oryx2D.entity.mob.Player;
import oryx2D.graphics.Screen;
import oryx2D.input.Keyboard;
import oryx2D.input.Mouse;
import oryx2D.level.Level;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	/**
	 * A 8 pixel tile is 50 pixels on screen = 6.25 ratio
	 */
	private static final int SCALE = 6;

	private static final int ACTUAL_WIDTH = (int) (800 / SCALE * 1.25);
	private static final int ACTUAL_HEIGHT = (int) (600 / SCALE * 1.25);

	private static final double FRAME_RATE = 60;

	private int currentFPS = 0;
	private boolean showFPS = true;

	private Thread gameThread;
	private boolean running = false;

	private Screen screen;

	private Level level;

	private Keyboard key;
	private Player player;

	private Mouse mouse;

	public Game() {

		setPreferredSize(new Dimension(ACTUAL_WIDTH * SCALE, ACTUAL_HEIGHT * SCALE));

		screen = new Screen(ACTUAL_WIDTH, ACTUAL_HEIGHT);

		level = new Level();

		key = new Keyboard();

		addKeyListener(key);

		player = new Player(130, 130, key);
		player.init(level);

		mouse = new Mouse();

		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		start();
	}

	public static int getWindowWidth() {
		return ACTUAL_WIDTH * SCALE;
	}

	public static int getWindowHeight() {
		return ACTUAL_HEIGHT * SCALE;
	}

	/**
	 * Starts the game loop
	 */
	public synchronized void start() {
		if (running) {
			System.err.println("Game is already running!");
			return;
		}

		running = true;
		gameThread = new Thread(this, "Game render thread");
		gameThread.start();
	}

	/**
	 * Stops the game loop
	 */
	public synchronized void stop() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / FRAME_RATE;
		double delta = 0;
		int updates = 0;

		requestFocus();

		while (running) {
			long nanoTime = System.nanoTime();
			delta += (nanoTime - lastTime) / ns;
			lastTime = nanoTime;

			while (delta >= 1) { //Only happens 60 times a second
				update();
				render();

				updates++;
				delta--;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				currentFPS = updates;
				updates = 0;
			}
		}
		stop();
	}

	public void update() {
		key.update();
		player.update();
	}

	public void render() {

		// Abort if screen is not in focus
		if (!this.isFocusOwner()) {
			return;
		}

		// Get or create buffer strategy
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		int xscroll = player.x - screen.width / 2;
		int yscroll = player.y - screen.height / 2;

		screen.setOffset(xscroll, yscroll);

		level.render(screen);
		player.render(screen);

		Graphics g = bs.getDrawGraphics();
		screen.drawPixels(g, getWidth(), getHeight());

		// Draw UI
		if (showFPS) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Verdana", Font.PLAIN, 20));
			g.drawString("FPS : " + currentFPS, 80, 40);
		}

		g.dispose(); //Release the memory
		bs.show(); //Show the buffer
	}

}
