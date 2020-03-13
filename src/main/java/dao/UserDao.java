package dao;

import entity.User;

import java.util.List;

public interface UserDao {
    int insert(User user);
    int update(User user);
    int delete(long userId);
    User getUserIdByUserName(String userName);
    User selectByUserId(long userId);
    List<User> selectAll();
    List<User> selectByAreaId(int areaId);
}
