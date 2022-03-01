package com.platform.base.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.platform.base.entity.City;
import com.platform.base.service.BaseService;
import com.platform.core.service.CityService;

@RestController
public class CityController {

	@Autowired
	BaseService baseService;
	
    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody City city) {
//        city.setTenantId(baseService.getTenantId());
    	cityService.save(city);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<City>> getAll() throws SQLException {
        List<City> cities = cityService.getAll();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<City>> get(@PathVariable(value = "id") String id) {
//        Optional<City> city = cityService.get(id);
    	List<City> cities = cityService.getAllRealmdata();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

//    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
//    public ResponseEntity<City> get(@PathVariable(value = "name") String name) {
//        City city = cityService.getByName(name);
//        return new ResponseEntity<>(city, HttpStatus.OK);
//    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<City> delete(@PathVariable(value = "name") String name) {
        cityService.delete(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}