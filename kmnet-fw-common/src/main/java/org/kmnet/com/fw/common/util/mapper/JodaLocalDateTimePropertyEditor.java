package org.kmnet.com.fw.common.util.mapper;

import java.beans.PropertyEditorSupport;

import org.joda.time.LocalDateTime;

public class JodaLocalDateTimePropertyEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(final String text) throws IllegalArgumentException {

		setValue(new LocalDateTime(text));
	}

	@Override
	public void setValue(final Object value) {

		super.setValue(value == null || value instanceof LocalDateTime ? value : new LocalDateTime(value));
	}

	@Override
	public LocalDateTime getValue() {

		return (LocalDateTime) super.getValue();
	}

	@Override
	public String getAsText() {

		return getValue().toString();
	}
}
