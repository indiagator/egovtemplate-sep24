package com.egov.egovtemplate.operations;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpController {

    @GetMapping("test")
    public String testSaga()
    {
        return "testsaga";
    }

}
