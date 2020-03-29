package core;

import javafx.scene.paint.Color;
import libs.GameText;
import libs.Sprite;
import javafx.scene.image.Image;

public class Characters
{
    public String name;
    public int hp;
    public int strength;
    public String spriteDir;
    public int cd;
    ChSprite sprite;
    GameText nameText;
    GameText hpText;
    GameText cdText = new GameText(Color.WHITE, Color.BLACK);
    int maxHP;
    int cooldown = 0;
    public int maxStrength;

    public Characters (String name, int hp, int strength, String spriteDir,int cd)
    {
        this.name = name;
        this.hp = hp;
        this.maxHP = hp;
        this.strength = strength;
        this.maxStrength = strength;
        this.cd = cd;
        sprite = new ChSprite(spriteDir);
        nameText = new GameText(Color.WHITE, Color.BLACK);
        hpText = new GameText(Color.WHITE, Color.BLACK);
        cdText = new GameText(Color.WHITE, Color.BLACK);
    }
}
