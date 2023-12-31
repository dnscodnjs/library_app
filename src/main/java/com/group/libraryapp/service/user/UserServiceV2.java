package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional // 아래있는 함수가 시작될때 start transaction; (트랜잭션 시작)
    // 함수가 예외없이 끝났다면 commit;
    // 혹시라도 문제가 있다면 rollback;
    public void saveUser(UserCreateRequest request) {
        //INSERT SQL 자동으로 날라감
        User u = userRepository.save(new User(request.getName(), request.getAge()));
        //u.getId(); // 1 2 3 으로 들어잇게됨
        //throw new IllegalArgumentException(); // 이렇게 강제로 오류를 두면 rollback처리가 된다(저장 안됨)
        //IOException 과 같은 Checked Exception 은 롤백이 일어나지 않음
    }

    @Transactional(readOnly = true) //조회하는 옵션만 쓰면 가능 (성능적 이점)
    public List<UserResponse> getUsers() {
        // 해당 테이블에 있는 모든 데이터를 가져옴
        // select * from user; 코드임
        return userRepository.findAll().stream() // userRepository 에서 유저정보를 가져온 다음에 stream으로 바꿔서
                .map(UserResponse::new) // 스트림에서 유저를 UserResponse로 변환시키고
                .collect(Collectors.toList()); // 다시 리스트로 만들어서 반환
    }

    @Transactional
    public void updateUser(UserUpdateRequest request) {
        // select * from user where id = ?; 코드
        // 결과 : Optional<User>
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new); //예외 처리

        user.updateName(request.getName());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String name) {
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
