package com.poseidon.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AboutController {

	@GetMapping("/about")
	public String about() {		
		return "about";
	}
	
	@PostMapping("/about")
	public String about(@RequestParam("names") String names, Model model) {
		List<String> members = new ArrayList<String>(Arrays.asList(names.split(",")));
		Collections.shuffle(members);
		Collections.shuffle(members);
		Collections.shuffle(members);
		
		model.addAttribute("list", members);
		
		return "about";
	}
	

}
