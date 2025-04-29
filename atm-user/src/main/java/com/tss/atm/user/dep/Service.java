package com.tss.atm.user.dep;

@org.springframework.stereotype.Service
public class Service  {
/*
    @Autowired
    private UserMapper userMapper;

    public User getByEmployeeId(String employeeId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, employeeId);
        return getOne(wrapper);
    }

    public boolean validateLogin(String userId, String password) {
        User user = getByEmployeeId(userId);
        if (user == null) {
            return false;
        }
        // 这里应该使用加密后的密码进行比对
//        return user.status() == 1; // 在职状态
        return true;
    }

    public List<User> getByDepartment(String department) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getDepartment, department);
        return userMapper.selectList(wrapper);
    }
    public LoginResponse x(LoginRequest request) {
        // 查找用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, request.getUsername());
        User user = userService.getOne(wrapper);

        // 验证用户存在且密码正确
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 验证用户状态
        if (user.getStatus() != null && user.getStatus().equals(UserStatus.INACTIVE)) {
            throw new BusinessException("用户已被禁用");
        }

        // 设置默认角色
        if (user.getRole() == null) {
            user.setRole("USER");
        }

        // 生成token
        String token = JwtUtils.generateToken(user.getUsername());
        log.info("Generated token: {}", token);

        // 存储到Redis，设置24小时过期
        String redisKey = "TOKEN:" + token;
        log.info("Storing user in Redis with key: {}", redisKey);
        redisTemplate.opsForValue().set(
                redisKey,
                user,
                TOKEN_EXPIRE_TIME,
                TimeUnit.SECONDS
        );
        log.info("User stored in Redis successfully");

        // 返回登录响应
        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .employeeId(user.getEmployeeId())
                .role(user.getRole())
                .name(user.getName())
                .department(user.getDepartment())
                .build();
    }

    private <T> void checkFieldUnique(SFunction<User, T> getField, T value, String fieldName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(getField, value);
        if (userService.getOne(wrapper) != null) {
            throw new BusinessException(fieldName + "已存在");
        }
    }

    public User register(User user) {
        // 检查唯一性
        checkFieldUnique(User::getEmployeeId, user.getEmployeeId(), "工号");
        checkFieldUnique(User::getEmail, user.getEmail(), "邮箱");
        checkFieldUnique(User::getPhone, user.getPhone(), "手机号");

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 设置默认值
        return userService.save(user) ? user : null;
    }

    public void logout(String token) {
        redisTemplate.delete("TOKEN:" + token);
    }

    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        return false;
    }

    public List<String> getUserPermissions(Long userId) {
        return null;
    }

    public List<String> getUserRoles(Long userId) {
        return null;
    }

    public User getCurrentUser(String token) {
        log.info("Getting current user with token: {}", token);
        // Extract token from Bearer header
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String redisKey = "TOKEN:" + token;
        log.info("Looking up Redis key: {}", redisKey);
        User user = redisTemplate.opsForValue().get(redisKey);
        log.info("Retrieved user from Redis: {}", user);
        if (user == null) {
            throw new BusinessException("用户未登录或登录已过期");
        }
        return user;
    }
    */
} 