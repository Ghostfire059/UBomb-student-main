package fr.ubx.poo.game;

import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.decor.bomb.BombNbDec;
import fr.ubx.poo.model.decor.bomb.BombNbInc;
import fr.ubx.poo.model.decor.bomb.BombRngDec;
import fr.ubx.poo.model.decor.bomb.BombRngInc;
import fr.ubx.poo.model.decor.door.DoorDown;
import fr.ubx.poo.model.decor.door.DoorUpClosed;
import fr.ubx.poo.model.decor.door.DoorUpOpened;

import java.util.Hashtable;
import java.util.Map;

public class WorldBuilder {
    private final Map<Position, Decor> grid = new Hashtable<>();

    private WorldBuilder() {
    }

    public static Map<Position, Decor> build(WorldEntity[][] raw, Dimension dimension) {
        WorldBuilder builder = new WorldBuilder();
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                Position pos = new Position(x, y);
                Decor decor = processEntity(raw[y][x]);
                if (decor != null)
                    builder.grid.put(pos, decor);
            }
        }
        return builder.grid;
    }

    private static Decor processEntity(WorldEntity entity) {
        switch (entity) {
            case Stone:
                return new Stone();
            case Tree:
                return new Tree();
            case Box:
            	return new Box();
            case Princess:
            	return new Princess();
            case Heart:
            	return new Heart();
            case Key:
            	return new Key();
            case DoorPrevOpened:
            	return new DoorDown();
            case DoorNextClosed:
            	return new DoorUpClosed();
            case DoorNextOpened:
            	return new DoorUpOpened();
            case BombNumberInc:
            	return new BombNbInc();
            case BombNumberDec:
            	return new BombNbDec();
            case BombRangeInc:
            	return new BombRngInc();
            case BombRangeDec:
            	return new BombRngDec();
            default:
                return null;
        }
    }
}
