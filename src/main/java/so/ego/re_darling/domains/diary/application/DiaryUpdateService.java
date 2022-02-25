package so.ego.re_darling.domains.diary.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import so.ego.re_darling.domains.diary.application.dto.DiaryPlaceUpdateRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryUpdateRequest;
import so.ego.re_darling.domains.diary.domain.*;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

@RequiredArgsConstructor
@Service
public class DiaryUpdateService {

  private final UserRepository userRepository;
  private final DiaryRepository diaryRepository;
  private final DiaryCommentRepository diaryCommentRepository;
  private final DiaryPlaceRepository diaryPlaceRepository;

  public Long updateDiaryComment(DiaryUpdateRequest diaryUpdateRequest) {
    User user =
        userRepository
            .findBySocialToken(diaryUpdateRequest.getSocialToken())
            .orElseThrow(() -> new IllegalArgumentException("Invalid SocialToken"));

    Diary diary =
        diaryRepository
            .findById(diaryUpdateRequest.getDiaryId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid DiaryId Index"));

    DiaryComment diaryComment =
        diaryCommentRepository
            .findByDiaryIdAndUserId(diary.getId(), user.getId())
            .orElseGet(
                () ->
                    diaryCommentRepository.save(
                        DiaryComment.builder()
                            .diary(diary)
                            .user(user)
                            .comment(diaryUpdateRequest.getSay())
                            .build()));

    diaryComment.updateComment(diaryUpdateRequest.getSay());

    return diaryComment.getId();
  }

  @Transactional
  public Long updateDiaryPlace(DiaryPlaceUpdateRequest updateRequest) {
    DiaryPlace diaryPlace =
        diaryPlaceRepository
            .findById(updateRequest.getPlaceId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid DiaryPlace"));
    diaryPlace.updatePlace(updateRequest.getName(), updateRequest.getComment());
    return diaryPlace.getId();
  }
}
