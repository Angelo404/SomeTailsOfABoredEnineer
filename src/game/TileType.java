package game;

public enum TileType {

    Grass("grass_32_1", true), Dirt("dirt_32_1", true), Water("water_32_1", false), Stone("stone_32_1", true),
    Player("player_32", true), Projectile("projectile_32", true), GrassGateRight("grass_right_gate_32", true),
    GrassGateLeft("grass_left_gate_32", true), Emeny1("enemy_32", true), Street("street_32_1", true), Street2("street_32_2", true),
    House1("house", true);

    String textureName;
    boolean buildable;

    TileType(String textureName, boolean buildable) {

        this.textureName = textureName;
        this.buildable = buildable;
    }
}
