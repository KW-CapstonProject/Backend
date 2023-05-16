package capstonServer.capstonServer.controller;



import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;

import ai.djl.modality.cv.ImageFactory;
import ai.djl.ndarray.NDArray;
import java.awt.image.BufferedImage;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.types.DataType;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import ai.djl.translate.TranslateException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private static final String MODEL_PATH = "./gogh2photo_G_BA.pt";
    private static final String IMAGE_PATH = "./tree.jpeg";
    private static final int TARGET_WIDTH = 64;
    private static final int TARGET_HEIGHT = 64;

    @GetMapping("")
    public void runStyleTransfer() {
        try (Model model = Model.newInstance(MODEL_PATH)) {
            // Create a Translator for resizing and processing the input image
            Translator<BufferedImage, Classifications> translator = new ImageTranslator(TARGET_WIDTH, TARGET_HEIGHT);

            // Create a Predictor for inference
            try (Predictor<BufferedImage, Classifications> predictor = model.newPredictor(translator)) {
                // Load and resize the input image
                BufferedImage image = loadImage(IMAGE_PATH);
                Classifications result = predictor.predict(image);

                // Process the output
                for (Classifications.Classification classification : result.items()) {
                    System.out.println(classification.getClassName() + ": " + classification.getProbability());
                }
            }
        } catch (IOException | TranslateException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage loadImage(String imagePath) throws IOException {
        return ImageIO.read(Paths.get(imagePath).toFile());
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
