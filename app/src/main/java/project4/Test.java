package project4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Test {

    private static final int FINISH_LINE = 50;  // 결승선 위치
    private static final int WIN_MOVE = 6;      // 승리 시 이동 칸 수
    private static final int DRAW_MOVE = 3;     // 비기기 시 이동 칸 수
    private static final int BURNING_MOVE = 3;  // 버닝 효과로 추가 이동 칸 수
    private static final int NUM_PLAYERS = 3;   // 플레이어 수

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 플레이어와 말의 초기화
        System.out.println("🐎묵찌빠 경마 게임에 오신 것을 환영합니다!🐎");
        System.out.println("각 플레이어가 자신의 말을 선택하고 경주를 시작합니다.");

        List<String> horseNames = new ArrayList<>();
        for (int i = 1; i <= NUM_PLAYERS; i++) {
            horseNames.add(getHorseName("플레이어 " + i));
        }

        int[] positions = new int[NUM_PLAYERS];
        int[] winStreaks = new int[NUM_PLAYERS];  // 연속 승리 횟수를 추적

        System.out.println("경기가 시작됩니다!");

        while (true) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("\n✊✌️✋");

            // 플레이어의 선택 입력
            List<String> choices = new ArrayList<>();
            for (int i = 1; i <= NUM_PLAYERS; i++) {
                choices.add(getPlayerChoice("플레이어 " + i));
            }

            // 게임 결과 결정
            String result = determineOutcome(choices);

            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000); // 1초 대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(".\n");
            }

            System.out.println("[결과] " + result);

            // 결과에 따라 말 이동 및 연속 승리 횟수 업데이트
            for (int i = 0; i < NUM_PLAYERS; i++) {
                if (result.contains("플레이어 " + (i + 1) + " 승리")) {
                    positions[i] += WIN_MOVE;
                    winStreaks[i]++;
                    if (winStreaks[i] >= 2) {  // 두 번 연속 승리 시
                        positions[i] += BURNING_MOVE;
                        System.out.println("🔥플레이어 " + (i + 1) + " 버닝!🔥");
                        System.out.println("🔥추가 " + BURNING_MOVE + "칸 이동!🔥");
                    }
                } else if (result.equals("무승부")) {
                    positions[i] += DRAW_MOVE;
                    winStreaks[i] = 0;
                } else {
                    winStreaks[i] = 0;
                }
            }

            // 위치 업데이트
            for (int i = 0; i < NUM_PLAYERS; i++) {
                if (positions[i] > FINISH_LINE) positions[i] = FINISH_LINE;
            }

            clearConsole();
            printRaceTrack(horseNames, positions, FINISH_LINE);

            if (isRaceFinished(positions)) {
                break;
            }

            try {
                Thread.sleep(2000); // 2초 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        clearConsole();
        announceWinner(positions, horseNames);

        scanner.close();
    }

    private static String getHorseName(String player) {
        Scanner scanner = new Scanner(System.in);
        String horseName;
        while (true) {
            System.out.print(player + "의 말 이름을 입력하세요: ");
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
            System.out.print(player + "의 선택은? (묵 / 찌 / 빠): ");
            choice = scanner.nextLine().trim();
            if (choice.equals("묵") || choice.equals("찌") || choice.equals("빠")) {
                break;
            } else {
                System.out.println("유효한 선택이 아닙니다. '묵', '찌', '빠' 중 하나를 입력하세요.");
            }
        }
        return choice;
    }

    private static String determineOutcome(List<String> choices) {
        List<String> winners = new ArrayList<>();
        Set<String> uniqueChoices = new HashSet<>(choices);

        // 모든 선택이 서로 같을 경우 무승부 처리
        if (uniqueChoices.size() == 1) {
            return "무승부";
        }

        // 모든 선택이 서로 다를 경우 무승부 처리
        if (uniqueChoices.size() == NUM_PLAYERS) {
            return "무승부";
        }

        // 승리 조건을 평가합니다.
        String[] choiceArray = choices.toArray(new String[0]);
        boolean[] isWinner = new boolean[NUM_PLAYERS];

        for (int i = 0; i < NUM_PLAYERS; i++) {
            boolean winner = true;
            for (int j = 0; j < NUM_PLAYERS; j++) {
                if (i != j) {
                    String result = getMatchResult(choiceArray[i], choiceArray[j]);
                    if (result.equals("패배")) {
                        winner = false;
                        break;
                    }
                }
            }
            isWinner[i] = winner;
        }

        // 결과를 승리로 설정
        for (int i = 0; i < NUM_PLAYERS; i++) {
            if (isWinner[i]) {
                winners.add("플레이어 " + (i + 1) + " 승리");
            }
        }

        if (winners.isEmpty()) {
            return "무승부";
        } else {
            return String.join(", ", winners);
        }
    }

    private static String getMatchResult(String choice1, String choice2) {
        if (choice1.equals(choice2)) {
            return "무승부";
        }

        if ((choice1.equals("묵") && choice2.equals("찌")) ||
                (choice1.equals("찌") && choice2.equals("빠")) ||
                (choice1.equals("빠") && choice2.equals("묵"))) {
            return "승리";
        } else {
            return "패배";
        }
    }

    private static void printRaceTrack(List<String> horseNames, int[] positions, int finishLine) {
        StringBuilder[] tracks = new StringBuilder[NUM_PLAYERS];
        for (int i = 0; i < NUM_PLAYERS; i++) {
            tracks[i] = new StringBuilder();
        }

        for (int i = 0; i <= finishLine; i++) {
            for (int j = 0; j < NUM_PLAYERS; j++) {
                if (i == positions[j]) {
                    tracks[j].append("🏇");
                } else {
                    tracks[j].append(" ");
                }
            }
        }

        for (int i = 0; i < NUM_PLAYERS; i++) {
            tracks[i].append("🏁");
        }

        System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        for (int i = 0; i < NUM_PLAYERS; i++) {
            System.out.println(horseNames.get(i) + " " + tracks[i].toString());
            System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        }
    }

    private static boolean isRaceFinished(int[] positions) {
        for (int position : positions) {
            if (position >= FINISH_LINE) {
                return true;
            }
        }
        return false;
    }

    private static void announceWinner(int[] positions, List<String> horseNames) {
        List<String> winners = new ArrayList<>();
        for (int i = 0; i < NUM_PLAYERS; i++) {
            if (positions[i] >= FINISH_LINE) {
                winners.add(horseNames.get(i));
            }
        }

        if (winners.size() > 1) {
            System.out.println("\n경기가 종료되었습니다!");
            System.out.println("🎉승자는 " + String.join(", ", winners) + "입니다!🎉");
        } else if (winners.size() == 1) {
            System.out.println("\n" + "🎉" + winners.get(0) + "가(이) 승리했습니다!🎉");
        } else {
            System.out.println("\n경기가 종료되었습니다!");
            System.out.println("🎉무승부입니다!🎉");
        }
    }

    private static void clearConsole() {
        // 콘솔 화면을 지우기 위한 ANSI 이스케이프 코드 사용
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}