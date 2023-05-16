package capstonServer.capstonServer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PhotoContest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    private Integer view;

    @ManyToOne
    @JsonManagedReference
    @JsonIgnore
    @JsonBackReference
    @JoinColumn(name="user_sn")
    private Users users;


}

/*
* 제목
* 내용
* 조회수
* 댓글 수
* 좋아요 수
* 사진
* 댓글  * (추후)
* 인기 많은 사진 (좋아요 수-> top 5 뽑는 것까지)
* 인기 많은 화풍 (화풍은 어떻게 처리? )
*  */