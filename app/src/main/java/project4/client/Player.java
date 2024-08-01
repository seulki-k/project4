package project4.client;

import java.util.Scanner;

public class Player {
    public static void burning(Scanner in) {
        String answer = in.nextLine();
        if (!answer.equals("noburning")) {
            System.out.println(answer);
            while (true) {
                System.out.println(in.nextLine()); //결과 출력
                if (in.nextLine().equals("burning")) {
                    break;
                }
            }
        }
    }

    public static void positionHorse(Scanner in) {
        System.out.println(in.nextLine());
        for (int i = 0; i < 3; i++) {
            System.out.println(in.nextLine());
            System.out.println(in.nextLine());
        }
    }
    public static void threeSecondWait() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(3000); // 1초 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(".\n");
        }
    }
}
