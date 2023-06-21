package capstonServer.capstonServer.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContestRequest {
    private String title;
    private String contents;
    private String category;
    private String style;
//    private ContestCategory.Category contestCategory;
//    private ContestCategory.Paint style;


}
