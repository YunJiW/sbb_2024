package com.mysite.sbb.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentForm {

    @NotEmpty
    String content;
}
