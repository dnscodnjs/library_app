package com.group.libraryapp.domain.user;

import javax.persistence.*;

@Entity
public class User {

    @Id //pk 라는 뜻
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment 의 기능
    private Long id = null;
    @Column(nullable = false, length = 20) // name varchar(20)
    private String name;
    private Integer age; // age int 랑 같기때문에 @Column을 안써도됨

    protected User(){ // JPA를 사용하기 위한 기본 생성자
    }

    public User(String name, Integer age) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다.", name));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
    public Long getId(){
        return id;
    }
    public void updateName(String name){
        this.name = name;
    }
}
