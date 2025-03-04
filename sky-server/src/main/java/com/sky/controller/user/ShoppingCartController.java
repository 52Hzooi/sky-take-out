package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "购物车相关接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("购物车添加")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("购物车添加：{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("购物车查询根据当前用户")
    public Result<List<ShoppingCart>> list() {
        Long currentId = BaseContext.getCurrentId();
        log.info("购物车查询根据当前用户:{}", currentId);
        return Result.success(shoppingCartService.showShoppingCart(currentId));
    }

    @DeleteMapping("/clean")
    @ApiOperation("购物车清除根据当前用户")
    public Result clean() {
        Long currentId = BaseContext.getCurrentId();
        log.info("购物车清除根据当前用户:{}", currentId);
        shoppingCartService.cleanShoppingCart(currentId);
        return Result.success();
    }

    @PostMapping("/sub")
    @ApiOperation("购物车商品-1")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("购物车商品-1 :{}", shoppingCartDTO);
        shoppingCartService.sub(shoppingCartDTO);
        return Result.success();
    }
}
