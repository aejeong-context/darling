package so.ego.re_darling.domains.diary.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryCommentRepository extends JpaRepository<DiaryComment,Long> {
    Optional<DiaryComment> findByDiaryIdAndUserId(Long diaryId, Long userId);
}
