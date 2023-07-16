package capstonServer.capstonServer.dto.response;


import capstonServer.capstonServer.entity.Contest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ContestResponse {
    private String author;
    private String title;
    private String contents;
    private int viewCount;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updateDate;

    public ContestResponse(Contest contest){
        this.author=contest.getAuthor();
        this.title=contest.getTitle();
        this.contents=contest.getContents();
        this.viewCount=contest.getViewCount();
        this.createdDate=contest.getCreateDate();

    }
}
