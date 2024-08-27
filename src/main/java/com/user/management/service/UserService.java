package com.user.management.service;

import com.user.management.config.BusinessException;
import com.user.management.dto.*;

import java.util.List;

public interface UserService {

    String helloUser(String name);

    GeneralResponse<CreateUserResponse> saveUser(CreateUserRequest request) throws BusinessException;

    GeneralResponse<CreateUserResponse> updateUser(Long id, CreateUserRequest request) throws BusinessException;

    GeneralResponse<DetailUserResponse> detailUser(Long id) throws BusinessException;

    GeneralResponse deleteUser(Long id) throws BusinessException;

    PaginationResponse<List<DetailUserResponse>> listUser(PaginationRequest<ListUserFilterRequest> request) throws BusinessException;
}
