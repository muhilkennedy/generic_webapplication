package com.mken.itinerary.api;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mken.base.util.CommonUtil;
import com.mken.itinerary.entity.Destination;
import com.mken.itinerary.excel.ItineraryExcelParser;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("admin/itinerary")
@ValidateUserToken
public class AdminItineraryController {
	
	@PostMapping(value = "/parse", produces = MediaType.APPLICATION_JSON)
	public void createDestination(@RequestParam("file") MultipartFile excelFile) throws IllegalStateException, IOException{
		ItineraryExcelParser excel = new ItineraryExcelParser(CommonUtil.generateFileFromMutipartFile(excelFile));
		excel.processItinerarySheet();
	}

}
