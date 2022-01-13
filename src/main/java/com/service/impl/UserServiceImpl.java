package com.service.impl;

import com.mapper.UserMapper;
import com.model.User;
import com.service.IUserService;
import com.tk.TkServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * IUserService
 * @author Hexiaoshu
 * @date 2020-11-28 18:11:57
 */
@Service("userService")
public class UserServiceImpl extends TkServiceImpl<UserMapper,User> implements IUserService {

}
