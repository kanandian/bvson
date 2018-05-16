package com.mlxc.dao;

import com.mlxc.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> getCommentsByCommodityId(long commodityId);

}
