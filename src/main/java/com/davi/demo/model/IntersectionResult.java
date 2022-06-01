package com.davi.demo.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class IntersectionResult {

	@NonNull
	private Point point1;

	@NonNull
	private Point point2;
}
