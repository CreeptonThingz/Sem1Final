import java.util.Scanner;
import java.io.*;

public class MedivalTimes {
    public static void main(String[] args) throws IOException {
        boolean isActive = true, selectingCharacter;
        String choice, fileName, gameCharacterName;
        int selectedRole, mainStat = 0;
        String[] roles = { "Knight", "Peasant", "Cleric", "Mage", "Courtier" };
        String[] mainStats = { "strength", "toughness", "intelligence", "magic", "influence"};
        GameCharacter gameCharacter;

        Scanner user = new Scanner(System.in);

        GameManager manager = new GameManager();

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
                    manager.setWorldName(user.nextLine().trim());

                    for (int i = 0; i < 4; i++) {
                        System.out.println("\n1. Knight\n" + 
                                           "2. Peasant\n" + 
                                           "3. Cleric\n" + 
                                           "4. Mage\n" + 
                                           "5. Courtier");

                        System.out.println("\nEnter Character " + (i+1) + " Role:");
                        selectedRole = Integer.parseInt(user.nextLine());

                        while (selectedRole > 5 || selectedRole < 1 || manager.checkRoleCount(selectedRole)) {
                            System.out.println("\nRole does not exist or exceeds the role limit, try again");
                            selectedRole = Integer.parseInt(user.nextLine());
                        }
                        
                        System.out.println("\nEnter " + mainStats[selectedRole-1] + " stat number (7-10):");
                        mainStat = Integer.parseInt(user.nextLine());

                        while (mainStat > 10 || mainStat < 7) {
                            System.out.println("\nStat number is out of range, try again");
                            mainStat = Integer.parseInt(user.nextLine());
                        }

                        selectingCharacter = true;
                        switch (selectedRole) {
                            case 1:
                                while (selectingCharacter) {
                                    gameCharacter = GameCharacter.knight(mainStat);

                                    System.out.println("\n" + gameCharacter.presentCharacter() + "\n");

                                    System.out.println("Accept character? (y/n)");
                                    choice = user.nextLine().trim();

                                    if (choice.equals("y")) {
                                        selectingCharacter = false;
                                        
                                        System.out.println("\nName your character:");
                                        gameCharacter.setName(user.nextLine().trim());

                                        manager.addCharacter(gameCharacter);
                                    }
                                }
                                break;

                            case 2:
                                while (selectingCharacter) {
                                    gameCharacter = GameCharacter.peasant(mainStat);

                                    System.out.println(gameCharacter + "\n");

                                    System.out.println("Accept character? (y/n)");
                                    choice = user.nextLine().trim();

                                    if (choice.equals("y")) {
                                        selectingCharacter = false;

                                        System.out.println("Name your character:");
                                        gameCharacter.setName(user.nextLine().trim());

                                        manager.addCharacter(gameCharacter);
                                    }
                                }
                                break;

                            case 3:
                                while (selectingCharacter) {
                                    gameCharacter = GameCharacter.cleric(mainStat);

                                    System.out.println(gameCharacter + "\n");

                                    System.out.println("Accept character? (y/n)");
                                    choice = user.nextLine().trim();

                                    if (choice.equals("y")) {
                                        selectingCharacter = false;

                                        System.out.println("Name your character:");
                                        gameCharacter.setName(user.nextLine().trim());

                                        manager.addCharacter(gameCharacter);
                                    }
                                }
                                break;

                            case 4:
                                while (selectingCharacter) {
                                    gameCharacter = GameCharacter.mage(mainStat);

                                    System.out.println(gameCharacter + "\n");

                                    System.out.println("Accept character? (y/n)");
                                    choice = user.nextLine().trim();

                                    if (choice.equals("y")) {
                                        selectingCharacter = false;

                                        System.out.println("Name your character:");
                                        gameCharacter.setName(user.nextLine().trim());

                                        manager.addCharacter(gameCharacter);
                                    }
                                }
                                break;

                            case 5:
                                while (selectingCharacter) {
                                    gameCharacter = GameCharacter.courtier(mainStat);

                                    System.out.println(gameCharacter + "\n");

                                    System.out.println("Accept character? (y/n)");
                                    choice = user.nextLine().trim();

                                    if (choice.equals("y")) {
                                        selectingCharacter = false;

                                        System.out.println("Name your character:");
                                        gameCharacter.setName(user.nextLine().trim());

                                        manager.addCharacter(gameCharacter);
                                    }
                                }
                                break;  
                        }
                    }

                    manager.resetCharacterAmount();
                    manager.resetRoleCount();
                    
                    System.out.println("\nSave game to:");
                    manager.save(user.nextLine().trim());
                    System.out.println("\nGame Saved");

                    break;

                // Validate files
                case "2":
                    System.out.println("\nEnter save file name:");
                    if (!manager.validate(user.nextLine().trim())) {
                        System.out.println("\nSuccessfully validated files");
                    } else {
                        System.out.println("\nValidation unsuccessful, check file");
                    }

                    manager.resetCharacterAmount();
                    manager.resetRoleCount();
                    break;

                // Reroll character
                case "3":
                    System.out.println("\nEnter save file name:");
                    fileName = user.nextLine().trim();

                    System.out.println("\nEnter character name to reroll:");
                    gameCharacterName = user.nextLine().trim();

                    if (!manager.reRollCharacter(fileName, gameCharacterName)) {
                        System.out.println("\nCharacter not found");
                    }

                    manager.resetCharacterAmount();
                    manager.save(fileName);
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
}
