package project4;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class CreateRoom {
    private static final int FINISH_LINE = 50;  // 결승선 위치
    private static final int WIN_MOVE = 6;      // 승리 시 이동 칸 수
    private static final int DRAW_MOVE = 3;     // 비기기 시 이동 칸 수
    private static final int BURNING_MOVE = 3;  // 버닝 효과로 추가 이동 칸 수
    private static final int NUM_PLAYERS = 3;   // 플레이어 수
    private static List<String> horseNames; // 플레이어 말 이름 저장되는 List
    public static void execute() {
        Scanner scanner = new Scanner(System.in);
        // 8888포트 사용, 대기 인원 총 2명.
        try (ServerSocket socket = new ServerSocket(8888, 2)) {
            System.out.println("🐎묵찌빠 경마 게임에 오신 것을 환영합니다!🐎");
            System.out.println("각 플레이어가 자신의 말을 선택하고 경주를 시작합니다.\n");
            // 게임 대기 중
            System.out.println("플레이어를 기다리는 중....");
            // 사용자 입장
            Socket s1 = socket.accept();
            System.out.println("첫 번째 멤버가 입장하였습니다. !");
            PrintStream out = new PrintStream(s1.getOutputStream());
            Scanner in = new Scanner(s1.getInputStream());
            out.println("wait");

            Socket s2 = socket.accept();
            System.out.println("두 번째 멤버가 입장하였습니다. !");
            PrintStream out2 = new PrintStream(s2.getOutputStream());
            Scanner in2 = new Scanner(s2.getInputStream());
            out.println("start");
            out2.println("start");

            horseNames =  new ArrayList<>();
            // 게임 스타트

            int[] positions = new int[NUM_PLAYERS]; // 말 위치
            int[] winStreaks = new int[NUM_PLAYERS];  // 연속 승리 횟수를 추적
            // 닉네임 설정
            SetName(scanner, out, out2, horseNames, in, in2);
            System.out.println("경기가 시작됩니다!");
            while (true) {

                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                System.out.println("\n✊✌️✋");

                // 플레이어의 선택 입력
                List<String> choices = new ArrayList<>();

                boolean f1 = true;
                boolean f2 = true;

                ob:
                for (int i = 0; i <= NUM_PLAYERS; i++) {
                    String choice;
                    //1번 플레이어 묵찌빠 선택
                    while (f1) {
                        System.out.print(horseNames.get(i) + "의 선택은? (묵 / 찌 / 빠): ");
                        choice = scanner.nextLine();
                        if (choice.equals("묵") || choice.equals("찌") || choice.equals("빠")) {
                            choices.add(choice);
                            f1 = false;
                            continue ob;
                        } else {
                            System.out.println("유효한 선택이 아닙니다. '묵', '찌', '빠' 중 하나를 입력하세요.");
                        }
                    }
                    //2번 플레이어 묵찌빠 선택
                    while (f2) {
                        out.println(horseNames.get(i) + "의 선택은? (묵 / 찌 / 빠): ");
                        String choice2 = in.nextLine(); // 1번이 보낸 값 수신
                        if (choice2.equals("묵") || choice2.equals("찌") || choice2.equals("빠")) {
                            choices.add(choice2);
                            out.println("success");
                            f2 = false;
                            continue ob;
                        } else {
                            out.println("유효한 선택이 아닙니다. '묵', '찌', '빠' 중 하나를 입력하세요.");
                        }
                    }
                    //3번 플레이어 묵찌빠 선택
                    while (true) {
                        out2.println(horseNames.get(i) + "의 선택은? (묵 / 찌 / 빠): ");
                        String choice3 = in2.nextLine(); // 2번이 보낸 값 수신
                        if (choice3.equals("묵") || choice3.equals("찌") || choice3.equals("빠")) {
                            choices.add(choice3);
                            out2.println("success");
                            break ob;
                        } else {
                            out2.println("유효한 선택이 아닙니다. '묵', '찌', '빠' 중 하나를 입력하세요.");
                        }
                    }
                }//묵찌빠 저장 완료

                System.out.println("묵찌빠 완료");
                out.println("묵찌빠 완료");
                out2.println("묵찌빠 완료");

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
                out.println("[결과] " + result);
                out2.println("[결과] " + result);

                //여기까지 joinRoom에서 수신


                // 결과에 따라 말 이동 및 연속 승리 횟수 업데이트
                for (int i = 0; i < NUM_PLAYERS; i++) {
                    if (result.contains(horseNames.get(i) + " 승리")) {
                        positions[i] += WIN_MOVE;
                        winStreaks[i]++;
                        if (winStreaks[i] >= 2) {  // 두 번 연속 승리 시
                            positions[i] += BURNING_MOVE;
                            System.out.println("🔥 " + horseNames.get(i) + " 버닝!🔥");
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

        } catch (Exception e) {
            System.err.println("서버 소켓 처리 중 오류: " + e.getMessage());
        }

    }

    //닉네임 설정
    private static void SetName(Scanner scanner, PrintStream out, PrintStream out2, List<String> horseNames, Scanner in, Scanner in2) {
        // 키보드 입력을 받아서 서버1,2에게 전송한다.
        System.out.print("말 이름을 입력하세요 (두 글자): ");
        String input = scanner.nextLine();
        out.println(input);
        out2.println(input);
        horseNames.add(input); // 1번 마 이름

        System.out.println("사용자2 가 입력 중입니다.");

        // 서버1이 보낸 데이터를 수신한다.
        String str = in.nextLine();
        System.out.println(str);
        // 서버 2에게 전송
        out2.println(str);
        horseNames.add(str); // 2번 마 이름


        System.out.println("사용자3 이 입력 중입니다.");

        // 서버2가 보낸 데이터를 수신한다.
        String str2 = in2.nextLine();
        System.out.println(str2);
        // 서버1에게 전송
        out.println(str2);
        horseNames.add(str2); // 3번 마 이름

        int index = 1;
        // 서버,1,2 에게 말 이름 전송
        for (String horseName : horseNames) {
            System.out.println((index) + "번째 플레이어 : " + horseName);
            out.println((index) + "번째 플레이어 : " + horseName);
            out2.println((index++) + "번째 플레이어 : " + horseName);
        }
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
                winners.add(horseNames.get(i) + " 승리");
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

    private static void clearConsole() {
        // 콘솔 화면을 지우기 위한 ANSI 이스케이프 코드 사용
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
}
