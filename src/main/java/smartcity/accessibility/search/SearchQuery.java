package smartcity.accessibility.search;

/**
 * @author Kolikant
 *
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.database.LocationListCallback;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;
import smartcity.accessibility.mapmanagement.Location;

/**
 * Author Kolikant
 */
public class SearchQuery {
	private static final String DefaultQueryName = null;
	
	private List<Location> places; // the nearby places result
	public enum SearchStage {
		NotRunning, Running, Done, Failed;

		private static SearchStage[] allValues = values();

		public static SearchStage fromOrdinal(int ¢) {
			return allValues[¢];
		}
	}

	public static final String EmptyList = "[]";
	protected static final String isThisAdressSpliter = "<-This is Adress: ";

	String QueryName;
	String queryString;
	boolean isAdress;
	final AtomicInteger searchStatus;

	protected SearchQuery(String parsedQuery) {
		QueryName = DefaultQueryName;
		String[] a = parsedQuery.split(isThisAdressSpliter);
		isAdress = Boolean.parseBoolean(a[0]);
		this.queryString = a[1];
		searchStatus = new AtomicInteger();
		SetSearchStatus(SearchStage.NotRunning);
	}

	protected SearchQuery RenameSearchQuery(String QueryName){
		this.QueryName = QueryName;
		return this;
	}
	
	public static SearchQuery adressSearch(String adress) {
		return new SearchQuery(Boolean.toString(true) + isThisAdressSpliter + adress);
	}

	public static SearchQuery TypeSearch(String Type) {
		return new SearchQuery(Boolean.toString(false) + isThisAdressSpliter + Type);
	}

	protected void SetSearchStatus(SearchStage ¢) {
		searchStatus.set(¢.ordinal());
	}

	public synchronized void waitOnSearch() throws InterruptedException {
		while (searchStatus.get() == SearchStage.Running.ordinal())
			wait();
	}

	protected synchronized void wakeTheWaiters() {
		notifyAll();
	}

	protected SearchQueryResult Search(GeocoderRequest r, MapView v) {
		return !isAdress ? null : adressSearch(r, v);
	}

	protected SearchQueryResult Search(Location initLocation, double radius) {
		return isAdress ? null : typeSearch(initLocation, radius);
	}

	private SearchQueryResult typeSearch(Location initLocation, double radius) {
		SetSearchStatus(SearchStage.Running);
		List<String> kindsOfLocations = new ArrayList<String>();
		kindsOfLocations.add(queryString);
		MapViewOptions options = new MapViewOptions();
		options.importPlaces();
		MapView mapView = JxMapsFunctionality.getMapView();
		JxMapsFunctionality.waitForMapReady((ExtendedMapView) mapView);
		NearbyPlacesSearch.findNearbyPlaces(mapView, initLocation, radius, kindsOfLocations,
				new LocationListCallback() {

					@Override
					public void done(List<Location> ¢) {
						places = ¢;
						SetSearchStatus(SearchStage.Done);
						wakeTheWaiters();
					}
				});
		try {
			waitOnSearch();
		} catch (InterruptedException ¢) {
			¢.printStackTrace();
		}
		return new SearchQueryResult(places);
	}

	private SearchQueryResult adressSearch(GeocoderRequest r, MapView v) {
		SetSearchStatus(SearchStage.Running);
		List<Location> $ = new ArrayList<Location>();
		Geocoder g = v.getServices().getGeocoder();
		g.geocode(r, new GeocoderCallback(v.getMap()) {
			@Override
			public void onComplete(GeocoderResult[] rs, GeocoderStatus s) {
				System.out.println("arrived to search on complete");
				if (s != GeocoderStatus.OK) {
					SetSearchStatus(SearchStage.Failed);
					wakeTheWaiters();
					return;
				}
				LatLng l = rs[0].getGeometry().getLocation();
				Location f = new Location(l);
				f.setName(rs[0].getFormattedAddress());
				$.add(f);
				SetSearchStatus(SearchStage.Done);
				wakeTheWaiters();
			}

		});
		return new SearchQueryResult($);
	}

	@SuppressWarnings("deprecation")
	public SearchQueryResult SearchByAddress(MapView ¢) {
		GeocoderRequest $ = new GeocoderRequest(¢.getMap());
		$.setAddress(queryString);
		return Search($, ¢);

	}

	/**
	 * Koral Chapnik
	 */
	@SuppressWarnings("deprecation")
	public SearchQueryResult searchByCoordinates(MapView v, LatLng c) {
		GeocoderRequest $ = new GeocoderRequest(v.getMap());
		$.setLocation(c);
		return Search($, v);
	}

	public SearchQueryResult searchByType(Location initLocation, double radius) {
		return Search(initLocation, radius);
	}

	@Override
	public String toString() {
		return Boolean.toString(isAdress) + isThisAdressSpliter + queryString;
	}

	public static SearchQuery toQuery(String ¢) {
		return new SearchQuery(¢);
	}

	public static String QueriesList2String(List<SearchQuery> qs) {
		List<String> $ = new ArrayList<String>();
		for (SearchQuery ¢ : qs)
			$.add(¢ + "");
		return $ + "";
	}

	public static List<SearchQuery> String2QueriesList(String favouriteQueries) {
		String p1 = favouriteQueries.replace("[", "").replace("]", "");
		List<SearchQuery> $ = new ArrayList<SearchQuery>();
		String[] split;
		if (p1.isEmpty())
			return $;
		split = p1.split(", ");
		for (String ¢ : split)
			$.add(SearchQuery.toQuery(¢));
		return $;
	}

}