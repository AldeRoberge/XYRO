package accountdb.item;

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
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;

public class ItemCollectionPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel quantityLabel;
	private JLabel iconLabel;

	boolean isSelected = false;
	private JPanel panel;
	private Component topStrut;
	private Component rightStrut;
	private Component leftStrut;
	private JPanel panel_2;
	private JPanel quantityPanel;
	private Component verticalStrut;
	private Component iconTopStrut;
	private Component iconLeftStrut;
	private Component iconRightStrut;
	private JPanel iconPanel;

	/**
	 * Create the panel.
	 */
	public ItemCollectionPanel() {
		setBorder(null);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		panel = new JPanel();
		panel.setBackground(new Color(20, 20, 20));
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		topStrut = Box.createVerticalStrut(5);
		panel.add(topStrut, BorderLayout.NORTH);

		rightStrut = Box.createHorizontalStrut(5);
		panel.add(rightStrut, BorderLayout.EAST);

		leftStrut = Box.createHorizontalStrut(5);
		panel.add(leftStrut, BorderLayout.WEST);

		iconPanel = new JPanel();
		iconPanel.setBorder(null);
		iconPanel.setBackground(new Color(54, 54, 54));
		iconPanel.setLayout(new BorderLayout());

		iconLabel = new JLabel("Icon");
		iconLabel.setForeground(Color.WHITE);
		iconLabel.setBackground(Color.WHITE);
		iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

		iconPanel.add(iconLabel, BorderLayout.CENTER);

		panel.add(iconPanel, BorderLayout.CENTER);

		quantityPanel = new JPanel();
		FlowLayout fl_quantityPanel = (FlowLayout) quantityPanel.getLayout();
		fl_quantityPanel.setAlignment(FlowLayout.RIGHT);
		quantityPanel.setOpaque(false);
		iconPanel.add(quantityPanel, BorderLayout.SOUTH);

		quantityLabel = new JLabel("Quantity");
		quantityPanel.add(quantityLabel);
		quantityLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		quantityLabel.setForeground(Color.WHITE);
		quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		quantityLabel.setOpaque(false);

		iconTopStrut = Box.createVerticalStrut(5);
		iconPanel.add(iconTopStrut, BorderLayout.NORTH);

		iconLeftStrut = Box.createHorizontalStrut(5);
		iconPanel.add(iconLeftStrut, BorderLayout.WEST);

		iconRightStrut = Box.createHorizontalStrut(5);
		iconPanel.add(iconRightStrut, BorderLayout.EAST);

		verticalStrut = Box.createVerticalStrut(5);
		panel.add(verticalStrut, BorderLayout.SOUTH);

	}

	public void registerMouseClickListener(Runnable runnable) {
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
				runnable.run();
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
			iconPanel.setBackground(new Color(100, 80, 34));
		} else {
			iconPanel.setBackground(new Color(54, 54, 54));
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

	public boolean noIcon() {
		return iconLabel.getIcon() == null;
	}

}
