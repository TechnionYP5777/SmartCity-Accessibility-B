package smartcity.accessibility.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.database.AbstractUserProfileManager;
import smartcity.accessibility.database.ParseDatabase;
import smartcity.accessibility.socialnetwork.UserBuilder;
/**
 * @author ariel
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AdressSearchServiceTest extends ServiceTest{
	Token t;

	
	@Before
	public void setup() throws Exception {
		ParseDatabase.initialize();
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		AbstractUserProfileManager mock_UserManagerProfile = Mockito.mock(AbstractUserProfileManager.class);
		AbstractUserProfileManager.initialize(mock_UserManagerProfile);
		mockMvc.perform(post("/signup?name=me&password=1234"));
		mockMvc.perform(post("/login?name=me&password=1234"));
		this.t = Token.calcToken(new UserBuilder().setUsername("me").setPassword("1234").build());
	}
	
	@Test
	@Category(UnitTests.class)
	public void searchSuccess() throws Exception {
		String res = mockMvc.perform(post("/simpleSearch/Yehalom 70").header("authToken", this.t.getToken())).andReturn().getResponse().getContentAsString();
		System.out.println(res);
		Assert.assertTrue(res.contains(":34.9924887"));
		Assert.assertTrue(res.contains(":31.9069527"));
		Assert.assertTrue(res.contains("Yahalom St 70, Modi'in-Maccabim-Re'ut, Israel"));
	}
}
