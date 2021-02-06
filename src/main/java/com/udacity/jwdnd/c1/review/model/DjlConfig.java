package com.udacity.jwdnd.c1.review.model;

import ai.djl.modality.Classifications;
import ai.djl.modality.cv.util.BufferedImageUtils;
import ai.djl.modality.cv.util.NDImageUtils;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DjlConfig {
    public static final class XrayTranslator implements Translator<BufferedImage, Classifications>
    {
private static final List<String> CLASSES= Arrays.asList("covid-19","normal");

        @Override
        public NDList processInput(TranslatorContext translatorContext, BufferedImage bufferedImage) throws Exception {
            NDArray array= BufferedImageUtils.toNDArray(translatorContext.getNDManager(),bufferedImage, NDImageUtils.Flag.COLOR);
            array=NDImageUtils.resize(array,224).div(255.0f);
            return new NDList(array);
        }
        @Override
        public Classifications processOutput(TranslatorContext translatorContext, NDList ndList) throws Exception {
            NDArray probabilties=ndList.singletonOrThrow();
            return new Classifications(CLASSES,probabilties);
        }


    }

    @Bean
public ZooModel xrayModel() throws Exception
    {
        Criteria<BufferedImage,Classifications> criteria=Criteria.builder()
                                                         .setTypes(BufferedImage.class,Classifications.class)
.optTranslator(new XrayTranslator())
                .build();

        return ModelZoo.loadModel(criteria);
    }
}