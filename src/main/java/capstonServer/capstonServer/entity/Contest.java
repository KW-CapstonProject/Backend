package capstonServer.capstonServer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Contest extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title; //제목

    @Column String author; //작성자

    @Column
    private String contents; //내용

    @Column(name="VIEW_COUNT")
    private int viewCount; //조회수

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL ,fetch = FetchType.LAZY,orphanRemoval = true )
    private List<Photo> photoList =new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @JsonBackReference
    @JoinColumn(name="users_id")
    private Users users;

    public void writePhoto(Photo photo){
        photoList.add(photo);
        photo.setContest(this);
    }


}

/*
* 제목
* 작성자
* 내용
* 조회수
* 댓글 수
* 좋아요 수
* 사진
* 댓글  * (추후)
* 인기 많은 사진 (좋아요 수-> top 5 뽑는 것까지)
* 인기 많은 화풍 (화풍은 어떻게 처리? -> 모델이 호출된 수로.. ? 그것도 좀 애매한데 )
*  */