package fr.mdbs.thetreasuremap.domain.model.movement;

import fr.mdbs.thetreasuremap.domain.model.movement.command.MoveForwardCommand;
import fr.mdbs.thetreasuremap.domain.model.movement.command.Executable;
import fr.mdbs.thetreasuremap.domain.model.movement.command.TurnLeftCommand;
import fr.mdbs.thetreasuremap.domain.model.movement.command.TurnRightCommand;

import java.util.HashMap;
import java.util.Map;

public final class MovementCommandFactory {
    private static final Map<Movement, Executable> commandMap = new HashMap<>();

    static {
        commandMap.put(Movement.A, new MoveForwardCommand());
        commandMap.put(Movement.G, new TurnLeftCommand());
        commandMap.put(Movement.D, new TurnRightCommand());
    }

    public static Executable getCommand(Movement movement) {
        return commandMap.get(movement);
    }
}

