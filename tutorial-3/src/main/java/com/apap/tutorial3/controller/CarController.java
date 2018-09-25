package com.apap.tutorial3.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.CarModel;
import com.apap.tutorial3.service.CarService;

@Controller
public class CarController {
	@Autowired
	private CarService mobilService;
	
	@RequestMapping("/car/add")
	public String add(@RequestParam(value = "id", required = true) String id,
					@RequestParam(value = "brand", required = true) String brand,
					@RequestParam(value = "type", required = true) String type,
					@RequestParam(value = "price", required = true) Long price,
					@RequestParam(value = "amount", required = true) Integer amount) {
		CarModel car = new CarModel(id, brand, type, price, amount);
		mobilService.addCar(car);
		return "add";
	}
	
	@RequestMapping(value = {"car/view/", "car/view/{id}"})
	public String view(@PathVariable Optional<String> id, Model model) {
		CarModel archive = null;
		String isIdAda = null;
		if (id.isPresent()) {
			archive = mobilService.getCarDetail(id.get());
			isIdAda = "ada ID";
			model.addAttribute("id", id.get());
		}
		
		model.addAttribute("car", archive);
		model.addAttribute("isIdAda", isIdAda);
		
		return "view-car";
	}
	
	@RequestMapping("car/viewall")
	public String viewall(Model model) {
		List<CarModel> archive = mobilService.getCarList();
		
		model.addAttribute("listCar", archive);
		return "viewall-car";
	}
}