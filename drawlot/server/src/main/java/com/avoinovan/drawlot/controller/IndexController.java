package com.avoinovan.drawlot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author by avoinovan
 */
@Controller
public class IndexController {

    @GetMapping("/regcomplete")
    public ModelAndView completeRegistration() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("platform", "Youtube");
        return new ModelAndView("regcomplete", params);
    }
}
