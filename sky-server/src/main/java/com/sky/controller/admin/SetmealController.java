package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐相关接口")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @ApiOperation("新增套餐")
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    @ApiOperation("分页查询套餐")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("删除套餐")
    @RequestMapping(method = RequestMethod.DELETE)
    public Result delete(@RequestParam("ids") List<Long> ids) {
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    @ApiOperation("根据id查询套餐")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<SetmealVO> getById(@PathVariable("id") Long id) {
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    @ApiOperation("根据id修改套餐")
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        setmealService.update(setmealDTO);
        return Result.success();
    }

    @ApiOperation("套餐状态起售/停售")
    @RequestMapping(value = "/status/{status}", method = RequestMethod.POST)
    public Result startOrStop(@PathVariable("status") Integer status, @RequestParam("id") Long id) {
        setmealService.startOrStop(status, id);
        return Result.success();
    }
}
