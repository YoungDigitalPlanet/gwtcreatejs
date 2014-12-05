package eu.ydp.gwtcreatejs.client.scripts;

enum ScriptsList {

	FLASH_PLUGIN("createjs/yflashplugin.js"), SOUND_JS("createjs/ysoundjs.js"), CREATE_JS("createjs/ypreloadjs.js"), MOVIE_CLIP(
			"createjs/movieclip-0.4.1.min.js"), TWEEN_JS("createjs/tweenjs-0.2.0.min.js"), EASEL_JS("createjs/easeljs-0.4.1.min.js");

	private final String url;

	private ScriptsList(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
