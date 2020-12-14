import java.util.Scanner;
import java.io.IOError;
import java.io.IOException;
import java.util.Random;

public class MedivalTimes {
    public static void main(String[] args) throws IOException {
        boolean isActive = true, selectingCharacter, duplicateRole;
        String choice, worldName, name, role = "";
        int totalStatPoints = 0, mainStat = 0, strength = 0, toughness = 0, intelligence = 0, magic = 0, influence = 0, knights = 0, peasants = 0, clerics = 0, mages = 0, courtiers = 0;
        String[] roles = { "Knight", "Peasant", "Cleric", "Mage", "Courtier" };

        Random random = new Random();
        Scanner user = new Scanner(System.in);

        SaveManager manager = new SaveManager();

        while (isActive) {
            System.out.println("\n1. Create a new game\n" + 
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

                    System.out.println("Before");
                    manager.setWorldName(worldName);
                    System.out.println("After");

                    for (int i = 0; i < 4; i++) {
                        selectingCharacter = true;
                        System.out.println("Selecting character");
                        while (selectingCharacter) {
                            duplicateRole = false;
                            System.out.println("No duplicate roles");

                            while (totalStatPoints > 28 || totalStatPoints < 8) {
                                mainStat = random.nextInt(4) + 7;
                                strength = random.nextInt(7);
                                toughness = random.nextInt(7);
                                intelligence = random.nextInt(7);
                                magic = random.nextInt(7);
                                influence = random.nextInt(7);
            
                                totalStatPoints = mainStat + strength + toughness + intelligence + magic + influence;
                            }

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
                                    role = roles[3];
                                    influence = mainStat;
                                    break;
                            }
        
                            if (duplicateRole = false) {
                                System.out.println("\n" + role + "," + strength + "," + toughness + "," + intelligence + "," + magic + "," + influence);
    
                                System.out.println("Confirm character? (y/n)");
                                choice = user.nextLine().trim().toLowerCase();
        
                                if (choice.equals("y")) { 
                                    switch (role) {
                                        case "Knight": knights++;
                                        case "Peasant": peasants++;
                                        case "Cleric": clerics++;
                                        case "Mage": mages++;
                                        case "Courtier": courtiers++;
                                    }
    
                                    System.out.println("Enter character name:");
                                    name = user.nextLine().trim();
    
                                    manager.createCharacter(name, role, strength, toughness, intelligence, magic, influence);
    
                                    selectingCharacter = false; 
                                }
                            }
                        }
                    }
                    
                    manager.save();
                    System.out.println("Game Saved");

                    break;

                case "2":
                    System.out.println("Selected 2");
                    break;

                case "3":
                    System.out.println("Selected 3");
                    break;

                case "4":
                    System.out.println("Quitting...");
                    isActive = false;
                    break;

                default: System.out.println("Selection not available");
            }

            
        }

        user.close();
    }
}
