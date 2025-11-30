/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

import javax.swing.JOptionPane;
import java.io.File;

/**
 *
 * @author phuoc
 */
public class OpenPdf {

    public static void openById(String id) {
        try {
            // 1. Đường dẫn tới thư mục "bill" trong Downloads
            String path = System.getProperty("user.home") + "\\Downloads\\bill\\";

            // 2. Tìm file <id>.pdf trong thư mục đó
            File file = new File(path + id + ".pdf");

            if (file.exists()) {
                Process p = Runtime.getRuntime().exec(
                        "rundll32 url.dll,FileProtocolHandler \"" + file.getAbsolutePath() + "\""
                );
            } else {
                JOptionPane.showMessageDialog(null, "File does not exist at: " + file.getAbsolutePath());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
