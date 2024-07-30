package org.example;

import java.util.Scanner;

public class Test {

    private static final int FINISH_LINE = 50;  // 결승선 위치
    private static final int WIN_MOVE = 6;      // 승리 시 이동 칸 수
    private static final int DRAW_MOVE = 3;     // 비기기 시 이동 칸 수

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 플레이어와 말의 초기화
        System.out.println("가위 바위 보 경마 게임에 오신 것을 환영합니다!");
        System.out.println("플레이어 1과 플레이어 2가 각자 말을 선택하고 경주를 시작합니다.");

        String player1Horse = getHorseName("플레이어 1");
        String player2Horse = getHorseName("플레이어 2");

        int player1Position = 0;
        int player2Position = 0;

        System.out.println("경기가 시작됩니다!");

        while (player1Position < FINISH_LINE && player2Position < FINISH_LINE) {
            System.out.println("\n가위 바위 보를 입력하세요. (가위 / 바위 / 보)");

            // 플레이어의 선택 입력
            String player1Choice = getPlayerChoice("플레이어 1");
            String player2Choice = getPlayerChoice("플레이어 2");

            // 게임 결과 결정
            String result = determineOutcome(player1Choice, player2Choice);
            System.out.println("결과: " + result);

            // 결과에 따라 말 이동
            switch (result) {
                case "플레이어 1 승리":
                    player1Position += WIN_MOVE;
                    break;
                case "플레이어 2 승리":
                    player2Position += WIN_MOVE;
                    break;
                case "무승부":
                    player1Position += DRAW_MOVE;
                    player2Position += DRAW_MOVE;
                    break;
            }

            // 위치 업데이트
            if (player1Position > FINISH_LINE) player1Position = FINISH_LINE;
            if (player2Position > FINISH_LINE) player2Position = FINISH_LINE;

            clearConsole();
            printRaceTrack(player1Horse, player1Position, player2Horse, player2Position, FINISH_LINE);

            try {
                Thread.sleep(1000); // 1초 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        clearConsole();
        if (player1Position >= FINISH_LINE && player2Position >= FINISH_LINE) {
            System.out.println("\n경기가 종료되었습니다! 무승부입니다!");
        } else if (player1Position >= FINISH_LINE) {
            System.out.println("\n" + player1Horse + "가(이) 승리했습니다!");
        } else {
            System.out.println("\n" + player2Horse + "가(이) 승리했습니다!");
        }

        scanner.close();
    }

    private static String getHorseName(String player) {
        Scanner scanner = new Scanner(System.in);
        String horseName;
        while (true) {
            System.out.print(player + "의 말 이름을 입력하세요 (두 글자): ");
            horseName = scanner.nextLine().trim();
            if (horseName.length() == 2) {
                break;
            } else {
                System.out.println("이름은 두 글자만 입력할 수 있습니다. 다시 시도하세요.");
            }
        }
        return horseName;
    }

    private static String getPlayerChoice(String player) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.print(player + "의 선택을 입력하세요 (가위 / 바위 / 보): ");
            choice = scanner.nextLine().trim();
            if (choice.equals("가위") || choice.equals("바위") || choice.equals("보")) {
                break;
            } else {
                System.out.println("유효한 선택이 아닙니다. '가위', '바위', '보' 중 하나를 입력하세요.");
            }
        }
        return choice;
    }

    private static String determineOutcome(String player1Choice, String player2Choice) {
        if (player1Choice.equals(player2Choice)) {
            return "무승부";
        }

        if ((player1Choice.equals("가위") && player2Choice.equals("보")) ||
                (player1Choice.equals("바위") && player2Choice.equals("가위")) ||
                (player1Choice.equals("보") && player2Choice.equals("바위"))) {
            return "플레이어 1 승리";
        } else {
            return "플레이어 2 승리";
        }
    }

    private static void printRaceTrack(String player1Horse, int player1Position, String player2Horse, int player2Position, int finishLine) {
        StringBuilder track1 = new StringBuilder();
        StringBuilder track2 = new StringBuilder();

        // 플레이어 1의 트랙
        for (int i = 0; i <= finishLine; i++) {
            if (i == player1Position) {
                track1.append("🏇");
            } else {
                track1.append(" ");
            }
        }
        track1.append("🏁"); // 결승선 추가

        // 플레이어 2의 트랙
        for (int i = 0; i <= finishLine; i++) {
            if (i == player2Position) {
                track2.append("🏇");
            } else {
                track2.append(" ");
            }
        }
        track2.append("🏁"); // 결승선 추가

        // 트랙과 결승선을 출력합니다.
        System.out.println("------------------------------------------------------------");
        System.out.println(player1Horse + " " + track1.toString());
        System.out.println("------------------------------------------------------------");
        System.out.println(player2Horse + " " + track2.toString());
        System.out.println("------------------------------------------------------------");
    }

    private static void clearConsole() {
        // 콘솔 화면을 지우기 위한 ANSI 이스케이프 코드 사용
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}