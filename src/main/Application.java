package main;

import javax.swing.*;
import view.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
