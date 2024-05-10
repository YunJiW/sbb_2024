package com.mysite.sbb.user;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class newPwForm {

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;


    @NotEmpty(message = "새로운 비밀번호는 필수입니다.")
    private String newPassword;


    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
    private String CheckPassword;

}
