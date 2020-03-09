package dao;

import entity.Friend;

import java.util.List;


public interface FriendDao {
    /**
     * 添加一条用户与医院的签约关系
     */
    int insertFriend(Friend friend);

    /**
     * 更新一条用户与医院的签约关系
     * @param friend
     * @return
     */
    int updateFriend(Friend friend);

    /**
     * 用户发起解约
     * @param
     * @return
     */
    int delete(Friend friend);

    /**
     * 返回与当前医院签约的所有用户（审核和未审核的）
     * @param hospitalId
     * @return
     */
    List<Friend> selectByHospitalId(int hospitalId);

    /**
     * 返回某个用户与医院的签约情况
     * @param userId
     * @return
     */
    List<Friend> selectByUserId(int userId);
}
