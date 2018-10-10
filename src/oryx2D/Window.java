package oryx2D;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class Window {

	private static final String TITLE = "Oryx2D";

	private JFrame frame;

	Game game = new Game();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		frame = new JFrame();

		frame.setResizable(false);
		frame.setBackground(Color.black);
		frame.setTitle(TITLE);
		frame.setLocationRelativeTo(null);

		try {
			ArrayList<Image> icons = new ArrayList<Image>();
			icons.add(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/oryx2D/icons/icon128.png")));
			icons.add(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/oryx2D/icons/icon16.png")));
			icons.add(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/oryx2D/icons/icon32.png")));
			frame.setIconImages(icons);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			BufferedImage image = ImageIO.read(Game.class.getResource("/oryx2D/cursor/cursor.png"));
			frame.setCursor(frame.getToolkit().createCustomCursor(image, new Point(image.getHeight() / 2, image.getWidth() / 2), "BMJ's Cursor"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.add(game, BorderLayout.CENTER);

	}

}
