package com.tangcheng.editor.rte.markdown.editor.md.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author tangcheng
 * 2017/11/09
 */
@Data
@NoArgsConstructor
@Entity
public class Blog implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String mdContent;

    @Column(columnDefinition = "text", nullable = false)
    private String htmlContent;

    private Date createTime;
    private Date updateTime;

}
