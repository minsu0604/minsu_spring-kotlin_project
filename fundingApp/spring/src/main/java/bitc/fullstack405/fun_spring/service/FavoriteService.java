package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.FavoriteCD_Dto;
import bitc.fullstack405.fun_spring.dto.FavoriteDto;
import bitc.fullstack405.fun_spring.entity.FavoriteEntity;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;

import java.util.List;

public interface FavoriteService {

    int getFavoriteUserCount(int projectId);

    void createFavorite(FavoriteCD_Dto favoriteDto);

    void deleteFavorite(FavoriteCD_Dto favoriteDto);

    List<FavoriteEntity> getFavoriteListByUserId(String userId);

    boolean checkFavorite(FavoriteCD_Dto favorite);
}
