import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.ArrayList;
import java.nio.charset.MalformedInputException;

/**
 * Created by Nore5515 on 4/16/2017.
 */

//First things first, I wanna list out what my plan is.
    //1     I want to have a gui
    //2     I want to have a skill menu, much like Fallout:NV
    //3     I want to have adventuring by clicking the "Adventure" button, and then clicking the area to adventure.
    //4     I want to have to manage my time between sleeping (no sleep means stat debuffs), fishing (to get food, if you don't have any), exploring (to get stuff from the carrier) and crafting (which could take hours).
    //5     I want a crafting system like C:DDA, where you have to have a tool with proper Tool Qualities (such as a hammer having Hammering 2) to craft your stuff
    //6     I want to be able to talk to other factions, and perhaps do quests for them.
    //7     I want an end-game, where eventually you have to pick a faction.
    //8     I want there to be a lot of role-play involved, such as dialoge options and traits
    //9     I want to unlock new areas after exploring a certain area multiple times
    //10    I want to have multiple branches of areas to explore, so not linear.
    //11    I want it so that when you level up, skills you used a lot are easier to level up
    //12    I want different classes to choose from

    //Combat
        //Your HP is found by taking your Fitness and multiplying it by 10
        //There are 4 types of status effects, each with their own resistance (each point of Fitness increases all resist by 10%, individual classes have different resists)
            //Disease; Obtained by rusted weapons, bad food or not sanitizing injuries. Reduces all stats, occasionally deals damage. Removed by antibiotics, sleeping 20 hours or seeing a doctor.
            //Bleeding; Obtained by jagged weapons, weapon crits or working with sharp metals. Deals damage every turn. Removed by [Cloth 1] <Medicine 3> or [Cloth 2].
            //Radiation; Obtained by venturing too far deep into the carrier or working with radioactive materials. Reduces all stats, damages a lot. Removed by Prussian Blue, Iodine Pills <Medicine 7> or seeing a doctor.
            //Poisoned; Obtained by venomous animals, poison darts or inhaling toxic gas. Dramatically deals damage quickly. Removed by Activated Charcoal <Medicine 6>, Antidote (50% + 15% for each point of Medicine).
    //Locations (Loot gen is handled by spawning until you get a failure, each success reduces spawn chance by 5%)
        //Surrounding waters
            //Encounters
                //Locked Box (10%)
                    //"You find a floating metal box, sealed shut by a lock."
                    //Lockpick 35%
                    //Fitness 5 to break open
                    //50% Chunk of Steel
                    //10% Wires (2)
                    //15% Hammer
                    //15% Rope
                    //35% Driftwood
                    //15% Shirt
                    //15% Pants
                    //15% Underwear
                    //25% Alcohol
                    //15% Shoes
                    //15% Hat
                //Driftwood (20%)
                    //"A huge plank of driftwood catches your eye, perhaps there is something there of value?"
                    //Driftwood
                    //35% Chunk of Steel
                    //20% Cloth Scraps
    //Item Bank
        //Chunk of Steel [Acc -2] [Blunt 1]
        //Metal Pipe [Acc 2] [Blunt 5]
        //Hollow Munition Round
        //Computer Board [Wiring 3]
        //Wires [Wiring 2]
        //Welder [Welding 2]
        //Hammer [Hammering 2] [Acc 1] [Blunt 3]
        //Nails
        //Sheet Metal [Boat 2]
        //9mm Pistol [Acc 4] [Piercing 8]
        //9mm Round
        //Rope [Fuel 1] [Rope 3]
        //Drift wood [Fuel 1] [Boat 1] [Acc -2] [Blunt 4]
        //Refined Wood [Fuel 2] [Boat 1]
        //Saw [Sawing 2]
        //Sledge Hammer [Destroyer 2] [Acc -4] [Blunt 12]
        //Crude Hammer [Hammering 1] [Acc 0] [Blunt 3]
        //Crude Welder [Welding 1]
        //Crude Saw [Sawing 1]
        //Pipe Pistol [Acc 2] [Piercing 6]
        //Crude 9mm Round
        //Alcohol [Sanitation 1] [Moral 2]
        //Fish
        //Lighter [Fire Starting 1]
        //Fire starter
        //Cloth Scraps [Fuel 1] [Cloth 1]
        //Shirt [Fuel 1] [Cloth 1] [Def 1]
        //Pants [Fuel 1] [Cloth 1] [Def 1]
        //Shoes [Fuel 1]
        //Hat [Fuel 1]
        //Underwear [Fuel 1]
        //Knife [Cutting 2] [Acc 0] [Slash 3]
        //Crude Knife [Cutting 1] [Acc -1] [Slash 2]
        //Crude Wires [Wiring 1]
        //String [Rope 1]
        //Cloth Rope [Fuel 1] [Rope 2]
        //Thick Cloth [Fuel 1] [Cloth 2]
        //Prussian Blue [Anti-Rad 3]
        //Iodine Pills [Anti-Rad 2]
        //Antidote [Antidote 2]
        //Charcoal [Antidote 1]
        //Activated Charcoal [Antidote 3]
        //Crude Pot [Cooking 1] [Acc -2] [Blunt 5]
        //Pot [Cooking 2] [Acc -1] [Blunt 4]
        //Reinforced Pot [Cooking 3] [Acc 0] [Blunt 4]
        //Salt [Sanitation 1] [Acc 0] [Stun 2]
        //Hydrochloric Acid [Sanitation 2] [Acid 2] [Acc 0] [Stun 4]
        //Raw Clam [Nutrition 1] [Hydration 1]
        //Raw Oyster [Nutrition 1] [Hydration 1]
        //Cooked Clam [Nutrition 2] [Hydration 1]
        //Cooked Oyster [Nutrition 2] [Hydration 1]
        //Shell
        //Calcium Carbonate [Acid 1]
        //Sand [Acc -1] [Stun 1]
        //Mother of Vinegar [Hydration -2] [Acc 0] [Stun 2]
        //Seaweed [Nutrition 1] [Hydration -1]
    //Crafting Recipes
        //\(Idle true or not)time/<SKILLS NEEDED> Items Needed (Count of Item), [Tool Quality Needed], [(CONSUMED) Tool is lost in crafting!] => Product (Count)
        //\1 Hour/ <Guns 3> <Fabrication 3> Pipe (1), Chunk of Steel (2), [Sawing 2], [Welding 2] => Pipe Pistol (1)
        //\1 Hour/ <Fabrication 1> Sheet Metal (1), [Sawing 2] => Chunk of Steel (3)
        //\2 Hours/ Chunk of Steel (2), [(CONSUMED) Rope 2], [Sawing 1], [Welding 2] => Saw (1)
        //\1 Hour/ <Chemistry 1> Salt Water (Nearby) [Cooking 1] => Salt (1), Water (1)
        //\2 Hours/ <Chemistry 3> Shell (5) [Cooking 2] => Calcium Carbonate (1)
        //<Chemistry 5> Calcium Carbonate (1)
        //\(IDLE)48 Hours/ <Chemistry 2> Alcohol(2), [Cooking 1] => Mother of Vinegar (3), Alcohol (1)
        //\1 Hour/ <Fabrication 2> Sheet Metal (1), [Sawing 2], [Hammering 1] => Crude Pot (1)
        //\2 Hours/ <Fabrication 3> Chunk of Steel (4), [Welding 1], [Hammering 1] => Crude Pot (1)
        //\4 Hours/ <Fabrication 5> Chunk of Steel (6), [Welding 1], [Hammering 2] => Pot (1)

    //Stats
        //There are 4 core stats
        //Optimism  (Charisma, Luck)
            //Increases odds of Hostile to Neutral, and Neutral to Friendly
            //Higher chance of loot spawning
            //Higher chance of crits
        //Fitness   (Strength, Endurance)
            //Increases how much you can carry
            //Increases raw melee damage
            //Increases HP and Resistances (energy, poison, bleeding, sleep)
        //Grace     (Dexterity, Perception)
            //A lot of good skills use this
            //Decreases crafting times
        //Focus     (Intelligence)
            //Increases XP gain
            //Able to read higher level skill books
            //Increases chance of crits
    //Skills
        //Skills are ranked starting at 0 to 10. Each point is a ++ to something, and some things have a base level needed.
        //Speech (Optimism)    Required for dialogue options
        //Fabrication (Focus)  Required for crafting higher level items
        //Guns (Grace)         Required for gun crafting, using higher level guns, gun accuracy
        //Melee (Fitness)      Required for melee crafting, melee accuracy
        //Lockpick (Grace)     Increases odds of a lock-pick success.
            //Each point increases chance by 15%
        //Stealth (Grace)      Increases odds of sneaking past, getting the jump on someone
        //Slight o Hand(Grace) Increases odds of stealing items, picking pockets
        //Scouting (Grace)     Increases odds of seeing an enemy/friend, faster completion of exploration
        //Cooking (Focus)      Required for food crafting, poison crafting.
        //Chemistry (Focus)    Required for drug and chemical crafting and combining.
        //Medicine (Focus)     Required for medical treatment and medicine creation.

