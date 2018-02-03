package com.company.template.client.web.dtos;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("Tag")
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class TagDto implements Serializable {

    private static final long serialVersionUID = -876929898508450858L;

    @NonNull
    @NotBlank
    private String caption;

    @NonNull
    @NotNull
    private Integer level;
}
