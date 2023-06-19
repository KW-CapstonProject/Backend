package capstonServer.capstonServer.controller;

import capstonServer.capstonServer.service.AIService;
import capstonServer.capstonServer.service.PapagoService;
import capstonServer.capstonServer.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class Text2ImageController {
    private final AIService aiService;
    private final StorageService s3Service;

    private final PapagoService papagoService;
    @GetMapping("")
    public String test(@RequestBody String word){
        String testWord = papagoService.test(word);
        System.out.println(testWord);
        return testWord;
    }
    @PostMapping("/generate")
    public ResponseEntity<?> generateImage(@RequestBody String prompt) {
        String word= papagoService.test(prompt);

        return new ResponseEntity<>(aiService.generatePicture(word), HttpStatus.OK);
    }

    @GetMapping("/get-history")
    public ResponseEntity<?> getHistory() {

            return new ResponseEntity<>(s3Service.listObjects(), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteImage(@RequestParam("name") String name) {

        s3Service.deleteObject(name);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}

