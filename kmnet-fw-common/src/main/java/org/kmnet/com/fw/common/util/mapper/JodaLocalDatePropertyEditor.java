package org.kmnet.com.fw.common.util.mapper;

import java.beans.PropertyEditorSupport;

import org.joda.time.LocalDate;

public class JodaLocalDatePropertyEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(final String text) throws IllegalArgumentException {

		setValue(new LocalDate(text));
	}

	@Override
	public void setValue(final Object value) {

		super.setValue(value == null || value instanceof LocalDate ? value : new LocalDate(value));
	}

	@Override
	public LocalDate getValue() {

		return (LocalDate) super.getValue();
	}

	@Override
	public String getAsText() {

		return getValue().toString();
	}
}
