package vo;

public class news {
	private String titles;
	private String text;

	public news(String titles, String text) {
		super();
		this.titles = titles;
		this.text = text;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
