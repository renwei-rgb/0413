package com.tss.atm.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObstacleGame extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static final int PLAYER_SIZE = 40;
    private static final int OBSTACLE_WIDTH = 20;
    private static final int OBSTACLE_HEIGHT = 60;
    private static final int GROUND_HEIGHT = 50;
    private static final double GRAVITY = 0.5;
    private static final double JUMP_FORCE = -15;

    private Canvas canvas;
    private GraphicsContext gc;
    private double playerX = 100;
    private double playerY = HEIGHT - GROUND_HEIGHT - PLAYER_SIZE;
    private double playerVelocityY = 0;
    private int score = 0;
    private boolean isJumping = false;
    private List<Rectangle> obstacles;
    private Random random;
    private boolean isGameOver = false;
    private AnimationTimer gameLoop;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        primaryStage.setTitle("障碍跳跃游戏");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // 初始化游戏
        obstacles = new ArrayList<>();
        random = new Random();

        // 键盘控制
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE && !isJumping && !isGameOver) {
                isJumping = true;
                playerVelocityY = JUMP_FORCE;
            } else if (event.getCode() == KeyCode.R && isGameOver) {
                resetGame();
            }
        });

        // 游戏循环
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isGameOver) {
                    updateGame();
                }
                drawGame();
            }
        };
        gameLoop.start();

        primaryStage.show();
    }

    private void updateGame() {
        // 更新玩家位置
        if (isJumping) {
            playerVelocityY += GRAVITY;
            playerY += playerVelocityY;

            if (playerY >= HEIGHT - GROUND_HEIGHT - PLAYER_SIZE) {
                playerY = HEIGHT - GROUND_HEIGHT - PLAYER_SIZE;
                isJumping = false;
                playerVelocityY = 0;
            }
        }

        // 更新障碍物
        for (int i = obstacles.size() - 1; i >= 0; i--) {
            Rectangle obstacle = obstacles.get(i);
            obstacle.setX(obstacle.getX() - 5);

            // 移除超出屏幕的障碍物
            if (obstacle.getX() + OBSTACLE_WIDTH < 0) {
                obstacles.remove(i);
                score++;
            }

            // 检测碰撞
            if (obstacle.getBoundsInParent().intersects(
                    new Rectangle(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE).getBoundsInParent())) {
                isGameOver = true;
                gameLoop.stop();
            }
        }

        // 生成新障碍物
        if (random.nextInt(50) == 0) {
            Rectangle obstacle = new Rectangle(
                    WIDTH,
                    HEIGHT - GROUND_HEIGHT - OBSTACLE_HEIGHT,
                    OBSTACLE_WIDTH,
                    OBSTACLE_HEIGHT
            );
            obstacle.setFill(Color.BLACK);
            obstacles.add(obstacle);
        }
    }

    private void drawGame() {
        // 清空画布
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        // 绘制背景
        gc.setFill(Color.SKYBLUE);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // 绘制地面
        gc.setFill(Color.GREEN);
        gc.fillRect(0, HEIGHT - GROUND_HEIGHT, WIDTH, GROUND_HEIGHT);

        // 绘制玩家
        gc.setFill(Color.RED);
        gc.fillOval(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);

        // 绘制障碍物
        gc.setFill(Color.BLACK);
        for (Rectangle obstacle : obstacles) {
            gc.fillRect(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
        }

        // 绘制分数
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(20));
        gc.fillText("分数: " + score, 20, 30);

        // 游戏结束显示
        if (isGameOver) {
            gc.setFill(Color.RED);
            gc.setFont(Font.font(40));
            String gameOver = "游戏结束!";
            String scoreText = "最终得分: " + score;
            String restart = "按 R 键重新开始";

            double gameOverX = (WIDTH - gc.getFont().getSize() * gameOver.length() / 2) / 2;
            double scoreX = (WIDTH - gc.getFont().getSize() * scoreText.length() / 2) / 2;
            double restartX = (WIDTH - gc.getFont().getSize() * restart.length() / 2) / 2;

            gc.fillText(gameOver, gameOverX, HEIGHT / 2 - 40);
            gc.fillText(scoreText, scoreX, HEIGHT / 2);
            gc.fillText(restart, restartX, HEIGHT / 2 + 40);
        }
    }

    private void resetGame() {
        playerY = HEIGHT - GROUND_HEIGHT - PLAYER_SIZE;
        playerVelocityY = 0;
        obstacles.clear();
        score = 0;
        isGameOver = false;
        gameLoop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 
} 