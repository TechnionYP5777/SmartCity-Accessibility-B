package smartcity.accessibility.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.UserProfile;

@RestController
public class ReviewsService {
	
	@RequestMapping(value = "/reviews", produces = "application/json")
	@ResponseBody
    public Review[] showMeStuff(@RequestParam("lat") Double lat,
    		@RequestParam("lng") Double lng) {
		
		List<Location> lstLocation = AbstractLocationManager.instance().getLocation(new LatLng(lat, lng), null);
		List<Review> lstReviews = new ArrayList<>();
		
		for(Location l : lstLocation)
			lstReviews.addAll(l.getReviews());
		
		//return lstReviews.toArray(null);
		
		Location l = new Location();
		l.setCoordinates(new LatLng(lat, lng));
		UserProfile u = new UserProfile("ayyy");
		return new Review[] { new Review(l, 5, "amazing", u), new Review(l, 3, "so lame bruh", u) };
    }

}
