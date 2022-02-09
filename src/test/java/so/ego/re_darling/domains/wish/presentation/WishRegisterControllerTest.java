package so.ego.re_darling.domains.wish.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;
import so.ego.re_darling.domains.user.domain.User;
import so.ego.re_darling.domains.user.domain.UserRepository;
import so.ego.re_darling.domains.wish.application.WishRegisterService;
import so.ego.re_darling.domains.wish.application.dto.WishResisterRequest;

import java.net.URI;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class WishRegisterControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private CoupleRepository coupleRepository;
  @Autowired private UserRepository userRepository;

  @Autowired protected ObjectMapper objectMapper;

  @Test
  void registerWish() throws Exception {

    Couple couple =
        coupleRepository.save(
            Couple.builder().coupleToken("AAA").firstDay(LocalDateTime.now()).build());

    userRepository.save(User.builder().socialToken("abc").couple(couple).build());

    final WishResisterRequest wishResisterRequest =
        WishResisterRequest.builder().socialToken("abc").content("바다가기").build();

    this.mockMvc
        .perform(
            post("/wishlist")
                .content(this.objectMapper.writeValueAsString(wishResisterRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andDo(
            document(
                "wish-register",
                requestFields(
                    fieldWithPath("socialToken").description("소셜 토큰"),
                    fieldWithPath("content").description("위시 내용"))));
  }
}
