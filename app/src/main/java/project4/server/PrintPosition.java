package project4.server;

import java.io.PrintStream;

public class PrintPosition {
    public static void printPosition(int[] positions, PrintStream out, PrintStream out2) {
        StringBuilder[] tracks = new StringBuilder[CreateRoom.NUM_PLAYERS];
        for (int i = 0; i < CreateRoom.NUM_PLAYERS; i++) {
            tracks[i] = new StringBuilder();
        }


        for (int i = 0; i <= CreateRoom.FINISH_LINE; i++) {
            for (int j = 0; j < CreateRoom.NUM_PLAYERS; j++) {
                if (i == positions[j]) {
                    tracks[j].append("🏇");
                } else {
                    tracks[j].append(" ");
                }
            }
        }

        for (int i = 0; i < CreateRoom.NUM_PLAYERS; i++) {
            tracks[i].append("🏁");
        }

        System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        out2.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");

        for (int i = 0; i < CreateRoom.NUM_PLAYERS; i++) {
            System.out.println(CreateRoom.horseNames.get(i) + "[" + CreateRoom.choices.get(i) + "]" + " " + tracks[i].toString());
            out.println(CreateRoom.horseNames.get(i) + "[" + CreateRoom.choices.get(i) + "]" + " " + tracks[i].toString());
            out2.println(CreateRoom.horseNames.get(i) + "[" + CreateRoom.choices.get(i) + "]" + " " + tracks[i].toString());
            System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
            out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
            out2.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        }
    }
}
