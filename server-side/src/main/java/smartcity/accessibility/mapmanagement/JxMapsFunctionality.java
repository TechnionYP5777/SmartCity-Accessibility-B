package smartcity.accessibility.mapmanagement;

import java.util.ArrayList;
import java.util.List;

import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

/*
 * Author Kolikant
 */

public abstract class JxMapsFunctionality {

	public static ExtendedMapView mv;

		public static class ExtendedMapView extends MapView {

		private static final long serialVersionUID = 1L;
		List<Marker> MarkerList = new ArrayList<Marker>();
		//@SuppressWarnings("unused")

		public ExtendedMapView(MapViewOptions options) {
			super(options);
			setOnMapReadyHandler(new MapReadyHandler() {
				@Override
				public void onMapReady(MapStatus arg0) {
					MapOptions options = new MapOptions();
					MapTypeControlOptions controlOptions = new MapTypeControlOptions();
					// Changing position of the map type control
					controlOptions.setPosition(ControlPosition.TOP_RIGHT);
					// Setting map type control options
					options.setMapTypeControlOptions(controlOptions);
					// Setting map options
					getMap().setOptions(options);

				}
			});
		}

		public void stop() {

			System.out.println("Stage is closing");
			// Save file
		}

		@Override
		public void addNotify() {
			super.addNotify();
		}

	}
	public static ExtendedMapView getMapView() {
		MapViewOptions $ = new MapViewOptions();
		$.importPlaces();
		return mv != null ? mv : (mv = new ExtendedMapView($));
	}

	public static void DestroyMapView() {
		mv.dispose();
		mv = null;
	}

	public static void waitForMapReady(ExtendedMapView mv) {
		mv.waitReady();
	}
}