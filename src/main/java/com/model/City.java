package com.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * @Description
 * @Author Hexiaoshu
 * @Date 2021/4/17
 * @modify
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class City {

    /** id */
    @Id
    private Long id;

    private Long parentid;

    /** name */
    private String name;

    @Transient
    private List<City> child;

}
