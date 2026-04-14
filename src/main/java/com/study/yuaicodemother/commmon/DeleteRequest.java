package com.study.yuaicodemother.commmon;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fxy
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
