package capstonServer.capstonServer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contest extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contest_id")
    private Long id;

    @Column
    private String title; //제목

    @Column
    private String author; //작성자

    @Column
    private String contents; //내용

    @Column
    private String category; // 카테고리

    @Column
    private String style; // 화풍

    @ColumnDefault("0")
    @Column(name="like_count")
    private Integer likeCount ;

//    @Column(name = "contestCategory",nullable = false) //카테고리
//    @Enumerated(value = EnumType.STRING)
//    private ContestCategory.Category contestCategory;

//    @Column(name = "paintingStyle",nullable = false) // 화풍
//    @Enumerated(value = EnumType.STRING)
//    private ContestCategory.Paint style;

    @ColumnDefault("0")
    @Column(name="VIEW_COUNT")
    private int viewCount; //조회수


    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL ,fetch = FetchType.LAZY,orphanRemoval = true )
    private List<Photo> photoList =new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @JsonBackReference
    @JoinColumn(name="users_id")
    private Users users;

    @Builder
    public Contest(String title, String author, String contents, String category, String style,Users users) {
        this.title = title;
        this.author=author;
        this.contents=contents;
        this.category=category;
        this.style=style;
        this.users = users;

    }

    public void writePhoto(Photo photo){
        photoList.add(photo);
        photo.setContest(this);
    }
}

/*
* 제목
* 작성자
* 내용
* 카테고리
* 화풍
* 조회수
* 댓글 수
* 좋아요 수
* 사진
* 댓글  * (추후)
* 인기 많은 사진 (좋아요 수-> top 5 뽑는 것까지)
*  */