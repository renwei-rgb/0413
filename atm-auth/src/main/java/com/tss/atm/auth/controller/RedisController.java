package com.tss.atm.auth.controller;

import com.tss.atm.common.service.RedisService;
import com.tss.atm.common.result.Result;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("/set")
    public Result<String> set(@RequestParam String key, @RequestParam String value) {
        redisService.set(key, value);
        return Result.success("set ok");
    }

    @GetMapping("/get")
    public Result<String> get(@RequestParam String key) {
        try {
            String value = redisService.get(key);
            if (value.isEmpty()) {
                return Result.success("Key not found");  // 键不存在时的友好提示
            }
            return Result.success(value);
        } catch (Exception e) {
            log.error("Redis get error: ", e);
            return Result.error(500, "Redis操作失败: " + e.getMessage());
        }
    }

    @PostMapping("/del")
    public Result<String> del(@RequestParam String key) {
        redisService.delete(key);
        return Result.success("deleted");
    }
}
