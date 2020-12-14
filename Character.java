public class Character {
    private String name, role;
    private int strength, toughness, intelligence, magic, influence;

    public Character(String name, String role, int strength, int toughness, int intelligence, int magic, int influence) {
        this.name = name;
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

}
