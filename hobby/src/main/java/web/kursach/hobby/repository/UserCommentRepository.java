package web.kursach.hobby.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.kursach.hobby.entity.UserComment;

@Repository
public interface UserCommentRepository extends JpaRepository<UserComment, Long> {

    Optional<UserComment> findById(Long id);
    
    List<UserComment> findByUserId(Long userId);
    List<UserComment> findByHobbyId(Long hobbyId);
    
}
