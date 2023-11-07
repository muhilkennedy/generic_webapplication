package com.test.tenant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import com.core.application.CoreApplication;
import com.tenant.dao.TenantRepository;
import com.tenant.entity.Tenant;
import com.tenant.service.TenantService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={CoreApplication.class})
public class TenantTests {
	
//	@Autowired
//	protected TenantService tenantService;
//	

	@BeforeEach
	void preTestCaseRun() {
		System.out.println("Hell yeah");
	}
	
	@AfterEach
	void postTestCaseRun() {
		System.out.println("executed testcase");
	}
//	@MockBean
//	protected TenantRepository tenantRepository;
//	
//	@Test
//	public void getTenantTests() {
//		Tenant tenant = new Tenant();
//		tenant.setTenantUniqueName("ten");
//		tenant.setTenantName("ten");
//		when(tenantRepository.findTenantByUniqueName("devTenant")).thenReturn(tenant);
//		assertEquals("ten", tenantService.findTenantByUniqueName("devTenant").getTenantName());
//		System.out.println("executed");
//	}
	
	@Autowired
	protected TenantRepository tenantRepository;
	
	@Test
	public void demoTest() {
		//disabled test
		assertNotEquals(null, tenantRepository.findTenantByUniqueName("prodTenant"));
		System.out.println("fount tenant");
	}
	
	

}









//@WebMvcTest(Controller.class)
//public class ControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CustomerService customerService; 
//
//...}
