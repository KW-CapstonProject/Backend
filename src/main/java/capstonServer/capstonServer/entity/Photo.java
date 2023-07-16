package capstonServer.capstonServer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue
    @Column(name = "photo_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String fileName;

    @Column
    private String fileUrl;

    @Column
    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contest contest;

    public Photo(String fileUrl,Contest contest){
        this.fileUrl=fileUrl;
        this.contest=contest;
    }

}
