package com.axel.todo.specification

import com.axel.todo.entities.TodoEntity
import com.axel.todo.entities.UserEntity
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

class TodoSpecification(private val username: String?, private val todoId: Long?) : Specification<TodoEntity> {

    private lateinit var r : Root<TodoEntity>
    private lateinit var cb : CriteriaBuilder
    private lateinit var p : Predicate

    override fun toPredicate(
        root: Root<TodoEntity>,
        query: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        r = root
        cb = criteriaBuilder
        p = criteriaBuilder.and()

        addId(todoId)
        addUsername(username)


        return p
    }


    private fun addId(todoId: Long?) {
        if(todoId == null) return
        p.expressions.add(cb.equal(r.get<Long>("id"), todoId))
    }

    private fun addUsername(username: String?) {
        if(username == null) return
        p.expressions.add(cb.equal(cb.upper(r.get<UserEntity>("user").get("username")), username.uppercase()))
    }
}