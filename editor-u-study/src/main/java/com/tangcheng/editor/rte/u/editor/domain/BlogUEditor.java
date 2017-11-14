package com.tangcheng.editor.rte.u.editor.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author tangcheng
 * 2017/11/09
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "blog_u_editor")
public class BlogUEditor implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    private Date createTime;
    private Date updateTime;

}
