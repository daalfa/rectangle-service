package com.davi.demo.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Point {

	@NonNull
	private Double x;

	@NonNull
	private Double y;
}
