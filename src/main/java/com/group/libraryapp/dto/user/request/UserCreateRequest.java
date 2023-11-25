package com.group.libraryapp.dto.user.request;

public class UserCreateRequest {
    private String name;
    private Integer age; // 그냥 int는 null 안됨!!!

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
