package io.github.nickiguess;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicReloadedEvent;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;


public class EventListener implements Listener {	
	@EventHandler
	public void onMythicMechanicLoad(MythicMechanicLoadEvent event)	{
		String eventMechanic = event.getMechanic().toString();
		if (eventMechanic.equals("Optional.empty")) {
			if(event.getMechanicName().equalsIgnoreCase("ACCESSTELEPORT"))	{
				SkillMechanic mechanic = new TeleportMechanic(event.getConfig());
				event.register(mechanic);
			}
		}
	}
	
	@EventHandler
	public void onMythicMobsReload(MythicReloadedEvent event) throws UnknownDependencyException, InvalidPluginException, InvalidDescriptionException {
		
	}
}
