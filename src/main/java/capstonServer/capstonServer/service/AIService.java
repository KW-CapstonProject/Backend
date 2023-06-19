package capstonServer.capstonServer.service;


import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    public class AIService {
        private final OpenAiService openAiService;
        private final StorageService s3Service;
        public String generatePicture(String prompt) {
            CreateImageRequest createImageRequest = CreateImageRequest.builder()
                    .prompt(prompt)
                    .size("512x512") //size 조정
                    .n(4) // 개수 조정
                    .build();

            String url = openAiService.createImage(createImageRequest).getData().get(0).getUrl();
            System.out.println(url);
            s3Service.upload(url); //AWS S3 bucket 저장
            return url;
        }
    }

