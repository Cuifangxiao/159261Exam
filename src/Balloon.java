import java.awt.*;
import java.util.Random;

public class Balloon {
    private int x;
    private int y;
    private int radius;

    private Color color;
    private double speed;
    Random r = new Random();
    Start start;
    long oldTime,currentTime;
    boolean booming;
    boolean isDeduction=false;

    public Balloon(Start start){
        radius = r.nextInt(20)+21;
        int index = r.nextInt(2);
        if(index == 0) color = Color.green;
        else color = Color.RED;

        this.start = start;
        x = radius + r.nextInt(500-2*radius);
        y = radius;
        speed = (r.nextInt(101) + 50*start.getSpeed())/30;
    }


    public void boom(){
        currentTime = start.getTime();
        radius++;
        if(currentTime-oldTime >= 200){
            start.balloons.remove(this);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getOldTime() {
        return oldTime;
    }

    public void setOldTime(long oldTime) {
        this.oldTime = oldTime;
    }

    public boolean isBoom() {
        return booming;
    }

    public void setBoom(boolean boom) {
        booming = boom;
    }
}
