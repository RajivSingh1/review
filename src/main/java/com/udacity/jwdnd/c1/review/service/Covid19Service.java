package com.udacity.jwdnd.c1.review.service;

import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.util.BufferedImageUtils;
import ai.djl.repository.zoo.ZooModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class Covid19Service {
@Autowired
private ZooModel xrayModel;

public String diagonase(String imageurl)
{
try (Predictor<BufferedImage, Classifications> predictor= xrayModel.newPredictor()){
Classifications result= predictor.predict(BufferedImageUtils.fromUrl(imageurl));
return"Diagnose: "
        +result.best().getClassName()
        +" , probability: "
        + result.best().getProbability();
} catch (Exception e) {
    throw new RuntimeException("Failed to diagnose",e);
}
}
}