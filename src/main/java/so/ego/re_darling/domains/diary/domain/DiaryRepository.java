package so.ego.re_darling.domains.diary.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
  List<Diary> findByCoupleId(Long coupleId);
}
