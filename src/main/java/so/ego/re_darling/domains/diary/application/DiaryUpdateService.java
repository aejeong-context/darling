package so.ego.re_darling.domains.diary.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import so.ego.re_darling.domains.diary.application.dto.DiaryRegisterRequest;
import so.ego.re_darling.domains.diary.application.dto.DiaryUpdateRequest;
import so.ego.re_darling.domains.diary.domain.Diary;
import so.ego.re_darling.domains.diary.domain.DiaryComment;
import so.ego.re_darling.domains.diary.domain.DiaryCommentRepository;
import so.ego.re_darling.domains.diary.domain.DiaryRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class DiaryUpdateService {

  private final UserRepository userRepository;
  private final DiaryRepository diaryRepository;
  private final DiaryCommentRepository diaryCommentRepository;

  public Map<Long,String> updateDiaryComment(DiaryUpdateRequest diaryUpdateRequest) {
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

    return Map.of(diaryComment.getId(),diaryComment.getComment());
  }
}
