import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class SaveManager {
    private Character[] myCharacters;
    private String worldName, fileName;
    private int characterAmnt, characterNum;
    private String[] characterData;

    Random random = new Random();

    public SaveManager() {
        myCharacters = new Character[4];
        characterAmnt = 0;
        characterNum = 0;
    }

    public void createCharacter(String name, String role, int strength, int toughness, int intelligence, int magic, int influence) {
        myCharacters[characterAmnt] = new Character(name, role, strength, toughness, intelligence, magic, influence);
        characterAmnt++;
    }

    public boolean validate(String saveFileName) throws IOException {
        File file = new File(saveFileName);
        Scanner scan = new Scanner(file);
        int knights = 0, peasants = 0, clerics = 0, mages = 0, courtiers = 0, totalStatPoints = 0, strength = 0, toughness = 0, intelligence = 0, magic = 0, influence = 0;
        String role = "";

        characterAmnt = 0;
        
        String line = scan.nextLine();

        // If character data or nothing is on first line
        if (line.indexOf(",") != -1 || line.trim().length() == 0) { 
            scan.close(); 
            return true; 
        }

        while (scan.hasNextLine()) {
            line = scan.nextLine();
            characterData = line.split(",");

            // If any extra data
            if (characterData.length != 7) {
                scan.close();
                return true;
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
                    
                    if (strength > 10 || strength < 7 || toughness > 6 || intelligence > 6 || magic > 6 || influence > 6) {
                        scan.close();
                        return true;
                    }
                    break;

                case "Peasant": 
                    peasants++;

                    if (toughness > 10 || toughness < 7 || strength > 6 || intelligence > 6 || magic > 6 || influence > 6) {
                        scan.close();
                        return true;
                    }
                    break;

                case "Cleric": 
                    clerics++;

                    if (intelligence > 10 || intelligence < 7 || toughness > 6 || strength > 6 || magic > 6 || influence > 6) {
                        scan.close();
                        return true;
                    }
                    break;

                case "Mage": 
                    mages++;

                    if (magic > 10 || magic < 7 || toughness > 6 || intelligence > 6 || strength > 6 || influence > 6) {
                        scan.close();
                        return true;
                    }
                    break;

                case "Courtier": 
                    courtiers++;

                    if (influence > 10 || influence < 7 || toughness > 6 || intelligence > 6 || magic > 6 || strength > 6) {
                        scan.close();
                        return true;
                    }
                    break;
            }

            totalStatPoints = strength + toughness + intelligence + magic + influence;

            characterAmnt++;

            // If too many or too little points
            if (totalStatPoints > 28 || totalStatPoints < 8) {
                scan.close();
                return true;
            }

            if (strength > 10 || strength < 0 || toughness > 10 || toughness < 0 || intelligence > 10 || intelligence < 0 || magic > 10 || magic < 0 || influence > 10 || influence < 0) {
                scan.close();
                return true;
            }
        }

        // If more or less than 4 characters
        if (characterAmnt > 4  || characterAmnt < 4) {
            scan.close();
            return true;
        }

        // If more than 2 roles
        if (knights > 2 || peasants > 2 || clerics > 2 || mages > 2 || courtiers > 2) {
            scan.close();
            return true;
        }

        scan.close();
        return false;
    }

    public boolean checkForCharacter(String saveFileName, String characterName) throws IOException {
        File file = new File(saveFileName);
        Scanner scan = new Scanner(file);

        String line;

        while (scan.hasNextLine()) {
            line = scan.nextLine();

            if (line.startsWith(characterName)) { 
                characterNum -= 1;

                scan.close();
                return true; 
            }
            characterNum++;
        }

        scan.close();
        return false;
    }

    public void updateCharacter(String characterName, String role, int strength, int toughness, int intelligence, int magic, int influence) {
        myCharacters[characterNum] = new Character(characterName, role, strength, toughness, intelligence, magic, influence);
    }

    public void save() throws IOException {
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

    public void setWorldName(String worldName) { this.worldName = worldName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getCharacterRole() { return myCharacters[0].getRole(); }
}