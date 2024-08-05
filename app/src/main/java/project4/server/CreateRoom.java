package project4.server;

import project4.common.ClearConsole;
import project4.common.Sound;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class CreateRoom {
    static final int FINISH_LINE = 50;  // 결승선 위치
    static final int WIN_MOVE = 6;      // 승리 시 이동 칸 수
    static final int DRAW_MOVE = 3;     // 비기기 시 이동 칸 수
    static final int BURNING_MOVE = 3;  // 버닝 효과로 추가 이동 칸 수
    static final int NUM_PLAYERS = 3;   // 플레이어 수
    static List<String> horseNames; // 플레이어 말 이름 저장되는 List
    static List<String> choices;

    public static void execute() {
        Scanner scanner = new Scanner(System.in);
        // 8888포트 사용, 대기 인원 총 2명.
        int port = 8888;
        try (ServerSocket socket = new ServerSocket(port, 2)) {

            InetAddress localHost = InetAddress.getLocalHost();
            String localIPAddress = localHost.getHostAddress();
            System.out.println("\n IP 주소: " + localIPAddress + ", 포트 주소: " +  port +"\n");

            System.out.println("🐎묵찌빠 경마 게임에 오신 것을 환영합니다!🐎");
            System.out.println("각 플레이어가 자신의 말을 선택하고 경주를 시작합니다.\n");
            // 게임 대기 중
            System.out.println("플레이어를 기다리는 중....");
            // 사용자 입장
            Socket s1 = socket.accept();
            System.out.println("첫 번째 멤버가 입장하였습니다!");
            PrintStream out = new PrintStream(s1.getOutputStream());
            Scanner in = new Scanner(s1.getInputStream());
            out.println("wait");

            Socket s2 = socket.accept();
            System.out.println("두 번째 멤버가 입장하였습니다!");
            PrintStream out2 = new PrintStream(s2.getOutputStream());
            Scanner in2 = new Scanner(s2.getInputStream());
            out.println("start");
            out2.println("start");

            horseNames = new ArrayList<>();
            // 게임 스타트

            int[] positions = new int[NUM_PLAYERS]; // 말 위치
            int[] winStreaks = new int[NUM_PLAYERS];  // 연속 승리 횟수를 추적
            // 닉네임 설정
            SetName.SetName(scanner, out, out2, horseNames, in, in2);
            System.out.println("경기가 시작됩니다!");
            Sound.horseSound();
            while (true) {

                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                System.out.println("\n✊✌️✋");

                // 플레이어의 선택 입력
                choices = new ArrayList<>();

                boolean f1 = true;
                boolean f2 = true;

                Battle.battle(f1, scanner, choices, f2, out, in, out2, in2);



                System.out.println("묵찌빠 완료");
                out.println("묵찌빠 완료");
                out2.println("묵찌빠 완료");

                // 게임 결과 결정
                String result = DetermineOutcome.determineOutcome(choices);

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


                int count = movePosition.movePosition(result, positions, winStreaks, out, out2);

                if (count == 0) {
                    out.println("noburning");
                    out2.println("noburning");
                } else {
                    //버닝 확인 종료
                    out.println("burning");
                    out2.println("burning");
                }

                // 위치 업데이트
                for (int i = 0; i < NUM_PLAYERS; i++) {
                    if (positions[i] > FINISH_LINE) positions[i] = FINISH_LINE;
                }

                ClearConsole.clearConsole();

                PrintPosition.printPosition(positions, out, out2);

                if (isRaceFinished(positions)) {
                    out.println("end");
                    out2.println("end");
                    break;
                } else {
                    out.println("play");
                    out2.println("play");

                }

                try {
                    Thread.sleep(2000); // 2초 대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            ClearConsole.clearConsole();
            AnnounceWinner.announceWinner(positions, horseNames, out, out2);

            String instr1 = new String();
            String instr2 = new String();
            while (true) {
                if (instr1.isEmpty()) {
                    instr1 = in.nextLine();
                }
                if (instr2.isEmpty()) {
                    instr2 = in2.nextLine();
                }
                if (!instr1.isEmpty() && !instr2.isEmpty()) {
                    break;
                }

            }
        } catch (Exception e) {
            System.err.println("서버 소켓 처리 중 오류: " + e.getMessage());
        }

    }

    public static String getMatchResult(String choice1, String choice2) {
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

    public static boolean isRaceFinished(int[] positions) {
        for (int position : positions) {
            if (position >= FINISH_LINE) {
                return true;
            }
        }
        return false;
    }

}
