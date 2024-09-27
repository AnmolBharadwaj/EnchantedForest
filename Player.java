public class Player {
    private String name;
    private int health;
    private int attackPower;

    public Player(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public int attack() {
        return attackPower;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public void increaseAttack(int amount) {
        attackPower += amount;
    }

    public int getHealth() {
        return health;
    }

    public String getStatus() {
        return name + " - Health: " + health + ", Attack Power: " + attackPower;
    }
}