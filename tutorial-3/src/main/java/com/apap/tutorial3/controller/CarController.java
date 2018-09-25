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
	
	@RequestMapping(value = {"car/update/", "car/update/{id}/amount/", "car/update/{id}/amount/{jml}"})
	public String update(@PathVariable Optional<String> id, 
						 @PathVariable Optional<Integer> jml, 
						 Model model) {
		CarModel archive = null;
		String isIdAda = null;
		
		if (id.isPresent()) {
			archive = mobilService.getCarDetail(id.get());
			isIdAda = "ada ID";
			model.addAttribute("id", id.get());
			
			String jmlIsAda = null;
			
			if (archive != null) {
				if (jml.isPresent()) {
					jmlIsAda = "ada Jumlah";
					archive.setAmount(jml.get());
					model.addAttribute("jml", jml.get());
				}
			}
			model.addAttribute("jmlIsAda", jmlIsAda);
		}
		
		model.addAttribute("isIdAda", isIdAda);
		model.addAttribute("car", archive);
		
		return "update";
	}
	
	@RequestMapping(value = {"car/delete/", "car/delete/{id}"})
	public String delete(@PathVariable Optional<String> id, Model model) {
		CarModel archive = null;
		String isIdAda = null;
		
		if (id.isPresent()) {
			isIdAda = "ada ID";
			archive = mobilService.getCarDetail(id.get());
			model.addAttribute("id", id.get());
			
			if (archive != null) {
				mobilService.deleteCar(archive);
			}
		}
		
		model.addAttribute("isIdAda", isIdAda);
		model.addAttribute("car", archive);
		
		return "delete";
	}
}