package com.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
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
    private Long id;

    /** name */
    private String name;

    private Date createTime;


}