import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int sizeBoard = 5;
        int lives = 3;
        String[][] board = new String[sizeBoard][sizeBoard];
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        String muchroom = "\uD83C\uDF44";
        String barn = "\uD83D\uDED6";
        String grass = "\uD83C\uDF3E";
        String person = "\uD83D\uDC68\uD83C\uDFFC\u200D\uD83C\uDF3E";
        String bad = "\uD83D\uDECF";
        String door = "\uD83D\uDEAA";


        int wifeCount = 0;
        int winterCount = 0;
        int count = 0;
        int personY = 5;
        int personX = 1;
        String leftBlock = " | ";
        String rightBlock = " |";
        String wall = " + —— + —— + —— + —— + —— + ";
        for (int y = 1; y <= sizeBoard; y++) {
            for (int x = 1; x <= sizeBoard; x++) {
                board[y - 1][x - 1] = "  ";
            }
        }

        int countMuchroom = 10;
        int countGrass = 20;

        for (int i = 0; i < countGrass; i++) {
            board[random.nextInt(sizeBoard - 1)][random.nextInt(sizeBoard)] = grass;
        }
        for (int i = 0; i < countMuchroom; i++) {
            board[random.nextInt(sizeBoard - 1)][random.nextInt(sizeBoard)] = muchroom;
        }
        board[sizeBoard - 1][sizeBoard - 1] = barn;
        board[personY - 1][personX - 1] = person;
        board[4][2] = bad;

        while (0 < lives) {
            for (String[] row : board) {
                System.out.println(wall);

                for (String column : row) {
                    System.out.print(leftBlock);
                    System.out.print(column);
                }
                System.out.println(rightBlock);
            }
            System.out.println(wall);

            board[personY - 1][personX - 1] = "  ";
            System.out.print("Ваш ход: ");
            String motion = scanner.nextLine();
            switch (motion) {
                case "w":
                    personY -= 1;
                    break;
                case "d":
                    personX += 1;
                    break;
                case "a":
                    personX -= 1;
                    break;
                case "s":
                    personY += 1;
                    break;
                case "wife":
                    if(wifeCount > 0) {
                        int wifeY = scanner.nextInt();
                        for (int i = 0; i <= 4; i++) {
                            if (board[wifeY - 1][i].equals(" ")) {
                                continue;
                            } else if (board[wifeY - 1][i].equals(grass)) {
                                count++;
                            } else if (board[wifeY - 1][i].equals(muchroom)) {
                                count = count + 3;
                            }
                            board[wifeY - 1][i] = "  ";
                        }
                        wifeCount--;
                    }
                    else{
                        System.out.println("Вам не хватает жён.");
                    }
            }
            if (motion.equals("stop")) {
                System.out.println("Ну как хочешь." +
                        "\nПока.");
                break;
            }

            for (int i = 0; i < 30; i++) {
                System.out.println();
            }
            if (board[personY - 1][personX - 1].equals(" ")) {
                continue;
            } else if (board[personY - 1][personX - 1].equals(grass)) {
                count++;
            } else if (board[personY - 1][personX - 1].equals(muchroom)) {
                if(task() == true){
                    count = count + 5;
                }
                else{
                    lives--;
                }
            } else if (board[personY - 1][personX - 1].equals(barn)) {
                boolean Repit = true;
                while (Repit) {
                    System.out.println("Что будешь делать?");
                    System.out.println("1. Сколько скошенно?");
                    System.out.println("2. Отправиться к барину.");
                    System.out.println("3. Вернуться не поле.");
                    int ans = scanner.nextInt();
                    switch (ans) {
                        case 1:
                            System.out.println(count);
                            break;
                        case 2:
                            System.out.println("Что хотите сделать?");
                            System.out.println("1. Выкупить себя за 150 стогов сена.");
                            System.out.println("2. Купить жену.");
                            System.out.println("3. Уйти.");
                            ans = scanner.nextInt();
                            if (ans == 1 && count >= 150) {
                                count = 0;
                                board[4][0] = door;
                                System.out.println("Успешно.");
                            }else if(ans == 2 && count >= 7){
                                wifeCount++;
                            }
                            else if (ans == 3) {
                                System.out.println();
                            } else {
                                System.out.println("Вам не хватает стогов сена.");
                            }
                            break;
                        case 3:
                            personX--;
                            board[personY - 1][personX - 1] = person;
                            board[sizeBoard - 1][sizeBoard - 1] = barn;
                            Repit = false;
                            continue;
                        default:
                            System.out.println("Выбирай, то что предлагают.");
                    }
                }
            } else if (board[personY - 1][personX - 1].equals(bad)) {
                restore(sizeBoard, board, countMuchroom, countGrass, personX, personY);
                winterCount++;
                board[personY - 1][personX - 1] = bad;
                personX--;

            } else if (board[personY - 1][personX - 1].equals(door)) {
                System.out.println("Вы свободны и вы прошли игру.");
                System.out.println("Вам потребовалось " + winterCount + " зим.");
                break;
            }

            board[personY - 1][personX - 1] = person;
        }
        if(lives <= 0){
            System.out.println("Вы умерли.");
        }
    }

    public static void restore(int sizeBoard, String[][] board, int countMuchroom, int countGrass, int personX, int personY) {
        Random random = new Random();
        String muchroom = "\uD83C\uDF44";
        String barn = "\uD83D\uDED6";
        String grass = "\uD83C\uDF3E";
        String person = "\uD83D\uDC68\uD83C\uDFFC\u200D\uD83C\uDF3E";
        String bad = "\uD83D\uDECF";
        board[personY - 1][personX - 1] = "  ";
        for (int y = 1; y <= sizeBoard; y++) {
            for (int x = 1; x <= sizeBoard; x++) {
                board[y - 1][x - 1] = "  ";
            }
        }
        board[sizeBoard - 1][sizeBoard - 1] = barn;
        for (int i = 0; i < countGrass; i++) {
            board[random.nextInt(sizeBoard - 1)][random.nextInt(sizeBoard)] = muchroom;
        }
        for (int i = 0; i < countMuchroom; i++) {
            board[random.nextInt(sizeBoard - 1)][random.nextInt(sizeBoard)] = grass;
        }

    }

    public static boolean task() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int a;
        int b;
        int answer;
        int choice = random.nextInt(4);
        switch (choice) {
            case 0:
                a = random.nextInt(500);
                b = random.nextInt(500);
                System.out.println("Решите пример: " + a + " + " + b + "=");
                answer = scanner.nextInt();
                if (answer == a + b) {
                    System.out.println("Всё верно.");
                    return true;
                } else {
                    System.out.println("Неверно, ты не смог собрать этот гриб.");
                    return false;
                }
            case 1:
                a = random.nextInt(400);
                b = random.nextInt(1, 10);
                System.out.println("Решите пример: " + a + " % " + b + "=");
                answer = scanner.nextInt();
                if (answer == a % b) {
                    System.out.println("Всё верно.");
                    return true;
                } else {
                    System.out.println("Неверно, ты не смог собрать этот гриб.");
                    return false;
                }
            case 2:
                a = random.nextInt(20);
                b = random.nextInt(10);
                System.out.println("Решите пример: " + a + " * " + b + "=");
                answer = scanner.nextInt();
                if (answer == a * b) {
                    System.out.println("Всё верно.");
                    return true;
                } else {
                    System.out.println("Неверно, ты не смог собрать этот гриб.");
                    return false;
                }
            case 3:
                a = random.nextInt(700);
                b = random.nextInt(700);
                System.out.println("Решите пример: " + a + " - " + b + "=");
                answer = scanner.nextInt();
                if (answer == a - b) {
                    System.out.println("Всё верно.");
                    return true;
                } else {
                    System.out.println("Неверно, ты не смог собрать этот гриб.");
                    return false;
                }
            default:
                return false;

        }
    }
}
