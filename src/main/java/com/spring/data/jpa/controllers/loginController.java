package com.spring.data.jpa.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class loginController {

    @RequestMapping(value={"/login","/",""},method = RequestMethod.GET)
    public String login(@RequestParam(value = "error",required = false)String error, Model model , Principal principal, RedirectAttributes flash){
        if(principal != null) {
            try {
                return "redirect:/listar?company=BALAM RUSH&page=0";

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if (error !=null){
            model.addAttribute("error","Verifica que los datos ingresados sean correctos");
        }
        return "login";
    }

}
