package hu.tutor.view.component;

import org.vaadin.inputmask.InputMask;

import com.vaadin.ui.TextField;

public class PhoneField extends TextField {

	public static final long serialVersionUID = 7626749860431859953L;

	public PhoneField() {
		this.addPhoneMask();
	}

	public PhoneField(String caption) {
		this.addPhoneMask();
		this.setCaption(caption);
	}

	private void addPhoneMask() {
		InputMask phoneMask = new InputMask("\\d{2}/\\d{2} \\d{3} \\d{3,4}");
		phoneMask.setPrefix("+");
		phoneMask.setAllowMinus(false);
		phoneMask.setAutoUnmask(true);
		phoneMask.setRegexMask(true);
		phoneMask.extend(this);
	}

}
