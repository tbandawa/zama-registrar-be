package me.tbandawa.web.skyzmetro.daos;

import java.util.List;

import me.tbandawa.web.skyzmetro.entities.UserToken;

public interface TokenDao {
	UserToken addToken(UserToken userToken);
	List<UserToken> getToken(long userId);
	int deleteToken(long userId);
}
