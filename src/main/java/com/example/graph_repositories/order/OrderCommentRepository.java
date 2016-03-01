package com.example.graph_repositories.order;

import com.example.graph.comments.OrderComment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 21.02.2016.
 */
public interface OrderCommentRepository extends CrudRepository<OrderComment,String> {

}
