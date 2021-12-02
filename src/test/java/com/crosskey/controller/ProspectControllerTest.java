package com.crosskey.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.crosskey.dto.ProspectDTO;
import com.crosskey.service.ProspectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@org.junit.jupiter.api.Tag("prospect")
@AutoConfigureMockMvc(addFilters = false)
public class ProspectControllerTest {

	@MockBean
	private ProspectService prospectService;
	@Autowired
	private MockMvc mockMvc;
	private static final String URL = "/prospect";

	private static final int STATUS_OK = 200;

	@Test
	public void testgetAllProspects() throws Exception {
		this.prospectService.readAllProspect();
		ResultActions actions = this.mockMvc
				.perform(
						get(URL).contentType(MediaType.APPLICATION_JSON).content(convertBeanToJson(getAllProspects())))
				.andDo(print());
		assertEquals(STATUS_OK, actions.andReturn().getResponse().getStatus(), "Test success Get Prospects");
	}

	@Test
	public void testSaveProspect() throws Exception {
		this.prospectService.processSingleProspect(populateProspect());
		ResultActions actions = this.mockMvc.perform(post((URL)));
		assertEquals(STATUS_OK, actions.andReturn().getResponse().getStatus(), "Test success process prospect");
	}

	private String convertBeanToJson(Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}

	/**
	 * Gets the prospect details.
	 *
	 * @return the prospect details
	 */
	private static List<ProspectDTO> getAllProspects() {

		List<ProspectDTO> prospect = new ArrayList<ProspectDTO>();
		prospect.add(populateProspect());

		return prospect;
	}

	private static ProspectDTO populateProspect() {
		ProspectDTO prospect = new ProspectDTO();
		//prospect.setId("1");
		prospect.setCustomerName("Juha");
		prospect.setTotalLoan(1000);
		prospect.setInterest(5);
		prospect.setYears(3);
		return prospect;
	}
}
