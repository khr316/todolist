package com.example.todolist.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ListDao {
    @Autowired
    JdbcTemplate jt;

    public Long listInsert(String user, String memo, String date) {
        String sql = "insert into todolist (id, memo, date) VALUES (?, ?, ?)";
        jt.update(sql, user, memo, date);

        // Retrieve the ID of the newly inserted row
        String idSql = "SELECT LAST_INSERT_ID()";
        return jt.queryForObject(idSql, Long.class);
    }

    public void listItemInsert(Long todoListId, List<String> todoItems) {
        String sql = "insert into todoitems (todolist_seq, item) VALUES (?, ?)";
        for (String item : todoItems) {
            jt.update(sql, todoListId, item);
        }
    }

    public String selectIdNo(String idNo){
        String sql = "select seq from user where id = ?";
        return jt.queryForObject(sql, String.class, idNo);
    }

    public List<Map<String,Object>> selectTodolist(String date, String user){
        String sql = "select ";
               sql += "a.seq as seq, ";
               sql += "a.memo as memo, ";
               sql += "a.date as date, ";
               sql += "b.id as id ";
               sql += "from todolist a, user b ";
               sql += "where a.id = b.seq ";
               sql += "and a.date = ? ";
               sql += "and b.id = ? ";
        return jt.queryForList(sql, date, user);
    }

    public List<Map<String,Object>> selectTodoItem(String date, String id){
        String sql = "select ";
               sql += "a.seq as seq, ";
               sql += "a.todolist_seq as todolistSeq, ";
               sql += "a.item as item, ";
               sql += "a.complete as com, ";
               sql += "b.date as date ";
               sql += "from todoitems a, todolist b ";
               sql += "where a.todolist_seq = b.seq ";
               sql += "and b.seq in ";
               sql += "(select seq from todolist where date = ? and id = ?)";
        return jt.queryForList(sql, date, id);
    }

    public void updateListItem(String com, String seq){
        String sql = "update todoitems set complete = ? where seq = ?";
        jt.update(sql, com,seq);
    }

    public void updateItem(String seq, String content){
        String sql = "update todoitems set item = ? where seq = ? ";
        jt.update(sql, content, seq);
    }

    public void delItem(String seq){
        String sql = "delete from todoitems where seq = ? ";
        jt.update(sql, seq);
    }

    public void delList(String seq){
        String sql = "delete from todolist where seq = ? ";
        jt.update(sql, seq);
    }

    public void addInsert(String seq, String item){
        String sql = "insert into todoitems(todolist_seq, item) ";
               sql += "values(?,?)";
        jt.update(sql, seq, item);
    }

}
