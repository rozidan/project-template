package com.company.template.server.domain.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Embeddable
public class Tag {

    @NonNull
    @NotBlank
    private String caption;

    @NonNull
    @NotNull
    private Integer level;
}
