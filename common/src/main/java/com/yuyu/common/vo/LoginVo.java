package com.yuyu.common.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LoginVo implements Serializable {

    private static final long serialVersionUID = 6086254933665046400L;

    @NotNull
    private String username;
    @NotNull
    private String password;

}
