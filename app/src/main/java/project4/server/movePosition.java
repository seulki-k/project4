package project4.server;

import java.io.PrintStream;

public class movePosition {
    public static int movePosition(String result, int[] positions, int[] winStreaks, PrintStream out, PrintStream out2) {
        // 결과에 따라 말 이동 및 연속 승리 횟수 업데이트
        String burning1 = "";
        String burning2 = "";
        int count = 0;
        for (int i = 0; i < CreateRoom.NUM_PLAYERS; i++) {
            if (result.contains(CreateRoom.horseNames.get(i) + " 승리")) {
                positions[i] += CreateRoom.WIN_MOVE;
                winStreaks[i]++;
                if (winStreaks[i] >= 2) {  // 두 번 연속 승리 시
                    count++;
                    positions[i] += CreateRoom.BURNING_MOVE;
                    burning1 = "🔥 " + CreateRoom.horseNames.get(i) + " 버닝!🔥";
                    System.out.println(burning1);
                    out.println(burning1);
                    out2.println(burning1);
                    burning2 = "🔥추가 " + CreateRoom.BURNING_MOVE + "칸 이동!🔥";
                    System.out.println(burning2);
                    out.println(burning2);
                    out2.println(burning2);
                }
            } else if (result.equals("무승부")) {
                positions[i] += CreateRoom.DRAW_MOVE;
                winStreaks[i] = 0;
            } else {
                winStreaks[i] = 0;
            }
        }
        return count;
    }
}
