package com.test.tenant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.SecureRandom;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.base.service.BaseSession;
import com.base.util.GenericResponse;
import com.core.application.CoreApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenant.api.model.TenantRequestBody;
import com.tenant.dao.TenantRepository;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.entity.TenantOrigin;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes={CoreApplication.class})
public class TenantMVCTests {
	
	private ObjectMapper om = new ObjectMapper();
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private TenantRepository tenantRepository;
	
	@Autowired
	private BaseSession baseSession;

	private void setUpSession() {
		if(baseSession.getTenantInfo() == null) {
			mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
			Tenant tenant = tenantRepository.findTenantByUniqueName("devTenant");
			baseSession.setTenantId(tenant.getRootId());
			baseSession.setTenantInfo(tenant);
		}
	}
	
	@BeforeEach
	public void setUp() {
		setUpSession();
	}
	
	@Test
	@Order(1)
	public void pingTenant() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Tenant", "devTenant");
		MvcResult result = mockMvc.perform(get("/tenant/ping").headers(headers)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		GenericResponse response = om.readValue(resultContent, GenericResponse.class);
		Assert.assertTrue(response.getStatusCode() == 200);
	}
	
	@Test
	@Order(2)
	public void createTenant() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Tenant", "devTenant");
		Tenant tenant = new Tenant();
		SecureRandom random = new SecureRandom();
		int randomInt = random.nextInt();
		tenant.setTenantUniqueName("UnitTestTenant"+randomInt);
		tenant.setTenantName("UnitTestTenant"+randomInt);
		TenantDetails tDetails = new TenantDetails();
		tDetails.setTenantCity("mpm");
		tDetails.setTenantContact("90090");
		TenantOrigin origin = new TenantOrigin();
		origin.setOrigin("http://");
		TenantRequestBody model = new TenantRequestBody();
		String jsonRequest = om.writeValueAsString(model);
		MvcResult result = mockMvc.perform(post("/tenant/create").headers(headers).content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		GenericResponse response = om.readValue(resultContent, GenericResponse.class);
		Assert.assertTrue(response.getStatusCode() == 200);

	}

}
