package smartcity.accessibility.gui.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import smartcity.accessibility.database.UserManager;
import smartcity.accessibility.gui.Application;
import smartcity.accessibility.gui.components.search.ElaborateSearchFrame;
import smartcity.accessibility.gui.components.user.LoginFrame;
import smartcity.accessibility.gui.components.user.SignUpFrame;
import smartcity.accessibility.gui.components.user.UserProfileFrame;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.socialnetwork.UserImpl;

public class ButtonsPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = -8394584034225983460L;

	public static GButton LOGIN_BUTTON;
	public static GButton LOGOUT_BUTTON;
	public static GButton SIGNUP_BUTTON;
	public static GButton USER_PROFILE_BUTTON;
	public static GButton CLEAR_MARKERS_BUTTON;
	public static GButton SEARCH_BY_TYPE;

	public ButtonsPanel() {
		USER_PROFILE_BUTTON = new GButton("");
		add(USER_PROFILE_BUTTON);
		USER_PROFILE_BUTTON.setVisible(false);
		USER_PROFILE_BUTTON.addMouseListener(this);

		LOGIN_BUTTON = new GButton("Login");
		LOGIN_BUTTON.addMouseListener(this);
		add(LOGIN_BUTTON);
		LOGIN_BUTTON.setVisible(true);

		LOGOUT_BUTTON = new GButton("Logout");
		LOGOUT_BUTTON.addMouseListener(this);
		add(LOGOUT_BUTTON);
		LOGOUT_BUTTON.setVisible(false);

		SIGNUP_BUTTON = new GButton("Signup");
		SIGNUP_BUTTON.addMouseListener(this);
		add(SIGNUP_BUTTON);
		SIGNUP_BUTTON.setVisible(true);

		CLEAR_MARKERS_BUTTON = new GButton("Clear Markers");
		CLEAR_MARKERS_BUTTON.addMouseListener(this);
		add(CLEAR_MARKERS_BUTTON);
		CLEAR_MARKERS_BUTTON.setVisible(true);

		SEARCH_BY_TYPE = new GButton("Elaborate Search");
		SEARCH_BY_TYPE.addMouseListener(this);
		add(SEARCH_BY_TYPE);
		SEARCH_BY_TYPE.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent ¢) {
		if (¢.getSource() == ButtonsPanel.LOGIN_BUTTON)
			new LoginFrame();
		if (¢.getSource() == SIGNUP_BUTTON)
			new SignUpFrame();
		if (¢.getSource() == CLEAR_MARKERS_BUTTON)
			JxMapsFunctionality.ClearMarkers(JxMapsFunctionality.getMapView());
		if (¢.getSource() == SEARCH_BY_TYPE)
			new ElaborateSearchFrame();
		if (¢.getSource() == ButtonsPanel.USER_PROFILE_BUTTON)
			new UserProfileFrame();
		if (¢.getSource() == ButtonsPanel.LOGOUT_BUTTON) {
			UserManager.logoutCurrUser();
			Application.appUser = UserImpl.DefaultUser();
			LOGOUT_BUTTON.setVisible(false);
			LOGIN_BUTTON.setVisible(true);
			USER_PROFILE_BUTTON.setVisible(false);
			SIGNUP_BUTTON.setVisible(true);
		}
		System.out.println("clicked " + ¢.getSource());

	}

	@Override
	public void mouseEntered(MouseEvent __) {

	}

	@Override
	public void mouseExited(MouseEvent __) {

	}

	@Override
	public void mousePressed(MouseEvent __) {

	}

	@Override
	public void mouseReleased(MouseEvent __) {

	}

}