package Model;

public class Lives {
    private boolean isAlive;
    private int livesCount;

    public Lives(){
        isAlive = true;
        this.livesCount = 1;
    }

    public void setLivesCount(int lives){
        this.livesCount = lives;
        if (livesCount == 0){
            setAlive(false);
        }
    }

    public void setAlive(boolean alive){
        this.isAlive = alive;
    }

    public int getLivesCount(){
        return this.livesCount;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
