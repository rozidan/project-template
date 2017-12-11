package com.company.template.client;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Idan Rozenfeld
 */
@FunctionalInterface
public interface IdentifierType<V> {
    @JsonValue
    V getValue();
}
