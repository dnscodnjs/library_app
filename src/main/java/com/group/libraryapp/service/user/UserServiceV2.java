package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserCreateRequest request) {
        //INSERT SQL 자동으로 날라감
        User u = userRepository.save(new User(request.getName(), request.getAge()));
        //u.getId(); // 1 2 3 으로 들어잇게됨
    }

    public List<UserResponse> getUsers(){
        // 해당 테이블에 있는 모든 데이터를 가져옴
        // select * from user; 코드임
        return userRepository.findAll().stream() // userRepository 에서 유저정보를 가져온 다음에 stream으로 바꿔서
                .map(UserResponse::new) // 스트림에서 유저를 UserResponse로 변환시키고
                .collect(Collectors.toList()); // 다시 리스트로 만들어서 반환
    }

    public void updateUser(UserUpdateRequest request){
        // select * from user where id = ?; 코드
        // 결과 : Optional<User>
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new); //예외 처리

        user.updateName(request.getName());
        userRepository.save(user);
    }
    public void deleteUser(String name){
        // select * from user where name = ?
        // findByName 은 없음
        User user = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new); // user가 없으면 null

        userRepository.delete(user);

/*        if(!userRepository.existsByName(name)){
            throw new IllegalArgumentException();
        }
        User user = userRepository.findByName(name);
        userRepository.delete(user);*/  //이렇게 쓰기도 가능
    }

}
