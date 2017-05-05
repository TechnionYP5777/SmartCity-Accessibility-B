package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.parse4j.ParseException;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;

public class HelpfulnessTest {
	private static User u1;
	private static User u2;
	private static User u3;
	private static Review r1;
	private static Review r2;
	private static Review r3;
	private static Location l;
	
	@BeforeClass
	public static void init(){
		u1 = UserImpl.RegularUser("Koral","123","");
		u2 = UserImpl.RegularUser("Koral2","123","");
		u3 = UserImpl.Admin("Simba", "355", "");
		LatLng c = new LatLng(39.750307, -104.999472);
		l = new Location(c);
		r1 = new Review(l, Score.getMinScore(), "very unaccessible place!", u1);
		r2 = new Review(l, 2, "middle accessibility level", u1);
		r3 = new Review(l, Score.getMaxScore(), "high accessibility level", u1);
		try {
			l.addReview(r1);
			l.addReview(r2);
			l.addReview(r3);
		} catch (ParseException e) {
			fail("shouldn't fail");
		}
	}
	
	@Test
	@Category(UnitTests.class)
	public void test() {
		try {
			r1.upvote(u1);
			r2.upvote(u2);
			r2.upvote(u3);
			r2.downvote(u1);
			r3.upvote(u1);
			r3.upvote(u2);
		} catch (UnauthorizedAccessException e) {
			fail("shouldn't fail");
		}
		
		assert u1.getHelpfulness().helpfulness() ==  2;
	}
}