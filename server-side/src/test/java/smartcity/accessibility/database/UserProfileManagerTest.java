package smartcity.accessibility.database;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import smartcity.accessibility.categories.BranchTests;
import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.socialnetwork.UserProfile;

public class UserProfileManagerTest {
	private static AbstractUserProfileManager manager;
	private static Map<String, Object> m;
	private static Map<String, Object> m_noid;
	private static UserProfile user1;
	protected static Database db;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		setUpMock();
		Injector injector = Guice.createInjector(new DatabaseTestModule());
		UserProfileManager.initialize(injector.getInstance(UserProfileManager.class));
		manager = UserProfileManager.instance();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test(timeout=500)
	@Category({ BranchTests.class, UnitTests.class })
	public void testBackgroundCalls() throws UserNotFoundException {
		assertEquals(null, manager.get("a", c->{}));
		assertEquals(null, manager.put(new UserProfile("a"), c->{}));
		assertEquals(null, manager.update(new UserProfile("a"), c->{}));
		assertEquals(null, manager.delete(new UserProfile("a"), c->{}));
	}

	@Test
	@Category({ BranchTests.class, UnitTests.class })
	public void testGet() throws UserNotFoundException, InterruptedException {
		UserProfile pf = manager.get("alexaxa", null);
		assertEquals(25, pf.getRating());
		assertEquals(5, pf.getNumOfReviews());
		assertEquals("alexaxa", pf.getUsername());
	}
	
	@Test
	@Category({ BranchTests.class, UnitTests.class })
	public void testPut() {
		assertEquals(true, manager.put(user1, null));
		Mockito.verify(db).put(UserProfileManager.DATABASE_CLASS, m_noid);
	}
	
	@Test
	@Category({ BranchTests.class, UnitTests.class })
	public void testUpdate() {
		assertEquals(true, manager.update(user1, null));
		Mockito.verify(db).update(UserProfileManager.DATABASE_CLASS, "MY_ID", m_noid);
	}
	
	@Test
	@Category({ BranchTests.class, UnitTests.class })
	public void testDelete() {
		assertEquals(true, manager.delete(user1, null));
		Mockito.verify(db).delete(UserProfileManager.DATABASE_CLASS, "MY_ID");
	}
	
	public static void setUpMock(){
		db = Mockito.mock(Database.class);
		m = new HashMap<>();
		m.put(UserProfileManager.ID_FIELD_NAME, "MY_ID");
		m.put(UserProfileManager.USERNAME_FIELD, "alexaxa");
		m.put(UserProfileManager.RATING_FIELD, 25);
		m.put(UserProfileManager.NUM_OF_REVIEWS_FIELD, 5);
		m_noid = new HashMap<>(m);
		m_noid.remove(UserProfileManager.ID_FIELD_NAME);
		List<Map<String, Object>> l = new ArrayList<>();
		l.add(m);
		Mockito.when(db.get(Mockito.anyString(), Mockito.anyMap())).thenReturn(l);
		Mockito.when(db.update(Mockito.anyString(), Mockito.anyString(), Mockito.anyMap())).thenReturn(true);
		Mockito.when(db.delete(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		user1 = UserProfileManager.fromMap(m);
	}
	
	public static class DatabaseTestModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(Database.class).toInstance(db);
		}
	}

}