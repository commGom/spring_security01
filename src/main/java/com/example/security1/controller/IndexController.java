package com.example.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //view를 return
public class IndexController {

    //머스테치 기본폴더 src/main/resources로 잡힌다
    //view resolver 설정할 때, prefix : templates, suffix : .mustache
    //머스테치 의존성 라이브러리 잡으면 자동으로  설정된다.
    @GetMapping({"","/"})
    public String index(){
        return "index";
    }

}
