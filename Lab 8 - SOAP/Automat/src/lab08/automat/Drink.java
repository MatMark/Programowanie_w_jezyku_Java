package lab08.automat;

public class Drink {
	private Integer count = 0;
	private String name = "";

	public Drink(Integer count, String name) {
		this.count = count;
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
