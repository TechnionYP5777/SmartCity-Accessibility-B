package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.DatabaseManager;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;

public class DeleteReviewsTest {
	User admin, user, defaultuser;
	Location location;
	Review review, review2;

	@Before
	public void setUp() throws Exception {
		DatabaseManager.initialize();
		defaultuser = UserImpl.DefaultUser();
		user = UserImpl.RegularUser("RegularUser", "", "");
		admin = UserImpl.Admin("Admin", "", "");
		location = new Location(new LatLng(100, 100));

		review = new Review(location, 5, "Nothing here", user);
		review2 = new Review(location, 3, "Nothing here either", admin);

		location.addReview(review);
		location.addReview(review2);
		// Check successful add
		nothingHasChangedCheck();
	}

	private void nothingHasChangedCheck() {
		assert location.getReviews().contains(review);
		assert location.getReviews().contains(review2);
		assert location.getReviews().size() == 2;
	}

	@Test(expected = UnauthorizedAccessException.class)
	public void regularUserCantDelete() throws UnauthorizedAccessException {
		try {
			location.deleteReview(user, review2);
		} catch (UnauthorizedAccessException ¢) {
			nothingHasChangedCheck();
			throw ¢;
		}
	}

	@Test(expected = UnauthorizedAccessException.class)
	public void defaultUserCantDelete() throws UnauthorizedAccessException {
		try {
			location.deleteReview(defaultuser, review);
		} catch (UnauthorizedAccessException ¢) {
			nothingHasChangedCheck();
			throw ¢;
		}
	}

	@Test
	public void adminCanDelete() throws UnauthorizedAccessException {
		location.deleteReview(admin, review);

		assert !location.getReviews().contains(review);

		assert location.getReviews().contains(review2);
		assert location.getReviews().size() == 1;
	}

	@Test
	public void userCanOwnedDelete() throws UnauthorizedAccessException {
		location.deleteReview(user, review);

		assert !location.getReviews().contains(review);

		assert location.getReviews().contains(review2);
		assert location.getReviews().size() == 1;
	}

}
