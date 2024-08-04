package project4;

import project4.client.JoinRoom;
import project4.server.CreateRoom;

import java.util.Scanner;

public class App {

    public static String[] menus = {"생성","입장","종료"};
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int index = 1;

        while (true) {
            mainMenu(index);
            String input = scanner.nextLine();
            try {
                int command = Integer.parseInt(input);

                switch (command) {
                    case 1:
                        CreateRoom.execute();
                        break;
                    case 2:
                        JoinRoom.execute();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("잘못된 입력입니다.");
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private static void mainMenu(int index) {
        System.out.println("===================================================");
        System.out.println("               🐎 묵찌빠 경마 게임 🐎               ");
        System.out.println("===================================================");
        System.out.println();
        System.out.println("                  /\\_/\\    ----    /\\_/\\            ");
        System.out.println("                 ( o.o )   ----   ( o.o )           ");
        System.out.println("                  > ^ <    ----    > ^ <            ");
        System.out.println("        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~       ");
        System.out.println("                  [1] 게임 생성                       ");
        System.out.println("                  [2] 게임 입장                       ");
        System.out.println("                  [3] 종료                           ");
        System.out.println("        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~       ");
        System.out.println("===================================================");

    }
}
