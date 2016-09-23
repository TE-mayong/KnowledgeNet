package org.kmnet.com.fw.common.util.mapper;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.BeanWrapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class CustomBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {

	@Override
	protected void initBeanWrapper(BeanWrapper bw) {

		bw.registerCustomEditor(LocalDate.class, new JodaLocalDateTimePropertyEditor());
		bw.registerCustomEditor(LocalDateTime.class, new JodaLocalDateTimePropertyEditor());
	}

	public static <T> BeanPropertyRowMapper<T> newInstance(Class<T> mappedClass) {

		BeanPropertyRowMapper<T> newInstance = new CustomBeanPropertyRowMapper<T>();
		newInstance.setMappedClass(mappedClass);
		return newInstance;
	}
}
