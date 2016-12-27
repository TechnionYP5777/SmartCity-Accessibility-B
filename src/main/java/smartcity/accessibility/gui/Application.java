package smartcity.accessibility.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.teamdev.jxmaps.MapComponentType;
import com.teamdev.jxmaps.MapViewOptions;

import smartcity.accessibility.database.DatabaseManager;
import smartcity.accessibility.gui.components.ButtonsPanel;
import smartcity.accessibility.gui.components.GMap;
import smartcity.accessibility.gui.components.MapFrame;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

public class Application {

	public static MapFrame frame;
	public static GMap mapView;

	public static final int FRAME_X_SIZE = 1000;
	public static final int FRAME_Y_SIZE = 700;

	public static User appUser = new UserImpl("", "", User.Privilege.DefaultUser);

	public static void main(String[] args) {
		frame = new MapFrame("JxMaps - Hello, World!");

		DatabaseManager.initialize();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MapViewOptions options = new MapViewOptions(MapComponentType.HEAVYWEIGHT);
		options.importPlaces();
		mapView = new GMap(options);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(mapView, BorderLayout.CENTER);

		JPanel panel2 = new ButtonsPanel();

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = gbc.gridx = 0;
		gbc.weightx = 0.0;
		gbc.ipady = FRAME_Y_SIZE - 100;
		gbc.ipadx = FRAME_X_SIZE;
		frame.getContentPane().add(panel, gbc);
		frame.setLocationRelativeTo(null);

		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		frame.getContentPane().add(panel2, gbc);

		frame.pack();
		frame.setVisible(true);

	}

}