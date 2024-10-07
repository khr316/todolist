package com.example.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todolist.dao.SignDao;

import jakarta.servlet.http.HttpSession;


@Controller
public class SignController {
    @Autowired
    SignDao signDao;

    @GetMapping("signup")
    public String signup(){
        return "sign/signup";
    }
    @PostMapping("signup")
    public String signupAction(@RequestParam String id,
                               @RequestParam String pw,
                               @RequestParam String email,
                               @RequestParam String phone){
        signDao.userInsert(id,pw,email,phone);
        return "redirect:/login";
    }

    @GetMapping("login")
    public String login(){
        return "sign/signin";
    }
    @PostMapping("login")
    public String loginAction(@RequestParam String id,
                              @RequestParam String pw,
                              HttpSession session,
                              Model model){
        int userCnt = signDao.userCNT(id,pw);
        int userDel = signDao.userDel(id);
        if(userCnt > 0 & userDel == 0) {
            session.setAttribute("user",id);
            return "redirect:/main";
        }
        else if(userCnt > 0 & userDel == 1){
            return "deleteuser";
        }
        else{
            model.addAttribute("error", "입력한 정보를 확인해주세요");
            return "sign/signin";
        }

    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/main";
    }

    @GetMapping("resign")
    public String resign(){
        return "sign/resign";
    }
    @PostMapping("resign")
    public String resignaction(@RequestParam String id){
        signDao.updateUser(id);
        return "sign/signin";
    }
    
}
