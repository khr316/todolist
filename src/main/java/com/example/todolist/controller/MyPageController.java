package com.example.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todolist.dao.MyPageDao;

import jakarta.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MyPageController {
    @Autowired
    MyPageDao myPageDao;
    
    @GetMapping("mypage")
    public String page(HttpSession session, Model model){
        if (session.getAttribute("user") == null){
            return "usererror";
        }
        String id = session.getAttribute("user").toString();
        List<Map<String,Object>> infoList = myPageDao.selectInfo(id);
        model.addAttribute("infoList", infoList);
        
        return "mypage/mypage";
    }

    @GetMapping("user/del")
    public String userDelete(@RequestParam String seq,
                             HttpSession session){
        myPageDao.deleteUser(seq);
        session.invalidate();
        return "redirect:/main";
    }

    @GetMapping("user/editpw")
    public String userEditPW(@RequestParam String seq,
                             @RequestParam String pw){
        myPageDao.updatePW(seq,pw);
        return "redirect:/mypage";
    }

    @GetMapping("user/editem")
    public String userEditEM(@RequestParam String seq,
                             @RequestParam String email){
        myPageDao.updateEM(seq,email);
        return "redirect:/mypage";
    }

    @GetMapping("user/editph")
    public String userEditPH(@RequestParam String seq,
                             @RequestParam String phone){
        myPageDao.updatePH(seq,phone);
        return "redirect:/mypage";
    }
}
