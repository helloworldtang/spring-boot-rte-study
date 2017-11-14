package com.tangcheng.editor.rte.u.editor.repository;

import com.tangcheng.editor.rte.u.editor.domain.BlogUEditor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tangcheng
 * 2017/11/10
 */
public interface BlogRepository extends JpaRepository<BlogUEditor, Long> {
}
