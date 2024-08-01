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
        for (String menu : menus) {
            System.out.println((index++) + ". " + menu);
        }
    }
}
