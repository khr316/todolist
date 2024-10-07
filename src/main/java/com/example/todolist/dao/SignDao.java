package com.example.todolist.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SignDao {
    @Autowired
    JdbcTemplate jt;

    public void userInsert(String id,
                           String pw,
                           String email,
                           String phone){
        String sql = "insert into user(id,pw,email,phone) ";
               sql += "values(?,?,?,?)";
        jt.update(sql, id,pw,email,phone);
    }

    public int userCNT(String id, String pw){
        String sql = "select count(*) from user where id = ? and pw = ?";
        return jt.queryForObject(sql, int.class, id,pw);
    }

    public int userDel(String id){
        String sql = "select del from user where id = ?";
        return jt.queryForObject(sql,int.class, id);
    }

    public void updateUser(String id){
        String sql = "update user set del = 0 where id = ?";
        jt.update(sql, id);
    }
}
