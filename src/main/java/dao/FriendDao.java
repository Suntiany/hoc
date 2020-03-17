package dao;

import entity.Friend;
import entity.User;
import org.apache.ibatis.annotations.Param;

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
    List<Friend> selectByHospitalId(long hospitalId);

    /**
     * 返回所有医院管理员审核成功的用户来给用户指派家庭医生
     */
    List<Friend> selectFriendByHospitalId(long hospitalId);


    /**
     * 返回某个用户与医院的签约情况
     * @param userId
     * @return
     */
    List<Friend> selectByUserId(long userId);



    Friend selectByUserIdAndHospitalId(@Param("userId") long userId, @Param("hospitalId") long hospitalId);
}
