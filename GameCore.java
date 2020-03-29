package core;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import libs.Configs;
import libs.CoreFunc;
import libs.GameText;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameCore implements CoreFunc {

    // Main Game variables should be here
    Background bg = new Background ("core/assets/images/worlds/world3.png");
    int enemyNumber = 5;
    Characters heroes [] = new Characters[5];
    Characters enemies [] = new Characters[enemyNumber];
    int level = 0;
    int turn = 0;
    int realTurn = 1;
    Random aggro = new Random();
    GameText message = new GameText(Color.WHITE, Color.BLACK);
    int chosenTarget;
    Stage window;
    Scene scene1, scene2;

    @Override
    public void init(GraphicsContext gc) {
        // initialize objects (initial position, initial size, etc)
        bg.resize (Configs.appWidth, Configs.appHeight);
        bg.render (gc, 0, 0);
        heroes [0] = new Characters("Tim", 300, 10, "core/assets/images/heroes/healer.png",0);
        heroes [1] = new Characters("Wyxi", 500, 10, "core/assets/images/heroes/tank.png",0);
        heroes [2] = new Characters("Jijun", 300, 20, "core/assets/images/heroes/warrior.png",0);
        heroes [3] = new Characters("Faliq", 300, 20, "core/assets/images/heroes/mage.png",0);
        enemies [0] = new Characters("Ikmal", 100, 30, "core/assets/images/enemies/world1A.png",0);
        enemies [1] = new Characters("Arwan", 1500, 40, "core/assets/images/enemies/world1B.png",0);
        enemies [2] = new Characters("XX", 2000, 40, "core/assets/images/enemies/world1C.png",0);
        enemies [3] = new Characters("YY", 2500, 45, "core/assets/images/enemies/world2A.png",0);
        enemies [4] = new Characters("ZZ", 3000, 45, "core/assets/images/enemies/world2B.png",0);
        for (int i = 0; i < 4; i ++)
        {
            heroes[i].sprite.resize(0.5);
            heroes[i].sprite.render(gc, i*100, 300);
        }
        for (int i = 0; i < enemyNumber; i ++)
        {
            enemies[i].sprite.resize(200, 200);
        }
    }

    @Override
    public void animate(GraphicsContext gc, int time, ArrayList input) {
        // any animations and keyboard controls should be here

        bg.render(gc, 0, 0);
        enemies[level].sprite.render(gc, 600, 230);
        enemies[level].hpText.setText(gc, "HP " + enemies[level].hp, 50, 600, 130);
        enemies[level].nameText.setText(gc, enemies[level].name, 50, 600, 180);
        message.setText(gc, "Turn: " + heroes[turn].name, 50, 120, 250);
        message.setText(gc, "Hero Ability : \nTim : Healer(+100HP)\nWyxi : Tank(Absorbs all damage)\nJijun : Warrior(+100 Damage)\nFaliq : Mage(50% Damage reduction)", 25, 20, 20);
        for (int i = 0; i < 4; i++) {
            if (heroes[i].hp <= 0) {
                message.setText(gc, "You lose!\n Press ENTER to restart.", 50, 50, 50);
                if (input.contains("ENTER")) {
                    level = 0;
                    for (int j = 0; j < 4; j++) {
                        heroes[j].hp = heroes[j].maxHP;
                    }
                    enemies[level].hp = enemies[level].maxHP;
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            heroes[i].hpText.setText(gc, "HP " + heroes[i].hp, 20, i * 100 + 50, 300);
            heroes[i].nameText.setText(gc, heroes[i].name, 20, i * 100 + 50, 330);
            heroes[i].cdText.setText(gc, heroes[i].cd, 20, i * 100 + 50, 330);
            heroes[i].sprite.render(gc, i * 100 + 50, 350);
        }
        if (input.contains("UP")) {
            enemies[level].hp -= heroes[turn].strength;
            System.out.println(enemies[level].hp);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            turn++;
        }
        if (input.contains("DOWN")) {
            if (heroes[turn].cooldown != 0) {
                enemies[level].hp -= heroes[turn].strength;
            } else {
                heroes[turn].cooldown = 3;
                switch (turn) {
                    case 0:
                        for (int i = 0; i < 4; i++) {
                            heroes[i].hp += 100;
                            if (heroes[i].hp > heroes[i].maxHP)
                                heroes[i].hp = heroes[i].maxHP;
                        }
                        break;
                    case 1:
                        chosenTarget = 1;
                        break;
                    case 2:
                        enemies[level].hp -= 100;
                        break;
                    case 3:
                        enemies[level].strength *= 0.5;
                        break;
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            turn++;
        }
        if (turn == 4) {
            if (chosenTarget != 1) {
                chosenTarget = aggro.nextInt(4);
            }
            heroes[chosenTarget].hp -= enemies[level].strength;
            turn = 0;
            realTurn += 1;
            for (int i = 0; i < 4; i++) {
                if (heroes[i].cooldown != 0)
                    heroes[i].cooldown--;
            }
        }
        if (enemies[level].hp <= 0) {


                Stage primaryStage = new Stage();
                window = primaryStage;

                //Button 1
                Label label1 = new Label("You win!\n Advance to the next level!\n Press UP to continue.");
                Button button1 = new Button("PRESS UP[^]");
                //button1.setOnAction(e -> input.contains("UP") );

                //Layout 1
                VBox layout1 = new VBox(20);
                layout1.getChildren().addAll(label1, button1);
                scene1 = new Scene(layout1, 600, 300);


                //To tell the computer to start which scene first
                window.setScene(scene1);
                window.setTitle("CONGRATULATION!");
                window.show();



                if (input.contains("UP")) {

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    window.close();
                    level++;
                    realTurn = 0;
                }

        }
    }

    @Override
    public void mouseClick(MouseEvent e) {
        // mouse click event here
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        // mouse move event here
    }
}
