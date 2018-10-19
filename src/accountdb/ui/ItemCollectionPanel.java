package accountdb.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ItemCollectionPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel quantityLabel;
	private JLabel iconLabel;
	private JPanel panel;

	boolean isSelected = false;

	/**
	 * Create the panel.
	 */
	public ItemCollectionPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel quantityPanel = new JPanel();
		panel.add(quantityPanel, BorderLayout.SOUTH);

		quantityLabel = new JLabel("Quantity");
		quantityLabel.setOpaque(false);
		quantityPanel.add(quantityLabel);

		iconLabel = new JLabel("Icon");
		iconLabel.setOpaque(true);
		iconLabel.setBackground(Color.WHITE);
		iconLabel.setForeground(Color.BLACK);
		iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(iconLabel, BorderLayout.CENTER);

		registerMouseClickListener();

	}

	private void registerMouseClickListener() {
		panel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Mouse clicked");
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				updateIsSelected();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});
	}

	private void updateIsSelected() {
		isSelected = !isSelected;

		if (isSelected) {
			iconLabel.setBackground(Color.BLUE);
			quantityLabel.setForeground(Color.BLUE);
		} else {
			iconLabel.setBackground(Color.WHITE);
			quantityLabel.setForeground(Color.BLACK);
		}
	}

	public ItemCollectionPanel setIcon(Icon icon) {
		iconLabel.setText("");
		iconLabel.setIcon(icon);
		return this;
	}

	public ItemCollectionPanel setQuantity(String quantity) {
		quantityLabel.setText(quantity);
		return this;
	}

}
