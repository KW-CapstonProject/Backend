package capstonServer.capstonServer.dto.request;

import lombok.Data;

@Data
public class SearchRequest {
    private String query;
    public String getQuery() {
        return query;
    }
}