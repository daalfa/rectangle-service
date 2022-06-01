package com.davi.demo.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Define a rectangle in an x,y plane with height and width starting at X and Y position (lower left corner of Rectangle)
 */
@Data
public class Rectangle {

	@NonNull
	private Double x;

	@NonNull
	private Double y;

	@NonNull
	private Double height;

	@NonNull
	private Double width;
}
