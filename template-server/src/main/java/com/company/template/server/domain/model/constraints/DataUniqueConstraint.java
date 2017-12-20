package com.company.template.server.domain.model.constraints;

public interface DataUniqueConstraint {

    String getConstraintName();

    String[] getFieldNames();
}
