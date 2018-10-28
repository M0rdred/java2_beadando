package hu.tutor.util;

import com.vaadin.ui.renderers.AbstractRenderer;

public class BooleanToCheckboxRenderer extends AbstractRenderer<Object, Boolean> {

	public BooleanToCheckboxRenderer(Class<Boolean> presentationType) {
		super(presentationType);
	}

}
