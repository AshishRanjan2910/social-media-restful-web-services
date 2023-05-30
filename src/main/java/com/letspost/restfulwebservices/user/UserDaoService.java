package com.letspost.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    //    JPA/Hibernate > Database
//    UserDaoService > Static List
    private static final List<User> users = new ArrayList<>();
    private static int userCount = 0;
    static {
        users.add(new User(++userCount, "Ranjan", LocalDate.now().minusYears(23)));
        users.add(new User(++userCount, "Athul", LocalDate.now().minusYears(33)));
        users.add(new User(++userCount, "Rajjo", LocalDate.now().minusYears(17)));
    }

    public List<User> findAll(){
        return users;
    }

    public User findOne(Integer id){
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public User save(User user){
        user.setId(++userCount);
        users.add(user);
        return user;
    }

    public void deleteById(Integer id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }

    public void updateUser(Integer id, User updatedUserDetails){
        deleteById(id);
        updatedUserDetails.setId(id);
        users.add(updatedUserDetails);
    }
}
