package com.controller;

import com.model.User;
import com.validator.ValidGroupEdit;
import com.validator.ValidGroupInsert;
import com.validator.ValidGroupSearch;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description 参数校检用例
 * @Author Hexiaoshu
 * @Date 2021/4/17
 * @modify
 */

@RestController
@RequestMapping("/validator")
@Validated  //请求参数校检
public class ValidatorController {

    /**
     * get型接口测试
     * @param id
     * @return
     */
    @GetMapping("/getInfo")
    public Object get(@Valid @NotNull(message = "id不能为空") Long id){
        return id;
    }

    /**
     * get型接口测试
     * @param user
     * @return Object
     */
    @GetMapping("/search")
    public Object get(@Validated(ValidGroupSearch.class) User user){
        return user;
    }

    /**
     * 接口分组测试
     * @param user
     * @return Object
     */
    @PostMapping("/add")
    public Object add(@Validated(ValidGroupInsert.class) @RequestBody User user){
        return user;
    }

    /**
     * 接口分组测试
     * @param user
     * @return Object
     */
    @PutMapping("/edit")
    public Object edit(@Validated(ValidGroupEdit.class) @RequestBody User user){
        return user;
    }


    /**
     * 删除参数测试
     * - List 传参, curl: localhost:8080/validator/del?ids=1&ids=2
     * - String 传参， curl: localhost:8080/validator/del?ids=1,2,3,4  ->String ids
     * @param ids
     * @return
     */
    @DeleteMapping("/del")
    public Object del(@Valid @NotEmpty(message = "id不能为空") @RequestParam("ids") List<Long> ids){
        return ids;
    }

}
