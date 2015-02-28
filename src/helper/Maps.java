/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Euaggelos
 */
public class Maps {

    //public static int tmpMap[][] = new int[40][60];
    public static int[][] LoadFromFile() {
        int tmpMap[][] = new int[40][120];
        int nrow = 0, ncol = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("testme.txt"))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.isEmpty()) {
                    continue;
                }
                String[] values = currentLine.trim().split(" ");
                //System.out.println(values[1]);
                for (String string : values) {
                    if (!string.isEmpty()) {
                        int id = Integer.parseInt(string);
                        //System.out.println(id);
                        tmpMap[nrow][ncol] = id;
                        ncol += 1;
                    }

                }
                ncol = 0;
                nrow += 1;
            }
        } catch (IOException e) {

        }
        return tmpMap;
    }

}
