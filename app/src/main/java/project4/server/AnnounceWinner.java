package project4.server;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class AnnounceWinner {
    public static void announceWinner(int[] positions, List<String> horseNames, PrintStream out, PrintStream out2) {
        List<String> winners = new ArrayList<>();
        for (int i = 0; i < CreateRoom.NUM_PLAYERS; i++) {
            if (positions[i] >= CreateRoom.FINISH_LINE) {
                winners.add(horseNames.get(i));
            }
        }

        if (winners.size() > 1) {
            System.out.println("\n경기가 종료되었습니다!");
            System.out.println("\n🎉승자는 " + String.join(", ", winners) + "입니다!🎉");

            out.println("\n경기가 종료되었습니다!");
            out.println("\n🎉승자는 " + String.join(", ", winners) + "입니다!🎉");

            out2.println("\n경기가 종료되었습니다!");
            out2.println("\n🎉승자는 " + String.join(", ", winners) + "입니다!🎉");
        } else if (winners.size() == 1) {
            System.out.println("\n경기가 종료되었습니다!");
            System.out.println("\n" + "🎉" + winners.get(0) + "가(이) 승리했습니다!🎉");

            out.println("\n경기가 종료되었습니다!");
            out.println("\n" + "🎉" + winners.get(0) + "가(이) 승리했습니다!🎉");

            out2.println("\n경기가 종료되었습니다!");
            out2.println("\n" + "🎉" + winners.get(0) + "가(이) 승리했습니다!🎉");
        } else {
            System.out.println("\n경기가 종료되었습니다!");
            System.out.println("\n🎉무승부입니다!🎉");

            out.println("\n경기가 종료되었습니다!");
            out.println("\n🎉무승부입니다!🎉");

            out2.println("\n경기가 종료되었습니다!");
            out2.println("\n🎉무승부입니다!🎉");
        }
    }
}
