package com.example.adam.project;


/**
 * Created by Adam on 12/20/2014.
 */
public class controller {
    protected boolean touchedleft = false;// true if leftarrow is touched
    protected boolean touchedright = false;// true if rightarrow is touched
    protected boolean touchedfire = false;// true if firebutton is touched
    protected int posleftx;
    protected int poslefty;
    protected int posrightx;
    protected int posrighty;
    protected int posfirex;
    protected int posfirey;
    protected int maxwidthforship;
    protected spaceShip spaceShip;
    protected int delaytimeforbullet = 0;


    public void PositionSetter(int posleftX, int posleftY, int posrightX, int posrightY, int posfireX, int posfireY) {
        this.posleftx = posleftX;
        this.poslefty = posleftY;
        this.posrightx = posrightX;
        this.posrighty = posrightY;
        this.posfirex = posfireX;
        this.posfirey = posfireY;
    }



    public void updatebulletdelay(){
        if (delaytimeforbullet > 0){
            delaytimeforbullet = delaytimeforbullet - 20;
        }
    }

    public void spaceshipobjectsetter(spaceShip spaceShip) {

        this.spaceShip = spaceShip;
    }

    public void handleMovement(spaceShip spaceShip) {

        if (touchedleft == true && touchedright == false) {
            int tempx = spaceShip.getX();
            tempx -= 5;
            if (tempx >= 0)
                spaceShip.setX(tempx);
        }
        else if (touchedright == true && touchedleft == false) {
            int tempx;
            tempx = spaceShip.getX();
            tempx += 5;
            if (tempx <= maxwidthforship)
                spaceShip.setX(tempx);
        }

    }


}
