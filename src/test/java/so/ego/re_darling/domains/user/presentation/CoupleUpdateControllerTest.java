package so.ego.re_darling.domains.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import so.ego.re_darling.domains.user.application.dto.CoupleFirstDayRequest;
import so.ego.re_darling.domains.user.domain.Couple;
import so.ego.re_darling.domains.user.domain.CoupleRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
class CoupleUpdateControllerTest {
  private MockMvc mockMvc;

  @Autowired private CoupleRepository coupleRepository;

  @Autowired protected ObjectMapper objectMapper;

  @BeforeEach
  public void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
  }

  @Test
  void updateFirstDay() throws Exception {
     coupleRepository.save(Couple.builder().coupleToken("abc").build());
    final CoupleFirstDayRequest coupleFirstDayRequest =
        CoupleFirstDayRequest.builder().coupleToken("abc").specialDay(LocalDateTime.now()).build();
    this.mockMvc
        .perform(
            put("/couple")
                .content(this.objectMapper.writeValueAsString(coupleFirstDayRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "couple-update-firstDay",
                requestFields(
                    fieldWithPath("coupleToken").description("커플 토큰"),
                    fieldWithPath("specialDay").description("처음 만날 날짜"))));
  }
}