//How To Do Items
    //Create a class called "Items"
    //When initializing an "Item", give it the name of the item desired (aka "Chunk of Steel", "Knife", etc)
    //Use if statements to assign that item respective properties (tags, melee/ranged accuracy, dmg, value)
    //when trying to craft something, search through all items in inventory/in base to see if the property is there OR that it exists at all
    //Inventory and loot containers can be lists of items
    //Create a new class called "ItemList"
    //ItemList functions as a regular list, but specifically for items
    //Functions in it to check for availability of a property, item count, etc
    //Consume function to remove items
    //Return and Add functions to do exactly that

//How To: Combat
    //Each item has it's own combat stats
    //Accuracy (each point either adds or subtracts a 10% to your hit chance)
    //Blunt/Slashing/Piercing (each point is how much damage it does, the player starts at 10 HP, some armor/enemies are strong to one and weak to the other)
    //Stun (each point increases stun chance with the weapon by 20%)
    //Not all weapons have damage stats explicitly written
    //Items without weapon stats are default given -3 Acc and 0 Blunt Damage
    //Fitness adds 1 dmg to melee Weapons
    //There are two types of weapons, ranged and melee
    //Ranged weapons always attack first
    //Melee weapons have a default 10% stun chance
    //Before combat begins, you might be able to get the jump on enemies with Scouting (aka u see them first) OR Stealth (aka u were creeping around)
    //If you get the jump on someone with a melee, you get a free hit with +50% accuracy
    //If you get jump on someone with ranged, you get to fire twice with +20% accuracy



