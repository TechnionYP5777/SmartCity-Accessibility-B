package smartcity.accessibility.mapmanagement;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;


/*
 * Author Kolikant
 */

public abstract class JxMapsFunctionality {
	
	public static MapView mv;
	
	public static class extendedMapView extends MapView{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		List<Marker> MarkerList = new ArrayList<Marker>();
		
		public extendedMapView(MapViewOptions __){
			setOnMapReadyHandler(new MapReadyHandler() {
				@Override
				public void onMapReady(MapStatus arg0) {
					
				}
			});
		}
		
		
		public void stop(){
			
		    System.out.println("Stage is closing");
		    // Save file
		}
		
	}
	
	
	public static MapView getMapView(){
		if (mv != null)
			return mv;
		MapViewOptions options = new MapViewOptions();
		options.importPlaces();
		return mv = new extendedMapView(options);
	}
	
	public static void DestroyMapView(){
		mv.dispose();
		mv = null;
	}
	
	public static void ClearMarkers(extendedMapView mv){
		for (Marker m: mv.MarkerList)
			m.remove();
	}
	
	public static MapView getMapView(MapViewOptions o){
	        o.importPlaces();
	        return mv = new extendedMapView(o);
	}
	
	
	
	public static void putMarker(extendedMapView mv, LatLng l, String name) {
		waitForMapReady(mv);
		Map map =mv.getMap();
		Marker marker = new Marker(map);
		marker.setPosition(l);
		map.setCenter(l);
		final InfoWindow window = new InfoWindow(map);
		mv.MarkerList.add(marker);
		window.setContent(name);
		window.open(map, marker);
	}

	public static void waitForMapReady(extendedMapView mv) {
		mv.waitReady();
	}

	
	
	public static void openFrame(MapView v, String s, double zoom) {
		openFrame(v,s,zoom, 700, 500);
	}
	
	public static void openFrame(MapView v, String s, double zoom, int x, int y) {
		JFrame frame = new JFrame(s);
		v.getMap().setZoom(zoom);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(v, BorderLayout.CENTER);
        frame.setSize(x, y);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);	
	}

	public static void initMapLocation(MapView mapView, String startAdress) {
		Map map = mapView.getMap();
		map.setZoom(17.0);
		GeocoderRequest request = new GeocoderRequest();
		request.setAddress(startAdress);
		Geocoder g = mapView.getServices().getGeocoder();
		g.geocode(request, new GeocoderCallback(map) {
			@Override
			public void onComplete(GeocoderResult[] rs, GeocoderStatus s) {
				System.out.println(s.name());
				if (s != GeocoderStatus.OK)
					return;
				map.setCenter(rs[0].getGeometry().getLocation());
				Marker marker = new Marker(map);
				marker.setPosition(rs[0].getGeometry().getLocation());
			}
		});
		
	}
}