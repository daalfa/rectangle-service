package com.davi.demo.model;

import lombok.Data;
import lombok.NonNull;

@Data
//@AllArgsConstructor
public class RectangleRequest {

	@NonNull
	private Rectangle rectangle1;

	@NonNull
	private Rectangle rectangle2;
}
