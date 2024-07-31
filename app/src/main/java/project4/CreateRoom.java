package project4;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateRoom {
    private static final int FINISH_LINE = 50;  // 결승선 위치
    private static final int WIN_MOVE = 6;      // 승리 시 이동 칸 수
    private static final int DRAW_MOVE = 3;     // 비기기 시 이동 칸 수
    private static final int BURNING_MOVE = 3;  // 버닝 효과로 추가 이동 칸 수
    private static final int NUM_PLAYERS = 3;   // 플레이어 수

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

            // 게임 스타트
            List<String> horseNames = new ArrayList<>(); // 플레이어 말 이름 저장되는 List
            int[] positions = new int[NUM_PLAYERS]; // 말 위치
            int[] winStreaks = new int[NUM_PLAYERS];  // 연속 승리 횟수를 추적

            System.out.println("경기가 시작됩니다!");
            // 닉네임 설정
            SetName(scanner, out, out2, horseNames, in, in2);

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
            System.out.println((index) + ". " + horseName);
            out.println((index) + "." + horseName);
            out2.println((index++) + "." + horseName);
        }
    }
}
