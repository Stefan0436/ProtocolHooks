package modkit.enhanced.player.titles;

import java.util.ArrayList;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

/**
 * 
 * Title Interface
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class MinecraftTitle {

	private TitleIntervalConfiguration configuration;
	private ArrayList<TitleComponent> components = new ArrayList<TitleComponent>();

	public static MinecraftTitle create() {
		return new MinecraftTitle();
	}

	/**
	 * Adds a component to this title
	 * 
	 * @param component TitleComponent instance
	 */
	public MinecraftTitle addComponent(TitleComponent component) {
		components.add(component);
		return this;
	}

	/**
	 * Adds components to this title
	 * 
	 * @param components TitleComponent instances
	 */
	public MinecraftTitle addComponents(TitleComponent... components) {
		for (TitleComponent comp : components)
			addComponent(comp);
		return this;
	}

	/**
	 * Adds a component to this title
	 * 
	 * @param type    Title component type
	 * @param message Title message
	 */
	public MinecraftTitle addComponent(TitleType type, String message) {
		addComponent(TitleComponent.create(type, message));
		return this;
	}

	/**
	 * Adds a component to this title
	 * 
	 * @param type    Title component type
	 * @param message Title message component
	 */
	public MinecraftTitle addComponent(TitleType type, Component message) {
		addComponent(TitleComponent.create(type, message));
		return this;
	}

	/**
	 * Removes the given component from the title
	 * 
	 * @param component Component to remove
	 */
	public MinecraftTitle removeComponent(TitleComponent component) {
		if (components.contains(component))
			components.remove(component);
		return this;
	}

	/**
	 * Replaces the given component
	 * 
	 * @param component   Component to replace
	 * @param replacement Replacement component
	 */
	public MinecraftTitle setComponent(TitleComponent component, TitleComponent replacement) {
		if (components.contains(component))
			components.set(components.indexOf(component), replacement);
		return this;
	}

	/**
	 * Replaces the given component
	 * 
	 * @param component   Component index to replace
	 * @param replacement Replacement component
	 */
	public MinecraftTitle setComponent(int component, TitleComponent replacement) {
		components.set(component, replacement);
		return this;
	}

	/**
	 * Removes the given component from the title
	 * 
	 * @param component Component index to remove
	 */
	public MinecraftTitle removeComponent(int component) {
		components.remove(component);
		return this;
	}

	/**
	 * Retrieves all title components
	 * 
	 * @return Array of TitleComponent instances
	 */
	public TitleComponent[] getComponents() {
		return components.toArray(t -> new TitleComponent[t]);
	}

	/**
	 * Assigns the interval configuration for this title
	 * 
	 * @param configuration TitleIntervalConfiguration instance
	 */
	public MinecraftTitle setIntervalConfiguration(TitleIntervalConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}

	/**
	 * Retrieves the interval configuration for this title
	 * 
	 * @return TitleIntervalConfiguration instance
	 */
	public TitleIntervalConfiguration getIntervalConfiguration() {
		return configuration;
	}

	/**
	 * 
	 * Minecraft title component
	 * 
	 * @author Sky Swimmer - AerialWorks Software Foundation
	 *
	 */
	public static class TitleComponent {
		private TitleType type;
		private Component component;

		/**
		 * Creates a new title component
		 * 
		 * @param type    Title type
		 * @param message Title message component
		 * @return TitleComponent instance
		 */
		public static TitleComponent create(TitleType type, Component message) {
			TitleComponent comp = new TitleComponent();
			comp.type = type;
			comp.component = message;
			return comp;
		}

		/**
		 * Creates a new title component
		 * 
		 * @param type    Title type
		 * @param message Title message
		 * @return TitleComponent instance
		 */
		public static TitleComponent create(TitleType type, String message) {
			return create(type, new TextComponent(message));
		}

		/**
		 * Assigns the type of this title component
		 * 
		 * @param type Title type
		 */
		public TitleComponent setType(TitleType type) {
			this.type = type;
			return this;
		}

		/**
		 * Assigns the message of this title component
		 * 
		 * @param message Title message
		 */
		public TitleComponent setMessage(String message) {
			setMessage(new TextComponent(message));
			return this;
		}

		/**
		 * Assigns the message of this title component
		 * 
		 * @param message Title message component
		 */
		public TitleComponent setMessage(Component message) {
			component = message;
			return this;
		}

		/**
		 * Retrieves the title message
		 */
		public Component getMessage() {
			return component;
		}

		/**
		 * Retrieves the title type
		 */
		public TitleType getType() {
			return type;
		}

	}
}
