import java.util.Scanner;
import java.io.*;

public class GameManager {
    private String worldName, line;
    private int characterAmnt;
    private String[] lineData;
    private int[] roleCounts;
    private GameCharacter[] myCharacters;

    public GameManager() {
        myCharacters = new GameCharacter[4];
        roleCounts = new int[5];
    }

    public void addCharacter(GameCharacter gameCharacter) {
        myCharacters[characterAmnt++] = gameCharacter;

        addToRole(gameCharacter.getRole());
    }

    public boolean validate(String saveFileName) throws IOException {
        File file = new File(saveFileName);
        Scanner scan = new Scanner(file);

        boolean hasError = false;
        
        line = scan.nextLine();

        // If character data or nothing is on first line
        if (line.indexOf(",") >= 0 || line.trim().length() == 0) { hasError = true; }

        while (scan.hasNextLine()) {
            // If extra or not enough data
            if (scan.nextLine().split(",").length != 7) { hasError = true; }
            characterAmnt++;
        }

        // If more or less than 4 characters
        if (characterAmnt > 4  || characterAmnt < 4) { hasError = true; }

        // Loads file to check each character stats
        if (!hasError) {
            resetCharacterAmount();
            load(saveFileName);

            while (characterAmnt < myCharacters.length) {
                // Use character to check their own stats
                if (!myCharacters[characterAmnt].checkCharacterStats()) { hasError = true; }
    
                addToRole(myCharacters[characterAmnt++].getRole());
            }
        }

        // If too many roles
        if (roleCounts[0] > 2 || roleCounts[1] > 2 || roleCounts[2] > 2 || roleCounts[3] > 2 || roleCounts[4] > 2) { hasError = true; }

        scan.close();
        resetCharacterAmount();
        resetRoleCount();
        return hasError;
    }

    public boolean reRollCharacter(String expectedName) throws IOException {
        for (int i = 0; i < 4; i++) {
            if (myCharacters[i].getName().equals(expectedName)) {
                myCharacters[i].reRollStats();

                return true;
            }
        }

        return false;
    }

    public void save(String fileName) throws IOException {
        PrintWriter output = new PrintWriter(fileName);

        output.print(toString());
        output.close();
        resetCharacterAmount();
        resetRoleCount();
    }

    public void load(String fileName) throws IOException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);

        setWorldName(scan.nextLine());

        while (scan.hasNextLine()) {
            lineData = scan.nextLine().split(",");

            myCharacters[characterAmnt] = new GameCharacter(lineData[1], Integer.parseInt(lineData[2]), Integer.parseInt(lineData[3]), Integer.parseInt(lineData[4]), Integer.parseInt(lineData[5]), Integer.parseInt(lineData[6]));
            myCharacters[characterAmnt++].setName(lineData[0]);
        }

        resetCharacterAmount();
        scan.close();
    }

    public void addToRole(String role) {
        switch (role) {
            case "Knight":
                roleCounts[0]++;
                break;
            
            case "Peasant":
                roleCounts[1]++;
                break;

            case "Cleric":
                roleCounts[2]++;
                break;

            case "Mage":
                roleCounts[3]++;
                break;

            case "Courtier":
                roleCounts[4]++;
                break;
        }
    }

    public String toString() {
        String str = worldName + "\n";

        for (int i = 0; i < 4; i++) {
            str += myCharacters[i] + "\n";
        }

        return str;
    }

    public void resetCharacterAmount() { characterAmnt = 0; }
    public void resetRoleCount() { 
        for (int i = 0; i < 5; i++) {
            roleCounts[i] = 0;
        }
    }

    public boolean checkRoleCount(int selectedRole) { return roleCounts[selectedRole-1] >= 2; }
    public int getRoleCount(int num) { return roleCounts[num]; }

    public void setWorldName(String worldName) { this.worldName = worldName; }
}