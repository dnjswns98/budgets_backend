package com.ssafy.transaction.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ssafy.budget.entity.Budget;
import com.ssafy.transaction.dto.TransactionResponse;
import com.ssafy.transaction.entity.Transaction;

@Mapper
public interface TransactionRepository {
	
	@Insert("""
            INSERT INTO transactions
            (user_id, type, amount, category_id, description, date)
            VALUES
            (#{userId}, #{transaction.type}, #{transaction.amount}, #{transaction.category_id}, #{transaction.description}, #{transaction.date})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "transaction.id")
    void save(@Param("userId") Long userId,
            @Param("transaction") Transaction transaction);
    
    @Select("SELECT count(*) FROM transactions WHERE id = #{id} and user_id=#{userId}")
    Boolean existsByIdAndUserId(@Param("userId") Long userId,@Param("id") Long id);
    
    @Update("""
            UPDATE transactions
            SET
                category_id = #{transaction.category_id},
                type = #{transaction.type},
                amount = #{transaction.amount},
                description = #{transaction.description},
                date = #{transaction.date}
            WHERE id = #{id} AND user_id = #{userId}
            """)
    void update(@Param("userId") Long userId, @Param("id") Long id, @Param("transaction") Transaction transaction);
    
    @Delete("DELETE FROM transactions  WHERE id = #{id} AND user_id = #{userId}")
    void delete(@Param("userId") Long userId, @Param("id") Long id);

    // ✅ 이번 달 지출 합계 계산 메서드
    @Select("""
        SELECT COALESCE(SUM(amount), 0)
        FROM transactions
        WHERE user_id = #{userId}
          AND category_id = #{categoryId}
          AND type = 'EXPENSE'
          AND date BETWEEN #{startDate} AND #{endDate}
        """)
    Integer getTotalSpentForCategory(@Param("userId") Long userId,
                                     @Param("categoryId") Integer categoryId,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);


	@Select("SELECT id,user_id, type, amount, category_id, description, date FROM transactions WHERE id= #{id}")
	List<TransactionResponse> allshow(Long userId);
}
