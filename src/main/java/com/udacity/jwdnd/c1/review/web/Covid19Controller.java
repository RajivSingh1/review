package com.udacity.jwdnd.c1.review.web;

//import com.udacity.jwdnd.c1.review.model.LocationStats;
import com.udacity.jwdnd.c1.review.service.Covid19Service;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping(value = "/api/v1",produces = MediaType.APPLICATION_JSON_VALUE)
public class Covid19Controller {
private final Covid19Service covid19Service;


    public Covid19Controller(Covid19Service covid19Service) {
        this.covid19Service = covid19Service;
    }
    @GetMapping("/covid19/diagnose")
    public ResponseEntity diagnose(@RequestParam String imageurl)
    {
String answer =covid19Service.diagonase(imageurl);
return ResponseEntity.ok(Collections.singletonMap("result",answer));
    }



}