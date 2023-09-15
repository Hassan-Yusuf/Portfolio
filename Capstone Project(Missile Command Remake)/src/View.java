import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

//to do
//handle collisions between game objects
//if enemy missile hits city, city is destroyed
//score/level system
public class View extends JPanel implements MouseListener, MouseMotionListener {

    public static int score;
    public static int level;
    public Timer timer, timer1, timer2, timer3, timer4, timer5, timer6;
    public JPanel topPanel;
    public JPanel middlePanel;
    public JPanel bottomPanel;
    private boolean gameOver;
    public drawString first, second;
    public int ammo;

    public static Bullet[] ammoLeft;
    public static Bullet[] ammoMiddle;
    public static Bullet[] ammoRight;
    public static City[] cities;
    public static Silo[] silos;
    public static ArrayList<enemyAgent> enemyAgents;
    ImageIcon arrowIcon = new ImageIcon("res/arrowRed.png");
    ImageIcon arrowIcon2 = new ImageIcon("res/arrowBlack.png");
    Image arrow = arrowIcon.getImage();

    public ArrayList<Aircraft> aircrafts;
    public ArrayList<Satellite> satellites;
    public Color playerColor = Color.orange;

    public int[][] listOfLaunchers;
    public int mouseX, mouseY;
    public double bulletVelocity;
    public static double extraVelocity;
    public static ArrayList<Explosion> explosions;
    private boolean spawning;
    private boolean doneAnimation;
    private int totalScore;
    private int scoreX;
    private int scoreY;
    private JLabel nextLevel, totalScoreLabel, levelScoreLabel, upgradeAmmo, upgradeSpeed;
    private boolean notPlaying;
    private int addXArrow;
    private boolean gameOverOpened;
    public static int color;


    //approach logic for bullets:
    //
    //total list of bullets
    //integer list of how many bullets are available
    //integer list decreases when it decides which bullet is fired from
    //if integer list <=0 cant fire


    public void emptyGrid(JPanel object) {
        if (object != null) {
            this.remove(object); //empties Panel
        }
    }

    public double distance(int x1, int y1, int x2, int y2) {
        double X = Math.pow(x2 - x1, 2);
        double Y = Math.pow(y2 - y1, 2);
        return Math.sqrt(X + Y);
    }

