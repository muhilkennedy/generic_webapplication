package com.mken.itinerary.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mken.base.util.CommonUtil;
import com.mken.base.util.Log;
import com.mken.itinerary.entity.Destination;
import com.mken.itinerary.entity.DestinationSeason;
import com.mken.itinerary.exception.DestinationException;
import com.mken.itinerary.model.DestinationAttributes;
import com.mken.itinerary.model.DestinationRequest;
import com.mken.itinerary.model.SeasonRequest;
import com.mken.itinerary.service.DestinationService;
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
@RequestMapping("admin/destination")
@ValidateUserToken
public class AdminDestinationController {

	@Autowired
	private DestinationService destinationService;

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Destination> createDestination(
			@RequestBody @Valid DestinationRequest destinationRequest) {
		GenericResponse<Destination> response = new GenericResponse<>();
		Destination dst = new Destination();
		dst.setName(destinationRequest.getName());
		dst.setDescription(destinationRequest.getDescription());
		dst.setGmap(destinationRequest.getGmap());
		dst.setRating(destinationRequest.getRating());
		dst.setParentid(destinationRequest.getParentDestinationId());
		dst = destinationService.createDestination(dst);
		return response.setStatus(Response.Status.OK).setData(dst).build();
	}

	@PostMapping(value = "/picture", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Destination> createDestination(@RequestParam("id") Long destinationId,
			@RequestParam("picture") MultipartFile picture) throws IOException, DestinationException {
		GenericResponse<Destination> response = new GenericResponse<>();
		Destination dst = new Destination();
		dst = destinationService.updateDestinationPicture(destinationId,
				CommonUtil.generateFileFromMutipartFile(picture));
		return response.setStatus(Response.Status.OK).setData(dst).build();
	}
	
	@PostMapping(value = "/pictures", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Destination> createDestinationPictures(@RequestParam("id") Long destinationId,
			@RequestParam("pictures") MultipartFile[] pictures) throws IOException, DestinationException {
		GenericResponse<Destination> response = new GenericResponse<>();
		destinationService.uploadDestinationPictures(destinationId, Arrays.stream(pictures).map(picture -> {
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
		return response.setStatus(Response.Status.OK).setData(destinationService.getDestinationById(destinationId)).build();
	}

	@PostMapping(value = "/season", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Destination> createDestinationSeason(
			@RequestParam("destinationId") @NotNull Long destinationId,
			@RequestBody @Valid List<SeasonRequest> destinationSeasons) throws DestinationException {
		GenericResponse<Destination> response = new GenericResponse<>();
		destinationService.createSeasonInfoForDestination(destinationId, destinationSeasons);
		Destination dst = destinationService.getDestinationById(destinationId);
		return response.setStatus(Response.Status.OK).setData(dst).build();
	}

	@PutMapping(value = "/season", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<DestinationSeason> updateDestinationSeason(
			@RequestParam("seasonId") @NotNull Long seasonId,
			@RequestParam("scaleValue") @NotNull int scaleValue) throws DestinationException {
		GenericResponse<DestinationSeason> response = new GenericResponse<>();
		DestinationSeason dst = destinationService.updateSeasonDetail(seasonId, scaleValue);
		return response.setStatus(Response.Status.OK).setData(dst).build();
	}
	
	@PostMapping(value = "/detail", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Destination> createOrUpdateDestinationDetail(
			@RequestParam("destinationId") @NotNull Long destinationId,
			@RequestBody @Valid DestinationAttributes destinationAttributes) throws DestinationException {
		GenericResponse<Destination> response = new GenericResponse<>();
		destinationService.createDestinationDetail(destinationId, destinationAttributes);
		return response.setStatus(Response.Status.OK).setData(destinationService.getDestinationById(destinationId))
				.build();
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON)
	public GenericResponse<?> deleteDestination(@RequestParam("destinationId") @NotNull Long destinationId) {
		destinationService.deleteDestination(destinationId);
		return new GenericResponse<>().setStatus(Response.Status.OK).build();
	}

	@PutMapping(value = "/details", produces = MediaType.APPLICATION_JSON)
	public GenericResponse<Destination> updateDestinationSeason(
			@RequestParam("destinationId") @NotNull Long destinationId,
			@RequestBody @Valid DestinationAttributes destinationDetail) throws DestinationException {
		GenericResponse<Destination> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK)
				.setData(destinationService.updateDestinationDetail(destinationId, destinationDetail)).build();
	}

}
