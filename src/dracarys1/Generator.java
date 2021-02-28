/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dracarys1;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Generator {

    private final String filegAlien = "/dracarys/images/greenalien.png",
            fileBat = "/dracarys/images/alien3.png",
            file4 = "/dracarys/images/alien4.png";

    private BufferedImage[] greenFrames, blueFrames, Bat, Alien4;

    private BufferedImage alien3[] = new BufferedImage[9];
    private BufferedImage alien5[] = new BufferedImage[5];
    private BufferedImage alien6[] = new BufferedImage[5];

    private Random rand;

    public Generator() throws IOException {
        rand = new Random(1000000000);
        greenFrames = SpriteSheetLoader.createSprites(filegAlien, 1, 5);

        Bat = SpriteSheetLoader.createSprites(fileBat, 1, 6);
        Alien4 = SpriteSheetLoader.createSprites(file4, 1, 2);
        BufferedImage b = ImageIO.read(Generator.class.getResourceAsStream("/dracarys/images/Bat1.png"));
        BufferedImage b2 = ImageIO.read(Generator.class.getResourceAsStream("/dracarys/images/Bat2.png"));
        alien6[0] = b;
        alien6[1] = b2;
        for (int i = 0; i < 6; i++) {
            alien3[i] = Bat[i];
        }
        for (int i = 7; i < 9; i++) {
            alien3[6] = greenFrames[1];
            alien3[i] = greenFrames[4];
        }
        for (int i = 0; i < 2; i++) {
            alien5[i] = Alien4[i];
        }
        for (int i = 3; i < 5; i++) {
            alien5[2] = greenFrames[1];
            alien5[i] = greenFrames[4];
        }
        for (int i = 3; i < 5; i++) {
            alien6[2] = greenFrames[1];
            alien6[i] = greenFrames[4];
        }

    }

    public Alien generateNewEnemy() {
        //System.out.println(""+Game.level);
        if (Game.level == 0) {
            //return new alien4(alien5, 2, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);

            return new Alien3(alien3, 6, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);
        } else if (Game.level == 3) {
            int nxtInt = rand.nextInt(3);

            switch (nxtInt) {
                case 0:
                    return new alien4(alien5, 2, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);
                case 1:
                    return new Bat(alien6, 2, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);
                case 2:
                    return new Alien3(alien3, 6, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);

            }
            return null;

        } else if (Game.level == 5) {

            int nxtInt = rand.nextInt(3);

            switch (nxtInt) {
                case 0:
                    return new alien4(alien5, 2, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);
                case 1:
                    return new Bat(alien6, 2, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);

                case 2:
                    return new Alien3(alien3, 6, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);

            }
            return null;

        } else if (Game.level == 1) {
            return new Bat(alien6, 2, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);
        } else {
            return new alien4(alien5, 2, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);
        }
    }
}

