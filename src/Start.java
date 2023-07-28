import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Start extends GameEngine {

    ArrayList<Balloon> balloons = new ArrayList<>();
    double speed;
    double isApp;
    double aDouble = 2.0;
    long passTime;
    int mouseX,mouseY;
    AudioClip popMusic = loadAudio("pop.wav");
    int score=0;


    long oldTime,currentTime;
    boolean isFail;
    public static void main(String[] args) {
        createGame(new Start());
    }

    @Override
    public void init() {
        score=100;
        isFail=false;
        setWindowSize(500,500);
        oldTime = getTime();
        passTime = oldTime;
        speed = 1.0;
        isApp = rand(aDouble)/speed;
        balloons.clear();
    }

    @Override
    public void update(double dt) {
        if(score<0) {
            isFail=true;
            return;
        }

        speed = (double) (currentTime-oldTime)/10000.0 +1.0;
        if( (double)(currentTime - passTime)/1000.0 > isApp){
            isApp = rand(aDouble)/speed;
            passTime = getTime();
            Balloon balloon = new Balloon(Start.this);
            balloons.add(balloon);
        }


        for(int i=0;i<balloons.size();i++){
            if(!balloons.get(i).booming){
                int y = balloons.get(i).getY();
                balloons.get(i).setY(y + (int) balloons.get(i).getSpeed());

                if(balloons.get(i).getY()>500+balloons.get(i).getRadius()){
                    if(balloons.get(i).getColor()==green && !balloons.get(i).isDeduction) {
                        balloons.get(i).isDeduction=true;
                        balloons.remove(i);
                        score -= 10;
                    }
                }
            }else {
                balloons.get(i).boom();
            }
        }
    }



    @Override
    public void paintComponent() {
        changeBackgroundColor(black);
        clearBackground(500,500);
        double passing1 = (double)(currentTime-oldTime)/1000.0;

        if(score<0){
            changeColor(Color.ORANGE);
            drawBoldText(150,100,"GAME OVER!");
            drawText(150,150,"Time: "+ String.format("%.2f",passing1));
            drawText(150,200,"Press Space to Restart");
        }

        changeColor(white);
        drawText(20,20,"Score: "+score);
        drawText(150,20,"Time :"+ String.format("%.2f",passing1));

        for (Balloon balloon : balloons) {
            changeColor(balloon.getColor());
            if(!balloon.booming) {
                drawSolidCircle(balloon.getX(), balloon.getY(), balloon.getRadius());
            }else{
                drawCircle(balloon.getX(), balloon.getY(), balloon.getRadius());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
        int i=0;
        for(Balloon balloon:balloons){
            double distance = distance(balloon.getX(),balloon.getY(),mouseX,mouseY);
            if(!balloon.booming) {
                if (distance < balloon.getRadius()) {
                    isLink(i);
                    if (balloon.getColor() == red) score -= 50;
                    else score += 10;
                    playAudio(popMusic);
                    balloon.setOldTime(getTime());
                    balloon.booming = true;
                }
            }
            i++;
        }
    }

    public void isLink(int i){
        for(int j=0;j<balloons.size();j++){
            if(i!=j && !balloons.get(j).booming){
                double distance = distance(balloons.get(j).getX(),balloons.get(j).getY(),balloons.get(i).getX(),balloons.get(i).getY());
                if(distance<balloons.get(i).getRadius()+balloons.get(j).getRadius()){
                    balloons.get(j).booming = true;
                    if (balloons.get(j).getColor() == red) score -= 50;
                    else score += 10;
                    playAudio(popMusic);
                    balloons.get(j).setOldTime(getTime());
                    isLink(j);
                }
            }
        }
    }



    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_SPACE){
            if(isFail){
                init();
            }
        }
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
