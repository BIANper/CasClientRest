package com.yuyu.first.controller;

import com.yuyu.common.utils.CasClient;
import com.yuyu.common.vo.LoginVo;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody LoginVo loginVo) {

        String tk = CasClient.timsCasClient(loginVo.getUsername(), loginVo.getPassword());
        return tk;
    }

}
