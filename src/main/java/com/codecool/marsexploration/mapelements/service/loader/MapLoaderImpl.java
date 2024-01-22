package com.codecool.marsexploration.mapelements.service.loader;

import com.codecool.marsexploration.mapelements.model.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapLoaderImpl implements MapLoader {
    @Override
    public Map loadMap(String path) {

        List<String[]> list = new ArrayList<>();
        File myObj = new File(path);

        try (Scanner reader = new Scanner(myObj)) {

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] ary = data.split("");
                list.add(ary);


            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String[][] representation = new String[list.size()][];
        int i = 0;
        for (String[] str: list             ) {
            representation[i++]=str;
        }
        return new Map(representation);
    }
}
