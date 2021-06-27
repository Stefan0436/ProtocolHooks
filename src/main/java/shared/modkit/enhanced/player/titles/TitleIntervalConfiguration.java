package modkit.enhanced.player.titles;

/**
 * 
 * Title Interval Configuration
 * 
 * @author Sky Swimmer - AerialWorks Software Foundation
 *
 */
public class TitleIntervalConfiguration {
	private int fadeInTicks = 0;
	private int fadeOutTicks = 0;
	private int activeTicks = 0;

	/**
	 * Creates a new TitleIntervalConfiguration instance.
	 * 
	 * @param fadeIn  Fade-in speed in ticks (how long the fade-in effect takes)
	 * @param active  Activity duration (how long the title stays visible before the
	 *                fade-out effect is played)
	 * @param fadeOut Fade-out speed in ticks (how long the fade-out effect takes)
	 */
	public TitleIntervalConfiguration(int fadeIn, int active, int fadeOut) {
		fadeInTicks = fadeIn;
		activeTicks = active;
		fadeOutTicks = fadeOut;
	}

	/**
	 * Creates a new TitleIntervalConfiguration instance.
	 * 
	 * @param fadeIn  Fade-in speed in ticks (how long the fade-in effect takes)
	 * @param active  Activity duration (how long the title stays visible before the
	 *                fade-out effect is played)
	 * @param fadeOut Fade-out speed in ticks (how long the fade-out effect takes)
	 */
	public static TitleIntervalConfiguration create(int fadeIn, int active, int fadeOut) {
		return new TitleIntervalConfiguration(fadeIn, active, fadeOut);
	}

	/**
	 * Retrieves the fade-in duration
	 * 
	 * @return Fade-in duration length in ticks
	 */
	public int getFadeInDuration() {
		return fadeInTicks;
	}

	/**
	 * Retrieves the fade-out duration
	 * 
	 * @return Fade-out duration length in ticks
	 */
	public int getFadeOutDuration() {
		return fadeOutTicks;
	}

	/**
	 * Retrieves the duration of the activity period
	 * 
	 * @return Activity duration length in ticks
	 */
	public int getActiveDuration() {
		return activeTicks;
	}
}
