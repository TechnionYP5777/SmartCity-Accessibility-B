package smartcity.accessibility.socialnetwork;

import java.util.List;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.search.SearchQuery;

/**
 * 
 * @author Kolikant
 *
 */

public interface User {

	enum Privilege {
		GodUser,
		Admin, RegularUser, DefaultUser;

		private static Privilege[] allValues = values();

		public static Privilege fromOrdinal(int i) {
			return allValues[i];
		}

		public static boolean pinPrivilegeLevel(User u) {
			return u.getPrivilege().compareTo(Admin) <= 0;
		}

		public static boolean deletePrivilegeLevel(User u) {
			return u.getPrivilege().compareTo(Admin) <= 0;
		}

		public static boolean addReviewPrivilegeLevel(User u) {
			return u.getPrivilege().compareTo(RegularUser) <= 0;
		}

		public static boolean commentReviewPrivilegeLevel(User u) {
			return u.getPrivilege().compareTo(RegularUser) <= 0;
		}

		public static Privilege minCommentLevel() {
			return RegularUser;
		}

		public static Privilege minPinLevel() {
			return Admin;
		}

		public static Privilege minDeleteLevel() {
			return Admin;
		}

	}

	/**
	 * make a search using an existing query and show the user the results
	 * 
	 * @param q
	 *            is the query to be used for the search
	 */
	static void search(SearchQuery __) {
		// q.Search();
	}

	public String getName();

	public void setLocalName(String name) throws UnauthorizedAccessException;

	public String getPassword();

	public Privilege getPrivilege();

	public List<SearchQuery> getFavouriteSearchQueries();

	public void setFavouriteSearchQueries(String favouriteQueries);

	public void setFavouriteSearchQueries(List<SearchQuery> favouriteQueries);

	public String getLocalName();

}
