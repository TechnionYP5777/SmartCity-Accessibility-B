package smartcity.accessibility.navigation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import com.teamdev.jxmaps.LatLng;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.navigation.exception.CommunicationFailed;
import smartcity.accessibility.navigation.mapquestcommunication.Latlng;

import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;

/**
 * This class contains test for Navigation. the tests are for the Navigation as
 * a whole unit and not for specific class but the main class is
 * Navigation.java. (Add sleep in the end of tests in order to view the map
 * before the test ends!)
 * 
 * @author yael
 *
 */
public class NavigationTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10000);

	@Test
	public void getMapSegmentFromLatLng() {
		MapSegment m = null;
		try {
			m = Navigation.getMapSegmentOfLatLng(31.766932, 34.631666);
		} catch (CommunicationFailed e) {
		}
		System.out.println(m.getLinkId());
		System.out.println(m.getStreet());
	}

	@Test
	public void avoidOneSegement() throws CommunicationFailed {
		Latlng from = new Latlng(31.768762, 34.632052);// abba ahimeir
		Latlng to = new Latlng(31.770981, 34.620567);// HaYam HaTichon Blvd 1
		List<MapSegment> segmentsToAvoid = new ArrayList<MapSegment>();
		segmentsToAvoid.add(Navigation.getMapSegmentOfLatLng(31.76935, 34.626793));// sd
		Double[] shapePoints = Navigation.getRouteFromMapQuest(from, to, segmentsToAvoid).getShape().getShapePoints();
		ExtendedMapView mapview = JxMapsFunctionality.getMapView();
		JxMapsFunctionality.waitForMapReady(mapview);
		JxMapsConvertor.displayRoute(mapview, Navigation.arrayToLatLng(shapePoints));
	}

	@Test
	public void avoidTwoSegement() throws CommunicationFailed {
		Latlng from = new Latlng(31.768762, 34.632052);// abba ahimeir
		Latlng to = new Latlng(31.770981, 34.620567);// HaYam HaTichon Blvd 1
		List<MapSegment> segmentsToAvoid = new ArrayList<MapSegment>();
		segmentsToAvoid.add(Navigation.getMapSegmentOfLatLng(31.76935, 34.626793));// sd
																					// tel
																					// hai
		segmentsToAvoid.add(Navigation.getMapSegmentOfLatLng(31.769937, 34.627658));
		Double[] shapePoints = Navigation.getRouteFromMapQuest(from, to, segmentsToAvoid).getShape().getShapePoints();
		ExtendedMapView mapview = JxMapsFunctionality.getMapView();
		JxMapsFunctionality.waitForMapReady(mapview);
		JxMapsConvertor.displayRoute(mapview, Navigation.arrayToLatLng(shapePoints));
	}

	@Test
	public void displayMap() throws CommunicationFailed {
		Location fromLocation = new Location(new LatLng(31.768762, 34.632052));
		Location toLocation = new Location(new LatLng(31.770981, 34.620567));
		LatLng[] shapePoints = Navigation.showRoute(fromLocation, toLocation, 0);
		ExtendedMapView mapview = JxMapsFunctionality.getMapView();
		JxMapsFunctionality.waitForMapReady(mapview);
		JxMapsConvertor.displayRoute(mapview, shapePoints);
	}

}
