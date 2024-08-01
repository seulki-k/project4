package project4.server;

public class ClearConsole {
    public static void clearConsole() {
        // 콘솔 화면을 지우기 위한 ANSI 이스케이프 코드 사용
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
