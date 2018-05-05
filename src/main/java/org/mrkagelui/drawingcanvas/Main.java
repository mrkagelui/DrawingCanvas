package org.mrkagelui.drawingcanvas;

import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        CanvasManager cm = new CanvasManager();
        String command;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command: ");
            command = sc.nextLine();
            if (command.startsWith("q") || command.startsWith("Q")) {
                break;
            }
            cm.setCommand(command);
            cm.update();
            cm.show(System.out);
        }
    }
}