    public int[] closestPoint(int[] origin, int[][] points) {
        int[] closestPoint = points[0];
        double closestDistance = distance(origin[0], origin[1], closestPoint[0], closestPoint[1]);
        for (int[] point : points) {
            double distance = distance(origin[0], origin[1], point[0], point[1]);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = point;
            }
        }
        return closestPoint;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getY() < constants.DIM_HEIGHT - 110 && !doneAnimation && !notPlaying && !gameOver) {
            shootMissile(e.getX(), e.getY());
        }
        if (doneAnimation && e.getX() >= constants.DIM_WIDTH / 2 - 100 && e.getX() <= constants.DIM_WIDTH / 2 + 100
                && e.getY() >= constants.DIM_HEIGHT / 2 - 50 && e.getY() <= constants.DIM_HEIGHT / 2 + 10 && notPlaying) {
            doneAnimation = false;
            setScoreLocation(constants.DIM_WIDTH - 210, 40);
            initSilos();
            initAmmo();
            respawnCities();
            spawnEnemies();
            listOfLaunchers = new int[][]{{constants.DIM_WIDTH / 2 - 30 + 30, constants.DIM_HEIGHT - 110}, {10 + 30, constants.DIM_HEIGHT - 110}, {constants.DIM_WIDTH - 84 + 30, constants.DIM_HEIGHT - 110}};
            remove(nextLevel);
            soundManager.stop(soundManager.waitingMusic);
            stopSpawning();
            addXArrow = 0;
            notPlaying = false;
            remove(totalScoreLabel);
            remove(levelScoreLabel);
            if (upgradeSpeed.isVisible()) remove(upgradeSpeed);
            if (upgradeAmmo.isVisible()) remove(upgradeAmmo);
        }
        if (doneAnimation && e.getX() >= constants.DIM_WIDTH / 2 - 120 && e.getX() <= constants.DIM_WIDTH / 2 + 120
                && e.getY() >= constants.DIM_HEIGHT / 2 - 150 && e.getY() <= constants.DIM_HEIGHT / 2 - 90 && (level == 5 || level == 10 || level == 15 && notPlaying)) {
            extraVelocity += 0.5;
            remove(upgradeSpeed);
        }
        if (doneAnimation && e.getX() >= constants.DIM_WIDTH / 2 - 120 && e.getX() <= constants.DIM_WIDTH / 2 + 120
                && e.getY() >= constants.DIM_HEIGHT / 2 - 150 && e.getY() <= constants.DIM_HEIGHT / 2 - 90 && (level == 4 || level == 8 || level == 12 || level == 2) && notPlaying) {
            ammo++;
            initAmmo();
            remove(upgradeAmmo);
        }
    }
    //constants.DIM_WIDTH/2-100,constants.DIM_HEIGHT/2-50,200,60

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    private class KAdapter extends KeyAdapter { //key binding to up arrow to shoot laser

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_P) {
                if (timer.isRunning()) {
                    timer.stop();
                    timer1.stop();
                } else {
                    timer.start();
                    timer1.start();
                }
            }
        }

    }

    public class drawString {
        int movingTextX;

        public drawString(int initX) {
            movingTextX = initX;
        }

        public void draw(Graphics g) {
            g.setColor(Color.blue);
            g.setFont(new Font("Calibri", Font.BOLD, 25));
            g.drawString("Missile command!      Don't insert coins!        Just play      Survive!       Produced in 2023", movingTextX, constants.DIM_HEIGHT - 45);
        }

        public void addX() {
            if (movingTextX >= -1100) {
                movingTextX -= 1;
            }
            if (movingTextX < -1100) {
                movingTextX = constants.DIM_WIDTH;
            }
        }

    }

    //        g.fillArc(constants.DIM_WIDTH/2-30, constants.DIM_HEIGHT-110, 60, 60, 0, 180);
    //        g.fillArc(10, constants.DIM_HEIGHT-110, 60, 60, 0, 180);
    //        g.fillArc(constants.DIM_WIDTH-84, constants.DIM_HEIGHT-110, 60, 60, 0, 180);
    public int getExplosion() {
        for (int i = 0; i < explosions.size(); i++) {
            Explosion explosion = explosions.get(i);
            if (explosion.isKilled()) {
                return i;
            }
        }
        return -1; //will probably give error
    }

    public boolean checkAndFire(Bullet[] ammo, double vectorX, double vectorY, int x1, int y1, int x2, int y2, int index) {
        for (int i = ammo.length - 1; i > -1; i--) {
            Bullet bullet = ammo[i];
            if (!bullet.fired && !bullet.usedUp && !silos[index].isKilled()) {
                bullet.setEnd(x1, y1);
                bullet.fireBullet(vectorX, vectorY, x2, y2); //have to use recursion
                return false;
            }
        }
        return true;
    }

    public int ammoDictionary(int[] closestLauncher) {
        return 0; //this needs to do first part.
    }

    public void shootMissile(int x1, int y1) {
        int x2, m;
        double angle;
        // +30 to each x coordinate because we want the middle of the arcs rather than the origin centered bottom left
        int[] mouseClick = {x1, y1};
        int[] closestLauncher = closestPoint(mouseClick, listOfLaunchers);
        x2 = closestLauncher[0];
        int y2 = constants.DIM_HEIGHT - 110;
        angle = Math.atan2(x1 - x2, y1 - y2);
        bulletVelocity = 5.0;
        double vectorX = bulletVelocity * Math.sin(angle);
        double vectorY = bulletVelocity * Math.cos(angle);
        //approach logic for bullets:
        //
        //total list of bullets
        //integer list of how many bullets are available
        //integer list decreases when it decides which bullet is fired from
        //if integer list <=0 cant fire
        //different approach
        //check all to see if there's ammo
        //if there is then check between available ammo
        //
        if (x2 == constants.DIM_WIDTH / 2 - 30 + 30) {
            int index = 0;
            if (checkAndFire(ammoMiddle, vectorX, vectorY, x1, y1, x2, y2, index)) {
                listOfLaunchers[index] = new int[]{99999, 99999};
                shootMissile(x1, y1);
            }
        }//new int[][]{{constants.DIM_WIDTH/2-30+30,constants.DIM_HEIGHT-110},{10+30,constants.DIM_HEIGHT-110},{constants.DIM_WIDTH-84+30,constants.DIM_HEIGHT-110}}
        else if (x2 == 10 + 30) {
            int index = 1;
            if (checkAndFire(ammoLeft, vectorX, vectorY, x1, y1, x2, y2, index)) {
                listOfLaunchers[index] = new int[]{99999, 99999};
                shootMissile(x1, y1);
            }
        } else if (x2 == constants.DIM_WIDTH - 84 + 30) {
            int index = 2;
            if (checkAndFire(ammoRight, vectorX, vectorY, x1, y1, x2, y2, index)) {
                listOfLaunchers[index] = new int[]{99999, 99999};
                shootMissile(x1, y1);
            }
        }
    }

    public void setup() {
        addKeyListener(new KAdapter());
        setFocusable(true);
        timer = new Timer(constants.DELAY, new timerGameLoop());
        timer.start();
        initialise();
    }

    public void initCities() {
        for (int x = 0; x < 6; x++) {
            int gap = x / 3;
            cities[x] = new City(80 + 100 * x + 55 * gap, constants.DIM_HEIGHT - 100);
        }
    }

    public void respawnCities() {
        for (int x = 0; x < 6; x++) {
            int gap = x / 3;
            if (!cities[x].isDead) {
                cities[x] = new City(80 + 100 * x + 55 * gap, constants.DIM_HEIGHT - 100);
            }
        }
    }

    public void initSilos() {
        silos[0] = new Silo(10, constants.DIM_HEIGHT - 110);
        silos[1] = new Silo(constants.DIM_WIDTH / 2 - 30, constants.DIM_HEIGHT - 110);
        silos[2] = new Silo(constants.DIM_WIDTH - 84, constants.DIM_HEIGHT - 110);
    }

    public void stopSpawning() {
        timer4 = new Timer(25000, e -> {
            timer1.stop();
            if (level > 5) timer2.stop();
            if (level > 10) timer3.stop();
            spawning = false;
        });
        timer4.setRepeats(false);
        timer4.start();
    }

    public void spawnEnemies() {
        initSilos();
        spawnEnemyAgents();
        spawnAircrafts();
        spawnSatellites();
        destroyEnemies();
    }

    public void spawnEnemyAgents() {
        int delay;
        int tempLevel = level - 2;
        if (tempLevel < 0) tempLevel = 0;
        delay = 3000 - tempLevel * 100;
        if (delay < 2000) delay = 2000;
        timer1 = new Timer(delay, e -> {
            enemyAgents.add(new enemyAgent());
        });
        timer1.start();
    }

    public void spawnAircrafts() {
        int delay = 15000;
        if (level >= 10) delay = 10000;
        if (level >= 15) delay = 5000;
        if (level >= 5) {
            timer2 = new Timer(delay, e -> {
                aircrafts.add(new Aircraft());
            });
            timer2.start();
        }
    }

    public void destroyEnemies() {
        JLabel testLabel = new JLabel("Destroy the incoming missiles!");
        testLabel.setForeground(Color.blue);
        testLabel.setFont(new Font("Monospaced", Font.BOLD, 35));
        testLabel.setBounds(80, constants.DIM_HEIGHT - 200, constants.DIM_WIDTH - 40, 40);
        add(testLabel);
        Timer timer6 = new Timer(700, e -> {
            if (testLabel.getForeground() == Color.blue) {
                testLabel.setForeground(Color.black);
            } else testLabel.setForeground(Color.blue);
        });
        timer6.start();
        Timer timer5 = new Timer(5000, e -> {
            remove(testLabel);
            timer6.stop();
        });
        timer5.setRepeats(false);
        timer5.start();
    }

    public void spawnSatellites() {
        int delay = 15000;
        if (level >= 15) delay = 10000;
        if (level >= 20) delay = 5000;
        if (level >= 10) {
            timer3 = new Timer(delay, e -> {
                satellites.add(new Satellite());
            });
            timer3.start();
        }
    }

    public void levelScreen() {
        level++;
        notPlaying = true;
        soundManager.play(soundManager.waitingMusic, true);
        AtomicInteger count = new AtomicInteger();
        setScoreLocation(constants.DIM_WIDTH / 2 - 75, constants.DIM_HEIGHT / 2 + 100);
        addXArrow = 90;
        totalScoreLabel = new JLabel("Total");
        levelScoreLabel = new JLabel("Level");
        totalScoreLabel.setForeground(Color.white);
        levelScoreLabel.setForeground(Color.white);
        totalScoreLabel.setFont(new Font("Monospaced", Font.BOLD, 35));
        levelScoreLabel.setFont(new Font("Monospaced", Font.BOLD, 35));
        totalScoreLabel.setBounds(scoreX - 50 + 120, scoreY - 75, 150, 40);
        levelScoreLabel.setBounds(scoreX - 50, scoreY - 75, 150, 40);
        add(totalScoreLabel);
        add(levelScoreLabel);
        AtomicBoolean ammoKilled = new AtomicBoolean(false);
        AtomicBoolean silosKilled = new AtomicBoolean(false);
        AtomicBoolean citiesKilled = new AtomicBoolean(false);
        final boolean[] done = {false};
        timer6 = new Timer(600, e -> {
            if (!done[0]) {
                soundManager.playaddScore();
                totalScore += score;
                score = 0;
            } else {
                int num = 0;
                int num1 = 0;
                int num2 = 0;
                for (int x = ammo - 1; x > -1; x--) {
                    if (!ammoLeft[x].fired) {
                        ammoLeft[x].exploded = true;
                        ammoLeft[x].fired = true;
                        break;
                    } else num++;
                }
                if (num == ammo) {
                    for (int x = ammo - 1; x > -1; x--) {
                        if (!ammoMiddle[x].fired) {
                            ammoMiddle[x].exploded = true;
                            ammoMiddle[x].fired = true;
                            break;
                        } else num1++;
                    }
                }
                if (num == ammo && num1 == ammo) {
                    for (int x = ammo - 1; x > -1; x--) {
                        if (!ammoRight[x].fired) {
                            ammoRight[x].exploded = true;
                            ammoRight[x].fired = true;
                            break;
                        } else num2++;
                    }
                }
                if (num == ammo && num1 == ammo && num2 == ammo) {
                    ammoKilled.set(true);
                } else {
                    soundManager.playaddScore();
                    totalScore += 20;
                }
                if (ammoKilled.get()) {
                    num = 0;
                    for (Silo silo : silos) {
                        if (!silo.isKilled()) {
                            silo.kill(true);
                            totalScore += 100;
                            soundManager.playaddScore();
                            break;
                        } else {
                            num++;
                        }
                        if (num == 3) silosKilled.set(true);
                    }
                    if (silosKilled.get() && !citiesKilled.get()) {
                        num = 0;
                        for (City city : cities) {
                            if (!city.isKilled()) {
                                city.kill(true);
                                totalScore += 500;
                                soundManager.playaddScore();
                                break;
                            } else {
                                num++;
                            }
                            if (num == ammo) {
                                citiesKilled.set(true);
                            }
                        }
                    } else if (citiesKilled.get() && silosKilled.get()) {
                        count.getAndIncrement();
                    }
                    if (count.get() == 3) {
                        nextLevel = new JLabel("Continue");
                        nextLevel = getButton(nextLevel);
                        nextLevel.setBounds(constants.DIM_WIDTH / 2 - 100, constants.DIM_HEIGHT / 2 - 50, 200, 60);
                        add(nextLevel);
                        upgradeAmmo = new JLabel("Upgrade Ammo");
                        upgradeSpeed = new JLabel("Upgrade Speed");
                        upgradeAmmo = getButton(upgradeAmmo);
                        upgradeSpeed = getButton(upgradeSpeed);
                        upgradeAmmo.setBounds(constants.DIM_WIDTH / 2 - 120, constants.DIM_HEIGHT / 2 - 150, 240, 60);
                        upgradeSpeed.setBounds(constants.DIM_WIDTH / 2 - 120, constants.DIM_HEIGHT / 2 - 150, 240, 60);
                        if (level == 5 || level == 10 || level == 15) add(upgradeSpeed);
                        if (level == 4 || level == 8 || level == 12) add(upgradeAmmo);
                        doneAnimation = true;
                        timer6.stop();
                    }
                }
            }
            done[0] = true;
        });
        timer6.start();
    }

    public JLabel getButton(JLabel label) {
        label.setFont(new Font("Monospaced", Font.BOLD, 25));
        label.setForeground(Color.blue);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.white));
        return label;
    }

    public void initAmmo() {
        ammoLeft = new Bullet[ammo];
        ammoMiddle = new Bullet[ammo];
        ammoRight = new Bullet[ammo];
        for (int x = 0; x < ammo; x++) {
            ammoLeft[x] = new Bullet();
            ammoLeft[x].resetBullet();
            ammoMiddle[x] = new Bullet();
            ammoMiddle[x].resetBullet();
            ammoRight[x] = new Bullet();
            ammoRight[x].resetBullet();
        }
    }

    public void altArrow() {
        Timer timer = new Timer(500, e -> {
            if (arrow == arrowIcon.getImage()) arrow = arrowIcon2.getImage();
            else arrow = arrowIcon.getImage();
        });
        timer.start();
    }

    private void initialise() {
        score = 0;
        totalScore = 0;
        level = 1;
        Timer timer, timer1, timer2, timer3, timer4, timer5, timer6 = null;
        gameOver = false;
        first = null;
        second = null;
        ammo = 5;
        cities = new City[6];
        silos = new Silo[3];
        enemyAgents = new ArrayList<>();
        aircrafts = new ArrayList<>();
        satellites = new ArrayList<>();
        listOfLaunchers = new int[][]{{constants.DIM_WIDTH / 2 - 30 + 30, constants.DIM_HEIGHT - 110}, {10 + 30, constants.DIM_HEIGHT - 110}, {constants.DIM_WIDTH - 84 + 30, constants.DIM_HEIGHT - 110}};
        mouseX = 0;
        mouseY = 0;
        bulletVelocity = 0;
        extraVelocity = 0;
        explosions = new ArrayList<>();
        spawning = true;
        doneAnimation = false;
        scoreX = constants.DIM_WIDTH - 210;
        scoreY = 40;
        notPlaying = false;
        gameOverOpened = false;
        color = 100;
        topPanel = new JPanel();
        middlePanel = new JPanel();
        bottomPanel = new JPanel();
        topPanel.setBounds(0, 0, constants.DIM_WIDTH - 15, 40);
        middlePanel.setBounds(0, 40, constants.DIM_WIDTH - 15, 480); //need to use pack or setpreferredsize later but ok for now.
        bottomPanel.setBounds(0, 520, constants.DIM_WIDTH - 15, 42);
        JLabel testLabel = new JLabel("test");
        JLabel testLabel2 = new JLabel("test");
        JLabel testLabel3 = new JLabel("test");
        topPanel.setBackground(Color.black);
        middlePanel.setBackground(Color.black);
        bottomPanel.setBackground(Color.black);
        topPanel.add(testLabel);
        middlePanel.add(testLabel2);
        bottomPanel.add(testLabel3);
        //add(topPanel);add(middlePanel);add(bottomPanel);
        first = new drawString(800);
        second = new drawString(1750);
        initAmmo();
        initCities();
        initSilos();
        spawnEnemies();
        stopSpawning();
        addMouseListener(this);
        addMouseMotionListener(this);
        soundManager.playGameBackground();
        soundManager.initExplosions();
        soundManager.initAddScore();
        altArrow();
    }

    public View() {
        setLayout(null);
        setBackground(Color.BLACK);
        setup();
    }

    public static Image secondaryCrosshair() {
        String InvaderImg = "res/secondCrosshair.png";
        var tempImage = new ImageIcon(InvaderImg);
        return tempImage.getImage();
    }

    public void drawScore(Graphics g) {  //draws score text
        g.setColor(Color.blue);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(String.valueOf(totalScore), scoreX + 90, scoreY);
        g.drawString(String.valueOf(score), scoreX, scoreY);
    }

    public void setScoreLocation(int x, int y) {
        scoreX = x;
        scoreY = y;
    }

    //    public void movingText(Graphics g){
