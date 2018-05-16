package com.mlxc.service.impl;

import com.mlxc.dao.CommentRepository;
import com.mlxc.entity.Comment;
import com.mlxc.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentsByCommodityId(long commodityId) {
        return commentRepository.getCommentsByCommodityId(commodityId);
    }
}
