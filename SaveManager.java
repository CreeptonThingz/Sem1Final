import java.util.Random;
import java.io.*;

public class SaveManager {
    private Character[] myCharacters;
    private String worldName, fileName = "game.txt";
    private int characterAmnt;

    Random random = new Random();

    public SaveManager() {
        myCharacters = new Character[4];
    }

    public void newGame(String worldName) {
        this.worldName = worldName;
        characterAmnt = 0;
    }

    public void createCharacter(String name, String role, int strength, int toughness, int intelligence, int magic, int influence) {
        myCharacters[characterAmnt] = new Character(name, role, strength, toughness, intelligence, magic, influence);
        characterAmnt++;
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
}
