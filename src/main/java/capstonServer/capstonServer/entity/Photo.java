package capstonServer.capstonServer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Contest contest;

}
