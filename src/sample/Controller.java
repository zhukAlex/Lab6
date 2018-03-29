package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;


public class Controller {
    @FXML
    Rectangle imageView1;

    @FXML
    Rectangle imageView2;

    @FXML
    Rectangle imageView3;

    private Main main;
    private Parent p;
    private Stage stage;
    BufferedImage bufferedImage1;
    BufferedImage bufferedImage2;
    BufferedImage bufferedImage3;
    BufferedImage [] images;

    float A1 [][] = new float [150][150];
    float A2 [][] = new float [150][150];
    float A3 [][] = new float [150][150];
    float A [][][] = new float [3][150][150];


    public void setMainApp(Main main, Parent p, Stage stage) throws IOException {
        this.main = main;
        this.stage = stage;
        this.p = p;
        A[0] = A1;
        A[1] = A2;
        A[2] = A3;
        bufferedImage1 = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        bufferedImage2 = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        bufferedImage3 = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        images = new BufferedImage[]{bufferedImage1, bufferedImage2, bufferedImage3};
        fillImages();
       // fillImages(bufferedImage2);
       // fillImages(bufferedImage3);
        imageView1 = (Rectangle) p.lookup("#img1");
        imageView2 = (Rectangle) p.lookup("#img2");
        imageView3 = (Rectangle) p.lookup("#img3");
        imageView1.setFill(new ImagePattern(SwingFXUtils.toFXImage(bufferedImage1, null), 0f, 0f, 150f, 150f, false));
        imageView2.setFill(new ImagePattern(SwingFXUtils.toFXImage(bufferedImage2, null), 0f, 0f, 150f, 150f, false));
        imageView3.setFill(new ImagePattern(SwingFXUtils.toFXImage(bufferedImage3, null), 0f, 0f, 150f, 150f, false));
    }

    private void fillImages() {
        Random random = new Random();

        for (int i = 0; i < bufferedImage1.getHeight(); i++) {
            for (int j = 0; j < bufferedImage1.getWidth(); j++) {
                /*bufferedImage1.setRGB(i, j, new Color(255, 255, 255).getRGB());
                bufferedImage3.setRGB(i, j, new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
                bufferedImage2.setRGB(i, j, new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());*/
                A1[i][j] = 1;
                A2[i][j] = random.nextFloat();
                A3[i][j] = random.nextFloat();
                fillImage(A1[i][j], 0, i, j);
                fillImage(A2[i][j], 1, i, j);
                fillImage(A3[i][j], 2, i, j);
            }
        }

        for (int i = 0; i < bufferedImage1.getHeight(); i++) {
            for (int j = 0; j < bufferedImage1.getWidth(); j++) {
                if (i < 10 ||  i >= bufferedImage1.getHeight() - 10) {
                  /*  bufferedImage1.setRGB(i, j,new Color(0, 0, 0).getRGB());
                    bufferedImage1.setRGB(j, i, new Color(0, 0, 0).getRGB());
                */
                    A1[i][j] = 0.5f;
                    A1[j][i] = 0.5f;
                    fillImage(A1[i][j], 0, i, j);
                    fillImage(A1[j][i], 0, j, i);
                }
            }
        }
    }

    public void fillImage(float a, int l, int i, int j) {
        if (a > 0.5f)
            images[l].setRGB(i, j, new Color(255, 255, 255).getRGB());
        else
            images[l].setRGB(i, j, new Color(0, 0, 0).getRGB());
    }

    public void onClickStart() {
        float a;

        for (int k = 0; k < 100; k++) {
            for (int l = 0; l < 3; l++) {
                for (int i = 0; i < images[l].getHeight(); i++) {
                    for (int j = 0; j < images[l].getWidth(); j++) {
                        a = solveDelta(l, i, j);
                        A[l][i][j] = 0.0002f * a + A[l][i][j];
                        fillImage(A[l][i][j], l, i, j);
                    }
                }
            }
        }
        imageView1.setFill(new ImagePattern(SwingFXUtils.toFXImage(bufferedImage1, null), 0f, 0f, 150f, 150f, false));
        imageView2.setFill(new ImagePattern(SwingFXUtils.toFXImage(bufferedImage2, null), 0f, 0f, 150f, 150f, false));
        imageView3.setFill(new ImagePattern(SwingFXUtils.toFXImage(bufferedImage3, null), 0f, 0f, 150f, 150f, false));

    }

    public float solveDelta(int l, int i, int j) {
        float a = 0.01f;
        int aSecond = 18;
        float h = (7 * aSecond - 20) / 20;
        float b = -A[l][i][j] * func(A[l][i][j]*A[l][i][j], aSecond) +
                a * funcDelta(l, i, j) + h *
                (A[l == 2 ? 0 : l+1][i][j] - 2 * A[l][i][j] +
                        A[l == 0 ? 2 : l - 1][i][j]);
        return b;
    }

    public float func(float A, float a) {
        return 2 * a * A * A * A * A - a * A * A + 1;
    }

    public float funcDelta(int l, int i, int j) {
        int height = bufferedImage1.getHeight();
        int width = bufferedImage1.getWidth();
        float a = A[l][i == height - 1 ? 0 : i + 1][j] +
                A[l][i][j == width - 1 ? 0 : j + 1] +
                A[l][i > 0 ? i - 1 : height - 1][j] +
                A[l][i][j > 0 ? j - 1 : width - 1] -
                4 * A[l][i][j];
        return a;
    }
}
