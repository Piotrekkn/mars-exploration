package com.codecool.marsexploration.output.service;

import com.codecool.marsexploration.mapelements.model.Map;

import java.io.*;

public class MapFileWriterImpl implements MapFileWriter{
    public void writeMapFile(Map map, String file){
        String text = map.toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            writer.write(text);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
