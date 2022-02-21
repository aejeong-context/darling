package so.ego.re_darling.domains.diary.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryPlaceRepository extends JpaRepository<DiaryPlace, Long> {

  List<DiaryPlace> findByDiaryId(Long diaryIndex);
}
