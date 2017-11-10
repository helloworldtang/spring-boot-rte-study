package com.tangcheng.editor.rte.markdown.editor.md.repository;

import com.tangcheng.editor.rte.markdown.editor.md.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tangcheng
 * 2017/11/10
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {
}
