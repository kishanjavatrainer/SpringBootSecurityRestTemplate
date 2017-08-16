package com.infotech.app.dao;
import com.infotech.app.entities.UserInfo;
public interface UserInfoDAO {
	public abstract UserInfo getActiveUser(String userName);
}