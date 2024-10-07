package com.example.todolist.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MyPageDao {
    @Autowired
    JdbcTemplate jt;

    public List<Map<String,Object>> selectInfo(String id){
        String sql = "select seq, id, pw, email, phone from user where id = ?";
        return jt.queryForList(sql, id);
    }

    public void deleteUser(String seq){
        String sql = "update user set del=1 where seq = ?";
        jt.update(sql, seq);
    }

    public void updatePW(String seq, String pw){
        String sql = "update user set pw = ? where seq = ?";
        jt.update(sql, pw, seq);
    }

    public void updateEM(String seq, String email){
        String sql = "update user set email = ? where seq = ?";
        jt.update(sql, email, seq);
    }

    public void updatePH(String seq, String phone){
        String sql = "update user set phone = ? where seq = ?";
        jt.update(sql, phone, seq);
    }
}
