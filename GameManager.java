import java.util.Scanner;
import java.io.*;

public class GameManager {
    private String worldName;
    private int characterAmnt;
    private String[] characterData;
    private int[] roleCounts;
    private GameCharacter[] myCharacters;

    public GameManager() {
        myCharacters = new GameCharacter[4];
        roleCounts = new int[5];
    }

    public void addCharacter(GameCharacter gameCharacter) {
        myCharacters[characterAmnt++] = gameCharacter;

        switch (gameCharacter.getRole()) {
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

    public boolean validate(String saveFileName) throws IOException {
        File file = new File(saveFileName);
        Scanner scan = new Scanner(file);

        int knights = 0, peasants = 0, clerics = 0, mages = 0, courtiers = 0;
        int totalStatPoints = 0, strength = 0, toughness = 0, intelligence = 0, magic = 0, influence = 0;
        String role = "";
        
        String line = scan.nextLine();

        // If character data or nothing is on first line
        if (line.indexOf(",") >= 0 || line.trim().length() == 0) { 
            scan.close(); 
            return false; 
        }

        while (scan.hasNextLine()) {
            line = scan.nextLine();
            characterData = line.split(",");

            // If any extra data
            if (characterData.length != 7) {
                scan.close();
                return false;
            }

            role = characterData[1];
            strength = Integer.parseInt(characterData[2]);
            toughness = Integer.parseInt(characterData[3]);
            intelligence = Integer.parseInt(characterData[4]);
            magic = Integer.parseInt(characterData[5]);
            influence = Integer.parseInt(characterData[6]);

            // Adds to role and checks if stats are correct for each role
            switch (role) {
                case "Knight": 
                    knights++;
                    
                    if (strength > 10 || strength < 7 || toughness > 6 || toughness < 0 || intelligence > 6 || magic > 6 || influence > 6 || knights > 2) {
                        scan.close();
                        return false;
                    }
                    break;

                case "Peasant": 
                    peasants++;

                    if (toughness > 10 || toughness < 7 || strength > 6 || intelligence > 6 || magic > 6 || influence > 6 || peasants > 2) {
                        scan.close();
                        return false;
                    }
                    break;

                case "Cleric": 
                    clerics++;

                    if (intelligence > 10 || intelligence < 7 || toughness > 6 || strength > 6 || magic > 6 || influence > 6 || clerics > 2) {
                        scan.close();
                        return false;
                    }
                    break;

                case "Mage": 
                    mages++;

                    if (magic > 10 || magic < 7 || toughness > 6 || intelligence > 6 || strength > 6 || influence > 6 || mages > 2) {
                        scan.close();
                        return false;
                    }
                    break;

                case "Courtier": 
                    courtiers++;

                    if (influence > 10 || influence < 7 || toughness > 6 || intelligence > 6 || magic > 6 || strength > 6 || courtiers > 2) {
                        scan.close();
                        return false;
                    }
                    break;
            }

            characterAmnt++;
        }

        // If more or less than 4 characters
        if (characterAmnt > 4  || characterAmnt < 4) {
            scan.close();
            return true;
        }

        scan.close();
        return false;
    }

    public boolean reRollCharacter(String saveFileName, String expectedName) throws IOException {
        File file = new File(saveFileName);
        Scanner scan = new Scanner(file);

        String line = scan.nextLine();
        setWorldName(line);

        // Reads data
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            characterData = line.split(",");

            myCharacters[characterAmnt] = new GameCharacter(characterData[1], Integer.parseInt(characterData[2]), Integer.parseInt(characterData[3]), Integer.parseInt(characterData[4]), Integer.parseInt(characterData[5]), Integer.parseInt(characterData[6]));
            myCharacters[characterAmnt].setName(characterData[0]);
            characterAmnt++;
        }

        for (int i = 0; i < 4; i++) {
            if (myCharacters[i].getName().equals(expectedName)) {
                myCharacters[i].reRollStats();
                scan.close();
                return true;
            }
        }

        scan.close();
        return false;
    }

    public void save(String fileName) throws IOException {
        PrintWriter output = new PrintWriter(fileName);

        output.print(toString());
        output.close();
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