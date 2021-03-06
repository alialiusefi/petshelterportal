package by.training.finaltask.dao;

import by.training.finaltask.entity.UserInfo;
import by.training.finaltask.exception.PersistentException;

import java.util.List;

public interface UserInfoDAO extends DAO<UserInfo> {

    UserInfo get(Integer userID) throws PersistentException;

    List<UserInfo> getAll(int offset, int rowcount) throws PersistentException;

    List<UserInfo> getAllStaffByFirstName(String firstname, int offset, int rowcount) throws PersistentException;

    boolean delete(Integer userID) throws PersistentException;

    List<UserInfo> getAllStaff(int offset, int rowcount) throws PersistentException;

    List<UserInfo> getAllStaffByPhone(long phone, int offset, int rowcount) throws PersistentException;

}