//        g.setColor(Color.blue);
//        g.setFont(new Font("Calibri",Font.BOLD,25));
//        g.drawString("Missile command!      Insert coins!        1 coin 1 play      Game Over!       Produced in 2022",movingTextX,constants.DIM_HEIGHT-45);
//    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAll(g);
    }


    public void drawAmmo(Graphics g) {
        int temp = 1;
        int currentX = 0;
        int currentY = 0;
        for (int x = 0; x < ammo; x++) {
            if (!ammoLeft[x].fired) {
                g.setColor(Color.blue);
                g.fillOval(silos[0].getX() + constants.Silo_W / 3 + currentX, silos[0].getY() + constants.Silo_H / 4 - 10 + currentY, 5, 5);
                currentX += 8;
                if (x == 1 || x == 4) {
                    currentY += 8;
                    currentX = -4 * temp;
                    temp++;
                }
            }
        }
        temp = 1;
        currentX = 0;
        currentY = 0;
        for (int x = 0; x < ammoMiddle.length; x++) {
            if (!ammoMiddle[x].fired) {
                g.setColor(Color.blue);
                g.fillOval(silos[1].getX() + constants.Silo_W / 3 + currentX, silos[1].getY() + constants.Silo_H / 4 - 10 + currentY, 5, 5);
                currentX += 8;
                if (x == 1 || x == 4) {
                    currentY += 8;
                    currentX = -4 * temp;
                    temp++;
                }
            }
        }
        temp = 1;
        currentX = 0;
        currentY = 0;
        for (int x = 0; x < ammoRight.length; x++) {
            if (!ammoRight[x].fired) {
                g.setColor(Color.blue);
                g.fillOval(silos[2].getX() + constants.Silo_W / 3 + currentX, silos[2].getY() + constants.Silo_H / 4 - 10 + currentY, 5, 5);
                currentX += 8;
                if (x == 1 || x == 4) {
                    currentY += 8;
                    currentX = -4 * temp;
                    temp++;
                }
            }
        }
    }

    public void drawCities(Graphics g) {
        for (int x = 0; x < 6; x++) {
            if (!cities[x].isKilled()) {
                cities[x].drawCity(g);
            }
        }
    }

    public void drawSilos(Graphics g) {
        for (int x = 0; x < 3; x++) {
            if (!silos[x].isKilled()) {
                silos[x].drawSilo(g);
            }
        }
    }

    //    private int scoreX=constants.DIM_WIDTH-180;
    //    private int scoreY=40;
    public void drawArrow(Graphics g) {
        g.drawImage(arrow, scoreX - 50 + addXArrow, scoreY - 20, this);
    }

    public void drawAll(Graphics g) {
        drawScore(g);
        drawArrow(g);
        g.setColor(playerColor);
        g.fillRect(0, constants.DIM_HEIGHT - 80, constants.DIM_WIDTH, 40);
        drawSilos(g);
        first.draw(g);
        second.draw(g);
        g.setColor(Color.blue);
        g.fillRect(mouseX - 1, mouseY - 12, 3, 24);
        g.fillRect(mouseX - 12, mouseY - 1, 24, 3);
        g.drawImage(secondaryCrosshair(), mouseX - 10, mouseY - 10, 20, 20, this);
        drawCities(g);
        for (int x = 0; x < ammo; x++) {
            if (ammoLeft[x].fired) {
                ammoLeft[x].drawBullet(g);
            }
            if (ammoMiddle[x].fired) {
                ammoMiddle[x].drawBullet(g);
            }
            if (ammoRight[x].fired) {
                ammoRight[x].drawBullet(g);
            }
        }
        if (!enemyAgents.isEmpty()) {
            for (enemyAgent agent : enemyAgents) {
                agent.drawAgent(g);
            }
        }
        if (!aircrafts.isEmpty()) {
            for (Aircraft aircraft : aircrafts) {
                aircraft.draw(g);
            }
        }
        if (!satellites.isEmpty()) {
            for (Satellite satellite : satellites) {
                satellite.draw(g);
            }
        }
        if (!explosions.isEmpty()) {
            for (Explosion explosion : explosions) {
                explosion.drawExplosion(g);
            }
        }
        if (!doneAnimation) drawAmmo(g);
    }

    public void openGameOver() {
//        JLabel gameOver = new JLabel("Game Over!");
//        gameOver.setFont(new Font("Calibri",Font.BOLD,50));
//        gameOver.setForeground(Color.white);
//        gameOver.setBounds(constants.DIM_WIDTH/2-200,constants.DIM_HEIGHT/2-100,400,100);
//        add(gameOver);
//        Timer timer = new Timer(3000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (getBackground() == Color.red) {
//                    setBackground(Color.blue);
//                } else {
//                    setBackground(Color.red);
//                }
//            }
//        });timer.start();
        if (timer != null) timer.stop();
        if (timer1 != null) timer1.stop();
        if (timer2 != null) timer2.stop();
        if (timer3 != null) timer3.stop();
        if (timer4 != null) timer4.stop();
        if (timer5 != null) timer5.stop();
        if (timer6 != null) timer6.stop();
        setBackground(Color.red);
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyGameGrid();
                totalScore += score;
                MissileCommand.main.gameOver(totalScore);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void emptyGameGrid() {
        emptyGrid(this);
    }

    public void updateGame() {
        int count = 0;
        if (gameOver && !gameOverOpened) {
            gameOverOpened = true;
            openGameOver();
        }
        for (City city : cities) {
            if (city.isKilled()) {
                count++;
            }
            if (!notPlaying && count == 6) {
                gameOver = true;
            }
        }
        if (!gameOver) {
            first.addX();
            second.addX();
            if (enemyAgents.isEmpty() && aircrafts.isEmpty() && satellites.isEmpty() && explosions.isEmpty() && !spawning) {
                spawning = true;
                levelScreen();
            }
            int tempCount = 0;
            for (int x = 0; x < ammo; x++) {
                if (ammoLeft[x].fired) {
                    ammoLeft[x].move();
                }
                if (ammoMiddle[x].fired) {
                    ammoMiddle[x].move();
                }
                if (ammoRight[x].fired) {
                    ammoRight[x].move();
                }
                if (level >= 5) {
                    if (ammoLeft[x].exploded) tempCount++;
                    if (ammoMiddle[x].exploded) tempCount++;
                    if(ammoRight[x].exploded) tempCount++;
                }
            }
            System.out.println(tempCount);
            if (tempCount == ammo*3 && (!aircrafts.isEmpty() || !satellites.isEmpty())) {
                gameOver = true;
            }
        }
        for (int i = 0; i < enemyAgents.size(); i++) {
            enemyAgent enemyAgent = enemyAgents.get(i);
            if (!enemyAgent.isKilled()) {
                enemyAgent.move();
            } else {
                enemyAgents.remove(enemyAgent);
            }
        }
        for (int i = 0; i < aircrafts.size(); i++) {
            Aircraft aircraft = aircrafts.get(i);
            if (!aircraft.isKilled()) {
                aircraft.moveAircraft();
            } else {
                aircrafts.remove(aircraft);
            }
        }
        for (int i = 0; i < satellites.size(); i++) {
            Satellite satellite = satellites.get(i);
            if (!satellite.isKilled()) {
                satellite.move();
            } else {
                satellites.remove(satellite);
            }
        }
        if (!explosions.isEmpty()) {
            for (int i = 0; i < explosions.size(); i++) {
                Explosion explosion = explosions.get(i);
                if (!explosion.isKilled()) {
                    if (!explosion.updateTillMax()) {
                        explosions.remove(explosion);
                    } else {
                        for (enemyAgent enemyAgent : enemyAgents) {
                            if (!enemyAgent.isKilled()) {
                                if (explosion.checkCollision(enemyAgent)) {
                                    enemyAgent.kill(true);
                                    score += 10 + 5 * level;
                                    explosions.add(new Explosion(enemyAgent.getX(), enemyAgent.getY(), 5, 40));
                                    soundManager.explode();
                                }
                            }
                        }
                        for (Aircraft aircraft : aircrafts) {
                            if (!aircraft.isKilled()) {
                                if (explosion.checkCollision(aircraft)) {
                                    aircraft.kill(true);
                                    score += 60;
                                    explosions.add(new Explosion(aircraft.getX(), aircraft.getY(), 5, 40));
                                    soundManager.explode();
                                }
                            }
                        }
                        for (Satellite satellite : satellites) {
                            if (!satellite.isKilled()) {
                                if (explosion.checkCollision(satellite)) {
                                    satellite.kill(true);
                                    score += 150;
                                    explosions.add(new Explosion(satellite.getX(), satellite.getY(), 5, 40));
                                    soundManager.explode();
                                }
                            }
                        }
                        for (int x = 0; x < 6; x++) {
                            if (!cities[x].isKilled()) {
                                if (explosion.checkCollision(cities[x])) {
                                    cities[x].kill(true);
                                    cities[x].isDead = true;
                                    explosions.add(new Explosion(cities[x].getX() + constants.City_W / 2, cities[x].getY() + constants.City_H / 2, 5, 20));
                                    soundManager.explode();
                                }
                            }
                        }
                        for (int x = 0; x < 3; x++) {
                            if (!silos[x].isKilled()) {
                                if (explosion.checkCollision(silos[x])) {
                                    silos[x].kill(true);
                                    if (x == 0) {
                                        for (Bullet bullet : ammoLeft) {
                                            bullet.fired = true;
                                            bullet.kill(true);
                                        }
                                    }
                                    if (x == 1) {
                                        for (Bullet bullet : ammoMiddle) {
                                            bullet.fired = true;
                                            bullet.kill(true);
                                        }
                                    }
                                    if (x == 2) {
                                        for (Bullet bullet : ammoRight) {
                                            bullet.fired = true;
                                            bullet.kill(true);
                                        }
                                    }
                                    explosions.add(new Explosion(silos[x].getX() + constants.Silo_W / 2, silos[x].getY() + constants.Silo_H * 1 / 3, 5, 20));
                                    soundManager.explode();
                                }
                            }
                        }
                    }
                }
            }
        }

        color++;
        if (color > 255) {
            color = 0;
        }
    }
    public void gameCycle(){
        updateGame();
        repaint();
    }
    public class timerGameLoop implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            gameCycle();
        }
    }

}
