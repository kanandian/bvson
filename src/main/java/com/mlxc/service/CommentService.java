package com.mlxc.service;

import com.mlxc.entity.Comment;

import java.util.List;

public interface CommentService {

    public List<Comment> getCommentsByCommodityId(long commodityId);
}
