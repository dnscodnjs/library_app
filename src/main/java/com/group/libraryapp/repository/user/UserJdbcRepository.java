package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 스프링 빈으로 설정
public class UserJdbcRepository {
    private JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUserNotExist(long id) {
        String readSQL = "select * from user where id = ?";

        return jdbcTemplate.query(readSQL, (rs, rowNum) -> 0, id).isEmpty(); // 비어있으면 true
    }

    public void updateUserName(String name, long id) {

        String sql = "update user set name = ? where id = ?";
        jdbcTemplate.update(sql, name, id);
    }

    public boolean isUserNotExist(String name) {

        // 예외 처리 ( 원래 없은 이름을 삭제해도 200 OK가 뜸)
        String readSQL = "select * from user where name = ?";
        return jdbcTemplate.query(readSQL, (rs, rowNum) -> 0, name).isEmpty(); // 비어있으면 true

    }

    public void deleteUser(String name) {

        String sql = "delete from user where name = ?";
        // 여기서 update는 sql update가 아닌 데이터의 변화를 뜻함
        jdbcTemplate.update(sql, name);
    }

    public void saveUser(String name, Integer age) {

        String sql = "insert into user (name, age) values (?,?)";
        jdbcTemplate.update(sql, name, age);
    }

    public List<UserResponse> getUsers() {

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
