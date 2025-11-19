package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品管理")
public class DishController {
    @Autowired
    private DishService dishService;

    @ApiOperation("新增菜品")
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody DishDTO dishDTO) {
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @ApiOperation("分页查询菜品")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        return Result.success(dishService.pageQuery(dishPageQueryDTO));
    }

    @ApiOperation("批量删除菜品")
    @RequestMapping(method = RequestMethod.DELETE)
    public Result delete(@RequestParam("ids") List<Long> ids) {
        dishService.deleteBatch(ids);
        return Result.success();
    }

    @ApiOperation("根据id查询菜品")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<DishVO> getById(@PathVariable Long id) {
        return Result.success(dishService.getByIdWithFlavor(id));
    }

    @ApiOperation("修改菜品")
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody DishDTO dishDTO) {
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }

    @ApiOperation("根据id起售/停售菜品")
    @RequestMapping(value = "/status/{status}", method = RequestMethod.POST)
    public Result updateStatus(@PathVariable Integer status, @RequestParam("id") Long id) {
        dishService.updateStatus(status, id);
        return Result.success();
    }
}
