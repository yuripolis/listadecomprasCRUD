package com.example.listadecompras.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class controller {


    @GetMapping("/index")
    public ModelAndView hello (){
        ModelAndView mv =  new ModelAndView("index");
        mv.addObject("nome", "samuka");
        return mv;
    }

    @GetMapping("/index-model")
    public String hello(Model model){
        model.addAttribute("nome", "samuka");
        return "index";
    }



}
