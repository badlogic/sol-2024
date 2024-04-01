package io.marioslab.sol.scenes;

import com.badlogic.gdx.graphics.Color;
import io.marioslab.sol.Assets;
import io.marioslab.sol.Inventory;
import io.marioslab.sol.Scene;
import io.marioslab.sol.Trigger;
import io.marioslab.sol.command.IfHasNot;
import io.marioslab.sol.command.AddItem;
import io.marioslab.sol.command.MoveTo;
import io.marioslab.sol.command.MoveToAnim;
import io.marioslab.sol.command.New;
import io.marioslab.sol.command.NextScene;
import io.marioslab.sol.command.PlaySound;
import io.marioslab.sol.command.Remove;
import io.marioslab.sol.command.SetAnimation;
import io.marioslab.sol.command.StopMusic;
import io.marioslab.sol.command.Wait;
import io.marioslab.sol.entity.Animated;
import io.marioslab.sol.entity.Fade;
import io.marioslab.sol.entity.Image;
import io.marioslab.sol.entity.Text;

public class OutdoorScene extends Scene {
	public OutdoorScene() {
		Assets.playMusic("adventure", 0.4f, true);
		
		// setup scene
		add(new Image("background", "outdoor", 0, 0, 0));
		add(new Animated("stef", "idle-left", 320, 80, 10));
		if(!Inventory.has("bird")) add(new Animated("bird", "bird", 71, 177, 1));
		
		// intro
		add(new Fade(Color.WHITE, 1, true));
		add(new MoveToAnim("stef", "walk", 220, 80, 64));
		add(new SetAnimation("stef", "front"));
		add(new Text("Dum Di Dum...", Color.BLACK, 2.5f, 250, 186));
		add(new Wait(2.5f));
		add(new SetAnimation("stef", "front"));		
		add(new Text("Cold out here", Color.BLACK, 2.5f, 250, 186));
		
		// bird trigger
		if(!Inventory.has("bird")) { 
			add(new Trigger("bird-trigger", 70, 177, 87, 195)
					.add(new MoveToAnim("stef", "walk", 60, 80, 64))
					.add(new SetAnimation("stef", "back"))
					.add(new Wait(0.2f))
					.add(new Text("Away, stupid bird!", Color.BLACK, 2f, 71, 220))
					.add(new Wait(0.2f))
					.add(new SetAnimation("bird", "bird-fly"))
					.add(new PlaySound("vulture"))
					.add(new MoveTo("bird", 200, 241, 80))
					.add(new SetAnimation("stef", "front"))
					.add(new Text("And stay away!", Color.BLACK, 2f, 71, 220))
					.add(new AddItem("bird"))
					.disable()
			);
		}
		
		// window trigger
		add(new Trigger("window-trigger", 183, 118, 226, 152)
				.add(new MoveToAnim("stef", "walk", 200, 80, 64))			
				.add(new SetAnimation("stef", "back"))
				.add(new PlaySound("glass"))
				.add(new Wait(2f))
				.add(new SetAnimation("stef", "front"))
				.add(new Text("I hope he cleans that soon", Color.BLACK, 2f, 220, 186))				
				.add(new Wait(2))
		);
		
		// doorbell trigger
		add(new Trigger("doorbell-trigger", 123, 112, 134, 126)
				.add(new MoveToAnim("stef", "walk", 100, 80, 64))
				.add(new SetAnimation("stef", "back"))
				.add(new Text("Ding Dong", Color.BLACK, 1f, 120, 186))
				.add(new PlaySound("door-bell"))
				.add(new Wait(1f))
				.add(new SetAnimation("stef", "front"))
				.add(new Text("Hihi", Color.BLACK, 1f, 120, 186))			
		);
		
		// door trigger
		add(new Trigger("door-trigger", 86, 81, 121, 155)
			.add(new IfHasNot(set("bird"), array(
				new New(new Text("That bird has to go!", Color.BLACK, 2f, 220, 186))
			)))
			.add(new MoveToAnim("stef", "walk", 92, 80, 64))
			.add(new SetAnimation("stef", "back"))
			.add(new Image("opendoor", "opendoor", 83, 80, 1))
			.add(new PlaySound("door-open"))
			.add(new Wait(1))
			.add(new PlaySound("door-close"))
			.add(new Remove("opendoor"))
			.add(new Remove("stef"))
			.add(new Text("Home Sweet Home", Color.BLACK, 2, 80, 186))
			.add(new Fade(Color.WHITE, 1, false))
			.add(new Wait(1))
			.add(new StopMusic("adventure"))
			.add(new NextScene(new EntranceScene()))
		);
	}
}
