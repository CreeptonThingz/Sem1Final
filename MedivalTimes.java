import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class MedivalTimes {
    public static void main(String[] args) throws IOException {
        boolean isActive = true, selectingCharacter, duplicateRole;
        String choice, worldName, name, role = "", characterName, fileName;
        int totalStatPoints = 0, mainStat = 0, strength = 0, toughness = 0, intelligence = 0, magic = 0, influence = 0, knights = 0, peasants = 0, clerics = 0, mages = 0, courtiers = 0;
        String[] roles = { "Knight", "Peasant", "Cleric", "Mage", "Courtier" };

        Scanner user = new Scanner(System.in);
        Random random = new Random();

        SaveManager manager = new SaveManager();

        while (isActive) {
            System.out.println("\nMenu:\n" +
                               "1. Create a new game\n" + 
                               "2. Validate a save file\n" + 
                               "3. Reroll/randomize an existing character\n" + 
                               "4. Quit\n");
            
            System.out.println("Input selection:");
            String selection = user.nextLine().trim();

            switch (selection) {
                // Create a new game with characters
                case "1":
                    System.out.println("\nEnter World Name:");
                    worldName = user.nextLine().trim();

                    manager.setWorldName(worldName);

                    for (int i = 0; i < 4; i++) {
                        selectingCharacter = true;
                        while (selectingCharacter) {
                            duplicateRole = false;
                            totalStatPoints = 0;

                            // Obtains random stats
                            while (totalStatPoints > 28 || totalStatPoints < 8) {
                                mainStat = rollMainStat(random);
                                strength = rollStat(random);
                                toughness = rollStat(random);
                                intelligence = rollStat(random);
                                magic = rollStat(random);
                                influence = rollStat(random);
                    
                                // Applies main stat to respective role unless there are too many roles
                                switch (random.nextInt(5)) {
                                    // Knight
                                    case 0:
                                        if (knights == 2) {
                                            duplicateRole = true;
                                            break;
                                        }

                                        role = roles[0];
                                        strength = mainStat;
                                        break;

                                    // Peasant
                                    case 1:
                                        if (peasants == 2) {
                                            duplicateRole = true;
                                            break;
                                        }

                                        role = roles[1];
                                        toughness = mainStat;
                                        break;

                                    // Cleric
                                    case 2:
                                        if (clerics == 2) {
                                            duplicateRole = true;
                                            break;
                                        }

                                        role = roles[2];
                                        intelligence = mainStat;
                                        break;

                                    // Mage
                                    case 3:
                                        if (mages == 2) {
                                            duplicateRole = true;
                                            break;
                                        }

                                        role = roles[3];
                                        magic = mainStat;
                                        break;

                                    // Courtier
                                    case 4:
                                        if (courtiers == 2) {
                                            duplicateRole = true;
                                            break;
                                        }

                                        role = roles[4];
                                        influence = mainStat;
                                        break;
                                }

                                totalStatPoints = strength + toughness + intelligence + magic + influence;
                            }

                            if (!duplicateRole) {
                                System.out.println("\n" + role + "," + strength + "," + toughness + "," + intelligence + "," + magic + "," + influence);
                                System.out.println("\n" + knights + "," + peasants + "," + clerics + "," + mages + "," + courtiers);

                                System.out.println("\nConfirm character? (y/n)");
                                choice = user.nextLine().trim().toLowerCase();

                                if (choice.equals("y")) { 
                                    switch (role) {
                                        case "Knight": 
                                            knights++;
                                            break;

                                        case "Peasant": 
                                            peasants++;
                                            break;
                                            
                                        case "Cleric": 
                                            clerics++;
                                            break;

                                        case "Mage": 
                                            mages++;
                                            break;

                                        case "Courtier": 
                                            courtiers++;
                                            break;
                                    }

                                    System.out.println("\nEnter character name:");
                                    name = user.nextLine().trim();

                                    manager.createCharacter(name, role, strength, toughness, intelligence, magic, influence);

                                    selectingCharacter = false; 
                                }
                            }                     
                        }
                    }
                    
                    System.out.println("\nSave game to:");
                    manager.setFileName(user.nextLine().trim());

                    manager.save();
                    System.out.println("Game Saved");

                    break;

                // Validate files
                case "2":
                    System.out.println("\nEnter save file name:");
                    if (!manager.validate(user.nextLine().trim())) {
                        System.out.println("\nSuccessfully validated files");
                    } else {
                        System.out.println("\nValidation unsuccessful, check file");
                    }

                    break;

                // Reroll character
                case "3":
                    System.out.println("\nEnter save file name:");
                    fileName = user.nextLine().trim();

                    System.out.println("\nEnter character name to reroll:");
                    characterName = user.nextLine().trim();
                    
                    if (manager.checkForCharacter(fileName, characterName)) {
                        while (totalStatPoints > 28 || totalStatPoints < 8) {
                            mainStat = rollMainStat(random);
                            strength = rollStat(random);
                            toughness = rollStat(random);
                            intelligence = rollStat(random);
                            magic = rollStat(random);
                            influence = rollStat(random);

                            switch (manager.getCharacterRole()) {
                                case "Knight": strength = mainStat;
                                case "Peasant": toughness = mainStat;
                                case "Cleric": intelligence = mainStat;
                                case "Mage": magic = mainStat;
                                case "Courtier": influence = mainStat;
                            }

                            totalStatPoints = strength + toughness + intelligence + magic + influence;
                        }

                        

                        manager.updateCharacter(characterName, manager.getCharacterRole(), strength, toughness, intelligence, magic, influence);
                    } else {
                        System.out.println("\nCharacter not found");
                    }
                    break;
                
                // Quit menu
                case "4":
                    System.out.println("\nQuitting...");
                    isActive = false;
                    break;

                default: System.out.println("\nSelection not available");
            }  
        }

        user.close();
    }

    private static int rollStat(Random random) {
        return random.nextInt(7);
    }

    private static int rollMainStat(Random random) {
        return random.nextInt(4) + 7;
    }
}
