package com.davi.demo.controller;

import com.davi.demo.model.AdjacencyResult;
import com.davi.demo.model.ContainmentResult;
import com.davi.demo.model.IntersectionResult;
import com.davi.demo.model.Point;
import com.davi.demo.model.Rectangle;
import com.davi.demo.model.RectangleRequest;
import com.davi.demo.service.RectangleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class RectangleControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected RectangleService rectangleService;


	@Test
	public void shouldReturnIntersection() throws Exception {
		val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
		val r2 = new Rectangle(0.5, 0.5, 1.0, 1.0);

		val rectangleRequest = new RectangleRequest(r1, r2);
		val requestBody = new ObjectMapper().writeValueAsString(rectangleRequest);

		val intersectionResult = createIntersectionResult(0.5, 1.0, 1.0, 0.5);
		val expectedBody = new ObjectMapper().writeValueAsString(intersectionResult);

		when(rectangleService.calculateIntersection(r1, r2))
				.thenReturn(Optional.of(intersectionResult));

		mockMvc.perform(post("/api/rectangle/intersection")
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(content().string(expectedBody));
	}

	@Test
	public void shouldReturnNoIntersection() throws Exception {
		val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
		val r2 = new Rectangle(2.0, 2.0, 1.0, 1.0);

		val rectangleRequest = new RectangleRequest(r1, r2);
		val requestBody = new ObjectMapper().writeValueAsString(rectangleRequest);

		when(rectangleService.calculateIntersection(r1, r2))
				.thenReturn(Optional.empty());

		mockMvc.perform(post("/api/rectangle/intersection")
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isNotFound());
	}

	@Test
	public void shouldReturnContainment() throws Exception {
		val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
		val r2 = new Rectangle(0.0, 0.0, 0.5, 0.5);

		val rectangleRequest = new RectangleRequest(r1, r2);
		val requestBody = new ObjectMapper().writeValueAsString(rectangleRequest);

		val containmentResult = new ContainmentResult(true);
		val expectedBody = new ObjectMapper().writeValueAsString(containmentResult);

		when(rectangleService.calculateContainment(r1, r2))
				.thenReturn(containmentResult);

		mockMvc.perform(post("/api/rectangle/containment")
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(content().string(expectedBody));
	}

	@Test
	public void shouldReturnNoContainment() throws Exception {
		val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
		val r2 = new Rectangle(0.0, 0.0, 2.0, 0.5);

		val rectangleRequest = new RectangleRequest(r1, r2);
		val requestBody = new ObjectMapper().writeValueAsString(rectangleRequest);

		val containmentResult = new ContainmentResult(false);
		val expectedBody = new ObjectMapper().writeValueAsString(containmentResult);

		when(rectangleService.calculateContainment(r1, r2))
				.thenReturn(containmentResult);

		mockMvc.perform(post("/api/rectangle/containment")
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(content().string(expectedBody));
	}

	@Test
	public void shouldReturnAdjacency() throws Exception {
		val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
		val r2 = new Rectangle(1.0, 0.0, 1.0, 1.0);

		val rectangleRequest = new RectangleRequest(r1, r2);
		val requestBody = new ObjectMapper().writeValueAsString(rectangleRequest);

		val adjacencyResult = new AdjacencyResult(true);
		val expectedBody = new ObjectMapper().writeValueAsString(adjacencyResult);

		when(rectangleService.calculateAdjacency(r1, r2))
				.thenReturn(adjacencyResult);

		mockMvc.perform(post("/api/rectangle/adjacency")
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(content().string(expectedBody));
	}

	@Test
	public void shouldReturnNoAdjacency() throws Exception {
		val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
		val r2 = new Rectangle(2.0, 0.0, 1.0, 1.0);

		val rectangleRequest = new RectangleRequest(r1, r2);
		val requestBody = new ObjectMapper().writeValueAsString(rectangleRequest);

		val adjacencyResult = new AdjacencyResult(false);
		val expectedBody = new ObjectMapper().writeValueAsString(adjacencyResult);

		when(rectangleService.calculateAdjacency(r1, r2))
				.thenReturn(adjacencyResult);

		mockMvc.perform(post("/api/rectangle/adjacency")
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(content().string(expectedBody));
	}

	private IntersectionResult createIntersectionResult(final Double x1, final Double  y1, final Double  x2, final Double  y2) {
		val pointA = new Point(x1, y1);
		val pointB = new Point(x2, y2);
		return new IntersectionResult(pointA, pointB);
	}
}
