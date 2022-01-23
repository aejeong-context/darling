package so.ego.re_darling.domains;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

  @CreatedDate private LocalDateTime createdDate;

  @LastModifiedDate private LocalDateTime modifiedDate;
}
