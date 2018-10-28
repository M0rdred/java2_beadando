package hu.tutor.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ActiveParameter {
	YES("Y"), NO("N");

	@Getter
	private String value;

}
