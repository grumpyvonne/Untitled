package Model;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LevelData {

    public static final String[] LEVEL1 = new String[]{
            "000000000t0000000000000000000000000t00000000000000000000000t000",
            "0000000q111p0000000000000000000000q11p00000000000000000000qp000",
            "00000000000000q11p0000000000000000000t00q11p0000000000q1p000000",
            "0000000000000000000qp000000e00000q1111p0000000000000qp000000000",
            "00000000000000000000000q11111p000000000000000000q1p0000000t0000",
            "0000000000000000q11000000000000000000000000000000000000q111p000",
            "0000t00t0001110000000000000000000000000000000001110000000000000",
            "000q1111p00e000000000000000000000000000t00000000000qp0000000000",
            "000000000qp0000000000000000000000000111100000000000000q1p000000",
            "0000000000000q1p0000t00000e000000000000000q111p0000000000000000",
            "00000lmr00000000000lmr000lmr0000000000000000000000lr0000000000t",
            "mmmmmfgh33lmmm333lmfgh33lfghmmmmmmmmmmmmr333333333fhmmmmmmmmmmm"
    };

    private int levelWidth;
    private int levelHeight;
    private List<Node> platforms;
    private List<Node> treesList;
    private HashMap<Node, Integer> treesHashMap;
    private List<Node> waterPlatforms;
    private Image rightBlock;
    private Image leftBlock;
    private Image middleBlock;
    private Image groundBlock;
    private Image leftGroundBlock;
    private Image rightGroundBlock;
    private Image tree;
    private Image airBlockMiddle;
    private Image airBlockRight;
    private Image airBlockLeft;

    private void setLevelWidth(int levelWidth) {
        this.levelWidth = levelWidth;
    }

    private void setLevelHeight(int levelHeight){
        this.levelHeight = levelHeight;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight(){
        return levelHeight;
    }

    public Node createEntity(int x, int y, int width, int height, Color color) {
        Rectangle entity = new Rectangle(width, height);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        return entity;
    }

    private void loadImages() {
        this.leftBlock = new Image("images/Tile_1.png");
        this.middleBlock = new Image("images/Tile_2.png");
        this.rightBlock = new Image("images/Tile_3.png");
        this.groundBlock = new Image("images/Tile_14.png");
        this.leftGroundBlock = new Image("images/Tile_4.png");
        this.rightGroundBlock = new Image("images/Tile_6.png");
        this.tree = new Image("images/Object_16.png");
        this.airBlockLeft = new Image("images/Tile_10.png");
        this.airBlockMiddle = new Image("images/Tile_12.png");
        this.airBlockRight = new Image("images/Tile_11.png");
    }

    public Node createEntityImage(int x, int y, int width, int height, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void initializeContent() {
        loadImages();
        treesHashMap = new HashMap<Node, Integer>();
        platforms = new ArrayList<>();
        treesList = new ArrayList<Node>();
        waterPlatforms = new ArrayList<Node>();
        setLevelWidth(LevelData.LEVEL1[0].length() * 60);
        setLevelHeight(LevelData.LEVEL1.length);
        for (int lines = 0; lines < LevelData.LEVEL1.length; lines++) {
            String line = LevelData.LEVEL1[lines];
            for (int rows = 0; rows < line.length(); rows++) {
                switch (line.charAt(rows)) {
                    case '0':
                        break;
                    case 'm':
                        Node platform = createEntityImage(rows * 60, lines * 60, 60, 60, this.middleBlock);
                        platforms.add(platform);
                        break;
                    case 'g':
                        Node groundPlatform = createEntityImage(rows * 60, lines * 60, 60, 60, this.groundBlock);
                        platforms.add(groundPlatform);
                        break;
                    case 'f':
                        Node leftGroundPlatform = createEntityImage(rows * 60, lines * 60, 60, 60, this.leftGroundBlock);
                        platforms.add(leftGroundPlatform);
                        break;
                    case 'h':
                        Node rightGroundPlatform = createEntityImage(rows * 60, lines * 60, 60, 60, this.rightGroundBlock);
                        platforms.add(rightGroundPlatform);
                        break;
                    case 'l':
                        Node leftPlatform = createEntityImage(rows * 60, lines * 60, 60, 60, this.leftBlock);
                        platforms.add(leftPlatform);
                        break;
                    case 'r':
                        Node rightPlatform = createEntityImage(rows * 60, lines * 60, 60, 60, this.rightBlock);
                        platforms.add(rightPlatform);
                        break;
                    case '1':
                        Node airPlatform = createEntityImage(rows * 60, lines * 60, 60, 60, this.airBlockMiddle);
                        platforms.add(airPlatform);
                        break;
                    case 'q':
                        Node airPlatformLeft = createEntityImage(rows * 60, lines * 60, 60, 60, this.airBlockLeft);
                        platforms.add(airPlatformLeft);
                        break;
                    case 'p':
                        Node airPlatformRight = createEntityImage(rows * 60, lines * 60, 60, 60, this.airBlockRight);
                        platforms.add(airPlatformRight);
                        break;
                    case 't':
                        Node tree = createEntityImage(rows*60-60, lines*60-105, 180, 180, this.tree);
                        treesList.add(tree);
                        treesHashMap.put(tree, 0);
                        break;
                    case '3':
                        Node water = createEntity(rows * 60, lines * 60 + 20, 60, 40, Color.BLUE);
                        waterPlatforms.add(water);
                        break;
                }
            }
        }
    }

    public List<Node> getPlatforms() {
        return platforms;
    }

    public HashMap<Node, Integer> getTreesHashMap() {
        return treesHashMap;
    }

    public List<Node> getTreesList() {
        return treesList;
    }

    public List<Node> getWaterPlatforms() {
        return waterPlatforms;
    }

}
