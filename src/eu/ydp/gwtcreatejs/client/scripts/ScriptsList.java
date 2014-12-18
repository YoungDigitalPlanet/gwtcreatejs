package eu.ydp.gwtcreatejs.client.scripts;

public enum ScriptsList implements ScriptUrl {

	SOUND_JS("createjs/ysoundjs.js"),
	CREATE_JS("createjs/ypreloadjs.js"),
	MOVIE_CLIP("createjs/movieclip-0.4.1.min.js"),
	TWEEN_JS("createjs/tweenjs-0.2.0.min.js"),
	EASEL_JS("createjs/easeljs-0.4.1.min.js");

	private final String url;

	private ScriptsList(String url) {
		this.url = url;
	}

	@Override
	public String getUrl() {
		return url;
	}
}
