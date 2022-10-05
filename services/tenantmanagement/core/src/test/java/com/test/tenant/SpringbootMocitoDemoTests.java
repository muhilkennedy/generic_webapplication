package com.test.tenant;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.core.application.CoreApplication;
import com.tenant.dao.TenantRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={CoreApplication.class})
public class SpringbootMocitoDemoTests{
	
//	@Autowired
//	protected TenantService tenantService;
//	
//	@MockBean
//	protected TenantRepository tenantRepository;
//	
//	@BeforeEach
//	void preTestCaseRun() {
//		System.out.println("Hell yeah");
//	}
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
	public void anotherDemo() {
		//disabled test
		assertNotEquals(null, tenantRepository.findTenantByUniqueName("prodTenant"));
	}

}
