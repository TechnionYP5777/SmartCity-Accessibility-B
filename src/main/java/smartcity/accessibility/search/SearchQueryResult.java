package smartcity.accessibility.search;

import java.util.List;

import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

/**
 * @author Kolikant
 *
 */
public class SearchQueryResult extends MapView{

	Map map;
	private List<GeocoderResult> coordinations;

	SearchQueryResult(List<GeocoderResult> c, Map m){
		this.coordinations = c;
		this.map = m;
	}
	
	public List<GeocoderResult> getCoordinations(){
		return coordinations;
	}
}
	

