package com.codecool.marsexploration.configuration.service;

import com.codecool.marsexploration.configuration.model.SimulationConfiguration;

public interface ConfigurationValidator {

    public boolean validate(SimulationConfiguration simulationConfiguration);
}
