package com.user.management.service.impl;

import com.user.management.config.BusinessException;
import com.user.management.dto.*;
import com.user.management.enums.Role;
import com.user.management.models.User;
import com.user.management.repository.TokenRepository;
import com.user.management.repository.UserRepository;
import com.user.management.service.AuthenticationService;
import com.user.management.service.UserService;
import com.user.management.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public String helloUser(String name) {
        return String.format("hello user %s", name);
    }

    @Override
    public GeneralResponse<CreateUserResponse> saveUser(CreateUserRequest request) throws BusinessException{
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);

        CreateUserResponse responseDTO = CreateUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdDate(user.getCreatedDate())
                .modifiedDate(user.getModifiedDate())
                .build();
        return new GeneralResponse<>("00", "data successfully saved", responseDTO);
    }

    @Override
    public GeneralResponse<CreateUserResponse> updateUser(Long id, CreateUserRequest request) throws BusinessException {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            userRepository.save(user);

            CreateUserResponse responseDTO = CreateUserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .createdDate(user.getCreatedDate())
                    .modifiedDate(user.getModifiedDate())
                    .build();
            return new GeneralResponse<>("00", "data successfully saved", responseDTO);
        } else {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "01", "User not found");
        }
    }

    @Override
    public GeneralResponse<DetailUserResponse> detailUser(Long id) throws BusinessException {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            DetailUserResponse responseDTO = DetailUserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .createdDate(user.getCreatedDate())
                    .modifiedDate(user.getModifiedDate())
                    .build();

            return new GeneralResponse<>("00", "success get detail data", responseDTO);
        }

        throw new BusinessException(HttpStatus.BAD_REQUEST, "01", "User not found");
    }

    @Override
    public GeneralResponse deleteUser(Long id) throws BusinessException {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);

            return new GeneralResponse("00", "Data successfully deleted", null);
        }
        throw new BusinessException(HttpStatus.BAD_REQUEST, "01", "User not found");
    }

    @Override
    public PaginationResponse<List<DetailUserResponse>> listUser(PaginationRequest<ListUserFilterRequest> request) throws BusinessException {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit(), Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<User> page = userRepository.findAll(
                Specification.where(UserSpecification.hasNameLike(request.getFilter()))
                        .and(UserSpecification.hasEmailLike(request.getFilter()))
                        .and(UserSpecification.hasUsernameLike(request.getFilter())),
                pageable);

        Long recordTotal = userRepository.count(
                Specification.where(UserSpecification.hasNameLike(request.getFilter()))
                        .and(UserSpecification.hasEmailLike(request.getFilter()))
                        .and(UserSpecification.hasUsernameLike(request.getFilter()))
        );

        return new PaginationResponse<>(
               recordTotal.intValue(),
                this.convertListEntity(page.getContent()),
                request.getLimit()
        );
    }

    List<DetailUserResponse> convertListEntity(List<User> users) {
        List<DetailUserResponse> list = new ArrayList<>();
        for (User user : users) {
            DetailUserResponse detail = DetailUserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .createdDate(user.getCreatedDate())
                    .modifiedDate(user.getModifiedDate())
                    .build();

            list.add(detail);
        }

        return list;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