public class Main {

    public static void main(String args[]){

        Main m = new Main();

    }

    public Main(){
        System.out.println("Main Initialized!");

        //Frame Stuff
        JFrame f = new JFrame();
        f.setSize(600,800);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Panels
        JPanel mainPanel = new JPanel();
        JPanel hubPanel = new JPanel();

        //Main Label
        JLabel label = new JLabel();
        label.setText("0");

        //Button Explore
        JButton buttonExplore = new JButton("Explore");
        buttonExplore.setPreferredSize(new Dimension(200,100));
        buttonExplore.addActionListener(new ActionListener() {
            int clicks = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = Integer.toString(clicks + 1);
                label.setText(s);
                clicks++;
                //actrivate explore panel
                System.out.println("new panel!");
                f.setContentPane(Explore(0,f,hubPanel));
            }
        });

        //Button Quit
        JButton buttonQuit = new JButton("Quit");
        buttonExplore.setPreferredSize(new Dimension(200,100));
        buttonQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            }
        });

        //Hub Panel Stuff
        hubPanel.setLayout(new GridLayout(3,1));
        hubPanel.add(buttonExplore);
        hubPanel.add(label);
        hubPanel.add(buttonQuit);

        //Explore Panel
        JPanel explorePanel = new JPanel();
        explorePanel.setLayout(new BorderLayout());
        //explorePanel.add()

        //Main Panel Stuff
        mainPanel.setLayout(new GridLayout(0,3));
        mainPanel.add(hubPanel,explorePanel);
        f.add(hubPanel);
    }

    public JPanel Explore(int location, JFrame f, JPanel quitPanel){
        Random rand = new Random();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,3));
        //Button Quit
        JButton butt = new JButton("Main");
        JLabel desc = new JLabel();
        butt.setPreferredSize(new Dimension(200,100));
        butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Switching to Hub Panel");
                f.setContentPane(quitPanel);
            }
        });
        panel.add(butt);
        panel.add(desc);
        int chance = -1;
        //If location chosen is "Surrounding Waters"
        if (location == 0){
            chance = rand.nextInt(100) + 1;
            if (chance <= 10) {
                desc.setText("<html>Locked Box:<br />You find a floating metal box, sealed shut by a lock.");
            } else if (chance > 10 && chance <= 30){
                desc.setText("<html>Driftwood:<br />A huge plank of driftwood catches your eye, perhaps there is something there of value?");
            } else{
                desc.setText("Nothing of interest.");
            }
        }
        return panel;
    }

}

//Locked Box (10%)
//"You find a floating metal box, sealed shut by a lock."
//Lockpick 35%
//Fitness 5 to break open
//50% Chunk of Steel
//10% Wires (2)
//15% Hammer
//15% Rope
//35% Driftwood
//15% Shirt
//15% Pants
//15% Underwear
//25% Alcohol
//15% Shoes
//15% Hat



class Encounter{

    String desc;


    public Encounter(){

    }

}

class Item{

    ArrayList<String> tags;
    String desc;
    int acc,blunt,slash,pierce;


    public Item(String itemname){
        if (itemname.equals("Chunk of Steel")){
            desc = "A chunk of scrap metal. Sturdy, but flexible if heated. There are dozens of things to craft with this.";
        }
        //else if (itemname.equals(""))
    }


}