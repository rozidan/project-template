package com.company.template.client.api;

import com.company.template.client.web.dtos.RestPageImpl;

import org.springframework.core.ParameterizedTypeReference;


/**
 * @param <T> the type of elements in the reference page
 * @author Idan Rozenfeld
 */
public class RestPageTypeReference<T> extends ParameterizedTypeReference<RestPageImpl<T>> {
}
