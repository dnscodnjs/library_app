package com.group.libraryapp.controller.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    //private final List<User> users = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    // jdbc 탬플릿을 생성자에 직접 넣어주지 않아도 스프링이 알아서 JDBC 템플릿을 넣어줌
    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request) {
        //users.add(new User(request.getName(), request.getAge()));

        String sql = "insert into user (name, age) values (?,?)";
        jdbcTemplate.update(sql, request.getName(), request.getAge());
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
/*        List<UserResponse> responses = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            //responses.add(new UserResponse(i + 1, users.get(i).getName(), users.get(i).getAge()));
            responses.add(new UserResponse(i + 1, users.get(i)));
        }
        return responses;*/
        
        String sql = "select * from user";
        // 위 sql 코드로 인한 결과들을 작성한 로직에 따라 UserResponse 바꿔주는 부분
        //mapRow의 역할 : 유저 정보를 내가 선언한 타입인 UserResponse 로 바꿔주는 역할을 수행하는 함수
        return jdbcTemplate.query(sql, (rs, rowNum) -> { // 람다로 변경
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);

        });
    }
}
