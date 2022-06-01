package com.davi.demo.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class ContainmentResult {

	@NonNull
	private Boolean isContained;
}
