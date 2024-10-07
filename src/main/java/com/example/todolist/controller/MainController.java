package com.example.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.todolist.dao.MainDao;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
    MainDao mainDao;

    @GetMapping("main")
    public String main(HttpSession session,
                       Model model){
        if(session.getAttribute("user") != null){
            model.addAttribute("user", "login");
        }
        if(session.getAttribute("user") == null){
            model.addAttribute("user", "not");
        }
        return "main";
    }
}
