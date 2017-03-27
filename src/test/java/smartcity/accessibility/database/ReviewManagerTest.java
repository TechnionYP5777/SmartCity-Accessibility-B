package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseUser;
import org.parse4j.callback.GetCallback;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User.Privilege;
import smartcity.accessibility.socialnetwork.UserImpl;

public class ReviewManagerTest {
	public static String testParseClass = "DatabaseManagerTestClass";
	public static String id_result = "";

	@BeforeClass
	public static void init() {
		ParseUser.currentUser = new ParseUser();
		DatabaseManager.initialize();
	}

	@Test
	@Category(UnitTests.class)
	public void UploadReviewTest() throws InterruptedException {
		LatLng k = new LatLng(20, 20);
		Location L = new Location(k);
		Review r = new Review(L, 5, "firstTest", "assaf");
		ReviewManager.uploadReview(r);
		Thread.sleep(6000);
		ReviewManager.uploadReview(r);
		Thread.sleep(6000);
	}

	@Test
	@Category(UnitTests.class)
	public void getReviewByUserAndLocationTest() throws InterruptedException {
		LatLng k = new LatLng(20, 20);
		Location L = new Location(k, Location.LocationTypes.Coordinate, Location.LocationSubTypes.Bar);
		Review r1 = new Review(L, 5, "secondTest1", "assaf"), r2 = new Review(L, 5, "secondTest2", "artur");
		ReviewManager.uploadReview(r1);
		Thread.sleep(7000);
		ReviewManager.uploadReview(r2);
		Thread.sleep(7000);
		UserImpl u1 = new UserImpl("assaf", "132456", null), u2 = new UserImpl("artur", "132456", null);
		ArrayList<Review> pinned = new ArrayList<Review>();
		GetCallback<ParseObject> g = new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				pinned.add(new Review(L, arg0.getInt("rating"), arg0.getString("comment"), arg0.getString("user")));
			}
		};
		ReviewManager.getReviewByUserAndLocation(u1, L, g);
		ReviewManager.getReviewByUserAndLocation(u2, L, g);
		Thread.sleep(7000);
		System.out.println(pinned.get(0).getRating().getScore() + "  " + pinned.get(0).getContent());
		System.out.println(pinned.get(1).getRating().getScore() + "  " + pinned.get(1).getContent());
	}

	@Test
	@Category(UnitTests.class)
	public void deleteReviewTest() throws InterruptedException {
		LatLng k = new LatLng(21, 20);
		Location L = new Location(k);
		Review r1 = new Review(L, 5, "theardtest1", "assaf"), r2 = new Review(L, 5, "theardtest2", "artur");
		ReviewManager.uploadReview(r1);
		Thread.sleep(6000);
		ReviewManager.deleteReview(r1);
		Thread.sleep(10000);
		ReviewManager.deleteReview(r2);
		Thread.sleep(10000);
		ArrayList<Review> pinned = new ArrayList<Review>();
		GetCallback<ParseObject> g = new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if (arg0 != null)
					pinned.add(new Review(L, arg0.getInt("rating"), arg0.getString("comment"), arg0.getString("user")));
			}
		};
		ReviewManager.getReviewByUserAndLocation(new UserImpl("assaf", "132456", null), L, g);
		Thread.sleep(9000);
		if (!pinned.isEmpty())
			assert (false);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("user", "assaf");
		m.put("location", new ParseGeoPoint(k.getLat(), k.getLng()));
		DatabaseManager.getObjectByFields("HiddenReview", m, g);
		Thread.sleep(6000);
		if (pinned.isEmpty())
			assert (false);

	}

	@Test
	@Category(UnitTests.class)
	public void updateReviewTest() throws InterruptedException {
		LatLng k = new LatLng(21, 21);
		Location L = new Location(k);
		UserImpl u1 = new UserImpl("assaf", "132456", Privilege.Admin),
				u2 = new UserImpl("artur", "132456", Privilege.Admin),
				u3 = new UserImpl("userrrr", "132456", Privilege.Admin);
		Review r1 = new Review(L, 5, "forthtest1", "assaf"), r2 = new Review(L, 5, "forthtest2", "artur"),
				r3 = new Review(L, 5, "forthtest3", "userrrr");
		ReviewManager.uploadReview(r1);
		Thread.sleep(6000);
		ReviewManager.uploadReview(r2);
		Thread.sleep(6000);
		ReviewManager.deleteReview(r1);
		Thread.sleep(10000);
		try {
			r2.pin(u2);
		} catch (UnauthorizedAccessException e) {
		}
		ReviewManager.updateReview(r1);
		ReviewManager.updateReview(r2);
		ReviewManager.updateReview(r3);
		Thread.sleep(15000);
		ArrayList<Review> pinned = new ArrayList<Review>();
		GetCallback<ParseObject> g = new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if (arg0 == null)
					return;
				pinned.add(new Review(L, arg0.getInt("rating"), arg0.getString("comment"), arg0.getString("user")));
				if (arg0.getInt("pined") == 1)
					try {
						pinned.get(0).pin(u2);
					} catch (UnauthorizedAccessException e) {
					}
			}
		};

		ReviewManager.getReviewByUserAndLocation(u1, L, g);
		Thread.sleep(6000);
		if (!pinned.isEmpty()) {
			System.out.println("here1");
			assert (false);
		}
		ReviewManager.getReviewByUserAndLocation(u2, L, g);
		Thread.sleep(6000);
		if (pinned.isEmpty()) {
			System.out.println("here2");
			assert (false);
		}
		if (!pinned.get(0).isPinned()) {
			System.out.println("here3");
			assert (false);
		}
		pinned.remove(0);
		ReviewManager.getReviewByUserAndLocation(u3, L, g);
		Thread.sleep(6000);
		if (!pinned.isEmpty())
			return;
		System.out.println("here4");
		assert (false);
	}
}
