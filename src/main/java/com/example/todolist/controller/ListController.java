package com.example.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todolist.dao.ListDao;
import java.time.LocalDate;
import jakarta.servlet.http.HttpSession;
import java.util.*;



@Controller
public class ListController {
    @Autowired
    ListDao listDao;

    @GetMapping("list/insert")
    public String listInsert(HttpSession session, Model model){
        if(session.getAttribute("user") == null){
            return "usererror";
        }
        String idNo = session.getAttribute("user").toString();
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("userNo", listDao.selectIdNo(idNo));
        return "list/insert";
    }

    @PostMapping("list/insert")
    public String insertTodoList(HttpSession session,
                                 @RequestParam String memo,
                                 @RequestParam String date,
                                 @RequestParam List<String> todoItems,
                                 RedirectAttributes redirectAttributes) {
        if(session.getAttribute("user") == null){
            return "usererror";
        }
        String user1 = session.getAttribute("user").toString();
        String user = listDao.selectIdNo(user1);
        Long todoListId = listDao.listInsert(user, memo, date);
        listDao.listItemInsert(todoListId, todoItems);
        return "redirect:/main";
    }

    @GetMapping("list/month")
    public String listMonth(HttpSession session,
                            Model model){
        if(session.getAttribute("user") == null){
            return "usererror";
        }
        String user = session.getAttribute("user").toString();
        model.addAttribute("user", user);
        return "list/month";
    }

    @GetMapping("list/day")
    public String listDay(@RequestParam(required = false) String year,
                          @RequestParam(required = false) String month, 
                          @RequestParam(required = false) String day,
                          HttpSession session,
                          Model model){
        if(session.getAttribute("user") == null){
            return "usererror";
        }
        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();

        // year, month, day가 null 또는 빈 문자열일 경우 현재 날짜로 설정
        if (year == null || year.isEmpty()) {
            year = String.valueOf(currentDate.getYear());
        }
        if (month == null || month.isEmpty()) {
            month = String.format("%02d", currentDate.getMonthValue());
        }
        if (day == null || day.isEmpty()) {
            day = String.format("%02d", currentDate.getDayOfMonth());
        }

        // 사용자 정보 가져오기
        String user = session.getAttribute("user").toString();

        // 날짜 문자열 생성
        String date = year + "-" + month + "-" + day;

        // 모델에 속성 추가
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);
        model.addAttribute("user", user);

        String user1 = session.getAttribute("user").toString();
        String id = listDao.selectIdNo(user1);

        // 할 일 목록 및 아이템 가져오기
        List<Map<String,Object>> todolist = listDao.selectTodolist(date, user);
        model.addAttribute("todolist", todolist);
        List<Map<String,Object>> todoitem = listDao.selectTodoItem(date, id);
        model.addAttribute("todoitem", todoitem);

        return "list/day";
    }

    @GetMapping("list/item")
    public String listItem(@RequestParam String seq,
                           @RequestParam String com,
                           @RequestParam String year,
                           @RequestParam String month,
                           @RequestParam String day,
                           @RequestParam String user
        ){
        listDao.updateListItem(com,seq);
        return "redirect:/list/day?year="+year+"&month="+month+"&day="+day+"&user="+user;
    }

    @GetMapping("list/item/edit")
    public String listItemEdit(@RequestParam String seq,
                               @RequestParam String content,
                               @RequestParam String year,
                               @RequestParam String month,
                               @RequestParam String day,
                               @RequestParam String user){
        listDao.updateItem(seq,content);
        return "redirect:/list/day?year="+year+"&month="+month+"&day="+day+"&user="+user;
    }

    @GetMapping("list/item/del")
    public String listItemDel(@RequestParam String seq,
                              @RequestParam String year,
                              @RequestParam String month,
                              @RequestParam String day,
                              @RequestParam String user){
        listDao.delItem(seq);
        return "redirect:/list/day?year="+year+"&month="+month+"&day="+day+"&user="+user;
    }

    @GetMapping("list/del")
    public String listDel(@RequestParam String seq){
        listDao.delList(seq);
        return "/main";
    }

    @PostMapping("list/insert/add")
    public String addInsert(@RequestParam String seq,
                            @RequestParam String item,
                            @RequestParam String date,
                            @RequestParam String user){
        String dateString = date;
        // 문자열을 '-'로 분리
        String[] parts = dateString.split("-");
        
        // 연도, 월, 일 추출
        String year = parts[0];
        String month = parts[1];
        String day = parts[2];
        
        listDao.addInsert(seq,item);
        return "redirect:/list/day?year="+year+"&month="+month+"&day="+day+"&user="+user;
    }
}
