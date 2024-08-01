package project4.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DetermineOutcome {
    public static String determineOutcome(List<String> choices) {
        List<String> winners = new ArrayList<>();
        Set<String> uniqueChoices = new HashSet<>(choices);

        // 모든 선택이 서로 같을 경우 무승부 처리
        if (uniqueChoices.size() == 1) {
            return "무승부";
        }

        // 모든 선택이 서로 다를 경우 무승부 처리
        if (uniqueChoices.size() == CreateRoom.NUM_PLAYERS) {
            return "무승부";
        }

        // 승리 조건을 평가합니다.
        String[] choiceArray = choices.toArray(new String[0]);
        boolean[] isWinner = new boolean[CreateRoom.NUM_PLAYERS];

        for (int i = 0; i < CreateRoom.NUM_PLAYERS; i++) {
            boolean winner = true;
            for (int j = 0; j < CreateRoom.NUM_PLAYERS; j++) {
                if (i != j) {
                    String result = CreateRoom.getMatchResult(choiceArray[i], choiceArray[j]);
                    if (result.equals("패배")) {
                        winner = false;
                        break;
                    }
                }
            }
            isWinner[i] = winner;
        }

        // 결과를 승리로 설정
        for (int i = 0; i < CreateRoom.NUM_PLAYERS; i++) {
            if (isWinner[i]) {
                winners.add(CreateRoom.horseNames.get(i) + " 승리");
            }
        }

        if (winners.isEmpty()) {
            return "무승부";
        } else {
            return String.join(", ", winners);
        }
    }
}
