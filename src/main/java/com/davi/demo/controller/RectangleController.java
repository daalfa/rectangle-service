package com.davi.demo.controller;

import com.davi.demo.model.AdjacencyResult;
import com.davi.demo.model.ContainmentResult;
import com.davi.demo.model.IntersectionResult;
import com.davi.demo.model.RectangleRequest;
import com.davi.demo.service.RectangleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/rectangle")
public class RectangleController {

	private final RectangleService rectangleService;

	public RectangleController(final RectangleService rectangleService) {
		this.rectangleService = rectangleService;
	}

	/*
		return 200 {} ?
	 */
	@PostMapping(value = "/intersection")
	public ResponseEntity<IntersectionResult> getIntersection(@RequestBody RectangleRequest rectangleRequest) {
		return ResponseEntity.of(
				rectangleService.calculateIntersection(rectangleRequest.getRectangle1(), rectangleRequest.getRectangle2()));
	}

	@PostMapping(value = "/containment")
	public ContainmentResult getContainment(@RequestBody RectangleRequest rectangleRequest) {
		return rectangleService.calculateContainment(rectangleRequest.getRectangle1(), rectangleRequest.getRectangle2());
	}

	@PostMapping(value = "/adjacency")
	public AdjacencyResult getAdjacency(@RequestBody RectangleRequest rectangleRequest) {
		return rectangleService.calculateAdjacency(rectangleRequest.getRectangle1(), rectangleRequest.getRectangle2());
	}
}
