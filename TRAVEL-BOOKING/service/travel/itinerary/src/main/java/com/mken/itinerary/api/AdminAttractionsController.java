package com.mken.itinerary.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mken.base.util.CommonUtil;
import com.mken.base.util.Log;
import com.mken.itinerary.entity.Attraction;
import com.mken.itinerary.exception.AttractionException;
import com.mken.itinerary.model.AttractionRequest;
import com.mken.itinerary.service.AttractionService;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("admin/attraction")
@ValidateUserToken
public class AdminAttractionsController {

	@Autowired
	private AttractionService attractionService;

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Attraction> createDestination(
			@RequestParam("destinationId") @NotNull @Valid Long destinationId,
			@RequestBody @Valid AttractionRequest attractionRequest) {
		GenericResponse<Attraction> response = new GenericResponse<>();
		Attraction atr = new Attraction();
		atr.setName(attractionRequest.getName());
		atr.setDescription(attractionRequest.getDescription());
		atr.setGmap(attractionRequest.getGmap());
		atr.setDestinationid(destinationId);
		atr = attractionService.createAttraction(atr);
		return response.setStatus(Response.Status.OK).setData(atr).build();
	}

	@PostMapping(value = "/picture", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Attraction> createDestination(@RequestParam("id") Long attractionId,
			@RequestParam("picture") MultipartFile picture)
			throws AttractionException, IllegalStateException, Exception {
		GenericResponse<Attraction> response = new GenericResponse<>();
		Attraction atr = new Attraction();
		atr = attractionService.updateAttractionPicture(attractionId, CommonUtil.generateFileFromMutipartFile(picture));
		return response.setStatus(Response.Status.OK).setData(atr).build();
	}

	@PostMapping(value = "/pictures", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Attraction> createDestinationPictures(@RequestParam("id") Long attractionId,
			@RequestParam("pictures") MultipartFile[] pictures) throws IOException, AttractionException {
		GenericResponse<Attraction> response = new GenericResponse<>();
		attractionService.updateAttractionPicture(attractionId, Arrays.stream(pictures).map(picture -> {
			try {
				return CommonUtil.generateFileFromMutipartFile(picture);
			} catch (IllegalStateException | IOException e) {
				Log.itinerary.error("Exception converting to file {}", e.getMessage());
				if (Log.itinerary.isDebugEnabled()) {
					e.printStackTrace();
				}
			}
			return null;
		}).collect(Collectors.toList()));
		return response.setStatus(Response.Status.OK).setData(attractionService.getAttractionById(attractionId))
				.build();
	}

}
