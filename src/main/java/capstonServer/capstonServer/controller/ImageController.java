package capstonServer.capstonServer.controller;


import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.Classifications.Classification;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.types.DataType;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/image")
@RestController

public class ImageController {

    @GetMapping("")
    public void run () {
        String modelPath = "models/gogh2photo_G_BA.pt";
        String imagePath = "images/tree.jpeg";
        int targetWidth = 64;
        int targetHeight = 64;

        // Load the PyTorch model
        try (Model model = Model.newInstance("my_model")) {

            model.load(Paths.get(modelPath));
            // Create a Translator for resizing the input image
            Translator<BufferedImage, Classifications> translator = new ImageTranslator(targetWidth, targetHeight);

            // Create a Predictor for inference
            try (Predictor<BufferedImage, Classifications> predictor = model.newPredictor(translator)) {

                // Load and resize the input image
                BufferedImage image = loadImage(imagePath);
                System.out.println(image);
                BufferedImage resizedImage = resizeImage(image, targetWidth, targetHeight);
                System.out.println(resizedImage);
                // Perform inference
                Classifications result = predictor.predict(resizedImage);
                System.out.println(result);


                // Process the output
                for (Classification classification : result.items()) {
                    System.out.println(classification.getClassName() + ": " + classification.getProbability());
                }
            }
        } catch (IOException  | TranslateException e) {
            e.printStackTrace();
        } catch (MalformedModelException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage loadImage(String imagePath) throws IOException {
        return ImageIO.read(Paths.get(imagePath).toFile());
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resizedImage;
    }

    private static class ImageTranslator implements Translator<BufferedImage, Classifications> {
        private int targetWidth;
        private int targetHeight;

        public ImageTranslator(int targetWidth, int targetHeight) {
            this.targetWidth = targetWidth;
            this.targetHeight = targetHeight;
        }

        @Override
        public NDList processInput(TranslatorContext ctx, BufferedImage image) {
            // Convert the image to an NDArray
            NDArray array = ImageFactory.getInstance()
                    .fromImage(image)
                    .toNDArray(ctx.getNDManager())
                    .toType(DataType.FLOAT32, true);

            return new NDList(array);
        }
        @Override
        public Classifications processOutput(TranslatorContext ctx, NDList list) {
            // Process the output
            NDArray probabilities = list.singletonOrThrow();
            return new Classifications(null, probabilities);
        }
    }
}
