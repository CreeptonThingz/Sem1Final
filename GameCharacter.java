import java.util.Random;

public class GameCharacter {
    private String name, role;
    private int strength, toughness, intelligence, magic, influence;

    private static Random random = new Random();

    public GameCharacter(String role, int strength, int toughness, int intelligence, int magic, int influence) {
        this.role = role;
        this.strength = strength;
        this.toughness = toughness;
        this.intelligence = intelligence;
        this.magic = magic;
        this.influence = influence;
    }

    public String toString() {
        return name + "," + role + "," + strength + "," + toughness + "," + intelligence + "," + magic + ","  + influence;
    }

    public String presentCharacter() {
        return "Strength: " + strength + "\n" + 
               "Toughness: " + toughness + "\n" + 
               "Intelligence: " + intelligence + "\n" + 
               "Magic: " + magic + "\n"  + 
               "Influence: " + influence;
    }

    public void setName(String name) { this.name = name; }
        
    public String getName() { return name; }
    public String getRole() { return role; }

    // Role presets
    public static GameCharacter knight(int strength) {
        int[] randomStats = randomStats(strength);

        return new GameCharacter("Knight", strength, randomStats[0], randomStats[1], randomStats[2], randomStats[3]); 
    }

    public static GameCharacter peasant(int toughness) {
        int[] randomStats = randomStats(toughness);

        return new GameCharacter("Peasant", randomStats[0], toughness, randomStats[1], randomStats[2], randomStats[3]);
    }

    public static GameCharacter cleric(int intelligence) {
        int[] randomStats = randomStats(intelligence);

        return new GameCharacter("Cleric", randomStats[0], randomStats[1], intelligence, randomStats[2], randomStats[3]);
    }

    public static GameCharacter mage(int magic) {
        int[] randomStats = randomStats(magic);

        return new GameCharacter("Mage", randomStats[0], randomStats[1], randomStats[2], magic, randomStats[3]);
    }

    public static GameCharacter courtier(int influence) {
        int[] randomStats = randomStats(influence);

        return new GameCharacter("Courtier", randomStats[0], randomStats[1], randomStats[2], randomStats[3], influence);
    }

    // Validation for each role - check that main stat is the only main stat - check that no stat is below 0
    public boolean checkCharacterStats() {
        switch (role) {
            case "Knight":
                if (strength > 10 || strength < 7 || toughness > 6 || intelligence > 6 || magic > 6 || influence > 6 ) { return false; }
                if (toughness < 0 || intelligence < 0 || magic < 0 || influence < 0) { return false; }
        
                return true;
            case "Peasant":
                if (toughness > 10 || toughness < 7 || strength > 6 || intelligence > 6 || magic > 6 || influence > 6) { return false; }
                if (strength < 0 || intelligence < 0 || magic < 0 || influence < 0) { return false; }
        
                return true;
            case "Cleric":
                if (intelligence > 10 || intelligence < 7 || toughness > 6 || strength > 6 || magic > 6 || influence > 6) { return false; }
                if (toughness < 0 || strength < 0 || magic < 0 || influence < 0) { return false; }
        
                return true;
            case "Mage":
                if (magic > 10 || magic < 7 || toughness > 6 || intelligence > 6 || strength > 6 || influence > 6) { return false; }
                if (toughness < 0 || intelligence < 0 || strength < 0 || influence < 0) { return false; }

                return true;
            case "Courtier":
                if (influence > 10 || influence < 7 || toughness > 6 || intelligence > 6 || magic > 6 || strength > 6) { return false; }
                if (toughness < 0 || intelligence < 0 || magic < 0 || strength < 0) { return false; }
        
                return true;
            default: return false;
        }
   }

    // Randomization of stats
    private static int[] randomStats(int mainStat) {
        int[] remainingStats = new int[4];
        int totalStatPoints = 0;

        while (totalStatPoints > 28 || totalStatPoints < 8) {
            for (int i = 0; i < 4; i++) { remainingStats[i] = random.nextInt(7); }

            totalStatPoints = mainStat + remainingStats[0] + remainingStats[1] + remainingStats[2] + remainingStats[3];
        }

        return remainingStats;
    }

    public void reRollStats() {
        int[] newStats;

        switch (role) {
            case "Knight":
                strength = random.nextInt(4) + 7;
                newStats = randomStats(strength);

                toughness = newStats[0];
                intelligence = newStats[1];
                magic = newStats[2];
                influence = newStats[3];
                
                break;

            case "Peasant":
                toughness = random.nextInt(4) + 7;
                newStats = randomStats(toughness);

                strength = newStats[0];
                intelligence = newStats[1];
                magic = newStats[2];
                influence = newStats[3];
                break;

            case "Cleric":
                intelligence = random.nextInt(4) + 7;
                newStats = randomStats(intelligence);

                toughness = newStats[0];
                strength = newStats[1];
                magic = newStats[2];
                influence = newStats[3];
                break;

            case "Mage":
                magic = random.nextInt(4) + 7;
                newStats = randomStats(magic);

                toughness = newStats[0];
                intelligence = newStats[1];
                strength = newStats[2];
                influence = newStats[3];

                break;

            case "Courtier":
                influence = random.nextInt(4) + 7;
                newStats = randomStats(influence);

                toughness = newStats[0];
                intelligence = newStats[1];
                magic = newStats[2];
                strength = newStats[3];

                break;
        }
    }

}
