package com.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.validator.ValidGroupEdit;
import com.validator.ValidGroupInsert;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * User
 * @author Hexiaoshu
 * @date 2020-11-28 18:11:57
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = -6170418806018348653L;
    /** id */
    @Id
    @NotNull(message = "id不能为空",groups = ValidGroupEdit.class)
    private Long id;

    /** name */
    private String userName;

    private String passWord;

    @NotNull(message = "手机号不能为空",groups = ValidGroupInsert.class)
    private String phone;

    private String status;

    private Date createTime;


}
